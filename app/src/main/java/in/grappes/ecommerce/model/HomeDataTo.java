package in.grappes.ecommerce.model;

import java.util.ArrayList;

/**
 * Created by gunjit on 03/05/17.
 */

public class HomeDataTo {
    public ArrayList<CategoryTo> categoryTos = new ArrayList<>();
    public ArrayList<BannerTo> bannerTos = new ArrayList<>();
    public BannerTo lowerBanner;
    public ArrayList<Product> dealsOfTheDayList = new ArrayList<>();
}
