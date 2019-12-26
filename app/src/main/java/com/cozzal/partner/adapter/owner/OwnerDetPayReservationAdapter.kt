package com.cozzal.partner.adapter.owner

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cozzal.partner.R
import com.cozzal.partner.entity.owner.payment.Reservation
import kotlinx.android.synthetic.main.content_owner_detail_payment_reservation.view.*

class OwnerDetPayReservationAdapter (private val context: Context, private var reservationList: ArrayList<Reservation>)
    : RecyclerView.Adapter<OwnerDetPayReservationAdapter.OwnerDetPayResViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OwnerDetPayResViewHolder =
        OwnerDetPayResViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.content_owner_detail_payment_reservation, p0, false))

    override fun getItemCount(): Int = reservationList.size

    override fun onBindViewHolder(p0: OwnerDetPayReservationAdapter.OwnerDetPayResViewHolder, p1: Int) {
        val resultItem = reservationList[p1]
        p0.itemView.tv_unit.text = resultItem.unit
        p0.itemView.tv_total_price.text = resultItem.total_price
        p0.itemView.tv_check_in.text = resultItem.check_in
        p0.itemView.tv_check_out.text = resultItem.check_out
    }

    inner class OwnerDetPayResViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}