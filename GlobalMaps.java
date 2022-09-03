package oop.ex6.main;
import oop.ex6.main.methods.Method;
import oop.ex6.main.variables.Variable;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class is responsible for tracking the variables and methods of the program.
 *
 * @author ore and niv
 */
public abstract class GlobalMaps {


    // Data Members //
    /**
     * a hashMap that contains all the variables in the program.
     */
    private static HashMap<Integer, HashMap<String, Variable>> variableHashMap;
    /**
     * a hashMap that contains all the methods in the program.
     */
    private static HashMap<String, Method> methodHashMap;

    /**
     * Private final static int represent not found
     */
    private final static int NOT_FOUND = -1;

    // Methods //

    /**
     * This method returns the variable in the variableHashMap if it's exists
     *
     * @param var   the variable name
     * @param level the level of the variable
     * @return the variable in the variableHashMap if it's exists, else return null
     */
    public static Variable getVariable(String var, int level) {
        for (int i = level; i >= 0; i--)
            if (getVariables().get(i) != null && getLevel(i).get(var) != null)
                return getLevel(i).get(var);
        return null;
    }

    /**
     * This method adds the variable to the variableHashMap in the current level
     *
     * @param var   the variable to add
     * @param level the level of the variable
     */
    public static void addVariable(Variable var, Integer level) {
        if(!getVariables().containsKey(level))
            getVariables().put(level, new HashMap<>());
        getVariables().get(level).put(var.getName(), var);
    }

    /**
     * This method changes the value of the variable in the variableHashMap in the current level
     *
     * @param var   the variable to change
     * @param level the level of the variable
     * @param value the new value of the variable
     */
    public static void changeVariable(Variable var, Integer level, String value) throws SyntaxException {
        Objects.requireNonNull(getVariable(var.getName(), level)).changeValue(value);
    }

    /**
     * This method adds the method to the methodHashMap
     *
     * @param method the method to add
     */
    public static void addMethod(Method method) {
        getMethods().put(method.getName(), method);
    }

    /**
     * This method checks if the name of the method already exists in the methodHashMap
     *
     * @param methodName the name of the method
     * @return true if exists and false otherwise
     */
    public static boolean checkIfMethodExists(String methodName) {
        return getMethods().containsKey(methodName);
    }

    /**
     * This method checks if the name of the variable already exists in the lower levels in variablesHashMap
     *
     * @param varName the name of the variable
     * @param level   the level of the variable
     * @return true if exists and false otherwise
     */
    public static boolean checkIfVarExistsAll(String varName, Integer level) {
        return getBelowLevel(level).containsKey(varName);
    }

    /**
     * This method checks if the name of the variable already exists in it's level in variablesHashMap
     *
     * @param varName the name of the variable
     * @param level   the level of the variable
     * @return true if exists and false otherwise
     */
    public static boolean checkIfVarExists(String varName, Integer level) {
        return getVariables().containsKey(level) && getLevel(level).containsKey(varName);
    }

    /**
     * This method removes all the variables in the level from the variableHashMap
     *
     * @param level the level to remove
     */
    public static void removeAllLevel(Integer level) {
        variableHashMap.remove(level);
    }

    /**
     * This method returns all the variables in this level from the variableHashMap
     *
     * @param level the level of the variables
     * @return HashMap<String, Variable> of all the variables in the level
     */
    public static HashMap<String, Variable> getLevel(Integer level) {
        return getVariables().get(level);
    }

    /**
     * This method return all the variables in this level and below from the variableHashMap
     *
     * @param level the level of the variables
     * @return HashMap<String, Variable> of all the variables in this level and below
     */
    public static HashMap<String, Variable> getBelowLevel(Integer level) {
        HashMap<String, Variable> belowVariables = new HashMap<>();
        for (int i = 0; i <= level; i++)
            if (getVariables().get(i) != null)
                belowVariables.putAll(getVariables().get(i));
        return belowVariables;
    }

    /**
     * This method returns the variableHashMap
     *
     * @return the variableHashMap
     */
    public static HashMap<Integer, HashMap<String, Variable>> getVariables() {
        return variableHashMap;
    }

    /**
     * This method returns the methodHashMap
     *
     * @return the methodHashMap
     */
    public static HashMap<String, Method> getMethods() {
        return methodHashMap;
    }

    /**
     * This method reset all the variables and the methods
     */
    public static void resetAll() {
        methodHashMap = new HashMap<>();
        variableHashMap = new HashMap<>();

    }

    /**
     * This method resets the global variables in the variables hash map
     *
     * @param globals the original hashmap of globals variables (level 0)
     */
    public static void resetGlobals(HashMap<String, Variable> globals) {
        variableHashMap.put(0, globals);
    }

    /**
     * Method that returns variable level, if it doesn't exist return not found.
     *
     * @param var the variable object
     * @param level the level of the line referred to this variable
     * @return the level of the variable, if this variable doesn't exists returns NOT_FOUND
     */
    public static int getLevelVariable(Variable var, int level) {
        for (int i = level; i >= 0; i--)
            if (getVariables().get(i) != null && getLevel(i).get(var.getName()) != null)
                return i;
        return NOT_FOUND;
    }
}