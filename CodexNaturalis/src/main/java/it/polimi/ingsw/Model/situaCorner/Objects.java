package it.polimi.ingsw.Model.situaCorner;

public enum Objects implements CardRes {
    QUILL,
    INKWELL,
    MANUSCRIPT;

    public static boolean equals(CardRes res, CardRes res1) {
        return res1.equals(res);
    }

    public static int hash(Orientation orient, boolean covered, CardRes res) {
        return Objects.hash(orient, covered, res);
    }
}
