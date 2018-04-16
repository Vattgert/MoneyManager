package com.example.productmanagment.places;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.productmanagment.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlacesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlacesFragment extends Fragment implements OnMapReadyCallback, PlacesContract.View{
    private GoogleMap mMap;
    private PlacesContract.Presenter presenter;
    private List<String> addresses;
    SupportMapFragment mapFragment;

    public PlacesFragment() {
        // Required empty public constructor
    }

    public static PlacesFragment newInstance() {
        PlacesFragment fragment = new PlacesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addresses = new ArrayList<>(0);
        getActivity().setTitle("Місця");
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        setHasOptionsMenu(true);
        mapFragment = (SupportMapFragment)this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        getMap();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.date_pick, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_date_range:
                Calendar calendar = Calendar.getInstance();
                SmoothDateRangePickerFragment.newInstance(callBack, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .show(getActivity().getFragmentManager(), "");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    SmoothDateRangePickerFragment.OnDateRangeSetListener callBack = (view, yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd) -> {
        String fdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(yearStart - 1900, monthStart, dayStart));
        String sdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(yearEnd - 1900, monthEnd, dayEnd));
        Log.wtf("MyLog", fdate + " " + sdate);
        presenter.loadAddressesByDate(fdate, sdate);
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMap();
    }

    @Override
    public void setPresenter(PlacesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showAddresses(List<String> addresses) {
        this.addresses = addresses;
        updateMap();
    }

    @Override
    public void getMap() {
        mapFragment.getMapAsync(this);
    }

    private void updateMap(){
        mMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLngBounds bounds;
        for(String address : addresses){
            String[] coordinates = address.split(";");
            Log.wtf("MyLog", coordinates[0] + " " + coordinates[1]);
            LatLng ltlg = new LatLng(Double.valueOf(coordinates[0]), Double.valueOf(coordinates[1]));
            Marker marker = mMap.addMarker(new MarkerOptions().position(ltlg));
            //builder.include(marker.getPosition());
        }

        //bounds = builder.build();
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        //mMap.moveCamera(cameraUpdate);
    }
}
