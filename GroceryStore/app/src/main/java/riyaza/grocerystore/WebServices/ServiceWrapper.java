package riyaza.grocerystore.WebServices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import riyaza.grocerystore.BuildConfig;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.beanResponse.AddNewAddress;
import riyaza.grocerystore.beanResponse.AddtoCart;
import riyaza.grocerystore.beanResponse.EditCartItem;
import riyaza.grocerystore.beanResponse.ForgotPassword;
import riyaza.grocerystore.beanResponse.GetAddress;
import riyaza.grocerystore.beanResponse.GetOrderProductDetails;
import riyaza.grocerystore.beanResponse.GetbannerModel;
import riyaza.grocerystore.beanResponse.NewPassword;
import riyaza.grocerystore.beanResponse.NewProdResopnce;
import riyaza.grocerystore.beanResponse.OrderHistoryAPI;
import riyaza.grocerystore.beanResponse.OrderSummery;
import riyaza.grocerystore.beanResponse.PlaceOrder;
import riyaza.grocerystore.beanResponse.ProductDetail_Res;
import riyaza.grocerystore.beanResponse.getCartDetails;
import riyaza.grocerystore.beanResponse.getWishlist;
import riyaza.grocerystore.beanResponse.userSignin;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import riyaza.grocerystore.beanResponse.NewUserRegistration;


public class ServiceWrapper  {

    private ServiceInterface mServiceInterface;

    public ServiceWrapper(Interceptor mInterceptorheader) {
        mServiceInterface = getRetrofit(mInterceptorheader).create(ServiceInterface.class);
    }

    public Retrofit getRetrofit(Interceptor mInterceptorheader) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkHttpClient = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constant.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Constant.API_READ_TIMEOUT, TimeUnit.SECONDS);

//        if (BuildConfig.DEBUG)
//            builder.addInterceptor(loggingInterceptor);

        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }


        mOkHttpClient = builder.build();
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mOkHttpClient)
                .build();
        return retrofit;
    }

    public Call<NewUserRegistration> newUserRegistrationCall( String fullname, String email,String phone, String username, String password){
       return mServiceInterface.NewUserRegistrationCall( convertPlainString(fullname),convertPlainString(email), convertPlainString(phone), convertPlainString( username),
               convertPlainString(password));
    }
    ///  user signin
    public Call<userSignin> UserSigninCall( String phone,  String password){
        return mServiceInterface.UserSigninCall(convertPlainString(phone),  convertPlainString(password));
    }

    ///  user signin
    public Call<ForgotPassword> UserForgotPassword(String phone){
        return mServiceInterface.UserForgotPassword(convertPlainString(phone));
    }
    ///  user new password
    public Call<NewPassword> UserNewPassword(String userid, String password){
        return mServiceInterface.UserNewPassword(convertPlainString(userid), convertPlainString(password));
    }

    ///  new product details
    public Call<NewProdResopnce> getNewProductRes(String securcode){
        return mServiceInterface.getNewPorduct(convertPlainString(securcode));
    }

    ///  best selling product details
    public Call<NewProdResopnce> getBestselling(String securcode){
        return mServiceInterface.getBestSelling(convertPlainString(securcode));
    }
    ///  get trending  product details
    public Call<NewProdResopnce> getTrendingPRod(String securcode){
        return mServiceInterface.getTrendingProd(convertPlainString(securcode));
    }
    ///  get conditional  product details
    public Call<NewProdResopnce> getConditionalPRod(String securcode){
        return mServiceInterface.getConditionalProd(convertPlainString(securcode));
    }

    // get product detials
    ///  get conditional  product details
    public Call<ProductDetail_Res> getProductDetails(String securcode, String prod_id){
        return mServiceInterface.getProductDetials(convertPlainString(securcode), convertPlainString(prod_id));
    }
    // add to cart
    public Call<AddtoCart> addtoCartCall(String securcode, String prod_id, String user_id, String prod_price){
        return mServiceInterface.addtocartcall(convertPlainString(securcode), convertPlainString(prod_id),convertPlainString(user_id), convertPlainString(prod_price) );
    }

    // add to wishlist
    public Call<AddtoCart> addtowishlistcall(String securcode, String prod_id, String user_id){
        return mServiceInterface.addtowishlist(convertPlainString(securcode), convertPlainString(prod_id),convertPlainString(user_id) );
    }

    // get user cart
    public Call<getCartDetails> getCartDetailsCall(String securcode, String qoute_id, String user_id){
        return mServiceInterface.getusercartcall(convertPlainString(securcode), convertPlainString(qoute_id),convertPlainString(user_id) );
    }
    //  get useer wihslit
    public Call<getWishlist> getuserWisshlistCall(String securcode, String user_id){
        return mServiceInterface.getuserwihslistcall(convertPlainString(securcode), convertPlainString(user_id) );
    }
    // delete wishlist item
    public Call<AddtoCart> deleteWishlistprod(String securcode, String user_id, String prod_id){
        return mServiceInterface.deleteWIshlistProd(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(prod_id) );
    }

    // delete cart item
    public Call<AddtoCart> deletecartprod(String securcode, String user_id, String prod_id){
        return mServiceInterface.deleteCartProd(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(prod_id) );
    }
    // edit cart item
    public Call<EditCartItem> editcartcartprodqty(String securcode, String user_id, String prod_id, String prod_qty){
        return mServiceInterface.editCartQty(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(prod_id),  convertPlainString(prod_qty) );
    }

    // get order summery
    public Call<OrderSummery> getOrderSummerycall(String securcode, String user_id, String qoute_id){
        return mServiceInterface.getOrderSummerycall(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(qoute_id) );
    }

    // add new address
    public Call<AddNewAddress> addNewAddressCall(String securcode, String user_id, String fullname, String email, String address1, String adress2, String city, String state,
                                                 String pincode, String phone){
        return mServiceInterface.addnewAddresscall(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(fullname),
                convertPlainString(email), convertPlainString(address1)
                , convertPlainString(adress2), convertPlainString(city), convertPlainString(state), convertPlainString(pincode), convertPlainString(phone));
    }

    // get order summery
    public Call<GetAddress> getUserAddresscall(String securcode, String user_id){
        return mServiceInterface.getUserAddress(convertPlainString(securcode), convertPlainString(user_id) );
    }
    // get place order api
    public Call<PlaceOrder> placceOrdercall(String securcode, String user_id, String paymentorder_id, String payment_id,  String address_id,
                                            String total_price, String qoute_id, String delivermode){
        return mServiceInterface.PlaceOrderCall(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(paymentorder_id), convertPlainString(payment_id),
                convertPlainString(address_id), convertPlainString(total_price), convertPlainString(qoute_id), convertPlainString( delivermode));
    }
    // get order history
    public Call<OrderHistoryAPI> getorderhistorycall(String securcode, String user_id){
        return mServiceInterface.getorderHistorycall(convertPlainString(securcode), convertPlainString(user_id) );
    }
    // get order prodcut detais history
    public Call<GetOrderProductDetails> getorderproductcall(String securcode, String user_id, String order_id){
        return mServiceInterface.getorderProductdetailscall(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(order_id) );
    }

    // get banner image
    public Call<GetbannerModel> getbannerModelCall(String securcode){
        return mServiceInterface.getbannerimagecall(convertPlainString(securcode) );
    }
      // convert aa param into plain text
    public RequestBody convertPlainString(String data){
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return  plainString;
    }
}


