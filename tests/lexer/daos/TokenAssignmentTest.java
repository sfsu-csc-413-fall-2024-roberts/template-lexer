package tests.lexer.daos;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import lexer.daos.Symbol;
import lexer.daos.Token;
import lexer.daos.TokenKind;

public class TokenAssignmentTest {
  @Test
  public void testLineNumberWithoutLine() {
    Token token = new Token(new Symbol("something", TokenKind.BogusToken), 7, 42);

    assertEquals(-1, token.getLineNumber());
  }

  @Test
  public void testLineNumberConstructor() {
    Token token = new Token(new Symbol("something", TokenKind.BogusToken), 7, 42, 97);

    assertEquals(97, token.getLineNumber());
  }

  @Test
  public void testTokenToString() {
    Token token = new Token(new Symbol("something", TokenKind.BogusToken), 7, 42, 97);

    String expected = String.format("%-20s left: %-8d right: %-8d line: %-8d %s", "something", 7,
        42, 97, TokenKind.BogusToken);

    assertEquals(expected, token.toString());
  }
}
