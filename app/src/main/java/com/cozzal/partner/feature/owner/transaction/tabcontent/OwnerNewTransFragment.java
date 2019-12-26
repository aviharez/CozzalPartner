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
import com.cozzal.partner.adapter.owner.OwnerNewTransAdapter;
import com.cozzal.partner.entity.owner.transaction.BookingList;
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
public class OwnerNewTransFragment extends Fragment {

    private final String TAG = OwnerNewTransFragment.class.getSimpleName();

    private RecyclerView rv_new_trans;
    private AVLoadingIndicatorView loading;
    private SharedPreferences pref;
    private OwnerNewTransAdapter adapter;
    private ArrayList<BookingList> transList;
    private String token;


    public OwnerNewTransFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_owner_new_trans, container, false);

        loading = (AVLoadingIndicatorView) v.findViewById(R.id.loading);

        rv_new_trans = (RecyclerView) v.findViewById(R.id.rv_new_trans);
        rv_new_trans.setHasFixedSize(true);
        rv_new_trans.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_new_trans.setItemAnimator(new DefaultItemAnimator());

        pref = getActivity().getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        token = pref.getString("token", null);
        if (!token.isEmpty()) {
            loading.setVisibility(View.VISIBLE);
            loadTransData("Bearer " + token);
        } else {
            Toast.makeText(getContext(), getText(R.string.wrong), Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void loadTransData(String token) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<BookingList>> call = apiService.getOwnerBookingList(token, "application/json");
        call.enqueue(new Callback<List<BookingList>>() {
            @Override
            public void onResponse(Call<List<BookingList>> call, Response<List<BookingList>> response) {
                try {
                    loading.setVisibility(View.GONE);
                    Log.d(TAG, "Respon: " + response.body());
                    transList = new ArrayList<>(response.body());
                    adapter = new OwnerNewTransAdapter(getContext(), transList);
                    rv_new_trans.setAdapter(adapter);
                } catch (Exception e) {
                    loading.setVisibility(View.GONE);
                    Log.e(TAG, "Error catch: " + e.getMessage());
                    Toast.makeText(getContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookingList>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Log.e(TAG, "Error failure: " + t.getMessage());
                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
