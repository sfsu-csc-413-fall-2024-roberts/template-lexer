package lexer.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import lexer.LexicalException;

public class SourceFileReader implements IReader {

  private BufferedReader reader;

  private int currentLineNumber;
  private int currentColumn;
  private char lastCharacterRead;

  public SourceFileReader(String fileName) throws FileNotFoundException, LexicalException {
    this(new BufferedReader(new FileReader(fileName)));
  }

  public SourceFileReader(BufferedReader reader) throws LexicalException {
    this.reader = reader;

    this.currentLineNumber = 0;
    this.currentColumn = -1;
    this.lastCharacterRead = ' ';
  }

  @Override
  public void close() throws Exception {
    this.reader.close();
  }

  @Override
  public char read() throws LexicalException {
    if (this.lastCharacterRead == '\n') {
      this.currentLineNumber++;
      this.currentColumn = -1;
    }

    this.lastCharacterRead = this.getNextCharacter();
    this.currentColumn++;

    return this.lastCharacterRead;
  }

  private char getNextCharacter() throws LexicalException {
    int next;

    try {
      next = this.reader.read();

      if (next == -1) {
        return '\0';
      }
    } catch (IOException e) {
      throw new LexicalException(String.format("Error reading file (column: %d, line: %d)",
          this.currentColumn, this.currentLineNumber));
    }

    return (char) next;
  }

  @Override
  public int getColumn() {
    return this.currentColumn;
  }

  @Override
  public int getLineNumber() {
    return this.currentLineNumber;
  }
}
