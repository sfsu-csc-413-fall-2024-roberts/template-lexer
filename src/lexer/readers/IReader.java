package lexer.readers;

import lexer.LexicalException;

public interface IReader extends AutoCloseable {
    public void close() throws Exception;

    public char read() throws LexicalException;

    public int getColumn();

    public int getLineNumber();
}
