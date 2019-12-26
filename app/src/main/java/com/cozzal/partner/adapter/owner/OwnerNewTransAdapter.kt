package com.cozzal.partner.adapter.owner

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cozzal.partner.R
import com.cozzal.partner.entity.owner.transaction.BookingList
import kotlinx.android.synthetic.main.content_owner_transaction.view.*

class OwnerNewTransAdapter(private val context: Context, private var transList: ArrayList<BookingList>)
    : RecyclerView.Adapter<OwnerNewTransAdapter.OwnerNewTransViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OwnerNewTransViewHolder =
        OwnerNewTransViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.content_owner_transaction, p0, false))

    override fun getItemCount(): Int = transList.size

    override fun onBindViewHolder(p0: OwnerNewTransViewHolder, p1: Int) {
        val resultItem = transList[p1]
        p0.itemView.tv_tenant_name.text = resultItem.tenant_name
        p0.itemView.tv_unit.text = resultItem.unit_number
        p0.itemView.tv_income.text = resultItem.income
        p0.itemView.tv_check_in.text = resultItem.check_in
        p0.itemView.tv_check_out.text = resultItem.check_out
    }

    inner class OwnerNewTransViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}