package in.grappes.ecommerce.ui.binder;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.BannerTo;
import in.grappes.ecommerce.ui.adapter.DataBindAdapter;
import in.grappes.ecommerce.ui.adapter.ViewPagerAdapter;
import in.grappes.ecommerce.utils.AppUtils;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by gunjit on 07/04/17.
 */

public class UpperBannerDataBinder extends DataBinder<UpperBannerDataBinder.ViewHolder> {
    Context context;
    ArrayList<BannerTo> pagerList = new ArrayList<>();

    public UpperBannerDataBinder(DataBindAdapter dataBindAdapter, Context context, ArrayList<BannerTo> pagerList) {
        super(dataBindAdapter);
        this.context = context;
        this.pagerList = pagerList;

    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.test_series_view_pager, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AutoScrollViewPager customViewPager;
        CircleIndicator indicator;
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context, pagerList);

        public ViewHolder(View itemView) {
            super(itemView);

            customViewPager = (AutoScrollViewPager) itemView.findViewById(R.id.homeViewPager);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(AppUtils.getDeviceDimensions((Activity) context).x, AppUtils.getDeviceDimensions((Activity) context).x/2);
            customViewPager.setLayoutParams(layoutParams);

            customViewPager.startAutoScroll();
            customViewPager.setInterval(5000);
            customViewPager.setCycle(true);
            customViewPager.setOffscreenPageLimit(2);

            customViewPager.setAdapter(viewPagerAdapter);
            indicator = (CircleIndicator) itemView.findViewById(R.id.indicator);
            indicator.setViewPager(customViewPager);

            customViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    try {

                        customViewPager.stopAutoScroll();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                customViewPager.startAutoScroll();
                            }
                        }, 5000);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            if(pagerList==null || pagerList.size()<1)
            {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                params.height = 0;
            }


        }
    }
}
