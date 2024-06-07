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

    String error = "default";

    public TUI(){}

    public void startTui() throws RemoteException {
        joinGame();
    }

    public void showPoints(){}

    @Override
    public void updatePoints(int points, String name){

        //DA RIFARE COL MENU'

        /*if(name.equals(Turn.getName())) {
            client.getClient().setPointsCounter(points);
            System.out.println("Your new points are" + points);
        }
        else{
            System.out.println(name+ "'s new points are" + points);
        }*/
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        cards.printGoalCard(goals.getFirst());
        cards.printGoalCard(goals.getLast());
    }

    public void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException{
        client.getClient().setCommonGoals(goals);
    }

    public void chooseGoalCard() throws RemoteException {
        boolean goon = false;
        int i;
        do {
            try {
                System.out.println("Choose your personal GoalCard: 1 or 2?");
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
        cards.plotHand(client.getClient());
    }

    @Override
    public void updateField(PlayingField field, String name) {
        if(name.equals(client.getName())) {
            client.getClient().setField(field);
            System.out.println("Your new field is:\n");
            cards.plotPlayingField(client.getClient());
        }
        else{
            System.out.println(name + "has placed a card");
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
    public void updateGoldDeck(LinkedList<GoldCard> goldDeck, boolean start, String name) {
        if (start) {
            System.out.println("The gold deck has been created!\n");
            client.getClient().setDrawableGoldCards(goldDeck);
        }
        else {
            client.getClient().setDrawableGoldCards(goldDeck);
            if (name.equals(Turn.getName())) {
                cards.plotGoldDeck(client.getClient());
            } else {
                System.out.println(name + "has drawn a card");
                System.out.println("These are the new drawable cards");
                cards.plotGoldDeck(client.getClient());
            }
        }

    }

    @Override
    public void updateResourceDeck(LinkedList<ResourceCard> resourceCards, boolean start, String name){
        if(start){
            System.out.println("The resource deck has been created!\n");
            client.getClient().setDrawableResourceCards(resourceCards);
        } else {
            client.getClient().setDrawableResourceCards(resourceCards);
            if (name.equals(Turn.getName())) {
                System.out.println("These are the new drawable cards");
                client.getClient().setDrawableResourceCards(resourceCards);
            } else {
                System.out.println(name + "has drawn a card");
                System.out.println("These are the new drawable cards");
                client.getClient().setDrawableResourceCards(resourceCards);
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
                System.out.println("Write 'front' or 'back' to decide which way you want to place the start card");
                f = reader.readLine();
                if(f.equals("front") || f.equals("back")){
                    if(f.equals("front"))
                        face = true;
                    else face = false;
                    client.setStartCardFace(face, client);
                    goon = true;
                } else {
                    System.out.println("You need to write front or back");
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
                System.out.println("Enter the number of players: ");

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

        /*} catch (RemoteException e) {
            System.out.println("an exception occurred while starting the client");
        } catch (NotBoundException e){
            System.out.print("NotBoundException occurred while initializing the client");
        } catch (WrongPlayersNumberException e){
            System.out.print("The number of players isn't supported");
        }*/

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
                System.out.println("there has been a problem in the join game");
            }
        } while(!goon);

    }

    public CommonClient chooseClient(String name){
        Scanner scan = new Scanner(System.in);
        String connection;


        System.out.println("What kind of connection would you like?");

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
            System.out.println("What kind of nickname would you like?");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            nickname = reader.readLine();

        } catch (IOException e) {
            System.out.println("There has been an error with the nickname");
        }

        name = nickname;
    }

    /*public void isYourTurnPt1() throws RemoteException {
        if(Turn.getName().equals(client.getName())) {
            boolean goon = false;
            String roba;
            do {
                try {
                    Menu1();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    roba = reader.readLine();
                    switch (roba) {
                        case "pgc" -> {
                            LinkedList<GoalCard> toPrint = client.getClient().getCommonGoals();
                            //for(GoalCard gc : toPrint)
                                //cards.printGoalCard(gc);
                            //plot goal cards da modellino (?)
                        }
                        case "hand" -> {
                            LinkedList<PlayableCard> toPrint = Turn.getHand();
                            for(int i =0; i<3; i++){
                                if(Turn.getCardFromHand(i).getId()>=1 && Turn.getCardFromHand(i).getId()<=40) {
                                    cards.printFrontResourceCard(Turn.getCardFromHand(i));
                                    cards.printBackResourceCard(Turn.getCardFromHand(i));
                                }
                                else {
                                    cards.printFrontGoldCard((GoldCard) Turn.getCardFromHand(i));
                                    cards.printBackGoldCard((GoldCard) Turn.getCardFromHand(i));
                                }
                            }

                            // poi può flippare
                        }
                        case "score" -> {
                            //GameController Grian = client.
                            //plot points
                        }
                        case "other" -> {
                            //choose player e poi plotti il suo field
                        }
                        case "place" -> {
                            placeCard();
                            goon = true;
                        }
                        case "myField" -> {
                            cards.plotPlayingField(Turn);
                        }
                        // case "freePosition" eventuale
                        case "toDraw" -> {
                            //plotti quelle pescabili
                        }
                        case "chat" -> {
                            //scrivi in chat
                        }
                        case "showChat" -> {
                            //show chat
                        }
                        case "q" -> {
                            //leave game
                        }
                        default -> System.out.println("Write a command in the menu");
                    }
                }catch(IOException e){
                    System.out.println("There has been a problem, try again!");
                }
            } while (!goon);

        }

    }

    public void isYourTurnPt2() throws RemoteException {
        boolean goon = false;
        String roba;

        do {
            try {
                Menu2();
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                roba = reader.readLine();
                switch (roba) {
                    case "pgc" -> {
                        LinkedList<GoalCard> toPrint = client.getClient().getCommonGoals();
                        //for(GoalCard gc : toPrint)
                        //cards.printGoalCard(gc);
                        //plot goal cards da modellino (?)
                    }
                    case "hand" -> {
                        LinkedList<PlayableCard> toPrint = client.getClient().getHand();
                        for(PlayableCard pc : toPrint){
                            if(pc.getId() >= 1 && pc.getId() <= 40) {
                                cards.printFrontResourceCard((ResourceCard) pc);
                                cards.printBackResourceCard((ResourceCard) pc);
                            }
                            else {
                                cards.printFrontGoldCard((GoldCard) pc);
                                cards.printBackGoldCard((GoldCard) pc);
                            }
                        }
                    }
                    case "score" -> {
                        //GameController Grian = client.
                        //plot points
                    }
                    case "other" -> {
                        //choose player e poi plotti il suo field
                    }
                    case "draw" -> {
                        drawCard();
                        goon = true;
                    }
                    case "myField" -> {
                        //plot playing field
                    }
                    // case "freePosition" eventuale
                    case "toDraw" -> {
                        //plotti quelle pescabili
                    }
                    case "chat" -> {
                        //scrivi in chat
                    }
                    case "showChat" -> {
                        //show chat
                    }
                    case "q" -> {
                        //leave game
                    }
                    default -> System.out.println("Write a command in the menu");
                }
            }catch(IOException e){
                System.out.println("There has been a problem, try again!");
            }
        } while (!goon);
        endTurn("NormalTurn");
    }*/

    private void isYourTurn() throws RemoteException {
        placeCard();
    }


    @Override
    public void updateTurn(Player player, String mex) throws RemoteException {

        this.Turn = player;

        //robaccia per printare che non è il tuo turno e bla bla
        if(Turn.getName().equals(client.getName())) {
            switch (mex) {
                case "StartCard" -> setStartCardFace();
                case "GoalCard" -> chooseGoalCard();
                case "NormalTurn" -> isYourTurn();
            }
        }
        else{
            System.out.println("It's" + Turn.getName() + "'s turn");
        }

    }
    public void endTurn(String mex) throws RemoteException {
        client.endTurn(Turn.getName(), mex);
    }

    @Override
    public void declareWinner(HashMap<String, Integer> classifica) {
        int i = 0;
        String first;
        String second;
        String third;
        System.out.println("These are your points now...");
        showPoints();
        System.out.println("Let's now check the goals...");
        System.out.println("...");
        System.out.println("You are jackie down the line...");
        System.out.println("...");
        System.out.println("These are the final points!");
        classifica.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        for (String name: classifica.keySet()) {
            if(name.equals(client.getName())){
                switch (i) {
                    case 0 -> {
                        System.out.println("with" + classifica.get(name) + "you won");
                    }
                    case 1 -> {
                        System.out.println("with" + classifica.get(name) + "you got the second place");
                    }
                    case 2 -> {
                        System.out.println("with" + classifica.get(name) + "you got the third place");
                    }
                    case 3 -> {
                        System.out.println("with" + classifica.get(name) + "you got the first place");
                    }
                }

            }
            else{
                switch (i) {
                    case 0 -> System.out.println("at the first place, "+ name + "with" + classifica.get(name));
                    case 1 -> System.out.println("at the second place, "+ name + "with" + classifica.get(name));
                    case 2 -> System.out.println("at the third place, "+ name + "with" + classifica.get(name));
                    case 3 -> System.out.println("at the fourth place, "+ name + "with" + classifica.get(name));
                }
            }
            i++;
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
                System.out.println("What card do you want to place?");
                i = getIndex();
                System.out.println("in what position do you want to place it? Send the x first!");
                x = getIndex();
                System.out.println("And now the y!");
                y = getIndex();
                System.out.println("Do you want to place it Front or Back? Send a 1 for front and a 0 for back");
                fac = getIndex();
                if (fac == 1 || fac == 0) {
                    if (fac == 0)
                        f = FB.BACK;
                    client.placeCard(client, i, x, y, f);
                    goon = true;
                }
                else{
                    System.out.println("the face of the card should be 1 or 0");
                }
            } catch (IOException e){
                System.out.println("I/O exception in place card");
            } catch (NumberFormatException e){
                System.out.println("Please enter a number");
            }
        } while (!goon) ;

    }


    /*public void setStartCardFace(){
        boolean face = true;
        boolean goon = false;
        String fac;
        do{
            try{
                System.out.println("Do you want to place the StartCard Front or Back?");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                fac = reader.readLine();
                if(fac.equals("1") || fac.equals("0") ) {
                    if (fac.equals("0"))
                        face = false;
                    client.setStartCardFace(face, this.client);
                    goon = true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while(!goon);
    } */


    public void drawCard() throws RemoteException {
            boolean goon = false;
            int whichDeck, whichCard;
            do {
                try {

                    System.out.println("From which deck do you want to draw?\n1 --> ResourceDeck\n2 --> GoldDeck");
                    whichDeck = getIndex();
                    System.out.println("Which card do you want to pick?");
                    whichCard = getIndex();
                    client.drawCard(whichDeck, whichCard, client);
                    /*if(error.equals("WrongIndexException")) {
                        System.out.println("You chose indexes which are not between 1 and 3!");
                        System.out.println("Please try again! ");
                    }
                    else*/
                        goon = true;
                } catch (IOException e) {
                    System.out.println("There has been a i/o problem, try again!");
                } catch (NumberFormatException e){
                    System.out.println("Please enter a number!");
                }

            }while (!goon) ;

        }


    private void Menu1(){
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("|                          MENU:                            |");
        System.out.println("|                                                           |");
        System.out.println("|   /pgc: to show the goal cards                            |");
        System.out.println("|   /hand: to show your hand                                |");
        System.out.println("|   /score: to get yours and others score                   |");
        System.out.println("|   /others: to show other player field                     |");
        System.out.println("|   /place: to place a card                                 |");
        System.out.println("|   /myField: to show your field                            |");
        System.out.println("|   /toDraw: to see the cards that you can draw             |");
        System.out.println("|   /chat: to write a message in the chat                   |");
        System.out.println("|   /showChat: to show the chat                             |");
        System.out.println("|   /q: to leave the game                                   |");
        System.out.println("|                                                           |");
        System.out.println("+-----------------------------------------------------------+");
    }

    private void Menu2(){
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("|                          MENU:                            |");
        System.out.println("|                                                           |");
        System.out.println("|   /pgc: to show the goal cards                            |");
        System.out.println("|   /hand: to show your hand                                |");
        System.out.println("|   /score: to get yours and others score                   |");
        System.out.println("|   /others: to show other player field                     |");
        System.out.println("|   /draw: to draw   a card                                 |");
        System.out.println("|   /myField: to show your field                            |");
        System.out.println("|   /toDraw: to see the cards that you can draw             |");
        System.out.println("|   /chat: to write a message in the chat                   |");
        System.out.println("|   /showChat: to show the chat                             |");
        System.out.println("|   /q: to leave the game                                   |");
        System.out.println("|                                                           |");
        System.out.println("+-----------------------------------------------------------+");
    }

}