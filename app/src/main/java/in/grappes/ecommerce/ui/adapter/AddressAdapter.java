package in.grappes.ecommerce.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.AddressTo;
import in.grappes.ecommerce.ui.activities.AddressActivity;

/**
 * Created by gunjit on 30/04/17.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    AddressActivity context;
    ArrayList<AddressTo> addressItemArrayList = new ArrayList<>();

    public AddressAdapter(AddressActivity context, ArrayList<AddressTo> addressItemArrayList)
    {
        this.context = context;
        this.addressItemArrayList = addressItemArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.address_item, parent, false);
        return new AddressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.addressText.setText(addressItemArrayList.get(position).getAddressItemText());
        holder.addressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.moveToNextActivity(addressItemArrayList.get(position));
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView addressText;

        public ViewHolder(View itemView) {
            super(itemView);
            addressText = (TextView)itemView.findViewById(R.id.addressItemText);
        }
    }


    @Override
    public int getItemCount() {
        return addressItemArrayList.size();
    }
}

