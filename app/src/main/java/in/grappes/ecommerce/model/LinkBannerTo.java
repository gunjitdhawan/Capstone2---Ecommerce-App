package in.grappes.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by gunjit on 21/02/17.
 */

public class LinkBannerTo implements Parcelable{
    public int showIndex;
    public ArrayList<BannerTo> linkBannerTos;

    protected LinkBannerTo(Parcel in) {
        showIndex = in.readInt();
        linkBannerTos = in.createTypedArrayList(BannerTo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(showIndex);
        dest.writeTypedList(linkBannerTos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LinkBannerTo> CREATOR = new Creator<LinkBannerTo>() {
        @Override
        public LinkBannerTo createFromParcel(Parcel in) {
            return new LinkBannerTo(in);
        }

        @Override
        public LinkBannerTo[] newArray(int size) {
            return new LinkBannerTo[size];
        }
    };
}
