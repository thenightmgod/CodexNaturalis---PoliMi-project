module it.polimi.ingsw {
    requires com.google.gson;
    requires java.datatransfer;
    requires java.desktop;
    requires java.rmi;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    exports it.polimi.ingsw.View;
    exports it.polimi.ingsw.Model.PlayerPackage;
    exports it.polimi.ingsw.View.GUI;
    exports it.polimi.ingsw.Network.RMI;
    exports it.polimi.ingsw.Network;
    exports it.polimi.ingsw.Controller;
    exports it.polimi.ingsw.View.TUI;
    exports it.polimi.ingsw.Model.CornerPackage;
    exports it.polimi.ingsw.Model.CardPackage.PlayableCardPackage;
    exports it.polimi.ingsw.Model.CardPackage.GoalCardPackage;
    exports it.polimi.ingsw.View.GUI.GUIController;
    exports it.polimi.ingsw.Model.Messages to com.google.gson;
    opens it.polimi.ingsw.Controller to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.View.GUI.GUIController to javafx.fxml;
    opens it.polimi.ingsw.Model.CardPackage to com.google.gson;
    opens it.polimi.ingsw.Model.CornerPackage to com.google.gson;
    opens it.polimi.ingsw.Model.DeckPackage to com.google.gson;
    opens it.polimi.ingsw.Model.PlayerPackage to com.google.gson;
    opens it.polimi.ingsw.View to com.google.gson;
    opens it.polimi.ingsw.Model.CardPackage.PlayableCardPackage to com.google.gson;
    opens it.polimi.ingsw.Model.CardPackage.GoalCardPackage to com.google.gson;

}