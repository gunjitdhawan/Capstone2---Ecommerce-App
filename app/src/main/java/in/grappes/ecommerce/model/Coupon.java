package in.grappes.ecommerce.model;

/**
 * Created by gunjit on 30/03/17.
 */

public class Coupon {
    public enum PromoType {
        ABSOLUTE,
        PERCENTAGE
    }
    public boolean promoApplied;
    public String promoCode;
    public float promoValue;
    public PromoType promoType;
    public float minimumSubtotalToApplyCoupon;
    public float maximumDiscountApplicable;

}
