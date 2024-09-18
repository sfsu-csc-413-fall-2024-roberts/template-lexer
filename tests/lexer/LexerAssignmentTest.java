package tests.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lexer.Lexer;
import lexer.LexicalException;
import lexer.daos.TokenKind;
import lexer.daos.Token;
import tests.helpers.TestReader;

public class LexerAssignmentTest {

  @ParameterizedTest
  @MethodSource("provideAssignmentOperators")
  public void testAssignmentOperators(String source, TokenKind kind, int expectedStart,
      int expectedEnd) throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {
      Token token = lexer.nextToken();

      assertEquals(source.trim(), token.getLexeme());
      assertEquals(kind, token.getKind());
      assertEquals(expectedStart, token.getLeftColumn());
      assertEquals(expectedEnd, token.getRightColumn());
    }
  }

  @ParameterizedTest
  @MethodSource("provideAssignmentKeywords")
  public void testAssignmentKeywords(String source, TokenKind kind, int expectedStart,
      int expectedEnd) throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {
      Token token = lexer.nextToken();

      assertEquals(source.trim(), token.getLexeme());
      assertEquals(kind, token.getKind());
      assertEquals(expectedStart, token.getLeftColumn());
      assertEquals(expectedEnd, token.getRightColumn());
    }
  }

  @ParameterizedTest
  @MethodSource("provideValidOctals")
  public void testValidOctals(String source, TokenKind kind, int expectedStart, int expectedEnd)
      throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {
      Token token = lexer.nextToken();

      assertEquals(source.trim(), token.getLexeme());
      assertEquals(kind, token.getKind());
      assertEquals(expectedStart, token.getLeftColumn());
      assertEquals(expectedEnd, token.getRightColumn());
    }
  }

  @ParameterizedTest
  @MethodSource("provideInvalidOctals")
  public void testInvalidOctals(String source, int expectedLine, int expectedColumn)
      throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {

      Exception exception = assertThrows(LexicalException.class, () -> {
        lexer.nextToken();
      });

      String expectedMessage =
          String.format("Lexical error on line %d, column %d: Invalid octal literal", expectedLine,
              expectedColumn);
      String actualMessage = exception.getMessage();

      assertEquals(expectedMessage, actualMessage);
    }
  }

  @ParameterizedTest
  @MethodSource("provideValidStrings")
  public void testValidStrings(String source, TokenKind kind, int expectedStart, int expectedEnd,
      int expectedLine) throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {
      Token token = lexer.nextToken();

      assertEquals(source.trim(), token.getLexeme());
      assertEquals(kind, token.getKind());
      assertEquals(expectedStart, token.getLeftColumn());
      assertEquals(expectedEnd, token.getRightColumn());
      assertEquals(expectedLine, token.getLineNumber());
    }
  }

  @ParameterizedTest
  @MethodSource("provideInvalidStrings")
  public void testInvalidStrings(String source, int expectedLine, int expectedColumn)
      throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {

      Exception exception = assertThrows(LexicalException.class, () -> {
        lexer.nextToken();
      });

      String expectedMessage =
          String.format("Lexical error on line %d, column %d: Unterminated string literal",
              expectedLine, expectedColumn);
      String actualMessage = exception.getMessage();

      assertEquals(expectedMessage, actualMessage);
    }
  }

  private static Stream<Arguments> provideAssignmentOperators() {
    return Stream.of(Arguments.of(" > ", TokenKind.Greater, 1, 1),
        Arguments.of(" >= ", TokenKind.GreaterEqual, 1, 2),
        Arguments.of(" ^ ", TokenKind.Power, 1, 1));
  }

  private static Stream<Arguments> provideAssignmentKeywords() {
    return Stream.of(Arguments.of(" string ", TokenKind.StringType, 1, 6),
        Arguments.of(" octal ", TokenKind.OctalType, 1, 5),
        Arguments.of(" choose ", TokenKind.Choose, 1, 6),
        Arguments.of(" option ", TokenKind.Option, 1, 6),
        Arguments.of(" break ", TokenKind.Break, 1, 5));
  }

  private static Stream<Arguments> provideValidOctals() {
    return Stream.of(Arguments.of(" @0 ", TokenKind.OctalLiteral, 1, 2),
        Arguments.of(" @1234567 ", TokenKind.OctalLiteral, 1, 8),
        Arguments.of(" @777 ", TokenKind.OctalLiteral, 1, 4));
  }

  private static Stream<Arguments> provideInvalidOctals() {
    return Stream.of(Arguments.of(" @8 ", 0, 2), Arguments.of(" @1234568 ", 0, 8),
        Arguments.of(" @778 ", 0, 4));
  }

  private static Stream<Arguments> provideValidStrings() {
    return Stream.of(Arguments.of(" 'im valid' ", TokenKind.StringLiteral, 1, 10, 0),
        Arguments.of(" 'im\nvalid' ", TokenKind.StringLiteral, 1, 5, 0),
        Arguments.of(" 'im\nvalid\n' ", TokenKind.StringLiteral, 1, 0, 0),
        Arguments.of(" \n\n'im\nvalid\n' ", TokenKind.StringLiteral, 1, 0, 2));
  }

  private static Stream<Arguments> provideInvalidStrings() {
    return Stream.of(Arguments.of(" 'im invalid ", 0, 13), Arguments.of(" 'im\ninvalid ", 1, 8));
  }
}
