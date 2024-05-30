package it.polimi.ingsw.Main;

import it.polimi.ingsw.View.GameView;
import it.polimi.ingsw.View.TUI.TUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainClient {
    public static void main(String[] args) {
        int input;
        boolean goon = false;

        do{
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("if you want to play with a Tui write 1");
                System.out.println("if you want to play with a Tui write 2");

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
