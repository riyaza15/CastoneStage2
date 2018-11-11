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
import android.widget.TextView;

//import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;

import java.util.List;

import riyaza.grocerystore.R;



public class NewProd_Adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<NewProd_Model> mNewProdModelList;
    private String TAG ="NewProd_adapter";
    private int mScrenwith;

    public NewProd_Adapter (Context context, List<NewProd_Model> list, int screenwidth ){
        mContext = context;
        mNewProdModelList = list;
        mScrenwith =screenwidth;
// response.body.getString("information")
    }

    private class NewProductHolder extends RecyclerView.ViewHolder {
        ImageView prod_img;
        TextView prod_name;
        CardView cardView;

        public NewProductHolder(View itemView) {
            super(itemView);
            prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( mScrenwith - (mScrenwith/100*65), LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(10,10,10,10);
            cardView.setLayoutParams(params);
            cardView.setPadding(5,5,5,5);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_newproduct, parent,false);
       // Log.e(TAG, "  view created ");
        return new NewProductHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewProd_Model model = mNewProdModelList.get(position);
       // Log.e(TAG, " assign value ");
        ((NewProductHolder) holder).prod_name.setText(model.getProd_name());

        Glide.with(mContext)
                .load(model.getImg_ulr())
                .into(((NewProductHolder) holder).prod_img);
        // imageview glider lib to get imagge from url
        ((NewProductHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
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
        return mNewProdModelList.size();
    }
}

