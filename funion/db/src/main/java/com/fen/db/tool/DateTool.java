package com.fen.db.tool;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Fei
 */
@SuppressLint("ConstantLocale")
public class DateTool {

    private final static DateFormat DF_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private final static DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private DateTool() {
    }

    public static Date parseStr2Date(String str) {
        try {
            if (str.endsWith(".SSS")) {
                return DF_SSS.parse(str);
            } else {
                return DF.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatDate2Str(Date date) {
        return DF_SSS.format(date);
    }
}