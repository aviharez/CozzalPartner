package com.cozzal.partner.entity.owner.payment

data class DetailPayment(
    val description: String,
    val expenditure_list: List<Expenditure>,
    val expenditure_total_price: String,
    val nominal: String,
    val nominal_paid: String,
    val reservation_list: List<Reservation>,
    val reservation_total_price: String
)