import java.util.ArrayList;
import java.util.Vector;

public class BasePiece implements GamePiece {
    private Enum<PieceColor> color;
    private Vector<Integer> position;
    private ArrayList<Vector<Integer>> legalNonTakeMoves;
    private ArrayList<Vector<Integer>> legalTakeMoves;


    public BasePiece(PieceColor color){
        this.color = color;
        this.position = new Vector<>();
        this.position.add(0);
        this.position.add(0);
        legalNonTakeMoves = new ArrayList<>();
        legalTakeMoves = new ArrayList<>();
    }

    public Enum<PieceColor> getColor() {
        return color;
    }

    public void setColor(Enum<PieceColor> color) {
        this.color = color;
    }

    public Vector<Integer> getPosition() {
        return position;
    }

    public void setPosition(Vector<Integer> position) {
        this.position = position;
    }

    public ArrayList<Vector<Integer>> getLegalNonTakeMoves() {
        return legalNonTakeMoves;
    }

    public void setLegalNonTakeMoves(ArrayList<Vector<Integer>> legalNonTakeMoves) {
        this.legalNonTakeMoves = legalNonTakeMoves;
    }

    public ArrayList<Vector<Integer>> getLegalTakeMoves() {
        return legalTakeMoves;
    }

    public void setLegalTakeMoves(ArrayList<Vector<Integer>> legalTakeMoves) {
        this.legalTakeMoves = legalTakeMoves;
    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public boolean updateTakes(ArrayList<GamePiece> pieces) {
        return updateLegalTakeMoves(pieces);
    }

    @Override
    public void updateMoves(ArrayList<GamePiece> pieces) {
        updateLegalNonTakeMoves(pieces);
    }

    @Override
    public void updateLegalNonTakeMoves(ArrayList<GamePiece> pieces) {
        legalNonTakeMoves.clear();

        // Calculate the moves that allow a take
        // Add the moves to take pieces diagonally
        ArrayList<Vector<Integer>> diagonalMoves = new ArrayList<>();
        if (this.getColor() == PieceColor.BLACK) {
            Vector<Integer> UL = new Vector<>();
            UL.add(-1);
            UL.add(-1);
            diagonalMoves.add(UL);

            Vector<Integer> DL = new Vector<>();
            DL.add(1);
            DL.add(-1);
            diagonalMoves.add(DL);
        } else {
            Vector<Integer> UR = new Vector<>();
            UR.add(-1);
            UR.add(1);
            diagonalMoves.add(UR);

            Vector<Integer> DR = new Vector<>();
            DR.add(1);
            DR.add(1);
            diagonalMoves.add(DR);
        }


        for (Vector<Integer> move : diagonalMoves) {
            Vector<Integer> target = position;

            target.set(0, position.get(0)+move.get(0));
            target.set(0, position.get(1)+move.get(1));

            boolean possible = true;
            if (target.get(0) >= 0 && target.get(0) <= 7 && target.get(1) >= 0 && target.get(1) <= 7) {
                for (GamePiece piece: pieces) {
                    if (piece.getPosition().equals(target)) {
                        possible = false;
                        break;
                    }
                }
            }
            if (possible) {
                legalNonTakeMoves.add(target);
            }
        }
    }

    @Override
    public boolean updateLegalTakeMoves(ArrayList<GamePiece> pieces) {
        legalTakeMoves.clear();

        // Calculate the moves that allow a take
        // Add the moves to take pieces diagonally
        ArrayList<Vector<Integer>> diagonalMoves = new ArrayList<>();

        Vector<Integer> UL = new Vector<>();
        UL.add(-1);
        UL.add(-1);
        diagonalMoves.add(UL);

        Vector<Integer> UR = new Vector<>();
        UR.add(-1);
        UR.add(1);
        diagonalMoves.add(UR);

        Vector<Integer> DL = new Vector<>();
        DL.add(1);
        DL.add(-1);
        diagonalMoves.add(DL);

        Vector<Integer> DR = new Vector<>();
        DR.add(1);
        DR.add(1);
        diagonalMoves.add(DR);

        for (Vector<Integer> move : diagonalMoves) {
            Vector<Integer> target = position;
            Vector<Integer> toBeTakenPosition = position;

            toBeTakenPosition.set(0, position.get(0)+move.get(0));
            toBeTakenPosition.set(0, position.get(1)+move.get(1));
            target.set(0, position.get(0)+move.get(0)*2);
            target.set(0, position.get(1)+move.get(1)*2);
            if (target.get(0) >= 0 && target.get(0) <= 7 && target.get(1) >= 0 && target.get(1) <= 7) {
                for (GamePiece piece: pieces
                     ) {
                    if (piece.getPosition().equals(toBeTakenPosition) && piece.getColor() != this.getColor()) {
                        legalTakeMoves.add(target);
                        break;
                    }
                }
            }
        }

        return !legalTakeMoves.isEmpty();
    }
}
