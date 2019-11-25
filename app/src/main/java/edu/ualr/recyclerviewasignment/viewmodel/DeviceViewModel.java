package edu.ualr.recyclerviewasignment.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.ualr.recyclerviewasignment.model.DeviceListItem;

public class DeviceViewModel extends ViewModel {
    MutableLiveData<List<DeviceListItem>> devices;

    public DeviceViewModel()
    {
        devices = new MutableLiveData<List<DeviceListItem>>() {
            @Override
            public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<DeviceListItem>> observer) {
                super.observe(owner, observer);
            }
        };
    }

    public LiveData<List<DeviceListItem>> getDevices()
    {
        return devices;
    }

    public void setDevices(List<DeviceListItem> ndevices) {
        devices.setValue(ndevices);
    }

    public void setDeviceItem(DeviceListItem deviceItem, int index)
    {
        List<DeviceListItem> deviceItemList = devices.getValue();
        deviceItemList.set(index,deviceItem);
        devices.setValue(deviceItemList);
    }

    public void removeDevice(DeviceListItem deviceItem) {
        List<DeviceListItem> deviceItemList = devices.getValue();
        deviceItemList.remove(deviceItem);
        devices.setValue(deviceItemList);
    }

    public void addDevice(DeviceListItem deviceItem) {
        List<DeviceListItem> deviceItemList = devices.getValue();
        deviceItemList.add(deviceItem);
        devices.setValue(deviceItemList);
    }
}
