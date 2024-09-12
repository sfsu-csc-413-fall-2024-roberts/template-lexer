package lexer.daos;

public class Token {
    private Symbol symbol;
    private int leftPosition;
    private int rightPosition;

    public Token(Symbol symbol, int leftPosition, int rightPosition) {
        this.symbol = symbol;
        this.leftPosition = leftPosition;
        this.rightPosition = rightPosition;
    }

    public String getLexeme() {
        return this.symbol.getLexeme();
    }

    public TokenKind getKind() {
        return this.symbol.getKind();
    }

    public int getLeftPosition() {
        return this.leftPosition;
    }

    public int getRightPosition() {
        return this.rightPosition;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %s", this.symbol.getLexeme(), this.leftPosition, this.rightPosition,
                this.symbol.getKind());
    }
}