package com.charitynow;


import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RetrofitClient {

    private static final String TAG = RetrofitClient.class.getSimpleName();
    public JsonElement mPlaces;
    public ArrayList<Pair<Float, Float>> mLocations;

    //creating a service for adapter with our GET class
    String PlacesAPI = "https://places.demo.api.here.com/places/v1";
    public static String token = "Token AJKnXv84fjrb0KIHawS0Tg";
    RestAdapter mRestAdapter;
    PlacesAPI mPlacesAPI;

    public RetrofitClient(){
        super();
        mRestAdapter = new RestAdapter.Builder().setEndpoint(PlacesAPI).build();
        mLocations = new ArrayList<>();
        mPlacesAPI = mRestAdapter.create(PlacesAPI.class);
    }

    public void getPlaces(Context context){
        disableSSLCertificateChecking();
        Callback<JsonElement> response = new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                Log.d(TAG, "Retrieved places from server");
                mPlaces = jsonElement;
                Log.d(TAG, jsonElement.toString());
                if(mPlaces!=null){
                    JsonObject placesObject = mPlaces.getAsJsonObject();
                    JsonObject results = placesObject.getAsJsonObject("results");
                    Log.d(TAG, results.toString());
                    JsonArray placesArray = results.getAsJsonArray("items");
                    if(placesArray!=null) {
                        for (JsonElement item: placesArray) {
                            JsonObject itemObj = item.getAsJsonObject();
                            JsonArray posArray = itemObj.getAsJsonArray("position");
                            Log.d(TAG, posArray.toString());
                            float lat = posArray.get(0).getAsFloat();
                            float lon = posArray.get(1).getAsFloat();
                            mLocations.add(new Pair(lat, lon));

                        }
                    }
                    for(Pair pair : mLocations){
                        Log.d(TAG, pair.first.toString() + "," + pair.second.toString());
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Failed to retrieve places from server");
                Log.e(TAG, error.toString());
                Log.e(TAG, error.getKind().toString());
            }
        };
        mPlacesAPI.places("41.8897,-87.6369", "atm-bank-exchange,coffee-tea,going-out,sights-museums,shopping,toilet-rest-area", "DemoAppId01082013GAL", "AJKnXv84fjrb0KIHawS0Tg", response);


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
