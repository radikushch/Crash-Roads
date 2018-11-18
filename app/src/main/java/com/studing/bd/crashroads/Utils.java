package com.studing.bd.crashroads;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean isEmailCorrect(String email) {
        Pattern validEmailAddressRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailAddressRegex.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordCorrect(String password1, String password2) {
        return password1.equals(password2);
    }

    public static String getDateStamp() {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }
}
