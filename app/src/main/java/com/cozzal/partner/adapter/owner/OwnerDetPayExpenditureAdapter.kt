package com.cozzal.partner.adapter.owner

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cozzal.partner.R
import com.cozzal.partner.entity.owner.payment.Expenditure
import kotlinx.android.synthetic.main.content_owner_detail_payment_expenditure.view.*

class OwnerDetPayExpenditureAdapter (private val context: Context, private var expenditureList: ArrayList<Expenditure>)
    : RecyclerView.Adapter<OwnerDetPayExpenditureAdapter.OwnerDetPayExpViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OwnerDetPayExpViewHolder =
        OwnerDetPayExpViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.content_owner_detail_payment_expenditure, p0, false))

    override fun getItemCount(): Int = expenditureList.size

    override fun onBindViewHolder(p0: OwnerDetPayExpViewHolder, p1: Int) {
        val resultItem = expenditureList[p1]
        p0.itemView.tv_note.text = resultItem.note
        p0.itemView.tv_price.text = resultItem.price
        p0.itemView.tv_date.text = resultItem.date
        p0.itemView.tv_total.text = resultItem.total
    }

    inner class OwnerDetPayExpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}