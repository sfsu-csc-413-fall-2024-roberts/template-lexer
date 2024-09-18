package lexer;

import lexer.daos.Token;
import lexer.readers.IReader;

public class Lexer implements ILexer {

    public Lexer(String filePath) {

    }

    public Lexer(IReader reader) {

    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

    @Override
    public Token nextToken() throws LexicalException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nextToken'");
    }

    @Override
    public Token anonymousIdentifierToken(String identifier) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'anonymousIdentifierToken'");
    }

}
