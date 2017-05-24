package in.grappes.ecommerce.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import in.grappes.ecommerce.model.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gunjit on 13/05/17.
 */

public class SharedPrefUtils {

    private static String SHARED_PREF_NAME = "sf_ecom";

    public static void clearSharedPref(Context context){
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

    public static User getCurrentUser(Context context) {

        User currentUser = null;

        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String userText = prefs.getString("user", null);
        if (userText != null) {
            currentUser = new Gson().fromJson(userText, User.class);
        }
        return currentUser;
    }

    public static void setCurrentUser(Context context, User user)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
        editor.putString("user", new Gson().toJson(user));
        editor.apply();
    }

    public static String getCurrentLocation(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String location = prefs.getString("location", null);
        return location;
    }

    public static void setUserLocation(Context context, String location)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
        editor.putString("location", location);
        editor.apply();
    }
}
