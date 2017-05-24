package in.grappes.ecommerce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.Cart;
import in.grappes.ecommerce.model.Product;
import in.grappes.ecommerce.ui.adapter.ProductDetailPagerAdapter;
import in.grappes.ecommerce.utils.AppUtils;
import in.grappes.ecommerce.utils.SharedPrefUtils;
import in.grappes.ecommerce.utils.ToolbarUtils;
import me.relex.circleindicator.CircleIndicator;

public class ProductDetailActivity extends AppCompatActivity implements Cart.CartUpdateListener{

    TextView productName;
    TextView productShortDesc;
    TextView productLongDesc;

    TextView productPrice;
    TextView productOriginalPrice;

    TextView addToCartBtn;
    TextView buyNowBtn;

    AutoScrollViewPager customViewPager;
    CircleIndicator indicator;

    ArrayList<String> pagerList;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        getIntentData();

        ToolbarUtils.setToolbar((ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)), ProductDetailActivity.this, "PRODUCT DETAIL");
        setViews();
        setupImageBanners();
        initViews();
    }



    private void getIntentData() {
        product = getIntent().getExtras().getParcelable("product");
        pagerList = product.imageLinkArray;
        pagerList.add(0, product.productImageLink);
    }

    private void initViews() {
        productName.setText(product.productName);
        productShortDesc.setText(product.shortDescription);
        productLongDesc.setText(product.productDescription);
        productOriginalPrice.setText(""+product.originalPrice);
        productPrice.setText(""+product.sellingPrice);

        if(product.sellingPrice==product.originalPrice)
        {
            productOriginalPrice.setVisibility(View.GONE);
        }
        else
        {
            productOriginalPrice.setVisibility(View.VISIBLE);
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductDetailActivity.this, getResources().getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show();
                Cart.getInstance(ProductDetailActivity.this).addProductToCart(product);
            }
        });

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart.getInstance(ProductDetailActivity.this).addProductToCart(product);
                proceedToNextActivity();
            }
        });
    }

    private void proceedToNextActivity() {
        if(SharedPrefUtils.getCurrentUser(ProductDetailActivity.this)!=null)
        {
            Intent i = new Intent(ProductDetailActivity.this, AddressActivity.class);
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(ProductDetailActivity.this, LoginActivity.class);
            i.putExtra("destination", "checkout");
            startActivity(i);
        }
    }

    private void setViews() {
        productName = (TextView) findViewById(R.id.product_name);
        productShortDesc = (TextView) findViewById(R.id.product_short_description);
        productLongDesc = (TextView) findViewById(R.id.product_long_description);
        productPrice = (TextView) findViewById(R.id.product_price);
        productOriginalPrice = (TextView) findViewById(R.id.product_original_price);
        addToCartBtn = (TextView) findViewById(R.id.add_to_cart_btn);
        buyNowBtn = (TextView) findViewById(R.id.buy_now_btn);
        customViewPager = (AutoScrollViewPager) findViewById(R.id.product_detail_pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
    }

    private void setupImageBanners() {
        ProductDetailPagerAdapter viewPagerAdapter = new ProductDetailPagerAdapter(ProductDetailActivity.this, pagerList);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(AppUtils.getDeviceDimensions(this).x, AppUtils.getDeviceDimensions(this).x/2);
        customViewPager.setLayoutParams(layoutParams);

        customViewPager.startAutoScroll();
        customViewPager.setInterval(5000);
        customViewPager.setCycle(true);
        customViewPager.setOffscreenPageLimit(2);

        customViewPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(customViewPager);

        customViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {

                    customViewPager.stopAutoScroll();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            customViewPager.startAutoScroll();
                        }
                    }, 5000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return false;
            }
        });

        if(pagerList==null || pagerList.size()<1)
        {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) customViewPager.getLayoutParams();
            params.height = 0;
        }

    }

    @Override
    public void OnCartUpdated() {

    }
}
