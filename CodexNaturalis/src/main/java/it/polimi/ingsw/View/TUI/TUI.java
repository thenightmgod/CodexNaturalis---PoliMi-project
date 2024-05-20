package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Exceptions.NameAlreadyTakenException;
import it.polimi.ingsw.Exceptions.RoomFullException;
import it.polimi.ingsw.Exceptions.RoomNotExistsException;
import it.polimi.ingsw.Exceptions.WrongPlayersNumberException;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
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
    public void showException(String name, String exception) {}

    public void createGame(){

        String input;
        String name;
        int num;
        boolean goon = false;
        Scanner scan = new Scanner(System.in);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        do {

            System.out.println("Enter the number of players: ");
            input = waitForInput(scan, executor);

            if(!input.equals("")){
                num = Integer.parseInt(input);
                if(num<2 || num>4)
                    System.out.println("The number of players must be between 2 and 4!");
            } else{
                num = 0;
                goon = true;
                break;
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

    public void joinGame() throws RoomFullException, RoomNotExistsException, NotBoundException, NameAlreadyTakenException, RemoteException {
        boolean goon = false;
        do {
            try {
                String name = getNickname();
                this.client = chooseClient(name);
                this.client.joinGame(name);
                goon = true;
            } catch (NameAlreadyTakenException e){
                System.out.print("The name you chose is already taken, try again! UwU ;)");
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

            //fare socketclient inizializzazione quando c'è, startarlo dopo e gestire eccezioni

            /*if(connection.equals("1")){
                try{
                    String nickname = getNickname();
                    // client = new SocketClient()
                    client.setView(this);
                } catch{(NotBoundException | RemoteException e) {
                    System.out.println("an exception occured while starting the client");
                }
                }
            }*/

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

}