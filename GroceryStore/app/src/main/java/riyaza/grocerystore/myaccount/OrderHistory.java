package riyaza.grocerystore.myaccount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.OrderHistoryAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistory extends AppCompatActivity {

    private String TAG = "orderhistory";
    private RecyclerView recyclerView_order;
    private ArrayList<orderhistory_model> Models = new ArrayList<>();
    private OrderHistory_Adapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerView_order = (RecyclerView) findViewById(R.id.recycler_orderhistory);
        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false);
        recyclerView_order.setLayoutManager(mLayoutManger3);
        recyclerView_order.setItemAnimator(new DefaultItemAnimator());

        adapter = new OrderHistory_Adapter( OrderHistory.this, Models);

        recyclerView_order.setAdapter(adapter);


        getUserOrderHistory();

    }

    public void getUserOrderHistory(){
        if (!NetworkUtility.isNetworkConnected(OrderHistory.this)){
            AppUtilits.displayMessage(OrderHistory.this,  getString(R.string.network_not_connected));

        }else {
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<OrderHistoryAPI> call = service.getorderhistorycall("1234", SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            call.enqueue(new Callback<OrderHistoryAPI>() {
                @Override
                public void onResponse(Call<OrderHistoryAPI> call, Response<OrderHistoryAPI> response) {
                   // Log.e(TAG, "response is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                    //  Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {
                        //    Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            //      Log.e(TAG, "  ss sixe 3 ");
                            Models.clear();

                           if (response.body().getInformation().size()>0){

                               for (int i =0; i<response.body().getInformation().size(); i++){

                                 Models.add(  new orderhistory_model(response.body().getInformation().get(i).getOrderId(), response.body().getInformation().get(i).getShippingaddress(),
                                           response.body().getInformation().get(i).getPrice(), response.body().getInformation().get(i).getDate()));


                               }
                               adapter.notifyDataSetChanged();
                           }

                        } else {
                            AppUtilits.displayMessage(OrderHistory.this, response.body().getMsg());

                        }

                    } else {
                        AppUtilits.displayMessage(OrderHistory.this, getString(R.string.network_error));

                    }
                }
                @Override
                public void onFailure(Call<OrderHistoryAPI> call, Throwable t) {
                  //  Log.e(TAG, "  fail- add to cart item "+ t.toString());
                    AppUtilits.displayMessage(OrderHistory.this, getString(R.string.fail_toorderhistory));

                }
            });


        }


        }

}
