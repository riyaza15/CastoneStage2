package riyaza.grocerystore.cart;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.PlaceOrder;
import riyaza.grocerystore.home.HomeActivity;
import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity extends AppCompatActivity {
    private String TAG =" PlaceOrderActivity";
    private String totalamount="0", addressid ="0", delivermode ="instant_pay";
    private TextView place_order,  continueshipping, orderIdtxt;
    private RadioButton radio_payment_gateway, radio_cash_on;
    private RelativeLayout relative_lay1, relative_lay2;

    private Boolean gotoHomeflag = false;
    InstamojoPay instamojoPay ;
    IntentFilter filter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        final Intent intent = getIntent();
        totalamount =  intent.getExtras().getString("amount");
        addressid =  intent.getExtras().getString("addressid");

        radio_payment_gateway =(RadioButton) findViewById(R.id.radio_payment_gateway);
        radio_cash_on = (RadioButton) findViewById(R.id.radio_cash_on);
        orderIdtxt = (TextView) findViewById(R.id.orderIdtxt);
        continueshipping = (TextView) findViewById(R.id.continueshipping);

        relative_lay1 = (RelativeLayout) findViewById(R.id.relative_lay1);
        relative_lay2 = (RelativeLayout) findViewById(R.id.relative_lay2);


        place_order = (TextView) findViewById(R.id.place_order);
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radio_payment_gateway.isChecked()){
                    delivermode ="instant_pay";

                    if (!addressid.equalsIgnoreCase("0")){

                        callInstamojoPay(  SharePreferenceUtils.getInstance().getString(Constant.USER_email),  SharePreferenceUtils.getInstance().getString(Constant.USER_phone),
                                totalamount, " shopeeasy app buy request ", SharePreferenceUtils.getInstance().getString(Constant.USER_name));

                        //  String orderid = "fed40b6b79754a65af7f16cf5ef7ecb3";  String paymentid ="MOJO8223005N32548486";
                        //CallPlaceOrderAPI( orderid, paymentid, totalamount, addressid);
                    }else {
                        AppUtilits.displayMessage(PlaceOrderActivity.this, getResources().getString(R.string.select_address) );
                    }

                }else {
                    delivermode ="cash_ondelivery";
                    if (!addressid.equalsIgnoreCase("0")){

                        CallPlaceOrderAPI( "cash_order_id", "cash_payment_id", totalamount, addressid);
                        //  String orderid = "fed40b6b79754a65af7f16cf5ef7ecb3";  String paymentid ="MOJO8223005N32548486";
                        //CallPlaceOrderAPI( orderid, paymentid, totalamount, addressid);
                    }else {
                        AppUtilits.displayMessage(PlaceOrderActivity.this, getResources().getString(R.string.select_address) );
                    }

                }




            }
        });

        continueshipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PlaceOrderActivity.this, HomeActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();

            }
        });




    }

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        instamojoPay = new InstamojoPay();
        filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
              //  Log.e(TAG, " payment done succesfully "+ response);
                // status=success:orderId=a089f02724ed4a8db6c069f6d30b3245:txnId=None:paymentId=MOJO7918005A76494611:token=qyFwLidQ0aBNNWlsmwHx1gHFhlt6A1
                // status=success:orderId=fed40b6b79754a65af7f16cf5ef7ecb3:txnId=None:paymentId=MOJO8223005N32548486:token=qnKill4PsGm6zfkehGlm6QS6vfJZdx

                String resArray[] =   response.split(":");
              //  Log.e(TAG,  " array index 0 "+ resArray[0] + "--orderid -"+ resArray[1] +"---paymentid--"+ resArray[3] );
                String orderid =  resArray[1].substring(resArray[1].indexOf("=")+1);
                String paymentid =  resArray[3].substring(resArray[3].indexOf("=")+1);

               // Log.e(TAG, " order id "+ orderid +" paymentid "+ paymentid);

                CallPlaceOrderAPI( orderid, paymentid, totalamount, addressid);
                unregisterReceiver(instamojoPay);
               // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG) .show();
            }

            @Override
            public void onFailure(int code, String reason) {
                AppUtilits.displayMessage(PlaceOrderActivity.this, reason );

               // Log.e(TAG, " payment fail "+ code+ "  reason -- " +reason);
               // Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG) .show();
                unregisterReceiver(instamojoPay);
            }
        };
    }

    public void CallPlaceOrderAPI(String orderid, String paymentid, String totalamount, String addressid){

        if (!NetworkUtility.isNetworkConnected(PlaceOrderActivity.this)){
            AppUtilits.displayMessage(PlaceOrderActivity.this,  getString(R.string.network_not_connected));

        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            //   Call<PlaceOrderActivity>  call = serviceWrapper.placceOrdercall("1234", SharePreferenceUtils.getInstance().getString(Constant.USER_DATA) )
            Call<PlaceOrder> call = serviceWrapper.placceOrdercall("1234", SharePreferenceUtils.getInstance().getString(Constant.USER_DATA), orderid,paymentid,
                    addressid, totalamount, SharePreferenceUtils.getInstance().getString(Constant.QUOTE_ID) , delivermode );

            call.enqueue(new Callback<PlaceOrder>() {
                @Override
                public void onResponse(Call<PlaceOrder> call, Response<PlaceOrder> response) {
                  //  Log.e(TAG, "response is "+ response.body() + "  ---- "+ new Gson().toJson(response.body()));
                    //  Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {
                        //    Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {

                            gotoHomeflag = true;
                            relative_lay1.setVisibility(View.GONE);
                            relative_lay2.setVisibility(View.VISIBLE);

                            orderIdtxt.setText(response.body().getInformation().getOrderId());
                            SharePreferenceUtils.getInstance().saveString(Constant.QUOTE_ID, "");




                        }else {
                            AppUtilits.displayMessage(PlaceOrderActivity.this, response.body().getMsg() );
                        }
                    }else {
                        AppUtilits.displayMessage(PlaceOrderActivity.this, getString(R.string.network_error));
                    }
                }

                @Override
                public void onFailure(Call<PlaceOrder> call, Throwable t) {

                  //  Log.e(TAG, "  fail- get user address "+ t.toString());
                    AppUtilits.displayMessage(PlaceOrderActivity.this, getString(R.string.failed_to_placeOrder));

                }
            });


        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (gotoHomeflag){
            Intent intent1 = new Intent(PlaceOrderActivity.this, HomeActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
            finish();
        }

    }
}
