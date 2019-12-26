package com.cozzal.partner.feature.owner.payment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cozzal.partner.R;
import com.cozzal.partner.adapter.owner.OwnerDetPayExpenditureAdapter;
import com.cozzal.partner.adapter.owner.OwnerDetPayReservationAdapter;
import com.cozzal.partner.entity.owner.payment.DetailPayment;
import com.cozzal.partner.entity.owner.payment.Expenditure;
import com.cozzal.partner.entity.owner.payment.Reservation;
import com.cozzal.partner.rest.ApiClient;
import com.cozzal.partner.rest.ApiInterface;
import com.cozzal.partner.util.Config;
import com.wang.avi.AVLoadingIndicatorView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

public class OwnerDetailPaymentActivity extends AppCompatActivity {

    private final String TAG = OwnerDetailPaymentActivity.class.getSimpleName();

    private RecyclerView rv_reservation, rv_expenditure;
    private AVLoadingIndicatorView loading;
    private SharedPreferences pref;
    private OwnerDetPayReservationAdapter resAdapter;
    private OwnerDetPayExpenditureAdapter expAdapter;
    private ArrayList<Reservation> reservationList;
    private ArrayList<Expenditure> expenditureList;
    private String token, payment_id, status;
    private TextView tv_nominal, tv_decs, tv_res_nominal, tv_exp_nominal;
    private LinearLayout ll_res, ll_exp, ll_button ;
    private Button bt_accept, bt_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_detail_payment);

        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);

        tv_nominal = (TextView) findViewById(R.id.tv_nominal);
        tv_decs = (TextView) findViewById(R.id.tv_desc);
        tv_res_nominal = (TextView) findViewById(R.id.tv_res_nominal);
        tv_exp_nominal = (TextView) findViewById(R.id.tv_exp_nominal);

        ll_res = (LinearLayout) findViewById(R.id.ll_res);
        ll_exp = (LinearLayout) findViewById(R.id.ll_exp);
        ll_button = (LinearLayout) findViewById(R.id.ll_button);

        bt_accept = (Button) findViewById(R.id.bt_accept);
        bt_reject = (Button) findViewById(R.id.bt_reject);

        rv_reservation = (RecyclerView) findViewById(R.id.rv_reservation);
        rv_reservation.setHasFixedSize(true);
        rv_reservation.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_reservation.setItemAnimator(new DefaultItemAnimator());

        rv_expenditure = (RecyclerView) findViewById(R.id.rv_expenditure);
        rv_expenditure.setHasFixedSize(true);
        rv_expenditure.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_expenditure.setItemAnimator(new DefaultItemAnimator());

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        if (b != null) {
            payment_id = b.getString("payment_id", null);
            status = b.getString("status", null);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }

        pref = getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        token = pref.getString("token", null);
        if (!token.isEmpty()) {
            loading.setVisibility(View.VISIBLE);
            loadData(payment_id,"Bearer " + token);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }

        if (status.equals("Accepted - Paid")) {
            ll_button.setVisibility(View.GONE);
        }

        bt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                loading.bringToFront();
                final OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder().add("id", payment_id).add("is_accepted", "1").build();
                final Request request = new Request.Builder()
                        .url(ApiClient.BASE_URL + "owner/confirm_payment")
                        .header("Authorization", "Bearer " + token)
                        .header("Accept", getString(R.string.accept_json))
                        .post(body).build();
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            okhttp3.Response response = client.newCall(request).execute();
                            Log.d(TAG, "Respon: " + response.body());
                            String jsonData = response.body().string();
                            JSONObject jObject = new JSONObject(jsonData);
                            String status = jObject.getString("status");
                            if (status.equals("success")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Payment Accepted", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else {
                                loading.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Failed to Accept Payment", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        bt_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                loading.bringToFront();
                final OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder().add("id", payment_id).add("is_accepted", "0").build();
                final Request request = new Request.Builder()
                        .url(ApiClient.BASE_URL + "owner/confirm_payment")
                        .header("Authorization", "Bearer " + token)
                        .header("Accept", getString(R.string.accept_json))
                        .post(body).build();
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            okhttp3.Response response = client.newCall(request).execute();
                            Log.d(TAG, "Respon: " + response.body());
                            String jsonData = response.body().string();
                            JSONObject jObject = new JSONObject(jsonData);
                            String status = jObject.getString("status");
                            if (status.equals("success")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Payment Rejected", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else {
                                loading.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Failed to Reject Payment", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

    }

    private void loadData(String payment_id, String token) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DetailPayment> call = apiService.getOwnerDetailPayment(payment_id, token, getString(R.string.accept_json));
        call.enqueue(new Callback<DetailPayment>() {
            @Override
            public void onResponse(Call<DetailPayment> call, Response<DetailPayment> response) {
                try {
                    loading.setVisibility(View.GONE);
                    Log.d(TAG, "Respon: " + response.body());

                    tv_decs.setText(response.body().getDescription());
                    tv_nominal.setText(response.body().getNominal_paid());
                    tv_res_nominal.setText(response.body().getReservation_total_price());
                    tv_exp_nominal.setText(response.body().getExpenditure_total_price());

                    if (response.body().getReservation_list().isEmpty()) ll_res.setVisibility(View.GONE);
                    if (response.body().getExpenditure_list().isEmpty()) ll_exp.setVisibility(View.GONE);

                    reservationList = new ArrayList<>(response.body().getReservation_list());
                    resAdapter = new OwnerDetPayReservationAdapter(getApplicationContext(), reservationList);
                    rv_reservation.setAdapter(resAdapter);

                    expenditureList = new ArrayList<>(response.body().getExpenditure_list());
                    expAdapter = new OwnerDetPayExpenditureAdapter(getApplicationContext(), expenditureList);
                    rv_expenditure.setAdapter(expAdapter);

                } catch (Exception e) {
                    loading.setVisibility(View.GONE);
                    Log.e(TAG, "Error catch: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), getText(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailPayment> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Log.e(TAG, "Error failure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), getText(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
