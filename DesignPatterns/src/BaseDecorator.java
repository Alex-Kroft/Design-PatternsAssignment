public abstract class BaseDecorator implements GamePiece {
    private final BasePiece wrappee;

    public BaseDecorator(BasePiece basePiece) {
        wrappee = basePiece;
    }

    public BasePiece getWrappee() {
        return wrappee;
    }

    public abstract boolean isKing();
}
