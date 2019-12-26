package com.cozzal.partner.feature.owner

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cozzal.partner.R
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.FrameLayout
import android.view.MenuItem
import com.cozzal.partner.feature.owner.calendar.OwnerCalendarFragment
import com.cozzal.partner.feature.owner.home.OwnerHomeFragment
import com.cozzal.partner.feature.owner.payment.OwnerPaymentFragment
import com.cozzal.partner.feature.owner.profil.OwnerProfilFragment
import com.cozzal.partner.feature.owner.transaction.OwnerTransactionFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.getItemId()) {
                R.id.navi_home -> {
                    initFragment(OwnerHomeFragment())
                    return true
                }
                R.id.navi_transaksi -> {
                    initFragment(OwnerTransactionFragment())
                    return true
                }
                R.id.navi_kalender -> {
                    initFragment(OwnerCalendarFragment())
                    return true
                }
                R.id.navi_pembayaran -> {
                    initFragment(OwnerPaymentFragment())
                    return true
                }
                R.id.navi_profil -> {
                    initFragment(OwnerProfilFragment())
                    return true
                }
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment(OwnerHomeFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initFragment(classFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameFragment, classFragment)
        transaction.commit()
    }
}
