package com.cozzal.partner.entity.owner.transaction

data class Expenditure (
    val no: Int,
    val date: String,
    val description: String,
    val apartement: String,
    val unit: String,
    val qty: Int,
    val nominal: String,
    val status: String
)