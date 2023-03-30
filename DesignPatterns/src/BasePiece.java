import java.util.ArrayList;
import java.util.Vector;

public class BasePiece implements GamePiece {
    protected PieceColor color;
    protected Vector<Integer> position;
    protected ArrayList<Vector<Integer>> legalNonTakeMoves;
    protected ArrayList<Vector<Integer>> legalTakeMoves;


    public BasePiece(PieceColor color){
        this.color = color;
        this.position = new Vector<>();
        this.position.add(0);
        this.position.add(0);
        legalNonTakeMoves = new ArrayList<>();
        legalTakeMoves = new ArrayList<>();
    }

    public PieceColor getColor() {
        return color;
    }

    public void setColor(PieceColor color) {
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
    public void updateTakes(ArrayList<GamePiece> pieces) {
        updateLegalTakeMoves(pieces);
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
        ArrayList<Vector<Integer>> diagonalMoves = Utility.getDiagonalMoves();
        if (this.getColor() == PieceColor.BLACK) {
            diagonalMoves.remove(0);
            diagonalMoves.remove(0);
        } else {
            diagonalMoves.remove(2);
            diagonalMoves.remove(2);
        }

        for (Vector<Integer> move : diagonalMoves) {
            Vector<Integer> target = new Vector<>();
            int targetX = position.get(0)+move.get(0);
            int targetY = position.get(1)+move.get(1);

            target.add(targetX);
            target.add(targetY);

            boolean possible = false;
            if (target.get(0) >= 0 && target.get(0) <= 7 && target.get(1) >= 0 && target.get(1) <= 7) {
                possible = true;
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
    public void updateLegalTakeMoves(ArrayList<GamePiece> pieces) {
        legalTakeMoves.clear();
        legalNonTakeMoves.clear();

        // Calculate the moves that allow a take
        // Add the moves to take pieces diagonally
        ArrayList<Vector<Integer>> diagonalMoves = Utility.getDiagonalMoves();
        for (Vector<Integer> move : diagonalMoves) {
            Vector<Integer> target = new Vector<>();
            target.add(position.get(0));
            target.add(position.get(1));
            target.set(0, position.get(0)+(move.get(0)*2));
            target.set(1, position.get(1)+(move.get(1)*2));


            Vector<Integer> toBeTakenPosition = new Vector<>();
            toBeTakenPosition.add(position.get(0));
            toBeTakenPosition.add(position.get(1));
            toBeTakenPosition.set(0, position.get(0)+move.get(0));
            toBeTakenPosition.set(1, position.get(1)+move.get(1));

            boolean takeable = false;
            boolean blocked = false;
            if (target.get(0) >= 0 && target.get(0) <= 7 && target.get(1) >= 0 && target.get(1) <= 7) {
                for (GamePiece piece: pieces) {
                    PieceColor thisColor = Utility.getTeamColor(this.getColor());
                    PieceColor targetColor = Utility.getTeamColor(piece.getColor());

                    if (piece.getPosition().equals(toBeTakenPosition) && thisColor != targetColor) {
                        takeable = true;
                    }
                    if (piece.getPosition().equals(target)) {
                        blocked = true;
                        break;
                    }
                }
                if (takeable && !blocked) {
                    legalTakeMoves.add(target);
                }
            }
        }
    }

    @Override
    public void move(Vector<Integer> target) {
        setPosition(target);
    }
}
