package com.pentavalue.tvquran.ui.fragments;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.view.Gravity;

import com.pentavalue.tvquran.R;


/**
 * @author AhmedRabie
 */
public class BaseFragment extends Fragment {
    private ProgressDialog mDialog;

    /**
     * This method is used to show a progress dialog for the screen.
     *
     * @return True if the progress dialog was actually shown due this call.
     */
    public boolean showLoadingDialog() {
        if (mDialog == null && this != null) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setMessage(getString(R.string.loading));
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setIndeterminate(true);
            mDialog.setCancelable(false);
            mDialog.getWindow().setGravity(Gravity.CENTER);
        }

        if (mDialog != null && !mDialog.isShowing()) {
            try {
                mDialog.show();
            } catch (Exception e) {
                // Nothing
            }
            return true;
        }
        return false;
    }

    /**
     * This method is used to hide the progress dialog off this screen.
     *
     * @return True if the dialog was actually dismissed due this call.
     */
    public boolean hideLoadingDialog() {
        if (this != null && mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                // Nothing
            }
            return true;
        }
        return false;
    }
}
