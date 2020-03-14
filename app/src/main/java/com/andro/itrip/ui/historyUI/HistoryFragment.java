package com.andro.itrip.ui.historyUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.andro.itrip.R;
import com.andro.itrip.Trip;

import java.util.List;

public class HistoryFragment extends Fragment implements HistoryContract.ViewInterface {

    TextView textView;
    HistoryContract.PresenterInterface historyPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        historyPresenter = new HistoryPresenter(this);
        return root;
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
