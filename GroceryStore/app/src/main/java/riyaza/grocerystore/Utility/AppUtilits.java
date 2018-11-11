package riyaza.grocerystore.Utility;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import riyaza.grocerystore.R;


public class AppUtilits {



    public static void displayMessage(Context mContext, String message){

      MessageDialog messageDialog = null;
      if (messageDialog == null)
         messageDialog = new MessageDialog(mContext, message);
      messageDialog.displayMessageShow();

    }

    public static void UpdateCartCount(final Context mContext, Menu mainmenu){
        MenuItem item = mainmenu.findItem(R.id.cart);
        //Log.e("apputil ", " menu title "+ item.getTitle() );

        TextView cartcount =  (TextView) item.getActionView().findViewById(R.id.cart_count);
        cartcount.setText( String.valueOf(SharePreferenceUtils.getInstance().getInteger(Constant.CART_ITEM_COUNT)));

        if (SharePreferenceUtils.getInstance().getInteger(Constant.CART_ITEM_COUNT)>0){
            cartcount.setVisibility(View.VISIBLE);
        }else {
            cartcount.setVisibility(View.GONE);
        }

        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Log.e("apputil ", " cart click");
                Intent intent = new Intent(mContext, riyaza.grocerystore.cart.CartDetails.class);
                mContext.startActivity(intent);
            }
        });

    }






}
