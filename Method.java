package oop.ex6.main.methods;

import oop.ex6.main.variables.Variable;

import java.util.LinkedList;
/**
 * This class represents a Method in the program.
 * each method has a name, list of args and list of lines(block).
 *
 * @author ore and niv
 */
public class Method {

    // Data Members //
    /**
     * The name of the method.
     */
    String name;
    /**
     * a linkedList that contains Object elements- the arguments of the method.
     */
    LinkedList<Variable> args;
    /**
     * a linkedList that contains the block (lines) of the method. each element represents a line.
     */
    LinkedList<String> methodBlock;

    //Constructor//

    /**
     * Constructor for Method class
     * @param name the method name
     * @param args the method arguments
     * @param methodBlock a LinkedList of the lines of the method
     */
    public Method(String name, LinkedList<Variable> args, LinkedList<String> methodBlock){
        this.name = name;
        this.args = args;
        this.methodBlock = methodBlock;
    }

    //Methods//

    /**
     * @return the name of the method
     */
    public String getName() {
        return this.name;
    }


    /**
     * @return Linked List of this class argument
     */
    public LinkedList<Variable> getArgs(){ return args;}

    /**
     * @return the lines of the method
     */
    public LinkedList<String> getBlock() {
        return methodBlock;
    }


}