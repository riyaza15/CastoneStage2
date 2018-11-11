package riyaza.grocerystore.WebServices;

import riyaza.grocerystore.beanResponse.AddNewAddress;
import riyaza.grocerystore.beanResponse.AddtoCart;
import riyaza.grocerystore.beanResponse.EditCartItem;
import riyaza.grocerystore.beanResponse.ForgotPassword;
import riyaza.grocerystore.beanResponse.GetAddress;
import riyaza.grocerystore.beanResponse.GetOrderProductDetails;
import riyaza.grocerystore.beanResponse.GetbannerModel;
import riyaza.grocerystore.beanResponse.NewPassword;
import riyaza.grocerystore.beanResponse.NewProdResopnce;
import riyaza.grocerystore.beanResponse.NewUserRegistration;
import riyaza.grocerystore.beanResponse.OrderHistoryAPI;
import riyaza.grocerystore.beanResponse.OrderSummery;
import riyaza.grocerystore.beanResponse.PlaceOrder;
import riyaza.grocerystore.beanResponse.ProductDetail_Res;
import riyaza.grocerystore.beanResponse.getCartDetails;
import riyaza.grocerystore.beanResponse.getWishlist;
import riyaza.grocerystore.beanResponse.userSignin;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ServiceInterface {

    // method,, return type ,, secondary url
    // ecommerce-android-app-project/new_user_registration.php
    @Multipart
    @POST("ecommerce-android-app-project/new_user_registration.php")
    Call<NewUserRegistration> NewUserRegistrationCall(
            @Part("fullname") RequestBody fullname,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );

    ///  user signin request
    @Multipart
    @POST("ecommerce-android-app-project/user_signin.php")
    Call<userSignin> UserSigninCall(
            @Part("phone") RequestBody phone,
            @Part("password") RequestBody password
    );

    ///  user forgot password request
    @Multipart
    @POST("ecommerce-android-app-project/user_forgot_password.php")
    Call<ForgotPassword> UserForgotPassword(
            @Part("phone") RequestBody phone
    );

    ///  create new password request
    @Multipart
    @POST("ecommerce-android-app-project/new_password.php")
    Call<NewPassword> UserNewPassword(
            @Part("userid") RequestBody userid,
            @Part("password") RequestBody password
    );
    // get new products
    @Multipart
    @POST("ecommerce-android-app-project/getnewproduct.php")
    Call<NewProdResopnce> getNewPorduct(
            @Part("securecode") RequestBody securecode
    );

    // get best selling products
    @Multipart
    @POST("ecommerce-android-app-project/getbestsellingprod.php")
    Call<NewProdResopnce> getBestSelling(
            @Part("securecode") RequestBody securecode
    );
    // get trending products
    @Multipart
    @POST("ecommerce-android-app-project/gettrendingprod.php")
    Call<NewProdResopnce> getTrendingProd(
            @Part("securecode") RequestBody securecode
    );
    // get conditional products
    @Multipart
    @POST("ecommerce-android-app-project/getconditionalprod.php")
    Call<NewProdResopnce> getConditionalProd(
            @Part("securecode") RequestBody securecode
    );

    // get product details
    @Multipart
    @POST("ecommerce-android-app-project/getproductdetails.php")
    Call<ProductDetail_Res> getProductDetials(
            @Part("securecode") RequestBody securecode,
            @Part("prod_id") RequestBody prod_id
    );

    // add to cart
    @Multipart
    @POST("ecommerce-android-app-project/add_prod_into_cart.php")
    Call<AddtoCart> addtocartcall(
            @Part("securecode") RequestBody securecode,
            @Part("prod_id") RequestBody prod_id,
            @Part("user_id") RequestBody user_id,
            @Part("prod_price") RequestBody prod_price
    );

    // add into wishlist
    @Multipart
    @POST("ecommerce-android-app-project/add_prod_into_wishlist.php")
    Call<AddtoCart> addtowishlist(
            @Part("securecode") RequestBody securecode,
            @Part("prod_id") RequestBody prod_id,
            @Part("user_id") RequestBody user_id
    );


    // get user cart
    @Multipart
    @POST("ecommerce-android-app-project/getusercartdetails.php")
    Call<getCartDetails> getusercartcall(
            @Part("securecode") RequestBody securecode,
            @Part("qoute_id") RequestBody qoute_id,
            @Part("user_id") RequestBody user_id
    );

    // get user wishlist
    @Multipart
    @POST("ecommerce-android-app-project/getuserwishlist.php")
    Call<getWishlist> getuserwihslistcall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id
    );

    // delete wishlist item
    @Multipart
    @POST("ecommerce-android-app-project/deletewishlistitem.php")
    Call<AddtoCart> deleteWIshlistProd(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("prod_id") RequestBody prod_id
    );

    // delete cart item
    @Multipart
    @POST("ecommerce-android-app-project/deletecartitem.php")
    Call<AddtoCart> deleteCartProd(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("prod_id") RequestBody prod_id
    );

    // edit cart qty
    @Multipart
    @POST("ecommerce-android-app-project/editcartitem.php")
    Call<EditCartItem> editCartQty(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("prod_id") RequestBody prod_id,
            @Part("prod_qty") RequestBody prod_qty
    );

    // get order summery
    @Multipart
    @POST("ecommerce-android-app-project/getordersummery.php")
    Call<OrderSummery> getOrderSummerycall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("qoute_id") RequestBody qoute_id
    );

    // add new address
    @Multipart
    @POST("ecommerce-android-app-project/add_address.php")
    Call<AddNewAddress> addnewAddresscall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("fullname") RequestBody fullname,
            @Part("email") RequestBody email,
            @Part("address1") RequestBody address1,
            @Part("address2") RequestBody address2,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("pincode") RequestBody pincode,
            @Part("phone") RequestBody phone


    );
    // get user address
    @Multipart
    @POST("ecommerce-android-app-project/getUserAddress.php")
    Call<GetAddress> getUserAddress(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id
    );

    // place order api
    @Multipart
    @POST("ecommerce-android-app-project/placeorderapi.php")
    Call<PlaceOrder> PlaceOrderCall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("paymentorder_id") RequestBody paymentorder_id,
            @Part("payment_id") RequestBody payment_id,
            @Part("address_id") RequestBody address_id,
            @Part("total_price") RequestBody total_price,
            @Part("qoute_id") RequestBody qoute_id,
            @Part("deliverymode") RequestBody deliverymode
    );

    // get order history
    @Multipart
    @POST("ecommerce-android-app-project/getorderhistory.php")
    Call<OrderHistoryAPI> getorderHistorycall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id
    );

    // get order prodct details history
    @Multipart
    @POST("ecommerce-android-app-project/getorderhistoryproddetails.php")
    Call<GetOrderProductDetails> getorderProductdetailscall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("order_id") RequestBody order_id
    );

    // get banner image
    @Multipart
    @POST("ecommerce-android-app-project/getbanner.php")
    Call<GetbannerModel> getbannerimagecall(
            @Part("securecode") RequestBody securecode
    );
}
