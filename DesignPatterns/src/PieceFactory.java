public class PieceFactory {

    public static GamePiece getGamePiece(PieceColor color) {
        return new BasePiece(color);
    }
}
