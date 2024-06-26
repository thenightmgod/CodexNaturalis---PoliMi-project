package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.CommonClient;
//import it.polimi.ingsw.Network.Socket.SocketClient;
import it.polimi.ingsw.View.GameView;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;


public class TUI implements GameView {

    boolean connectionType;
    CardsTUI cards = new CardsTUI();
    Player Turn;
    String name = "Carlos O'Connell";
    CommonClient client;
    int ServerPort= 4444;
    String serverIp;
    InputHandler inputHandler;
    TuiChat chat = new TuiChat();
    Boolean mirko = true;

    public TUI(){}

    public void startTui() throws RemoteException {
        inputHandler = new InputHandler(this);
        inputHandler.handleUserInput("askServerIp");
        inputHandler.handleUserInput("chooseClient");
        joinGame();
    }

    @Override
    public void updateChat(String name, LinkedList<ChatMessage> chat){
        if(name.equals(this.name)){
            this.chat.setMessages(chat);
        }
    }

    @Override
    public void twenty(String name){
        if(name.equals(client.getNames()))
            System.out.println("You arrived at 20 points");
        else System.out.println( name + " arrived at 20 points");
    }

    @Override
    public void lastRound(){
        mirko = false;
        System.out.println("This is your last turn");
    }

    @Override
    public void updatePoints(HashMap<String, Integer> points, String name){
        client.getClient().setPointsCounter(points);
    }

    @Override
    public void leaveGameMessage(){
        System.out.println("Someone left the game, you'll have to start a new one");
        System.exit(0);
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        System.out.println("Choose your personal GoalCard");
        cards.printGoalCard(goals.getFirst());
        cards.printGoalCard(goals.getLast());
    }

    public void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException{
        client.getClient().setCommonGoals(goals);
        cards.plotGoals(client.getClient());
    }


    @Override
    public void updateHands(LinkedList<PlayableCard> hand, String name) {
        client.getClient().setHand(hand);
        System.out.println("This is your new hand");
        cards.plotHandSeria(client.getClient());
    }

    @Override
    public void updateField(PlayingField field, String name) {
        if(name.equals(client.getNames())) {
            client.getClient().setField(field);
            System.out.println("Your new field is:\n");
            cards.plotPlayingField(client.getClient());
        }
        else{
            System.out.println(name + " has placed a card");
        }
    }

    @Override
    public void updateFreePosition(String name, LinkedList<Position> freePositions) {
        client.getClient().setFreePositions(freePositions);
        System.out.println("Your new free positions are:");
        System.out.println("(remember that the start card is placed in (0, 0))");
        cards.plotFreePos(client.getClient());

    }

    @Override
    public void updateResourceDeck(LinkedList<ResourceCard> resourceCards, boolean start, String name){
        if(start){
            System.out.println("The resource deck has been created!\n");
            client.getClient().setDrawableResourceCards(resourceCards);
        } else {
            client.getClient().setDrawableResourceCards(resourceCards);
            if (name.equals(Turn.getName())) {
//                System.out.println("These are the new drawable cards");
                client.getClient().setDrawableResourceCards(resourceCards);
            } else {
                System.out.println(Turn.getName() + " has drawn a card");
//                System.out.println("These are the new drawable cards");
                client.getClient().setDrawableResourceCards(resourceCards);
            }
        }
    }

    @Override
    public void updateGoldDeck(LinkedList<GoldCard> goldCards, boolean start, String name){
        if(start){
            System.out.println("The gold deck has been created!\n");
            client.getClient().setDrawableGoldCards(goldCards);
        } else {
            client.getClient().setDrawableGoldCards(goldCards);
            if (name.equals(Turn.getName())) {
//                System.out.println("These are the new drawable cards");
                client.getClient().setDrawableGoldCards(goldCards);
            } else {
                System.out.println(name + "has drawn a card");
//                System.out.println("These are the new drawable cards");
                client.getClient().setDrawableGoldCards(goldCards);
            }
        }
    }

    @Override
    public void showStartCard(StartCard card) throws RemoteException {
        cards.printFrontStartCard(card);
        cards.printBackStartCard(card);

    }

    public void setStartCardFace() throws RemoteException {
        inputHandler.handleUserInput("setStartCardFace");
    }

    public void chooseGoalCard() throws RemoteException {
        inputHandler.handleUserInput("chooseGoalCard");
    }

