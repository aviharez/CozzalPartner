package com.cozzal.partner.adapter.owner

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cozzal.partner.R
import com.cozzal.partner.entity.owner.payment.PaymentList
import com.cozzal.partner.feature.owner.payment.OwnerDetailPaymentActivity
import kotlinx.android.synthetic.main.content_owner_payment.view.*

class OwnerPaymentAdapter (private val context: Context, private var paymentList: ArrayList<PaymentList>)
    : RecyclerView.Adapter<OwnerPaymentAdapter.OwnerPaymentViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OwnerPaymentViewHolder =
        OwnerPaymentViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.content_owner_payment, p0, false))

    override fun getItemCount(): Int = paymentList.size

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(p0: OwnerPaymentViewHolder, p1: Int) {
        val resultItem = paymentList[p1]
        p0.itemView.tv_title.text = resultItem.title
        p0.itemView.tv_payment_number.text = resultItem.payment_number
        p0.itemView.tv_date.text = resultItem.date
        p0.itemView.tv_status.text = resultItem.status
        p0.itemView.tv_nominal.text = resultItem.nominal
        if (resultItem.status.equals("Accepted - Paid")) {
            p0.itemView.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.PAID))
            p0.itemView.v_cover.background = ContextCompat.getDrawable(context, R.color.PAID)
            p0.itemView.tv_status.setTextColor(ContextCompat.getColor(context, R.color.PAID))
        } else {
            p0.itemView.iv_status.setColorFilter(ContextCompat.getColor(context, R.color.Waiting))
            p0.itemView.v_cover.background = ContextCompat.getDrawable(context, R.color.Waiting)
            p0.itemView.tv_status.setTextColor(ContextCompat.getColor(context, R.color.Waiting))
        }
        p0.itemView.linear.setOnClickListener {
            val i = Intent(context, OwnerDetailPaymentActivity::class.java)
            val b = Bundle()
            b.putString("payment_id", resultItem.payment_id)
            b.putString("status", resultItem.status)
            i.putExtras(b)
            context.startActivity(i)
        }
    }

    inner class OwnerPaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}