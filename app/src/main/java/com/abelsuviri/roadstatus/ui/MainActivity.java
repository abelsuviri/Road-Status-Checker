package com.abelsuviri.roadstatus.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abelsuviri.roadstatus.R;
import com.abelsuviri.viewmodel.MainViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

/**
 * @author Abel Suviri
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.roadIdInput)
    TextInputLayout roadInput;

    @BindView(R.id.roadNameLabel)
    TextView roadNameLabel;

    @BindView(R.id.roadStatusLabel)
    TextView roadStatusLabel;

    @BindView(R.id.roadStatusDescriptionLabel)
    TextView roadStatusDescriptionLabel;

    @BindView(R.id.loadingLayout)
    RelativeLayout loadingLayout;

    @BindView(R.id.infoLayout)
    ConstraintLayout infoLayout;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        initSubscriptions();
    }

    @OnClick(R.id.searchButton)
    void loadRoadInfo() {
        mainViewModel.getRoadInfo(roadInput.getEditText().getText().toString()).observe(this, road -> {
            roadInput.getEditText().getText().clear();
            infoLayout.setVisibility(View.VISIBLE);
            roadNameLabel.setText(road.get(0).name);
            roadStatusLabel.setText(road.get(0).status);
            roadStatusDescriptionLabel.setText(road.get(0).statusDescription);
        });
    }

    /**
     * This method will observe for changes in the isLoading and isFailed variables.
     * If isLoading is true then the loading layer will be visible, if false it will be hidden.
     * If isFailed is true then a retry dialog will be visible
     */
    private void initSubscriptions() {
        mainViewModel.getExists().observe(this, exists ->  {
            infoLayout.setVisibility(View.INVISIBLE);
            roadInput.setError((exists) ? "" : getString(R.string.road_not_found));
        });

        mainViewModel.getIsLoading().observe(this, isLoading -> loadingLayout.setVisibility((isLoading) ? View.VISIBLE : View.GONE));

        mainViewModel.getIsFailed().observe(this, isFailed -> {
            if (isFailed) {
                infoLayout.setVisibility(View.INVISIBLE);
                showRetryDialog();
            }
        });
    }

    /**
     * This method displays an AlertDialog to try again to call the server.
     */
    private void showRetryDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getResources().getString(R.string.request_error));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.retry), (dialog, i) -> {
            dialog.dismiss();
            loadRoadInfo();
        }).show();
    }
}
