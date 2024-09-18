package lexer;

import lexer.daos.Token;

public interface ILexer extends AutoCloseable {
  public Token nextToken() throws LexicalException;

  public Token anonymousIdentifierToken(String identifier);
}
