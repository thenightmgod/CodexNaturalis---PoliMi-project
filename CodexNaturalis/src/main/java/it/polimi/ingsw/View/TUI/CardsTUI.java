package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.*;
import it.polimi.ingsw.Model.CornerPackage.*;
import java.util.LinkedList;

import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.*;
import it.polimi.ingsw.Model.CornerPackage.*;
import it.polimi.ingsw.Model.CardPackage.*;
import it.polimi.ingsw.Model.DeckPackage.GoldDeck;
import it.polimi.ingsw.Model.DeckPackage.ResourceDeck;
import it.polimi.ingsw.Model.CornerPackage.CardRes;
import it.polimi.ingsw.Model.DeckPackage.StartDeck;

import java.security.interfaces.RSAKey;
import java.sql.SQLOutput;
import java.util.LinkedList;

import static it.polimi.ingsw.Model.CornerPackage.Resources.*;

import static it.polimi.ingsw.Model.CornerPackage.Resources.*;

public class CardsTUI {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";

    public CardsTUI() {
    }

    ;


    //------------------------PRINTARE START CARD-----------------------------
    public void PrintFrontStartCard(StartCard c) {
        System.out.print(ANSI_BRIGHT_YELLOW);
        System.out.println("———————————————");
        System.out.print("|");
        StringBuilder sb = new StringBuilder();
        Corner corner = c.getCorner(Orientation.HL);
        printCorner(corner, sb);
        System.out.print("         ");
        StringBuilder sb2 = new StringBuilder();
        Corner c3 = c.getCorner(Orientation.HR);
        printCorner(c3, sb2);
        System.out.println("|");
        System.out.print("|  ");

        int size = c.getBackRes().size();
        PrintBackResFrontStartCard(c, size);

        System.out.println("   |");
        System.out.print("|");
        StringBuilder sb3 = new StringBuilder();
        Corner cornerll = c.getCorner(Orientation.LL);
        printCorner(cornerll, sb3);
        System.out.print("         ");
        StringBuilder sb4 = new StringBuilder();
        Corner c4 = c.getCorner(Orientation.LR);
        printCorner(c4, sb4);
        System.out.println("|");
        System.out.print("———————————————");
        System.out.println(ANSI_RESET);

    }

    public void PrintBackResFrontStartCard(StartCard c, int size) {
        LinkedList<Resources> BackRes = c.getBackRes();
        if (size == 1) {
            System.out.print("  ");
        } else if (size == 2) {
            System.out.print(" ");
        }
        System.out.print("[");
        if (BackRes.contains(ANIMAL_KINGDOM)) {
            System.out.print((Resources.ANIMAL_KINGDOM.getShortName()));
            ;
        }
        if (BackRes.contains(PLANT_KINGDOM)) {
            System.out.print((Resources.PLANT_KINGDOM.getShortName()));
        }
        if (BackRes.contains(FUNGI_KINGDOM)) {
            System.out.print((FUNGI_KINGDOM.getShortName()));
        }
        if (BackRes.contains(INSECT_KINGDOM)) {
            System.out.print((Resources.INSECT_KINGDOM.getShortName()));
        }
        System.out.print("]");
        if (size == 1) {
            System.out.print("  ");
        } else if (size == 2) {
            System.out.print(" ");
        }

    }


    public void printBackStartCard(StartCard c) {
        System.out.print(ANSI_BRIGHT_YELLOW);
        System.out.println("———————————————");
        System.out.print("|");

        StringBuilder sb = new StringBuilder();
        Corner corner = c.getBackCorner(Orientation.HL);
        printCorner(corner, sb);

        System.out.print("         ");

        StringBuilder sb2 = new StringBuilder();
        Corner c3 = c.getBackCorner(Orientation.HR);
        printCorner(c3, sb2);
        System.out.println("|");
        System.out.print("|  ");

        System.out.println("           |");
        System.out.print("|");

        StringBuilder sb3 = new StringBuilder();
        Corner cornerll = c.getBackCorner(Orientation.LL);
        printCorner(cornerll, sb3);
        System.out.print("         ");

        StringBuilder sb4 = new StringBuilder();
        Corner c4 = c.getBackCorner(Orientation.LR);
        printCorner(c4, sb4);
        System.out.println("|");
        System.out.print("———————————————");


        System.out.println(ANSI_RESET);

    }

