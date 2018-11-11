package riyaza.grocerystore.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.GetAddress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderAddress extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private String TAG = "orderaddress";
    private TextView continuebtn;

    private OrderAddress_Adapter adapter;
    private ArrayList<OrderAddress_Model>  modellist = new ArrayList<>();
    private String totalamount="0";
    public String addressid ="0";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderaddress);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        totalamount =  intent.getExtras().getString("amount");


        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.order_recyclerview);
        continuebtn =(TextView) findViewById(R.id.continuebtn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderAddress.this, OrderAddress_AddNew.class);
                startActivity(intent);

            }
        });

       // Log.e(TAG, " payment detaisl -"+SharePreferenceUtils.getInstance().getString(Constant.USER_email)+"--"+
         //       SharePreferenceUtils.getInstance().getString(Constant.USER_phone)+"--"+
           //     totalamount+ " buy from app "+"--" +SharePreferenceUtils.getInstance().getString(Constant.USER_name) );

       // totalamount ="12";
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addressid.equalsIgnoreCase("0")){

                    Intent intent1 = new Intent(OrderAddress.this, PlaceOrderActivity.class);
                    intent1.putExtra("amount", totalamount);
                    intent1.putExtra("addressid", addressid);
                    startActivity(intent1);


                }else {
                    AppUtilits.displayMessage(OrderAddress.this, getResources().getString(R.string.select_address) );
                }

            }
        });

        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManger3);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new OrderAddress_Adapter(OrderAddress.this, modellist);
        recyclerView.setAdapter(adapter);





    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserAddress();

    }


    public void getUserAddress(){

        if (!NetworkUtility.isNetworkConnected(OrderAddress.this)){
            AppUtilits.displayMessage(OrderAddress.this,  getString(R.string.network_not_connected));

        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<GetAddress> call = serviceWrapper.getUserAddresscall("1234", SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            call.enqueue(new Callback<GetAddress>() {
                @Override
                public void onResponse(Call<GetAddress> call, Response<GetAddress> response) {
                  //  Log.e(TAG, "response is "+ response.body() + "  ---- "+ new Gson().toJson(response.body()));
                    //  Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {
                        //    Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {

                            if (response.body().getInformation().getAddressDetails().size()>0){

                                modellist.clear();
                                for (int i=0; i<response.body().getInformation().getAddressDetails().size() ; i++){

                                    modellist.add(new OrderAddress_Model(response.body().getInformation().getAddressDetails().get(i).getAddressId(),
                                            response.body().getInformation().getAddressDetails().get(i).getFullname(),
                                            response.body().getInformation().getAddressDetails().get(i).getAddress1()+" "+
                                                    response.body().getInformation().getAddressDetails().get(i).getAddress2()+"\n"+
                                                    response.body().getInformation().getAddressDetails().get(i).getCity()+" "+
                                                    response.body().getInformation().getAddressDetails().get(i).getState()+"\n"+
                                                    response.body().getInformation().getAddressDetails().get(i).getPincode()+"\n"+
                                                    response.body().getInformation().getAddressDetails().get(i).getEmail(),
                                            response.body().getInformation().getAddressDetails().get(i).getPhone()));
                                }

                                adapter.notifyDataSetChanged();

                            }



                        }else {
                            AppUtilits.displayMessage(OrderAddress.this, response.body().getMsg() );
                        }
                    }else {
                        AppUtilits.displayMessage(OrderAddress.this, getString(R.string.network_error));
                    }

                }

                @Override
                public void onFailure(Call<GetAddress> call, Throwable t) {
                 //   Log.e(TAG, "  fail- get user address "+ t.toString());
                    AppUtilits.displayMessage(OrderAddress.this, getString(R.string.fail_togetaddress));


                }
            });


        }
    }

    /*

    1) cart detail -- produ details price
    2) summery
    3) discount coupan-   table - sno. coupan code  valid datte, 10%, Rs 100
    3) select address - -if not --create
    4) integration intamojo...
    5) start payment process--
    6) if successfull payment then store orderid and paymetnid along with user id, product detaiils , shipping address id,
       6.1) from cartdetails delete user cart and qoute id
    7) successfull

    */



}
