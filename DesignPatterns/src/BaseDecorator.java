public abstract class BaseDecorator implements GamePiece {
    private BasePiece wrappee;

    public BaseDecorator(BasePiece basePiece) {

    }


    public abstract boolean checkIfCanMove();

    public abstract boolean checkIfCanTake();
}
