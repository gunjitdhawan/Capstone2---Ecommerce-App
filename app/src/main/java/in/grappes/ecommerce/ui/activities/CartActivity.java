package in.grappes.ecommerce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.Cart;
import in.grappes.ecommerce.ui.adapter.CartAdapter;
import in.grappes.ecommerce.utils.SharedPrefUtils;
import in.grappes.ecommerce.utils.ToolbarUtils;

public class CartActivity extends AppCompatActivity implements Cart.CartUpdateListener {

    CartAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager llm;

    TextView subtotalTv;
    View proceedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ToolbarUtils.setToolbar((ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)), CartActivity.this, getResources().getString(R.string.my_cart));

        setViews();
        initViews();
        setAdapter();
        setUpRecyclerView();

    }

    private void initViews() {
        subtotalTv.setText(getResources().getString(R.string.subtotal_text)+""+Cart.getInstance(this).getSubtotal());
    }

    private void setViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        subtotalTv = (TextView) findViewById(R.id.subtotal_view);
        proceedBtn = findViewById(R.id.proceed_btn);
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Cart.getInstance(CartActivity.this).getSubtotal()<1)
                {
                    Toast.makeText(CartActivity.this, getResources().getString(R.string.add_something), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    proceedToNextActivity();
                }
            }
        });
    }

    private void proceedToNextActivity() {
        if(SharedPrefUtils.getCurrentUser(CartActivity.this)!=null)
        {
            Intent i = new Intent(CartActivity.this, AddressActivity.class);
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(CartActivity.this, LoginActivity.class);
            i.putExtra("destination", "checkout");
            startActivity(i);
        }
    }

    private void setAdapter() {
        adapter = new CartAdapter(CartActivity.this);
    }

    private void setUpRecyclerView() {

        llm = new LinearLayoutManager(CartActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnCartUpdated() {
        Log.e("OnCartUpdated", "called");
        initViews();
    }
}
