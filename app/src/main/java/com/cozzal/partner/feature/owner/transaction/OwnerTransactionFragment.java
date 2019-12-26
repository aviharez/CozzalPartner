package com.cozzal.partner.feature.owner.transaction;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.TextView;
import com.cozzal.partner.R;
import com.cozzal.partner.adapter.TabAdapterOwnerTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerTransactionFragment extends Fragment {

    ViewPager viewPager1;
    TabLayout tabLayout1;

    private Typeface mTypeface;
    private Typeface mTypefaceBold;


    public OwnerTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_owner_transaction, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        viewPager1 = v.findViewById(R.id.viewpager1);
        tabLayout1 = v.findViewById(R.id.tablayout1);

        tabLayout1.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout1.addTab(tabLayout1.newTab().setText("New"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Confirm"));
        tabLayout1.addTab(tabLayout1.newTab().setText("Expenditure"));

        TabAdapterOwnerTransaction adapter = new TabAdapterOwnerTransaction(getActivity().getSupportFragmentManager(), tabLayout1.getTabCount());
        viewPager1.setAdapter(adapter);

        wrapTabIndicatorToTitle(tabLayout1, 50, 50);
        viewPager1.setOffscreenPageLimit(5);
        viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));

        for (int i = 0; i < tabLayout1.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout1.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(getContext());
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                tabTextView.setText(tab.getText());
                tabTextView.setTextColor(Color.parseColor("#acacac"));

                // First tab is the selected tab, so if i==0 then set BOLD typeface
                if (i == 0) {
                    tabTextView.setTypeface(null, Typeface.BOLD);
                    tabTextView.setTextColor(Color.parseColor("#000000"));
                }

            }

        }



        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager1.setCurrentItem(tab.getPosition());
                TextView text = (TextView) tab.getCustomView();

                text.setTextColor(Color.parseColor("#000000"));
                text.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();
                text.setTextColor(Color.parseColor("#acacac"));
                text.setTypeface(null, Typeface.NORMAL);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }

    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }

}
