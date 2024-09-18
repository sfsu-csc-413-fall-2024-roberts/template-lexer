package tools;

import java.io.IOException;

import tools.lexer.SymbolTableGenerator;
import tools.lexer.TokenKindGenerator;

public class CompilerTools {
    public static void main(String[] args) throws IOException {
        FileGeneratorTool[] tools = {
                new TokenKindGenerator(),
                new SymbolTableGenerator()
        };

        for (FileGeneratorTool tool : tools) {
            tool.generate();
        }
    }
}
