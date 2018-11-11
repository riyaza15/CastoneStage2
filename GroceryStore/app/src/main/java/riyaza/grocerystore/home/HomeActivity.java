package riyaza.grocerystore.home;

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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.GetbannerModel;
import riyaza.grocerystore.beanResponse.NewProdResopnce;
import riyaza.grocerystore.beanResponse.getCartDetails;
import riyaza.grocerystore.cart.CartDetails;
import riyaza.grocerystore.login.SigninActivity;
import riyaza.grocerystore.myaccount.myaccount;
import riyaza.grocerystore.wishlist.WishlistDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;



public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private String TAG ="HomeActivity";

    private RecyclerView recycler_bestSelling;
    private ArrayList<BestSelling_Model> bestSellingModelArrayList = new ArrayList<BestSelling_Model>();
    private BestSelling_Adapter bestSellingAdapter;

    private RecyclerView recycler_NewProd;
    private ArrayList<NewProd_Model> newPordModelList = new ArrayList<NewProd_Model>();
    private NewProd_Adapter newProdAdapter;

    private RecyclerView recycler_trending;
    private ArrayList<BestSelling_Model> trendingArrayList = new ArrayList<BestSelling_Model>();
    private BestSelling_Adapter trendingAdapter;


    private RecyclerView recycler_conditional;
    private ArrayList<BestSelling_Model> conditionalArraylist = new ArrayList<BestSelling_Model>();
    private BestSelling_Adapter conditionalAdapter;

    private BannerSlider bannerSlider;
    private List<Banner> remoteBanners=new ArrayList<>();

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Menu mainmenu;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

       /* for (int i=0; i<4; i++) {
            bestSellingModel = new BestSelling_Model("prod  "+ i, "", "5 USD", "3 USD", "4");
            bestSellingModelArrayList.add(bestSellingModel);
        }*/

        bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
  /*      remoteBanners.add(new RemoteBanner("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg"
        ));
        remoteBanners.add(new RemoteBanner("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg"
        ));
        bannerSlider.setBanners(remoteBanners);
*/

        /// for best selling
        recycler_bestSelling = (RecyclerView) findViewById(R.id.recycler_bestselling);
        LinearLayoutManager mLayoutManger = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);
        recycler_bestSelling.setLayoutManager(mLayoutManger);
        recycler_bestSelling.setItemAnimator(new DefaultItemAnimator());

        bestSellingAdapter = new BestSelling_Adapter(this,  bestSellingModelArrayList, GetScreenWidth());
        recycler_bestSelling.setAdapter(bestSellingAdapter);

        // for trending product
        recycler_trending = (RecyclerView) findViewById(R.id.recycler_trending);
        LinearLayoutManager mLayoutManger1 = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);
        recycler_trending.setLayoutManager(mLayoutManger1);
        recycler_trending.setItemAnimator(new DefaultItemAnimator());

        trendingAdapter = new BestSelling_Adapter(this,  trendingArrayList, GetScreenWidth());
        recycler_trending.setAdapter(trendingAdapter);

        // conditional prod
        recycler_conditional = (RecyclerView) findViewById(R.id.recycler_condit);
        LinearLayoutManager mLayoutManger2 = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);
        recycler_conditional.setLayoutManager(mLayoutManger2);
        recycler_conditional.setItemAnimator(new DefaultItemAnimator());

        conditionalAdapter = new BestSelling_Adapter(this,  conditionalArraylist, GetScreenWidth());
        recycler_conditional.setAdapter(conditionalAdapter);
       // bestSellingAdapter.notifyDataSetChanged();

      /*  for (int i=0; i<4; i++) {
            newProdModel = new NewProd_Model("","new product  "+ i, "");
            newPordModelList.add(newProdModel);
        } */
        // for new product
        recycler_NewProd = (RecyclerView) findViewById(R.id.recycler_newProd);
        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);
        recycler_NewProd.setLayoutManager(mLayoutManger3);
        recycler_NewProd.setItemAnimator(new DefaultItemAnimator());

        newProdAdapter = new NewProd_Adapter(this, newPordModelList, GetScreenWidth());
        recycler_NewProd.setAdapter(newProdAdapter);

        getNewProdRes();
        getBestSelling();
        getTrendingProd();
        getConditionalProd();
        getbannerimg();


    }

    public void getbannerimg(){
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.network_not_connected));

        }else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<GetbannerModel> call = service.getbannerModelCall("1234");
            call.enqueue(new Callback<GetbannerModel>() {
                @Override
                public void onResponse(Call<GetbannerModel> call, Response<GetbannerModel> response) {
                  //  Log.e(TAG, " banner response is "+ response.body().getInformation().toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1) {
                            if (response.body().getInformation().size() > 0) {

                                for (int i=0; i<response.body().getInformation().size(); i++) {
                                    remoteBanners.add(new RemoteBanner(response.body().getInformation().get(i).getImgurl()));

                                }


                            }else {

                                remoteBanners.add(new RemoteBanner("http://frh-groceries.com/ecommerce-android-app-project/downloads/mybanner1.jpg"));
                                remoteBanners.add(new RemoteBanner("http://frh-groceries.com/ecommerce-android-app-project/downloads/mybanner2%20.jpg"));
                            }

                            bannerSlider.setBanners(remoteBanners);
                        }else {
                            remoteBanners.add(new RemoteBanner("http://frh-groceries.com/ecommerce-android-app-project/downloads/mybanner1.jpg"));
                            remoteBanners.add(new RemoteBanner("http://frh-groceries.com/ecommerce-android-app-project/downloads/mybanner2%20.jpg"));
                            bannerSlider.setBanners(remoteBanners);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetbannerModel> call, Throwable t) {
                  //  Log.e(TAG, "fail banner ads "+ t.toString());
                }
            });
        }

    }

    public void getNewProdRes(){

        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.network_not_connected));

        }else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<NewProdResopnce> call = service.getNewProductRes("1234");
            call.enqueue(new Callback<NewProdResopnce>() {
                @Override
                public void onResponse(Call<NewProdResopnce> call, Response<NewProdResopnce> response) {
                   // Log.e(TAG, " response is "+ response.body().getInformation().toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                                if (response.body().getInformation().size()>0){

                                    newPordModelList.clear();
                                    for (int i=0; i< response.body().getInformation().size(); i++){

                                        newPordModelList.add(new NewProd_Model(response.body().getInformation().get(i).getId(), response.body().getInformation().get(i).getName(),
                                                response.body().getInformation().get(i).getImgUrl()));

                                    }

                                  newProdAdapter.notifyDataSetChanged();
                                }

                        }else {
                          //  Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                           // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    }else {
                       // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));

                      //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<NewProdResopnce> call, Throwable t) {
                  //  Log.e(TAG, "fail new prod "+ t.toString());

                }
            });

        }

    }

    public void getBestSelling(){
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.network_not_connected));

        }else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<NewProdResopnce> call = service.getBestselling("1234");
            call.enqueue(new Callback<NewProdResopnce>() {
                @Override
                public void onResponse(Call<NewProdResopnce> call, Response<NewProdResopnce> response) {
                  //  Log.e(TAG, " response is "+ response.body().getInformation().toString());

                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                                if (response.body().getInformation().size()>0) {
                                    bestSellingModelArrayList.clear();
                                    for (int i = 0; i < response.body().getInformation().size(); i++) {

                                        bestSellingModelArrayList.add(new BestSelling_Model(response.body().getInformation().get(i).getId(), response.body().getInformation().get(i).getName(),
                                                response.body().getInformation().get(i).getImgUrl(), response.body().getInformation().get(i).getMrp(),
                                                response.body().getInformation().get(i).getPrice(), response.body().getInformation().get(i).getRating()));


                                    }
                                    bestSellingAdapter.notifyDataSetChanged();
                                }
                        }else {
                          //  Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    }else {
                       // Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                        // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<NewProdResopnce> call, Throwable t) {
                  //  Log.e(TAG, " fail best sell "+ t.toString());
                }
            });


        }

    }

    public void getTrendingProd(){
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.network_not_connected));

        }else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<NewProdResopnce> call = service.getTrendingPRod("1234");
            call.enqueue(new Callback<NewProdResopnce>() {
                @Override
                public void onResponse(Call<NewProdResopnce> call, Response<NewProdResopnce> response) {
                 //   Log.e(TAG, " response is "+ response.body().getInformation().toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                            if (response.body().getInformation().size()>0) {
                                if (response.body().getInformation().size()>0) {
                                    trendingArrayList.clear();
                                    for (int i = 0; i < response.body().getInformation().size(); i++) {

                                        trendingArrayList.add(new BestSelling_Model(response.body().getInformation().get(i).getId(), response.body().getInformation().get(i).getName(),
                                                response.body().getInformation().get(i).getImgUrl(), response.body().getInformation().get(i).getMrp(),
                                                response.body().getInformation().get(i).getPrice(), response.body().getInformation().get(i).getRating()));


                                    }
                                    trendingAdapter.notifyDataSetChanged();
                                }
                            }
                        }else {
                           // Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    }else {
                     //   Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                        // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<NewProdResopnce> call, Throwable t) {
                  //  Log.e(TAG, " fail best sell "+ t.toString());
                }
            });


        }

    }

    public void getConditionalProd(){
        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.network_not_connected));

        }else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<NewProdResopnce> call = service.getConditionalPRod("1234");
            call.enqueue(new Callback<NewProdResopnce>() {
                @Override
                public void onResponse(Call<NewProdResopnce> call, Response<NewProdResopnce> response) {
                  //  Log.e(TAG, " response is "+ response.body().getInformation().toString());
                    if (response.body()!= null && response.isSuccessful()){
                        if (response.body().getStatus() ==1){
                            if (response.body().getInformation().size()>0) {
                                if (response.body().getInformation().size()>0) {
                                    conditionalArraylist.clear();
                                    for (int i = 0; i < response.body().getInformation().size(); i++) {

                                        conditionalArraylist.add(new BestSelling_Model(response.body().getInformation().get(i).getId(), response.body().getInformation().get(i).getName(),
                                                response.body().getInformation().get(i).getImgUrl(), response.body().getInformation().get(i).getMrp(),
                                                response.body().getInformation().get(i).getPrice(), response.body().getInformation().get(i).getRating()));


                                    }
                                    conditionalAdapter.notifyDataSetChanged();
                                }
                            }
                        }else {
                          //  Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    }else {
                      //  Log.e(TAG, "failed to get rnew prod "+ response.body().getMsg());
                        // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<NewProdResopnce> call, Throwable t) {
                    //Log.e(TAG, " fail best sell "+ t.toString());
                }
            });


        }

    }




    public int GetScreenWidth(){
        int  width =100;

        DisplayMetrics  displayMetrics = new DisplayMetrics();
        WindowManager windowManager =  (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        return width;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.myaccount_toolbar_menu, menu);
        mainmenu = menu;
        if (mainmenu!=null){
            AppUtilits.UpdateCartCount(HomeActivity.this, mainmenu);
            getUserCartDetails();
          //  Log.e(TAG , "  option menu create" );
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
      //  Log.e(TAG, "  option click "+ item.getTitle() );
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
                      //  Log.e("onClick: ", "-- " + searchEditText.getText().toString().trim());
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
       // Log.e(TAG, "navi option "+ item.getTitle());

        if (id == R.id.nav_home){


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
        if (mainmenu!=null){
            AppUtilits.UpdateCartCount(HomeActivity.this, mainmenu);
         //   Log.e(TAG , "  on resume mehtod " );
        }
    }

    public void getUserCartDetails(){

        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)){
            AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.network_not_connected));

        }else {
           //   Log.e(TAG, "  get user cart count "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<getCartDetails> call = service.getCartDetailsCall( "1234" , SharePreferenceUtils.getInstance().getString(Constant.QUOTE_ID),
                    SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));

            call.enqueue(new Callback<getCartDetails>() {
                @Override
                public void onResponse(Call<getCartDetails> call, Response<getCartDetails> response) {
                  //  Log.e(TAG, "response is "+ response.body() + "  ---- "+ new Gson().toJson(response.body()));
                    //  Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {
                        //    Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {
                            //      Log.e(TAG, "  ss sixe 3 ");
                          //  Log.e(TAG, " size is  "+ String.valueOf(response.body().getInformation().getProdDetails().size()));
                            SharePreferenceUtils.getInstance().saveInt( Constant.CART_ITEM_COUNT, response.body().getInformation().getProdDetails().size()  );
                            AppUtilits.UpdateCartCount(HomeActivity.this, mainmenu);

                        }
                    }

                }

                @Override
                public void onFailure(Call<getCartDetails> call, Throwable t) {
                 //   Log.e(TAG, "  fail- add to cart item "+ t.toString());
                  //  AppUtilits.displayMessage(CartDetails.this, getString(R.string.fail_toGetcart));

                }
            });
        }






    }

}
