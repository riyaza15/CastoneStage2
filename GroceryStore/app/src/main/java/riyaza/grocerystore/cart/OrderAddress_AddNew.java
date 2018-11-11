package riyaza.grocerystore.cart;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.DataValidation;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.AddNewAddress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderAddress_AddNew  extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private String TAG = "orderaddress_addnew";
    private CheckBox checkbill, checkship;
    private EditText fullname, emailaddress,address1, address2, city,state,pincode,phone;
    private TextView savecontinue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_editaddress);


        checkbill = (CheckBox) findViewById(R.id.checkbox_billing);
        checkship = (CheckBox) findViewById(R.id.checkbox_Shipping);
        fullname = (EditText) findViewById(R.id.fullname);
        emailaddress = (EditText) findViewById(R.id.emailaddress);
        address1 = (EditText) findViewById(R.id.address1);
        address2 = (EditText) findViewById(R.id.address2);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        pincode = (EditText) findViewById(R.id.pincode);
        phone = (EditText) findViewById(R.id.phone);
        savecontinue = (TextView) findViewById(R.id.savecontinue);


        savecontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DataValidation.isNotValidFullName(  fullname.getText().toString())){
                    AppUtilits.displayMessage(OrderAddress_AddNew.this, getString(R.string.full_name) + " "+ getString(R.string.is_invalid));

                } else if (DataValidation.isNotValidEmail(emailaddress.getText().toString().trim())){
                    AppUtilits.displayMessage(OrderAddress_AddNew.this, getString(R.string.email) + " "+ getString(R.string.is_invalid));
                } else if (TextUtils.isEmpty(address1.getText().toString().trim())){
                    AppUtilits.displayMessage(OrderAddress_AddNew.this, getString(R.string.address_line1) + " "+ getString(R.string.is_invalid));
                }else if (TextUtils.isEmpty(city.getText().toString().trim())){
                    AppUtilits.displayMessage(OrderAddress_AddNew.this, getString(R.string.city) + " "+ getString(R.string.is_invalid));
                }else if (TextUtils.isEmpty(state.getText().toString().trim())){
                    AppUtilits.displayMessage(OrderAddress_AddNew.this, getString(R.string.state) + " "+ getString(R.string.is_invalid));
                }else if (TextUtils.isEmpty(pincode.getText().toString().trim())){
                    AppUtilits.displayMessage(OrderAddress_AddNew.this, getString(R.string.pincode) + " "+ getString(R.string.is_invalid));
                }else if ( DataValidation.isValidPhoneNumber(phone.getText().toString().trim())){
                    AppUtilits.displayMessage(OrderAddress_AddNew.this, getString(R.string.phone_no) + " "+ getString(R.string.is_invalid));
                }else {

                  //  Log.e(TAG, " aass ");
                     if (TextUtils.isEmpty(address2.getText().toString().trim())){
                        address2.setText("");
                    }
                    addnewAddressAPI();

                }


            }
        });


    }

    public void addnewAddressAPI(){
        if (!NetworkUtility.isNetworkConnected(OrderAddress_AddNew.this)){
            AppUtilits.displayMessage(OrderAddress_AddNew.this,  getString(R.string.network_not_connected));

        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<AddNewAddress> call = serviceWrapper.addNewAddressCall("1234", SharePreferenceUtils.getInstance().getString(Constant.USER_DATA),
                   fullname.getText().toString(), emailaddress.getText().toString(), address1.getText().toString(), address2.getText().toString(), city.getText().toString(),
                    state.getText().toString(), pincode.getText().toString(), phone.getText().toString());

            call.enqueue(new Callback<AddNewAddress>() {
                @Override
                public void onResponse(Call<AddNewAddress> call, Response<AddNewAddress> response) {
                  //  Log.e(TAG, "response is "+ response.body() + "  ---- "+ new Gson().toJson(response.body()));
                    //  Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {
                        //    Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            //      Log.e(TAG, "  ss sixe 3 ");

                            AlertDialog.Builder builder = new AlertDialog.Builder(OrderAddress_AddNew.this);
                            LayoutInflater inflater = LayoutInflater.from(OrderAddress_AddNew.this);
                            View g=    inflater.inflate(R.layout.display_message_popup, null);
                            TextView txtview = (TextView) g.findViewById(R.id.txt_msg);
                            TextView btn_ok = (TextView) g.findViewById(R.id.btn_ok);

                            txtview.setText(response.body().getMsg());
                            builder.setView(g);
                            final AlertDialog alert = builder.create();
                            alert.setCancelable(false);
                            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alert.dismiss();
                                    finish();
                                }
                            });

                            alert.show();

                        }else {
                            AppUtilits.displayMessage(OrderAddress_AddNew.this, response.body().getMsg() );
                        }
                    }else {
                        AppUtilits.displayMessage(OrderAddress_AddNew.this, getString(R.string.network_error));
                    }
                }

                @Override
                public void onFailure(Call<AddNewAddress> call, Throwable t) {
                   // Log.e(TAG, "  fail- add new address "+ t.toString());
                    AppUtilits.displayMessage(OrderAddress_AddNew.this, getString(R.string.fail_toaddaddress));

                }
            });




        }


        }



}