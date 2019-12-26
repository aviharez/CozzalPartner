package com.cozzal.partner.feature.owner.payment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.Toast;
import com.cozzal.partner.R;
import com.cozzal.partner.adapter.owner.OwnerPaymentAdapter;
import com.cozzal.partner.entity.owner.payment.PaymentList;
import com.cozzal.partner.rest.ApiClient;
import com.cozzal.partner.rest.ApiInterface;
import com.cozzal.partner.util.Config;
import com.wang.avi.AVLoadingIndicatorView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerPaymentFragment extends Fragment {

    private final String TAG = OwnerPaymentFragment.class.getSimpleName();

    private RecyclerView rv_payment;
    private AVLoadingIndicatorView loading;
    private SharedPreferences pref;
    private OwnerPaymentAdapter adapter;
    private ArrayList<PaymentList> paymentList;
    private String token;
    private LinearLayout ll_empty;


    public OwnerPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_owner_payment, container, false);

        loading = (AVLoadingIndicatorView) v.findViewById(R.id.loading);

        ll_empty = (LinearLayout) v.findViewById(R.id.ll_empty);
        ll_empty.setVisibility(View.GONE);

        rv_payment = (RecyclerView) v.findViewById(R.id.rv_payment);
        rv_payment.setHasFixedSize(true);
        rv_payment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_payment.setItemAnimator(new DefaultItemAnimator());

        pref = getActivity().getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        token = pref.getString("token", null);
        if (!token.isEmpty()) {
            loading.setVisibility(View.VISIBLE);
            loadPaymentData("Bearer " + token);
        } else {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void loadPaymentData(String token) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PaymentList>> call = apiService.getOwnerPaymentList(token, getString(R.string.accept_json));
        call.enqueue(new Callback<List<PaymentList>>() {
            @Override
            public void onResponse(Call<List<PaymentList>> call, Response<List<PaymentList>> response) {
                try {
                    loading.setVisibility(View.GONE);
                    Log.d(TAG, "Respon: " + response.body());
                    if (response.body().isEmpty()) {
                        ll_empty.setVisibility(View.VISIBLE);
                    } else {
                        paymentList = new ArrayList<>(response.body());
                        adapter = new OwnerPaymentAdapter(getContext(), paymentList);
                        rv_payment.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    loading.setVisibility(View.GONE);
                    Log.e(TAG, "Error catch: " + e.getMessage());
                    Toast.makeText(getContext(), getText(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PaymentList>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Log.e(TAG, "Error failure: " + t.getMessage());
                Toast.makeText(getContext(), getText(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
