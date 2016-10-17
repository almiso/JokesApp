package org.almiso.jokesapp.ui;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.almiso.jokesapp.R;
import org.almiso.jokesapp.network.calback.ProgressInterface;

public class BaseActivity extends AppCompatActivity {

    /* Constants */

    protected String TAG;

    /* Controls */

    private ProgressDialog progressDialog;

    /* Common methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    /* Initialisation methods */

    private void initData() {
        TAG = this.getClass().getSimpleName();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setMessage(getString(R.string.app_name));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    /* protected methods */

    protected ProgressInterface getProgressInterface() {
        return new ProgressInterface() {
            @Override
            public void showProgress() {
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }

            @Override
            public void hideProgress() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        };
    }
}