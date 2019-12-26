package com.cozzal.partner.rest;

import com.cozzal.partner.entity.LoginAuth;
import com.cozzal.partner.entity.owner.*;
import com.cozzal.partner.entity.owner.payment.DetailPayment;
import com.cozzal.partner.entity.owner.payment.PaymentList;
import com.cozzal.partner.entity.owner.transaction.BookingList;
import com.cozzal.partner.entity.owner.transaction.ConfirmedList;
import com.cozzal.partner.entity.owner.transaction.Expenditure;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginAuth> getLoginStatus(@Field("email") String email,
                                   @Field("password") String password);

    @GET("owner/bookingList")
    Call<List<BookingList>> getOwnerBookingList(@Header("Authorization") String token, @Header("Accept") String accept);

    @GET("owner/confirmedBooking")
    Call<List<ConfirmedList>> getOwnerConfirmedList(@Header("Authorization") String token, @Header("Accept") String accept);

    @GET("owner/offers")
    Call<List<Offer>> getOwnerOffersList(@Header("Authorization") String token, @Header("Accept") String accept);

    @GET("owner/expenditure")
    Call<List<Expenditure>> getOwnerExpenditureList(@Header("Authorization") String token, @Header("Accept") String accept);

    @GET("owner/payment")
    Call<List<PaymentList>> getOwnerPaymentList(@Header("Authorization") String token, @Header("Accept") String accept);

    @GET("owner/payment_detail/{payment_id}")
    Call<DetailPayment> getOwnerDetailPayment(@Path("payment_id") String payment_id, @Header("Authorization") String token, @Header("Accept") String accept);
}
