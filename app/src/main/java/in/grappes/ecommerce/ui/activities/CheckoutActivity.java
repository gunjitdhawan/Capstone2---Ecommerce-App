package in.grappes.ecommerce.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.AddressTo;
import in.grappes.ecommerce.model.Cart;
import in.grappes.ecommerce.model.Coupon;
import in.grappes.ecommerce.model.OrderItemTo;
import in.grappes.ecommerce.model.OrderTo;
import in.grappes.ecommerce.model.Product;
import in.grappes.ecommerce.presenter.OrderPresenter;
import in.grappes.ecommerce.utils.AppConstants;
import in.grappes.ecommerce.utils.SharedPrefUtils;

public class CheckoutActivity extends AppCompatActivity implements Cart.CartUpdateListener {

    LinearLayout orderItemLayout;
    View orderAddressLayout;
    TextView orderAddress;
    View orderAddressChangeBtn;
    TextView applyCouponCodeBtn;
    TextView couponAppliedText;
    TextView subtotalTextView;
    TextView discountTextView;
    TextView deliveryChargesTextView;
    TextView finalAmountTextView;
    CheckBox codBtn;
    CheckBox onlinePayBtn;
    View placeOrderBtn;
    AddressTo addressTo;
    AlertDialog couponDialog;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getIntentData();
        setViews();
        setOrderItemsView();
        setOrderAddressLayout();
        setOrderSummaryLayout();
        setPaymentOptionLayout();
        setPlaceOrderButton();
    }

    private void getIntentData() {
        addressTo = getIntent().getExtras().getParcelable("address");
    }

    private void setPlaceOrderButton() {
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Placing order...");
                progressDialog.show();
                placeOrder();
            }
        });
    }


    private void placeOrder() {

        OrderTo orderTo = new OrderTo();
        orderTo.products = getOrderItems(Cart.getInstance(CheckoutActivity.this).cartItems);
        orderTo.couponCode = Cart.getInstance(CheckoutActivity.this).coupon;
        orderTo.deliveryCharges = Cart.getInstance(CheckoutActivity.this).getDeliveryCharges();
        orderTo.discount = Cart.getInstance(CheckoutActivity.this).getDiscount();
        orderTo.finalTotal = Cart.getInstance(CheckoutActivity.this).getFinalAmount();
        orderTo.subTotal = Cart.getInstance(CheckoutActivity.this).getSubtotal();
        orderTo.emailId = SharedPrefUtils.getCurrentUser(CheckoutActivity.this).email;
        orderTo.orderPlacingTime = System.currentTimeMillis();
        orderTo.orderStatus = "DISPATCHED";

        if(codBtn.isChecked())
        {
            orderTo.paymentMethod = "CASH ON DELIVERY";
        }
        else
        {
            orderTo.paymentMethod = "ONLINE PAYMENT";
        }

        new OrderPresenter(CheckoutActivity.this, placeOrderInterface).placeOrder(orderTo);
    }

    private ArrayList<OrderItemTo> getOrderItems(HashMap<Product, Integer> cartItems) {
        ArrayList<OrderItemTo> orderItemTos = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            OrderItemTo orderItemTo = new OrderItemTo();
            orderItemTo.product = entry.getKey();
            orderItemTo.quantity = entry.getValue();
            orderItemTos.add(orderItemTo);
        }
        return orderItemTos;
    }

    private void setPaymentOptionLayout() {
        codBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codBtn.setChecked(true);
                onlinePayBtn.setChecked(false);
            }
        });

        onlinePayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlinePayBtn.setChecked(true);
                codBtn.setChecked(false);
            }
        });
    }

    private void setOrderSummaryLayout() {
        applyCouponCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CheckoutActivity.this);
                LayoutInflater inflater = CheckoutActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.coupon_code_view, null);
                dialogBuilder.setView(dialogView);

                final EditText editText = (EditText) dialogView.findViewById(R.id.coupon_code_et);
                View applyBtn = dialogView.findViewById(R.id.apply_btn);
                View cancelBtn = dialogView.findViewById(R.id.cancel_btn);
                applyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkIfCouponCodeCorrect(editText.getText().toString());
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckoutActivity.super.onBackPressed();
                    }
                });
                couponDialog = dialogBuilder.create();
                couponDialog.show();

            }
        });

        discountTextView.setText(""+Cart.getInstance(CheckoutActivity.this).getDiscount());
        subtotalTextView.setText(""+Cart.getInstance(CheckoutActivity.this).getSubtotal());
        deliveryChargesTextView.setText(""+Cart.getInstance(CheckoutActivity.this).getDeliveryCharges());
        finalAmountTextView.setText(""+Cart.getInstance(CheckoutActivity.this).getFinalAmount());
    }

    private void checkIfCouponCodeCorrect(String couponCode) {
        progressDialog.setMessage("Applying coupon...");
        progressDialog.show();
        new OrderPresenter(CheckoutActivity.this, applyCouponInterface).applyCoupon(couponCode);
    }

    private void setOrderAddressLayout() {
        orderAddress.setText(addressTo.getAddressItemText());
        orderAddressChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CheckoutActivity.this, AddressActivity.class);
                startActivityForResult(i, AppConstants.CHANGE_ADDRESS);
            }
        });
    }

    private void setOrderItemsView() {
        orderItemLayout.removeAllViews();
        for (final Map.Entry<Product, Integer> entry : Cart.getInstance(CheckoutActivity.this).cartItems.entrySet())
        {
            View view = getLayoutInflater().inflate(R.layout.checkout_order_item, null);
            ((TextView)view.findViewById(R.id.product_name)).setText(entry.getKey().productName);
            ((TextView)view.findViewById(R.id.product_price)).setText(""+entry.getKey().sellingPrice);
            ImageView productImage = ((ImageView)view.findViewById(R.id.product_image));
            final TextView changeQtyBtn = (TextView) view.findViewById(R.id.change_quantity_btn);
            changeQtyBtn.setText("CHANGE QUANTITY("+entry.getValue()+")");
            view.findViewById(R.id.remove_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cart.getInstance(CheckoutActivity.this).removeProductFromCart(entry.getKey());
                    setOrderItemsView();
                    setOrderSummaryLayout();
                }
            });

            changeQtyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showQuantityChangeDialog(entry.getKey(), changeQtyBtn);
                }
            });
            orderItemLayout.addView(view);
        }
    }

    private void showQuantityChangeDialog(final Product product, final TextView changeQtyBtn) {
        List<String> quantityList = new ArrayList<String>();
        for(int i = 1; i<=10 ;i++)
        {
            quantityList.add(""+i);
        }
        //Create sequence of items
        final CharSequence[] quantityArray = quantityList.toArray(new String[quantityList.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Change Quantity");
        dialogBuilder.setItems(quantityArray, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = quantityArray[item].toString();
                changeQtyBtn.setText("CHANGE QUANTITY("+selectedText+")");
                Cart.getInstance(CheckoutActivity.this).updateQuantityOfProductInCart(product, Integer.parseInt(selectedText));
                setOrderSummaryLayout();
            }
        });

        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();

    }

    private void setViews() {
        progressDialog = new ProgressDialog(CheckoutActivity.this);
        progressDialog.setMessage("Placing order...");
        orderItemLayout = (LinearLayout) findViewById(R.id.order_summary_layout);
        orderAddressLayout = findViewById(R.id.order_address_layout);
        orderAddress = (TextView) findViewById(R.id.order_address);
        orderAddressChangeBtn = findViewById(R.id.order_address_change_btn);
        applyCouponCodeBtn = (TextView) findViewById(R.id.apply_coupon_code_btn);
        couponAppliedText = (TextView) findViewById(R.id.coupon_applied_text);
        subtotalTextView = (TextView) findViewById(R.id.order_subtotal);
        discountTextView = (TextView) findViewById(R.id.order_discount);
        deliveryChargesTextView = (TextView) findViewById(R.id.order_delivery_charges);
        finalAmountTextView = (TextView) findViewById(R.id.order_total);
        codBtn = (CheckBox) findViewById(R.id.cod_btn);
        onlinePayBtn = (CheckBox) findViewById(R.id.online_payment_btn);
        placeOrderBtn = findViewById(R.id.place_order_btn);
    }

    @Override
    public void OnCartUpdated() {
        setOrderItemsView();
        setOrderSummaryLayout();
    }

    OrderPresenter.ApplyCouponInterface applyCouponInterface = new OrderPresenter.ApplyCouponInterface() {
        @Override
        public void onCouponAppliedSuccess(Coupon coupon) {
            progressDialog.dismiss();
            Toast.makeText(CheckoutActivity.this, "Coupon Applied!", Toast.LENGTH_SHORT).show();
            Cart.getInstance(CheckoutActivity.this).applyPromoCode(coupon);
            couponAppliedText.setVisibility(View.VISIBLE);
            applyCouponCodeBtn.setVisibility(View.GONE);

            if(couponDialog!=null) {
                couponDialog.dismiss();
            }
        }

        @Override
        public void onCouponAppliedFailure(String errorMsg) {
            progressDialog.dismiss();
            if(couponDialog!=null) {
                couponDialog.dismiss();
            }
            Toast.makeText(CheckoutActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    OrderPresenter.PlaceOrderInterface placeOrderInterface = new OrderPresenter.PlaceOrderInterface() {
        @Override
        public void OnOrderPlacedSuccess(OrderTo order) {
            Cart.getInstance(CheckoutActivity.this).clearCart();
            Intent i = new Intent(CheckoutActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("openMyOrders", true);
            startActivity(i);
            finish();
        }

        @Override
        public void OnOrderPlacedFailed(String errorMsg) {
            progressDialog.dismiss();
        }
    };


}
