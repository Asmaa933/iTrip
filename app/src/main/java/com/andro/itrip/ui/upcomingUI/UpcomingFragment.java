package com.andro.itrip.ui.upcomingUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.andro.itrip.R;

public class UpcomingFragment extends Fragment implements UpcomingContract.ViewInterface {

    TextView textView;
    UpcomingContract.PresenterInterface upcomingPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_upcoming, container, false);
        textView = root.findViewById(R.id.text_upcoming);
        upcomingPresenter = new UpcomingPresenter(this);
        upcomingPresenter.sendMessage();
        return root;
    }
    public void displayMessage(String message) {
        textView.setText(message);
    }

}

