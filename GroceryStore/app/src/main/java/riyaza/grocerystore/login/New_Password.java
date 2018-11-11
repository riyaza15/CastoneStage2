package riyaza.grocerystore.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.DataValidation;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.NewPassword;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class New_Password  extends AppCompatActivity {
    private String TAG ="New_Password", userid;
    private EditText password, retype_password;
    private TextView submit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        password = (EditText) findViewById(R.id.password);
        retype_password = (EditText) findViewById(R.id.retype_password);
        submit = (TextView) findViewById(R.id.submit);

        Intent intent = getIntent();

        if (intent!=null && !intent.getExtras().getString("userid").equals(null) ){

            userid = intent.getExtras().getString("userid");

        }else {
            userid = "";
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DataValidation.isNotValidPassword(password.getText().toString())){
                    AppUtilits.displayMessage(New_Password.this, getString(R.string.password) + " "+ getString(R.string.is_invalid));

                }else if (DataValidation.isNotValidPassword(retype_password.getText().toString())){
                    AppUtilits.displayMessage(New_Password.this, getString(R.string.retype_password) + " "+ getString(R.string.is_invalid));

                }else if (!password.getText().toString().equals(retype_password.getText().toString())){
                    AppUtilits.displayMessage(New_Password.this,  getString(R.string.password_not_match));

                }else {
                    // network connection and progroess dialog
                    /// here call retrofit method

                   sendNewPasswordReq();
                }



            }
        });

    }


    public void sendNewPasswordReq(){


        if (!NetworkUtility.isNetworkConnected(New_Password.this)){
            AppUtilits.displayMessage(New_Password.this,  getString(R.string.network_not_connected));

        }else {

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NewPassword>  call =   serviceWrapper.UserNewPassword(userid, password.getText().toString() );
            call.enqueue(new Callback<NewPassword>() {
                @Override
                public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {

                    Log.d(TAG, "reponse : "+ response.toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){

                            // response.body().getInformation().getOtp()
                            // start home activity

                            Intent intent = new Intent(New_Password.this, SigninActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);// this will clear all the stack
                            startActivity(intent);
                            finish();


                        }else {
                            AppUtilits.displayMessage(New_Password.this,  response.body().getMsg());
                        }
                    }else {
                        AppUtilits.displayMessage(New_Password.this,  getString(R.string.failed_request));

                    }
                }

                @Override
                public void onFailure(Call<NewPassword> call, Throwable t) {
                  //  Log.e(TAG, " failure "+ t.toString());
                    AppUtilits.displayMessage(New_Password.this,  getString(R.string.failed_request));
                }
            });




        }




    }




}