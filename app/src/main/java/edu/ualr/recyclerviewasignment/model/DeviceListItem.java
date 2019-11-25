package edu.ualr.recyclerviewasignment.model;

/**
 * Created by irconde on 2019-10-04.
 */
public abstract class DeviceListItem {
    protected boolean isSection = false;
    protected Device.DeviceStatus deviceStatus = Device.DeviceStatus.Linked;

    public Device.DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Device.DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public boolean isSection() {
        return this.isSection;
    }
}
