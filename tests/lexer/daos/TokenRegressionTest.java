package tests.lexer.daos;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import lexer.daos.Symbol;
import lexer.daos.Token;
import lexer.daos.TokenKind;

public class TokenRegressionTest {

  @Test
  public void testGetLexeme() {
    String testLexeme = "something";
    Token token = new Token(new Symbol(testLexeme, TokenKind.BogusToken));

    assertEquals(testLexeme, token.getLexeme());
  }

  @Test
  public void testGetKind() {
    Token token = new Token(new Symbol("something", TokenKind.BogusToken));

    assertEquals(TokenKind.BogusToken, token.getKind());
  }

  @Test
  public void testGetLeftColumn() {
    Token token = new Token(new Symbol("something", TokenKind.BogusToken), 7, 42);

    assertEquals(7, token.getLeftColumn());
  }

  @Test
  public void testGetRightColumn() {
    Token token = new Token(new Symbol("something", TokenKind.BogusToken), 7, 42);

    assertEquals(42, token.getRightColumn());
  }
}
