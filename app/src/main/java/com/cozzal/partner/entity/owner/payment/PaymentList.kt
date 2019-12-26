package com.cozzal.partner.entity.owner.payment

data class PaymentList (
    val payment_id: String,
    val payment_number: String,
    val title: String,
    val date: String,
    val nominal: String,
    val status: String
)