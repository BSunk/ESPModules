package com.bsunk.esplight.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bharat on 12/13/2016.
 */
public class mDNSModule implements Parcelable{

    String ip;
    String type;
    int port;
    String name;

    public mDNSModule() {}

    public mDNSModule(String ip, String type, int port, String name) {
        this.ip=ip;
        this.type = type;
        this.port = port;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(ip);
        out.writeString(type);
        out.writeInt(port);
        out.writeString(name);
    }

    public static final Parcelable.Creator<mDNSModule> CREATOR
            = new Parcelable.Creator<mDNSModule>() {
        public mDNSModule createFromParcel(Parcel in) {
            return new mDNSModule(in);
        }

        public mDNSModule[] newArray(int size) {
            return new mDNSModule[size];
        }
    };

    private mDNSModule(Parcel in) {
        ip = in.readString();
        type = in.readString();
        port = in.readInt();
        name = in.readString();
    }

}
