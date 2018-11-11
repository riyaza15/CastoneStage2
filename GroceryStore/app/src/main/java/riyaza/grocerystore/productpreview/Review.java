package riyaza.grocerystore.productpreview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import riyaza.grocerystore.R;


public class Review extends Fragment {

    private Context mContext;

    private String TAG =" review";

    private ArrayList<ReviewModel> modelArrayList = new ArrayList<>();
    private Review_Adapter adapter;
    private RecyclerView recycler_userreview;
    private String reviewstring ="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view=  inflater.inflate(R.layout.activity_product_review , container, false);
        mContext = view.getContext();


        recycler_userreview = (RecyclerView) view.findViewById(R.id.recycler_userreview);
        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager( mContext, LinearLayoutManager.HORIZONTAL, false);
        recycler_userreview.setLayoutManager(mLayoutManger3);
        recycler_userreview.setItemAnimator(new DefaultItemAnimator());


        reviewstring = (  ((ProductDetails) getActivity()).prod_review );


        try {
            JSONArray jsonArray;
            if (reviewstring!=null) {
                jsonArray = new JSONArray(reviewstring);
            }else{
                jsonArray =   new JSONArray("[]");
            }
           // Log.e(TAG , " array size  "+ jsonArray.length());

            for (int i =0; i< jsonArray.length(); i++){
                   JSONObject object = jsonArray.getJSONObject(i);
                  // object.get("title");
                   // Log.e(TAG, "  json value "+ object.get("title"));
                   // String title =
                    modelArrayList.add(new ReviewModel(String.valueOf(object.get("title")), String.valueOf(object.get("desc")),
                            String.valueOf(object.get("username")), String.valueOf( object.get("date")), String.valueOf( object.get("rating"))));
            }


        } catch (JSONException e) {
            e.printStackTrace();
          //  Log.e(TAG, " json error "+ e.toString());
        }

        adapter = new Review_Adapter(mContext, modelArrayList);
        recycler_userreview.setAdapter(adapter);


        return  view; //super.onCreateView(inflater, container, savedInstanceState);
    }
}
