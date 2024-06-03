package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.GoalCard;
import it.polimi.ingsw.Model.CardPackage.GoalCardPackage.ObjectsGoalCard;
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
import it.polimi.ingsw.Model.PlayerPackage.FB;
import it.polimi.ingsw.Model.PlayerPackage.Player;
import it.polimi.ingsw.Model.PlayerPackage.PlayingField;
import it.polimi.ingsw.Model.PlayerPackage.Position;

import java.security.interfaces.RSAKey;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.List;

import static it.polimi.ingsw.Model.CornerPackage.Resources.*;

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
        System.out.print("———————————————");
        System.out.println(ANSI_RESET);
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
            System.out.print((ANIMAL_KINGDOM.getShortName()));
            ;
        }
        if (BackRes.contains(PLANT_KINGDOM)) {
            System.out.print((PLANT_KINGDOM.getShortName()));
        }
        if (BackRes.contains(FUNGI_KINGDOM)) {
            System.out.print((FUNGI_KINGDOM.getShortName()));
        }
        if (BackRes.contains(INSECT_KINGDOM)) {
            System.out.print((INSECT_KINGDOM.getShortName()));
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

        System.out.print("———————————————");
        System.out.print(ANSI_RESET);
    }

    public void printResourceCardJackie(ResourceCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.println(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.println(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.println(ANSI_RED);
        System.out.print("———————————————");
    }

    public void printFrontResourceCardFirstLine(ResourceCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.println(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.println(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.println(ANSI_RED);

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
            System.out.println(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.println(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.println(ANSI_RED);

        System.out.print("|             |");
    }

    public void printFrontResourceCardThirdLine(ResourceCard card) {
        if (card.getColor().equals(CardColor.GREEN)) {
            System.out.print(ANSI_GREEN);
        } else if (card.getColor().equals(CardColor.PURPLE)) {
            System.out.println(ANSI_PURPLE);
        } else if (card.getColor().equals(CardColor.BLUE)) {
            System.out.println(ANSI_BLUE);
        } else if (card.getColor().equals(CardColor.RED))
            System.out.println(ANSI_RED);

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
            case GREEN -> System.out.print((PLANT_KINGDOM.getShortName()));
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
            case GREEN -> System.out.print((PLANT_KINGDOM.getShortName()));
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

        System.out.print("———————————————");
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
                    System.out.print((PLANT_KINGDOM.getShortName()));
                } else if (i == 1) {
                    System.out.print((ANIMAL_KINGDOM.getShortName()));
                } else if (i == 2) {
                    System.out.print((FUNGI_KINGDOM.getShortName()));
                } else {
                    System.out.print((INSECT_KINGDOM.getShortName()));
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
            case GREEN -> System.out.print((PLANT_KINGDOM.getShortName()));
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
            case GREEN -> System.out.print((PLANT_KINGDOM.getShortName()));
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
            System.out.print(ANSI_RESET);
        }
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
 //       if (c.getCovered()) {
            sb.append("❌");
  /*      } else {
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
            }*/
            System.out.print(sb.toString());
        }

    //----------------------------------------------PLOTTING------------------------------------------------------------

    public void plotPlayingField(Player p){
        PlayingField Field = p.getPlayerField();
        int maxX = Field.getField().keySet().stream().mapToInt(Position::getX).max().orElse(400);
        int minX = Field.getField().keySet().stream().mapToInt(Position::getX).min().orElse(400);
        int maxY = Field.getField().keySet().stream().mapToInt(Position::getY).max().orElse(400);
        int minY = Field.getField().keySet().stream().mapToInt(Position::getY).min().orElse(400);


        for(int j = maxY; j >= minY; j--){
            for(int i = minX; i <= maxX; i++){

                Position tocheck = new Position(j,i);
                PlayableCard card = Field.getField().get(tocheck);

                int Carlos = 0;
                while(Carlos < 5) {
                    if (card != null) {
                        int finalI = i;
                        int finalJ = j;
                        List<Position> positions = Field.getField().keySet().stream().filter(s -> s.getX()== finalI && s.getY()== finalJ).toList();
                        Position position = positions.getFirst();
                        FB roba = position.getFace();
                        if (card.getId() >= 1 && card.getId() <= 40) {
                            ResourceCard c = (ResourceCard) card;
                            if(roba== FRONT){
                                switch (Carlos){
                                    case 0, 4 -> printResourceCardJackie(c);
                                    case 1 -> printFrontResourceCardFirstLine(c);
                                    case 2 -> printFrontResourceCardSecondLine(c);
                                    case 3 -> printFrontResourceCardThirdLine(c);
                                }
                            }
                            else if(roba == BACK){
                                switch (Carlos){
                                    case 0, 4 -> printResourceCardJackie(c);
                                    case 1 -> printBackResourceCardFirstLine(c);
                                    case 2 -> printBackResourceCardSecondLine(c);
                                    case 3 -> printBackResourceCardThirdLine(c);
                                }
                            }
                        } else if(card.getId() >= 41 && card.getId() <= 81) {
                            GoldCard c = (GoldCard) card;
                            if(roba== FRONT){
                                switch (Carlos){
                                    case 0, 4 -> printGoldCardJackie(c);
                                    case 1 -> printFrontGoldCardFirstLine(c);
                                    case 2 -> printFrontGoldCardSecondLine(c);
                                    case 3 -> printFrontGoldCardThirdLine(c);
                                }
                            }
                            else if(roba== BACK){
                                switch (Carlos){
                                    case 0, 4 -> printGoldCardJackie(c);
                                    case 1 -> printBackGoldCardFirstLine(c);
                                    case 2 -> printBackGoldCardSecondLine(c);
                                    case 3 -> printBackGoldCardThirdLine(c);
                                }
                            }
                        } else {
                            StartCard c = (StartCard) card;
                            if(roba== FRONT){
                                switch (Carlos){
                                    case 0, 4 -> printStartCardJackie(c);
                                    case 1 -> printFrontStartCardFirstLine(c);
                                    case 2 -> printFrontStartCardSecondLine(c);
                                    case 3 -> printFrontStartCardThirdLine(c);
                                }
                            }
                            else if(roba== BACK){
                                switch (Carlos){
                                    case 0, 4 -> printStartCardJackie(c);
                                    case 1 -> printBackStartCardFirstLine(c);
                                    case 2 -> printBackStartCardSecondLine(c);
                                    case 3 -> printBackStartCardThirdLine(c);
                                }
                            }
                        }

                    } else {

                    }
                    System.out.println("\n");
                    Carlos++;
                }
            }

        }
    }
}

