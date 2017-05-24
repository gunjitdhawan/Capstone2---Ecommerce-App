package in.grappes.ecommerce.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.Cart;
import in.grappes.ecommerce.model.Product;
import in.grappes.ecommerce.ui.activities.CartActivity;

/**
 * Created by gunjit on 20/05/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> implements Cart.CartUpdateListener {

    CartActivity context;
    HashMap<Product, Integer> cartItems;
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Integer> quantities = new ArrayList<>();
    public CartAdapter(CartActivity context)
    {
        this.context = context;
        cartItems = Cart.getInstance(this).cartItems;

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            products.add(entry.getKey());
            quantities.add(entry.getValue());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cart_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


            final Product product = products.get(position);
            Integer quantity = quantities.get(position);

            holder.dishName.setText("Product : "+ product.productName);
            holder.dishPrice.setText("Price : Rs."+product.sellingPrice);
            holder.quantity.setText(""+quantity);

            holder.quantityMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart.getInstance(CartAdapter.this).removeOneQuantityFromCart(product);
                }
            });

            holder.quantityPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart.getInstance(CartAdapter.this).addProductToCart(product);
                }
            });


    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    @Override
    public void OnCartUpdated() {
        Log.e("onCartUpdated", "adapter");
        cartItems = Cart.getInstance(this).cartItems;

        products.clear();
        quantities.clear();

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            products.add(entry.getKey());
            quantities.add(entry.getValue());
        }
        context.OnCartUpdated();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishName;
        TextView dishPrice;
        TextView quantityMinus;
        TextView quantityPlus;
        TextView quantity;
        public ViewHolder(View itemView) {
            super(itemView);
            dishName = (TextView) itemView.findViewById(R.id.dish_name);
            dishPrice = (TextView) itemView.findViewById(R.id.dish_price);
            quantityMinus = (TextView) itemView.findViewById(R.id.quantity_decrease);
            quantityPlus = (TextView) itemView.findViewById(R.id.quantity_increase);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
        }
    }
}
