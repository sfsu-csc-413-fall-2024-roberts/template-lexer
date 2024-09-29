package tests.helpers;

import lexer.readers.IReader;

public class TestReader implements IReader {
  private int lineNumber;
  private int column;
  private int index;
  private String source;

  public TestReader(String source) {
    this.source = source;
    this.lineNumber = 0;
    this.column = 0;
    this.index = 0;
  }

  @Override
  public void close() {
    // no-op
  }

  @Override
  public char read() {

    try {
      if (this.source.charAt(index) == '\n') {
        this.column = -1;
        this.lineNumber++;
      }

      this.column++;
      this.index++;

      return this.source.charAt(this.index);
    } catch (IndexOutOfBoundsException exception) {
      return '\0';
    }
  }

  @Override
  public int getColumn() {
    return this.column;
  }

  @Override
  public int getLineNumber() {
    return this.lineNumber;
  }
}
