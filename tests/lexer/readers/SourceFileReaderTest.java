package tests.lexer.readers;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;

import lexer.readers.IReader;
import lexer.readers.SourceFileReader;

public class SourceFileReaderTest {

    public void testEmptySourceFile() {
        BufferedReader sourceCode = new BufferedReader(
                new StringReader(""));

        try (SourceFileReader reader = new SourceFileReader(sourceCode)) {
            assertEquals('\0', reader.read());
            assertEquals('\0', reader.read());
        }
    }

    public void testSingleCharacterSourceFile() {
        BufferedReader sourceCode = new BufferedReader(
                new StringReader("7"));

        try (SourceFileReader reader = new SourceFileReader(sourceCode)) {
            assertEquals('7', reader.read());
            assertEquals('\0', reader.read());
        }
    }

    public void testSourceFile() {
        BufferedReader sourceCode = new BufferedReader(
                new StringReader("program {}"));

        try (SourceFileReader reader = new SourceFileReader(sourceCode)) {
            assertEquals('p', reader.read());
            assertEquals('r', reader.read());
            assertEquals('o', reader.read());
            assertEquals('g', reader.read());
            assertEquals('r', reader.read());
            assertEquals('a', reader.read());
            assertEquals('m', reader.read());
            assertEquals(' ', reader.read());
            assertEquals('{', reader.read());
            assertEquals('}', reader.read());
            assertEquals('\0', reader.read());
        }
    }

    public void testGetColumnBeforeRead() {
        BufferedReader sourceCode = new BufferedReader(
                new StringReader("program {\n  int i\n}"));

        try (SourceFileReader reader = new SourceFileReader(sourceCode)) {
            assertEquals(-1, reader.getColumn());
        }
    }

    public void testGetColumn() {
        BufferedReader sourceCode = new BufferedReader(
                new StringReader("program {\n  int i\n}"));

        try (SourceFileReader reader = new SourceFileReader(sourceCode)) {
            reader.read(); // p
            assertEquals(0, reader.getColumn());
            reader.read(); // r
            assertEquals(1, reader.getColumn());
            reader.read(); // o
            assertEquals(2, reader.getColumn());

            advanceCharacters(reader, 7);

            reader.read();
            assertEquals(0, reader.getColumn());

            advanceCharacters(reader, 7);

            reader.read();
            assertEquals(0, reader.getColumn());
        }
    }

    private void advanceCharacters(IReader reader, int characterCount) {
        for (int i = 0; i < characterCount; i++) {
            reader.read();
        }
    }

    public void testGetLineNumberBeforeRead() {
        BufferedReader sourceCode = new BufferedReader(
                new StringReader("program {\n  int i\n}"));

        try (SourceFileReader reader = new SourceFileReader(sourceCode)) {
            assertEquals(-1, reader.getLineNumber());
        }
    }

    public void testGetLineNumber() {
        BufferedReader sourceCode = new BufferedReader(
                new StringReader("program {\n  int i\n}"));

        try (SourceFileReader reader = new SourceFileReader(sourceCode)) {
            reader.read();
            assertEquals(0, reader.getLineNumber());

            advanceCharacters(reader, 9);
            assertEquals(0, reader.getLineNumber());

            reader.read();
            assertEquals(1, reader.getLineNumber());

            advanceCharacters(reader, 7);
            assertEquals(2, reader.getLineNumber());
        }
    }
}
