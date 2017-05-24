package in.grappes.ecommerce.ui.binder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.BannerTo;
import in.grappes.ecommerce.ui.adapter.DataBindAdapter;

/**
 * Created by gunjit on 07/04/17.
 */

public class LowerBannerDataBinder extends DataBinder<LowerBannerDataBinder.ViewHolder>{
    BannerTo bannerTo;
    Context context;
    public LowerBannerDataBinder(DataBindAdapter dataBindAdapter, Context context, BannerTo lowerBanner) {
        super(dataBindAdapter);
        this.bannerTo = lowerBanner;
        this.context = context;
    }

    @Override
    public LowerBannerDataBinder.ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.lower_banner_layout, parent, false);
        return new LowerBannerDataBinder.ViewHolder(view);
    }

    @Override
    public void bindViewHolder(LowerBannerDataBinder.ViewHolder holder, int position) {
        Glide.with(context).load(bannerTo.imageUrl).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.lower_banner_image);
        }
    }
}
