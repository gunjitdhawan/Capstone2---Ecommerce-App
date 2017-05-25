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
import in.grappes.ecommerce.model.BannerTo;

/**
 * Created by gunjit on 03/12/16.
 */

public class ViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<BannerTo> pagerToArrayList;
    ProgressDialog progressDialog;

    public ViewPagerAdapter(Context context, ArrayList<BannerTo> pagerToArrayList) {
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
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(mContext.getResources().getString(R.string.wait));
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Glide.with(mContext).load(pagerToArrayList.get(position).imageUrl).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}