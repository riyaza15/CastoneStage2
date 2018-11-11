package riyaza.grocerystore.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.home.HomeActivity;
import riyaza.grocerystore.login.SigninActivity;

public class SplashActivity extends AppCompatActivity {
    private String TAG ="splashAcctivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
       // Log.e(TAG, " splash start showing");
    }

    public void init(){

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                /// if user registered user
                // then show home screen
                // else  show login screen
                // key  register_user
              //  Log.e(TAG, "  counter running ");
                if (SharePreferenceUtils.getInstance().getString(Constant.USER_DATA).equalsIgnoreCase("")){
                    // not registted user  so show login screen
                    Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
                    startActivity(intent);
                }else {
                    // home sscreen
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        }, 3000 );

    }
}
