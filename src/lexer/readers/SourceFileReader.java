package lexer.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import lexer.LexicalException;

public class SourceFileReader implements IReader {

    private BufferedReader reader;

    private String currentLine;
    private int currentLineNumber;
    private int currentColumn;

    public SourceFileReader(String fileName) throws FileNotFoundException, LexicalException {
        this(new BufferedReader(new FileReader(fileName)));
    }

    public SourceFileReader(BufferedReader reader) throws LexicalException {
        this.reader = reader;
        this.currentLineNumber = -1;

        this.getNextLine();
    }

    private String getNextLine() throws LexicalException {
        try {
            this.currentLine = this.reader.readLine();
            this.currentLineNumber++;
            this.currentColumn = -1;

            return this.currentLine;
        } catch (IOException e) {
            throw new LexicalException(String.format("Invalid attempt to read line %d", this.currentLine + 1));
        }
    }

    @Override
    public void close() throws Exception {
        this.reader.close();
    }

    @Override
    public char read() throws LexicalException {
        if (this.currentLine == null) {
            return '\0';
        }

        if (this.currentColumn + 1 == this.currentLine.length()) {
            String line = this.getNextLine();

            if (line == null) {
                return '\0';
            }
        }

        return this.currentLine.charAt(++this.currentColumn);
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
