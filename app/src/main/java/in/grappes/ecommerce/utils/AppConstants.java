package in.grappes.ecommerce.utils;

/**
 * Created by gunjit on 10/03/17.
 */

public class AppConstants {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "9XpAqHPHP2NmLhUABTVGq4god";
    public static final String TWITTER_SECRET = "lX62Ywv5QQYvRdYXyNYJ7d2jLHHLVnMdARYHcTiElUmuXa7Qs7";
    public static final int EDIT_PROFILE = 112;
    public static final int LOGIN = 113;
    public static int CHANGE_ADDRESS = 104;
    enum LoginDestination {
        PROFILE, CHECKOUT
    }
}
