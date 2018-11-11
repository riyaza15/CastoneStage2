package riyaza.grocerystore.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;

import java.util.List;

import riyaza.grocerystore.R;


public class BestSelling_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BestSelling_Model> mBestSellingModelList;
    private String TAG ="BestSelling_adapter";
    private int mScrenwith;

    public BestSelling_Adapter(Context context, List<BestSelling_Model> list, int screenwidth ){
        mContext = context;
        mBestSellingModelList = list;
        mScrenwith =screenwidth;

    }

    private class BestSellingHolder extends RecyclerView.ViewHolder {
        ImageView prod_img;
        TextView prod_name, prod_oldPrice, prod_price;
        RatingBar prod_rating;
        CardView cardView;

        public BestSellingHolder(View itemView) {
            super(itemView);
            prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            prod_oldPrice = (TextView) itemView.findViewById(R.id.price_old);
            prod_price = (TextView) itemView.findViewById(R.id.prod_price);
            prod_rating =(RatingBar) itemView.findViewById(R.id.prod_rating);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( mScrenwith - (mScrenwith/100*65), LinearLayout.LayoutParams.MATCH_PARENT);
             params.setMargins(10,10,10,10);
             cardView.setLayoutParams(params);
             cardView.setPadding(5,5,5,5);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_bestselling, parent,false);
       // Log.e(TAG, "  view created ");
        return new BestSellingHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
          final BestSelling_Model model = mBestSellingModelList.get(position);
       //   Log.e(TAG, " assign value ");
        ((BestSellingHolder) holder).prod_name.setText(model.getProd_name());
        ((BestSellingHolder) holder).prod_oldPrice.setText(model.getOld_price());
        ((BestSellingHolder) holder).prod_price.setText(model.getPrice());
        ((BestSellingHolder) holder).prod_rating.setRating(  Float.valueOf(model.getRating()));

        Glide.with(mContext)
                .load(model.getImg_ulr())
                .into(((BestSellingHolder) holder).prod_img);
        // imageview glider lib to get imagge from url
        ((BestSellingHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, riyaza.grocerystore.productpreview.ProductDetails.class);
                intent.putExtra("prod_id", model.getProd_id());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mBestSellingModelList.size();
    }
}
