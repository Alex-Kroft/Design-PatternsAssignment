import java.util.ArrayList;
import java.util.Vector;

public class KingPiece extends BaseDecorator {

    public KingPiece(BasePiece basePiece) {
        super(basePiece);
    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public void updateLegalNonTakeMoves() {
        ArrayList<Vector> moves = new ArrayList<>();

        //code for finding legal moves

        getWrappee().setLegalNonTakeMoves(moves);
    }

    @Override
    public boolean updateLegalTakeMoves() {
        ArrayList<Vector> moves = new ArrayList<>();

        //code for finding legal moves

        getWrappee().setLegalTakeMoves(moves);
        return !moves.isEmpty();
    }

    @Override
    public Vector getPosition() {
        return getWrappee().getPosition();
    }
}
