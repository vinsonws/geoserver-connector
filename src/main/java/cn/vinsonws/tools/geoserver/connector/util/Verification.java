package cn.vinsonws.tools.geoserver.connector.util;

/**
 * @author Vinsonws
 */
public class Verification {
    public static Object validateNotNull(String argName, Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException(argName + " must not be null.");
        }
        return arg;
    }

    public static String validateNotEmptyString(String argName, String arg) {
        validateNotNull(arg, argName);
        if (arg.isEmpty()) {
            throw new IllegalArgumentException(argName + " must be a non-empty string.");
        }
        return arg;
    }

    public static String validateNullOrNotEmptyString(String argName, String arg) {
        if (arg != null && arg.isEmpty()) {
            throw new IllegalArgumentException(argName + " must be a non-empty string.");
        }
        return arg;
    }

}
