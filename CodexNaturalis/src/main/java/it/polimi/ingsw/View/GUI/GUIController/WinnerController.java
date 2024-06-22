package it.polimi.ingsw.View.GUI.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
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
                loadLabelQuattro();
            }
        }
    }
    private void loadLabelDue(){
        Image primo = loadImage("/view/MyCodexNaturalisPhotos/oro.png");
        image1.setImage(primo);
        labelUno.setText(standings.get(1));
        labelUno.setVisible(true);
        Image secondo = loadImage("/view/MyCodexNaturalisPhotos/argento.png");
        image2.setImage(secondo);
        labelDue.setText(standings.get(0));
        labelDue.setVisible(true);
    }
    private void loadLabelTre(){
        Image primo = loadImage("/view/MyCodexNaturalisPhotos/oro.png");
        image1.setImage(primo);
        labelUno.setText(standings.get(2));
        labelUno.setVisible(true);
        Image secondo = loadImage("/view/MyCodexNaturalisPhotos/argento.png");
        image2.setImage(secondo);
        labelDue.setText(standings.get(1));
        labelDue.setVisible(true);
        Image terzo = loadImage("/view/MyCodexNaturalisPhotos/bronzo.png");
        image3.setImage(terzo);
        labelTre.setText(standings.get(0));
        labelTre.setVisible(true);
    }
    private void loadLabelQuattro(){
        Image primo = loadImage("/view/MyCodexNaturalisPhotos/oro.png");
        image1.setImage(primo);
        labelUno.setText(standings.get(3));
        labelUno.setVisible(true);
        Image secondo = loadImage("/view/MyCodexNaturalisPhotos/argento.png");
        image2.setImage(secondo);
        labelDue.setText(standings.get(2));
        labelDue.setVisible(true);
        Image terzo = loadImage("/view/MyCodexNaturalisPhotos/bronzo.png");
        image3.setImage(terzo);
        labelTre.setText(standings.get(1));
        labelTre.setVisible(true);
        Image quarto = loadImage("/view/MyCodexNaturalisPhotos/carbone.png");
        image4.setImage(quarto);
        labelQuattro.setText(standings.get(0));
        labelQuattro.setVisible(true);
    }
}
