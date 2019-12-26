package com.cozzal.partner.feature.owner.profil;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.cozzal.partner.R;
import com.cozzal.partner.feature.LoginActivity;
import com.cozzal.partner.rest.ApiClient;
import com.cozzal.partner.util.Config;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerProfilFragment extends Fragment {

    private final String TAG = OwnerProfilFragment.class.getSimpleName();

    private LinearLayout logout;
    private SharedPreferences pref;
    private String token;
    private ProgressBar loading;


    public OwnerProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_owner_profil, container, false);

        pref = getActivity().getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        token = pref.getString("token", null);

        loading = (ProgressBar) v.findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        logout = (LinearLayout) v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //yes clicked
                                loading.setVisibility(View.VISIBLE);
                                loading.bringToFront();
                                final SharedPreferences.Editor edit = pref.edit();
                                final OkHttpClient client = new OkHttpClient();
                                final Request request = new Request.Builder().url(ApiClient.BASE_URL + "logout").header("Authorization", "Bearer " + token).build();
                                final Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Response response = client.newCall(request).execute();
                                            Log.d(TAG, "Respon: " + response.body());
                                            String jsonData = response.body().string();
                                            JSONObject jObject = new JSONObject(jsonData);
                                            String status = jObject.getString("logout");
                                            if (status.equals("success")) {
                                                edit.clear();
                                                edit.putBoolean("login", false);
                                                edit.apply();
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent i = new Intent(getActivity(), LoginActivity.class);
                                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(i);
                                                        getActivity().finish();
                                                    }
                                                });
                                            } else {
                                                loading.setVisibility(View.GONE);
                                                Toast.makeText(getContext(), "Failed to log out", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //no
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Anda yakin ingin log out?").setPositiveButton("Ya", dialogClickListener).setNegativeButton("Tidak", dialogClickListener).show();
            }
        });

        return v;
    }

}
