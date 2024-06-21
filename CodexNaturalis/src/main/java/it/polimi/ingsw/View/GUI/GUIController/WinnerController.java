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
        loadLabelUno();
        loadLabelDue();
        loadLabelTre();
        loadLabelQuattro();
    }
    private void loadLabelUno(){
        labelUno = new Label("1." + standings.get(0));
        labelUno.setVisible(true);
    }
    private void loadLabelDue(){
        labelDue = new Label("2." + standings.get(1));
        labelDue.setVisible(true);
    }
    private void loadLabelTre(){
        labelTre =  new Label("2." + standings.get(1));
        labelTre.setVisible(true);
    }
    private void loadLabelQuattro(){
        labelQuattro = new Label("4." + standings.get(3));
        labelQuattro.setVisible(true);
    }
}
