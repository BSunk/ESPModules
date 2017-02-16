package com.bsunk.esplight.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Bharat on 12/28/2016.
 */

public class LightModel extends RealmObject {

    private String name;
    private String ip;
    private int port;
    private String mqttIP;
    private String mqttPort;
    private boolean power;
    private boolean connectionCheck;
    private int pattern;
    private int brightness;
    private int mqttStatus;
    private String solidColor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMqttIP() {
        return mqttIP;
    }

    public void setMqttIP(String mqttIP) {
        this.mqttIP = mqttIP;
    }

    public String getMqttPort() {
        return mqttPort;
    }

    public void setMqttPort(String mqttPort) {
        this.mqttPort = mqttPort;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public boolean isConnectionCheck() {
        return connectionCheck;
    }

    public void setConnectionCheck(boolean connectionCheck) {
        this.connectionCheck = connectionCheck;
    }

    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getMqttStatus() {
        return mqttStatus;
    }

    public void setMqttStatus(int mqttStatus) {
        this.mqttStatus = mqttStatus;
    }

    public String getSolidColor() {
        return solidColor;
    }

    public void setSolidColor(String solidColor) {
        this.solidColor = solidColor;
    }

}
