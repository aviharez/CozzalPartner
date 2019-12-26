package com.cozzal.partner.entity.owner

data class Offer (
    val offer_id: String,
    val unit_number: String,
    val title: String,
    val message: String,
    val price_wd: Int,
    val price_we: Int,
    val price_wk: Int,
    val price_mn: Int
)