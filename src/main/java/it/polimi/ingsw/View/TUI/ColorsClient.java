package it.polimi.ingsw.View.TUI;

public enum ColorsClient {

    RESET("\u001B[0m"),
    BLACK("\u001b[30m"),
    YELLOW("\u001b[33m"),
    BLUE("\u001b[34m"),
    RED("\u001b[31m"),
    PURPLE("\\u001b[127m"),
    CYAN("\u001b[36m"),
    GREEN("\u001B[32m"),
    GOLDISH("\u001b[220m"),
    WHITE("\u001b[37m"),
    WHITE_BACKGROUND("\u001B[47m");

    private final String color;

    ColorsClient(String color) {
        this.color = color;
    }

    public String toString() {
        return color;
    }
}
