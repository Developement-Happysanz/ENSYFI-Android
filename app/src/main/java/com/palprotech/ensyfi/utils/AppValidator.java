package com.palprotech.ensyfi.utils;

/**
 * Created by Admin on 12-04-2017.
 */

public class AppValidator {

    public static boolean checkNullString(String value) {
        if (value == null)
            return false;
        else
            return value.trim().length() > 0;
    }

    public static boolean checkStringMinLength(int minValue, String value) {
        if (value == null)
            return false;
        return value.trim().length() >= minValue;
    }
}
