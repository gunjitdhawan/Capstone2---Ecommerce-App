package in.grappes.ecommerce.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.OrderTo;
import in.grappes.ecommerce.utils.AppUtils;

/**
 * Created by gunjit on 30/04/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    ArrayList<OrderTo> orderList;

    public  OrderAdapter(Context context, ArrayList<OrderTo> orderList)
    {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        OrderTo orderTo = orderList.get(position);
        holder.orderNumber.setText("Order #"+orderTo.orderPlacingTime);
        holder.orderStatus.setText(""+orderTo.orderStatus);
        holder.orderDate.setText(AppUtils.convertDate(orderTo.orderPlacingTime, "E, MMM d, yyyy"));
        holder.orderInfo.setText("Rs."+orderTo.finalTotal+" | "+orderTo.products.size()+" items");
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderStatus;
        TextView orderNumber;
        TextView orderDate;
        TextView orderInfo;
        public ViewHolder(View itemView) {
            super(itemView);

            orderStatus = (TextView) itemView.findViewById(R.id.order_status);
            orderNumber = (TextView) itemView.findViewById(R.id.order_number);
            orderDate = (TextView) itemView.findViewById(R.id.order_placing_date);
            orderInfo = (TextView) itemView.findViewById(R.id.order_mini_info);
        }
    }
}
