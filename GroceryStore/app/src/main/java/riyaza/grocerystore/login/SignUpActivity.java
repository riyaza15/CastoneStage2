package riyaza.grocerystore.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.DataValidation;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.NewUserRegistration;
import riyaza.grocerystore.home.HomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity  extends AppCompatActivity {
    private String TAG ="SignupActivity";

    EditText fullname, user_email, phone_no, username, password, retype_password;
    TextView create_acc;
    private LinearLayout layout_signin;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname = (EditText) findViewById(R.id.fullname);
        user_email = (EditText) findViewById(R.id.user_email);
        phone_no = (EditText) findViewById(R.id.phone_number);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        retype_password = (EditText) findViewById(R.id.retype_password);
        layout_signin = (LinearLayout) findViewById(R.id.layout_signin);

        create_acc = (TextView) findViewById(R.id.create_account);


        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DataValidation.isNotValidFullName(fullname.getText().toString())){
                    /// show error pupup
                    AppUtilits.displayMessage(SignUpActivity.this, getString(R.string.full_name) + " "+ getString(R.string.is_invalid));
                }else if ( DataValidation.isNotValidEmail(user_email.getText().toString())){
                    AppUtilits.displayMessage(SignUpActivity.this, getString(R.string.email) + " "+ getString(R.string.is_invalid));

                }else if ( DataValidation.isValidPhoneNumber(phone_no.getText().toString())){
                    AppUtilits.displayMessage(SignUpActivity.this, getString(R.string.phone_no) + " "+ getString(R.string.is_invalid));

                }else if (DataValidation.isNotValidAddress(username.getText().toString())){
                    AppUtilits.displayMessage(SignUpActivity.this, getString(R.string.username) + " "+ getString(R.string.is_invalid));

                }else if (DataValidation.isNotValidPassword(password.getText().toString())){
                    AppUtilits.displayMessage(SignUpActivity.this, getString(R.string.password) + " "+ getString(R.string.is_invalid));

                }else if (DataValidation.isNotValidPassword(retype_password.getText().toString())){
                    AppUtilits.displayMessage(SignUpActivity.this, getString(R.string.retype_password) + " "+ getString(R.string.is_invalid));

                }else if (!password.getText().toString().equals(retype_password.getText().toString())){
                    AppUtilits.displayMessage(SignUpActivity.this,  getString(R.string.password_not_match));

                }else {
            // network connection and progroess dialog
              /// here call retrofit method

                    sendNewRegistrationReq();
                }

            }
        });
        layout_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    public void sendNewRegistrationReq(){

        if (!NetworkUtility.isNetworkConnected(SignUpActivity.this)){
            AppUtilits.displayMessage(SignUpActivity.this,  getString(R.string.network_not_connected));

        }else {

           ServiceWrapper serviceWrapper = new ServiceWrapper(null);
           Call<NewUserRegistration> callNewREgistration=   serviceWrapper.newUserRegistrationCall(fullname.getText().toString(),
                   user_email.getText().toString(), phone_no.getText().toString(),
                    username.getText().toString(), password.getText().toString() );
            callNewREgistration.enqueue(new Callback<NewUserRegistration>() {
                @Override
                public void onResponse(Call<NewUserRegistration> call, Response<NewUserRegistration> response) {
                    Log.d(TAG, "reponse : "+ response.toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                            // store userdata to share prerference
                            SharePreferenceUtils.getInstance().saveString(Constant.USER_DATA, response.body().getInformation().getUserId());
                            SharePreferenceUtils.getInstance().saveString(Constant.USER_name, response.body().getInformation().getFullname());
                            SharePreferenceUtils.getInstance().saveString(Constant.USER_email, response.body().getInformation().getEmail());
                            SharePreferenceUtils.getInstance().saveString(Constant.USER_phone, response.body().getInformation().getPhone());

                            // start home activity
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            //intent.putExtra("userid", "sdfsd");
                            startActivity(intent);
                            finish();

                        }else {
                            AppUtilits.displayMessage(SignUpActivity.this,  response.body().getMsg());
                        }
                    }else {
                        AppUtilits.displayMessage(SignUpActivity.this,  getString(R.string.failed_request));

                    }
                }

                @Override
                public void onFailure(Call<NewUserRegistration> call, Throwable t) {
                  //  Log.e(TAG, " failure "+ t.toString());
                    AppUtilits.displayMessage(SignUpActivity.this,  getString(R.string.failed_request));

                }
            });
        }


    }

}
