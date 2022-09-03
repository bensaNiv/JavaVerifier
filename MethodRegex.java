package oop.ex6.main.regex;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Singleton Class that handles declaration of new method and calling to existing method.
 */
public class MethodRegex {
    // Constants //
    /**
     * an instance of MethodRegex class
     */
    private static final MethodRegex instance = new MethodRegex();

    /**
     * a Regular expression for method call
     */
    private final static String METHOD_CALL = MainRegex.OPTIONAL_SPACE + "(" +
            MainRegex.METHOD_NAME + ")" +
            MainRegex.OPTIONAL_SPACE + "\\((" + ".*" + ")\\)" +
            MainRegex.OPTIONAL_SPACE;

    // Data Members //
    /**
     * Pattern for declaring method line RegEx.
     */
    private Pattern methodLinePattern;
    /**
     * Matcher for declaring method line RegEx.
     */
    private Matcher methodLineMatcher;
    /**
     * Pattern for calling method line RegEx.
     */
    private Pattern callPattern;
    /**
     * Matcher for calling method line RegEx.
     */
    private Matcher callMatcher;
    /**
     * Matcher for last calling method line.
     */
    private Matcher lastCallMatcher;

    // Methods //

    /**
     * The get instance class in a singleton class
     *
     * @return the instance of this class
     */
    public static MethodRegex getInstance() {
        if (instance == null)
            return new MethodRegex();
        return instance;
    }

    /**
     * The private Constructor of this class - as suits a singleton class
     */
    private MethodRegex() {
        callPattern = Pattern.compile(METHOD_CALL + MainRegex.END_LINE +
                MainRegex.OPTIONAL_SPACE);
        methodLinePattern = Pattern.compile(MainRegex.OPTIONAL_SPACE + "void" +
                MainRegex.WHITE_SPACE + MainRegex.OPTIONAL_SPACE + "(" +
                MainRegex.METHOD_NAME + ")" +
                MainRegex.OPTIONAL_SPACE + "\\((" + ".*" + ")\\)" +
                MainRegex.OPTIONAL_SPACE + MainRegex.SCOPE_START_LINE +
                MainRegex.OPTIONAL_SPACE);
    }


    /**
     * check if the given line is set of a method
     *
     * @param methodLine The line that is suspected to be a method line
     * @return true if the given line is according the method line pattern, false other wise
     */
    public boolean checkMethod(String methodLine) {
        methodLineMatcher = methodLinePattern.matcher(methodLine);
        lastCallMatcher = methodLineMatcher;
        return methodLineMatcher.matches();
    }

    /**
     * check if the given line is a call for method
     *
     * @param callLine The line that is suspected to be a call for a method
     * @return true if the given line is according the call method pattern, false other wise
     */
    public boolean checkCall(String callLine) {
        callMatcher = callPattern.matcher(callLine);
        lastCallMatcher = callMatcher;
        return callMatcher.matches();
    }

    /**
     * This function returns the last name of the latest method call
     *
     * @return the last name of a method that was used in a call
     */
    public String getLastCallName() {
        return lastCallMatcher.group(1);
    }

    /**
     * This function returns the last parameters of the latest method call
     *
     * @return the last parameters of a method that was used in a call
     */
    public String getLastCallParameters() {
        return lastCallMatcher.group(2);
    }
}
