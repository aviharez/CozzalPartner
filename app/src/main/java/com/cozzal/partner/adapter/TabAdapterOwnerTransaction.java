package com.cozzal.partner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.cozzal.partner.feature.owner.transaction.tabcontent.OwnerConfirmTransFragment;
import com.cozzal.partner.feature.owner.transaction.tabcontent.OwnerExpenditureFragment;
import com.cozzal.partner.feature.owner.transaction.tabcontent.OwnerNewTransFragment;

public class TabAdapterOwnerTransaction extends FragmentStatePagerAdapter {

    int mnooftabs;

    public TabAdapterOwnerTransaction(FragmentManager fm, int mnooftabs) {
        super(fm);
        this.mnooftabs = mnooftabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                OwnerNewTransFragment tab1 = new OwnerNewTransFragment();
                return tab1;
            case 1:
                OwnerConfirmTransFragment tab2 = new OwnerConfirmTransFragment();
                return tab2;
            case 2:
                OwnerExpenditureFragment tab3 = new OwnerExpenditureFragment();
                return tab3;

            default:
                 return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
