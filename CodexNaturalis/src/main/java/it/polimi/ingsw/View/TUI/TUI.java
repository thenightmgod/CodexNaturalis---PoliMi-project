package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
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

    Player Turn;

    CommonClient client;

    int ServerPort= 4444;

    String error;


    @Override
    public void updatePoints(int points, String name) {
    }

    @Override
    public void showGoals(LinkedList<GoalCard> goals, String name) {

    }

    @Override
    public void showHands(LinkedList<PlayableCard> hand, String name) {

    }

    @Override
    public void updateField(PlayingField field, String name) {

    }

    @Override
    public void showFreePosition(String name, LinkedList<Position> freePositions) {}

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

    public void createGame(){

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
            try{
                name = getNickname();
                client = chooseClient(name);
                client.createGame(name, num); // è tutto nullo perchè così è inizializzata la view

            } catch (RemoteException e) {
                System.out.println("an exception occurred while starting the client");
            } catch (NotBoundException e){
                System.out.print("NotBoundException occurred while initializing the client");
            } catch (WrongPlayersNumberException e){
                System.out.print("The number of players isn't supported");
            }

        }

        executor.shutdown();

    }

    public void joinGame() throws RemoteException {
        boolean goon = false;
        do {
            //try {
                String name = getNickname();
                this.client = chooseClient(name);
                this.client.joinGame(name);
                goon = true;
            /*} catch (NameAlreadyTakenException e){
                System.out.print("The name you chose is already taken, try again! UwU ;)");
            } catch (RoomFullException e){
                System.out.print("The room is already full");
                System.out.println("Create a new room!");
                goon = true;
            } catch (RoomNotExistsException e){
                System.out.print("There isn't a room available");
                System.out.println("Create a new room!");
                goon = true;
            }*/
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
                } catch (RemoteException e) {
                    System.out.println("an exception occurred while starting the client");
                } catch (NotBoundException e){
                    System.out.print("NotBoundException occurred while initializing the client");
                }
            }else {
                String nickname= getNickname();
                client = new SocketClient(nickname);
                client.setView(this);
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
                    goon = true;
                }
                System.out.println("the face of the card should be 1 or 0");
            } catch (IOException e) {
                throw new RuntimeException(e);
            /*} catch (WrongIndexException | RequirementsNotSatisfied | WrongPositionException e){
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e){
                System.out.println("Please enter a number!");
            }*/
            }
        }while (!goon) ;
    }


    public void setStartCardFace(){
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
    }


    public void chooseGoalCard() {
        int i = 0;
        do {
            try {
                System.out.println("Choose your personal GoalCard: 1 or 2?");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                i = Integer.parseInt(reader.readLine());
                if (i == 1 || i == 2) {
                    client.chooseGoalCard(i, this.client);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            /*} catch (WrongIndexException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e){
                System.out.println("Please enter a number!");
            }*/
            }
            while (i == 0) ;
        }
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
                    goon = true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
            /*} catch (WrongIndexException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e){
                System.out.println("Please enter a number!");
            }*/
                }
            }while (!goon) ;
        }


}