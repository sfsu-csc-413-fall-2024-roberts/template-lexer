package tools;

import java.nio.file.Path;

import tools.lexer.SymbolTableGenerator;
import tools.lexer.TokenKindGenerator;

public class CompilerTools {
    public static void main(String[] args) throws CompilerToolException {
        Path pathToTokenFile = Path.of("tools", "config", "tokens.txt");

        FileGeneratorTool[] tools = {
                new TokenKindGenerator(pathToTokenFile),
                new SymbolTableGenerator(pathToTokenFile)
        };

        for (FileGeneratorTool tool : tools) {
            tool.generate();
        }
    }
}
