package com.sfamobile.dahlia.sfamobile;

/**
 * Created by Admin on 17-09-2016.
 */

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Utils {

    private static String mDateFormatForImageFile = "yyyyMMdd_HHmmss";
    private static String mFileExtnForImage = ".jpg";
    public static final String INDIA_COUNTRY_CODE_PREFIX = "+91";
    public static final String CONSTANT_ZERO = "0";
    public static final int MOBILE_NO_LENGTH = 10;
    public static final String IS_NUMERIC_CHECK_EXP = "^[0-9]*$";


    private static final String LIME_ACCESSIBILITY_ID = "com.app.lime/.service.AxisAutoFillAccessibilityService";
    private static final String LIME_INPUT_METHOD_ID = "com.app.lime/.service.AxisAutoFillInputService";

    private static String TAG = "Utils";

    public static String getDateWithDaySuffix(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        String daySuffix = getDayNumberSuffix(calendar.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat parseFormat = new SimpleDateFormat("E, dd'" + daySuffix + "' MMM yyyy");
        return parseFormat.format(calendar.getTime());
    }

    public static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }

        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    /*
     * TODO: Need to add string input validation
     */
    public static String getIndianCurrencyCommaSeparatedWithoutSymbol(String amount,
                                                                      boolean withDecimal) {
        String formattedMoney = amount;
        try {
            Double money = Double.parseDouble(amount);
            if (withDecimal) {
                DecimalFormat formatter = (DecimalFormat) NumberFormat
                        .getCurrencyInstance(new Locale("en", "IN"));
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setCurrencySymbol("");
                formatter.setDecimalFormatSymbols(symbols);
                formattedMoney = formatter.format(money);
            } else {
                NumberFormat formatter = DecimalFormat.getInstance(new Locale("en", "IN"));
                formattedMoney = formatter.format(money);
            }
        } catch (NumberFormatException e) {
            Log.e("Utils", e.getMessage());
        }

        return formattedMoney;
    }

    /*
     * TODO: Need to add string input validation
     */
    public static String getIndianCurrencyFormatted(String amount, boolean useNewSymbol) {
        String formattedMoney = amount;
        try {
            Double money = null;
            try {
                money = Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                money = 0.0;
            }
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale(
                    "en", "IN"));
            if (useNewSymbol) {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setCurrencySymbol("\u20B9");
                formatter.setDecimalFormatSymbols(symbols);
            }
            formattedMoney = formatter.format(money);
        } catch (NumberFormatException e) {
            Log.e("Utils", e.getMessage());
        }

        return formattedMoney;
    }

    /*
     * Gives formatted indian currency without decimal
     */
    public static String getIndianCurrencyFormattedWithoutDecimal(String amount,
                                                                  boolean useNewSymbol) {
        String formattedMoney = amount;
        try {
            Double money = null;
            try {
                money = Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                money = 0.0;
            }
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale(
                    "en", "IN"));
            if (useNewSymbol) {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setCurrencySymbol("\u20B9");
                formatter.setDecimalFormatSymbols(symbols);
            } else {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setCurrencySymbol("");
                formatter.setDecimalFormatSymbols(symbols);
            }
            formattedMoney = formatter.format(money);
        } catch (NumberFormatException e) {
            Log.e("Utils", e.getMessage());
        }

        return formattedMoney.substring(0, formattedMoney.lastIndexOf("."));
    }


    public static String prefixZeroToANumber(int number) {
        if (number < 10) {
            return new StringBuilder().append(0).append(number).toString();
        } else {
            return String.valueOf(number);
        }

    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static String converTimein12HourFormat(int hour, int minute) {
        String timeSuffix = " AM";

        if (hour >= 12) {
            timeSuffix = " PM";
        }
        if (hour > 12) {
            hour = hour - 12;
        }

        return new StringBuilder().append(prefixZeroToANumber(hour)).append(":")
                .append(prefixZeroToANumber(minute)).append(timeSuffix).toString();
    }


    public static File createImageFile() throws IOException {
        // Create image for writing bitmap
        String timeStamp = new SimpleDateFormat(mDateFormatForImageFile).format(new Date());
        File imageFile = File.createTempFile(timeStamp, mFileExtnForImage);
        return imageFile;
    }


    public static String getNameInitials(String Name) {

        if (!TextUtils.isEmpty(Name)) {
            String[] parts = Name.split(" ");
            String part1 = parts[0];
            String StrName = "";
            if (!part1.trim().isEmpty()) {
                StrName += part1.charAt(0);
            }
            if (parts.length > 1) {
                String part2 = parts[1];

                if (!part2.trim().isEmpty()) {
                    StrName += part2.charAt(0);
                }
            }
            Name = StrName;
        }
        return Name;
    }

    public static String validateMobileNumber(String mobileno) {
        if (!TextUtils.isEmpty(mobileno)) {
            if (mobileno.startsWith(INDIA_COUNTRY_CODE_PREFIX)) {
                mobileno = mobileno.substring(INDIA_COUNTRY_CODE_PREFIX.length());
            }
            if (mobileno.startsWith(CONSTANT_ZERO)) {
                mobileno = mobileno.substring(CONSTANT_ZERO.length());
            }

            mobileno = mobileno.replaceAll("[()\\-\\s]", "");

            if ((mobileno.length() == MOBILE_NO_LENGTH) && (mobileno.matches(IS_NUMERIC_CHECK_EXP))) {
                return mobileno;
            }
        }
        return mobileno;
    }


    public static boolean isVaildAmount(String amount, String availableBlance) {
        boolean result = false;
        int iAmount, iAvailableBal;
        try {
            iAmount = Integer.parseInt(amount);
            iAvailableBal = (int) Double.parseDouble(availableBlance);
            if (iAvailableBal >= iAmount) {
                result = true;
            }
        } catch (Exception ex) {
            //amount is not valid ignore it
        }
        return result;
    }


    public static String getFirstName(String Name) {

        if (!TextUtils.isEmpty(Name)) {
            String[] parts = Name.split(" ");
            if (parts != null && parts.length > 0) {
                Name = parts[0];
            }
        }
        return Name;
    }

    public static String getLastName(String Name) {
        String lastName = "";

        if (!TextUtils.isEmpty(Name)) {
            String[] parts = Name.split(" ");
            if (parts != null) {
                int len = parts.length;
                if (len > 1) {
                    lastName = parts[len - 1];
                }
            }
        }
        return lastName;
    }


    public static String getUserName(String firstName, String secondName) {
        String fullName = "";
        if (TextUtils.isEmpty(firstName) && TextUtils.isEmpty(secondName)) {

        } else if (TextUtils.isEmpty(firstName)) {
            fullName = secondName;
        } else if (TextUtils.isEmpty(secondName)) {
            fullName = firstName;
        } else {
            fullName = firstName + " " + secondName;
        }
        return fullName;
    }

    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

}

