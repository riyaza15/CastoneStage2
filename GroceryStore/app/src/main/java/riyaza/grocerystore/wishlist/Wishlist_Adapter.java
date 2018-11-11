package riyaza.grocerystore.wishlist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import riyaza.grocerystore.R;
import riyaza.grocerystore.Utility.AppUtilits;
import riyaza.grocerystore.Utility.Constant;
import riyaza.grocerystore.Utility.NetworkUtility;
import riyaza.grocerystore.Utility.SharePreferenceUtils;
import riyaza.grocerystore.WebServices.ServiceWrapper;
import riyaza.grocerystore.beanResponse.AddtoCart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import riyaza.grocerystore.wishlist.data.WishListContact;


import static riyaza.grocerystore.wishlist.WishlistDetails.mainmenu;




public class Wishlist_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Wishlist_Model> wishlist_models;
    private Context mContext;
    private String TAG ="cartAdapter";
    public Wishlist_Adapter (Context context, List<Wishlist_Model>  wishlist_models){
        this. wishlist_models =  wishlist_models;
        this.mContext = context;

    }

    private class WishlistItemView extends RecyclerView.ViewHolder {
        ImageView prod_img, prod_delete, prod_add_cart;
        TextView prod_name, prod_rating_count, prod_price;
        AppCompatRatingBar prod_rating;


        public WishlistItemView(View itemView) {
            super(itemView);
            prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            prod_rating = (AppCompatRatingBar) itemView.findViewById(R.id.prod_rating);
            prod_rating_count = (TextView) itemView.findViewById(R.id.prod_rating_count);
            prod_price = (TextView) itemView.findViewById(R.id.prod_price);

            prod_delete = (ImageView) itemView.findViewById(R.id.prod_delete);
            prod_add_cart =(ImageView) itemView.findViewById(R.id.prod_add_cart);


        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_wishlist_item, parent,false);
      //  Log.e(TAG, "  view created ");
        return new WishlistItemView(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final Wishlist_Model model =  wishlist_models.get(position);

        ((WishlistItemView) holder).prod_name.setText(model.getProd_name());
        ((WishlistItemView) holder).prod_rating.setRating( Float.valueOf(model.getRating()) );
        ((WishlistItemView) holder).prod_rating_count.setText(model.getRating_count() );
        ((WishlistItemView) holder).prod_price.setText( model.getPrice());


        Glide.with(mContext)
                .load(model.getImg_ulr())
                .into(((WishlistItemView) holder).prod_img);


        ((WishlistItemView) holder).prod_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtocartapi( model.getProd_id(), ((WishlistItemView) holder).prod_price.getText().toString());
            }
        });

        ((WishlistItemView) holder).prod_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, riyaza.grocerystore.productpreview.ProductDetails.class);
                intent.putExtra("prod_id", model.getProd_id());
                mContext.startActivity(intent);
            }
        });

        ((WishlistItemView) holder).prod_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, riyaza.grocerystore.productpreview.ProductDetails.class);
                intent.putExtra("prod_id", model.getProd_id());
                mContext.startActivity(intent);
            }
        });

        ((WishlistItemView) holder).prod_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deleteProduct(model.getProd_id());
                deleteProductFromList(model.getProd_id());
                Log.e("wishadaptet", "delete call  "+ model.getProd_id() );
            }
        });
    }
    @Override
    public int getItemCount() {
        return wishlist_models.size();
    }

    public void addtocartapi(String prod_id, String prod_price){

        if (!NetworkUtility.isNetworkConnected(mContext)){
            AppUtilits.displayMessage(mContext,  mContext.getString(R.string.network_not_connected));

        }else {

            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<AddtoCart> call = service.addtoCartCall("12345", prod_id, SharePreferenceUtils.getInstance().getString(Constant.USER_DATA), prod_price );
            call.enqueue(new Callback<AddtoCart>() {
                @Override
                public void onResponse(Call<AddtoCart> call, Response<AddtoCart> response) {
                  //  Log.e(TAG, "reposne is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            AppUtilits.displayMessage(mContext, mContext.getString(R.string.add_to_cart));
                            SharePreferenceUtils.getInstance().saveString(Constant.QUOTE_ID, response.body().getInformation().getQouteId());

                            SharePreferenceUtils.getInstance().saveInt( Constant.CART_ITEM_COUNT,   response.body().getInformation().getCartCount());
                            AppUtilits.UpdateCartCount(mContext, mainmenu);
                            //(WishlistDetails)

                        }else {
                            AppUtilits.displayMessage(mContext, mContext.getString(R.string.fail_add_to_cart));
                        }
                    }else {
                        AppUtilits.displayMessage(mContext, mContext.getString(R.string.network_error));
                    }


                }

                @Override
                public void onFailure(Call<AddtoCart> call, Throwable t) {
                  //  Log.e(TAG, "  fail- add to cart item "+ t.toString());
                    AppUtilits.displayMessage(mContext, mContext.getString(R.string.fail_add_to_cart));
                }
            });
        }
    }


    public void deleteProduct(String prod_id){
        if (!NetworkUtility.isNetworkConnected(mContext)){
            AppUtilits.displayMessage(mContext,  mContext.getString(R.string.network_not_connected));

        }else {
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<AddtoCart > call = service.deleteWishlistprod("12345", SharePreferenceUtils.getInstance().getString(Constant.USER_DATA), prod_id );
            call.enqueue(new Callback<AddtoCart>() {
                @Override
                public void onResponse(Call<AddtoCart> call, Response<AddtoCart> response) {

                  //  Log.e(TAG, "reposne is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            AppUtilits.displayMessage(mContext, response.body().getMsg());

                            ((WishlistDetails) mContext).getUserWishlistDetails();
                            // update cart count
                            //    SharePreferenceUtils.getInstance().saveInt( Constant.CART_ITEM_COUNT,   SharePreferenceUtils.getInstance().getInteger(Constant.CART_ITEM_COUNT) -1);
                            //    AppUtilits.UpdateCartCount(mContext, CartDetails.mainmenu);

                        }else {
                            AppUtilits.displayMessage(mContext,  response.body().getMsg());
                        }
                    }else {
                        AppUtilits.displayMessage(mContext, mContext.getString(R.string.network_error));
                    }
                }

                @Override
                public void onFailure(Call<AddtoCart> call, Throwable t) {
                  //  Log.e(TAG, "  fail delete cart "+ t.toString());
                    AppUtilits.displayMessage(mContext, mContext.getString(R.string.fail_todeletewishlist));

                }
            });


        }

    }
    public void deleteProductFromList(String prod_id) {


        int moviesRemoved = mContext.getContentResolver().delete(WishListContact.WhilistEntry.CONTENT_URI,
                WishListContact.WhilistEntry.COLUMN_PRODID + " = ?", new String[]{prod_id});

        Log.e("wishadpter", "delte res "+ moviesRemoved);
        Intent intent = new Intent(mContext, WishlistDetails.class);
        mContext.startActivity(intent);


    }


}
