package in.grappes.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by gunjit on 30/03/17.
 */

public class Product implements Parcelable{

    public String productName;
    public String productDescription;
    public String shortDescription;
    public String category;
    public String categoryCode;
    public String productImageLink;
    public ArrayList<String> imageLinkArray = new ArrayList<>();
    public float originalPrice;
    public float sellingPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!productName.equals(product.productName)) return false;
        return categoryCode.equals(product.categoryCode);
    }

    public Product(Parcel in) {
        productName = in.readString();
        productDescription = in.readString();
        shortDescription = in.readString();
        category = in.readString();
        categoryCode = in.readString();
        productImageLink = in.readString();
        imageLinkArray = in.createStringArrayList();
        originalPrice = in.readFloat();
        sellingPrice = in.readFloat();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public Product() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
        parcel.writeString(productDescription);
        parcel.writeString(shortDescription);
        parcel.writeString(category);
        parcel.writeString(categoryCode);
        parcel.writeString(productImageLink);
        parcel.writeStringList(imageLinkArray);
        parcel.writeFloat(originalPrice);
        parcel.writeFloat(sellingPrice);
    }
}
