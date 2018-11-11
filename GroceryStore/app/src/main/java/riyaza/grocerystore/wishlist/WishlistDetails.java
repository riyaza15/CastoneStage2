package riyaza.grocerystore.wishlist;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.getWishlist;
import riyaza.grocerystore.cart.CartDetails;
import riyaza.grocerystore.home.HomeActivity;
import riyaza.grocerystore.login.SigninActivity;
import riyaza.grocerystore.myaccount.myaccount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import riyaza.grocerystore.wishlist.data.WishListContact;


public class WishlistDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    public static Menu mainmenu;
    private String TAG = "wishlist";
    private RecyclerView recycler_wishlist;
    private TextView wishlist_count;
    private Wishlist_Adapter wishlistAdapter;
    private ArrayList<Wishlist_Model> modelArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

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


        recycler_wishlist = (RecyclerView) findViewById(R.id.recycler_wishlist);
        wishlist_count = (TextView) findViewById(R.id.wishlist_count);

        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_wishlist.setLayoutManager(mLayoutManger3);
        recycler_wishlist.setItemAnimator(new DefaultItemAnimator());

         wishlistAdapter = new Wishlist_Adapter(this,  modelArrayList);
        recycler_wishlist.setAdapter( wishlistAdapter);

        diplayFavoriteList();



    }



    public void diplayFavoriteList(){
        getSupportLoaderManager().initLoader(1,
                new Bundle(), wishlistsLoader);

    }

    public void getUserWishlistDetails(){


    }

    public void updatecartcount(){

        AppUtilits.UpdateCartCount(WishlistDetails.this, mainmenu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.myaccount_toolbar_menu, menu);
        mainmenu = menu;
        if (mainmenu!=null)
            AppUtilits.UpdateCartCount(WishlistDetails.this, mainmenu);
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
            Intent intent = new Intent(this, CartDetails.class);
            startActivity(intent);
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
            Intent intent = new Intent(this, myaccount.class);
            startActivity(intent);

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
            AppUtilits.UpdateCartCount(WishlistDetails.this, mainmenu);
    }


    private LoaderManager.LoaderCallbacks<Cursor> wishlistsLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    // Define the columns to retrieve
                    Log.e("Loader","In Loader");
                    String[] projectionFields = {
                            WishListContact.WhilistEntry.COLUMN_PRODID,
                            WishListContact.WhilistEntry.COLUMN_TITLE  ,
                            WishListContact.WhilistEntry.COLUMN_PRICE,
                            WishListContact.WhilistEntry.COLUMN_RATING,
                            WishListContact.WhilistEntry.COLUMN_RATINGCOUNT,
                            WishListContact.WhilistEntry.COLUMN_PORTER_URI

                    };
                    // Construct the loader
                    CursorLoader cursorLoader = new CursorLoader(WishlistDetails.this,
                            WishListContact.WhilistEntry.CONTENT_URI, // URI
                            projectionFields, // projection fields
                            null, // the selection criteria
                            null, // the selection args
                            null // the sort order
                    );
                    // Return the loader for use
                    return cursorLoader;
                }


                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

                    try{
                        int idColumnIndex = cursor.getColumnIndex(WishListContact.WhilistEntry.COLUMN_PRODID);
                        int titleColumnIndex = cursor.getColumnIndex(WishListContact.WhilistEntry.COLUMN_TITLE);
                        int priceColumnIndex = cursor.getColumnIndex(WishListContact.WhilistEntry.COLUMN_PRICE);
                        int ratingColumnIndex = cursor.getColumnIndex(WishListContact.WhilistEntry.COLUMN_RATING);
                        int ratingCountColumnIndex = cursor.getColumnIndex(WishListContact.WhilistEntry.COLUMN_RATINGCOUNT);
                        int imageURIColumnIndex = cursor.getColumnIndex(WishListContact.WhilistEntry.COLUMN_PORTER_URI);
                        modelArrayList.clear();
                        while (cursor.moveToNext()) {
                            String id = cursor.getString(idColumnIndex);
                            String title = cursor.getString(titleColumnIndex);
                            String price = cursor.getString(priceColumnIndex);
                            String rating = cursor.getString(ratingColumnIndex);
                            String ratingCount = cursor.getString(ratingCountColumnIndex);
                            String imageURI = cursor.getString(imageURIColumnIndex);
                            Wishlist_Model wishlist_model = new Wishlist_Model( id ,title, imageURI, price, rating,ratingCount);
                            modelArrayList.add(wishlist_model);

                        }
                    }
                    finally {
                        cursor.close();
                    }

                    recycler_wishlist.setAdapter( wishlistAdapter);
                }


                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
//                    adapter.swapCursor(null);
                    recycler_wishlist.setAdapter(null);

                }
            };


}