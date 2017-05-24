package in.grappes.ecommerce.ui.adapter;

import android.content.Context;

import java.util.HashMap;

import in.grappes.ecommerce.model.HomeDataTo;
import in.grappes.ecommerce.ui.binder.CategoryStripDataBinder;
import in.grappes.ecommerce.ui.binder.DataBinder;
import in.grappes.ecommerce.ui.binder.DealOfDayDataBinder;
import in.grappes.ecommerce.ui.binder.LowerBannerDataBinder;
import in.grappes.ecommerce.ui.binder.UpperBannerDataBinder;

/**
 * Created by gunjit on 07/04/17.
 */

public class HomeAdapter extends DataBindAdapter {

    public HashMap<Integer, DataBinder> dataBinderHashMap = new HashMap<>();
    public Context context;
    public CategoryStripDataBinder categoryStripDataBinder;
    public UpperBannerDataBinder upperBannerDataBinder;
    public LowerBannerDataBinder lowerBannerDataBinder;
    public DealOfDayDataBinder dealOfDayDataBinder;
    private final int CATEGORY_STRIP_VIEW = 101;
    private final int UPPER_BANNER_VIEW = 102;
    private final int LOWER_BANNER_VIEW = 103;
    private final int DEAL_OF_THE_DAY_VIEW = 104;

    //public List<FeedItem> recentlyAskedQuestionList = new ArrayList<>();


    public  HomeAdapter(Context context, HomeDataTo homeDataTo)
    {
        this.context = context;
        categoryStripDataBinder = new CategoryStripDataBinder(this, context, homeDataTo.categoryTos);
        upperBannerDataBinder = new UpperBannerDataBinder(this, context, homeDataTo.bannerTos);
        lowerBannerDataBinder = new LowerBannerDataBinder(this, context, homeDataTo.lowerBanner);
        dealOfDayDataBinder = new DealOfDayDataBinder(this, context, homeDataTo.dealsOfTheDayList);

        dataBinderHashMap.put(CATEGORY_STRIP_VIEW, categoryStripDataBinder);
        dataBinderHashMap.put(UPPER_BANNER_VIEW, upperBannerDataBinder);
        dataBinderHashMap.put(LOWER_BANNER_VIEW, lowerBannerDataBinder);
        dataBinderHashMap.put(DEAL_OF_THE_DAY_VIEW, dealOfDayDataBinder);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CATEGORY_STRIP_VIEW;
        }
        else if (position == 1) {
            return UPPER_BANNER_VIEW;
        }
        else if(position == 2)
        {
            return DEAL_OF_THE_DAY_VIEW;
        }
        return LOWER_BANNER_VIEW;
    }

    @Override
    public <T extends DataBinder> T getDataBinder(int viewType) {
        return (T) dataBinderHashMap.get(viewType);
    }
}
