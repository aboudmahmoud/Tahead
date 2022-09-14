package com.example.taehaed;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taehaed.Adapters.ImageFileApabter;
import com.example.taehaed.Pojo.ImageFileData;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Constans  {


    //Here Will Be The keys and methods to use
    public static String Token;
    public final static String TokenKey = "TokenKay";
    //Don't Change Value of this  ,, the Value is ( Bearer )  In Case U try to Change it
    public static String Bearer = "Bearer";
    //That Are Keys and data to use
    //Fill Free To Change any of The Value but not the name of or data type
    public final static String KayAttachmentSsting = "aboud";
    public final static String KayMoudlIdexRoot2 = "aboud2";
    public final static String KayMoudleOpration = "aboud3";

    public final static String DESCRIBABLE_KEY = "aboud4";
    public final static String ObjectKey = "aboud5";
    public final static String LoginKeyLoginPage = "aboud6";
    public final static String LoginKeyMain = "aboud7";
    public final static String NoteKey = "aboud8";
    public final static String IdServerFragment = "aboud9";
    public final static String ServNameKey = "abdelrhman1";
    public final static String FormFragmentKey = "abdelrhman2";
    public final static String FormNumbrActvity = "Abdelrhman3";
    public final static String ServisIDFtoA = "Abdelrhman4";
    public final static String IdkeyFrgment = "Abdelrhman5";
    public final static String ProfilekeyFragmen = "Abdelrhman6";
    public final static String ReuestToFramgent = "Abdelrhman7";
    public final static String RequsetIDSend = "Abdelrhman8";
    public final static String FormNumberSend = "Abdelrhman9";
    public final static String KeyForNamefield = "Mahmoud";
    public final static String KeyForDone = "Mahmoud1";
    public final static String DoneStatus = "Mahmoud2";
    public final static String ImageUri = "Mahmoud3";
    public final static String  NotesKey= "Mahmoud4";
    public final static String ErrorMessageValdition = "برجاء عدم ترك الحقل فارغ";
    public  static Location letion ;


    //to set Data in  Forms
    final static Calendar myCalendar = Calendar.getInstance();


    //to set all edit text without reptat the code
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

    //to make edit text that data open data paiker
    public static void updateLabel(TextInputEditText textInputEditText, Calendar myCalendar) {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        textInputEditText.setText(dateFormat.format(myCalendar.getTime()));
    }

    //to get the value form any object and convert to string
    @NonNull
    public static String getValue(Object FormSingleData) {
        if (FormSingleData == null) {
            return "";

        }
        return FormSingleData.toString();
    }

    //to check if object its not null
    public static boolean itsNotNull(Object FormSingleData) {
        return FormSingleData != null;
    }

    // to get the valau of object to number
    public static double getValueOfboleaan(Object FormSingleData) {
        return Double.parseDouble(FormSingleData.toString());
    }
//set altert without repationg many lines
    public static AlertDialog setAlertMeaage(String s, Context context) {
        AlertDialog alertDialog =
                new AlertDialog.Builder(context).setTitle(s)
                        .setMessage(context.getString(R.string.plaseWait)).setCancelable(false)
                        .setIcon(R.drawable.ic_log_out_svgrepo_com).create();
        return alertDialog;
    }
//to set error in edit text in InputText
    public static void setErrorInputText(TextInputEditText p, Context context) {
        p.setError(ErrorMessageValdition);
        Toast.makeText(context, ErrorMessageValdition, Toast.LENGTH_SHORT).show();

    }
//To set Error in textview
    public static void setErrorTextView(TextView p, Context context) {
        p.setError(ErrorMessageValdition);
        Toast.makeText(context, ErrorMessageValdition, Toast.LENGTH_SHORT).show();

    }

    // to  CheckInputfieifItsNot Valdute
    public static boolean CheckInputfield(TextInputEditText p, Context context) {
        if (p.getText().toString().equals("")) {
            setErrorInputText(p, context);
            return true;
        }
        return false;
    }

    //To go to Map to specic loaction
    public static void showMap(Uri geoLocation, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation); //lat lng or address query
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    //To get File Extnetion form Uri
    public static String getFileExtension(Uri mUri, Context context) {

        ContentResolver cr = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));


    }

