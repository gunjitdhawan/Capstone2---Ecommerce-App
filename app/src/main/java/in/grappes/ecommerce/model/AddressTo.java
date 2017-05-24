package in.grappes.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gunjit on 30/04/17.
 */

public class AddressTo implements Parcelable {

    public String houseNumber;
    public String locality;
    public String deliveryInstructions;
    public String customerName;
    public String customerPhone;
    public String linkedEmail;

    public AddressTo(Parcel in) {
        houseNumber = in.readString();
        locality = in.readString();
        deliveryInstructions = in.readString();
        customerName = in.readString();
        customerPhone = in.readString();
        linkedEmail = in.readString();
    }

    public AddressTo() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(houseNumber);
        dest.writeString(locality);
        dest.writeString(deliveryInstructions);
        dest.writeString(customerName);
        dest.writeString(customerPhone);
        dest.writeString(linkedEmail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressTo> CREATOR = new Creator<AddressTo>() {
        @Override
        public AddressTo createFromParcel(Parcel in) {
            return new AddressTo(in);
        }

        @Override
        public AddressTo[] newArray(int size) {
            return new AddressTo[size];
        }
    };

    public String getAddressItemText() {
        return houseNumber+"\n"+
                locality+"\n"+
                deliveryInstructions+"\n"+
                customerName+"\n"+
                customerPhone;
    }
}