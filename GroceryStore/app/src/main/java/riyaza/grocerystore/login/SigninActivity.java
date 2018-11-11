package riyaza.grocerystore.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.DataValidation;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.beanResponse.userSignin;
import riyaza.grocerystore.home.HomeActivity;

import static android.support.constraint.Constraints.TAG;


public class SigninActivity extends AppCompatActivity {
    private String TAG = "SigninActivity";
    private TextView skip, forgot_password, login;
    private EditText phone_no, password;
    private LinearLayout signup_here;
    userSignin response ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
      //  Log.e(TAG,"  start siggnin Activity" );

        skip =(TextView) findViewById(R.id.btn_skip);
        login = (TextView) findViewById(R.id.login);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        signup_here = (LinearLayout) findViewById(R.id.layout_signup_here);

        phone_no = (EditText) findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.password);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        signup_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ( DataValidation.isValidPhoneNumber(phone_no.getText().toString())){
                    AppUtilits.displayMessage(SigninActivity.this, getString(R.string.phone_no) + " "+ getString(R.string.is_invalid));

                }else if (DataValidation.isNotValidPassword(password.getText().toString())){
                    AppUtilits.displayMessage(SigninActivity.this, getString(R.string.password) + " "+ getString(R.string.is_invalid));

                }else {

//                    LoginAsyncTask task = new LoginAsyncTask();
//                    task.execute("http://frh-groceries.com/ecommerce-android-app-project/user_signin.php");

                    sendUserLoginData();
                }

            }
        });

    }




    public void sendUserLoginData(){

        if (!NetworkUtility.isNetworkConnected(SigninActivity.this)){
            AppUtilits.displayMessage(SigninActivity.this,  getString(R.string.network_not_connected));

        }else {

            ArrayList<String> mylist = new ArrayList<String>();
            mylist.add(phone_no.getText().toString());
            mylist.add(password.getText().toString());

            new sendUserDetailTOServer().execute(mylist);

//                                if (response.getMsg()== "sucessfull login"){
//                        if (response.getStatus() ==1){
//                            // store userdata to share prerference
//                            //  Log.e(TAG, "  user data "+  response.body().getInformation());
//                            SharePreferenceUtils.getInstance().saveString(Constant.USER_DATA, response.getInformation().getUserId());
//                            SharePreferenceUtils.getInstance().saveString(Constant.USER_name, response.getInformation().getFullname());
//                            SharePreferenceUtils.getInstance().saveString(Constant.USER_email, response.getInformation().getEmail());
//                            SharePreferenceUtils.getInstance().saveString(Constant.USER_phone, response.getInformation().getPhone());
//
//
//                            // start home activity
//                            Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
//                            //intent.putExtra("userid", "sdfsd");
//                            startActivity(intent);
//                            finish();
//
//                        }else {
//                            AppUtilits.displayMessage(SigninActivity.this,  response.getMsg());
//                        }
//                    }else {
//                        AppUtilits.displayMessage(SigninActivity.this,  getString(R.string.failed_request));
//
//                    }
                }



        }


    public class sendUserDetailTOServer extends AsyncTask<ArrayList<String>, Integer, userSignin> {

        private ProgressDialog dialog = new ProgressDialog(SigninActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected userSignin doInBackground(ArrayList<String>... alldata) {
            String urlTo = "http://frh-groceries.com/ecommerce-android-app-project/user_signin.php";

            ArrayList<String> passed = alldata[0]; //get passed arraylist

            String phone = passed.get(0);
            String pass = passed.get(1);
            Log.e(TAG, "phone " + phone + "---" + pass);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("phone", phone);
            hashMap.put("password", pass);
            userSignin res = performPostCall(urlTo, hashMap);

            return res;
        }

        public userSignin performPostCall(String requestURL, HashMap<String, String> postDataParams) {

            URL url;
            String response = "";
            try {
                url = new URL(requestURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                } else {
                    response = "";

                }
            } catch (Exception e) {
                Log.e(TAG, "error " + e.toString());
            }
            Log.e(TAG, "res " + response);

            userSignin projectsList = new Gson().fromJson(response, userSignin.class);
            return projectsList;
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(userSignin result) {
            String msg = result.getMsg();
            Log.e(TAG, " " +
                    " result  " + msg);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (msg.equals("sucessfull login")) {

                if (result.getStatus() == 1) {
                    // store userdata to share prerference
                    //  Log.e(TAG, "  user data "+  response.body().getInformation());
                    SharePreferenceUtils.getInstance().saveString(Constant.USER_DATA, result.getInformation().getUserId());
                    SharePreferenceUtils.getInstance().saveString(Constant.USER_name, result.getInformation().getFullname());
                    SharePreferenceUtils.getInstance().saveString(Constant.USER_email, result.getInformation().getEmail());
                    SharePreferenceUtils.getInstance().saveString(Constant.USER_phone, result.getInformation().getPhone());


                    // start home activity
                    Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
                    //intent.putExtra("userid", "sdfsd");
                    startActivity(intent);
                    finish();

                } else {
                    AppUtilits.displayMessage(SigninActivity.this, result.getMsg());
                }
            } else {
                AppUtilits.displayMessage(SigninActivity.this, getString(R.string.failed_request));

            }


        }
    }



    }





