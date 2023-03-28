import java.util.ArrayList;
import java.util.Vector;

public class BasePiece implements GamePiece {
    private Enum<PieceColor> color;
    private Vector<Integer> position;
    private ArrayList<Vector<Integer>> legalNonTakeMoves;
    private ArrayList<Vector<Integer>> legalTakeMoves;

    public BasePiece(PieceColor color){
        this.color = color;
        this.position = new Vector<>();
        this.position.add(0);
        this.position.add(0);
        legalNonTakeMoves = new ArrayList<>();
        legalTakeMoves = new ArrayList<>();
    }

    public Enum<PieceColor> getColor() {
        return color;
    }

    public void setColor(Enum<PieceColor> color) {
        this.color = color;
    }

    public Vector<Integer> getPosition() {
        return position;
    }

    public void setPosition(Vector<Integer> position) {
        this.position = position;
    }

    public ArrayList<Vector<Integer>> getLegalNonTakeMoves() {
        return legalNonTakeMoves;
    }

    public void setLegalNonTakeMoves(ArrayList<Vector<Integer>> legalNonTakeMoves) {
        this.legalNonTakeMoves = legalNonTakeMoves;
    }

    public ArrayList<Vector<Integer>> getLegalTakeMoves() {
        return legalTakeMoves;
    }

    public void setLegalTakeMoves(ArrayList<Vector<Integer>> legalTakeMoves) {
        this.legalTakeMoves = legalTakeMoves;
    }

    @Override
    public void move(int x, int y) {

    }

    @Override
    public void updateTake() {
        GamePiece.super.updateTake();
    }

    @Override
    public void updateMove() {
        GamePiece.super.updateMove();
    }

    @Override
    public void updateLegalNonTakeMoves() {
        ArrayList<Vector<Integer>> moves = new ArrayList<>();

        //code for finding legal moves

        setLegalNonTakeMoves(moves);
    }

    @Override
    public boolean updateLegalTakeMoves() {
        ArrayList<Vector<Integer>> moves = new ArrayList<>();



        setLegalTakeMoves(moves);
        return !moves.isEmpty();
    }
}
