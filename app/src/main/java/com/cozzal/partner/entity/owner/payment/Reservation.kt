package com.cozzal.partner.entity.owner.payment

data class Reservation(
    val apartment: String,
    val check_in: String,
    val check_out: String,
    val total_price: String,
    val unit: String
)