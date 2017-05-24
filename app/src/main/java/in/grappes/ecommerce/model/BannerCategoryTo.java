package in.grappes.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gunjit on 09/04/17.
 */

public class BannerCategoryTo implements Parcelable {
    @SerializedName("deepLinkBannerTo")
    public LinkBannerTo deepLinkBannerTo;
    @SerializedName("webLinkBannerTo")
    public LinkBannerTo webLinkBannerTo;
    @SerializedName("showOnlyBannerTo")
    public LinkBannerTo showOnlyBannerTo;

    @SerializedName("totalCount")
    public int totalBannerCount;
    @SerializedName("packageCount")
    public int packageBannerCount;
    @SerializedName("deepLinkCount")
    public int deepLinkBannerCount;
    @SerializedName("webLinkCount")
    public int webLinkBannerCount;
    @SerializedName("showOnlyCount")
    public int showOnlyBannerCount;

    protected BannerCategoryTo(Parcel in) {
        deepLinkBannerTo = in.readParcelable(LinkBannerTo.class.getClassLoader());
        webLinkBannerTo = in.readParcelable(LinkBannerTo.class.getClassLoader());
        showOnlyBannerTo = in.readParcelable(LinkBannerTo.class.getClassLoader());
        totalBannerCount = in.readInt();
        packageBannerCount = in.readInt();
        deepLinkBannerCount = in.readInt();
        webLinkBannerCount = in.readInt();
        showOnlyBannerCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(deepLinkBannerTo, flags);
        dest.writeParcelable(webLinkBannerTo, flags);
        dest.writeParcelable(showOnlyBannerTo, flags);
        dest.writeInt(totalBannerCount);
        dest.writeInt(packageBannerCount);
        dest.writeInt(deepLinkBannerCount);
        dest.writeInt(webLinkBannerCount);
        dest.writeInt(showOnlyBannerCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BannerCategoryTo> CREATOR = new Creator<BannerCategoryTo>() {
        @Override
        public BannerCategoryTo createFromParcel(Parcel in) {
            return new BannerCategoryTo(in);
        }

        @Override
        public BannerCategoryTo[] newArray(int size) {
            return new BannerCategoryTo[size];
        }
    };
}