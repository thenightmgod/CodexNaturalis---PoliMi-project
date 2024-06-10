package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ObjectsGoalCard;
import it.polimi.ingsw.Model.CardPackage.PlayableCardPackage.*;
import it.polimi.ingsw.Model.CornerPackage.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

import it.polimi.ingsw.Model.DeckPackage.GoldDeck;
import it.polimi.ingsw.Model.DeckPackage.ResourceDeck;
import it.polimi.ingsw.Model.CornerPackage.CardRes;
import it.polimi.ingsw.Model.DeckPackage.StartDeck;
import it.polimi.ingsw.Model.PlayerPackage.*;
import it.polimi.ingsw.Model.RoomPackage.Room;
import it.polimi.ingsw.Network.VirtualView;

import java.util.List;

import static it.polimi.ingsw.Model.CornerPackage.Resources.*;

import static it.polimi.ingsw.Model.PlayerPackage.FB.BACK;
import static it.polimi.ingsw.Model.PlayerPackage.FB.FRONT;

public class CardsTUI {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    //public static final String ANSI_BG_PURPLE = "\u001B[45m";
    // public static final String ANSI_BG_GREEN = "\u001B[42m";
    public static final String GREEN_SQUARE = "🟩";
    public static final String PURPLE_SQUARE = "\uD83D\uDFEA";
    public static final String RED_SQUARE = "\uD83D\uDFE5";
    public static final String BLUE_SQUARE = "🟦";

    public CardsTUI() {
    }

    //-----------------------------------------PRINTARE START CARD------------------------------------------------------

    public void printFrontStartCard(StartCard c) {
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
        System.out.println("———————————————");
        System.out.print(ANSI_RESET);
    }

    public void printStartCardJackie(StartCard c) {
        System.out.print(ANSI_BRIGHT_YELLOW);
        System.out.print("———————————————");
    }

    public void printFrontStartCardFirstLine(StartCard c) {
        System.out.print(ANSI_BRIGHT_YELLOW);
        System.out.print("|");
        StringBuilder sb = new StringBuilder();
        Corner corner = c.getCorner(Orientation.HL);
        printCorner(corner, sb);
        System.out.print("         ");
        StringBuilder sb2 = new StringBuilder();
        Corner c3 = c.getCorner(Orientation.HR);
        printCorner(c3, sb2);
        System.out.print("|");
    }

    public void printFrontStartCardSecondLine(StartCard c) {
        System.out.print(ANSI_BRIGHT_YELLOW);
        System.out.print("|  ");

        int size = c.getBackRes().size();
        PrintBackResFrontStartCard(c, size);

        System.out.print("   |");
    }

    public void printFrontStartCardThirdLine(StartCard c) {
        System.out.print(ANSI_BRIGHT_YELLOW);
        System.out.print("|");
        StringBuilder sb3 = new StringBuilder();
        Corner cornerll = c.getCorner(Orientation.LL);
        printCorner(cornerll, sb3);
        System.out.print("         ");
        StringBuilder sb4 = new StringBuilder();
        Corner c4 = c.getCorner(Orientation.LR);
        printCorner(c4, sb4);
        System.out.print("|");
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
        System.out.println("———————————————");


        System.out.print(ANSI_RESET);
    }

    public void printBackStartCardFirstLine(StartCard c) {
        System.out.print(ANSI_BRIGHT_YELLOW);
        System.out.print("|");

        StringBuilder sb = new StringBuilder();
        Corner corner = c.getBackCorner(Orientation.HL);
        printCorner(corner, sb);

        System.out.print("         ");

        StringBuilder sb2 = new StringBuilder();
        Corner c3 = c.getBackCorner(Orientation.HR);
        printCorner(c3, sb2);
        System.out.print("|");
    }

    public void printBackStartCardSecondLine(StartCard c) {
        System.out.print(ANSI_BRIGHT_YELLOW);
        System.out.print("|  ");

        System.out.print("           |");
    }

    public void printBackStartCardThirdLine(StartCard c) {
        System.out.print(ANSI_BRIGHT_YELLOW);
        System.out.print("|");

        StringBuilder sb3 = new StringBuilder();
        Corner cornerll = c.getBackCorner(Orientation.LL);
        printCorner(cornerll, sb3);
        System.out.print("         ");

        StringBuilder sb4 = new StringBuilder();
        Corner c4 = c.getBackCorner(Orientation.LR);
        printCorner(c4, sb4);
        System.out.print("|");
    }

    //-----------------------------------------PRINTARE RESOURCE CARD---------------------------------------------------

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

