package com.charitynow;


import android.database.Cursor;
import android.database.MatrixCursor;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class RetrofitClient {
    private static final String TAG = RetrofitClient.class.getSimpleName();

    //creating a service for adapter with our GET class
    String PlacesAPI = "https://places.demo.api.here.com/places/v1";
    public static String token = "Token AJKnXv84fjrb0KIHawS0Tg";
    RestAdapter mRestAdapter = new RestAdapter.Builder().setEndpoint(PlacesAPI).build();
    PlacesAPI mPlacesAPI = mRestAdapter.create(PlacesAPI.class);

    public void getPlaces(){
        disableSSLCertificateChecking();

            try{
                JsonElement places = mPlacesAPI.places("52.5159,13.3777", "sights-museums", "DemoAppId01082013GAL", "AJKnXv84fjrb0KIHawS0Tg");
                Log.d(TAG, "Retrieved places from server");
                Log.d(TAG, places.toString());

            }
            catch(RetrofitError error) {
                Log.e(TAG, "Failed to retrieve devices from server");
                Log.e(TAG, error.toString());
            }


    }

    public void postLogin(String username, String password, Callback<JsonElement> response){
        disableSSLCertificateChecking();
        mPlacesAPI.login(username, password, response);
    }

    public void postRegister(String email,
                             String password2,
                             String password1,
                             String username,
                             Callback<JsonElement> response
    ){
        disableSSLCertificateChecking();
        mPlacesAPI.register(token, email, password2, password1, username, response);
    }


    private static void disableSSLCertificateChecking() {

        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // not implemented
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // not implemented
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                }
        };

        try {

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }

            });
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
