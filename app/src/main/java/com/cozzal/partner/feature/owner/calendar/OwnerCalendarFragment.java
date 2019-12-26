package com.cozzal.partner.feature.owner.calendar;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import com.cozzal.partner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerCalendarFragment extends Fragment {

    private final String TAG = OwnerCalendarFragment.class.getSimpleName();

    private ImageButton ib_menu;

    public OwnerCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_owner_calendar, container, false);

        ib_menu = (ImageButton) v.findViewById(R.id.ib_menu);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), ib_menu);
                popupMenu.getMenuInflater().inflate(R.menu.owner_calendar_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.mn_multi_calendar:
                                Intent i = new Intent(getContext(), MultiCalendarActivity.class);
                                startActivity(i);
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return v;
    }

}
