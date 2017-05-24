package in.grappes.ecommerce.model;

import java.util.ArrayList;

/**
 * Created by gunjit on 30/04/17.
 */

public class OrderTo {
    public ArrayList<OrderItemTo> products = new ArrayList<>();
    public float subTotal;
    public float discount;
    public float deliveryCharges;
    public float finalTotal;
    public Coupon couponCode;
    public String paymentMethod;
    public String emailId;
    public long orderPlacingTime;
    public String orderStatus;
}
