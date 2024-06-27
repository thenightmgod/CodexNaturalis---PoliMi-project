package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.PlayerPackage.PlayerColor;
import it.polimi.ingsw.View.ChatMessage;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.GoldCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.PlayableCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.ResourceCard;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.util.HashMap;
import java.util.LinkedList;

public class ClientModel {

    private String name;
    private LinkedList<String> players;
    private LinkedList<PlayableCard> hand;
    private PlayingField field;
    private HashMap<String, Integer> points;
    private LinkedList<GoalCard> commonGoals;
    private LinkedList<Position> freePosition;
    private LinkedList<GoldCard> drawableGoldCards;
    private LinkedList<ResourceCard> drawableResourceCards;
    private HashMap<String, PlayingField> otherFields;
    private LinkedList<ChatMessage> chat;
    private LinkedList<PlayerColor> colors;

    public ClientModel(String name){
        this.name = name;
        this.hand = new LinkedList<>();
        this.field = new PlayingField();
        this.points = new HashMap<>();
        this.commonGoals = new LinkedList<>();
        this.freePosition = new LinkedList<>();
        this.drawableGoldCards = new LinkedList<>();
        this.drawableResourceCards = new LinkedList<>();
        this.otherFields = new HashMap<>();
        this.players = new LinkedList<>();
        this.chat = new LinkedList<>();
        this.colors = new LinkedList<>();
    }

    public void setColors(LinkedList<PlayerColor> colors){
        this.colors = colors;
    }

    public LinkedList<PlayerColor> getColors(){
        return colors;
    }

    public void setPlayers(LinkedList<String> players){
        this.players = players;
    }

    public LinkedList<String> getPlayers(){
        return players;
    }

    public LinkedList<GoldCard> getDrawableGoldCards(){
        return drawableGoldCards;
    }

    public void setDrawableGoldCards(LinkedList<GoldCard> drawableGoldCards){
        this.drawableGoldCards = drawableGoldCards;
    }

    public LinkedList<ResourceCard> getDrawableResourceCards(){
        return drawableResourceCards;
    }

    public void setDrawableResourceCards(LinkedList<ResourceCard> drawableResourceCards){
        this.drawableResourceCards = drawableResourceCards;
    }

    public String getName(){
        return name;
    }

    public void setName(String Name){
        this.name = Name;
    }

    public void setFreePositions(LinkedList<Position> freePosition){
        this.freePosition = freePosition;
    }

    public LinkedList<Position> getFreePositions(){
        return freePosition;
    }

    public LinkedList<PlayableCard> getHand(){
        return hand;
    }

    public void setHand(LinkedList<PlayableCard> Hand){
        this.hand = Hand;
    }

    public PlayingField getField(){
        return field;
    }

    public void setField(PlayingField Field){
        this.field = Field;
    }

    public HashMap<String, Integer> getPointsCounter(){
        return points;
    }

    public void setPointsCounter(HashMap<String, Integer> points){
        this.points = points;
    }

    public LinkedList<GoalCard> getCommonGoals(){
        return commonGoals;
    }

    public void setCommonGoals(LinkedList<GoalCard> commonGoals){
        this.commonGoals = commonGoals;
    }

    public HashMap<String, PlayingField> getOtherFields(){
        return otherFields;
    }

    public void setChat(LinkedList<ChatMessage> chat){
        this.chat = chat;
    }

    public LinkedList<ChatMessage> getChat(){
        return chat;
    }

}
