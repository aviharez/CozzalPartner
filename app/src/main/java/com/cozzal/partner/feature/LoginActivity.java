package com.cozzal.partner.feature;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.cozzal.partner.R;
import com.cozzal.partner.entity.LoginAuth;
import com.cozzal.partner.feature.owner.MainActivity;
import com.cozzal.partner.rest.ApiClient;
import com.cozzal.partner.rest.ApiInterface;
import com.cozzal.partner.util.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getSimpleName();

    private EditText et_email, et_password;
    private FrameLayout bt_login;
    private ProgressBar loading;
    private Dialog dialog;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);

        pref = getSharedPreferences(Config.SHARED_PREF, MODE_PRIVATE);
        final boolean login_status = pref.getBoolean("login_status", false);
        final String role = pref.getString("role", null);
        if (login_status) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

        loading = (ProgressBar) findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        bt_login = (FrameLayout) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_email.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty()) {
                    showDialog();
                    getLoginAuth(et_email.getText().toString(), et_password.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please complete your information!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getLoginAuth(String email, String password) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginAuth> call = apiService.getLoginStatus(email, password);
        call.enqueue(new Callback<LoginAuth>() {
            @Override
            public void onResponse(Call<LoginAuth> call, Response<LoginAuth> response) {
                try {
                    Log.d(TAG, "Respon: " + response.body());
                    if (response.body().getStatus().equals("success")) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("token", response.body().getToken());
                        editor.putString("role", response.body().getRole());
                        editor.putBoolean("login_status", true);
                        editor.apply();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                hideDialog();
                            }
                        }, 2500);
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect email/password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error catch: " + e.getMessage());
                    hideDialog();
                    Toast.makeText(getApplicationContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginAuth> call, Throwable t) {
                Log.e(TAG, "Error failure: " + t.getMessage());
                hideDialog();
                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog(){
        if(!dialog.isShowing())
            dialog.show();
    }

    private void hideDialog(){
        if(dialog.isShowing())
            dialog.dismiss();
    }
}
