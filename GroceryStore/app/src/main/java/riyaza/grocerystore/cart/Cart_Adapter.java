package riyaza.grocerystore.cart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import riyaza.grocerystore.beanResponse.EditCartItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Cartitem_Model> cartitem_models;
    private Context mContext;
    private String TAG ="cartAdapter";
    public Cart_Adapter (Context context, List<Cartitem_Model> cartitemModels){
        this.cartitem_models = cartitemModels;
        this.mContext = context;

    }

    private class cartItemView extends RecyclerView.ViewHolder {
        ImageView prod_img, prod_delete, add_to_wishlist;
        TextView prod_name, prod_oldPrice, prod_price;
        CardView cardView;
        EditText prod_qty;

        public cartItemView(View itemView) {
            super(itemView);
            prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            prod_oldPrice = (TextView) itemView.findViewById(R.id.price_old);
            prod_price = (TextView) itemView.findViewById(R.id.prod_price);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            prod_delete = (ImageView) itemView.findViewById(R.id.cart_delete);
            prod_qty = (EditText) itemView.findViewById(R.id.prod_qty);
            add_to_wishlist =(ImageView) itemView.findViewById(R.id.add_to_wishlist);


        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cartdetails_item, parent,false);
       // Log.e(TAG, "  view created ");
        return new cartItemView(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

       final Cartitem_Model model =  cartitem_models.get(position);

        ((cartItemView) holder).prod_name.setText(model.getProd_name());
        ((cartItemView) holder).prod_oldPrice.setText(model.getOld_price());
        ((cartItemView) holder).prod_price.setText(model.getPrice());
        ((cartItemView) holder).prod_qty.setText(model.getQty());

        Glide.with(mContext)
                .load(model.getImg_ulr())
                .into(((cartItemView) holder).prod_img);


        ((cartItemView) holder).add_to_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtowishlist( model.getProd_id());
            }
        });

        ((cartItemView) holder).prod_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, riyaza.grocerystore.productpreview.ProductDetails.class);
                intent.putExtra("prod_id", model.getProd_id());
                mContext.startActivity(intent);
            }
        });

        ((cartItemView) holder).prod_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, riyaza.grocerystore.productpreview.ProductDetails.class);
                intent.putExtra("prod_id", model.getProd_id());
                mContext.startActivity(intent);
            }
        });

        // delete product from cart
        ((cartItemView) holder).prod_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteProduct(model.getProd_id());
            }
        });

        ((cartItemView) holder).prod_qty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_GO ||i == EditorInfo.IME_ACTION_SEND ){

                    if (!((cartItemView) holder).prod_qty.getText().toString().trim().equals("") || !((cartItemView) holder).prod_qty.getText().toString().trim().equals("0")){

                        updateCartQty( model.getProd_id(), ((cartItemView) holder).prod_qty.getText().toString().trim());

                    }


                }


                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartitem_models.size();
    }



    public void addtowishlist( String prod_id){
        if (!NetworkUtility.isNetworkConnected(mContext)){
            AppUtilits.displayMessage(mContext,  mContext.getString(R.string.network_not_connected));

        }else {
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<AddtoCart> call = service.addtowishlistcall("12345", prod_id, SharePreferenceUtils.getInstance().getString(Constant.USER_DATA) );
            call.enqueue(new Callback<AddtoCart>() {
                @Override
                public void onResponse(Call<AddtoCart> call, Response<AddtoCart> response) {

                   // Log.e(TAG, "reposne is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            AppUtilits.displayMessage(mContext, mContext.getString(R.string.add_to_wishlist));
                        }else {
                            AppUtilits.displayMessage(mContext, mContext.getString(R.string.fail_add_to_wishlist));
                        }
                    }else {
                        AppUtilits.displayMessage(mContext, mContext.getString(R.string.network_error));
                    }
                }

                @Override
                public void onFailure(Call<AddtoCart> call, Throwable t) {
                    AppUtilits.displayMessage(mContext, mContext.getString(R.string.fail_add_to_wishlist));
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
            Call<AddtoCart > call = service.deletecartprod("12345", SharePreferenceUtils.getInstance().getString(Constant.USER_DATA), prod_id );
            call.enqueue(new Callback<AddtoCart>() {
                @Override
                public void onResponse(Call<AddtoCart> call, Response<AddtoCart> response) {

                  //  Log.e(TAG, "reposne is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            AppUtilits.displayMessage(mContext, response.body().getMsg());

                            ((CartDetails) mContext).getUserCartDetails();
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
                   // Log.e(TAG, "  fail delete cart "+ t.toString());
                    AppUtilits.displayMessage(mContext, mContext.getString(R.string.fail_todeletecart));

                }
            });


        }

    }

    public void updateCartQty(String prod_id, String prod_qty){
        if (!NetworkUtility.isNetworkConnected(mContext)){
            AppUtilits.displayMessage(mContext,  mContext.getString(R.string.network_not_connected));

        }else {
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<EditCartItem>  call = service.editcartcartprodqty( "12345", SharePreferenceUtils.getInstance().getString(Constant.USER_DATA), prod_id , prod_qty);
            call.enqueue(new Callback<EditCartItem>() {
                @Override
                public void onResponse(Call<EditCartItem> call, Response<EditCartItem> response) {

                    //Log.e(TAG, " edit response "+ response.toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            AppUtilits.displayMessage(mContext, response.body().getInformation().getStatus());

                            if (response.body().getInformation().getStatus().equalsIgnoreCase("successful update cart")){
                                ((CartDetails) mContext).cart_totalamt.setText(  "$ " + response.body().getInformation().getTotalprice());
                            }


                        }else {
                            AppUtilits.displayMessage(mContext,  response.body().getMsg());
                        }
                    }else {
                        AppUtilits.displayMessage(mContext, mContext.getString(R.string.network_error));
                    }
                }


                @Override
                public void onFailure(Call<EditCartItem> call, Throwable t) {
                   // Log.e(TAG, " edit fail "+ t.toString());
                    AppUtilits.displayMessage(mContext,  mContext.getString(R.string.fail_toeditcart));
                }
            });





        }

    }
























}
