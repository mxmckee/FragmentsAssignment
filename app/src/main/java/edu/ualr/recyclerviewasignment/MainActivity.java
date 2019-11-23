package edu.ualr.recyclerviewasignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceViewModel;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{

    public DeviceViewModel sharedViewModel;
    DeviceListFragment deviceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedViewModel = new DeviceViewModel();

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
                return true;
            case R.id.disconnect_action:
                return true;
            case R.id.linked_action:
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnItemSelected(Device device, int index) {
        DeviceBottomSheetFragment dialog = new DeviceBottomSheetFragment();
        dialog.onItemSelected(device,index);
        dialog.show(getSupportFragmentManager(), "BottomSheetDialog");
    }
}
