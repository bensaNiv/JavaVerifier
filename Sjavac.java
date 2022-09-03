package oop.ex6.main;

import java.io.IOException;

/**
 * This class represents the main class of the program. it prints 0 if the code is legal, 1 if the code
 * is illegal and 2 in case of IO errors.
 *
 * @author ore and niv
 */
public class Sjavac {
    // Constants //
    /**
     * 0 - if the code is legal.
     */
    private final static int LEGAL = 0;

    /**
     * 1 - if the code is illegal.
     */
    private final static int ILLEGAL = 1;

    /**
     * 2 - in case of IO errors.
     */
    private final static int IO_ERRORS = 2;

    /**
     * This method prints 0 if the code is legal, 1 if the code is illegal and 2 in case of IO errors.
     *
     * @param args an array of string
     */
    public static void main(String[] args) {
        GlobalMaps.resetAll();
        try {
            if (args.length != 1) {
                throw new IOException("The number of argument given is wrong");
            }
            // calling Scanner with the file
            Scanner.checkFile(args[0]);
            System.out.println(LEGAL);
        } catch (SyntaxException e) {
            System.out.println(ILLEGAL);
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(IO_ERRORS);
            System.err.println(e.getMessage());
        }
    }


}