package riyaza.grocerystore.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import riyaza.grocerystore.R;

public class OrderSummery_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Cartitem_Model> cartitem_models;
    private Context mContext;
    private String TAG = "order_adapter";

    public OrderSummery_Adapter(Context context, List<Cartitem_Model> cartitemModels) {
        this.cartitem_models = cartitemModels;
        this.mContext = context;

    }
    private class ordersummeryItemView extends RecyclerView.ViewHolder {
        TextView prod_name,  prod_qty, prod_price;


        public  ordersummeryItemView(View itemView) {
            super(itemView);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            prod_price = (TextView) itemView.findViewById(R.id.prod_price);
            prod_qty = (TextView) itemView.findViewById(R.id.prod_qty);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ordersum_item, parent,false);
       // Log.e(TAG, "  view created ");
        return new ordersummeryItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

      //  Log.e(TAG, "bind view "+ position);
        final Cartitem_Model model =  cartitem_models.get(position);

        ((ordersummeryItemView) holder).prod_name.setText(model.getProd_name());
        ((ordersummeryItemView) holder).prod_price.setText(model.getPrice());
        ((ordersummeryItemView) holder).prod_qty.setText(model.getQty());


    }

    @Override
    public int getItemCount() {
        return cartitem_models.size();
    }
}