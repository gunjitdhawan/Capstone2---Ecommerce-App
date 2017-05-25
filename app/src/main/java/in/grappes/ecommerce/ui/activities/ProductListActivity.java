package in.grappes.ecommerce.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.Product;
import in.grappes.ecommerce.presenter.MenuPresenter;
import in.grappes.ecommerce.ui.adapter.ProductGridAdapter;
import in.grappes.ecommerce.utils.ToolbarUtils;

public class ProductListActivity extends AppCompatActivity {

    private GridLayoutManager glm;

    ArrayList<Product> products = new ArrayList<>();

    MenuPresenter menuPresenter;
    ProductGridAdapter rcAdapter;

    ProgressDialog loadingDialog;
    String categoryId, categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        getIntentData();
        ToolbarUtils.setToolbar((ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)), ProductListActivity.this, categoryName+getResources().getString(R.string.list));
        setViews();
        fetchProductList();

        setRecyclerView();
    }

    private void getIntentData() {
        categoryId = getIntent().getExtras().getString("catId");
        categoryName = getIntent().getExtras().getString("catName");
    }

    private void setViews() {
        loadingDialog = new ProgressDialog(ProductListActivity.this);
        loadingDialog.setMessage(getResources().getString(R.string.loading));
    }

    private void fetchProductList() {
        if(menuPresenter==null)
        {
            menuPresenter = new MenuPresenter(ProductListActivity.this, getProductsInterface);
        }
        loadingDialog.show();
        menuPresenter.fetchAllProductByCategory(categoryId);
    }

    private void setRecyclerView() {
        glm = new GridLayoutManager(ProductListActivity.this, 2);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(glm);

        rcAdapter = new ProductGridAdapter(ProductListActivity.this, products);
        rView.setAdapter(rcAdapter);
    }

    MenuPresenter.GetProductsInterface getProductsInterface = new MenuPresenter.GetProductsInterface() {
        @Override
        public void OnGetAllProductOfCategory(ArrayList<Product> products) {
            loadingDialog.dismiss();
            ProductListActivity.this.products.clear();
            ProductListActivity.this.products.addAll(products);
            rcAdapter.notifyDataSetChanged();

        }

        @Override
        public void OnGetProductDetailFailure(String errorMessage) {
            loadingDialog.dismiss();
            Toast.makeText(ProductListActivity.this, getResources().getString(R.string.couldnt_fetch), Toast.LENGTH_SHORT).show();
        }
    };
}
