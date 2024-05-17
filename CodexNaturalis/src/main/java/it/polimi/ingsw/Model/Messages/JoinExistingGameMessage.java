package it.polimi.ingsw.Model.Messages;
import com.google.gson.Gson;
import it.polimi.ingsw.Model.PlayerPackage.*;

public class JoinExistingGameMessage extends Message{
    public String name;


    public JoinExistingGameMessage(String nickname) {
        super("JoinExistingGameMessage");
        this.name=nickname;
    }

    public String MessageToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
