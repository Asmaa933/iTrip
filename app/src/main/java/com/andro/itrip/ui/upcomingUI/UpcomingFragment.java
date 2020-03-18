package com.andro.itrip.ui.upcomingUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.lifecycle.ViewModelProviders;

import com.andro.itrip.GlobalApplication;
import com.andro.itrip.R;
import com.andro.itrip.Trip;
import com.andro.itrip.Utils;
import com.andro.itrip.headUI.ChatHeadService;

import java.util.List;

public class UpcomingFragment extends Fragment implements UpcomingContract.ViewInterface {

    private static UpcomingContract.PresenterInterface upcomingPresenter;
    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private LinearLayout emptyView;

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
        if (!Utils.canDrawOverlays(GlobalApplication.getAppContext())) {
            requestPermission(Utils.OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        updateTripLists();
    }

    @Override
    public void displayTrips(List<Trip> tripList) {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        adapter = new TripAdapter(tripList, upcomingPresenter, getContext());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void displayNoTrips() {
        recyclerView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);

    }

    @Override
    public void displayMessage(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void needPermissionDialog(final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission(requestCode);
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    private void requestPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + GlobalApplication.getAppContext().getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Utils.OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(getActivity())) {
                needPermissionDialog(requestCode);
            }

        } else if (requestCode == Utils.OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG) {
            if (!Utils.canDrawOverlays(getActivity())) {
                needPermissionDialog(requestCode);


            }

        }

    }
    public static void updateTripLists(){
        upcomingPresenter.getTripList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

