package tools.lexer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import tools.FileGeneratorTool;

public class TokenKindGenerator extends FileGeneratorTool {

  public TokenKindGenerator(List<String> lines) throws IOException {
    super(lines);
  }

  @Override
  protected String generateFileContent() throws IOException {
    StringBuffer buffer = new StringBuffer();

    buffer.append(String.join("",
        List.of(this.formatString(0, 2, "package lexer.daos;"), this.autoGeneratedWarning(),
            this.formatString(0, 1, "public enum TokenKind {"),
            this.formatString(1, 1, "// Special TokenKinds for internal use"),
            this.formatString(1, 2, "BogusToken, EOF,"),
            this.formatString(1, 0, "// Generated TokenKinds (from tokens.txt)"))));

    int index = 0;
    while (this.hasNext()) {
      String[] lineParts = this.next().split("\s+");

      if (index % 5 == 0) {
        buffer.append(System.lineSeparator());
        buffer.append(this.formatString(1, String.format("%s, ", lineParts[0])));
      } else if (index % 5 == 4) {
        buffer.append(String.format("%s,", lineParts[0]));
      } else {
        buffer.append(String.format("%s, ", lineParts[0]));
      }

      index++;
    }
    buffer.append(System.lineSeparator());
    buffer.append(this.formatString(0, 1, "}"));


    return buffer.toString();
  }

  @Override
  public Path getGeneratedFilePath() {
    return Path.of("src", "lexer", "daos", "TokenKind.java");
  }

}
