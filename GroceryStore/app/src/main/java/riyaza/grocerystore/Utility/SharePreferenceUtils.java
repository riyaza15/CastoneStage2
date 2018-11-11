package riyaza.grocerystore.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import riyaza.grocerystore.MyApp;


public class SharePreferenceUtils {

    private static String PREFERENCE_NAME = "shopeasy";
    private static SharePreferenceUtils sharePreferenceUtils;
    private SharedPreferences sharedPreferences;

    private SharePreferenceUtils(Context context){
        PREFERENCE_NAME = PREFERENCE_NAME + context.getPackageName();
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharePreferenceUtils getInstance(){
        if (sharePreferenceUtils == null){
             sharePreferenceUtils = new SharePreferenceUtils(MyApp.getContext());
        }
        return sharePreferenceUtils;
    }

    // login response user_id 1234
    public void saveString(String key,  String Val ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, Val);
        editor.commit();
    }

    public String getString(String key, String defVal){
        return sharedPreferences.getString(key, defVal);
    }


    public String getString(String key){
        return sharedPreferences.getString(key, "");
    }

    public void saveInt(String key,  int Val ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, Val);
        editor.commit();
    }

    public int getInteger(String key){ return sharedPreferences.getInt(key, 0 ); }

    /**
     * Clear all values from this preference
     */
    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * Clear value of given key from this preference
     *
     * @param key name of the key whose value to be removed
     */
    public void clearString(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
