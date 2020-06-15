package com.mohamed.halim.essa.earthquake.util;

import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.mohamed.halim.essa.earthquake.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DataBindingUtil {
    /**
     * set the text to the givig text view to 0.00 format of mag
     *
     * @param textView : to set the text
     * @param mag      : to format
     */
    @BindingAdapter("magnitude")
    public static void setMagnitude(TextView textView, float mag) {
        String s = String.format("%.2f", mag);
        textView.setText(s);
    }


    /**
     * format the given Time in HH:mm a format
     *
     * @param time     a date object created by unix time stamp
     * @param textView set text to time in (HH:mm a)
     */
    @BindingAdapter("formatTime")
    public static void formattedTime(TextView textView, Long time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");
        Date date = new Date(time);
        textView.setText(timeFormat.format(date));
    }

    /**
     * format the given date in MMM DD, yyyy format
     *
     * @param time     a date object created by unix time stamp
     * @param textView to set the text the date after format
     */
    @BindingAdapter("formatDate")
    public static void formattedDate(TextView textView, Long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
        Date date = new Date(time);
        textView.setText(dateFormat.format(date));
    }

    /**
     * set the background to color match with magitude
     *
     * @param textView  : to set the back ground to
     * @param magnitude : to get the color matches it
     */
    @BindingAdapter("magnitudeColor")
    public static void getMagnitudeColor(TextView textView, float magnitude) {
        int color;
        switch ((int) magnitude) {
            case 0:
            case 1:
                color = R.color.magnitude1;
                break;
            case 2:
                color = R.color.magnitude2;
                break;
            case 3:
                color = R.color.magnitude3;
                break;
            case 4:
                color = R.color.magnitude4;
                break;
            case 5:
                color = R.color.magnitude5;
                break;
            case 6:
                color = R.color.magnitude6;
                break;
            case 7:
                color = R.color.magnitude7;
                break;
            case 8:
                color = R.color.magnitude8;
                break;
            case 9:
                color = R.color.magnitude9;
                break;
            default:
                color = R.color.magnitude10plus;
        }
        GradientDrawable magnitudeCircle = (GradientDrawable) textView.getBackground();
        magnitudeCircle.setColor(ContextCompat.getColor(textView.getContext(), color));
    }


}
