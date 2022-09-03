package oop.ex6.main.variables;

import oop.ex6.main.GlobalMaps;
import oop.ex6.main.SyntaxException;
import oop.ex6.main.regex.AssignmentRegex;
import oop.ex6.main.regex.DeclarationRegex;
import oop.ex6.main.regex.MainRegex;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Class that parse a variable line
 */
public class VariableParser {
    // Constants //
    /**
     * error message if we are trying to call to un valid variable
     */
    private static final String UN_VALID_MSG = "called variable without assignment or declaration";

    /**
     * error message if we are trying to call to un valid variable
     */
    private static final String NOT_EXISTS_MSG = "Variable Not exist";

    /**
     * the level of all the methods
     */
    private final static int METHOD_LEVEL = 1;

    // Data Members //
    /**
     * The VariableParser instance
     */
    private static final VariableParser instance = new VariableParser();

    // Methods //

    /**
     * The get instance class in a singleton class
     *
     * @return The instance object of this class
     */
    public static VariableParser getInstance() {
        return instance;
    }

    /**
     * Method that is called when we need to process line that declare new variables
     *
     * @param lineLevel the level of the line
     * @return Linked List of all the variables created in this line
     */
    public LinkedList<Variable> getNewVariables(int lineLevel) throws SyntaxException {
        String type = DeclarationRegex.getInstance().getDeclarationType();
        String declarationLine = DeclarationRegex.getInstance().getDeclarationLine();
        boolean isFinal = DeclarationRegex.getInstance().isDeclarationFinal();
        if (declarationLine.endsWith(MainRegex.COMMA))
            throw new SyntaxException("Un valid assignment structure");
        String[] variableArray = declarationLine.trim().split(MainRegex.COMMA);
        LinkedList<Variable> variableInLine = new LinkedList<>();
        for (String variableLine : variableArray) {
            String varName = variableLine.trim(), varValue = null;
            // if there was an "=" in the line - assignment
            if (AssignmentRegex.getInstance().checkAssignment(variableLine + MainRegex.END)) {
                varName = AssignmentRegex.getInstance().getAssignmentName();
                varValue = AssignmentRegex.getInstance().getAssignmentValue();
                // check if the variable was initialize with an existing variable value
                if (GlobalMaps.checkIfVarExistsAll(varValue, lineLevel)) {
                    Variable changedVar = GlobalMaps.getVariable(varValue, lineLevel);
                    varValue = getVarValue(varValue, lineLevel);
                    assert changedVar != null;
                    VariableFactory.checkTypes(type, changedVar.getType());
                }
            }
            variableInLine.add(VariableFactory.create(varName, type, varValue, isFinal));
        }
        return variableInLine;
    }

    /**
     * Method that is called when there is an parameter assignment
     *
     * @param lineLevel the level of the line according to scope
     * @return Linked list of all the variables that was create\changed in this line
     * @throws SyntaxException exception that i thrown when Syntax error occurred
     */
    public LinkedList<Variable> getChangeVariables(int lineLevel) throws SyntaxException {
        LinkedList<Variable> variableInLine = new LinkedList<>();
        String varName, varValue;
        boolean isFirst = true;
        String[] variableArray = AssignmentRegex.getInstance().getAssignmentValue().split(MainRegex.COMMA);
        for (String variableLine : variableArray) {
            if (isFirst || AssignmentRegex.getInstance().checkAssignment(variableLine + MainRegex.END)){
                varName = AssignmentRegex.getInstance().getAssignmentName();
                varValue = AssignmentRegex.getInstance().getAssignmentValue();
                if (isFirst) {
                    varValue = variableLine;
                    isFirst = false;
                }
                // check if vars exists
                Variable originalVar = checkVarExist(varName, lineLevel);
                // check if the variable was initialize with an existing variable value
                if (GlobalMaps.checkIfVarExistsAll(varValue, lineLevel))
                    varValue = getVarValue(varValue, lineLevel);
                if (varValue == null)
                    throw new SyntaxException(UN_VALID_MSG);
                variableInLine.add(VariableFactory.create(varName, originalVar.getType(), varValue,
                        originalVar.isVarFinal()));
            } else { // called a value without the initialization or declaration
                throw new SyntaxException(UN_VALID_MSG);
            }
        }
        return variableInLine;
    }

    /**
     * private help Method that checks if a variable exists
     *
     * @param varName   the name of the variable
     * @param lineLevel the level of the line
     * @return true if the variable found and false otherwise
     * @throws SyntaxException if there was a syntax error
     */
    private Variable checkVarExist(String varName, int lineLevel) throws SyntaxException {
        Variable originalVar = GlobalMaps.getVariable(varName, lineLevel);
        if (originalVar == null)
            throw new SyntaxException(NOT_EXISTS_MSG);
        return originalVar;
    }

    /**
     * * Private help method that check if a given variable String is well initialized
     *
     * @param variable the name of the variable
     * @param level    the level of the line
     * @return the value of this variable
     * @throws SyntaxException if there was a syntax error
     */
    private String getVarValue(String variable, int level) throws SyntaxException {
        Variable value = Objects.requireNonNull(GlobalMaps.getVariable(variable, level));
        if (value.getValue() == null && GlobalMaps.getLevelVariable(value, level) != METHOD_LEVEL)
            throw new SyntaxException(UN_VALID_MSG);
        return value.getValue();
    }
}