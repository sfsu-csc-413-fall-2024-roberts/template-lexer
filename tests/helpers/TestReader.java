package tests.helpers;

import java.util.List;
import java.util.stream.Collectors;

import lexer.readers.IReader;

public class TestReader implements IReader {
  private int lineNumber;
  private int column;
  private int index;
  private String source;
  private char lastRead;

  public TestReader(List<String> sourceLines) {
    this.source = sourceLines.stream().collect(Collectors.joining("\n"));

    this.lineNumber = -1;
    this.column = -1;
    this.index = -1;
    this.lastRead = '\n';
  }

  @Override
  public void close() {
    // no-op
  }

  @Override
  public char read() {
    if (this.lastRead == '\n') {
      this.column = -1;
      this.lineNumber++;
    }
    this.column++;
    this.index++;

    char c = '\0';

    try {
      return this.source.charAt(this.index);
    } catch (IndexOutOfBoundsException exception) {
      return c;
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
