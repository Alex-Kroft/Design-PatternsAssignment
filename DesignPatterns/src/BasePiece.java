import java.util.ArrayList;
import java.util.Vector;

public class BasePiece implements GamePiece {
    private Enum<PieceColor> color;
    private Vector position;
    private ArrayList<Vector> legalNonTakeMoves;
    private ArrayList<Vector> legalTakeMoves;

    public BasePiece(PieceColor color){
        this.color = color;
        legalNonTakeMoves = new ArrayList<>();
        legalTakeMoves = new ArrayList<>();
    }

    public Enum<PieceColor> getColor() {
        return color;
    }

    public void setColor(Enum<PieceColor> color) {
        this.color = color;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public ArrayList<Vector> getLegalNonTakeMoves() {
        return legalNonTakeMoves;
    }

    public void setLegalNonTakeMoves(ArrayList<Vector> legalNonTakeMoves) {
        this.legalNonTakeMoves = legalNonTakeMoves;
    }

    public ArrayList<Vector> getLegalTakeMoves() {
        return legalTakeMoves;
    }

    public void setLegalTakeMoves(ArrayList<Vector> legalTakeMoves) {
        this.legalTakeMoves = legalTakeMoves;
    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public void updateLegalNonTakeMoves() {
        ArrayList<Vector> moves = new ArrayList<>();

        //code for finding legal moves

        setLegalNonTakeMoves(moves);
    }

    @Override
    public boolean updateLegalTakeMoves() {
        ArrayList<Vector> moves = new ArrayList<>();



        setLegalTakeMoves(moves);
        return !moves.isEmpty();
    }
}
