package it.polimi.ingsw.View.GUI.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.LinkedList;

public class WinnerController extends GUIController {
    private LinkedList<String> standings;
    @FXML
    private Label labelUno;
    @FXML
    private Label labelDue;
    @FXML
    private Label labelTre;
    @FXML
    private Label labelQuattro;
    @Override
    public void setArgs(Object... args){
        standings = (LinkedList<String>) args[0];
        int size = standings.size();
        switch(size) {
            case 2 -> {
                loadLabelDue();
            }
            case 3 -> {
                loadLabelTre();
            }
            case 4 -> {
                loadLabelDue();
                loadLabelTre();
                loadLabelQuattro();
            }
        }
    }
    private void loadLabelDue(){
        labelUno.setText("1."+standings.get(1));
        labelUno.setVisible(true);
        labelDue.setText("2."+ standings.get(0));
        labelDue.setVisible(true);
    }
    private void loadLabelTre(){
        labelUno.setText("1."+ standings.get(2));
        labelUno.setVisible(true);
        labelDue.setText("2."+ standings.get(1));
        labelDue.setVisible(true);
        labelTre.setText("3."+standings.get(0));
        labelTre.setVisible(true);
    }
    private void loadLabelQuattro(){
        labelUno.setText("1."+standings.get(3));
        labelUno.setVisible(true);
        labelDue.setText("2."+standings.get(2));
        labelDue.setVisible(true);
        labelTre.setText("3."+standings.get(1));
        labelTre.setVisible(true);
        labelQuattro.setText("4."+standings.get(0));
        labelQuattro.setVisible(true);
    }
}
