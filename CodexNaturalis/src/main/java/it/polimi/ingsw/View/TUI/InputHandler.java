package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Network.RMI.RMIClient;
import it.polimi.ingsw.Network.Socket.SocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class handles user input in a separate thread.
 */
public class InputHandler extends Thread{
    /**
     * The TUI instance associated with this InputHandler.
     */
    private final TUI tui;
    /**
     * Scanner to read user input.
     */
    private Scanner scanner;
    /**
     * The current task to be performed.
     */
    private String whatToDo;
    /**
     * The type of connection, true for Socket and false for RMI.
     */
    boolean connectionType;
    /**
     * Constructor for the InputHandler class.
     * @param tui The TUI instance associated with this InputHandler.
     */
    public InputHandler(TUI tui){
        this.scanner = new Scanner(System.in);
        this.whatToDo = "";
        this.tui = tui;
    }

    /**
     * The main loop that runs in the thread, handling user input.
     */
    @Override
    public void run(){
        while (true) {
            try {
                if (!whatToDo.isEmpty()) {
                    handleUserInput(whatToDo);
                    whatToDo = "";
                }
            } catch (RemoteException ignored) {}
        }
    }

    /**
     * Handles user input based on the given command.
     * @param whatToDo The command to be executed.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void handleUserInput(String whatToDo) throws RemoteException {
        switch(whatToDo){
            case "createGame" ->
                createGame();
            case "placeCard" ->
                placeCard();
            case "chooseGoalCard" ->
                chooseGoalCard();
            case "setStartCardFace" ->
                setStartCardFace();
            case "drawCard" ->
                drawCard();
            case "chooseClient"->
                chooseClient();
            case "askServerIp" ->
                askServerIp();
            case "setName" ->
                setName();
            case "anotherGame" ->
                anotherGame();
            case "chatMessage" ->
                chatMessage();
            case "updateColors" ->
                updateColors();
        }
    }

    /**
     * Asks the user if they want to play another game. If the user chooses to play again, the game is restarted.
     * If the user chooses not to play again, the application is closed.
     *
     * @throws RemoteException If a remote communication problem occurs.
     */
    private void anotherGame() throws RemoteException {
        String carlos;
        do {
            System.out.println("Do you want to play another game?\n1 --> yes\n2 --> no");
            carlos = scanner.next();
        } while (!carlos.equals("1") && !carlos.equals("2"));
        if (carlos.equals("1"))
            this.tui.joinGame();
        else System.exit(0);
    }

    /**
     * Prompts the user to enter their nickname and sets it. If the connection type is not RMI, it prepares for a socket connection.
     *
     * @throws RemoteException If a remote communication problem occurs.
     */
    private void setName() throws RemoteException {
        getNickname();
        if (!(connectionType)) {
            ((RMIClient) this.tui.client ).setName(this.tui.name);
        }
        else{
            ((SocketClient) this.tui.client).setName(this.tui.name);
        }
        this.tui.joinGame();
    }

    /**
     * Prompts the user to create a new game by entering the number of players. The game is created with the entered number of players.
     */
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

    /**
     * Prompts the user to enter a number and returns the entered number.
     *
     * @return The number entered by the user.
     */
    private int getIndex() {
        int input = 77;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                break;  // if input was a valid integer, break the loop
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
        return input;
    }

    /**
     * Prompts the user to place a card on the game board. The user is asked to choose a card, its position, and its orientation (front or back).
     *
     * @throws RemoteException If a remote communication problem occurs.
     */
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

    /**
     * Prompts the user to choose a goal card. The user can choose the card on the top or the card on the bottom.
     *
     * @throws RemoteException If a remote communication problem occurs.
     */
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

    /**
     * Prompts the user to set the orientation (front or back) of their start card.
     *
     * @throws RemoteException If a remote communication problem occurs.
     */
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

    /**
     * Prompts the user to draw a card from a specified deck.
     */
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

