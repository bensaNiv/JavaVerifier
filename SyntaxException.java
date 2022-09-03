package oop.ex6.main;

/**
 * This class is throwing Syntax Exception if there was a syntax error in the program.
 *
 * @author ore and niv
 */
public class SyntaxException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * Syntax error message
     */
    private static final String MSG = "Error: the syntax is illegal, ";

    /**
     * Constructor for the exception
     */
    public SyntaxException(String str) {
        super(MSG + str);
    }
}