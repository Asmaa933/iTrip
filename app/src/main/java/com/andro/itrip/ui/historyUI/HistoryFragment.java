package com.andro.itrip.ui.historyUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andro.itrip.GlobalApplication;
import com.andro.itrip.HistoryMap;
import com.andro.itrip.R;
import com.andro.itrip.Trip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements HistoryContract.ViewInterface {

    TextView textView;
    HistoryContract.PresenterInterface historyPresenter;
    private RecyclerView recyclerView;
    private HistoryTripAdapter adapter;
    private LinearLayout emptyView;
    private List<Trip> trips;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        historyPresenter = new HistoryPresenter(this);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyView = view.findViewById(R.id.layout_empty_history);
        recyclerView = view.findViewById(R.id.historyRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        trips = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        historyPresenter.getTripList();
    }

    @Override
    public void displayTrips(List<Trip> tripList) {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        adapter = new HistoryTripAdapter(tripList, historyPresenter, getContext());
        recyclerView.setAdapter(adapter);
        trips = tripList;
    }

    @Override
    public void displayNoTrips() {
        recyclerView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
    }

    public void displayMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mapButton) {

            if (trips != null && !trips.isEmpty()) {
                ArrayList<String> startPoints = new ArrayList<>();
                ArrayList<String> destintationPoints = new ArrayList<>();
                for (int i = 0; i < trips.size(); i++) {
                    startPoints.add(trips.get(i).getStartAddress());
                    destintationPoints.add(trips.get(i).getDestAddress());
                }

                Intent intent = new Intent(GlobalApplication.getAppContext(), HistoryMap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putStringArrayListExtra(getString(R.string.start_points),startPoints);
                intent.putStringArrayListExtra(getString(R.string.end_points),destintationPoints);


                getContext().startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
