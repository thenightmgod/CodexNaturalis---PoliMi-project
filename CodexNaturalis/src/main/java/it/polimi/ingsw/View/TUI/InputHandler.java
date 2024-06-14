package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.Network.RMI.RMIClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class InputHandler extends Thread{

    private final TUI tui;
    private Scanner scanner;
    private String whatToDo;
    boolean connectionType;

    public InputHandler(TUI tui){
        this.scanner = new Scanner(System.in);
        this.whatToDo = "";
        this.tui = tui;
    }

    @Override
    public void run(){
        while (true) {
            try {
                handleUserInput(whatToDo);
            } catch (RemoteException ignored) {}
        }
    }

    public void handleUserInput(String whatToDo) throws RemoteException {
        switch(whatToDo){
            case "createGame":
                createGame();
                break;
            case "placeCard":
                placeCard();
                break;
            case "chooseGoalCard":
                chooseGoalCard();
                break;
            case "setStartCardFace":
                setStartCardFace();
                break;
            case "drawCard":
                drawCard();
                break;
            case "chooseClient":
                chooseClient();
                break;
            case "askServerIp":
                askServerIp();
                break;
            default:
                break;
        }
    }

    private void createGame(){
        {
            System.out.println("CREATING A NEW GAME...");
            int input = 0;
            boolean goon = false;

            do {
                try {
                    System.out.println("Enter the number of players");
                    input = Integer.parseInt(scanner.nextLine());

                    if (input < 2 || input > 4)
                        System.out.println("The number of players must be between 2 and 4!");
                    else {
                        goon = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number!");
                }
            } while(!goon);

            try {
                this.tui.client.createGame(this.tui.name, input);
            } catch ( RemoteException e) {
                System.out.println("There was a problem creating the game.");
            }
        }
    }

    private int getIndex() {
        int input = 77;

        while (true) {
            try {
                input = scanner.nextInt();
                break;  // if input was a valid integer, break the loop
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                scanner.next();  // discard the invalid input
            }
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
                    this.tui.client.placeCard(this.tui.client, i, x, y, f);
                    goon = true;
                } else {
                    System.out.println("You must choose between 1(front) or 0(back)");
                }
            } catch (IOException e) {
                System.out.println("I/O exception in place card");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        } while (!goon);
    }

    private void chooseGoalCard() throws RemoteException {
        boolean goon = false;
        int i;
        do {
            try {
                System.out.println("write '1' for the card on the top or '2' for the card on the bottom?");
                i = scanner.nextInt();
                if (i == 1 || i == 2) {
                    this.tui.client.chooseGoalCard(i, this.tui.client);
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
        this.tui.endTurn("GoalCard");
    }

    private void setStartCardFace() throws RemoteException {
        String f;
        boolean face;
        boolean goon = false;
        do {
            try {
                System.out.println("Do you want to place your Start Card Front or Back?\n1 --> Front\n0 --> Back");
                f = scanner.nextLine();
                if(f.equals("1") || f.equals("0")){
                    if(f.equals("1"))
                        face = true;
                    else face = false;
                    this.tui.client.setStartCardFace(face, this.tui.client);
                    goon = true;
                } else {
                    System.out.println("You must choose between 1(front) or 0(back)");
                }
            } catch(IOException e){
                System.out.println("There was an error while reading the front or back");
            }
        } while(!goon);

        this.tui.endTurn("StartCard");

    }

    private void drawCard(){
        boolean goon = false;
        int whichDeck, whichCard;
        do {
            try {
                System.out.println("From which deck do you want to draw?\n1 --> ResourceDeck\n2 --> GoldDeck");
                whichDeck = getIndex();
                System.out.println("Which card do you want to pick?");
                whichCard = getIndex();
                this.tui.client.drawCard(whichDeck, whichCard, this.tui.client);
                goon = true;
            } catch (IOException e) {
                System.out.println("There has been a i/o problem, try again!");
            } catch (NumberFormatException e){
                System.out.println("Please enter a number!");
            }

        }while (!goon) ;

    }

    private void chooseClient(){

        getNickname();
        String connection;

        System.out.println("Choose the kind of connection you want to play with");

        do{
            System.out.println("0 --> RMI\n1 --> Socket");
            connection = scanner.nextLine();

            if(connection.equals("0")){
                try{
                    this.tui.client = new RMIClient(this.tui.name, this.tui.serverIp);
                    this.tui.client.setView(this.tui);
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

    }

    private void getNickname(){

        String nickname = "Carlos";

        try {
            System.out.println("Write your nickname");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            nickname = reader.readLine();

        } catch (IOException e) {
            System.out.println("There has been an error with the nickname");
        }

        this.tui.name = nickname;
    }

    private void askServerIp(){
        do {
            System.out.println("Insert the server ip");
            this.tui.serverIp = scanner.nextLine();
        } while (!this.tui.serverIp.isEmpty() && !isValidFormat(this.tui.serverIp));
    }

    public static boolean isValidFormat(String input) {

        String[] parts = input.split("\\.");

        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            if (!isNumeric(part)) {
                return false;
            }
            int num = Integer.parseInt(part);
            if (num < 0 || num > 255) {
                return false;
            }
        }

        return true;
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
