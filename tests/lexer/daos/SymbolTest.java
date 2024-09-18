package tests.lexer.daos;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import lexer.daos.Symbol;
import lexer.daos.TokenKind;

public class SymbolTest {

    @Test
    public void testGetLexeme() {
        Symbol symbol = new Symbol("testLexeme", TokenKind.BogusToken);

        assertEquals("testLexeme", symbol.getLexeme());
    }

    @Test
    public void testGetKind() {
        Symbol symbol = new Symbol("testKind", TokenKind.BogusToken);

        assertEquals(TokenKind.BogusToken, symbol.getKind());
    }
}
