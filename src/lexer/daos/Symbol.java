package lexer.daos;

/**
 * The symbol keeps track of a lexeme and its token kind
 */
public class Symbol {

    private String lexeme;
    private TokenKind kind;

    public Symbol(String lexeme, TokenKind kind) {
        this.lexeme = lexeme;
        this.kind = kind;
    }

    public String getLexeme() {
        return lexeme;
    }

    public TokenKind getKind() {
        return kind;
    }
}