    /**
     * Prompts the user to choose the type of client connection (RMI or Socket) and sets up the chosen connection.
     */
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
                this.tui.client = new SocketClient(this.tui.name);
                this.tui.client.setView(this.tui);
                connectionType = true;
            }

        } while(!connection.equals("0") && !connection.equals("1"));

    }

    /**
     * Prompts the user to enter their nickname and sets it.
     */
    private void getNickname(){

        String nickname = "Carlos";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Write your nickname:");
                nickname = reader.readLine();

                if (nickname != null && !nickname.trim().isEmpty()) {
                    break;
                } else {
                    System.out.println("Nickname cannot be empty. Please enter a valid nickname.");
                }
            }
        } catch (IOException e) {
            System.out.println("There has been an error with the nickname");
        }

        this.tui.name = nickname;
    }

    /**
     * Prompts the user to enter the server IP address.
     */
    private void askServerIp(){
        do {
            System.out.println("Insert the server ip");
            this.tui.serverIp = scanner.nextLine();
        } while (!this.tui.serverIp.isEmpty() && !isValidFormat(this.tui.serverIp));
    }

    /**
     * Checks if the given string is a valid IP address format.
     *
     * @param input The string to check.
     * @return true if the string is a valid IP address format, false otherwise.
     */
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

    /**
     * Checks if the given string can be parsed to an integer.
     *
     * @param str The string to check.
     * @return true if the string can be parsed to an integer, false otherwise.
     */
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Handles the chat functionality of the game. The user can choose to see the chat, send a message, or go back to the game.
     * If the user chooses to send a message, they can select the recipient of the message and write the message content.
     *
     * @throws RemoteException If a remote communication problem occurs.
     */
    private void chatMessage() throws RemoteException {
        int choice= 0;
        while(choice != 3) {
            System.out.println("(1) ->  see the chat\n(2) -> send a message\n(3) -> go back to the game");
            choice = getIndex();
            switch (choice) {
                case 1 -> this.tui.chat.printChat(this.tui.name);
                case 2 -> {
                    LinkedList<String> names = this.tui.client.getClient().getPlayers();
                    LinkedList<String> otherPlayers = new LinkedList<>();
                    for (String name : names) {
                        if (!name.equals(this.tui.name)) {
                            otherPlayers.add(name);
                        }
                    }
                    System.out.println("Select to whom you want to send the message:");
                    System.out.println("0 -> everyone");
                    for (int i = 0; i < otherPlayers.size(); i++) {
                        System.out.println((i + 1) + " -> " + otherPlayers.get(i));
                    }
                    int selectedPlayerIndex = getIndex() - 1;
                    if (selectedPlayerIndex == -1) {
                        System.out.println("Write your message:");
                        String message = scanner.nextLine();
                        ChatMessage mex = new ChatMessage(message, this.tui.name, "everyone");
                        this.tui.sendChatMessage(mex);
                        return;
                    }
                    String selectedPlayer = otherPlayers.get(selectedPlayerIndex);
                    System.out.println("Write your message:");
                    String message = scanner.nextLine();
                    ChatMessage mex = new ChatMessage(message, this.tui.name, selectedPlayer);
                    this.tui.sendChatMessage(mex);
                }
                case 3 -> {
                    return;
                }
            }
        }
    }

    public void updateColors(){
        LinkedList<PlayerColor> colors;
        colors = this.tui.client.getClient().getColors();
        int selectedColorIndex = -1;
        while (selectedColorIndex < 0 || selectedColorIndex > colors.size()) {
            System.out.println("Select a color:");

            for (int i = 0; i < colors.size(); i++) {
                String colorCode = getColorCode(colors.get(i));
                System.out.println(colorCode + (i + 1) + " -> " + colors.get(i) + "\u001B[0m");            }
            try {
                selectedColorIndex = Integer.parseInt(scanner.nextLine()) - 1;
                this.tui.color = colors.get(selectedColorIndex);
                colors.remove(selectedColorIndex);
                this.tui.client.getClient().setColors(colors);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private String getColorCode(PlayerColor color) {
        switch (color) {
            case RED:
                return "\u001B[31m";
            case GREEN:
                return "\u001B[32m";
            case YELLOW:
                return "\u001B[33m";
            case BLUE:
                return "\u001B[34m";
            default:
                return "\u001B[0m";
        }
    }
}