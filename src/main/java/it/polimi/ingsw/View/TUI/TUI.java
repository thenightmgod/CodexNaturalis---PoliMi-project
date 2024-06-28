package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
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

/**
 * This class is the Text User Interface of the game, it's the view of the game
 */
public class TUI implements GameView {

    /**
     * This is the connection type of the client
     */
    boolean connectionType;

    /**
     * This is the class that handles the cards
     */
    CardsTUI cards = new CardsTUI();
    /**
     * This is the player that has the turn
     */
    Player Turn;
    String name = "Carlos O'Connell";
    /**
     * This is the client that is connected to the server
     */
    CommonClient client;
    /**
     * This is the port of the server
     */
    int ServerPort= 4444;
    /**
     * This is the ip of the server
     */
    String serverIp;
    /**
     * This is the input handler of the client
     */
    InputHandler inputHandler;
    /**
     * This is the chat of the client
     */
    TuiChat chat = new TuiChat();
    /**
     * This is the boolean that tells if it's the last turn
     */
    Boolean mirko = true;
    /**
     * This is the color of the player
     */
    PlayerColor color;
    /**
     * This is the constructor of the class
     */
    public TUI(){}

    /**
     * Starts the TUI of the game
     */
    public void startTui() throws RemoteException {
        inputHandler = new InputHandler(this);
        inputHandler.handleUserInput("askServerIp");
        inputHandler.handleUserInput("chooseClient");
        joinGame();
    }

    /**
     * Updates the colors for this client.
     *
     * @param turn The player whose turn it is.
     * @param colors The updated colors.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void updateColors(Player turn, LinkedList<PlayerColor> colors) throws RemoteException {
        this.client.getClient().setColors(colors);
        this.inputHandler.handleUserInput("updateColors");
        this.client.endColor(color);
    }

    /**
     * Updates the chat of the client
     *
     * @param name The name of the player
     * @param chat The chat of the game.
     */
    @Override
    public void updateChat(String name, LinkedList<ChatMessage> chat){
        if(name.equals(this.name)){
            this.chat.setMessages(chat);
        }
    }

    /**
     * Notifies the client when the 20 points are achieved.
     *
     * @param name the name of the player that does it.
     */
    @Override
    public void twenty(String name){
        if(name.equals(client.getNames()))
            System.out.println("You arrived at 20 points");
        else System.out.println( name + " arrived at 20 points");
    }

    /**
     * Notifies the clients when the last round begins
     */
    @Override
    public void lastRound(){
        mirko = false;
        System.out.println("This is your last turn");
    }
    /**
     * Updates the points for this client.
     *
     * @param points The new points for this client.
     * @param name The name of the client.
     */
    @Override
    public void updatePoints(HashMap<String, Integer> points, String name){
        client.getClient().setPointsCounter(points);
    }

    /**
     * Sends a message to this client that someone has left the game.
     *
     */
    @Override
    public void leaveGameMessage(){
        System.out.println("Someone left the game, you'll have to start a new one");
        System.exit(0);
    }