    //-------------------------PRINTARE RESOURCE CARD----------------------------
    public void printFrontResourceCard(ResourceCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.println(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.println(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.println(ANSI_RED);
        System.out.println("———————————————");
        System.out.print("|");
        StringBuilder sb = new StringBuilder();
        Corner c = card.getCorner(Orientation.HL);
        printCorner(c, sb);
        printResourcePoints(card);


        System.out.print("    ");
        StringBuilder sb2 = new StringBuilder();
        Corner c3 = card.getCorner(Orientation.HR);
        printCorner(c3, sb2);
        System.out.println("|");

        System.out.println("|             |");


        System.out.print("|");
        StringBuilder sb3 = new StringBuilder();
        Corner c2 = card.getCorner(Orientation.LL);
        printCorner(c2, sb3);
        System.out.print("         ");

        StringBuilder sb4 = new StringBuilder();
        Corner c4 = card.getCorner(Orientation.LR);
        printCorner(c4, sb4);
        System.out.println("|");

        System.out.print("———————————————");
        System.out.print(ANSI_RESET);

    }

    public void printResourcePoints(ResourceCard card) {
        System.out.print(ANSI_RESET);
        int points = ((ResourceCard) card).getPoints();
        System.out.print("    ");
        if (points > 0) {
            System.out.print("" + points);
        } else
            System.out.print(" ");
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);
    }

    public void printBackResourceCard(ResourceCard card) {
        CardColor c = card.getColor();
        switch (c) {
            case GREEN -> System.out.print(ANSI_GREEN);
            case RED -> System.out.print(ANSI_RED);
            case PURPLE -> System.out.print(ANSI_PURPLE);
            case BLUE -> System.out.print(ANSI_BLUE);
        }
        System.out.println("———————————————");
        System.out.print("|");
        System.out.print("              ");
        System.out.println("|");
        System.out.print("|      ");
        switch (c) {
            case GREEN -> System.out.print((Resources.PLANT_KINGDOM.getShortName()));
            case RED -> System.out.print((FUNGI_KINGDOM.getShortName()));
            case PURPLE -> System.out.print((INSECT_KINGDOM.getShortName()));
            case BLUE -> System.out.print((ANIMAL_KINGDOM.getShortName()));
        }
        System.out.println("      |");
        System.out.println("|              |");
        System.out.println("———————————————");
        System.out.print(ANSI_RESET);
    }


    //------------------PRINTARE GOLD CARD------------------------
    public void printFrontGoldCard(GoldCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);
        System.out.println("———————————————");
        System.out.print("|");
        StringBuilder sb = new StringBuilder();
        Corner c = card.getCorner(Orientation.HL);
        printCorner(c, sb);
        printPoints(card);


        StringBuilder sb2 = new StringBuilder();
        Corner c3 = card.getCorner(Orientation.HR);
        printCorner(c3, sb2);
        System.out.println("|");

        System.out.println("|              |");


        System.out.print("|");
        StringBuilder sb3 = new StringBuilder();
        Corner c2 = card.getCorner(Orientation.LL);
        printCorner(c2, sb3);
        System.out.print(" ");
        printRequirement(card);
        System.out.print(" ");


        StringBuilder sb4 = new StringBuilder();
        Corner c4 = card.getCorner(Orientation.LR);
        printCorner(c4, sb4);
        System.out.println("|");

        System.out.print("———————————————");
        System.out.print(ANSI_RESET);
    }

