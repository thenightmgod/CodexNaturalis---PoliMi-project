package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.RMIClient;
//import it.polimi.ingsw.Network.Socket.SocketClient;
import it.polimi.ingsw.View.GameView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TUI implements GameView {

    boolean connectionType;

    CardsTUI cards = new CardsTUI();

    Player Turn;

    String name = "Carlos O'Connell";

    CommonClient client;

    int ServerPort= 4444;

    public TUI(){}

    public void startTui() throws RemoteException {
        joinGame();
    }

    @Override
    public void twenty(String name){
        if(name.equals(client.getName()))
            System.out.println("You arrived at 20 points");
        else System.out.println( name + " arrived at 20 points");
    }

    @Override
    public void lastRound(){
        System.out.println("This is your last turn");
    }

    @Override
    public void updatePoints(int points, String name){
        //TODO
    }

    @Override
    public void leaveGameMessage(){
        System.out.println("Someone left the game, you'll have to start a new one");
        //gestire il post leave come cazzo crei la partita di nuovo
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

    public void chooseGoalCard() throws RemoteException {
        boolean goon = false;
        int i;
        do {
            try {
                System.out.println("write '1' for the card on the top or '2' for the card on the bottom?");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                i = Integer.parseInt(reader.readLine());
                if (i == 1 || i == 2) {
                    client.chooseGoalCard(i, this.client);
                    goon = true;
                } else {
                    System.out.println("You put a number which isn't ");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e){
                System.out.println("Please enter a number!");
            }
        } while (!goon);
        endTurn("GoalCard");
    }

    @Override
    public void updateHands(LinkedList<PlayableCard> hand, String name) {
        client.getClient().setHand(hand);
        System.out.println("This is your new hand");
        cards.plotHandSeria(client.getClient());
    }

    @Override
    public void updateField(PlayingField field, String name) {
        if(name.equals(client.getName())) {
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
        LinkedList<Position> positions = client.getClient().getFreePositions();
        System.out.println(positions);
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
                System.out.println(name + "has drawn a card");
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
        String f;
        boolean face;
        boolean goon = false;
        do {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Do you want to place your Start Card Front or Back?\n1 --> Front\n0 --> Back");
                f = reader.readLine();
                if(f.equals("1") || f.equals("0")){
                    if(f.equals("1"))
                        face = true;
                    else face = false;
                    client.setStartCardFace(face, client);
                    goon = true;
                } else {
                    System.out.println("You must choose between 1(front) or 0(back)");
                }
            } catch(IOException e){
                System.out.println("There was an error while reading the front or back");
            }
        } while(!goon);

        //da controllare sta situa
        endTurn("StartCard");

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
                if(details.equals("PlaceCardWell"))
                    drawCard();
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
                joinGame();
            }
            case "Exception" ->
                    System.out.println("There has been an error");
        }
    }

    public void createGame() throws RemoteException, NotBoundException {

        System.out.println("CREATING A NEW GAME...");
        int input = 0;

        boolean goon = false;

        do {
            try {
                System.out.println("Enter the number of players");

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                input = Integer.parseInt(reader.readLine());

                if (input < 2 || input > 4)
                    System.out.println("The number of players must be between 2 and 4!");
                else {
                    goon = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while(!goon);

        //try{
        if(connectionType){

        } else{
            client = new RMIClient(name);
            client.setView(this);
        }

        client.createGame(name, input);// è tutto nullo perchè così è inizializzata la view
    }

    public void startingGame(){
        System.out.println("Game is starting...");
    }

    public void joinGame() throws RemoteException {
        boolean goon = false;
        do {
            try {
                getNickname();
                this.client = chooseClient(name);
                this.client.joinGame(name);
                goon = true;

            } catch (RemoteException e){
                System.out.println("there's been a problem in the join game");
            }
        } while(!goon);

    }

    public CommonClient chooseClient(String name){
        Scanner scan = new Scanner(System.in);
        String connection;

        System.out.println("Choose the kind of connection you want to play with");

        do{
            System.out.println("0 --> RMI\n1 --> Socket");
            connection = scan.next();

            if(connection.equals("0")){
                try{
                    client = new RMIClient(name);
                    client.setView(this);
                    connectionType = false;
                } catch (RemoteException e) {
                    System.out.println("an exception occurred while starting the client");
                } catch (NotBoundException e){
                    System.out.print("NotBoundException occurred while initializing the client");
                }
            }else {
                /*client = new SocketClient(name);
                client.setView(this);
                connectionType = true;*/
            }

        } while(!connection.equals("0") && !connection.equals("1"));

        return client;
    }

    public void getNickname(){
        String nickname = "Carlos";

        try {
            System.out.println("Write your nickname");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            nickname = reader.readLine();

        } catch (IOException e) {
            System.out.println("There has been an error with the nickname");
        }

        name = nickname;
    }

    private void isYourTurn() throws RemoteException {
        placeCard();
    }


    @Override
    public void updateTurn(Player player, String mex) throws RemoteException {
        //robaccia per printare che non è il tuo turno e bla bla
        this.Turn = player;
        if(Turn.getName().equals(client.getName())) {
            switch (mex) {
                case "StartCard" -> setStartCardFace();
                case "GoalCard" -> chooseGoalCard();
                case "NormalTurn" -> isYourTurn();
            }
        }
        return;
    }

    @Override
    public void printNotYourTurn(Player player) {
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


        if(client.getName().equals(standings.get(0)))
            System.out.println("JACKIE DOWN THE LINE YOU WON");
        else if (client.getName().equals(standings.get(1))) {
            System.out.println("JACKIE DOWN THE LINE YOU GOT THE SECOND PLACE");
        }
        else if (client.getName().equals(standings.get(2))) {
            System.out.println("JACKIE DOWN THE LINE YOU GOT THE THIRD PLACE");
        } else {
            System.out.println("JACKIE DOWN THE LINE YOU GOT THE FOURTH PLACE COGLIONE");
        }
        //far finire tutto
    }

    //            FUNZIONI PER GIOCARE

    private int getIndex() {

        int input = 77;

        boolean goon = false;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = Integer.parseInt(reader.readLine());

        } catch (NumberFormatException e) {
            System.out.println("Please enter a number!");
        } catch (IOException e) {
            System.out.println("getindex sbatti!");
            throw new RuntimeException();
        }


        return input;
    }


    private void placeCard() throws RemoteException {
        boolean goon = false;
        int i, x, y;
        boolean face = false;
        FB f = FB.FRONT;
        int fac;
        do {
            try {
                System.out.println("Which card do you want to place?");
                i = getIndex();
                System.out.println("In what position do you want to place it?\n Send the X first");
                x = getIndex();
                System.out.println("And now the Y");
                y = getIndex();
                System.out.println("Do you want to place it Front or Back?\n1 --> Front\n0 --> Back");
                fac = getIndex();
                if (fac == 1 || fac == 0) {
                    if (fac == 0)
                        f = FB.BACK;
                    client.placeCard(client, i, x, y, f);
                    goon = true;
                }
                else{
                    System.out.println("You must choose between 1(front) or 0(back)");
                }
            } catch (IOException e){
                System.out.println("I/O exception in place card");
            } catch (NumberFormatException e){
                System.out.println("Please enter a number");
            }
        } while (!goon) ;

    }

    public void drawCard() throws RemoteException {
            boolean goon = false;
            int whichDeck, whichCard;
            do {
                try {
                    cards.plotDrawables(client.getClient());
                    System.out.println("From which deck do you want to draw?\n1 --> ResourceDeck\n2 --> GoldDeck");
                    whichDeck = getIndex();
                    System.out.println("Which card do you want to pick?");
                    whichCard = getIndex();
                    client.drawCard(whichDeck, whichCard, client);
                        goon = true;
                } catch (IOException e) {
                    System.out.println("There has been a i/o problem, try again!");
                } catch (NumberFormatException e){
                    System.out.println("Please enter a number!");
                }

            }while (!goon) ;

        }
}