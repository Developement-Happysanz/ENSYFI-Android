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

    public static String checkEditTextValid100AndA(String value) {
        boolean check = value.matches("\\d+");
        String EditTextValid;
        if (check) {
            int validMark = 100;
            int mark = Integer.parseInt(value);
            if (mark <= -1 || mark >= validMark + 1) {
                EditTextValid = "NotValidMark";
            } else {
                EditTextValid = "valid";
            }
        } else {
            if (!value.contentEquals("AB")) {
                EditTextValid = "NotValidAbsent";
            } else {
                EditTextValid = "valid";
            }
        }
        return EditTextValid;
    }

    public static String checkEditTextValidInternalAndA(String value) {
        boolean check = value.matches("\\d+");
        String EditTextValid;
        if (check) {
            int validMark = 40;
            int mark = Integer.parseInt(value);
            if (mark <= -1 || mark >= validMark + 1) {
                EditTextValid = "NotValidMark";
            } else {
                EditTextValid = "valid";
            }
        } else {
            if (!value.contentEquals("A")) {
                EditTextValid = "NotValidAbsent";
            } else {
                EditTextValid = "valid";
            }
        }
        return EditTextValid;
    }

    public static String checkEditTextValidExternalAndA(String value) {
        boolean check = value.matches("\\d+");
        String EditTextValid;
        if (check) {
            int validMark = 60;
            int mark = Integer.parseInt(value);
            if (mark <= -1 || mark >= validMark + 1) {
                EditTextValid = "NotValidMark";
            } else {
                EditTextValid = "valid";
            }
        } else {
            if (!value.contentEquals("A")) {
                EditTextValid = "NotValidAbsent";
            } else {
                EditTextValid = "valid";
            }
        }
        return EditTextValid;
    }
}
