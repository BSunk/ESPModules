
package com.bsunk.esplight.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SolidColor implements Parcelable
{

    @SerializedName("r")
    @Expose
    private Integer r;
    @SerializedName("g")
    @Expose
    private Integer g;
    @SerializedName("b")
    @Expose
    private Integer b;
    public final static Parcelable.Creator<SolidColor> CREATOR = new Creator<SolidColor>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SolidColor createFromParcel(Parcel in) {
            SolidColor instance = new SolidColor();
            instance.r = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.g = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.b = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public SolidColor[] newArray(int size) {
            return (new SolidColor[size]);
        }

    }
    ;

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public Integer getG() {
        return g;
    }

    public void setG(Integer g) {
        this.g = g;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(r);
        dest.writeValue(g);
        dest.writeValue(b);
    }

    public int describeContents() {
        return  0;
    }

}
