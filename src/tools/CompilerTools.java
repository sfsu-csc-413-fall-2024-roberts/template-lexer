package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import tools.lexer.SymbolTableGenerator;
import tools.lexer.TokenKindGenerator;

public class CompilerTools {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.out.println("Usage: java tools.CompilerTools <path_to_tokens_file>");
      System.exit(1);
    }

    List<String> tokenFileLines = Files.readAllLines(Path.of(args[0]));

    FileGeneratorTool[] tokenTools =
        { new TokenKindGenerator(tokenFileLines), new SymbolTableGenerator(tokenFileLines) };

    for (FileGeneratorTool tool : tokenTools) {
      tool.writeToFile(tool.getGeneratedFilePath());
    }
  }
}
