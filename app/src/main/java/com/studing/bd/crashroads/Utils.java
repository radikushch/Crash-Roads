package com.studing.bd.crashroads;

import android.content.Context;
import android.graphics.Bitmap;
import android.telephony.TelephonyManager;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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

    public static byte[] bitmapToArray(Bitmap userPhotoBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            assert tm != null;
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception ignored) { }
        return null;
    }

    public static int getAge(String birthDate) {
        int birthYear = Integer.parseInt(birthDate.split("/")[2]);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return year - birthYear;
    }
}
