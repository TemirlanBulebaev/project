package com.example.project.entities;

public class DeviceInfo {
    //@NotBlank(message = "Device id cannot be blank")
    //@ApiModelProperty(value = "Id устройства", required = true, dataType = "string", allowableValues = "Non empty string")
    private String deviceId;

    //@NotNull
    //@ApiModelProperty(value = "Тип устройства (Android/iOS)", required = true, dataType = "string",
            //allowableValues = "DEVICE_TYPE_ANDROID, DEVICE_TYPE_IOS")
    private DeviceType deviceType;

    //@NotBlank(message = "Device notification Token cannot be blank")
    //@ApiModelProperty(value = "Device notification id", dataType = "string", allowableValues = "Non empty string")
    private String notificationToken;

    public DeviceInfo() {}

    public DeviceInfo(String deviceId, DeviceType deviceType, String notificationToken) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.notificationToken = notificationToken;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getNotificationToken() {
        return this.notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    @Override
    public String toString() {
        return "{" +
                " deviceId='" + getDeviceId() + "'" +
                ", deviceType='" + getDeviceType() + "'" +
                ", notificationToken='" + getNotificationToken() + "'" +
                "}";
    }

}
