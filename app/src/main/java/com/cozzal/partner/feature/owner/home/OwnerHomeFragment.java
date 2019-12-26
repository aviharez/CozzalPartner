package com.cozzal.partner.feature.owner.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cozzal.partner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerHomeFragment extends Fragment {


    public OwnerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_home, container, false);
    }

}
