import java.util.ArrayList;
import java.util.Vector;

public class KingPiece extends BaseDecorator {

    public KingPiece(BasePiece basePiece) {
        super(basePiece);
    }

    @Override
    public void move(Vector<Integer> target) {

    }

    @Override
    public void updateLegalNonTakeMoves(ArrayList<GamePiece> pieces) {
        ArrayList<Vector<Integer>> moves = new ArrayList<>();

        //code for finding legal moves

        getWrappee().setLegalNonTakeMoves(moves);
    }

    @Override
    public boolean updateLegalTakeMoves(ArrayList<GamePiece> pieces) {
        ArrayList<Vector<Integer>> moves = new ArrayList<>();

        //code for finding legal moves

        getWrappee().setLegalTakeMoves(moves);
        return !moves.isEmpty();
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
    public Enum<PieceColor> getColor() {
        return getWrappee().getColor();
    }

    @Override
    public ArrayList<Vector<Integer>> getLegalNonTakeMoves() {
        return null;
        //TODO !!!!!!!!!
    }

    @Override
    public ArrayList<Vector<Integer>> getLegalTakeMoves() {
        return null;
        //TODO !!!!!!!!!
    }
}
