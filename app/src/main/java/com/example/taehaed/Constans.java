package com.example.taehaed;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Constans {
//Here Will Be The keys and methods to use

    public static String Token;
    public final static String TokenKey = "TokenKay";
    //Don't Change Value of this  ,, the Value is ( Bearer )  In Case U try to Change it
    public static String Bearer = "Bearer";

   //That Are Keys and data to use
    //Fill Free To Change any of The Value
    public final static String KayMoudlIdexRoot = "aboud";
    public final static String KayMoudlIdexRoot2 = "aboud2";
    public final static String KayMoudleOpration = "aboud3";
    public final static String DESCRIBABLE_KEY = "aboud4";
    public final static String ObjectKey = "aboud5";
    public final static String LoginKeyLoginPage = "aboud6";
    public final static String LoginKeyMain = "aboud7";
    public final static String NoteKey="aboud8";
    public final static String IdServerFragment="aboud9";
    public final static String  ServNameKey="abdelrhman1";
    public final static String  FormFragmentKey="abdelrhman2";
    public final static String  FormNumbrActvity="Abdelrhman3";
    public final static String  ServisIDFtoA="Abdelrhman4";
    public final static String  IdkeyFrgment="Abdelrhman5";
    public final static String  ProfilekeyFragmen="Abdelrhman6";
    public final static String ReuestToFramgent="Abdelrhman7";
    public final static String RequsetIDSend="Abdelrhman8";
    public final static String FormNumberSend="Abdelrhman9";
    public final static String ErrorMessageValdition="برجاء عدم ترك الحقل فارغ";
    //to set Data in  Forms
    final static Calendar myCalendar= Calendar.getInstance();

    public static void setDateForInputText(TextInputEditText p, Context context) {
        p.setOnClickListener(view1 -> {
            new DatePickerDialog(context, (datePicker, year, month, day) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                Constans.updateLabel(p, myCalendar);

            }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });
    }

    public  static void updateLabel(TextInputEditText textInputEditText, Calendar myCalendar){
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        textInputEditText.setText(dateFormat.format(myCalendar.getTime()));
    }
    @NonNull
    public static String getValue(Object FormSingleData) {
        if (FormSingleData == null) {
            return "";

        }
        return FormSingleData.toString();
    }

    public static boolean itsNotNull(Object FormSingleData) {
        return FormSingleData != null;
    }

    public  static double getValueOfboleaan(Object FormSingleData) {
        return Double.parseDouble(FormSingleData.toString());
    }
    public static  AlertDialog setAlertMeaage(String s,Context context) {
        AlertDialog alertDialog =
                new AlertDialog.Builder(context).setTitle(s)
                        .setMessage("يرجو الانتظار ....").setCancelable(false)
                        .setIcon(R.drawable.ic_log_out_svgrepo_com).create();
        return alertDialog;
    }

    public static void setErrorInputText(TextInputEditText p, Context context) {
        p.setError(ErrorMessageValdition);
        Toast.makeText(context, ErrorMessageValdition, Toast.LENGTH_SHORT).show();

    }
    public static void setErrorTextView(TextView p, Context context) {
        p.setError(ErrorMessageValdition);
        Toast.makeText(context, ErrorMessageValdition, Toast.LENGTH_SHORT).show();

    }

    public  static boolean CheckInputfield(TextInputEditText p,Context context) {
        if (p.getText().toString().equals("")) {
            setErrorInputText(p, context);
            return true;
        }
        return false;
    }

    public static   void showMap(Uri geoLocation,Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation); //lat lng or address query
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static String getFileExtension(Uri mUri,Context  context) {

        ContentResolver cr = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));


    }
}
