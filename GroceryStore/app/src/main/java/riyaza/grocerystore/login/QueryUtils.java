package riyaza.grocerystore.login;

import android.graphics.Movie;
import android.text.TextUtils;
import android.util.Log;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import riyaza.grocerystore.beanResponse.userSignin;
import riyaza.grocerystore.productpreview.Review;

import static android.support.constraint.Constraints.TAG;
import static android.view.View.STATUS_BAR_HIDDEN;


public class QueryUtils {


    private static final String STATUS = "status" ;
    private static final String MESSAGE = "message";
    private static final String INFORMATION = "information";
    private static final String USERID = "user_id";
    private static final String FULLNAME = "fullname";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";


    private QueryUtils(){
    }
    public static userSignin fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem creating the HTTP request.", e);
        }
        userSignin user = extractFeatureFromJson(jsonResponse);
        return user;
    }


    private static userSignin extractFeatureFromJson(String userJSON) {
        if (TextUtils.isEmpty(userJSON)) {
            return null;
        }
        userSignin user = null;
        try {
            JSONObject baseJson = new JSONObject(userJSON);
            JSONObject sts = baseJson.getJSONObject(STATUS);
            JSONObject msg = baseJson.getJSONObject(MESSAGE);
            JSONObject inf = baseJson.getJSONObject(INFORMATION);
            String userid= inf.getString(USERID);
            String fullname= inf.getString(FULLNAME);
            String email= inf.getString(EMAIL);
            String phone= inf.getString(PHONE);

            userSignin.Information info = new userSignin.Information(USERID,FULLNAME,EMAIL,PHONE);

             user= new userSignin(STATUS,MESSAGE,info);

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem in the movie JSON results", e);
        }
        return user;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem building the URL ", e);
        }
        Log.v("URL", String.valueOf(url));
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
                Log.i("Detail", "connected "+ line);

            }
        }
        return output.toString();
    }












}
