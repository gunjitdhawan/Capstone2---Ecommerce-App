package in.grappes.ecommerce.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import in.grappes.ecommerce.ui.binder.DataBinder;

/**
 * Adapter class for managing a set of data binders
 * <p/>
 * Created by yqritc on 2015/03/01.
 */
public abstract class DataBindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getDataBinder(viewType).newViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        getDataBinder(getItemViewType(position)).bindViewHolder(viewHolder, position);
    }

    @Override
    public abstract int getItemCount();

    @Override
    public abstract int getItemViewType(int position);

    public abstract <T extends DataBinder> T getDataBinder(int viewType);


}
