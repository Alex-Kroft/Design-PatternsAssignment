import java.util.ArrayList;
import java.util.Vector;

public class Utility {

    public Utility() {

    }

    public static ArrayList<Vector<Integer>> getDiagonalMoves() {
        ArrayList<Vector<Integer>> diagonalMoves = new ArrayList<>();
        Vector<Integer> UL = new Vector<>();
        UL.add(-1);
        UL.add(-1);
        diagonalMoves.add(UL);

        Vector<Integer> UR = new Vector<>();
        UR.add(1);
        UR.add(-1);
        diagonalMoves.add(UR);

        Vector<Integer> DL = new Vector<>();
        DL.add(-1);
        DL.add(1);
        diagonalMoves.add(DL);

        Vector<Integer> DR = new Vector<>();
        DR.add(1);
        DR.add(1);
        diagonalMoves.add(DR);

        return diagonalMoves;
    }

    public static PieceColor getTeamColor(PieceColor color) {
        if (color == PieceColor.DARK_RED) {
            color = PieceColor.RED;
        }
        if (color == PieceColor.DARK_GRAY) {
            color = PieceColor.BLACK;
        }
        return color;
    }
}
