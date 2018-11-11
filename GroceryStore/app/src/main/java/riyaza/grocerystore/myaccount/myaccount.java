package riyaza.grocerystore.myaccount;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.cart.OrderAddress_AddNew;
import riyaza.grocerystore.home.HomeActivity;
import riyaza.grocerystore.login.SigninActivity;
import riyaza.grocerystore.wishlist.WishlistDetails;


public class myaccount extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private NavigationView navigationView;
    private DrawerLayout drawer;
    public static Menu mainmenu;

    private String TAG ="myaccount";
    private TextView myacc_username, myacc_email, myacc_phone, myacc_address, myacc_orderhistory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hearview =navigationView.getHeaderView(0);
        TextView  txtusername = (TextView) hearview.findViewById(R.id.username);
        TextView  txtuser_email = (TextView) hearview.findViewById(R.id.user_email);
        txtusername.setText(SharePreferenceUtils.getInstance().getString(Constant.USER_name));
        txtuser_email.setText(SharePreferenceUtils.getInstance().getString(Constant.USER_email));


        myacc_username = (TextView) findViewById(R.id.myacc_username);
        myacc_email = (TextView) findViewById(R.id.myacc_email);
        myacc_phone =(TextView) findViewById(R.id.myacc_phone);
        myacc_address = (TextView) findViewById(R.id.myacc_address);
      //  myacc_order =(TextView) findViewById(R.id.myacc_orderhistory);
        myacc_orderhistory = (TextView) findViewById(R.id.myacc_orderhistory);

        myacc_username.setText(SharePreferenceUtils.getInstance().getString(Constant.USER_name));
        myacc_email.setText(SharePreferenceUtils.getInstance().getString(Constant.USER_email));
        myacc_phone.setText(SharePreferenceUtils.getInstance().getString(Constant.USER_phone));

        myacc_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myaccount.this, OrderAddress_AddNew.class);
                startActivity(intent);

            }
        });
        myacc_orderhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myaccount.this, OrderHistory.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.myaccount_toolbar_menu, menu);
        mainmenu = menu;
        if (mainmenu!=null)
            AppUtilits.UpdateCartCount(myaccount.this, mainmenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
       // Log.e(TAG, "  option click "+ item.getTitle() );
        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {

            // Associate searchable configuration with the SearchView
            SearchManager searchManager =  (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =  (SearchView) mainmenu.findItem(R.id.search).getActionView();
            final EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

            searchEditText.setHint(getString(R.string.search_name));

            searchEditText.setHintTextColor(getResources().getColor(R.color.white));
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));

            searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    //  Log.e("onClick: ", "-- " + searchEditText.getText().toString().trim());
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        //run query to the server
                       // Log.e("onClick: ", "-- " + searchEditText.getText().toString().trim());
                        if ( null!=searchEditText.getText().toString().trim() && !searchEditText.getText().toString().trim().equals("")){

                        }
                        //  AppUtils.GetSearchResult( HomeActivity.this, TAG, searchEditText.getText().toString());
                    }
                    return false;
                }
            });
            return true;
        }else if (id==R.id.cart){
            //  Intent intent = new Intent(this, CartDetails.class);
            // startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id =  item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }else if (id == R.id.nav_new_prod){


        }else if (id == R.id.nav_myaccount){
           // Intent intent = new Intent(this, myaccount.class);
            //startActivity(intent);

        }else if (id == R.id.nav_wishlist){
              Intent intent = new Intent(this, WishlistDetails.class);
              startActivity(intent);

        }else if (id == R.id.nav_logout){

            SharePreferenceUtils.getInstance().clear();
            Intent intent = new Intent(this, SigninActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);// this will clear all the stack
            startActivity(intent);
            finish();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mainmenu!=null)
            AppUtilits.UpdateCartCount(myaccount.this, mainmenu);
    }

}
