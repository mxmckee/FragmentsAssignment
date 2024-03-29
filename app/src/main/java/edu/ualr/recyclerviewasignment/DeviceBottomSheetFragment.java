package edu.ualr.recyclerviewasignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import edu.ualr.recyclerviewasignment.data.DeviceDataFormatTools;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceViewModel;

public class DeviceBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener{
    private DeviceViewModel viewModel;

    private EditText deviceName;
    private TextView deviceStatus;
    private Spinner deviceType;
    private TextView timeConnection;
    private Button button;
    private Device device;
    private int index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(DeviceViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.device_detail_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RelativeLayout image = view.findViewById(R.id.detail_thumbnail_image);
        ImageView statusMark = view.findViewById(R.id.detail_status_mark);

        deviceName = view.findViewById(R.id.detail_device_name_edittext);
        deviceStatus = view.findViewById(R.id.detail_device_status);
        deviceType = view.findViewById(R.id.device_type_spinner);
        timeConnection = view.findViewById(R.id.last_time_connection_textview);
        button = view.findViewById(R.id.save_btn);

        List<DeviceListItem> devices = viewModel.getDevices().getValue();
        int pos = devices.indexOf(device);
        Device tDevice = (Device) devices.get(pos);
        if(tDevice.equals(device))
        {
            device = tDevice;
            index = pos;
        }

        DeviceDataFormatTools.setDeviceThumbnail(getContext(), image, device);
        DeviceDataFormatTools.setDeviceStatusMark(getContext(), statusMark, device);

        deviceName.setHint(device.getName().toUpperCase());
        deviceStatus.setText(device.getDeviceStatus().toString());
        ArrayAdapter<Device.DeviceType> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Device.DeviceType.values());
        deviceType.setAdapter(adapter);
        deviceType.setSelection(adapter.getPosition(device.getDeviceType()));
        timeConnection.setText(DeviceDataFormatTools.getTimeSinceLastConnection(getContext(),device));
        button.setOnClickListener(this);

        deviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Device newDevice = device;
                switch (position) {
                    case 0:
                        newDevice.setDeviceType(Device.DeviceType.Unknown);
                        break;
                    case 1:
                        newDevice.setDeviceType(Device.DeviceType.Desktop);
                        break;
                    case 2:
                        newDevice.setDeviceType(Device.DeviceType.Laptop);
                        break;
                    case 3:
                        newDevice.setDeviceType(Device.DeviceType.Tablet);
                        break;
                    case 4:
                        newDevice.setDeviceType(Device.DeviceType.Smartphone);
                        break;
                    case 5:
                        newDevice.setDeviceType(Device.DeviceType.SmartTV);
                        break;
                    case 6:
                        newDevice.setDeviceType(Device.DeviceType.GameConsole);
                        break;
                }
                DeviceDataFormatTools.setDeviceThumbnail(getContext(), image, newDevice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.save_btn)
        {
            save();
        }
        this.dismiss();
    }

    private void save()
    {
        if (!deviceName.getText().toString().isEmpty()) {
            device.setName(deviceName.getText().toString());
        }
        device.setDeviceType((Device.DeviceType) deviceType.getSelectedItem());
        viewModel.setDeviceItem(device,index);
    }

    public void onItemSelected(Device newDevice, int newIndex)
    {
        this.device = newDevice;
        this.index = newIndex;
    }
}
