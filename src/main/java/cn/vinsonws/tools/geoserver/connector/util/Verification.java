package cn.vinsonws.tools.geoserver.connector.util;

/**
 * @author Vinsonws
 */
public class Verification {
    public static void validateNotNull(String argName, Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException(argName + " must not be null.");
        }
    }

    public static void validateNotEmptyString(String argName, String arg) {
        validateNotNull(arg, argName);
        if (arg.isEmpty()) {
            throw new IllegalArgumentException(argName + " must be a non-empty string.");
        }
    }

    public static void validateNullOrNotEmptyString(String argName, String arg) {
        if (arg != null && arg.isEmpty()) {
            throw new IllegalArgumentException(argName + " must be a non-empty string.");
        }
    }

}
