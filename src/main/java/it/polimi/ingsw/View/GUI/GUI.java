package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.StartCard;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;
import it.polimi.ingsw.Network.CommonClient;
import it.polimi.ingsw.View.GUI.GUIController.*;
import it.polimi.ingsw.View.GameView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * GUI class is the main class of the GUI view. It implements the GameView interface and it is used to manage the GUI
 * of the game.
 */
public class GUI implements GameView {
    /**
     * username is the name of the player.
     */
    private String username;
    /**
     * primaryStage is the main stage of the GUI.
     */
    private Stage primaryStage;
    /**
     * guicontrollers is a map that contains all the controllers of the GUI.
     */
    private Map<String, GUIController> guicontrollers = new HashMap<>();
    /**
     * Turn is the player that has the turn.
     */
    private Player Turn;
    /**
     * client is the client that is used to communicate with the server.
     */
    private CommonClient client;
    /**
     * args is the arguments of the main method.
     */
    private String[] args;
    /**
     * first_turn is a boolean that is true if it is the first turn.
     */
    public boolean first_turn = true;

    /**
     * color is the color of the player.
     */
    public PlayerColor color;
    /**
     * chatController is the controller of the chat.
     */
    private ChatController chatController;

    /**
     * Sets the first_turn to the specified value.
     *
     * @param first_turn The new value for the first_turn field.
     */
    public void setFirst_turn(boolean first_turn) {
        this.first_turn = first_turn;
    }

    //-------METODI DEI CONTROLLER---------------------------

    /**
     * Initializes the GUI and switches to the login scene.
     *
     * @param args The command line arguments.
     * @param stage The primary stage for this application.
     * @throws IOException If an I/O error occurs.
     */
    public void start(String[] args, Stage stage) throws IOException {
        this.client = null;
        this.args = args;
        this.primaryStage = stage;
        this.primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        this.Turn = null;
        switchToScene("login");
    }

    public String[] getArgs() {
        return args;
    }

