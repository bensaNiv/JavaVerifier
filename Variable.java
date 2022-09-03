package oop.ex6.main.variables;

import oop.ex6.main.SyntaxException;

/**
 * This class represents a Variable in the code. it can be of type int, double, string, boolean, char.
 * each variable has a name, type, value and isFinal boolean.
 *
 * @author ore and niv
 */
public class Variable {
    // Constants //
    /**
     * This is an error massage for trying to change final value
     */
    private static final String FINAL_MSG = "you are trying to change final value";

    // Data Members //
    /**
     * The name of the variable.
     */
    private final String name;
    /**
     * The type of the variable- int/double/string/boolean/char.
     */
    private final String type;
    /**
     * The value of the variable.
     */
    private String value;
    /**
     * A boolean value that represents whether the variable is final.
     */
    private final boolean isFinal;


    //Constructor//

    /**
     * @param name    a String that represents the variable's name
     * @param type    a String that represents the variable's type
     * @param value   an object that represents the variable's value
     * @param isFinal true if the variable is final and false otherwise
     */
    public Variable(String name, String type, String value, boolean isFinal) {
        this.name = name;
        this.type = type;
        this.isFinal = isFinal;
        this.value = value;
    }

    //Methods//
    /**
     * This method returns the name of the variable
     *
     * @return the String name of the variable
     */
    public String getName(){
        return this.name;
    }

    /**
     * This method returns the value of the variable
     *
     * @return the String value of the variable
     */
    public String getValue(){
        return this.value;
    }

    /**
     * This method returns the type of the variable
     *
     * @return the String type of the variable
     */
    public String getType(){
        return this.type;
    }

    /**
     * This method returns true if the variable is final and false otherwise
     *
     * @return true if the variable is final and false otherwise
     */
    public Boolean isVarFinal(){
        return this.isFinal;
    }

    /**
     * This method changes the value of the variable (if it's not final)
     *
     * @param value string that represents optional new value for the variable
     */
    public void changeValue(String value) throws SyntaxException {
        if(isVarFinal())
            throw new SyntaxException(FINAL_MSG);
        VariableFactory.checkValue(this, value);
        this.value = value;
    }

}
