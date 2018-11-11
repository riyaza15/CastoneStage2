package riyaza.grocerystore;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.ArrayList;

import riyaza.grocerystore.wishlist.Wishlist_Adapter;
import riyaza.grocerystore.wishlist.Wishlist_Model;
import riyaza.grocerystore.wishlist.data.WishListContact;

public class WidgetActivity extends AppCompatActivity {
    private RecyclerView recycler_wishlist;
    private TextView wishlist_count;
    private Wishlist_Adapter wishlistAdapter;
    private ArrayList<Wishlist_Model> modelArrayList = new ArrayList<>();
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private AppWidgetManager widgetManager;
    private RemoteViews views;
    private ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        layout= (ConstraintLayout) findViewById(R.id.layout_1);

        recycler_wishlist = (RecyclerView) findViewById(R.id.recycler_wishlist);
        wishlist_count = (TextView) findViewById(R.id.wishlist_count);

        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_wishlist.setLayoutManager( mLayoutManger3);
        recycler_wishlist.setItemAnimator(new DefaultItemAnimator());

        wishlistAdapter = new Wishlist_Adapter(this,  modelArrayList);
        recycler_wishlist.setAdapter( wishlistAdapter);

        diplayFavoriteList();
        widgetManager = AppWidgetManager.getInstance(this);
        views = new RemoteViews(this.getPackageName(), R.layout.list_widget);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               diplayFavoriteList();
               displayWidget();
//                views.setTextViewText(R.id.wigdet_title, "Wish list");
//                StringBuilder ing= new StringBuilder();
//                 for (int i=0; i< modelArrayList.size(); i=i+1){
//                     ing.append("_").append(modelArrayList.get(i).getProd_name())
//                             .append("").append("\n");
//
//                 }
//
//                views.setTextViewText(R.id.wishlist_List, ing.toString());
//
//
//                widgetManager.updateAppWidget(mAppWidgetId, views);
//                Intent resultValue = new Intent();
//                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
//                setResult(RESULT_OK, resultValue);
//                finish();
            }
        });
    }

    public void displayWidget(){

        views.setTextViewText(R.id.wigdet_title, "Wish list");
        StringBuilder ing= new StringBuilder();
        for (int i=0; i< modelArrayList.size(); i=i+1){
            ing.append("_").append(modelArrayList.get(i).getProd_name())
                    .append("").append("\n");

        }

        views.setTextViewText(R.id.wishlist_List, ing.toString());


        widgetManager.updateAppWidget(mAppWidgetId, views);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
    public void diplayFavoriteList(){
        String[] projection = {
                WishListContact.WhilistEntry.COLUMN_PRODID,
                WishListContact.WhilistEntry.COLUMN_TITLE  ,
                WishListContact.WhilistEntry.COLUMN_PRICE,
                WishListContact.WhilistEntry.COLUMN_RATING,
                WishListContact.WhilistEntry.COLUMN_RATINGCOUNT,
                WishListContact.WhilistEntry.COLUMN_PORTER_URI

        };

        Cursor cursor=getContentResolver().query(
                WishListContact.WhilistEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

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


}
