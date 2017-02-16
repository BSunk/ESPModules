
package com.bsunk.esplight.data.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllResponse implements Parcelable
{

    @SerializedName("power")
    @Expose
    private Integer power;
    @SerializedName("brightness")
    @Expose
    private Integer brightness;
    @SerializedName("currentPattern")
    @Expose
    private CurrentPattern currentPattern;
    @SerializedName("solidColor")
    @Expose
    private SolidColor solidColor;
    @SerializedName("patterns")
    @Expose
    private List<String> patterns = null;
    public final static Parcelable.Creator<AllResponse> CREATOR = new Creator<AllResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AllResponse createFromParcel(Parcel in) {
            AllResponse instance = new AllResponse();
            instance.power = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.brightness = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.currentPattern = ((CurrentPattern) in.readValue((CurrentPattern.class.getClassLoader())));
            instance.solidColor = ((SolidColor) in.readValue((SolidColor.class.getClassLoader())));
            in.readList(instance.patterns, (java.lang.String.class.getClassLoader()));
            return instance;
        }

        public AllResponse[] newArray(int size) {
            return (new AllResponse[size]);
        }

    }
    ;

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public CurrentPattern getCurrentPattern() {
        return currentPattern;
    }

    public void setCurrentPattern(CurrentPattern currentPattern) {
        this.currentPattern = currentPattern;
    }

    public SolidColor getSolidColor() {
        return solidColor;
    }

    public void setSolidColor(SolidColor solidColor) {
        this.solidColor = solidColor;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(power);
        dest.writeValue(brightness);
        dest.writeValue(currentPattern);
        dest.writeValue(solidColor);
        dest.writeList(patterns);
    }

    public int describeContents() {
        return  0;
    }

}
