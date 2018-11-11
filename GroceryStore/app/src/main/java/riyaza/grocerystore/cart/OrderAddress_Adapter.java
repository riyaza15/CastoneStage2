package riyaza.grocerystore.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import riyaza.grocerystore.R;


public class OrderAddress_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderAddress_Model> address_models;
    private Context mContext;
    private String TAG = "orderaddress_adapter";

    private ArrayList<RelativeLayout> addrlayoutsList=  new ArrayList<>();
    private ArrayList<ImageView> imagelist = new ArrayList<>();

    public OrderAddress_Adapter (Context context, List<OrderAddress_Model> addressModels) {
        this.address_models = addressModels;
        this.mContext = context;

    }
    private class OrderAddressItemView extends RecyclerView.ViewHolder {
        TextView fullname,  fulladdress, phonenmber;
        ImageView imageselect;
        RelativeLayout address_layoutmain;


        public OrderAddressItemView(View itemView) {
            super(itemView);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
            phonenmber = (TextView) itemView.findViewById(R.id.phonenumber);
            fulladdress = (TextView) itemView.findViewById(R.id.fulladdress);
            imageselect = (ImageView) itemView.findViewById(R.id.imageselect);
            address_layoutmain = (RelativeLayout) itemView.findViewById(R.id.address_layoutmain);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_orderaddress_item, parent,false);
      //  Log.e(TAG, "  view created ");
        return new OrderAddressItemView(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

      //  Log.e(TAG, "bind view "+ position);
        final OrderAddress_Model model =  address_models.get(position);

        ((OrderAddressItemView) holder).fullname.setText(model.getaddrs_fullname());

        ((OrderAddressItemView) holder).phonenmber.setText(model.getaddrs_phone());
        ((OrderAddressItemView) holder).fulladdress.setText(model.getfulladdress());

        addrlayoutsList.add(((OrderAddressItemView) holder).address_layoutmain);
        imagelist.add(((OrderAddressItemView) holder).imageselect);

       // ((OrderAddressItemView) holder).address_layoutmain.setTag( model.getaddress_id());
        ((OrderAddressItemView) holder).address_layoutmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   Log.e(TAG, "  user select the addres " + model.getaddress_id() );

                (( OrderAddress) mContext).addressid = model.getaddress_id();

                for (int i=0; i<addrlayoutsList.size(); i++){
                     addrlayoutsList.get (i).setBackgroundResource(R.drawable.boarder_black_rounder_white);
                    imagelist.get(i).setVisibility(View.GONE);
                }

                ((OrderAddressItemView) holder).address_layoutmain.setBackgroundResource(R.drawable.boarder_green_rounder_white);
                ((OrderAddressItemView) holder).imageselect.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return address_models.size();
    }
}
