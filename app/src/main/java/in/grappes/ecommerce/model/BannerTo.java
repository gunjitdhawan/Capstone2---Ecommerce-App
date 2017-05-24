package in.grappes.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gunjit on 09/04/17.
 */

public class BannerTo implements Parcelable {
    public static final Creator<BannerTo> CREATOR = new Creator<BannerTo>() {
        @Override
        public BannerTo createFromParcel(Parcel in) {
            return new BannerTo(in);
        }

        @Override
        public BannerTo[] newArray(int size) {
            return new BannerTo[size];
        }
    };

    public String imageUrl;
    public String link;

    public BannerTo(Parcel in) {
        imageUrl = in.readString();
        link = in.readString();
    }

    public BannerTo() {

    }

    @Override
    public boolean equals(Object o) {
        BannerTo item = (BannerTo) o;
        if (link != null && item.link != null) {
            if (item.link.equalsIgnoreCase(link)) {
                return true;
            }
        }
        return super.equals(o);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}