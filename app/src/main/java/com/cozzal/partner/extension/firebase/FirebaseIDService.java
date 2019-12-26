package com.cozzal.partner.extension.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.cozzal.partner.util.Config;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();

        //saving reg id to shared preference
        storeRegIdInPref(token);

        // sending reg id to your server
        registerToken(token);

        //notify UI that reg has complete
        Intent regComplete = new Intent(Config.REGISTRATION_COMPLETE);
        regComplete.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(regComplete);

        Log.d(TAG, "Refreshed token: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.apply();
    }

    private void registerToken(String token) {
//        OkHttpClient client = new OkHttpClient();
        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        final int login = pref.getInt("login", 0);
//        if (login == 1){
//            String username = pref.getString("username", "gagal");
//            RequestBody body = new FormBody.Builder().add("Token", token).add("username", username).build();
//
//            Request request = new Request.Builder().url("http://transaksi.cozzal.com/fcm/registers.php").post(body).build();
//            try {
//                client.newCall(request).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }

}
