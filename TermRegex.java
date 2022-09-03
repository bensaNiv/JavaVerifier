package oop.ex6.main.regex;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Singleton Class that handles the all the term calls lines
 */
public class TermRegex {
    // Constants //
    /**
     * an instance of TermRegex class
     */
    private static final TermRegex instance = new TermRegex();

    /**
     * a Regular Expression for The Term line
     */
    private final static String TERM_LINE = MainRegex.OPTIONAL_SPACE + "(?:(?:if)|(?:while))" +
            MainRegex.OPTIONAL_SPACE + "\\((.+)\\)" + MainRegex.SCOPE_START;

    // Data Members //
    /**
     * Pattern for term line RegEx.
     */
    private Pattern termLinePattern;
    /**
     * Matcher for term line RegEx.
     */
    private Matcher termLineMatcher;

    // Methods //

    /**
     * The get instance class in a singleton class
     *
     * @return the instance of this class
     */
    public static TermRegex getInstance() {
        return instance;
    }

    /**
     * The private constructor of this class
     */
    private TermRegex() {
        termLinePattern = Pattern.compile(TERM_LINE);
    }

    /**
     * This method checks if a given line is a term line
     *
     * @param termLine line suspected to be as a "if"/"while condition
     * @return true if the given line is a proper term line, false otherwise
     */
    public boolean checkTerm(String termLine) {
        termLineMatcher = termLinePattern.matcher(termLine);
        return termLineMatcher.matches();
    }

    /**
     * This method returns the condition that was in the close brackets in the term line
     *
     * @return the condition that was in the close brackets in the term line
     */
    public String getCondition() {
        return termLineMatcher.group(1);
    }
}
