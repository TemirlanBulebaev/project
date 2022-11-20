package com.example.project.payload;

import com.example.project.entities.DeviceInfo;

public class LoginRequest {

    //@NotBlank(message = "Login Email cannot be blank")
    //@ApiModelProperty(value = "Email", required = true, allowableValues = "NonEmpty String")
    private String email;

    //@NotNull(message = "Login password cannot be blank")
    //@ApiModelProperty(value = "Пароль", required = true, allowableValues = "NonEmpty String")
    private String password;

   //@Valid
   // @NotNull(message = "Device info cannot be null")
    //@ApiModelProperty(value = "Информация об устройстве", required = true, dataType = "object", allowableValues = "A valid " +
           // "deviceInfo object")
    private DeviceInfo deviceInfo;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password, DeviceInfo deviceInfo) {
        this.email = email;
        this.password = password;
        this.deviceInfo = deviceInfo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String toString() {
        return "{" +
                ", email='" + getEmail() + "'" +
                ", password='" + getPassword() + "'" +
                ", deviceInfo='" + getDeviceInfo() + "'" +
                "}";
    }


}
