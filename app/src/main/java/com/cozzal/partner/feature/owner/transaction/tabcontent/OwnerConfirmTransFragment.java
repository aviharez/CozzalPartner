package com.cozzal.partner.feature.owner.transaction.tabcontent;


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

import android.widget.Toast;
import com.cozzal.partner.R;
import com.cozzal.partner.adapter.owner.OwnerConfirmTransAdapter;
import com.cozzal.partner.entity.owner.transaction.ConfirmedList;
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
public class OwnerConfirmTransFragment extends Fragment {

    private final String TAG = OwnerConfirmTransFragment.class.getSimpleName();

    private RecyclerView rv_confirm_trans;
    private AVLoadingIndicatorView loading;
    private SharedPreferences pref;
    private OwnerConfirmTransAdapter adapter;
    private ArrayList<ConfirmedList> transList;
    private String token;


    public OwnerConfirmTransFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_owner_confirm_trans, container, false);

        loading = (AVLoadingIndicatorView) v.findViewById(R.id.loading);

        rv_confirm_trans = (RecyclerView) v.findViewById(R.id.rv_confirm_trans);
        rv_confirm_trans.setHasFixedSize(true);
        rv_confirm_trans.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_confirm_trans.setItemAnimator(new DefaultItemAnimator());

        pref = getActivity().getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        token = pref.getString("token", null);
        if (!token.isEmpty()) {
            loading.setVisibility(View.VISIBLE);
            loadTransData("Bearer " + token);
        } else {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void loadTransData(String token) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ConfirmedList>> call = apiService.getOwnerConfirmedList(token, "application/json");
        call.enqueue(new Callback<List<ConfirmedList>>() {
            @Override
            public void onResponse(Call<List<ConfirmedList>> call, Response<List<ConfirmedList>> response) {
                try {
                    loading.setVisibility(View.GONE);
                    Log.d(TAG, "Respon: " + response.body());
                    transList = new ArrayList<>(response.body());
                    adapter = new OwnerConfirmTransAdapter(getContext(), transList);
                    rv_confirm_trans.setAdapter(adapter);
                } catch (Exception e) {
                    loading.setVisibility(View.GONE);
                    Log.e(TAG, "Error catch: " + e.getMessage());
                    Toast.makeText(getContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ConfirmedList>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Log.e(TAG, "Error failure: " + t.getMessage());
                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
