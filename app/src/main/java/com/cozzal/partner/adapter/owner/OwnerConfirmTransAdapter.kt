package com.cozzal.partner.adapter.owner

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cozzal.partner.R
import com.cozzal.partner.entity.owner.transaction.ConfirmedList
import kotlinx.android.synthetic.main.content_owner_transaction.view.*

class OwnerConfirmTransAdapter (private val context: Context, private var transList: ArrayList<ConfirmedList>)
    : RecyclerView.Adapter<OwnerConfirmTransAdapter.OwnerConfirmTransViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OwnerConfirmTransViewHolder =
        OwnerConfirmTransViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.content_owner_transaction, p0, false))

    override fun getItemCount(): Int = transList.size

    override fun onBindViewHolder(p0: OwnerConfirmTransViewHolder, p1: Int) {
        val resultItem = transList[p1]
        p0.itemView.tv_tenant_name.text = resultItem.tenant_name
        p0.itemView.tv_unit.text = resultItem.unit_number
        p0.itemView.tv_income.text = resultItem.income
        p0.itemView.tv_check_in.text = resultItem.check_in
        p0.itemView.tv_check_out.text = resultItem.check_out
        if (resultItem.status.equals("PAID")) {
            p0.itemView.image.setColorFilter(ContextCompat.getColor(context, R.color.PAID));
        } else {
            p0.itemView.image.setColorFilter(ContextCompat.getColor(context, R.color.UNPAID));
        }
    }

    inner class OwnerConfirmTransViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}