package in.grappes.ecommerce.model;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gunjit on 30/03/17.
 */

public class Cart {

    public static float MINIMUM_ORDER_AMOUNT = 100;
    public static float MINIMUM_ORDER_AMOUNT_TO_AVOID_DELIVERY_CHARGES = 500;
    public static float DELIVERY_CHARGE = 40;

    public Coupon coupon;

    static CartUpdateListener updateListener;

    private Cart(){}

    public void clearCart() {
        cartItems.clear();
        updateListener.OnCartUpdated();
    }

    private static class SingletonHelper{
        private static final Cart INSTANCE = new Cart();
    }

    public static Cart getInstance(CartUpdateListener cartUpdateListener){
        updateListener = cartUpdateListener;
        return SingletonHelper.INSTANCE;
    }

    public HashMap<Product, Integer> cartItems = new HashMap<>();

    public void addProductToCart(Product product)
    {
        if(cartItems.containsKey(product))
        {
            cartItems.put(product, cartItems.get(product) + 1);
        }
        else
        {
            cartItems.put(product, 1);
        }
        updateListener.OnCartUpdated();
    }

    public void removeOneQuantityFromCart(Product product)
    {
        if(cartItems.containsKey(product) && cartItems.get(product)>1)
        {
            cartItems.put(product, cartItems.get(product) - 1);
        }
        else {
            removeProductFromCart(product);
        }
        updateListener.OnCartUpdated();
    }

    public void removeProductFromCart(Product product)
    {
        cartItems.remove(product);
        updateListener.OnCartUpdated();
    }

    public void updateQuantityOfProductInCart(Product product, int newQuantity)
    {
        cartItems.put(product, newQuantity);
        updateListener.OnCartUpdated();
    }

    public float getSubtotal()
    {
        float subtotal = 0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            subtotal+=product.sellingPrice*quantity;
        }
        return subtotal;
    }

    public float getFinalAmount()
    {
        return getSubtotal() - getDiscount() + getDeliveryCharges();
    }

    public float getDiscount()
    {
        if(coupon!=null) {
            if (coupon.promoType == Coupon.PromoType.ABSOLUTE) {
                if (coupon.minimumSubtotalToApplyCoupon <= getSubtotal()) {
                    return coupon.promoValue;
                }
                else
                {
                    Log.e("CouponA", "minSubtotalNotReached");
                }
            } else if (coupon.promoType == Coupon.PromoType.PERCENTAGE) {
                if (coupon.minimumSubtotalToApplyCoupon <= getSubtotal()) {
                    return coupon.promoValue * getSubtotal() * 0.01f;
                }
                else
                {
                    Log.e("CouponP", "minSubtotalNotReached");
                }
            }
            else
            {
                Log.e("Coupon", "Type Not Matched");
            }
        }
        else
        {
            Log.e("Coupon", "Coupon in Null");
        }
        return 0;
    }

    public float getDeliveryCharges()
    {
        if(MINIMUM_ORDER_AMOUNT_TO_AVOID_DELIVERY_CHARGES <= getSubtotal())
        {
            return 0;
        }
        else
        {
            return DELIVERY_CHARGE;
        }
    }

    public void applyPromoCode(Coupon coupon)
    {
        this.coupon = coupon;
        updateListener.OnCartUpdated();
    }

    public interface CartUpdateListener
    {
        void OnCartUpdated();
    }

}
