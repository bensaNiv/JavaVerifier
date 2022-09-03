package oop.ex6.main.regex;

import java.util.Set;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Public Class that represent the main regular expressions used in the program
 */
public class MainRegex {

    // Constants //

    // General expression //
    // or char
    public final static String OR = "|";
    // end of line char
    public final static String END = ";";
    // Comment - in a start of a comment line
    public final static String COMMENT = "(//)";
    // comma
    public final static String COMMA = ",";
    // final string representation
    public final static String FINAL = "final";


    // complex regular expressions //

    // End of line after action
    public final static String END_LINE = "\\s*;\\s*";
    //End of a line regular expression
    public final static String END_REGULAR = ".*;\\s*";
    //White Space regular expression
    public final static String OPTIONAL_SPACE = "\\s*";
    //White Space regular expression
    public final static String WHITE_SPACE = "\\s+";
    //Comment regular expression
    public final static String COMMENT_REGULAR = COMMENT + ".*";
    // Start of scope regular expression
    public final static String SCOPE_START_LINE = ".*\\{\\s*";
    // Start of scope regular expression
    public final static String SCOPE_START = "\\s*\\{\\s*";
    // End of scope regular expression
    public final static String SCOPE_END = "\\s*\\}\\s*";
    // Equal regular expression
    public final static String EQUAL = "=";
    // Return Statement regular expression
    public final static String RETURN = OPTIONAL_SPACE + "return" + OPTIONAL_SPACE + END + OPTIONAL_SPACE;
    // Method Name regular expression
    public final static String METHOD_NAME = "[a-zA-Z]+\\w*";
    // Variable Name regular expression
    public final static String VARIABLE_NAME = "(?:)[a-zA-Z]+\\w*|(?:)[_]+\\w+";
    // Regular expression of all the the types names
    public final static String TYPES_REGULAR = getTypes();
    // Regular expression for conditions with || or &&
    public final static String TERM_CONDITIONS = "&&|\\|\\|";

    // the types Regular Expression

    //int type regular expression
    public final static String INT_REGULAR = OPTIONAL_SPACE + "-?[\\d]+" + OPTIONAL_SPACE;
    // double type regular expression
    public final static String DOUBLE_REGULAR = OPTIONAL_SPACE + "-?[\\d]+(\\.\\d*)?" + OPTIONAL_SPACE;
    // char type regular expression
    public final static String CHAR_REGULAR = OPTIONAL_SPACE + "'.'" + OPTIONAL_SPACE;
    // String type regular expression
    public final static String STRING_REGULAR = OPTIONAL_SPACE + "\"(.*)\"" + OPTIONAL_SPACE;
    //boolean Type regular expression
    public final static String BOOL_REGULAR = OPTIONAL_SPACE + "(?:true|false)" + OPTIONAL_SPACE;


    // Compiled patters for types
    /**
     * int value pattern
     */
    public static final Pattern INT_TYPE = Pattern.compile(INT_REGULAR);
    /**
     * double value pattern
     */
    public static final Pattern DOUBLE_TYPE = Pattern.compile(DOUBLE_REGULAR);
    /**
     * string value pattern
     */
    public static final Pattern STRING_TYPE = Pattern.compile(STRING_REGULAR);
    /**
     * boolean value pattern
     */
    public static final Pattern BOOLEAN_TYPE = Pattern.compile("(" + BOOL_REGULAR + ")" + OR +
            "(" + DOUBLE_REGULAR + ")" + OR + "(" + INT_REGULAR + ")");
    /**
     * char value pattern
     */
    public static final Pattern CHAR_TYPE = Pattern.compile(CHAR_REGULAR);


    // Compiled patters for search
    /**
     * return statement pattern
     */
    public static final Pattern RETURN_STATEMENT_PATTERN = Pattern.compile(RETURN);
    /**
     * end line pattern
     */
    public static final Pattern END_REGULAR_PATTERN = Pattern.compile(END_REGULAR);
    /**
     * open scope pattern
     */
    public static final Pattern OPEN_SCOPE_PATTERN = Pattern.compile(SCOPE_START_LINE);
    /**
     * close scope pattern
     */
    public static final Pattern CLOSE_SCOPE_PATTERN = Pattern.compile(SCOPE_END);
    /**
     * comment pattern
     */
    public static final Pattern COMMENT_PATTERN = Pattern.compile(COMMENT_REGULAR);
    /**
     * comment pattern
     */
    public static final Pattern EMPTY_LINE_PATTERN = Pattern.compile(OPTIONAL_SPACE);

    // Methods //

    /**
     * Private Help Method that creates a regular expression of all the types that are legal in Javac
     *
     * @return string that represent the valid types in Javac
     */
    private static String getTypes() {
        StringBuilder typeRegex = new StringBuilder("(");
        Set<String> typesSet = Stream.of("int", "double", "boolean", "String", "char").
                collect(Collectors.toSet());
        for (String t : typesSet) { // create the regular expression with loop
            typeRegex.append(t).append(OR);
        }
        typeRegex = new StringBuilder(typeRegex.substring(0, typeRegex.length() - 1) + ")");
        return typeRegex.toString();
    }
}
