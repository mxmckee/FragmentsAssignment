package edu.ualr.recyclerviewasignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import edu.ualr.recyclerviewasignment.adapter.DeviceListAdapter;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;
import edu.ualr.recyclerviewasignment.model.DeviceSection;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceViewModel;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{

    private DeviceListAdapter mAdapter;
    public DeviceViewModel viewModel;
    DeviceListFragment deviceFragment;

    private static final String FRAGMENT_TAG = "BottomSheetDialog";
    private static final String TAG = MainActivity.class.getSimpleName();

    List<DeviceListItem> linkedDevices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this).get(DeviceViewModel.class);

        deviceFragment = new DeviceListFragment();
        deviceFragment.setListener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_placeholder, deviceFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect_action:
                setAllDeviceStatuses(Device.DeviceStatus.Ready, Device.DeviceStatus.Connected);
                return true;
            case R.id.disconnect_action:
                setAllDeviceStatuses(Device.DeviceStatus.Connected, Device.DeviceStatus.Ready);
                return true;
            case R.id.linked_action:
                if (item.isChecked()) {
                    item.setChecked(false);
                    hideLinked();
                }
                else {
                    item.setChecked(true);
                    showLinked();
                }
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnItemSelected(Device device, int index) {
        DeviceBottomSheetFragment dialog = new DeviceBottomSheetFragment();
        dialog.onItemSelected(device,index);
        dialog.show(getSupportFragmentManager(), FRAGMENT_TAG);
    }

    private void setAllDeviceStatuses(Device.DeviceStatus initialStatus, Device.DeviceStatus finalStatus) {
        viewModel.getDevices().observe(this, devices -> {
            for (int i = 0; i < devices.size(); i++) {
                if (!devices.get(i).isSection()) {
                    if (devices.get(i).getDeviceStatus().equals(initialStatus)) {
                        devices.get(i).setDeviceStatus(finalStatus);
                        viewModel.setDeviceItem(devices.get(i), i);
                    }
                }
            }
        });
        viewModel.getDevices().removeObservers(this);
    }

    private void hideLinked() {
        viewModel.getDevices().observe(this, devices -> {
            for (int i = 0; i < devices.size(); i++) {
                if (devices.get(i).isSection() && ((DeviceSection) devices.get(i)).getLabel().equals(Device.DeviceStatus.Linked.toString())) {
                    linkedDevices.add(devices.get(i));
                    viewModel.removeDevice(devices.get(i));
                }
                else if (!devices.get(i).isSection()) {
                    if (devices.get(i).getDeviceStatus().equals(Device.DeviceStatus.Linked)) {
                        linkedDevices.add(devices.get(i));
                        viewModel.removeDevice(devices.get(i));
                    }
                }
            }
        });
        viewModel.getDevices().removeObservers(this);
    }

    private void showLinked() {
        viewModel.getDevices().observe(this, devices -> {
            for (int i = 0; i < linkedDevices.size(); i++) {
                viewModel.addDevice(linkedDevices.get(i));
            }
            linkedDevices.clear();
        });
        viewModel.getDevices().removeObservers(this);
    }
}
