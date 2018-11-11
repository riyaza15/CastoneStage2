package riyaza.grocerystore.wishlist.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static riyaza.grocerystore.wishlist.data.WishListContact.WhilistEntry.TABLE_NAME;

public class WishlistProvider extends ContentProvider {
    public static final String LOG_TAG = WishlistProvider.class.getSimpleName();
    private WishlistDbHelper wishlistDbHelper;
    public static final int WHISHLIST = 100;
    public static final int WHISHLIST_ID = 101;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(WishListContact.CONTENT_AUTHORITY, WishListContact.PATH_WISHLIST, WHISHLIST);
        sUriMatcher.addURI(WishListContact.CONTENT_AUTHORITY, WishListContact.PATH_WISHLIST + "/#", WHISHLIST_ID);
    }


    @Override
    public boolean onCreate() {
        wishlistDbHelper= new WishlistDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = wishlistDbHelper.getReadableDatabase();
        Cursor cursor;
        String id;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case WHISHLIST:
                cursor = database.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case WHISHLIST_ID:

                id = uri.getPathSegments().get(1);

                cursor = database.query(TABLE_NAME,
                        projection,
                        WishListContact.WhilistEntry.COLUMN_PRODID + " = " + id,
                        null,
                        null,
                        null,
                        sortOrder);
                break;



            default:
                throw new UnsupportedOperationException("Cannot query the Unknown uri: " + uri);

        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WHISHLIST:
                return insertMovie(uri, values);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }



    private Uri insertMovie(Uri uri, ContentValues values) {

        SQLiteDatabase database = wishlistDbHelper.getWritableDatabase();
        long id = database.insert(TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = wishlistDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if (null == selection) selection = "1";
        switch (match) {
            case WHISHLIST:
                rowsDeleted = database.delete(TABLE_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


}
