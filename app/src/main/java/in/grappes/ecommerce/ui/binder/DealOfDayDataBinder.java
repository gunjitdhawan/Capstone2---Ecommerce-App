package in.grappes.ecommerce.ui.binder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.Product;
import in.grappes.ecommerce.ui.adapter.DataBindAdapter;
import in.grappes.ecommerce.ui.adapter.ProductGridAdapter;

/**
 * Created by gunjit on 07/04/17.
 */

public class DealOfDayDataBinder extends DataBinder<DealOfDayDataBinder.ViewHolder>{
    ArrayList<Product> products = new ArrayList<>();
    Context context;
    private GridLayoutManager glm;

    public DealOfDayDataBinder(DataBindAdapter dataBindAdapter, Context context, ArrayList<Product> products) {
        super(dataBindAdapter);
        this.context = context;
        this.products = products;
    }

    @Override
    public DealOfDayDataBinder.ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.popular_product_layout, parent, false);
        return new DealOfDayDataBinder.ViewHolder(view);
    }

    @Override
    public void bindViewHolder(DealOfDayDataBinder.ViewHolder holder, int position) {
        setRecyclerView(holder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rView;
        public ViewHolder(View itemView) {
            super(itemView);
            rView = (RecyclerView)itemView.findViewById(R.id.popular_product_list);}
    }

    private void setRecyclerView(ViewHolder holder) {
        glm = new GridLayoutManager(context, 2);


        holder.rView.setHasFixedSize(true);
        holder.rView.setLayoutManager(glm);

        ProductGridAdapter rcAdapter = new ProductGridAdapter(context, products);
        holder.rView.setAdapter(rcAdapter);
    }
}
