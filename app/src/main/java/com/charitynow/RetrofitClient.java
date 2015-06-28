package com.charitynow;


import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

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
import retrofit.mime.TypedFile;

public class RetrofitClient {
    private static final String TAG = RetrofitClient.class.getSimpleName();

    //creating a service for adapter with our GET class
    String API = "apiendpoint.com/";
    //public static String token = "Token 547bc40f9e77c7a4e906e5072bd8a71d8426dd57"; //Adam's
    public static String token = "Token something"; //Krish's
    RestAdapter mRestAdapter = new RestAdapter.Builder().setEndpoint(API).build();
    PlacesAPI mPlacesAPI = mRestAdapter.create(PlacesAPI.class);

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
