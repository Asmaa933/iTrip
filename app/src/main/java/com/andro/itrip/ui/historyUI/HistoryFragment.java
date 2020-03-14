package com.andro.itrip.ui.historyUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andro.itrip.R;
import com.andro.itrip.Trip;
import com.andro.itrip.ui.upcomingUI.TripAdapter;

import java.util.List;

public class HistoryFragment extends Fragment implements HistoryContract.ViewInterface {

    TextView textView;
    HistoryContract.PresenterInterface historyPresenter;
    private RecyclerView recyclerView;
    private HistoryTripAdapter adapter;
    private LinearLayout emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        historyPresenter = new HistoryPresenter(this);
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
        adapter = new HistoryTripAdapter(tripList,historyPresenter,getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayNoTrips() {
        recyclerView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
    }

    public void displayMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
