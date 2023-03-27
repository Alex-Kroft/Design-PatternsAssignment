public class BasePiece implements GamePiece {
    private Enum<PieceColor> color;
    private int posX;
    private int posY;
    private boolean canMove;
    private boolean canTake;

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
