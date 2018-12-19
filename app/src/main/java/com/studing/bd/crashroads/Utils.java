package com.studing.bd.crashroads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.telephony.TelephonyManager;

import com.studing.bd.crashroads.model.RouteQuality;

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

    public static byte[] bitmapToArray(Bitmap userPhotoBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap arrayToBitmap(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
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
        return " ";
    }

    public static String getAge(String birthDate) {
        if(birthDate == null || birthDate.length() == 0){
            return " ";
        }
        int birthYear = Integer.parseInt(birthDate.split("/")[2]);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.valueOf(year - birthYear);
    }

    public static int convertRouteQuality(RouteQuality quality) {
        if(quality.equals(RouteQuality.Low)) return 1;
        if(quality.equals(RouteQuality.Bad)) return 2;
        if(quality.equals(RouteQuality.Medium)) return 3;
        if(quality.equals(RouteQuality.Good)) return 4;
        if(quality.equals(RouteQuality.High)) return 5;
        return 0;
    }

    public static int getPolylineColor(int rating) {
        switch(rating){
            case 1:
                return Color.RED;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.GREEN;
        }
        return Color.YELLOW;
    }
}
