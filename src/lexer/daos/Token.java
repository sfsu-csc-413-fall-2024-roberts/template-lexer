package lexer.daos;

public class Token {

    private Symbol symbol;
    private int leftColumn;
    private int rightColumn;

    public Token(Symbol symbol) {
        this.symbol = symbol;
    }

    public Token(Symbol symbol, int leftColumn, int rightColumn) {
        this.symbol = symbol;
        this.leftColumn = leftColumn;
        this.rightColumn = rightColumn;
    }

    public String getLexeme() {
        return this.symbol.getLexeme();
    }

    public TokenKind getKind() {
        return this.symbol.getKind();
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getLeftColumn() {
        return leftColumn;
    }

    public int getRightColumn() {
        return rightColumn;
    }

}
