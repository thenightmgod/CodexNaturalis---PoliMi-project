package it.polimi.ingsw.Main;

import it.polimi.ingsw.View.GameView;
import it.polimi.ingsw.View.TUI.TUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static it.polimi.ingsw.View.TUI.CardsTUI.*;

public class MainClient {
    public static final String ANSI_ORANGE = "\033[48;2;255;165;0m";
    public static final String ANSI_REDD = "\033[101m";
    public static final String ANSI_GIANNI = "\033[106m";


    public static void main(String[] args) {
        int input;
        boolean goon = false;

        do{
            try {
                System.out.print(ANSI_REDD);
                System.out.println(" ░▒▓██████▓▒░ ░▒▓██████▓▒░░▒▓███████▓▒░░▒▓████████▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" +
                        "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░ \n" +
                        "░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░ \n" +
                        "░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓██████▓▒░  ░▒▓██████▓▒░  \n" +
                        "░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░ \n" +
                        "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░ \n" +
                        " ░▒▓██████▓▒░ ░▒▓██████▓▒░░▒▓███████▓▒░░▒▓████████▓▒░▒▓█▓▒░░▒▓█▓▒░ \n" );
                System.out.print(ANSI_GIANNI);
                        System.out.println("░▒▓███████▓▒░ ░▒▓██████▓▒░▒▓████████▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓███████▓▒░ ░▒▓██████▓▒░░▒▓█▓▒░      ░▒▓█▓▒░░▒▓███████▓▒░      ░▒▓███████▓▒░        ░▒▓██████▓▒░         \n" +
                                "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ ░▒▓█▓▒░   ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░             ░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░        \n" +
                                "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ ░▒▓█▓▒░   ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░             ░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░               \n" +
                                "░▒▓█▓▒░░▒▓█▓▒░▒▓████████▓▒░ ░▒▓█▓▒░   ░▒▓█▓▒░░▒▓█▓▒░▒▓███████▓▒░░▒▓████████▓▒░▒▓█▓▒░      ░▒▓█▓▒░░▒▓██████▓▒░       ░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░               \n" +
                                "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ ░▒▓█▓▒░   ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░      ░▒▓█▓▒░               \n" +
                                "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ ░▒▓█▓▒░   ░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░      ░▒▓█▓▒░░▒▓█▓▒░▒▓██▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓██▓▒░ \n" +
                                "░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░ ░▒▓█▓▒░    ░▒▓██████▓▒░░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░▒▓████████▓▒░▒▓█▓▒░▒▓███████▓▒░       ░▒▓███████▓▒░░▒▓██▓▒░░▒▓██████▓▒░░▒▓██▓▒░ \n");
                        System.out.print(ANSI_ORANGE);
                        System.out.println(ANSI_BLUE);
                        System.out.print(" ____   ___   ___ _____ _____    ____   ___   ___   ___   ___      __   _  _   _  ___   ___     _____ _____ _  ___   ___  \n" +
                                "|___ \\ / _ \\ / _ \\___  |___ /   |___ \\ / _ \\ / _ \\ / _ \\ ( _ )    / /_ | || | / |/ _ \\ / _ \\   |___  |___ // |/ _ \\ / _ \\ \n" +
                                "  __) | | | | | | | / /  |_ \\     __) | | | | | | | (_) |/ _ \\   | '_ \\| || |_| | | | | | | |     / /  |_ \\| | | | | | | |\n" +
                                " / __/| |_| | |_| |/ /  ___) |   / __/| |_| | |_| |\\__, | (_) |  | (_) |__   _| | |_| | |_| |    / /  ___) | | |_| | |_| |\n" +
                                "|_____|\\___/ \\___//_/  |____/   |_____|\\___/ \\___/   /_/ \\___/    \\___/   |_| |_|\\___/ \\___/    /_/  |____/|_|\\___/ \\___/ \n" );

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
                    //startare gui
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