        System.out.println("———————————————");
        System.out.print(ANSI_RESET);
    }

    public void printResourceCardJackie(ResourceCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);
        System.out.print("———————————————");
    }

    public void printFrontResourceCardFirstLine(ResourceCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);

        System.out.print("|");
        StringBuilder sb = new StringBuilder();
        Corner c = card.getCorner(Orientation.HL);
        printCorner(c, sb);
        printResourcePoints(card);


        System.out.print("    ");
        StringBuilder sb2 = new StringBuilder();
        Corner c3 = card.getCorner(Orientation.HR);
        printCorner(c3, sb2);
        System.out.print("|");
    }

    public void printFrontResourceCardSecondLine(ResourceCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);

        System.out.print("|             |");
    }

    public void printFrontResourceCardThirdLine(ResourceCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);

        System.out.print("|");
        StringBuilder sb3 = new StringBuilder();
        Corner c2 = card.getCorner(Orientation.LL);
        printCorner(c2, sb3);
        System.out.print("         ");

        StringBuilder sb4 = new StringBuilder();
        Corner c4 = card.getCorner(Orientation.LR);
        printCorner(c4, sb4);
        System.out.print("|");
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

    public void printBackResourceCardFirstLine(ResourceCard card) {
        CardColor c = card.getColor();
        switch (c) {
            case GREEN -> System.out.print(ANSI_GREEN);
            case RED -> System.out.print(ANSI_RED);
            case PURPLE -> System.out.print(ANSI_PURPLE);
            case BLUE -> System.out.print(ANSI_BLUE);
        }
        System.out.print("|");
        System.out.print("              ");
        System.out.print("|");
    }

    public void printBackResourceCardSecondLine(ResourceCard card) {
        System.out.print("|      ");
        CardColor c = card.getColor();
        switch (c) {
            case GREEN -> System.out.print((Resources.PLANT_KINGDOM.getShortName()));
            case RED -> System.out.print((FUNGI_KINGDOM.getShortName()));
            case PURPLE -> System.out.print((INSECT_KINGDOM.getShortName()));
            case BLUE -> System.out.print((ANIMAL_KINGDOM.getShortName()));
        }
        System.out.print("      |");
    }

    public void printBackResourceCardThirdLine(ResourceCard card) {
        CardColor c = card.getColor();
        switch (c) {
            case GREEN -> System.out.print(ANSI_GREEN);
            case RED -> System.out.print(ANSI_RED);
            case PURPLE -> System.out.print(ANSI_PURPLE);
            case BLUE -> System.out.print(ANSI_BLUE);
        }
        System.out.print("|              |");
    }


    //----------------------------------------------PRINTARE GOLD CARD--------------------------------------------------
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

        System.out.println("———————————————");
        System.out.print(ANSI_RESET);
    }

    public void printGoldCardJackie(GoldCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);
        System.out.print("———————————————");
    }

    public void printFrontGoldCardFirstLine(GoldCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);
        System.out.print("|");
        StringBuilder sb = new StringBuilder();
        Corner c = card.getCorner(Orientation.HL);
        printCorner(c, sb);
        printPoints(card);


        StringBuilder sb2 = new StringBuilder();
        Corner c3 = card.getCorner(Orientation.HR);
        printCorner(c3, sb2);
        System.out.print("|");
    }

    public void printFrontGoldCardSecondLine(GoldCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);
        System.out.print("|              |");
    }

    public void printFrontGoldCardThirdLine(GoldCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.print(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.print(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.print(ANSI_RED);
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
        System.out.print("|");
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

    public void printBackGoldCardFirstLine(GoldCard card) {
        CardColor c = card.getColor();
        switch (c) {
            case GREEN -> System.out.print(ANSI_GREEN);
            case RED -> System.out.print(ANSI_RED);
            case PURPLE -> System.out.print(ANSI_PURPLE);
            case BLUE -> System.out.print(ANSI_BLUE);
        }
        System.out.print("|");
        System.out.print("              ");
        System.out.print("|");
    }

    public void printBackGoldCardSecondLine(GoldCard card) {
        CardColor c = card.getColor();
        switch (c) {
            case GREEN -> System.out.print(ANSI_GREEN);
            case RED -> System.out.print(ANSI_RED);
            case PURPLE -> System.out.print(ANSI_PURPLE);
            case BLUE -> System.out.print(ANSI_BLUE);
        }
        System.out.print("|      ");
        switch (c) {
            case GREEN -> System.out.print((Resources.PLANT_KINGDOM.getShortName()));
            case RED -> System.out.print((FUNGI_KINGDOM.getShortName()));
            case PURPLE -> System.out.print((INSECT_KINGDOM.getShortName()));
            case BLUE -> System.out.print((ANIMAL_KINGDOM.getShortName()));
        }
        System.out.print("      |");
    }

    public void printBackGoldCardThirdLine(GoldCard card) {
        CardColor c = card.getColor();
        switch (c) {
            case GREEN -> System.out.print(ANSI_GREEN);
            case RED -> System.out.print(ANSI_RED);
            case PURPLE -> System.out.print(ANSI_PURPLE);
            case BLUE -> System.out.print(ANSI_BLUE);
        }
        System.out.print("|              |");
    }

    //--------------------------------------------printare goal card----------------------------------------------------

    public void printGoalCard(GoalCard c) {
        System.out.print(ANSI_YELLOW);
        System.out.println("———————————————");
        System.out.print("|");
        int id = c.getId();
        if (id == 87 || id == 89) {
            printDiagonalUp(c);
        } else if (id == 88 || id == 90) {
            printDiagonalDown(c);
        } else if (id == 91) {
            printL(c);
        } else if (id == 92) {
            printReverseL(c);
        } else if (id == 93) {
            printGoalCard93(c);
        } else if (id == 94) {
            printGoalCard94(c);
        } else {
            System.out.print("      ");
            if (id > 94 && id < 99) {
                System.out.println("2      |");
                System.out.print("|   ");
                System.out.print("[");
                switch (id) {
                    case 95 -> {
                        System.out.print(FUNGI_KINGDOM.getShortName());
                        System.out.print(FUNGI_KINGDOM.getShortName());
                        System.out.print(FUNGI_KINGDOM.getShortName());
                    }
                    case 96 -> {
                        System.out.print(PLANT_KINGDOM.getShortName());
                        System.out.print(PLANT_KINGDOM.getShortName());
                        System.out.print(PLANT_KINGDOM.getShortName());
                    }
                    case 97 -> {
                        System.out.print(ANIMAL_KINGDOM.getShortName());
                        System.out.print(ANIMAL_KINGDOM.getShortName());
                        System.out.print(ANIMAL_KINGDOM.getShortName());
                    }
                    case 98 -> {
                        System.out.print(INSECT_KINGDOM.getShortName());
                        System.out.print(INSECT_KINGDOM.getShortName());
                        System.out.print(INSECT_KINGDOM.getShortName());
                    }
                }
                System.out.print("]");
            } else if (id > 98 && id <= 102) {
                int[] o = ((ObjectsGoalCard) c).getObj();
                if (id == 102) {
                    System.out.println("2      |");
                    System.out.print("|   ");
                    System.out.print("[");
                    System.out.print(Objects.QUILL.getShortName());
                    System.out.print(Objects.QUILL.getShortName());
                    System.out.print("]  ");
                } else if (id == 101) {
                    System.out.println("2      |");
                    System.out.print("|   ");
                    System.out.print("[");
                    System.out.print(Objects.INKWELL.getShortName());
                    System.out.print(Objects.INKWELL.getShortName());
                    System.out.print("]  ");
                } else if (id == 100) {
                    System.out.println("2      |");
                    System.out.print("|   ");
                    System.out.print("[");
                    System.out.print(Objects.MANUSCRIPT.getShortName());
                    System.out.print(Objects.MANUSCRIPT.getShortName());
                    System.out.print("]  ");
                } else {
                    System.out.println("3      |");
                    System.out.print("|   ");
                    System.out.print("[");
                    System.out.print(Objects.QUILL.getShortName());
                    System.out.print(Objects.INKWELL.getShortName());
                    System.out.print(Objects.MANUSCRIPT.getShortName());
                    System.out.print("]");
                }
            }
            System.out.println("  |");
            System.out.println("|             |");
            System.out.println("———————————————");
            System.out.println(ANSI_RESET);
        }
        System.out.print(ANSI_RESET);
    }

    private void printL(GoalCard c) {
        System.out.print("  3");
        System.out.print("    ");
        System.out.print(RED_SQUARE);
        System.out.println("    |");
        System.out.print("|       ");
        System.out.print(RED_SQUARE);
        System.out.println("    |");
        System.out.print("|         ");
        System.out.print(GREEN_SQUARE);
        System.out.println("  |");
        System.out.println("———————————————");
    }

    private void printGoalCard93(GoalCard c) {
        System.out.print("  3");
        System.out.print("      ");
        System.out.print(RED_SQUARE);
        System.out.println("  |");
        System.out.print("|       ");
        System.out.print(BLUE_SQUARE);
        System.out.println("    |");
        System.out.print("|       ");
        System.out.print(BLUE_SQUARE);
        System.out.println("    |");
        System.out.println("———————————————");
    }

    private void printReverseL(GoalCard c) {
        System.out.print("  3");
        System.out.print("    ");
        System.out.print(GREEN_SQUARE);
        System.out.println("    |");
        System.out.print("|       ");
        System.out.print(GREEN_SQUARE);
        System.out.println("    |");
        System.out.print("|     ");
        System.out.print(PURPLE_SQUARE);
        System.out.println("      |");
        System.out.println("———————————————");
    }

    private void printGoalCard94(GoalCard c) {
        System.out.print("  3");
        System.out.print("  ");
        System.out.print(BLUE_SQUARE);
        System.out.println("     |");
        System.out.print("|       ");
        System.out.print(PURPLE_SQUARE);
        System.out.println("    |");
        System.out.print("|       ");
        System.out.print(PURPLE_SQUARE);
        System.out.println("    |");
        System.out.println("———————————————");
    }

    public void printDiagonalDown(GoalCard c) {
        if (c.getId() == 88) {
            System.out.print("  2");
            System.out.print("  ");
            System.out.print(GREEN_SQUARE);
            System.out.println("      |");
            System.out.print("|       ");
            System.out.print(GREEN_SQUARE);
            System.out.println("    |");
            System.out.print("|         ");
            System.out.print(GREEN_SQUARE);
            System.out.println("  |");
            System.out.println("———————————————");
        } else if (c.getId() == 90) {
            System.out.print("  2");
            System.out.print("  ");
            System.out.print(PURPLE_SQUARE);
            System.out.println("      |");
            System.out.print("|       ");
            System.out.print(PURPLE_SQUARE);
            System.out.println("    |");
            System.out.print("|         ");
            System.out.print(PURPLE_SQUARE);
            System.out.println("  |");
            System.out.println("———————————————");
        }
    }

    public void printDiagonalUp(GoalCard c) {
        if (c.getId() == 89) {
            System.out.print("  2");
            System.out.print("    ");
            System.out.print(BLUE_SQUARE);
            System.out.println("    |");
            System.out.print("|     ");
            System.out.print(BLUE_SQUARE);
            System.out.println("      |");
            System.out.print("|   ");
            System.out.print(BLUE_SQUARE);
            System.out.println("        |");
            System.out.println("———————————————");
        } else if (c.getId() == 87) {
            System.out.print("  2");
            System.out.print("    ");
            System.out.print(RED_SQUARE);
            System.out.println("    |");
            System.out.print("|     ");
            System.out.print(RED_SQUARE);
            System.out.println("      |");
            System.out.print("|   ");
            System.out.print(RED_SQUARE);
            System.out.println("        |");
            System.out.println("———————————————");
        }
    }

    //----------------------------PRINTARE ANGOLI (UGUALE SIA PER GOLD CHE PER RISORSA)---------------------------------

    public void printCorner(Corner c, StringBuilder sb) {
        if (c.getCovered()) {
            sb.append("❌");
        } else {
        CardRes res = c.getRes();
        if (res instanceof Resources) {
            switch ((Resources) res) {
                case PLANT_KINGDOM:
                    sb.append(Resources.PLANT_KINGDOM.getShortName());
                    break;
                case ANIMAL_KINGDOM:
                    sb.append(Resources.ANIMAL_KINGDOM.getShortName());
                    break;
                case FUNGI_KINGDOM:
                    sb.append(Resources.FUNGI_KINGDOM.getShortName());
                    break;
                case INSECT_KINGDOM:
                    sb.append(Resources.INSECT_KINGDOM.getShortName());
                    break;
            }
        } else if (res instanceof Objects) {
            switch ((Objects) res) {
                case QUILL:
                    sb.append(Objects.QUILL.getShortName());
                    break;
                case MANUSCRIPT:
                    sb.append(Objects.MANUSCRIPT.getShortName());
                    break;
                case INKWELL:
                    sb.append(Objects.INKWELL.getShortName());
                    break;
            }
        } else if (res instanceof CornerState) {
            switch ((CornerState) res) {
                case ABSENT:
                    sb.append(CornerState.ABSENT.getShortName());
                    break;
                case EMPTY:
                    sb.append(CornerState.EMPTY.getShortName());
                    break;
            }
        }
        }
        // Stampa il contenuto del StringBuilder fuori dalle condizioni
        System.out.print(sb.toString());
        }

    public void plotPlayingField(ClientModel model){
        PlayingField Field = model.getField();
        int maxX = Field.getField().keySet().stream().mapToInt(Position::getX).max().orElse(400);
        int minX = Field.getField().keySet().stream().mapToInt(Position::getX).min().orElse(400);
        int maxY = Field.getField().keySet().stream().mapToInt(Position::getY).max().orElse(400);
        int minY = Field.getField().keySet().stream().mapToInt(Position::getY).min().orElse(400);

        for (int j = maxY; j >= minY; j--) {
                for (int i = minX; i <= maxX; i++) {
                    Position toCheck = new Position(i, j);
                    PlayableCard card = Field.getCardFromPos(toCheck);
                    if (card != null) {
                        int finalI = i;
                        int finalJ = j;
                        List<Position> positions = Field.getField().keySet().stream().filter(s -> s.getX() == finalI
                                && s.getY() == finalJ).toList();
                        Position position = positions.getFirst();
                        FB roba = position.getFace();
                        if (card.getId() >= 1 && card.getId() <= 40) {
                            ResourceCard c = (ResourceCard) card;
                            if (roba == FRONT) {
                                printResourceCardJackie(c);
                            } else if (roba == BACK) {
                                printResourceCardJackie(c);
                            }
                        } else if (card.getId() >= 41 && card.getId() <= 80) {
                            GoldCard c = (GoldCard) card;
                            if (roba == FRONT) {
                                printGoldCardJackie(c);
                            } else if (roba == BACK) {
                                printGoldCardJackie(c);
                            }
                        } else {
                            StartCard c = (StartCard) card;
                            if (roba == FRONT) {
                                printStartCardJackie(c);
                            } else if (roba == BACK) {
                                printStartCardJackie(c);
                            }
                        }
                    } else {
                        printEmpty();
                    }
                }
                System.out.println();

                for (int i = minX; i <= maxX; i++) {
                    Position toCheck = new Position(i, j);
                    PlayableCard card = Field.getCardFromPos(toCheck);
                    if (card != null) {
                        int finalI = i;
                        int finalJ = j;
                        List<Position> positions = Field.getField().keySet().stream().filter(s -> s.getX() == finalI
                                && s.getY() == finalJ).toList();
                        Position position = positions.get(0);
                        FB roba = position.getFace();
                        if (card.getId() >= 1 && card.getId() <= 40) {
                            ResourceCard c = (ResourceCard) card;
                            if (roba == FRONT) {
                                printFrontResourceCardFirstLine(c);
                            } else if (roba == BACK) {
                                printBackResourceCardFirstLine(c);
                            }
                        } else if (card.getId() >= 41 && card.getId() <= 80) {
                            GoldCard c = (GoldCard) card;
                            if (roba == FRONT) {
                                printFrontGoldCardFirstLine(c);
                            } else if (roba == BACK) {
                                printBackGoldCardFirstLine(c);
                            }
                        } else {
                            StartCard c = (StartCard) card;
                            if (roba == FRONT) {
                                printFrontStartCardFirstLine(c);
                            } else if (roba == BACK) {
                                printBackStartCardFirstLine(c);
                            }
                        }
                    } else {
                        printEmpty();
                    }
                }
                System.out.println();

                for (int i = minX; i <= maxX; i++) {
                    Position toCheck = new Position(i, j);
                    PlayableCard card = Field.getCardFromPos(toCheck);
                    if (card != null) {
                        int finalI = i;
                        int finalJ = j;
                        List<Position> positions = Field.getField().keySet().stream().filter(s -> s.getX() == finalI
                                && s.getY() == finalJ).toList();
                        Position position = positions.get(0);
                        FB roba = position.getFace();
                        if (card.getId() >= 1 && card.getId() <= 40) {
                            ResourceCard c = (ResourceCard) card;
                            if (roba == FRONT) {
                                printFrontResourceCardSecondLine(c);
                            } else if (roba == BACK) {
                                printBackResourceCardSecondLine(c);
                            }
                        } else if (card.getId() >= 41 && card.getId() <= 80) {
                            GoldCard c = (GoldCard) card;
                            if (roba == FRONT) {
                                printFrontGoldCardSecondLine(c);
                            } else if (roba == BACK) {
                                printBackGoldCardSecondLine(c);
                            }
                        } else {
                            StartCard c = (StartCard) card;
                            if (roba == FRONT) {
                                printFrontStartCardSecondLine(c);
                            } else if (roba == BACK) {
                                printBackStartCardSecondLine(c);
                            }
                        }
                    } else {
                        printEmpty();
                    }
                }
                System.out.println();

                for (int i = minX; i <= maxX; i++) {
                    Position toCheck = new Position(i, j);
                    PlayableCard card = Field.getCardFromPos(toCheck);
                    if (card != null) {
                        int finalI = i;
                        int finalJ = j;
                        List<Position> positions = Field.getField().keySet().stream().filter(s -> s.getX() == finalI
                                && s.getY() == finalJ).toList();
                        Position position = positions.get(0);
                        FB roba = position.getFace();
                        if (card.getId() >= 1 && card.getId() <= 40) {
                            ResourceCard c = (ResourceCard) card;
                            if (roba == FRONT) {
                                printFrontResourceCardThirdLine(c);
                            } else if (roba == BACK) {
                                 printBackResourceCardThirdLine(c);
                            }
                        } else if (card.getId() >= 41 && card.getId() <= 80) {
                            GoldCard c = (GoldCard) card;
                            if (roba == FRONT) {
                                printFrontGoldCardThirdLine(c);
                            } else if (roba == BACK) {
                                printBackGoldCardThirdLine(c);
                            }
                        } else {
                            StartCard c = (StartCard) card;
                            if (roba == FRONT) {
                                printFrontStartCardThirdLine(c);
                            } else if (roba == BACK) {
                                printBackStartCardThirdLine(c);
                            }
                        }
                    } else {
                        printEmpty();
                    }
                }
                System.out.println();

                for (int i = minX; i <= maxX; i++) {
                    Position toCheck = new Position(i, j);
                    PlayableCard card = Field.getCardFromPos(toCheck);
                    if (card != null) {
                        int finalI = i;
                        int finalJ = j;
                        List<Position> positions = Field.getField().keySet().stream().filter(s -> s.getX() == finalI
                                && s.getY() == finalJ).toList();
                        Position position = positions.getFirst();
                        FB roba = position.getFace();
                        if (card.getId() >= 1 && card.getId() <= 40) {
                            ResourceCard c = (ResourceCard) card;
                            if (roba == FRONT) {
                                printResourceCardJackie(c);
                            } else if (roba == BACK) {
                                printResourceCardJackie(c);
                            }
                        } else if (card.getId() >= 41 && card.getId() <= 80) {
                            GoldCard c = (GoldCard) card;
                            if (roba == FRONT) {
                                printGoldCardJackie(c);
                            } else if (roba == BACK) {
                                printGoldCardJackie(c);
                            }
                        } else {
                            StartCard c = (StartCard) card;
                            if (roba == FRONT) {
                                printStartCardJackie(c);
                            } else if (roba == BACK) {
                                printStartCardJackie(c);
                            }
                        }
                    } else {
                        printEmpty();
                    }
                }
                System.out.println();
            System.out.print(ANSI_RESET);
            }
    }

    public void printEmpty(){
        System.out.print("               ");
    }

    public static void main(String[] args) throws RemoteException {
        CardsTUI tui = new CardsTUI();
        GameController beppe = new GameController(5, 4);

        Player p = new Player("frank", PlayerColor.YELLOW);
        Player p2 = new Player("CARLOS O'CONNELL", PlayerColor.BLUE);
        Player p3 = new Player("MARCO DEVI MORIRE", PlayerColor.GREEN);
/*
        VirtualView v1 = new VirtualView() {
            @Override
            public void updateTurn(Player p, String mex) throws RemoteException {

            }

            @Override
            public void showException(String exception, String details) throws RemoteException, NotBoundException {

            }

            @Override
            public void updatePoints(int points, String name) throws RemoteException {

            }

            @Override
            public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {

            }

            @Override
            public void updateField(String name, PlayingField field) throws RemoteException {

            }

            @Override
            public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {

            }

            @Override
            public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException {

            }

            @Override
            public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException {

            }

            @Override
            public void declareWinner(LinkedList<String> standings) throws RemoteException {

            }

            @Override
            public void declareWinner(HashMap<String, Integer> classifica) throws RemoteException {

            }

            @Override
            public String getName() throws RemoteException {
                return "";
            }

            @Override
            public void showStartCard(StartCard card) throws RemoteException {

            }

            @Override
            public void startingGame(Player p) throws RemoteException {

            }

            @Override
            public void twenty(String name) throws RemoteException {

            }

            @Override
            public void lastRound() throws RemoteException {

            }

            @Override
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }

            public void notYourTurn(Player turn) throws RemoteException {

            }
        };
        VirtualView v2 = new VirtualView() {
            @Override
            public void updateTurn(Player p, String mex) throws RemoteException {

            }

            @Override
            public void notYourTurn(Player turn) throws RemoteException {

            }

            @Override
            public void showException(String exception, String details) throws RemoteException, NotBoundException {

            }

            @Override
            public void updatePoints(int points, String name) throws RemoteException {

            }

            @Override
            public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {

            }

            @Override
            public void updateField(String name, PlayingField field) throws RemoteException {

            }

            @Override
            public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {

            }

            @Override
            public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException {

            }

            @Override
            public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException {

            }

            @Override
            public void declareWinner(LinkedList<String> standings) throws RemoteException {

            }

            @Override
            public void declareWinner(HashMap<String, Integer> classifica) throws RemoteException {

            }

            @Override
            public String getName() throws RemoteException {
                return "";
            }

            @Override
            public void showStartCard(StartCard card) throws RemoteException {

            }

            @Override
            public void startingGame(Player p) throws RemoteException {

            }

            @Override
            public void twenty(String name) throws RemoteException {

            }

            @Override
            public void lastRound() throws RemoteException {

            }

            @Override
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };
        VirtualView v3 = new VirtualView() {
            @Override
            public void updateTurn(Player p, String mex) throws RemoteException {

            }

            @Override
            public void notYourTurn(Player turn) throws RemoteException {

            }

            @Override
            public void showException(String exception, String details) throws RemoteException, NotBoundException {

            }

            @Override
            public void updatePoints(int points, String name) throws RemoteException {

            }

            @Override
            public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {

            }

            @Override
            public void updateField(String name, PlayingField field) throws RemoteException {

            }

            @Override
            public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {

            }

            @Override
            public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException {

            }

            @Override
            public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException {

            }

            @Override
            public void declareWinner(LinkedList<String> standings) throws RemoteException {

            }

            @Override
            public void declareWinner(HashMap<String, Integer> classifica) throws RemoteException {

            }

            @Override
            public String getName() throws RemoteException {
                return "";
            }

            @Override
            public void showStartCard(StartCard card) throws RemoteException {

            }

            @Override
            public void startingGame(Player p) throws RemoteException {

            }

            @Override
            public void twenty(String name) throws RemoteException {

            }

            @Override
            public void lastRound() throws RemoteException {

            }

            @Override
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };
        VirtualView v4 = new VirtualView() {
            @Override
            public void updateTurn(Player p, String mex) throws RemoteException {

            }

            @Override
            public void notYourTurn(Player turn) throws RemoteException {

            }

            @Override
            public void showException(String exception, String details) throws RemoteException, NotBoundException {

            }

            @Override
            public void updatePoints(int points, String name) throws RemoteException {

            }

            @Override
            public void updateGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void updateCommonGoals(LinkedList<GoalCard> goals) throws RemoteException {

            }

            @Override
            public void showHand(LinkedList<PlayableCard> hand) throws RemoteException {

            }

            @Override
            public void updateField(String name, PlayingField field) throws RemoteException {

            }

            @Override
            public void showFreePositions(String name, LinkedList<Position> freePosition) throws RemoteException {

            }

            @Override
            public void updateResourceDeck(String name, boolean start, LinkedList<ResourceCard> deck) throws RemoteException {

            }

            @Override
            public void updateGoldDeck(String name, boolean start, LinkedList<GoldCard> deck) throws RemoteException {

            }

            @Override
            public void declareWinner(LinkedList<String> standings) throws RemoteException {

            }

            @Override
            public void declareWinner(HashMap<String, Integer> classifica) throws RemoteException {

            }

            @Override
            public String getName() throws RemoteException {
                return "";
            }

            @Override
            public void showStartCard(StartCard card) throws RemoteException {

            }

            @Override
            public void startingGame(Player p) throws RemoteException {

            }

            @Override
            public void twenty(String name) throws RemoteException {

            }

            @Override
            public void lastRound() throws RemoteException {

            }

            @Override
            public void update() throws RemoteException {

            }

            @Override
            public void showOtherField(String player) throws RemoteException {

            }
        };



        beppe.addPlayer("frank", PlayerColor.YELLOW, v1);
        beppe.addPlayer("CARLOS O'CONNELL", PlayerColor.BLUE,v2);
        beppe.addPlayer("MARCO DEVI MORIRE", PlayerColor.GREEN, v3);
        */

        tui.plotPoints(beppe);

        Position position1 = new Position(1,1);
        Position position2 = new Position(2,2);
        Position position3 = new Position(3,3);
        Position position4 = new Position(0,0);
        Position position5 = new Position(0,2);
        Position position6 = new Position(-1,1);
        Position position7 = new Position(-2,2);
        Position position8 = new Position(-3,3);

        ResourceDeck rd = new ResourceDeck();
        StartDeck sd = new StartDeck();
        GoldDeck gd = new GoldDeck();

        ResourceCard r1 = (ResourceCard) rd.getCardById(1);
        ResourceCard r2 = (ResourceCard) rd.getCardById(2);
        ResourceCard r3 = (ResourceCard) rd.getCardById(3);
        StartCard s1 = (StartCard) sd.getCardById(85);
        ResourceCard r4 = (ResourceCard)rd.getCardById(6);

        GoldCard g1 = (GoldCard) gd.getCardById(41);
        GoldCard g2 = (GoldCard) gd.getCardById(43);
        GoldCard g3 = (GoldCard) gd.getCardById(45);

        HashMap<Position, PlayableCard> mappa = p.getPlayerField().getField();
        mappa.put(position1, r1);
        mappa.put(position2, r2);
        mappa.put(position3, r3);
        mappa.put(position5, r4);
        mappa.put(position4, s1);
        mappa.put(position6, g1);
        mappa.put(position7, g2);
        mappa.put(position8, g3);

        tui.plotPoints(beppe);

    }

    //-------------------------------PUNTIIIIII--------------------------------------------------------------------------

    public void plotPoints(GameController controller){
        System.out.printf("%-20s %-10s%n", "Player", "Points");
        System.out.println("---------------------------------");

        int carlos = controller.getPlayers().size();
        int zero = 0;
        for(int i  = zero ; i < carlos; i++){

            System.out.printf("%-20s %-10d%n", controller.getPlayers().get(i).getName(), controller.getPlayers().get(i).getPointsCounter());
        }
    }

    public void plotPlayerResources(ClientModel model) {
        System.out.printf("%-20s %-10s %-10s %-10s %-10s %-10s %-10s %-10s%n",
                "Player", "Plant", "Animal", "Fungi", "Insect", "Quill", "Inkwell", "Manuscript");
        System.out.println("-------------------------------------------------------------------------------");


 /*       int[] resources = player.getResourceCounter();
        int[] objects =
        System.out.printf("%-20s %-10d %-10d %-10d %-10d %-10d %-10d %-10d%n",
                    player.getName(), resources[0], resources[1], resources[2], resources[3],
                    objects[0], objects[1], objects[2]);*/
    }

    //-----------------------------------------HAND--------------------------------------------------------------------

    public void plotHand(ClientModel model){
        LinkedList<PlayableCard> toPrint = model.getHand();
        for(PlayableCard p : toPrint){
            if (p.getId() >= 1 && p.getId() <= 40) {
                printFrontResourceCard((ResourceCard) p);
                printBackResourceCard((ResourceCard) p);
            } else if (p.getId() >= 41 && p.getId() <= 80) {
                printFrontGoldCard((GoldCard) p);
                printBackGoldCard((GoldCard) p);
            } else {
                printFrontStartCard((StartCard) p);
                printBackStartCard((StartCard) p);
            }

        }
    }

    public void plotHandSeria(ClientModel model){
        LinkedList<PlayableCard> toPrint= model.getHand();
        System.out.println("");
        System.out.println("CARD 1               CARD 2               CARD 3");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printResourceCardJackie(c);
                }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                    printGoldCardJackie(c);
            }
            System.out.print("      ");
            System.out.print(ANSI_RESET);
        }
        System.out.println("");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printFrontResourceCardFirstLine(c);
            }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                printFrontGoldCardFirstLine(c);
            }
            System.out.print("     ");
        }
        System.out.println("");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printFrontResourceCardSecondLine(c);
            }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                printFrontGoldCardSecondLine(c);
            }
            System.out.print("     ");
            System.out.print(ANSI_RESET);
        }
        System.out.println("");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printFrontResourceCardThirdLine(c);
            }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                printFrontGoldCardThirdLine(c);
            }
            System.out.print("     ");
            System.out.print(ANSI_RESET);
        }
        System.out.println("");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printResourceCardJackie(c);
            }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                printGoldCardJackie(c);
            }
            System.out.print("      ");
            System.out.print(ANSI_RESET);
        }
        System.out.println("");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printResourceCardJackie(c);
            }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                printGoldCardJackie(c);
            }
            System.out.print("      ");
            System.out.print(ANSI_RESET);
        }
        System.out.println("");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printBackResourceCardFirstLine(c);
            }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                printBackGoldCardFirstLine(c);
            }
            System.out.print("     ");
            System.out.print(ANSI_RESET);
        }
        System.out.println("");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printBackResourceCardSecondLine(c);
            }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                printBackGoldCardSecondLine(c);
            }
            System.out.print("     ");
        }
        System.out.println("");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printBackResourceCardThirdLine(c);
            }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                printBackGoldCardThirdLine(c);
            }
            System.out.print("     ");
            System.out.print(ANSI_RESET);
        }
        System.out.println("");
        for(PlayableCard card : toPrint){
            if (card.getId() >= 1 && card.getId() <= 40) {
                ResourceCard c = (ResourceCard) card;
                printResourceCardJackie(c);
            }
            else if (card.getId() >= 41 && card.getId() <= 80){
                GoldCard c = (GoldCard) card;
                printGoldCardJackie(c);
            }
            System.out.print("      ");
        }
        System.out.println("");
        System.out.println("");
        System.out.print(ANSI_RESET);
    }

    //---------------------------------------GOLDDECK--------------------------------------------------------------

    public void plotGoldDeck(ClientModel model){
        LinkedList<GoldCard> toPrint = model.getDrawableGoldCards();
        printFrontGoldCard(toPrint.get(0));
        printFrontGoldCard(toPrint.get(1));
        printBackGoldCard(toPrint.get(2));
    }

    public void plotResourceDeck(ClientModel model){
        LinkedList<ResourceCard> toPrint = model.getDrawableResourceCards();
        printFrontResourceCard(toPrint.get(0));
        printFrontResourceCard(toPrint.get(1));
        printBackResourceCard( toPrint.get(2));
    }

    public void plotDrawables(ClientModel model){
        LinkedList<GoldCard> Gold = model.getDrawableGoldCards();
        LinkedList<ResourceCard> Res = model.getDrawableResourceCards();

        System.out.println("");
        System.out.println("RESOURCE CARDS               GOLD CARDS");

        printResourceCardJackie(Res.get(0));
        System.out.print("      ");
        printGoldCardJackie(Gold.get(0));
        System.out.print(ANSI_RESET);
        System.out.println("");

        printFrontResourceCardFirstLine(Res.get(0));
        System.out.print("      ");
        printFrontGoldCardFirstLine(Gold.get(0));
        System.out.print(ANSI_RESET);
        System.out.println("");

        printFrontResourceCardSecondLine(Res.get(0));
        System.out.print("      ");
        printFrontGoldCardSecondLine(Gold.get(0));
        System.out.print(ANSI_RESET);
        System.out.println("");

        printFrontResourceCardThirdLine(Res.get(0));
        System.out.print("      ");
        printFrontGoldCardThirdLine(Gold.get(0));
        System.out.print(ANSI_RESET);
        System.out.println("");

        printResourceCardJackie(Res.get(0));
        System.out.print("      ");
        printGoldCardJackie(Gold.get(0));
        System.out.print(ANSI_RESET);
        System.out.println("");

    }

}