    public void placeCard() throws RemoteException {
        inputHandler.handleUserInput("placeCard");
    }

    public void drawCard() throws RemoteException {
        cards.plotDrawables(client.getClient());
        inputHandler.handleUserInput("drawCard");
    }

    public void sendChatMessage(ChatMessage mex) throws RemoteException {
        client.sendChatMessage(mex);
    }

    @Override
    public void showException(String exception, String details) throws RemoteException, NotBoundException {
        switch(exception) {
            case "WrongIndexException" -> {
                switch(details) {
                    case "DrawDeck" -> {
                        System.out.println("you put an index which isn't 1 or 2");
                        drawCard();
                    }
                    case "DrawIndex" -> {
                        System.out.println("you put an index which isn't between 1 and 3");
                        drawCard();
                    }
                    case "PlaceCard" -> {
                        System.out.println("You chose a card which wasn't in your hand, put an index between 1 and 3!");
                        placeCard();
                    }
                }
            }
            case "Nothing" -> {
                if(details.equals("PlaceCardWell")) {
                    if (mirko) drawCard();
                    else {
                        endTurn("NormalTurn");
                    }
                }
            }
            case "WrongPositionException" -> {
                System.out.println("The position you chose isn't in the free ones!");
                System.out.println("Look at the free position and choose one in those");
                placeCard();
            }
            case "WrongPlayersNumberException" -> {
                System.out.println("This shouldn't happen");
            }
            case "RoomNotExistsException" -> {
                System.out.println("There isn't a pre existing game, you'll have to create a game from scratch");
                createGame();
            }
            case "RoomFullException" -> {
                System.out.println("The last game is full, you'll have to create a game from scratch!");
                createGame();
            }
            case "RequirementsNotSatisfied" -> {
                System.out.println("The requirements for the card you chose aren't satisfied! ");
                System.out.println("Flip this gold card or play another one!");
                placeCard();
            }
            case "NameAlreadyTakenException" -> {
                System.out.println("Name already taken! Please try again!");
                setName();
            }
            case "Exception" ->
                    System.out.println("There has been an error");
        }
    }

    public void setName() throws RemoteException {
        inputHandler.handleUserInput("setName");
    }

    public void createGame() throws RemoteException {
        inputHandler.handleUserInput("createGame");
    }

    public void startingGame(){
        System.out.println("Game is starting...");
    }

    public void joinGame() throws RemoteException {
        try {
            this.client.joinGame(name);
        } catch (RemoteException e){
            System.out.println("there's been a problem in the join game");
        }
    }


    private void isYourTurn() throws RemoteException {
        System.out.println();
        cards.plotPoints(client.getClient());
        System.out.println();
        inputHandler.handleUserInput("chatMessage");
        placeCard();
    }


    @Override
    public void updateTurn(Player player, String mex) throws RemoteException {
        //robaccia per printare che non è il tuo turno e bla bla
        this.Turn = player;
        if(Turn.getName().equals(client.getNames())) {
            switch (mex) {
                case "StartCard" -> setStartCardFace();
                case "GoalCard" -> chooseGoalCard();
                case "NormalTurn" -> isYourTurn();
            }
        }
        return;
    }

    @Override
    public void printNotYourTurn(Player player, String mex) {
        this.Turn = player;
        System.out.println("It's " + Turn.getName() + "'s turn");
    }

    public void endTurn(String mex) throws RemoteException {
        client.endTurn(Turn.getName(), mex);
    }

    @Override
    public void declareWinner(LinkedList<String> standings) {

        System.out.println("...");
        System.out.println("You are jackie down the line...");
        System.out.println("...");
        System.out.println("...");
        System.out.println("...");
        System.out.println("...");


        if(client.getNames().equals(standings.get(0)))
            System.out.println("JACKIE DOWN THE LINE YOU WON");
        else if (client.getNames().equals(standings.get(1))) {
            System.out.println("JACKIE DOWN THE LINE YOU GOT THE SECOND PLACE");
        }
        else if (client.getNames().equals(standings.get(2))) {
            System.out.println("JACKIE DOWN THE LINE YOU GOT THE THIRD PLACE");
        } else {
            System.out.println("JACKIE DOWN THE LINE YOU GOT THE FOURTH PLACE");
        }
        //far finire tutto
        System.exit(0);
    }

}