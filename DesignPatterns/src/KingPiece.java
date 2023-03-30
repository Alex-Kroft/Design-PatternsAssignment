import java.util.ArrayList;
import java.util.Vector;

public class KingPiece extends BaseDecorator {

    public KingPiece(BasePiece basePiece) {
        super(basePiece);
        if (basePiece.getColor().equals(PieceColor.BLACK)) {
            basePiece.setColor(PieceColor.DARK_GRAY);
        }
        if (basePiece.getColor().equals(PieceColor.RED)) {
            basePiece.setColor(PieceColor.DARK_RED);
        }
    }

    @Override
    public void move(Vector<Integer> target) {
        getWrappee().setPosition(target);
    }

    @Override
    public void updateTakes(ArrayList<GamePiece> pieces) {
        updateLegalTakeMoves(pieces);
    }

    @Override
    public void updateMoves(ArrayList<GamePiece> pieces) {

    }

    @Override
    public void updateLegalNonTakeMoves(ArrayList<GamePiece> pieces) {

    }

    @Override
    public void updateLegalTakeMoves(ArrayList<GamePiece> pieces) {
        getWrappee().legalTakeMoves.clear();
        getWrappee().legalNonTakeMoves.clear();

        // Calculate the moves that allow a take
        // Add the moves to take pieces diagonally
        ArrayList<Vector<Integer>> diagonalMoves = Utility.getDiagonalMoves();
        for (Vector<Integer> move : diagonalMoves) {
            Vector<Integer> target = new Vector<>();
            target.add(getWrappee().position.get(0));
            target.add(getWrappee().position.get(1));
            target.set(0, target.get(0)+move.get(0));
            target.set(1, target.get(1)+move.get(1));
            boolean pieceInTheWay = false;
            while (target.get(0) >= 0 && target.get(0) <= 7 && target.get(1) >= 0 && target.get(1) <= 7) {
                System.out.println("CHECKING TARGET " + target);

                boolean possible = true;
                boolean blocked = false;
                for (GamePiece piece: pieces) {
                    if (piece.getPosition().equals(target)) {
                        possible = false;
                        System.out.println("THERE IS A PIECE AT THIS TARGET");
                        pieceInTheWay = true;
                        if (Utility.getTeamColor((piece.getColor())) == Utility.getTeamColor(this.getColor())) {
                            blocked = true;
                            System.out.println("THIS PIECE IS FRIENDLY");
                        }
                        break;
                    }
                }
                if (blocked) {
                    System.out.println("BECAUSE OF A FRIENDLY PIECE BEING IN THE WAY, I BREAK OUT OF THE LOOP");
                    break;
                }
                if (possible) {
                    Vector<Integer> place = new Vector<>();
                    place.add(target.get(0));
                    place.add(target.get(1));
                    System.out.println("THIS MOVE IS POSSIBLE");
                    if (pieceInTheWay) {
                        System.out.println("THIS MOVE WILL TAKE AT LEAST ONE ENEMY PIECE");

                        getWrappee().legalTakeMoves.add(place);
                    } else {
                        System.out.println("THIS MOVE WON'T TAKE ANYTHING");
                        getWrappee().legalNonTakeMoves.add(place);
                    }
                }
                target.set(0, target.get(0)+move.get(0));
                target.set(1, target.get(1)+move.get(1));
            }
        }
    }

    @Override
    public Vector<Integer> getPosition() {
        return getWrappee().getPosition();
    }

    @Override
    public void setPosition(Vector<Integer> position) {
        getWrappee().setPosition(position);
    }

    @Override
    public PieceColor getColor() {
        return getWrappee().getColor();
    }

    @Override
    public void setColor(PieceColor color) {
        getWrappee().setColor(color);
    }

    @Override
    public boolean isKing() {
        System.out.println(getWrappee());
        System.out.println(getWrappee().getPosition());
        return true;
    }

    @Override
    public ArrayList<Vector<Integer>> getLegalNonTakeMoves() {
        return getWrappee().getLegalNonTakeMoves();
    }

    @Override
    public ArrayList<Vector<Integer>> getLegalTakeMoves() {
        return getWrappee().getLegalTakeMoves();
    }
}
