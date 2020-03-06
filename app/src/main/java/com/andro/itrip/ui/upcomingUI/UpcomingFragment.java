package com.andro.itrip.ui.upcomingUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.lifecycle.ViewModelProviders;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.R;
import com.andro.itrip.Trip;

import java.util.List;

public class UpcomingFragment extends Fragment implements UpcomingContract.ViewInterface {

    UpcomingContract.PresenterInterface upcomingPresenter;
    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private LinearLayout emptyView;
    String userId = "Cx4XpQrM06S9doN2mLH0cUustHj2";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upcoming, container, false);
        upcomingPresenter = new UpcomingPresenter(this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyView = view.findViewById(R.id.layout_empty);
        recyclerView = view.findViewById(R.id.mainRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        Trip tr = new Trip("1","First","Mar 6, 2020 07:33 PM","upcomming","true","true","cairo" ,
//                "33","34","ismailia","43","45");
//        FireBaseHandler.getInstance().addTrip(tr,userId);

    }


    @Override
    public void onStart() {
        super.onStart();
        upcomingPresenter.getTripList();
    }

    @Override
    public void displayTrips(List<Trip> tripList) {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        adapter = new TripAdapter(tripList, getContext());
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

