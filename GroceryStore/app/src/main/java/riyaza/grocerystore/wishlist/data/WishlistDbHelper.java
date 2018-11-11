package riyaza.grocerystore.wishlist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WishlistDbHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "whilist3.db";
    private static final int DATABASE_VERSION = 1;

    public WishlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE "
                + WishListContact.WhilistEntry.TABLE_NAME
                + " (" + WishListContact.WhilistEntry.COLUMN_PRODID + " TEXT PRIMARY KEY, "
                + WishListContact.WhilistEntry.COLUMN_TITLE + " TEXT  , "
                + WishListContact.WhilistEntry.COLUMN_PRICE + " TEXT  , "
                + WishListContact.WhilistEntry.COLUMN_RATING + " TEXT , "
                + WishListContact.WhilistEntry.COLUMN_RATINGCOUNT + " TEXT , "
                + WishListContact.WhilistEntry.COLUMN_PORTER_URI + " TEXT "
                + " );";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
