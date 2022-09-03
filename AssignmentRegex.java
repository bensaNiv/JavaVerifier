package oop.ex6.main.regex;

import oop.ex6.main.SyntaxException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Public singleton class that represent the Assignment of a value regex.
 */
public class AssignmentRegex {
    // Constants //
    /**
     * an error message if there was a parameter exception
     */
    private final String ASSIGN_FAIL = "Cant assign to parameter no value";
    /**
     * an instance of AssignmentRegex class
     */
    private static final AssignmentRegex instance = new AssignmentRegex();
    /**
     * Regular expression for the Assignment Line
     */
    private final String ASSIGNMENT_LINE = MainRegex.OPTIONAL_SPACE + "(" +
            MainRegex.VARIABLE_NAME + ")" +
            MainRegex.OPTIONAL_SPACE +
            MainRegex.EQUAL +
            MainRegex.OPTIONAL_SPACE + "(.*)"
            + MainRegex.OPTIONAL_SPACE +
            MainRegex.END_LINE;

    // Data Members //
    /**
     * Pattern for assignment RegEx.
     */
    private Pattern assignmentPattern;
    /**
     * Matcher for assignment RegEx.
     */
    private Matcher assignmentMatcher;

    // Methods //
    /**
     * The get instance class in a singleton class
     *
     * @return the instance of this class
     */
    public static AssignmentRegex getInstance() {
        return instance;
    }

    /**
     * The private constructor of this class
     */
    private AssignmentRegex() {
        assignmentPattern = Pattern.compile(ASSIGNMENT_LINE);
    }

    /**
     * check if a given line is a assignment line
     *
     * @param line line suspected to be as a assignment
     * @return true if the given line is a proper assignment line, false otherwise
     */
    public boolean checkAssignment(String line) {
        assignmentMatcher = assignmentPattern.matcher(line);
        return assignmentMatcher.matches();
    }

    /**
     * This method returns the name of the variable that was in the assignment
     *
     * @return the name of the variable that was in the assignment
     */
    public String getAssignmentName() {
        return assignmentMatcher.group(1);
    }


    /**
     * This method returns the value of the variable that was in the assignment
     *
     * @return the value that was assign to the variable that was in the assignment
     */
    public String getAssignmentValue() throws SyntaxException {
        String value = assignmentMatcher.group(2);
        if (value == null) {
            throw new SyntaxException(ASSIGN_FAIL);
        }
        return value;
    }
}