//to Make Groub of View Not Editable
    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);

            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);

            }
        }
    }

    //set Valdit for phone Number The Valdtite is less than 12
    public static boolean setPhoneNumberValdtion(TextInputEditText p, Context context) {
        if (p.getText().toString().length() < 11) {
            p.setError(context.getString(R.string.PhoneNumberValdtion));
            Toast.makeText(context, context.getString(R.string.PhoneNumberValdtion), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //set Valdtie ForIDNumberis == 14
    public static boolean VadlditoForIdNumber(TextInputEditText p, Context context) {
        if (p.getText().length() != 14) {
            p.setError(context.getString(R.string.IDNumber));
            Toast.makeText(context, context.getString(R.string.IDNumber), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
//To download pdf form internt
    public static void downloadAndOpenPdf(String url, String fileName, Activity activity, Context contexte) {

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File file = new File(path, fileName);


        //  Log.d("Aboud", "downloadAndOpenPdf: " + file);
        if (file.exists()) {
            Toast.makeText(contexte, contexte.getString(R.string.filesIsExstits) + " \n" + fileName + path, Toast.LENGTH_SHORT).show();
        } else {

            DownloadManager dm = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url)).setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);


            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    contexte.unregisterReceiver(this);

                }
            };
            activity.registerReceiver(receiver, new IntentFilter(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            dm.enqueue(req);
            Toast.makeText(contexte, contexte.getString(R.string.Donwlcomplet) + " " + Environment.DIRECTORY_DOWNLOADS, Toast.LENGTH_SHORT).show();
        }
    }
//To Check if Gps is Working or not
    public static boolean checkLocation(Activity activity, Context contextcontext){
        LocationManager lm = (LocationManager)contextcontext.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(gps_enabled && network_enabled) {
            Log.d("Aboud", "checkLocation: "+gps_enabled);
            return gps_enabled;
        }
        else{
            // notify user
            new AlertDialog.Builder(contextcontext)
                    .setMessage("يرجو فتح تحديد المكان من الجهاز")
                    .setPositiveButton("فتح", (paramDialogInterface, paramInt) ->
                            contextcontext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS) ))
                    .setNegativeButton("لا",null)
                    .show();
            return false;
        }


    }

    //To getLocation
    @SuppressLint("MissingPermission")
    public static Location getLoaction(Activity activity, Context contextcontext) {
        LocationManager mLocationManager;
        mLocationManager = (LocationManager) contextcontext.getSystemService(LOCATION_SERVICE);
        android.location.LocationListener listener= location ->{
            letion=location;
            Log.d("Aboud", "getLoaction: lists"+location);
        };

        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {

       Location l = mLocationManager.getLastKnownLocation(provider);
            Log.d("Aboud", "Location l: "+ l);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
        if(bestLocation!=null)
        {
            Log.d("aboud", "getLoaction: "+bestLocation);
            mLocationManager.removeUpdates(listener);
            return bestLocation;
        }
        else{
            Log.d("aboud", "getLoaction: letion "+letion);
            if(letion!=null)
            {

                Log.d("aboud", "getLoactionDone: "+letion);
                mLocationManager.removeUpdates(listener);
                return letion;

            }else {
mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);

            return Constans.getLoaction(activity,contextcontext); }

        }

        }

  /*  //To getLocation
    @SuppressLint("MissingPermission")
    public static void getLoaction2( Context contextcontext, GetGpsLoation getGpsLoation,boolean status) {

        LocationManager mLocationManager;
        mLocationManager = (LocationManager) contextcontext.getSystemService(LOCATION_SERVICE);
        android.location.LocationListener listener= location -> {

            if(location!=null)
            {

                if(status)
                {
                    getGpsLoation.getLocation(false,null);
                }else{
                    getGpsLoation.getLocation(status,location);
                    getLoaction2(contextcontext,getGpsLoation,true);

                }




            }


        };
        if(status)
        {
            Log.d("aboud", "getLoaction2: status ture wtf");
            mLocationManager.removeUpdates(listener);
            mLocationManager=null;

        }else{
            Log.d("aboud", "getLoaction2: status false wtf");
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);

        }




    }*/
        //getPramtion , I will deleti this code
    public static void getPermations(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }

    //The ChechPremation to check Premtion for loaction , Current No using
    public static boolean ChechPremation(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }

    // Form The name We Pring Permation Camre
    public static boolean getPermationForCamre(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
            return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED;

        } else {
            return true;
        }
    }
    // Form The name We Pring Permation Location,, What is the difrent bwteen this and ChechPremation ,no dirrent <-___->
    public static boolean getPermationForLocation(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED&&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_DENIED&& ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PackageManager.PERMISSION_GRANTED);
            }
            return false;

        } else {
            return true;
        }
    }
    // Form The name We Pring Permation ForFiles
    public static boolean getPermationForFiles(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED
                &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
            return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED;

        } else {
            return true;
        }
    }

    //this To Se Multee Adpatber  and savle alot of code , like I have form 1 adapter to 4 or some time 6 sso
    // so Number Of adpatier*  3line ofCode -> 6*3 = 18  ! Thats alot << that method save this
    public  static  void setRes(RecyclerView p, RecyclerView.Adapter adapter,Context context) {
        p.setAdapter(adapter);
        p.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    //Same As Prives but for add file to addpater
    public static void setAdpater(Uri uri, String Filenmae,
                            ArrayList<ImageFileData> fileData, ImageFileApabter fileApabter) {
        fileData.add(new ImageFileData(uri, Filenmae));
        fileApabter.setImageFileDatalist(fileData);
    }

    //This To donlowdTheFile
    public static    void donlowdTheFile(ArrayList<Object> files,Activity activity,Context context) {
        ArrayList<Object> listOFUri = files;
        if (itsNotNull(listOFUri)) {

            for (int i = 0; i < listOFUri.size(); i++) {

                String name = getValue(listOFUri.get(i));
                try {
                    Constans.downloadAndOpenPdf(name, Uri.parse(name).getLastPathSegment(), activity,context);
                } catch (Exception ex) {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Aboud", "file : " + ex.getMessage());
                }
            }

        }
    }




}

