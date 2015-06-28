package com.charitynow;


import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
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
    public ArrayList<JsonElement> mFlowArrayList;
    public ArrayList<String> mPlaceStrings;
    public ArrayList<Pair<Float, Float>> mLocations;
    public ArrayList<Integer> mTrafficScores;

    //creating a service for adapter with our GET class
    String PlacesAPI = "https://places.demo.api.here.com/places/v1";
    String TrafficAPI = "https://traffic.cit.api.here.com/traffic/6.1";
    RestAdapter mPlacesRestAdapter;
    RestAdapter mTrafficRestAdapter;
    PlacesAPI mPlacesAPI;
    TrafficAPI mTrafficAPI;

    public RetrofitClient(){
        super();
        mPlacesRestAdapter = new RestAdapter.Builder().setEndpoint(PlacesAPI).build();
        mLocations = new ArrayList<>();
        mPlaceStrings = new ArrayList<>();
        mPlacesAPI = mPlacesRestAdapter.create(PlacesAPI.class);

        mTrafficRestAdapter = new RestAdapter.Builder().setEndpoint(TrafficAPI).build();
        mTrafficAPI = mTrafficRestAdapter.create(TrafficAPI.class);
        mFlowArrayList = new ArrayList<>();

        mTrafficScores = new ArrayList<>();


    }



    public void getFlow(final Context context){
        disableSSLCertificateChecking();
        Callback<JsonElement> response = new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                mFlowArrayList.add(jsonElement);
                int trafficScore = jsonElement.getAsJsonObject().get("RWS").getAsJsonArray().get(0).getAsJsonObject().get("RW").getAsJsonArray().get(0)
                        .getAsJsonObject().get("FIS").getAsJsonArray().get(0).getAsJsonObject().get("FI").getAsJsonArray().get(0).getAsJsonObject()
                        .get("TMC").getAsJsonObject().get("PC").getAsInt();
                if(mFlowArrayList.size() == mLocations.size())
                {
                    for(int i = 0; i < mPlaceStrings.size(); i++){
                        Data.places.add(new Place(mPlaceStrings.get(i), mLocations.get(i).first, mLocations.get(i).second));
                        Log.d(TAG, Data.places.get(i).name);
                    }

                    PlacesListActivity PLA = (PlacesListActivity) context;
                    PLA.setupLV();

//                    Firebase firebase = new Firebase("https://brilliant-torch-3400.firebaseio.com/");
//                    firebase.child("checkedInPlace").setValue(Data.places.get(0));
//                    firebase.child(Data.companyName).setValue(Data.places.get(0));
//                    for(JsonElement jsonElement1 : mFlowArrayList){
//                        Log.d(TAG, jsonElement1.toString());
//                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Failed to retrieve flow from server");
                Log.e(TAG, error.toString());
                Log.e(TAG, error.getKind().toString());
                Log.e(TAG, error.getBody().toString());
            }
        };
        for(Pair pair : mLocations){
            mTrafficAPI.flow(pair.first+","+pair.second+",100", "DemoAppId01082013GAL", "AJKnXv84fjrb0KIHawS0Tg", response);
        }


    }

    public void getPlaces(final Context context){
        disableSSLCertificateChecking();
        Callback<JsonElement> response = new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                Log.d(TAG, "Retrieved places from server");
                mPlaces = jsonElement;
                if(mPlaces!=null){
                    JsonObject placesObject = mPlaces.getAsJsonObject();
                    JsonObject results = placesObject.getAsJsonObject("results");
                    JsonArray placesArray = results.getAsJsonArray("items");
                    if(placesArray!=null) {
                        for (JsonElement item: placesArray) {
                            JsonObject itemObj = item.getAsJsonObject();
                            Log.d(TAG, item.toString());
                            String title = itemObj.get("title").getAsString();
                            mPlaceStrings.add(title);
                            JsonArray posArray = itemObj.getAsJsonArray("position");
                            float lat = posArray.get(0).getAsFloat();
                            float lon = posArray.get(1).getAsFloat();
                            Log.d(TAG, lat + "," + lon);
                            mLocations.add(new Pair(lat, lon));

                        }
                    }

                }
                getFlow(context);
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

//    public void postLogin(String username, String password, Callback<JsonElement> response){
//        disableSSLCertificateChecking();
//        mPlacesAPI.login(username, password, response);
//    }
//
//    public void postRegister(String email,
//                             String password2,
//                             String password1,
//                             String username,
//                             Callback<JsonElement> response
//    ){
//        disableSSLCertificateChecking();
//        mPlacesAPI.register(token, email, password2, password1, username, response);
//    }


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
