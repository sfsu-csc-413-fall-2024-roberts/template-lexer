package lexer.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SourceFileReader implements IReader {

    private BufferedReader reader;

    public SourceFileReader(String sourceFilePath) throws FileNotFoundException {
        this(new BufferedReader(new FileReader(sourceFilePath)));
    }

    public SourceFileReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

    @Override
    public char read() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public int getColumn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getColumn'");
    }

    @Override
    public int getLineNumber() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLineNumber'");
    }

}
