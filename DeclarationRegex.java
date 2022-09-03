package oop.ex6.main.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Public class that represent the Declaration of a value regex - singleton
 */
public class DeclarationRegex {
    // Constants //
    /**
     * an instance of DeclarationRegex class
     */
    private static final DeclarationRegex instance = new DeclarationRegex();
    /**
     * Regular expression for the Term Line
     */
    public final String DECLARATION_LINE = MainRegex.OPTIONAL_SPACE +
            "(" + MainRegex.FINAL + MainRegex.WHITE_SPACE + "){0,1}" +
            MainRegex.TYPES_REGULAR + MainRegex.WHITE_SPACE + "(.+)"
            + MainRegex.END_LINE;

    // Data Members //
    /**
     * Pattern for Type line RegEx.
     */
    private Pattern typeLinePattern;
    /**
     * Matcher for Type line RegEx.
     */
    private Matcher typeLineMatcher;

    // Methods //

    /**
     * The get instance class in a singleton class
     *
     * @return the instance of this class
     */
    public static DeclarationRegex getInstance() {
        return instance;
    }

    /**
     * The private constructor of this class
     */
    private DeclarationRegex() {
        typeLinePattern = Pattern.compile(DECLARATION_LINE);
    }

    /**
     * check if a given line is a declaration line
     *
     * @param termLine line suspected to be as a declaration of a parameter
     * @return true if the given line is a proper declaration line, false otherwise
     */
    public boolean checkDeclaration(String termLine) {
        typeLineMatcher = typeLinePattern.matcher(termLine);
        return typeLineMatcher.matches();
    }

    /**
     * This method checks if the variable is final
     *
     * @return if the variable declaration was over final variable
     */
    public boolean isDeclarationFinal() {
        if (typeLineMatcher.group(1) == null)
            return false;
        else return typeLineMatcher.group(1).trim().matches(MainRegex.FINAL);
    }


    /**
     * This function returns the type of the variables
     *
     * @return the type that was in the second group in the regex expression
     */
    public String getDeclarationType() {
        return typeLineMatcher.group(2);
    }

    /**
     * This function returns the names of the variables
     *
     * @return the type declaration that was after the type name
     */
    public String getDeclarationLine() {
        return typeLineMatcher.group(3);
    }

}