    public void printPoints(GoldCard card) {
        int points = ((GoldCard) card).getPoints();
        System.out.print(ANSI_RESET);
        if (points < 5) {
            System.out.print("   " + points);
        } else
            System.out.print("    " + points);
        if (((GoldCard) card).getPointsCondition().equals(PointsCondition.CORNERS)) {
            System.out.print(PointsCondition.CORNERS.getShortName());
        } else if (((GoldCard) card).getPointsCondition().equals(PointsCondition.FREE)) {
            System.out.print(PointsCondition.FREE.getShortName());
        } else if (((GoldCard) card).getPointsCondition().equals(PointsCondition.OBJECTS_INKWELL)) {
            System.out.print(PointsCondition.OBJECTS_INKWELL.getShortName());
        } else if (((GoldCard) card).getPointsCondition().equals(PointsCondition.OBJECTS_MANUSCRIPT)) {
            System.out.print(PointsCondition.OBJECTS_MANUSCRIPT.getShortName());
        } else {
            System.out.print(PointsCondition.OBJECTS_QUILL.getShortName());
        }
        System.out.print("   ");
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);

    }

    public void printRequirement(GoldCard card) {
        System.out.print(ANSI_RESET);
        int[] requirements = ((GoldCard) card).getRequirements();
        int type = 0;
        System.out.print(" ");
        for (int i = 0; i < 4; i++) {
            if (requirements[i] != 0) {
                if (requirements[i] == 5) {
                    System.out.print(" ");
                }
                type = type + 1;
                int x = requirements[i];
                System.out.print("" + x);
                if (i == 0) {
                    System.out.print((Resources.PLANT_KINGDOM.getShortName()));
                } else if (i == 1) {
                    System.out.print((Resources.ANIMAL_KINGDOM.getShortName()));
                } else if (i == 2) {
                    System.out.print((FUNGI_KINGDOM.getShortName()));
                } else {
                    System.out.print((Resources.INSECT_KINGDOM.getShortName()));
                }
            }
        }
        if (type == 1) {
            System.out.print("  ");
        }
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);
    }


    public void printBackGoldCard(GoldCard card) {
        CardColor c = card.getColor();
        switch (c) {
            case GREEN -> System.out.print(ANSI_GREEN);
            case RED -> System.out.print(ANSI_RED);
            case PURPLE -> System.out.print(ANSI_PURPLE);
            case BLUE -> System.out.print(ANSI_BLUE);
        }
        System.out.println("———————————————");
        System.out.print("|");
        System.out.print("              ");
        System.out.println("|");
        System.out.print("|      ");
        switch (c) {
            case GREEN -> System.out.print((Resources.PLANT_KINGDOM.getShortName()));
            case RED -> System.out.print((FUNGI_KINGDOM.getShortName()));
            case PURPLE -> System.out.print((INSECT_KINGDOM.getShortName()));
            case BLUE -> System.out.print((ANIMAL_KINGDOM.getShortName()));
        }
        System.out.println("      |");
        System.out.println("|              |");
        System.out.println("———————————————");
        System.out.print(ANSI_RESET);
    }


    //-----------------------PRINTARE ANGOLI (UGUALE SIA PER GOLD CHE PER RISORSA)------------------------------
    public void printCorner(Corner c, StringBuilder sb) {
        if (c.getCovered()) {
            sb.append("X");
        } else {
            if (c.getRes().equals(Resources.PLANT_KINGDOM)) {
                sb.append(Resources.PLANT_KINGDOM.getShortName());
            } else if (c.getRes().equals(Resources.ANIMAL_KINGDOM)) {
                sb.append(Resources.ANIMAL_KINGDOM.getShortName());
            } else if (c.getRes().equals(FUNGI_KINGDOM)) {
                sb.append(FUNGI_KINGDOM.getShortName());
            } else if (c.getRes().equals(Resources.INSECT_KINGDOM)) {
                sb.append(Resources.INSECT_KINGDOM.getShortName());
            } else if (c.getRes().equals(Objects.QUILL)) {
                sb.append(Objects.QUILL.getShortName());
            } else if (c.getRes().equals(Objects.MANUSCRIPT)) {
                sb.append(Objects.MANUSCRIPT.getShortName());
            } else if (c.getRes().equals(Objects.INKWELL)) {
                sb.append(Objects.INKWELL.getShortName());
            } else if (c.getRes().equals(CornerState.ABSENT)) {
                sb.append(CornerState.ABSENT.getShortName());
            } else if (c.getRes().equals(CornerState.EMPTY)) {
                sb.append(CornerState.EMPTY.getShortName());
            }
            System.out.print(sb.toString());
        }
    }
}