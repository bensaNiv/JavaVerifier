package oop.ex6.main.methods;

import oop.ex6.main.GlobalMaps;
import oop.ex6.main.SyntaxException;
import oop.ex6.main.regex.MethodRegex;
import oop.ex6.main.variables.Variable;
import oop.ex6.main.variables.VariableFactory;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class represents the method factory. it creates new Method objects.
 *
 * @author ore and niv
 */
public class MethodFactory {

    // Constants //
    /**
     * the message for declaring method that already exists
     */
    private static final String METHOD_EXISTS = "method with the same name already exists.";

    /**
     * the message for incorrect method line
     */
    private static final String IN_CORRECT_METHOD = "method line is incorrect";

    // Methods//

    /**
     * @param methodLine  a String that represents the method's line
     * @param methodBlock a LinkedList that represents the method's lines
     * @return the method with it's args and block
     */
    public static Method create(String methodLine, LinkedList<String> methodBlock) throws SyntaxException {
        if (MethodRegex.getInstance().checkMethod(methodLine)) {
            MethodParser.getInstance().parseDeclaration();
            // find method's name and arguments
            String methodName = MethodParser.getInstance().getMethodName();
            LinkedList<ArrayList<Object>> methodArgs = MethodParser.getInstance().getMethodVariable();

            // check if there's method with the same name
            if(GlobalMaps.checkIfMethodExists(methodName))
                throw new SyntaxException(METHOD_EXISTS);

            // check valid args using variable factory
            LinkedList<Variable> validArgs = new LinkedList<>();
            for (ArrayList<Object> arg : methodArgs) {
                String name = arg.get(0).toString();
                String type = arg.get(1).toString();
                boolean isFinal = (boolean) arg.get(2);
                validArgs.add(VariableFactory.create(name, type, null, isFinal));
            }

            // create the method
            return new Method(methodName, validArgs, methodBlock);
        } else throw new SyntaxException(IN_CORRECT_METHOD);
    }

}