package com.andro.itrip.ui.upcomingUI;

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
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.lifecycle.ViewModelProviders;

import com.andro.itrip.R;
import com.andro.itrip.Trip;

import java.util.List;

public class UpcomingFragment extends Fragment implements UpcomingContract.ViewInterface {

    TextView textView;
    UpcomingContract.PresenterInterface upcomingPresenter;
    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private LinearLayout empty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upcoming, container, false);
        upcomingPresenter = new UpcomingPresenter(this);
        upcomingPresenter.sendMessage();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        empty = view.findViewById(R.id.layout_empty);
        recyclerView = view.findViewById(R.id.mainRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

      //  adapter = new TripAdapter(,getContext());

    }

    @Override
    public void displayTrips(List<Trip> tripList) {

    }

    @Override
    public void displayNoTrips() {

    }

    public void displayMessage(String message) {
        textView.setText(message);
    }

}

