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
import it.polimi.ingsw.Network.Socket.SocketClient;
import it.polimi.ingsw.View.GameView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.*;

public class TUI implements GameView {

    boolean connectionType;

    CardsTUI cards;

    Player Turn;

    CommonClient client;

    int ServerPort= 4444;

    String error = "default";

    public void showPoints(){

    }

    @Override
    public void updatePoints(int points, String name){
        if(name.equals(Turn.getName())) {
            client.getClient().setPointsCounter(points);
            System.out.println("Your new points are" + points);
        }
        else{
            System.out.println(name + "new points are" + points);
        }
    }

    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) {
        client.getClient().setCommonGoals(goals);
    }

    @Override
    public void updateHands(LinkedList<PlayableCard> hand, String name) {
        client.getClient().setHand(hand);
    }

    @Override
    public void updateField(PlayingField field, String name) {
        if(name.equals(Turn.getName())) {
            client.getClient().setField(field);
            System.out.println("Your new field is:\n");
            //plotField();
        }
        else{
            System.out.println(name + "has placed a card");
        }
    }

    @Override
    public void updateFreePosition(String name, LinkedList<Position> freePositions) {
        client.getClient().setFreePositions(freePositions);
        System.out.println("Your new free positions are:");
        //plotFreePositions();
    }

    @Override
    public void updateGoldDeck(LinkedList<GoldCard> goldDeck, String name) {
        if(name.equals(Turn.getName())){
            client.getClient().setDrawableGoldCards(goldDeck);
            System.out.println("These are the new drawable cards");
            //plot.GoldDeck
        }
        else{
            System.out.println(name + "has drawn a card");
            System.out.println("These are the new drawable cards");
            //plot.GoldDeck
        }
    }

    @Override
    public void updateResourceDeck(LinkedList<ResourceCard> resourceCards, String name){
        if(name.equals(Turn.getName())){
            client.getClient().setDrawableResourceCards(resourceCards);
            System.out.println("These are the new drawable cards");
            //plot.ResDeck
        }
        else{
            System.out.println(name + "has drawn a card");
            System.out.println("These are the new drawable cards");
            //plot.ResDeck
        }
    }

    @Override
    public void showStartCard(StartCard card) {
        cards.printFrontStartCard(card);
        cards.printBackStartCard(card);
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
                }
                System.out.println("You need to write front or back");
            } catch(IOException e){
                System.out.println("There was an error while reading the front or back");
            }
        } while(!goon);
    }

    @Override
    public void showException(String name, String details) {
        switch(details){
            case "WrongIndexException" ->
                    error = "WrongIndexException";
            case "WrongPositionException" ->
                    error = "WrongPositionException";
            case "WrongPlayersNumberException" ->
                    error = "WrongPlayersNumberException";
            case "RoomNotExistsException" ->
                    error = "RoomNotExistsException";
            case "RoomFullException" ->
                    error = "RoomFullException";
            case "RequirementsNotSatisfied" ->
                    error = "RequirementsNotSatisfied";
            case "NameAlreadyTakenException" ->
                    error = "NameAlreadyTakenException";
            case "Exception" ->
                    error = "Sbatti";
        }
    }


    public void createGame() throws RemoteException {

        String input;
        String name;
        int num = 0;
        boolean goon = false;
        Scanner scan = new Scanner(System.in);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        do {
            try {
                System.out.println("Enter the number of players: ");
                input = waitForInput(scan, executor);

                if (!input.isEmpty()) {
                    num = Integer.parseInt(input);
                    if (num < 2 || num > 4)
                        System.out.println("The number of players must be between 2 and 4!");
                } else {
                    num = 0;
                    goon = true;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        } while(num < 2 || num > 4);

        if(!goon){
            //fare in modo che richiami in qualche modo
        } else{
            //try{
            name = getNickname();
            client = chooseClient(name);
            client.createGame(name, num); // è tutto nullo perchè così è inizializzata la view

            /*} catch (RemoteException e) {
                System.out.println("an exception occurred while starting the client");
            } catch (NotBoundException e){
                System.out.print("NotBoundException occurred while initializing the client");
            } catch (WrongPlayersNumberException e){
                System.out.print("The number of players isn't supported");
            }*/

        }

        executor.shutdown();

    }

    public void joinGame() throws RemoteException {
        boolean goon = false;
        do {
            try {
                String name = getNickname();
                this.client = chooseClient(name);
                this.client.joinGame(name);
                switch (error) {
                    case "NameAlreadyTakenException" -> {
                        System.out.println("Name already taken! Please try again!");
                        error = "default";
                    }
                    case "RoomFullException" -> {
                        System.out.println("Room full! Please try again!");
                        error = "default";
                    }
                    case "RoomNotExistsException" -> {
                        System.out.println("The room doesn't exist! Please create a game from scratch!");
                        error = "default";
                    }
                    default -> goon = true;
                }
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
                    String nickname = getNickname();
                    client = new RMIClient(nickname);
                    client.setView(this);
                    connectionType = false;
                } catch (RemoteException e) {
                    System.out.println("an exception occurred while starting the client");
                } catch (NotBoundException e){
                    System.out.print("NotBoundException occurred while initializing the client");
                }
            }else {
                String nickname = getNickname();
                client = new SocketClient(nickname);
                client.setView(this);
                connectionType = true;
            }

        } while(!connection.equals("0") && !connection.equals("1"));

        return client;
    }


    public String getNickname(){
        String nickname;
        boolean goon = false;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            nickname = reader.readLine();
            goon = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return nickname;
    }

    private static String waitForInput(Scanner scanner, ExecutorService executor) {
        try {
            // Avvia un'attività per leggere l'input dell'utente
            Future<String> future = executor.submit(scanner::nextLine);

            // Imposta il timeout
            return future.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // Se si verifica un'eccezione, restituisce una stringa vuota
            return "";
        }
    }

    public void isYourTurn(boolean LL) throws RemoteException {
        if(Turn.getName().equals(client.getName())) {
            boolean goon = false;
            String roba;
            do {
                try {
                    Menu1();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    roba = reader.readLine();
                    switch (roba) {
                        case "help" -> {

                        }
                        case "pgc" -> {
                            LinkedList<GoalCard> toPrint = client.getClient().getCommonGoals();
                            //for(GoalCard gc : toPrint)
                                //cards.printGoalCard(gc);
                            //plot goal cards da modellino (?)
                        }
                        case "hand" -> {
                            LinkedList<PlayableCard> toPrint = client.getClient().getHand();
                            for(PlayableCard pc : toPrint){
                                if(pc.getId() >= 1 && pc.getId() <= 40)
                                    cards.printFrontResourceCard((ResourceCard) pc);
                                else cards.printFrontGoldCard((GoldCard) pc);
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
                    System.out.println("sbatti");
                }
            } while (!goon);

            // do while parte 2 per pescare

            endTurn();
        }
        else{
            System.out.println("You are not your turn");
            //magari altre funzioni
        }
        if(LL)
            declareWinner();
    }

    @Override
    public void updateTurn(Player player, boolean LL) throws RemoteException {
        this.Turn = player;
        isYourTurn(LL);
    }

    public void endTurn() throws RemoteException {
        client.endTurn(client.getName());
    }

    public void declareWinner() throws RemoteException {
        System.out.println("These are your points now...");
        showPoints();
        System.out.println("Let's now check the goals...");
        System.out.println("...");
        System.out.println("You are jackie down the line...");
        System.out.println("...");
        checkGoals();
        System.out.println("These are the final points!");

        // bisogna staccare tutto poi
        // come si fa boh
    }

    public void checkGoals() throws RemoteException {
        client.checkGoals(client.getName());
    }

    //            FUNZIONI PER GIOCARE

    private void placeCard() {
        boolean goon = false;
        int i, x, y;
        boolean face = false;
        FB f = FB.FRONT;
        String fac;
        do {
            try {
                System.out.println("What card do you want to place?");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                i = Integer.parseInt(reader.readLine());
                System.out.println("in what position do you want to place it? Send the x first!");
                x = Integer.parseInt(reader.readLine());
                System.out.println("And now the y!");
                y = Integer.parseInt(reader.readLine());
                System.out.println("Do you want to place it Front or Back? Send a 1 for front and a 0 for back");
                fac = reader.readLine();
                if (fac.equals("1") || fac.equals("0")) {
                    if (fac.equals("0"))
                        f = FB.BACK;
                    client.placeCard(client, i, x, y, f);
                    switch (error) {
                        case "WrongIndexException" -> {
                            System.out.println("You chose a card which wasn't in your hand, put an index between 1 and 3!");
                            error = "default";
                        }
                        case "RequirementsNotSatisfied" -> {
                            System.out.println("The requirements for the card you chose aren't satisfied! ");
                            System.out.println("Flip this gold card or play another one!");
                            error = "default";
                        }
                        case "WrongPositionException" -> {
                            System.out.println("The position you chose isn't in the free ones!");
                            System.out.println("Look at the free position and choose one in those");
                            error = "default";
                        }
                        default -> goon = true;
                    }
                    System.out.println("the face of the card should be 1 or 0");
                }
            } catch (IOException e){
                throw new RuntimeException(e);
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


    public void chooseGoalCard() {
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
                }
                System.out.println("You put a number which isn't ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e){
                System.out.println("Please enter a number!");
            }
        }while (!goon) ;
    }


    public void drawCard() {
            boolean goon = false;
            int whichDeck, whichCard;
            do {
                try {
                    System.out.println("From which deck do you want to draw?\n1 --> ResourceDeck\n2 --> GoldDeck");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    whichDeck = Integer.parseInt(reader.readLine());
                    System.out.println("Which card do you want to pick?");
                    whichCard = Integer.parseInt(reader.readLine());
                    client.drawCard(whichDeck, whichCard, client);
                    if(error.equals("WrongIndexException")) {
                        System.out.println("You chose indexes which are not between 1 and 3!");
                        System.out.println("Please try again! ");
                    }
                    else
                        goon = true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NumberFormatException e){
                    System.out.println("Please enter a number!");
                }

            }while (!goon) ;
        }


    private void Menu1(){
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("|                          MENU:                            |");
        System.out.println("|                                                           |");
        System.out.println("|   /help: to show all the commands available               |");
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
        System.out.println("|   /help: to show all the commands available               |");
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