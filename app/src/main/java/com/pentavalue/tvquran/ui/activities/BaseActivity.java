package com.pentavalue.tvquran.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;

import com.pentavalue.tvquran.R;

/**
 * Created by OmniaGamil on 5/17/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        init();*/
    }
    /**
     * This method is used to show a progress dialog for the screen.
     *
     * @return True if the progress dialog was actually shown due this call.
     */
    public void showLoadingDialog() {
        if (mDialog == null && this != null) {
            mDialog = new ProgressDialog(this);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setMessage(getString(R.string.loading));
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setIndeterminate(true);
            mDialog.setCancelable(false);
            mDialog.getWindow().setGravity(Gravity.CENTER);
        }

        if (this!=null &&!this.isFinishing() && mDialog != null && !mDialog.isShowing()) {
            try {
                mDialog.show();
            } catch (Exception e) {
                // Nothing
                e.printStackTrace();
            }

        }
       // return false;
    }

    /**
     * This method is used to hide the progress dialog off this screen.
     *
     * @return True if the dialog was actually dismissed due this call.
     */
    public void hideLoadingDialog() {
        if (this != null && !this.isFinishing() && mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                // Nothing
            }

        }

}
}
