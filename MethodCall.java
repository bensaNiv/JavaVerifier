package oop.ex6.main.methods;

import oop.ex6.main.GlobalMaps;
import oop.ex6.main.SyntaxException;
import oop.ex6.main.regex.MethodRegex;
import oop.ex6.main.regex.MainRegex;
import oop.ex6.main.variables.VariableFactory;

import java.util.Objects;

/**
 * This class checks if a method call is valid.
 *
 * @author ore and niv
 */
public abstract class MethodCall {
    // Constants //
    /**
     * the message for illegal method call
     */
    private static final String METHOD_CALL_EXCEPTION = "the method calling is illegal.";

    /**
     * the message for wrong number of arguments in method call
     */
    private static final String WRONG_ARGS_NUMBER = "wrong number of arguments sent to the method";

    /**
     * Private final String representing empty String
     */
    private static final String EMPTY_STRING = "";

    /**
     * This method checks if a method call is valid
     * @param level the level of the calling method line
     * @throws SyntaxException if there was an error
     */
    public static void checkMethodCall(int level) throws SyntaxException {
        String methodName = MethodRegex.getInstance().getLastCallName();
        String parameters = MethodRegex.getInstance().getLastCallParameters();
        // check if the method exist in this program
        if (!GlobalMaps.checkIfMethodExists(methodName))
            throw new SyntaxException(METHOD_CALL_EXCEPTION);
        Method method = GlobalMaps.getMethods().get(methodName);
        String[] parametersArray = parameters.trim().split(MainRegex.COMMA);
        if(parametersArray.length == 1 && parametersArray[0].equals(EMPTY_STRING))
            parametersArray = new String[0];
        checkParameters(method, parametersArray, level);
    }

    /**
     * This method checks if the parameters are valid
     * @param method     the method this calling method line refers to
     * @param parameters a string that represent parameter's name/ constant object
     * @param level      the level of the calling method line
     * @throws SyntaxException if there was an error
     */
    private static void checkParameters(Method method, String[] parameters, int level) throws SyntaxException {
        if (parameters.length != method.getArgs().size())
            throw new SyntaxException(WRONG_ARGS_NUMBER);
        for (int i = 0; i < parameters.length; i++) {
            if(GlobalMaps.checkIfVarExistsAll(parameters[i], level))
                parameters[i] = Objects.requireNonNull(GlobalMaps.getVariable(parameters[i], level)).getValue();
            VariableFactory.checkValue(method.getArgs().get(i), parameters[i]);
        }
    }
}