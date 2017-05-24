package in.grappes.ecommerce.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.Product;
import in.grappes.ecommerce.ui.activities.ProductDetailActivity;

/**
 * Created by gunjit on 27/04/17.
 */

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.ViewHolder> {

    ArrayList<Product> products;
    Context context;
    public ProductGridAdapter(Context context, ArrayList<Product> products)
    {
        this.context = context;
        this.products = products;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_list_item, parent, false);
        return new ProductGridAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product currentProduct = products.get(position);
        Log.e("ImageLink", currentProduct.productImageLink);
        Glide.with(context).load(currentProduct.productImageLink).into(holder.productImage);
        holder.productPrice.setText("Rs."+currentProduct.sellingPrice);
        holder.productName.setText(currentProduct.productName+"\n"+currentProduct.shortDescription);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProductDetailActivity(currentProduct);
            }
        });
    }

    private void startProductDetailActivity(Product product) {
        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra("product", product);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return products.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        ImageView productImage;
        View root;
        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
        }
    }
}
