package oop.ex6.main;

import oop.ex6.main.methods.MethodCall;
import oop.ex6.main.regex.AssignmentRegex;
import oop.ex6.main.regex.DeclarationRegex;
import oop.ex6.main.regex.MethodRegex;
import oop.ex6.main.regex.MainRegex;
import oop.ex6.main.variables.Variable;
import oop.ex6.main.variables.VariableParser;

import java.util.LinkedList;


/**
 * This class represents line processing. there's four valid options for a line that ends with ; -
 * return statement (inside a method)
 * calling a method with/without arguments
 * initialize variables
 * declaration variables
 *
 * @author ore and niv
 */
public abstract class LineProcessing {
    // Constants //
    /**
     * This is an error massage if there's method that doesn't end with return statement
     */
    private static final String ILLEGAL_RETURN = "return statement outside method block";
    /**
     * This is an error massage if the line is un valid line
     */
    private static final String INVALID_LINE = "Invalid line";
    /**
     * This is an error massage if there's already variable with the same name in the level
     */
    private static final String VAR_EXISTS = "variable with the same name already exists";
    /**
     * the level outside of all the scopes
     */
    private final static int PRIMARY_LEVEL = 0;

    // Methods //

    /**
     * This method checks lines that ends with ; suffix
     *
     * @param line  a string that represents the line
     * @param level the level of the line
     */
    public static void checkLine(String line, int level) throws SyntaxException {
        // if the line is a return line
        if (MainRegex.RETURN_STATEMENT_PATTERN.matcher(line).matches()) {
            if (level == PRIMARY_LEVEL)
                throw new SyntaxException(ILLEGAL_RETURN);
        }

        // if the line is calling to method line
        else if (MethodRegex.getInstance().checkCall(line) && level > PRIMARY_LEVEL)
            MethodCall.checkMethodCall(level);

            // else it's a declaration or initialization line
        else if (DeclarationRegex.getInstance().checkDeclaration(line)) {
            LinkedList<Variable> newVars = VariableParser.getInstance().getNewVariables(level);
            addNewVariables(newVars, level);

            // else it's an initialization or initialization line
        } else if (AssignmentRegex.getInstance().checkAssignment(line)) {
            LinkedList<Variable> changeVars = VariableParser.getInstance().getChangeVariables(level);
            changeVariables(changeVars, level);

            // otherwise
        } else throw new SyntaxException(INVALID_LINE);
    }

    /**
     * This method responsible for changing the values of the variables in the GlobalMap variablesHashMap
     *
     * @param changeVars a list of variables to change in GlobalMaps variable hashMap
     * @param level      the level of the line
     * @throws SyntaxException if there was a syntax exception
     */
    private static void changeVariables(LinkedList<Variable> changeVars, int level) throws SyntaxException {
        for (Variable changeVar : changeVars)
            GlobalMaps.changeVariable(changeVar, level, changeVar.getValue());
    }

    /**
     * This method responsible for adding the new variables in the GlobalMap variablesHashMap
     *
     * @param newVars a list of variables to add to GlobalMaps variable hashMap
     * @param level   the level of the line
     * @throws SyntaxException if there was a syntax exception
     */
    public static void addNewVariables(LinkedList<Variable> newVars, int level) throws SyntaxException {
        for (Variable addVar : newVars) {
            // check if there is already variable with the same name in the level
            if (GlobalMaps.checkIfVarExists(addVar.getName(), level))
                throw new SyntaxException(VAR_EXISTS);
            GlobalMaps.addVariable(addVar, level);
        }
    }
}