package in.grappes.ecommerce.ui.binder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.CategoryTo;
import in.grappes.ecommerce.ui.activities.ProductListActivity;
import in.grappes.ecommerce.ui.adapter.DataBindAdapter;

/**
 * Created by gunjit on 07/04/17.
 */

public class CategoryStripDataBinder extends DataBinder<CategoryStripDataBinder.ViewHolder>{
    Context context;
    ArrayList<CategoryTo> categoryToArrayList = new ArrayList<>();
    public CategoryStripDataBinder(DataBindAdapter dataBindAdapter, Context context, ArrayList<CategoryTo> categoryTos) {
        super(dataBindAdapter);
        this.context = context;
        this.categoryToArrayList = categoryTos;
    }

    @Override
    public CategoryStripDataBinder.ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.category_strip_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(CategoryStripDataBinder.ViewHolder holder, int position) {
        holder.categoryStripLayout.removeAllViews();
        for(final CategoryTo categoryTo : categoryToArrayList) {
            View view = View.inflate(context, R.layout.category_item, null);
            TextView catName = (TextView) view.findViewById(R.id.cat_name);
            View root = view.findViewById(R.id.root_layout);
            catName.setText(categoryTo.categoryName);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startProductListActivityWithCategory(categoryTo);
                }
            });


            holder.categoryStripLayout.addView(view);
        }
    }

    private void startProductListActivityWithCategory(CategoryTo categoryTo) {
        Intent i = new Intent(context, ProductListActivity.class);
        i.putExtra("catId", categoryTo.categoryId);
        i.putExtra("catName", categoryTo.categoryId);
        context.startActivity(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout categoryStripLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            categoryStripLayout = (LinearLayout) itemView.findViewById(R.id.category_strip);
        }
    }
}
