package in.grappes.ecommerce.ui.binder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import in.grappes.ecommerce.ui.adapter.DataBindAdapter;

/**
 * Class for binding view and data
 * <p/>
 * Created by yqritc on 2015/03/01.
 */
abstract public class DataBinder<T extends RecyclerView.ViewHolder> {

    private DataBindAdapter mDataBindAdapter;

    public DataBinder(DataBindAdapter dataBindAdapter) {
        mDataBindAdapter = dataBindAdapter;
    }



    abstract public T newViewHolder(ViewGroup parent);

    abstract public void bindViewHolder(T holder, int position);


    public final void notifyDataSetChanged() {
        mDataBindAdapter.notifyDataSetChanged();
    }
    
}
