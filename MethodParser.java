package oop.ex6.main.methods;

import oop.ex6.main.SyntaxException;
import oop.ex6.main.regex.MethodRegex;
import oop.ex6.main.regex.MainRegex;


import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class parse the Method declaration line.
 *
 * @author Ore and Niv
 */
public class MethodParser {
    // Constants //
    /**
     * a massage that represents parameters error in the declaration of the method
     */
    private static final String WRONG_PARAMETERS = "wrong parameters in the declaration of the method";

    // Data Members //

    /**
     * The MethodParser instance
     */
    private static final MethodParser instance = new MethodParser();
    /**
     * List of all the the method variables
     */
    private LinkedList<ArrayList<Object>> methodVariables;
    /**
     * String that represents the method name
     */
    private String methodName;

    // Methods //

    /**
     * @return The instance object oft this class
     */
    public static MethodParser getInstance() {
        return instance;
    }

    /**
     * private help method that parse the method declaration given line
     *
     * @throws SyntaxException if there was a parse declaration error
     */
    public void parseDeclaration() throws SyntaxException {
        methodVariables = new LinkedList<>();
        methodName = MethodRegex.getInstance().getLastCallName();
        String parameterString = MethodRegex.getInstance().getLastCallParameters();
        if (parameterString.length() != 0 && !parameterString.matches(MainRegex.WHITE_SPACE)) {
            String[] parameters = parameterString.split(MainRegex.COMMA);
            for (String parameterLine : parameters)
                checkParameters(parameterLine);
        }
    }

    /**
     * Private help method that check the parameter name and type
     *
     * @param parameterLine a string represent the line of the parameters
     * @throws SyntaxException if parameters are un valid
     */
    private void checkParameters(String parameterLine) throws SyntaxException {
        boolean isFinal = false;
        String name, type;
        String[] parameterPair = parameterLine.trim().split(MainRegex.WHITE_SPACE);
        if (parameterPair.length != 2 && parameterPair.length != 3)
            throw new SyntaxException(WRONG_PARAMETERS);
        if (parameterPair.length == 3) {
            if (!parameterPair[0].equals(MainRegex.FINAL)) {
                throw new SyntaxException(WRONG_PARAMETERS);
            }
            type = parameterPair[1];
            name = parameterPair[2];
            isFinal = true;
        } else {
            type = parameterPair[0];
            name = parameterPair[1];
        }
        // check if the name and the type are proper
        if (!name.matches(MainRegex.VARIABLE_NAME) || !type.matches(MainRegex.TYPES_REGULAR)) {
            throw new SyntaxException(WRONG_PARAMETERS);
        }
        ArrayList<Object> parameterDefying = new ArrayList<>();
        parameterDefying.add(name);
        parameterDefying.add(type);
        parameterDefying.add(isFinal);
        methodVariables.add(parameterDefying);
    }

    /**
     * Public getter for the method name
     *
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Public getter for the method Variables initial values
     */
    public LinkedList<ArrayList<Object>> getMethodVariable() {
        return methodVariables;
    }


}
