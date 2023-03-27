public class KingPiece extends BaseDecorator {

    public KingPiece(BasePiece basePiece) {
        super(basePiece);
    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public void checkIfCanMove() {
        getWrappee().setCanMove(false);
    }

    @Override
    public void checkIfCanTake() {
        getWrappee().setCanTake(false);
    }
}
