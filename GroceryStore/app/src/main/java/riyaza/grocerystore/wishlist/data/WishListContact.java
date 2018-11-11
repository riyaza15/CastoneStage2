package riyaza.grocerystore.wishlist.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class WishListContact {

    private WishListContact (){}
    public static final String CONTENT_AUTHORITY ="riyaza.grocerystore";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WISHLIST = "whishlistTb";

    public static final class WhilistEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_WISHLIST);

        public static final  String TABLE_NAME = "whishlistTb";


        public static final String COLUMN_PRODID = "prod_id";
        public static final String COLUMN_TITLE = "prod_name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RATINGCOUNT = "rating_count";
        public static final String COLUMN_PORTER_URI = "img_uri";
    }

}
