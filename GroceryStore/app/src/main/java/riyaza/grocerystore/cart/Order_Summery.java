package riyaza.grocerystore.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.OrderSummery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order_Summery extends AppCompatActivity {

    private String TAG = "ordersummery";
    private TextView place_order, subtotalvalue, shippingvalue, ordertotalvalue;

    private RecyclerView item_recyclerview;
    private ArrayList<Cartitem_Model> cartitemModels = new ArrayList<>();
    private OrderSummery_Adapter orderSummeryAdapter;
    private float totalamount =0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summery);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        place_order = (TextView) findViewById(R.id.place_order);
        subtotalvalue = (TextView) findViewById(R.id.subtotal_value);
        shippingvalue = (TextView) findViewById(R.id.shipping_value);
        ordertotalvalue = (TextView) findViewById(R.id.order_total_value);

        item_recyclerview = (RecyclerView) findViewById(R.id.item_recyclerview);

        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false);
        item_recyclerview.setLayoutManager(mLayoutManger3);
        item_recyclerview.setItemAnimator(new DefaultItemAnimator());

        orderSummeryAdapter = new OrderSummery_Adapter(this, cartitemModels);
        item_recyclerview.setAdapter(orderSummeryAdapter);

        getUserCartDetails();

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Log.e(TAG, "  total amount is "+String.valueOf(totalamount));
                if ( totalamount >0){

                        Intent intent = new Intent(Order_Summery.this, OrderAddress.class);
                        intent.putExtra("amount",  String.valueOf(totalamount));
                        startActivity(intent);

                }


            }
        });

    }

    public void getUserCartDetails(){

        if (!NetworkUtility.isNetworkConnected(Order_Summery.this)){
            AppUtilits.displayMessage(Order_Summery.this,  getString(R.string.network_not_connected));

        }else {
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<OrderSummery> call = service.getOrderSummerycall("1234" , SharePreferenceUtils.getInstance().getString(Constant.USER_DATA),
                            SharePreferenceUtils.getInstance().getString(Constant.QUOTE_ID));

            call.enqueue(new Callback<OrderSummery>() {
                @Override
                public void onResponse(Call<OrderSummery> call, Response<OrderSummery> response) {
                 //   Log.e(TAG, "response is "+ response.body() + "  ---- "+ new Gson().toJson(response.body()));
                    //  Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {
                        //    Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {

                            subtotalvalue.setText("$"+response.body().getInformation().getSubtotal());
                            shippingvalue.setText(response.body().getInformation().getShippingfee());
                            ordertotalvalue.setText("$"+response.body().getInformation().getOrdertotal());
                            try {

                                totalamount = Float.valueOf( response.body().getInformation().getOrdertotal());

                            }catch (Exception e){
                                Log.e(TAG, "amount error "+ e.toString() );
                            }
                            cartitemModels.clear();
                            for (int i=0; i<response.body().getInformation().getProdDetails().size(); i++){


                                cartitemModels.add( new Cartitem_Model(response.body().getInformation().getProdDetails().get(i).getId(),
                                        response.body().getInformation().getProdDetails().get(i).getName(), "", "",
                                        response.body().getInformation().getProdDetails().get(i).getPrice(), response.body().getInformation().getProdDetails().get(i).getQty()));

                            }

                            orderSummeryAdapter.notifyDataSetChanged();


                        } else {
                        AppUtilits.displayMessage(Order_Summery.this, response.body().getMsg() );
                    }
                }else {
                    AppUtilits.displayMessage(Order_Summery.this, getString(R.string.network_error));
                }

            }

                @Override
                public void onFailure(Call<OrderSummery> call, Throwable t) {

                    Log.e(TAG, "  fail- order symmer item "+ t.toString());
                    AppUtilits.displayMessage(Order_Summery.this, getString(R.string.fail_toordersummery));


                }
            });



        }






    }


}
