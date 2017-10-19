package com.pentavalue.tvquran.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.pentavalue.tvquran.R;

/**
 * Created by Passant on 6/12/2017.
 */

public class DialogUtilit {
    Dialog dialog;

    public Dialog showCustomDialog(Activity mContext, String msg) {

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView msgTxt = (TextView) dialog.findViewById(R.id.txt_msg);
        msgTxt.setText(msg);
        if (!mContext.isFinishing())
        dialog.show();
        return dialog;
    }
}
