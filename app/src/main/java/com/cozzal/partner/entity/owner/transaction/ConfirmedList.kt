package com.cozzal.partner.entity.owner.transaction

data class ConfirmedList(
    val apartment: String,
    val check_in: String,
    val check_out: String,
    val income: String,
    val status: String,
    val tenant_name: String,
    val unit_number: String
)