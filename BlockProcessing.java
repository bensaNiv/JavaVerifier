package oop.ex6.main;

import oop.ex6.main.methods.Method;
import oop.ex6.main.regex.MethodRegex;
import oop.ex6.main.regex.MainRegex;
import oop.ex6.main.regex.TermRegex;

import java.util.Objects;


/**
 * This class represents block processing. there's two types of blocks-
 * method block- can appear only in level 0
 * if/while block- can appear only in level > 0
 *
 * @author ore and niv
 */
public abstract class BlockProcessing {
    // Constants //
    /**
     * This is an error massage if there's method that doesn't end with return statement
     */
    private static final String MISSING_RETURN = "missing return statement";
    /**
     * This is an error massage if the scope is illegal
     */
    private static final String SCOPE_ILLEGAL = "un valid scope syntax";
    /**
     * This is an error massage if there's method inside another method
     */
    private static final String METHOD_INSIDE_METHOD = "a method may not be declared inside another method";
    /**
     * This is an error massage if the if/while conditions are illegal
     */
    private static final String CONDITIONS_ILLEGAL = "if/while conditions are illegal";
    /**
     * the max depth of a block
     */
    private final static int DEPTH = java.lang.Integer.MAX_VALUE;
    /**
     * the level outside of all the scopes
     */
    private final static int PRIMARY_LEVEL = 0;
    /**
     * the level of all the methods
     */
    private final static int METHOD_LEVEL = 1;

    // Methods //

    /**
     * This method checks if the lines inside the method are valid. it uses Scanner and LinePrecessing class.
     *
     * @param method a method to check
     */
    public static void checkMethod(Method method) throws SyntaxException {
        // check return statement
        if (!MainRegex.RETURN_STATEMENT_PATTERN.matcher(method.getBlock().removeLast()).matches())
            throw new SyntaxException(MISSING_RETURN);

        // add method variables to variableHashMap in globalMaps
        LineProcessing.addNewVariables(method.getArgs(), METHOD_LEVEL);

        // check other lines
        int currLevel = METHOD_LEVEL;
        int nextLevel;
        for (String line : method.getBlock()) {
            nextLevel = Scanner.processLine(line, currLevel);
            if (nextLevel == currLevel - 1)
                GlobalMaps.removeAllLevel(currLevel); // block ends- delete all local level variables
            currLevel = nextLevel;
            if (nextLevel >= DEPTH)
                throw new SyntaxException(SCOPE_ILLEGAL);
        }
        currLevel -= 1;
        if (currLevel != 0)
            throw new SyntaxException(SCOPE_ILLEGAL);
        GlobalMaps.removeAllLevel(METHOD_LEVEL); // method ends- delete all local level variables
    }

    public static void checkBlock(String curLine, int level) throws SyntaxException {
        // if block is a method && level > 0
        if (MethodRegex.getInstance().checkMethod(curLine) && level > PRIMARY_LEVEL)
            throw new SyntaxException(METHOD_INSIDE_METHOD);
            // if it's if/while block
        else if (TermRegex.getInstance().checkTerm(curLine) && level > PRIMARY_LEVEL) {
            String condition = TermRegex.getInstance().getCondition();
            checkAllConditions(condition, level);
        } else {
            throw new SyntaxException(SCOPE_ILLEGAL);
        }
    }

    /**
     * This function separate between the conditions and checks all of them
     *
     * @param allConditions all the conditions to check
     * @param level         the level of the line
     * @throws SyntaxException if there was a syntax error
     */
    private static void checkAllConditions(String allConditions, int level) throws SyntaxException {
        String[] conditions = allConditions.split(MainRegex.TERM_CONDITIONS);
        for (String cond : conditions)
            checkCondition(cond.trim(), level);
    }

    /**
     * This function checks if a condition is valid
     *
     * @param cond  the condition to check
     * @param level the level of the line
     * @throws SyntaxException if there was a syntax error
     */
    private static void checkCondition(String cond, int level) throws SyntaxException {
        // if it's a variable
        if (GlobalMaps.getBelowLevel(level).containsKey(cond))
            cond = Objects.requireNonNull(GlobalMaps.getVariable(cond, level)).getValue();
        if (cond == null || !MainRegex.BOOLEAN_TYPE.matcher(cond).matches())
            throw new SyntaxException(CONDITIONS_ILLEGAL);
    }
}