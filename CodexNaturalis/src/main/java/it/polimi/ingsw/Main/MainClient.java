package it.polimi.ingsw.Main;

import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.GUI.GUIApplication;
import it.polimi.ingsw.View.GameView;
import it.polimi.ingsw.View.TUI.TUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static it.polimi.ingsw.View.TUI.CardsTUI.*;

public class MainClient {
    public static final String ANSI_ORANGE = "\033[48;2;255;165;0m";
    public static final String ANSI_REDD = "\033[103m";
    public static final String ANSI_GIANNI = "\033[106m";

    public static void main(String[] args) {
        System.out.println("   _____          _             _   _       _                   _ _     \n" +
                "  / ____|        | |           | \\ | |     | |                 | (_)    \n" +
                " | |     ___   __| | _____  __ |  \\| | __ _| |_ _   _ _ __ __ _| |_ ___ \n" +
                " | |    / _ \\ / _` |/ _ \\ \\/ / | . ` |/ _` | __| | | | '__/ _` | | / __|\n" +
                " | |___| (_) | (_| |  __/>  <  | |\\  | (_| | |_| |_| | | | (_| | | \\__ \\\n" +
                "  \\_____\\___/ \\__,_|\\___/_/\\_\\ |_| \\_|\\__,_|\\__|\\__,_|_|  \\__,_|_|_|___/\n" +
                "                                                                        \n" +
                "                                                                        \n");
        int input;
        boolean goon = false;

        do{
            try {
                System.out.println(ANSI_RESET );
                        System.out.println("Choose the kind of User Interface you want to play with");
                System.out.println("1 --> TUI\n2 --> GUI");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                input = Integer.parseInt(reader.readLine());
                if(input == 1){
                    TUI ui = new TUI();
                    ui.startTui();
                    goon = true;
                }
                else if(input == 2){
                    GUIApplication.main(args);
                    goon = true;
                }
                else{
                    System.out.println("Put a number between 1 or 2");
                }

            } catch (IOException e) {
                System.out.println("There has been a problem, try again");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        } while(!goon);
    }
}
