package in.grappes.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gunjit on 12/03/17.
 */

public class User implements Parcelable{
    public String email;
    public String firstName;
    public String mobileNumber;
    public String appVersion;
    public String androidVersion;
    public String deviceInfo;
    public String imei;
    public String password;

    public User(Parcel in) {
        email = in.readString();
        firstName = in.readString();
        mobileNumber = in.readString();
        appVersion = in.readString();
        androidVersion = in.readString();
        deviceInfo = in.readString();
        imei = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(firstName);
        parcel.writeString(mobileNumber);
        parcel.writeString(appVersion);
        parcel.writeString(androidVersion);
        parcel.writeString(deviceInfo);
        parcel.writeString(imei);
        parcel.writeString(password);
    }
}
