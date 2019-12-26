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
import com.cozzal.partner.adapter.owner.OwnerExpenditureAdapter;
import com.cozzal.partner.entity.owner.transaction.Expenditure;
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
public class OwnerExpenditureFragment extends Fragment {

    private final String TAG = OwnerExpenditureFragment.class.getSimpleName();

    private RecyclerView rv_expenditure;
    private AVLoadingIndicatorView loading;
    private SharedPreferences pref;
    private OwnerExpenditureAdapter adapter;
    private ArrayList<Expenditure> expendList;
    private String token;


    public OwnerExpenditureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_owner_expenditure, container, false);

        loading = (AVLoadingIndicatorView) v.findViewById(R.id.loading);

        rv_expenditure = (RecyclerView) v.findViewById(R.id.rv_expenditure);
        rv_expenditure.setHasFixedSize(true);
        rv_expenditure.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_expenditure.setItemAnimator(new DefaultItemAnimator());

        pref = getActivity().getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        token = pref.getString("token", null);
        if (!token.isEmpty()) {
            loading.setVisibility(View.VISIBLE);
            loadExpenditure("Bearer " + token);
        } else {
            Toast.makeText(getContext(), getText(R.string.wrong), Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void loadExpenditure(String token) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Expenditure>> call = apiService.getOwnerExpenditureList(token, getString(R.string.accept_json));
        call.enqueue(new Callback<List<Expenditure>>() {
            @Override
            public void onResponse(Call<List<Expenditure>> call, Response<List<Expenditure>> response) {
                try {
                    loading.setVisibility(View.GONE);
                    Log.d(TAG, "Respon: " + response.body());
                    expendList = new ArrayList<>(response.body());
                    adapter = new OwnerExpenditureAdapter(getContext(), expendList);
                    rv_expenditure.setAdapter(adapter);
                } catch (Exception e) {
                    loading.setVisibility(View.GONE);
                    Log.e(TAG, "Error catch: " + e.getMessage());
                    Toast.makeText(getContext(), getText(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Expenditure>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Log.e(TAG, "Error failure: " + t.getMessage());
                Toast.makeText(getContext(), getText(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
