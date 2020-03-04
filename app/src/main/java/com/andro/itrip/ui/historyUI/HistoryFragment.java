package com.andro.itrip.ui.historyUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.andro.itrip.R;
import com.andro.itrip.ui.upcomingUI.UpcomingContract;
import com.andro.itrip.ui.upcomingUI.UpcomingPresenter;

public class HistoryFragment extends Fragment implements HistoryContract.ViewInterface {

    TextView textView;
    HistoryContract.PresenterInterface historyPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        textView = root.findViewById(R.id.text_history);
        historyPresenter = new HistoryPresenter(this);
        historyPresenter.sendMessage();
        return root;
    }
    public void displayMessage(String message) {
        textView.setText(message);
    }
}
