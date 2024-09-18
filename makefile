# Note: Anywhere you see @, that means I want to prevent that
#     command from printing (and only want to see the result of
#     executing that command)

# Defining some variables to prevent typos
COMPILE_DIR         = out
SRC_DIR             = src
SOURCE_FILE         = sources

# clean should be used to remove all compiled (.class) files
# the find command on *nix finds all files (-type f) starting from the
#    current directory (.), where the filename ends in ".class", and
#    deletes (-delete) them
clean:
	@echo "Deleting all class files..."
	@find . -name "*.class" -type f -delete
	@echo "Deleting compiled files..."
	@rm -rf $(COMPILE_DIR)
	@rm -f $(SOURCE_FILE)

tools: clean
	@javac -d $(COMPILE_DIR) -cp $(SRC_DIR):. src/tools/CompilerTools.java
	@java -cp $(COMPILE_DIR):. tools.CompilerTools

lexer: tools
	@javac -d $(COMPILE_DIR) -cp $(SRC_DIR):. src/lexer/Lexer.java
	@java -cp $(COMPILE_DIR):. lexer.Lexer