package com.cozzal.partner.adapter.owner

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cozzal.partner.R
import com.cozzal.partner.entity.owner.transaction.Expenditure
import kotlinx.android.synthetic.main.content_owner_expenditure.view.*

class OwnerExpenditureAdapter (private val context: Context, private var expenList: ArrayList<Expenditure>)
    : RecyclerView.Adapter<OwnerExpenditureAdapter.OwnerExpenditureViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OwnerExpenditureViewHolder =
        OwnerExpenditureViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.content_owner_expenditure, p0, false))

    override fun getItemCount(): Int  = expenList.size

    override fun onBindViewHolder(p0: OwnerExpenditureViewHolder, p1: Int) {
        val resultItem = expenList[p1]
        p0.itemView.tv_date.text = resultItem.date
        p0.itemView.tv_unit.text = resultItem.unit
        p0.itemView.tv_desc.text = resultItem.description
        p0.itemView.tv_nominal.text = resultItem.nominal
        if (resultItem.status.equals("PAID")) {
            p0.itemView.image.setColorFilter(ContextCompat.getColor(context, R.color.PAID));
        } else {
            p0.itemView.image.setColorFilter(ContextCompat.getColor(context, R.color.UNPAID));
        }
    }

    inner class OwnerExpenditureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}