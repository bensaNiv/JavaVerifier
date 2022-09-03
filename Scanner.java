package oop.ex6.main;

import oop.ex6.main.methods.Method;
import oop.ex6.main.methods.MethodFactory;
import oop.ex6.main.regex.MethodRegex;
import oop.ex6.main.regex.MainRegex;
import oop.ex6.main.variables.Variable;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;

/**
 * This class represents the scanner class. It scans the given file and checks if the lines are valid.
 *
 * @author Ore and Niv
 */
public abstract class Scanner {
    // Constants //

    /**
     * represent the regular line type (ends with ;)
     */
    private final static int REGULAR_LINE = 1;
    /**
     * represent the comment line type (starts with // )
     */
    private final static int COMMENT_LINE = 2;
    /**
     * represent the open scope line type (ends with { )
     */
    private final static int OPEN_SCOPE = 3;
    /**
     * represent the comment line type (starts with } )
     */
    private final static int CLOSE_SCOPE = 4;
    /**
     * represent the empty line
     */
    private final static int EMPTY_LINE = 5;
    /**
     * the level outside of all the scopes
     */
    private final static int PRIMARY_LEVEL = 0;

    /**
     * This is an IO error massage if there eas a problem in reading the file
     */
    private static final String FAILED_READING = "Failed to read the file.";
    /**
     * This is an error massage if the line is un valid line
     */
    private static final String INVALID_LINE = "Failed to detect line in file";
    /**
     * This is an error massage if the scope is illegal
     */
    private static final String SCOPE_ILLEGAL = "un valid scope syntax";


    // Data Members //

    /**
     * represent boolean value that says if we are inside a method or not
     */
    private static boolean insideMethod;

    /**
     * linked list that contain a method scope
     */
    private static LinkedList<String> methodLinkedList;

    /**
    * Parameter that counts the number of blocks in a method
     */
    private static int countBlock ;

    // Methods //

    /**
     * This method reads the file and check if all the lines and methods are valid
     *
     * @param filePath the path of the file to check
     * @throws IOException     if there was an IO error
     * @throws SyntaxException if there was a syntax error
     */
    public static void checkFile(String filePath) throws IOException, SyntaxException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String curLine;
            resetMethodCheckers();
            while ((curLine = reader.readLine()) != null)
                processLinePrimary(curLine); // process every line in the file
            reader.close();
            if (insideMethod)  // scope is open and didn't closed him
                throw new SyntaxException(SCOPE_ILLEGAL);
            HashMap<String, Variable> globals = GlobalMaps.getLevel(PRIMARY_LEVEL);
            // check if all method blocks are valid
            for (Method method : GlobalMaps.getMethods().values()) {
                GlobalMaps.resetGlobals(globals);
                BlockProcessing.checkMethod(method);
            }
        } catch (IOException e) {
            throw new IOException(FAILED_READING);
        }
    }

    /**
     * private help method that process every line in the code in level 0.
     * creates all the global variables and methods.
     *
     * @param curLine The current Line in the file
     * @throws SyntaxException Exception that is thrown if the lines are not according the types possible
     */
    private static void processLinePrimary(String curLine) throws SyntaxException {
        if (insideMethod) { // if we are inside a method scope
            if (matcherCheck(curLine, OPEN_SCOPE))
                countBlock += 1;
            else if (matcherCheck(curLine, CLOSE_SCOPE))
                countBlock -= 1;
            if (countBlock == 0) { // if we reached the end of the scope
                String methodLine = methodLinkedList.removeFirst();
                GlobalMaps.addMethod(MethodFactory.create(methodLine, methodLinkedList));
                resetMethodCheckers();
            } else {
                methodLinkedList.add(curLine);
            }
        } else {
            if (matcherCheck(curLine, REGULAR_LINE)) { // ends with ;
                LineProcessing.checkLine(curLine, PRIMARY_LEVEL);
            } else if (matcherCheck(curLine, OPEN_SCOPE)) {
                countBlock += 1;
                if (MethodRegex.getInstance().checkMethod(curLine)) { // initialization method line
                    insideMethod = true;
                    methodLinkedList.add(curLine);
                } else throw new SyntaxException(SCOPE_ILLEGAL);
                // starts with // or empty Line
            } else if (!matcherCheck(curLine, COMMENT_LINE) &&
                    !matcherCheck(curLine, EMPTY_LINE)) {
                throw new SyntaxException(INVALID_LINE);
            }
        }
    }

    /**
     * public method that process every line in the code - we use it inside scopes
     *
     * @param curLine The current Line in the file
     * @param level   the level of this line
     * @return the level of the next line
     * @throws SyntaxException Exception that is thrown if the lines are not according the types possible
     */
    public static int processLine(String curLine, int level) throws SyntaxException {
        // empty line or comment line
        if (matcherCheck(curLine, COMMENT_LINE) || matcherCheck(curLine, EMPTY_LINE))
            return level;
        else if (matcherCheck(curLine, REGULAR_LINE)) {  // ends with ;
            LineProcessing.checkLine(curLine, level);
            return level;
        }
        // lines that ends with "{" - if/ while
        else if (matcherCheck(curLine, OPEN_SCOPE)) { // ends with {
            BlockProcessing.checkBlock(curLine, level);
            return level + 1;
        }
        // lines that contains only "}"
        else if (matcherCheck(curLine, CLOSE_SCOPE)) // contains only }
            return level - 1;
        else throw new SyntaxException(INVALID_LINE);
    }

    /**
     * This method checks the type of the line using Regex
     *
     * @param curLine   The current Line in the file
     * @param checkType The type of line we want to check - if its match that kind of line
     * @return true if the line is in the same syntax as the line type that was enterd
     * @throws SyntaxException if not match to any kind of the lines type as defined
     */
    private static boolean matcherCheck(String curLine, int checkType) throws SyntaxException {
        Matcher matcher;
        switch (checkType) {
            case (REGULAR_LINE): // check if ends with ;
                matcher = MainRegex.END_REGULAR_PATTERN.matcher(curLine);
                return matcher.matches();
            case (COMMENT_LINE):  // check if starts with //
                matcher = MainRegex.COMMENT_PATTERN.matcher(curLine);
                return matcher.matches();
            case (OPEN_SCOPE): // check if ends with {
                matcher = MainRegex.OPEN_SCOPE_PATTERN.matcher(curLine);
                return matcher.matches();
            case (CLOSE_SCOPE): // check if starts with }
                matcher = MainRegex.CLOSE_SCOPE_PATTERN.matcher(curLine);
                return matcher.matches();
            case (EMPTY_LINE): // check if the line is empty line
                matcher = MainRegex.EMPTY_LINE_PATTERN.matcher(curLine);
                return matcher.matches();
            default: // not from the defined options
                throw new SyntaxException(INVALID_LINE);
        }
    }

    /**
     * Private Help method to reset the parameters that check the oop.ex6.main.methods scope
     */
    private static void resetMethodCheckers() {
        countBlock = 0;
        insideMethod = false;
        methodLinkedList = new LinkedList<>();
    }
}