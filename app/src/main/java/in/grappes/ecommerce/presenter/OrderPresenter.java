package in.grappes.ecommerce.presenter;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import in.grappes.ecommerce.R;
import in.grappes.ecommerce.model.Coupon;
import in.grappes.ecommerce.model.OrderTo;

/**
 * Created by gunjit on 06/05/17.
 */

public class OrderPresenter {

    Context context;
    FetchAllOrdersInterface fetchAllOrdersInterface;
    PlaceOrderInterface placeOrderInterface;
    ApplyCouponInterface applyCouponInterface;

    public OrderPresenter(Context context, FetchAllOrdersInterface fetchAllOrdersInterface)
    {
        this.context = context;
        this.fetchAllOrdersInterface = fetchAllOrdersInterface;
    }

    public interface FetchAllOrdersInterface{
        void onFetchOrdersSuccess(ArrayList<OrderTo> orderTos);
        void onFetchOrderFailed(String errorMsg);
    }

    public void fetchAllUserOrders(String emailId)
    {
        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Query mQueryRef = mFirebaseRef.child("orders").orderByChild("emailId").equalTo(emailId);

        mQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("onDataChange",""+new Gson().toJson(dataSnapshot.getChildren()));
                ArrayList<OrderTo> orderTos = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    OrderTo orderTo = productSnapshot.getValue(OrderTo.class);
                    orderTos.add(orderTo);
                }
                fetchAllOrdersInterface.onFetchOrdersSuccess(orderTos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError!=null)
                {
                    fetchAllOrdersInterface.onFetchOrderFailed(databaseError.getMessage());
                    return;
                }
                fetchAllOrdersInterface.onFetchOrderFailed(context.getResources().getString(R.string.somethingwentwrong));
            }
        });
    }


    ///////////////////////////////////////////////////////////////////////////////


    public OrderPresenter(Context context, PlaceOrderInterface placeOrderInterface)
    {
        this.context = context;
        this.placeOrderInterface = placeOrderInterface;
    }

    public interface PlaceOrderInterface{
        void OnOrderPlacedSuccess(OrderTo order);
        void OnOrderPlacedFailed(String errorMsg);
    }

    public void placeOrder(final OrderTo order)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("orders");
        String userId = mDatabase.push().getKey();
        mDatabase.child(userId).setValue(order, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError!=null)
                {
                    placeOrderInterface.OnOrderPlacedFailed(databaseError.getMessage());
                    return;
                }
                placeOrderInterface.OnOrderPlacedSuccess(order);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////


    public OrderPresenter(Context context, ApplyCouponInterface applyCouponInterface)
    {
        this.context = context;
        this.applyCouponInterface = applyCouponInterface;
    }

    public interface ApplyCouponInterface{
        void onCouponAppliedSuccess(Coupon coupon);
        void onCouponAppliedFailure(String errorMsg);
    }

    public void applyCoupon(String couponCode)
    {
        DatabaseReference mFirebaseRef = FirebaseDatabase.getInstance().getReference();
        Query mQueryRef = mFirebaseRef.child("coupons").orderByChild("promoCode").equalTo(couponCode);

        mQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot couponSnapShot : dataSnapshot.getChildren()) {
                    Coupon coupon = couponSnapShot.getValue(Coupon.class);
                    if (coupon != null) {
                        applyCouponInterface.onCouponAppliedSuccess(coupon);
                    } else {
                        applyCouponInterface.onCouponAppliedFailure(context.getResources().getString(R.string.invalid_coupon));
                    }
                    break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                applyCouponInterface.onCouponAppliedFailure(""+databaseError.getMessage());
            }
        });
    }
}
