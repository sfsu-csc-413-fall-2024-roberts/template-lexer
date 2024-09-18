package tests.lexer.readers;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import lexer.readers.SourceFileReader;

public class SourceFileReaderTest {

  private BufferedReader createSourceReader(String source) {
    return new BufferedReader(new StringReader(source));
  }

  @Test
  public void testEmptyFile() throws Exception {
    BufferedReader source = createSourceReader("");

    try (SourceFileReader reader = new SourceFileReader(source)) {
      assertEquals('\0', reader.read());
    }
  }

  @Test
  public void testSingleCharacterFile() throws Exception {
    BufferedReader source = createSourceReader("b");

    try (SourceFileReader reader = new SourceFileReader(source)) {
      assertEquals('b', reader.read());
      assertEquals('\0', reader.read());
    }
  }

  @Test
  public void testMultilineFile() throws Exception {
    String content = "program {\n  int i\n}";
    BufferedReader source = createSourceReader(content);

    try (SourceFileReader reader = new SourceFileReader(source)) {
      for (int index = 0; index < content.length(); index++) {
        // This is done because our Reader strips the newlines off of the end
        // of the line, and is not expected to return it to the Lexer

        assertEquals(content.charAt(index), reader.read());
      }

      assertEquals('\0', reader.read());
    }
  }

  @Test
  public void testGetColumn() throws Exception {
    String content = "compsci";
    BufferedReader source = createSourceReader(content);

    try (SourceFileReader reader = new SourceFileReader(source)) {
      for (int index = 0; index < content.length(); index++) {
        reader.read();
        assertEquals(index, reader.getColumn());
      }
    }
  }

  @Test
  public void testGetColumnMultiline() throws Exception {
    String content = "a\nbc\nd";
    BufferedReader source = createSourceReader(content);

    try (SourceFileReader reader = new SourceFileReader(source)) {
      reader.read(); // a
      assertEquals(0, reader.getColumn());
      reader.read(); // \n
      assertEquals(1, reader.getColumn());

      reader.read(); // b
      assertEquals(0, reader.getColumn());
      reader.read(); // c
      assertEquals(1, reader.getColumn());
      reader.read(); // \n
      assertEquals(2, reader.getColumn());

      reader.read(); // d
      assertEquals(0, reader.getColumn());
    }
  }

  @Test
  public void testGetLineNumber() throws Exception {
    String content = "a\nbc\nd";
    BufferedReader source = createSourceReader(content);

    try (SourceFileReader reader = new SourceFileReader(source)) {
      reader.read(); // a
      assertEquals(0, reader.getLineNumber());
      reader.read(); // \n
      assertEquals(0, reader.getLineNumber());

      reader.read(); // b
      assertEquals(1, reader.getLineNumber());
      reader.read(); // c
      assertEquals(1, reader.getLineNumber());
      reader.read(); // \n
      assertEquals(1, reader.getLineNumber());

      reader.read(); // d
      assertEquals(2, reader.getLineNumber());
    }
  }

}
