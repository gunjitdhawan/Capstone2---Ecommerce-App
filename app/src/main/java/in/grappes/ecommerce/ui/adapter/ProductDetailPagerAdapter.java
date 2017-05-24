package in.grappes.ecommerce.ui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.grappes.ecommerce.R;

/**
 * Created by gunjit on 03/12/16.
 */

public class ProductDetailPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<String> pagerToArrayList;
    ProgressDialog progressDialog;

    public ProductDetailPagerAdapter(Context context, ArrayList<String> pagerToArrayList) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pagerToArrayList = pagerToArrayList;
    }

    @Override
    public int getCount() {
        if(pagerToArrayList!=null) {
            return pagerToArrayList.size();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        //TODO Load Image with Glide
        Glide.with(mContext).load(pagerToArrayList.get(position)).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}