package com.example.productmanagment.utils.schedulers;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIUtils {
    public static DatePickerDialog getDatePickerDialog(Context context,  DatePickerDialog.OnDateSetListener onDateSetListener){
        Calendar calendar = GregorianCalendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(context, onDateSetListener, year, month, day);
    }

    public static TimePickerDialog getTimePickerDialog(Context context,   TimePickerDialog.OnTimeSetListener timePickerListener){
        return new TimePickerDialog(context, timePickerListener, 24, 0, true);
    }

    public static Calendar setCalendarTime(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    public static class DecimalDigitsInputFilter extends DigitsKeyListener{
        
    }
}
