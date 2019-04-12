package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.support.v4.content.ContextCompat.getSystemService;

public class Utilitarios implements Serializable {

    private static String TAG = "Utilitarios";

    public  String getMd5(String s) {

        String hashtext = "";


        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            hashtext= hexString.toString();

        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        return hashtext;
    }


    public boolean conexionInternet( ConnectivityManager connectivityManager ){

        boolean ban = false;




        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d(TAG, "Estás online");

            ban = true;

            Log.d(TAG, " Estado actual: " + networkInfo.getState());

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // Estas conectado a un Wi-Fi

                Log.d("MIAPP", " Nombre red Wi-Fi: " + networkInfo.getExtraInfo());

                ban = true;
            }

        } else {


           ban = false;

            Log.d(TAG, "Estás offline");
        }

        return ban;

    }//end conexionInternet



}//Utilitarios
