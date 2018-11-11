package riyaza.grocerystore.productpreview;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import riyaza.grocerystore.R;


public class Review_Adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ReviewModel> mReviewModelList;
    private String TAG ="review_adapter";

    public Review_Adapter(Context context, List<ReviewModel> list ){
        mContext = context;
        mReviewModelList = list;

    }

    private class ReviewHolder extends RecyclerView.ViewHolder {

        TextView review_title, review_details, username, postdate;
        AppCompatRatingBar review_rating;

        public ReviewHolder(View itemView) {
            super(itemView);
            review_title = (TextView) itemView.findViewById(R.id.title);
            review_details = (TextView) itemView.findViewById(R.id.descrip);
            username = (TextView) itemView.findViewById(R.id.username);
            postdate =(TextView) itemView.findViewById(R.id.date);
            review_rating = (AppCompatRatingBar) itemView.findViewById(R.id.review_rating);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_review_item, parent,false);
       // Log.e(TAG, "  view created ");
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ReviewModel model = mReviewModelList.get(position);

       // Log.e(TAG, " assign value ");
        (( ReviewHolder) holder).review_title.setText(model.getTitle());
        (( ReviewHolder) holder).review_details.setText(model.getDesc());
        (( ReviewHolder) holder).username.setText(model.getUsername());
        (( ReviewHolder) holder).postdate.setText(model.getDate());
        (( ReviewHolder) holder).review_rating.setRating( Float.valueOf(model.getRating()));
    //    ((BestSelling_Adapter.BestSellingHolder) holder).prod_oldPrice.setText(model.getOld_price());
  //      ((BestSelling_Adapter.BestSellingHolder) holder).prod_price.setText(model.getPrice());
//        ((BestSelling_Adapter.BestSellingHolder) holder).prod_rating.setRating(  Float.valueOf(model.getRating()));


    }

    @Override
    public int getItemCount() {
        return mReviewModelList.size();
    }
}