    /**
     * Switches to the specified scene. If there is no scene yet, it is loaded and added to the guicontrollers map.
     * The title of the primary stage is updated based on the scene name.
     *
     * @param sceneName The name of the scene to switch to.
     * @param args The arguments to pass to the scene's controller.
     * @throws IOException If an I/O error occurs.
     */
    public void switchToScene(String sceneName, Object... args) throws IOException {
        GUIController controller = guicontrollers.get(sceneName);
        if (controller == null) {
            controller = loadController(sceneName, args);
            guicontrollers.put(sceneName, controller);
        }
        primaryStage.setScene(controller.getScene());

        switch(sceneName) {
            case "declareWinner" -> {
                primaryStage.setTitle("FINAL RESULTS");
            }
            case "login" -> {
                primaryStage.setTitle("Login");
            }
            case "turn" -> {
                primaryStage.setTitle(client.getNames());
            }
            default -> {
                primaryStage.setTitle(client.getNames() + "'s " + sceneName);
            }
        }
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Loads the specified scene's controller. This method first retrieves the FXML file for the scene,
     * then uses the FXMLLoader to load the scene and get its controller. The controller is then initialized
     * with the necessary data (GUI, stage, client, root, and args).
     *
     * @param sceneName The name of the scene to load the controller for.
     * @param args The arguments to pass to the scene's controller.
     * @return The loaded and initialized controller for the specified scene.
     * @throws IOException If an I/O error occurs while loading the FXML file.
     */
    private GUIController loadController(String sceneName, Object... args) throws IOException {
        URL fxmlUrl = getClass().getResource("/view/" + sceneName + ".fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        GUIController controller = loader.getController();
        controller.setGui(this);
        controller.setStage(primaryStage);
        controller.setClient(client);
        controller.setRoot(root);
        controller.setArgs(args);
        return controller;
    }

    /**
     * Sets the client.
     *
     * @param client The client to set.
     */
    public void setClient(CommonClient client) {
        this.client = client;
    }

    /**
     * Sets the username.
     */
    public void setName(String username) {
        this.username = username;
    }


    //----------METODI DELLA GAMEVIEW(interfaccia comune view)-------------------------------

    /**
     * Updates the points of the client and the GUI. This method first updates the points of the client,
     * then schedules the GUI to be updated on the JavaFX Application Thread.
     *
     * @param points The new points of the client.
     * @param name The name of the client.
     */
    @Override
    public void updatePoints(HashMap<String, Integer> points, String name) {
        client.getClient().setPointsCounter(points);
        Platform.runLater(()-> {
            ((TurnController)guicontrollers.get("turn")).updatePoints(points);
        });
    }

    /**
     * Updates the goals of the client and switches to the goalCard scene. This method is scheduled to be run on the JavaFX Application Thread.
     *
     * @param goals The new goal cards of the client.
     * @param name The name of the client.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void updateGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        Platform.runLater(() -> {
            try {
                switchToScene("goalCard", goals);
            } catch (IOException e) {
                System.out.println("Goal Card non correttamente inizializzata");
            }
        });
    }

    /**
     * Sets the common goals of the client.
     *
     * @param goals The common goal cards of the game.
     * @param name The name of the client.
     */
    @Override
    public void updateCommonGoals(LinkedList<GoalCard> goals, String name) throws RemoteException {
        client.getClient().setCommonGoals(goals);
    }


    /**
     * Updates the hands of the client and the GUI. This method first updates the hands of the client,
     * then schedules the GUI to be updated on the JavaFX Application Thread.
     *
     * @param hand The new hand of the client.
     * @param name The name of the client.
     */
    @Override
    public void updateHands(LinkedList<PlayableCard> hand, String name) {
        client.getClient().setHand(hand);
        if(!first_turn) {
            Platform.runLater(() -> {
                ((TurnController)guicontrollers.get("turn")).updateHands(hand);
            });
        }
    }

    /**
     * Updates the field of the client and the GUI. This method first updates the field of the client,
     * then schedules the GUI to be updated on the JavaFX Application Thread.
     *
     * @param field The new field of the client.
     * @param name The name of the client.
     */
    @Override
    public void updateField(PlayingField field, String name) {
        if (name.equals(client.getNames())) {
            client.getClient().setField(field);
            if (!first_turn) {
                Platform.runLater(() -> {
                    ((TurnController) guicontrollers.get("turn")).plotField();
                });
            }
        }
        else {
            client.getClient().getOtherFields().put(name, field);
        }
    }

    @Override
    public void updateFreePosition(String name, LinkedList<Position> freePositions) {
    }


    /**
     * Shows the exception message on the GUI. This method is scheduled to be run on the JavaFX Application Thread.
     *
     * @param name The name of the exception.
     * @param exception The exception message.
     * @throws RemoteException If a remote access error occurs.
     * @throws NotBoundException If the name is not bound.
     */
    @Override
    public void showException(String name, String exception) throws RemoteException, NotBoundException {
        switch (name) {
            case "NameAlreadyTakenException" -> {
                Platform.runLater(() -> {
                    guicontrollers.get("login").showException("NameAlreadyTakenException");
                });
            }
            case "RoomNotExistsException" -> {
                Platform.runLater(() -> {
                    guicontrollers.get("login").showException("RoomNotExistsException");
                });
            }
            case "RoomFullException" -> {
                Platform.runLater(() -> {
                    guicontrollers.get("login").showException("RoomFullException");
                });
            }
            case "RequirementsNotSatisfied" -> {
                Platform.runLater( () -> {
                    guicontrollers.get("turn").showException("RequirementsNotSatisfied");
                });
            }
            case "Nothing" -> {
                Platform.runLater(() -> {
                    try {
                        ((TurnController) guicontrollers.get("turn")).drawCard();
                    } catch (RemoteException e) {
                        System.out.println();
                    }
                });
            }
        }
    }

    /**
     * Shows the start card scene. This method is scheduled to be run on the JavaFX Application Thread.
     *
     * @param card The start card to show.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void showStartCard(StartCard card) throws RemoteException {
        Platform.runLater(() -> {
            try {
                switchToScene("startCard", card);
            } catch (FileNotFoundException e) {
                System.out.println("Show start card scene non correttamente inizializzata");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Updates the turn of the client and the GUI. This method first updates the turn of the client,
     * then schedules the GUI to be updated on the JavaFX Application Thread.
     *
     * @param player The new player that has the turn.
     * @param mex The message to show.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void updateTurn(Player player, String mex) throws RemoteException {
        this.Turn = player;
        if (Turn.getName().equals(client.getNames())) {
            Platform.runLater(() -> {
                    switch (mex) {
                        case "StartCard" -> {
                                ((StartCardController) guicontrollers.get("startCard")).chooseStartCard();
                        }
                        case "GoalCard" -> {
                                ((GoalCardController) guicontrollers.get("goalCard")).chooseGoalCard();
                        }
                        case "NormalTurn"-> {
                            try {
                                if(first_turn) {
                                    switchToScene("turn", client.getClient().getDrawableResourceCards(), client.getClient().getDrawableGoldCards() , client.getClient().getCommonGoals(), client.getClient().getHand(), client.getClient().getField(), true);
                                }
                                else ((TurnController) guicontrollers.get("turn")).isYourTurn();
                                first_turn=false;
                            } catch (IOException e) {
                                System.out.println("turnScene non correttamente inizializzata");
                            }
                        }
                    }
                });
        }
    }

    /**
     * Ends the turn of the client with the specified message.
     *
     * @param mex The message specified for the end of the turn.
     * @throws RemoteException If a remote access error occurs.
     */
    public void endTurn(String mex) throws RemoteException {
        client.endTurn(Turn.getName(), mex);
    }

    /**
     * Updates the gold deck of the client and the GUI. This method first updates the gold deck of the client,
     * then schedules the GUI to be updated on the JavaFX Application Thread.
     *
     * @param deck The new gold deck of the client.
     * @param start The boolean that is true if it is the start.
     * @param name The name of the client.
     */
    @Override
    public void updateGoldDeck(LinkedList<GoldCard> deck, boolean start, String name) {
        client.getClient().setDrawableGoldCards(deck);
        if(!first_turn) {
            Platform.runLater(() -> {
                ((TurnController) guicontrollers.get("turn")).updateGoldDeck(deck);
            });
        }
    }

    /**
     * Updates the resource deck of the client and the GUI. This method first updates the resource deck of the client,
     * then schedules the GUI to be updated on the JavaFX Application Thread.
     *
     * @param deck The new resource deck of the client.
     * @param start The boolean that is true if it is the start.
     * @param name The name of the client.
     */
    @Override
    public void updateResourceDeck(LinkedList<ResourceCard> deck, boolean start, String name) {
        client.getClient().setDrawableResourceCards(deck);
        if(!first_turn) {
            Platform.runLater(() -> {
                ((TurnController) guicontrollers.get("turn")).updateResourceDeck(deck);
            });
        }
    }

    @Override
    public void startingGame() throws RemoteException {
    }

    /**
     * Declares the winner of the game and switches to the "declareWinner" scene. This method is scheduled to be run on the JavaFX Application Thread.
     *
     * @param standings The final standings of the game.
     */
    @Override
    public void declareWinner(LinkedList<String> standings) {
        Platform.runLater( () -> {
            try {
                switchToScene("declareWinner", standings);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Updates the GUI to show that a player has reached twenty points. This method is scheduled to be run on the JavaFX Application Thread.
     *
     * @param name The name of the player who reached twenty points.
     */
    @Override
    public void twenty(String name) {
        Platform.runLater(() -> {
            ((TurnController) guicontrollers.get("turn")).twenty(name);
        });
    }

    /**
     * Updates the GUI to indicate the last round of the game. This method is scheduled to be run on the JavaFX Application Thread.
     */
    @Override
    public void lastRound() {
        Platform.runLater(() -> {
            ((TurnController) guicontrollers.get("turn")).lastRound();
        });
    }

    /**
     * Updates the GUI to indicate that it's not the client's turn. This method is scheduled to be run on the JavaFX Application Thread.
     * Depending on the provided message, it updates different parts of the GUI.
     *
     * @param turn The player who currently has the turn.
     * @param mex The message indicating the current state of the game.
     */
    @Override
    public void printNotYourTurn(Player turn, String mex) {
        this.Turn = turn;
        Platform.runLater(() -> {
            switch(mex) {
                case "StartCard"-> {
                    ((StartCardController) guicontrollers.get("startCard")).waitYourTurn();
                }
                case "GoalCard"-> {
                    ((GoalCardController) guicontrollers.get("goalCard")).waitYourTurn();
                }
                case "Choose your color" ->{
                    if(guicontrollers.get("color") != null)
                        ((ColorController) guicontrollers.get("color")).waitYourTurn();
                    else {
                        try {
                            switchToScene("color", client.getClient().getColors(), false);
                        } catch (IOException e) {
                            System.out.println("errore colori");
                        }
                    }
                }
                case "NormalTurn"->{
                    try {
                        if(first_turn) {
                            switchToScene("turn", client.getClient().getDrawableResourceCards(), client.getClient().getDrawableGoldCards(), client.getClient().getCommonGoals(), client.getClient().getHand(), client.getClient().getField(), false);
                        }
                        else ((TurnController) guicontrollers.get("turn")).waitMyTurn();
                        first_turn = false;
                } catch (IOException e) {
                    System.out.println("turnScene non correttamente inizializzata");
                }
                }
            }
        });
    }

    /**
     * Shows that a player has left the game.
     */
    @Override
    public void leaveGameMessage() {
        System.out.println("Someone left the game");
        System.exit(0);
    }

    /**
     * Updates the chat of the client and the GUI. This method first updates the chat of the client,
     * then schedules the GUI to be updated on the JavaFX Application Thread.
     *
     * @param name The name of the client.
     * @param chat The new chat.
     */
    @Override
    public void updateChat(String name, LinkedList<ChatMessage> chat){
        if(name.equals(this.username) || (name.equals("everyone"))){
            this.client.getClient().setChat(chat);
            Platform.runLater(() -> {
                if (chatController != null) {
                    chatController.refreshChat();
                }
            });
        }
    }

    /**
     * Updates the colors that can be chosen and the GUI. This method first updates the colors,
     * then schedules the GUI to be updated on the JavaFX Application Thread.
     *
     * @param turn The player who has the turn.
     * @param colors The new colors of the client.
     */
    @Override
    public void updateColors(Player turn, LinkedList<PlayerColor> colors) {
        client.getClient().setColors(colors);
        Platform.runLater(() -> {
            try {
                if(guicontrollers.get("color") != null)
                    ((ColorController) guicontrollers.get("color")).loadThings(colors);
                else switchToScene("color", colors, true);
            } catch (IOException e) {
                System.out.println("Color scene non correttamente inizializzata");
            }
        });
    }

    /**
     * Returns the client associated with this GUI.
     *
     * @return The client associated with this GUI.
     */
    public CommonClient getClient() {
        return this.client;
    }
    /**
     * Sets the player color for this GUI.
     *
     * @param color The new player color.
     */
    public void setPlayerColor(PlayerColor color) {
        this.color = color;
    }

    /**
     * Sets the chat controller for this GUI.
     *
     * @param chatController The new chat controller.
     */
    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

    /**
     * Returns the player who currently has the turn.
     *
     * @return The player who currently has the turn.
     */
    public Player getTurn() {
        return Turn;
    }
}