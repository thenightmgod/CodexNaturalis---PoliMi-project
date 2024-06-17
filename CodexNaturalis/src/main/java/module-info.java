module it.polimi.ingsw {
    requires com.google.gson;
    requires java.datatransfer;
    requires java.desktop;
    requires java.rmi;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    exports it.polimi.ingsw.View.GUI;
    exports it.polimi.ingsw.Network.RMI;
    exports it.polimi.ingsw.View.GUI.GUIController;
    opens it.polimi.ingsw.Controller to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.View.GUI.GUIController to javafx.fxml;
}