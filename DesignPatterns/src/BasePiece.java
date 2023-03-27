public class BasePiece implements GamePiece {
    private Enum<PieceColor> color;
    private int posX;
    private int posY;
    private boolean canMove;
    private boolean canTake;

    public BasePiece(PieceColor color){
        this.color = color;
    }

    public Enum<PieceColor> getColor() {
        return color;
    }

    public void setColor(Enum<PieceColor> color) {
        this.color = color;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean canTake() {
        return canTake;
    }

    public void setCanTake(boolean canTake) {
        this.canTake = canTake;
    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public void checkIfCanMove() {
        setCanMove(false);
    }

    @Override
    public void checkIfCanTake() {
        setCanTake(false);
    }
}
