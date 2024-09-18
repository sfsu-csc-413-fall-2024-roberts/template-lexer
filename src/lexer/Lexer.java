package lexer;

import java.io.FileNotFoundException;

import lexer.daos.Symbol;
import lexer.daos.SymbolTable;
import lexer.daos.Token;
import lexer.daos.TokenKind;
import lexer.readers.IReader;
import lexer.readers.SourceFileReader;

public class Lexer implements ILexer {

  private IReader reader;
  private char currentCharacter;
  private int startPosition;
  private int endPosition;

  public Lexer(String filePath) throws FileNotFoundException, LexicalException {
    this(new SourceFileReader(filePath));
  }

  public Lexer(IReader reader) throws LexicalException {
    this.reader = reader;

    this.currentCharacter = this.reader.read();
    this.startPosition = this.endPosition = this.reader.getColumn();
  }

  @Override
  public Token nextToken() throws LexicalException {
    ignoreWhitespace();

    this.startPosition = this.endPosition = this.reader.getColumn();

    if (this.currentCharacter == '\0') {
      return new Token(new Symbol("\0", TokenKind.EOF), this.startPosition, this.endPosition);
    }

    if (Character.isDigit(this.currentCharacter)) {
      return number();
    }

    if (Character.isJavaIdentifierStart(this.currentCharacter)) {
      return identifierOrKeyword();
    }

    return operatorOrSeparator();
  }

  private void advance() throws LexicalException {
    if (!atEof()) {
      this.currentCharacter = this.reader.read();
      this.endPosition++;
    }
  }

  private boolean atEof() {
    return this.currentCharacter == '\0';
  }

  private void ignoreWhitespace() throws LexicalException {
    while (!this.atEof() && Character.isWhitespace(this.currentCharacter)) {
      advance();
    }
  }

  private Token number() throws LexicalException {
    String lexeme = "";

    while (!this.atEof() && Character.isDigit(currentCharacter)) {
      lexeme += this.currentCharacter;

      this.advance();
    }

    return new Token(new Symbol(lexeme, TokenKind.IntLit), this.startPosition,
        this.endPosition - 1);
  }

  private Token identifierOrKeyword() throws LexicalException {
    String lexeme = "";

    do {
      lexeme += this.currentCharacter;
      this.advance();
    } while (!this.atEof() && Character.isJavaIdentifierPart(this.currentCharacter));

    Symbol symbol = SymbolTable.symbol(lexeme, TokenKind.Identifier);

    return new Token(symbol, this.startPosition, this.endPosition - 1);
  }

  private Token operatorOrSeparator() throws LexicalException {
    String possibleSingle = "" + this.currentCharacter;

    try {
      this.advance();
    } catch (LexicalException le) {
      return singleCharacterOperatorOrSeparator(possibleSingle);
    }

    String doubleCharacter = possibleSingle + this.currentCharacter;

    Symbol symbol = SymbolTable.symbol(doubleCharacter, TokenKind.BogusToken);

    if (symbol == null) {
      return singleCharacterOperatorOrSeparator(possibleSingle);
    } else if (symbol.getKind() == TokenKind.Comment) {
      ignoreComment();

      return nextToken();
    } else {
      // Double character operator or separator (the symbol was found)
      advance();

      return new Token(symbol, this.startPosition, this.endPosition - 1);
    }
  }

  private Token singleCharacterOperatorOrSeparator(String lexeme) throws LexicalException {
    Symbol symbol = SymbolTable.symbol(lexeme, TokenKind.BogusToken);

    if (symbol == null) {
      throw new LexicalException("Invalid character: " + lexeme);
    }

    return new Token(symbol, this.startPosition, this.endPosition - 1);
  }

  private void ignoreComment() throws LexicalException {
    int currentLineNumber = this.reader.getLineNumber();

    while (!atEof() && this.reader.getLineNumber() == currentLineNumber) {
      this.advance();
    }
  }

  @Override
  public Token anonymousIdentifierToken(String identifier) {
    return new Token(new Symbol(identifier, TokenKind.Identifier), -1, -1);
  }

  @Override
  public void close() throws Exception {
    this.reader.close();
  }

  public static void main(String[] args) {
    try (ILexer lexer = new Lexer("sample-files/simple-program.x")) {
      Token token = lexer.nextToken();

      while (token.getKind() != TokenKind.EOF) {
        System.out.println(token);
        token = lexer.nextToken();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