    /**
     * Shows the goal cards for this client.
     *
     * @param goals The new goal cards for this client.
     */
    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        System.out.println("Choose your personal GoalCard");
        cards.printGoalCard(goals.getFirst());
        cards.printGoalCard(goals.getLast());
    }

    /**
     * Shows the common goal cards for this client.
     *
     * @param goals The new common goal cards for this client.
     */
    public void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException{
        client.getClient().setCommonGoals(goals);
        cards.plotGoals(client.getClient());
    }

    /**
     * Shows the hand for this client.
     *
     * @param hand The new hand for this client.
     */
    @Override
    public void updateHands(LinkedList<PlayableCard> hand, String name) {
        client.getClient().setHand(hand);
        System.out.println("This is your new hand");
        cards.plotHandSeria(client.getClient());
    }

    /**
     * Shows the field for this client.
     *
     * @param field The new field for this client.
     * @param name The name of the player
     */
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

    /**
     * Shows the free positions for this client.
     *
     * @param freePositions The new free positions for this client.
     * @param name The name of the player
     */
    @Override
    public void updateFreePosition(String name, LinkedList<Position> freePositions) {
        client.getClient().setFreePositions(freePositions);
        System.out.println("Your new free positions are:");
        System.out.println("(remember that the start card is placed in (0, 0))");
        cards.plotFreePos(client.getClient());

    }

    /**
     * Updates the resource deck for this client.
     *
     * @param resourceCards The new resource deck for this client.
     * @param start The boolean that tells if it's the start of the game.
     * @param name The name of the player
     */
    @Override
    public void updateResourceDeck(LinkedList<ResourceCard> resourceCards, boolean start, String name){
        if(start){
            System.out.println("The resource deck has been created!\n");
            client.getClient().setDrawableResourceCards(resourceCards);
        } else {
            client.getClient().setDrawableResourceCards(resourceCards);
            if (name.equals(Turn.getName())) {
                client.getClient().setDrawableResourceCards(resourceCards);
            } else {
                System.out.println(Turn.getName() + " has drawn a card");
                client.getClient().setDrawableResourceCards(resourceCards);
            }
        }
    }

    /**
     * Updates the gold deck for this client.
     *
     * @param goldCards The new gold deck for this client.
     * @param start The boolean that tells if it's the start of the game.
     * @param name The name of the player
     */
    @Override
    public void updateGoldDeck(LinkedList<GoldCard> goldCards, boolean start, String name){
        if(start){
            System.out.println("The gold deck has been created!\n");
            client.getClient().setDrawableGoldCards(goldCards);
        } else {
            client.getClient().setDrawableGoldCards(goldCards);
            if (name.equals(Turn.getName())) {
                client.getClient().setDrawableGoldCards(goldCards);
            } else {
                System.out.println(name + "has drawn a card");
                client.getClient().setDrawableGoldCards(goldCards);
            }
        }
    }

    /**
     * Shows the start card for this client.
     *
     * @param card The new start card for this client.
     */
    @Override
    public void showStartCard(StartCard card) throws RemoteException {
        cards.printFrontStartCard(card);
        cards.printBackStartCard(card);

    }

    /**
     * Asks the client to choose the start card face.
     */
    public void setStartCardFace() throws RemoteException {
        inputHandler.handleUserInput("setStartCardFace");
    }

    /**
     * Asks the client to choose the goal card.
     */
    public void chooseGoalCard() throws RemoteException {
        inputHandler.handleUserInput("chooseGoalCard");
    }

    /**
     * Asks the client to place a card.
     */
    public void placeCard() throws RemoteException {
        inputHandler.handleUserInput("placeCard");
    }

    /**
     * Asks the client to draw a card.
     */
    public void drawCard() throws RemoteException {
        cards.plotDrawables(client.getClient());
        inputHandler.handleUserInput("drawCard");
    }

    /**
     * Sends a chat message to the server.
     */
    public void sendChatMessage(ChatMessage mex) throws RemoteException {
        client.sendChatMessage(mex);
    }

    /**
     * Handles exceptions that occur during the game and provides appropriate responses.
     * This method takes in the type of exception and additional details to determine the correct response.
     *
     * @param exception The type of exception that occurred.
     * @param details Additional details about the exception.
     * @throws RemoteException If a remote access error occurs.
     * @throws NotBoundException If an attempt is made to lookup or unbind in the registry a name that has no associated binding.
     */
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

    /**
     * Sets the name of the player by handling user input.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    public void setName() throws RemoteException {
        inputHandler.handleUserInput("setName");
    }

    /**
     * Creates a new game by handling user input.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    public void createGame() throws RemoteException {
        inputHandler.handleUserInput("createGame");
    }

    /**
     * Notifies that the game is starting.
     */
    public void startingGame(){
        System.out.println("Game is starting...");
    }

    /**
     * Allows the client to join the game
     */
    public void joinGame() throws RemoteException {
        try {
            this.client.joinGame(name);
        } catch (RemoteException e){
            System.out.println("there's been a problem in the join game");
        }
    }

    /**
     * This method is called when it's the turn of the client. It prints the points of the client,
     * asks for a chat message input and then proceeds to place a card.
     *
     * @throws RemoteException If a remote access error occurs.
     */
    private void isYourTurn() throws RemoteException {
        System.out.println();
        cards.plotPoints(client.getClient());
        System.out.println();
        inputHandler.handleUserInput("chatMessage");
        placeCard();
    }

    /**
     * This method is called to update the turn of the game. It sets the current player and if it's the turn of the client,
     * it handles the different cases based on the provided message.
     *
     * @param player The player whose turn it is.
     * @param mex The message provided.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void updateTurn(Player player, String mex) throws RemoteException {
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

    /**
     * This method is called to print a message indicating that it's not the turn of the client.
     *
     * @param player The player whose turn it is.
     * @param mex The message indicating the type of turn.
     */
    @Override
    public void printNotYourTurn(Player player, String mex) {
        this.Turn = player;
        System.out.println("It's " + Turn.getName() + "'s turn");
    }

    /**
     * Ends the current turn . This method sends a message to the server to indicate the end of the turn.
     *
     * @param mex The message to be sent to the server.
     * @throws RemoteException If a remote access error occurs.
     */
    public void endTurn(String mex) throws RemoteException {
        client.endTurn(Turn.getName(), mex);
    }

    /**
     * Declares the winner of the game based on the final standings. This method prints out the result of the game
     * and the position of the client in the standings.
     *
     * @param standings The final standings of the game.
     */
    @Override
    public void declareWinner(LinkedList<String> standings) {

        System.out.println("...");
        System.out.println("...");
        System.out.println("...");
        System.out.println("...");
        System.out.println("...");
        System.out.println("...");


        if(client.getNames().equals(standings.get(0)))
            System.out.println("YOU WON");
        else if (client.getNames().equals(standings.get(1))) {
            System.out.println("YOU GOT THE SECOND PLACE");
        }
        else if (client.getNames().equals(standings.get(2))) {
            System.out.println("YOU GOT THE THIRD PLACE");
        } else {
            System.out.println("YOU GOT THE FOURTH PLACE");
        }
        System.exit(0);
    }
}