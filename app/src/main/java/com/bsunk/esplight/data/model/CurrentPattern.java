
package com.bsunk.esplight.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentPattern implements Parcelable
{

    @SerializedName("index")
    @Expose
    private Integer index;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Parcelable.Creator<CurrentPattern> CREATOR = new Creator<CurrentPattern>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CurrentPattern createFromParcel(Parcel in) {
            CurrentPattern instance = new CurrentPattern();
            instance.index = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public CurrentPattern[] newArray(int size) {
            return (new CurrentPattern[size]);
        }

    }
    ;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(index);
        dest.writeValue(name);
    }

    public int describeContents() {
        return  0;
    }

}
