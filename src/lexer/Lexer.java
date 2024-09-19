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
  private int leftColumn;
  private int rightColumn;

  public Lexer(String filePath) throws FileNotFoundException, LexicalException {
    this(new SourceFileReader(filePath));
  }

  public Lexer(IReader reader) throws LexicalException {
    this.reader = reader;
    this.currentCharacter = '\0';
    this.leftColumn = 0;
    this.rightColumn = -1;

    this.advance();
  }

  private void advance() throws LexicalException {
    this.currentCharacter = this.reader.read();
    this.rightColumn = this.reader.getColumn();
  }

  private boolean isAtEndOfFile() {
    return this.currentCharacter == '\0';
  }

  @Override
  public Token nextToken() throws LexicalException {
    this.ignoreWhiteSpace();

    if (isAtEndOfFile()) {
      return new Token(new Symbol("\0", TokenKind.EOF), this.rightColumn, this.rightColumn);
    }

    this.startToken();

    if (Character.isDigit(this.currentCharacter)) {
      return this.integerLiteral();
    }

    if (Character.isJavaIdentifierStart(this.currentCharacter)) {
      return this.identifierOrKeyword();
    }

    return this.operatorOrSeparator();
  }

  private void ignoreWhiteSpace() throws LexicalException {
    while (!isAtEndOfFile() && Character.isWhitespace(this.currentCharacter)) {
      this.advance();
    }
  }

  private void startToken() {
    this.leftColumn = this.rightColumn = this.reader.getColumn();
  }

  private Token integerLiteral() throws LexicalException {
    String lexeme = "";

    while (!isAtEndOfFile() && Character.isDigit(this.currentCharacter)) {
      lexeme += this.currentCharacter;
      this.advance();
    }

    return new Token(SymbolTable.symbol(lexeme, TokenKind.IntLit), this.leftColumn,
        this.rightColumn - 1);
  }

  private Token identifierOrKeyword() throws LexicalException {
    String lexeme = "";

    while (!isAtEndOfFile() && Character.isJavaIdentifierPart(this.currentCharacter)) {
      lexeme += this.currentCharacter;
      this.advance();
    }

    return new Token(SymbolTable.symbol(lexeme, TokenKind.Identifier), this.leftColumn,
        this.rightColumn - 1);
  }

  private Token operatorOrSeparator() throws LexicalException {
    String singleCharacter = String.valueOf(this.currentCharacter);

    try {
      this.advance();
    } catch (LexicalException e) {
      return this.singleCharacterOperator(singleCharacter);
    }

    String doubleCharacter = singleCharacter + this.currentCharacter;
    Symbol possibleSymbol = SymbolTable.symbol(doubleCharacter, TokenKind.BogusToken);

    if (possibleSymbol == null) {
      return this.singleCharacterOperator(singleCharacter);
    } else if (possibleSymbol.getKind() == TokenKind.Comment) {
      return this.comment();
    } else {
      this.advance();
      return new Token(possibleSymbol, this.leftColumn, this.rightColumn - 1);
    }
  }

  private Token singleCharacterOperator(String lexeme) {
    Symbol symbol = SymbolTable.symbol(lexeme, TokenKind.BogusToken);

    return new Token(symbol, this.leftColumn, this.rightColumn - 1);
  }

  private Token comment() throws LexicalException {
    while (!isAtEndOfFile() && this.currentCharacter != '\n') {
      this.advance();
    }

    return this.nextToken();
  }

  @Override
  public void close() throws Exception {
    this.reader.close();
  }

  @Override
  public Token anonymousIdentifierToken(String identifier) {
    return new Token(new Symbol(identifier, TokenKind.Identifier), -1, -1);
  }

  public static void main(String[] args) throws Exception {
    try (Lexer lexer = new Lexer("sample-files/simple-program.x")) {
      Token token = lexer.nextToken();

      while (token.getKind() != TokenKind.EOF) {
        System.out.println(token);
        token = lexer.nextToken();
      }
    }
  }
}
