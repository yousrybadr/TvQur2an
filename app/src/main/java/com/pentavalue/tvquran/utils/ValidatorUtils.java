package com.pentavalue.tvquran.utils;

import android.util.Patterns;

import java.util.List;

/**
 * Created by Rabie on 5/2/2017.
 */

public class ValidatorUtils {
    public static boolean isValidEmail(CharSequence charSequence) {
        if (charSequence == null)
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() <= 0 || charSequence.equals(""))
            return true;
        return false;
    }

    public static boolean isEmpty(List<?> list) {
        if (list == null || list.size() <= 0)
            return true;
        return false;
    }

    public static boolean isValidLength(CharSequence charSequence, int length) {
        if (charSequence.length() == length)
            return true;
        return false;
    }

    public static boolean isEmpty(Object object) {
        if (object == null)
            return true;
        return false;
    }
}
