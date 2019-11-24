package edu.ualr.recyclerviewasignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ualr.recyclerviewasignment.adapter.DeviceListAdapter;
import edu.ualr.recyclerviewasignment.data.DataGenerator;
import edu.ualr.recyclerviewasignment.model.Device;
import edu.ualr.recyclerviewasignment.model.DeviceListItem;
import edu.ualr.recyclerviewasignment.viewmodel.DeviceViewModel;

public class DeviceListFragment extends Fragment implements OnItemSelectedListener{

    private DeviceViewModel viewModel;

    private RecyclerView mRecyclerView;
    private DeviceListAdapter mAdapter;
    private OnItemSelectedListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.device_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(getActivity()).get(DeviceViewModel.class);
        mAdapter = new DeviceListAdapter(getActivity(), viewModel);
        viewModel.getDevices().observe(this, new Observer<List<DeviceListItem>>() {
            @Override
            public void onChanged(List<DeviceListItem> devices) {
                mAdapter.updateList(devices);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

        initRecyclerView(view);
    }

    private void initRecyclerView(View view){
        mRecyclerView = view.findViewById(R.id.devices_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        List<DeviceListItem> data = DataGenerator.getDevicesDataset(5);
        viewModel.setDevices(data);
        mAdapter.addAll(data);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter.setListener(this);

    }

    public void setListener(OnItemSelectedListener nlistener)
    {
        listener = nlistener;
    }

    @Override
    public void OnItemSelected(Device device, int index) {
        listener.OnItemSelected(device,index);
    }
}

