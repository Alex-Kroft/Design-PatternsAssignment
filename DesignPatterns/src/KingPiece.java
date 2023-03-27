public class KingPiece extends BaseDecorator {
    private BasePiece basePiece;

    public KingPiece(BasePiece basePiece) {
        super(basePiece);
    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public void update() {

    }

    @Override
    public boolean checkIfCanMove() {
        return false;
    }

    @Override
    public boolean checkIfCanTake() {
        return false;
    }
}
