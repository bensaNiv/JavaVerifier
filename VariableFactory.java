package ex6.main.variables;

import oop.ex6.main.SyntaxException;
import oop.ex6.main.regex.MainRegex;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class represents the variable factory. it creates new Variable objects.
 *
 * @author ore and niv
 */
public class VariableFactory {
    // Constants //
    /**
     * This is an error massage if the type of the variable is un valid
     */
    private static final String TYPE_ILLEGAL = "the type of the variable is un valid";
    /**
     * This is an error massage if the variable is final but not initialized with a value
     */
    private static final String UN_INITIALIZED_FINAL = "must initialize final variables";
    /**
     * This is an error massage if the name of the variable is illegal
     */
    private static final String NAME_ILLEGAL = "illegal variable name";
    /**
     * This is an error massage if the value of the variable is illegal
     */
    private static final String VALUE_ILLEGAL = "illegal variable value";

    /**
     * This map contains all the valid types of variables and it's values
     */
    private static final Map<String, Pattern> validTypes = Map.ofEntries(
            Map.entry("int", MainRegex.INT_TYPE),
            Map.entry("double", MainRegex.DOUBLE_TYPE),
            Map.entry("String", MainRegex.STRING_TYPE),
            Map.entry("boolean", MainRegex.BOOLEAN_TYPE),
            Map.entry("char", MainRegex.CHAR_TYPE));


    // Methods //

    /**
     * This method creates new Variable object and returns it
     *
     * @param name    a String that represents the variable's name
     * @param type    a String that represents the variable's type
     * @param value   a String that represents the variable's value
     * @param isFinal true if the variable is final and false otherwise
     * @return the correct variable according to the type and value
     */
    public static Variable create(String name, String type, String value, boolean isFinal) throws SyntaxException {
        if (!name.matches(MainRegex.VARIABLE_NAME))
            throw new SyntaxException(NAME_ILLEGAL);
        if (isFinal && value == null)
            throw new SyntaxException(UN_INITIALIZED_FINAL);
        if (validTypes.containsKey(type) && (value == null || validTypes.get(type).matcher(value).matches()))
            // create new var
            return new Variable(name, type, value, isFinal);
        throw new SyntaxException(TYPE_ILLEGAL);
    }

    /**
     * This method checks if the insert value can be a value of the Variable
     *
     * @param var   the variable
     * @param value the value to check
     */
    public static void checkValue(Variable var, String value) throws SyntaxException {
        if (value == null || !validTypes.get(var.getType()).matcher(value).matches())
            throw new SyntaxException(VALUE_ILLEGAL);
    }

    /**
     * This method checks if the types of the variables are matched
     *
     * @param type       the type of the variable to initialize
     * @param typeAssign the type of the variable to assign
     * @throws SyntaxException if there was a type error
     */
    public static void checkTypes(String type, String typeAssign) throws SyntaxException {
        if (!(type.equals(typeAssign)))
            if (!(type.equals("boolean") && (typeAssign.equals("int") || typeAssign.equals("double"))) &&
                    !(type.equals("double") && typeAssign.equals("int")))
                throw new SyntaxException(TYPE_ILLEGAL);
    }

}