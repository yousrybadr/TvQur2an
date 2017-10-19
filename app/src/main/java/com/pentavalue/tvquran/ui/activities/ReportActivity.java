package com.pentavalue.tvquran.ui.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.model.Entries;
import com.pentavalue.tvquran.network.listeneres.OnContactListener;
import com.pentavalue.tvquran.network.managers.SettingManager;
import com.pentavalue.tvquran.utils.DialogUtilit;
import com.pentavalue.tvquran.utils.ValidatorUtils;

public class ReportActivity extends BaseActivity implements View.OnClickListener {

    TextView txt_suraName, txt_readerName, txt_category;
    EditText userName, email, problem;
    Button sendBtn;
    TextView pageTitle;
    ImageView filterImg;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_report);
        txt_category = (TextView) findViewById(R.id.txt_category);
        txt_readerName = (TextView) findViewById(R.id.txt_readerName);
        txt_suraName = (TextView) findViewById(R.id.txt_suraName);
        userName = (EditText) findViewById(R.id.edt_userName);
        email = (EditText) findViewById(R.id.edt_email);
        problem = (EditText) findViewById(R.id.edt_message);
        sendBtn = (Button) findViewById(R.id.btn_send);
        pageTitle = (TextView) findViewById(R.id.titleTxt);
        pageTitle.setText(getString(R.string.report_title));
        filterImg = (ImageView) findViewById(R.id.rightImg);
        filterImg.setVisibility(View.GONE);
        sendBtn.setOnClickListener(this);
        Entries entity = (Entries) getIntent().getExtras().getSerializable("entity");
        txt_readerName.setText(entity.getReciter_name());
        txt_suraName.setText(entity.getTitle());
        txt_category.setText(entity.getCategory_name());
        handler = new Handler();
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (userName.getText().length() > 0 && email.getText().length() > 0 && problem.getText().length() > 0) {
                    sendBtn.setEnabled(true);
                    sendBtn.setBackgroundDrawable(getResources().getDrawable((R.drawable.enabled_btn)));
                } else {
                    sendBtn.setEnabled(false);
                    sendBtn.setBackgroundDrawable(getResources().getDrawable((R.drawable.rounded_txt)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (userName.getText().length() > 0 && email.getText().length() > 0 && problem.getText().length() > 0) {
                    sendBtn.setEnabled(true);
                    sendBtn.setBackgroundDrawable(getResources().getDrawable((R.drawable.enabled_btn)));

                } else {
                    sendBtn.setEnabled(false);
                    sendBtn.setBackgroundDrawable(getResources().getDrawable((R.drawable.rounded_txt)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        problem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (userName.getText().length() > 0 && email.getText().length() > 0 && problem.getText().length() > 0) {
                    sendBtn.setEnabled(true);
                    sendBtn.setBackgroundDrawable(getResources().getDrawable((R.drawable.enabled_btn)));

                } else {
                    sendBtn.setEnabled(false);
                    sendBtn.setBackgroundDrawable(getResources().getDrawable((R.drawable.rounded_txt)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        if (ValidatorUtils.isValidEmail(email.getText().toString())) {
            showLoadingDialog();
            SettingManager.getInstance(this).contactUs(userName.getText().toString()
                    , email.getText().toString(), problem.getText().toString());
            SettingManager.getInstance(this).addListener(new OnContactListener() {
                @Override
                public void onSuccess() {
                    hideLoadingDialog();
                    DialogUtilit dialogUtilit = new DialogUtilit();
                    clearForm();
                    final Dialog mDialog = dialogUtilit.showCustomDialog(ReportActivity.this, getString(R.string.report_msg));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (mDialog.isShowing())
                                mDialog.dismiss();
                        }
                    }, 3000);
                }

                @Override
                public void onException(Exception exception) {
                    hideLoadingDialog();
                    exception.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "Please write valid email", Toast.LENGTH_SHORT).show();

        }

    }

    private void clearForm() {

        email.getText().clear();
        userName.getText().clear();
        problem.getText().clear();
        sendBtn.setEnabled(false);
        sendBtn.setBackgroundDrawable(getResources().getDrawable((R.drawable.rounded_txt)));

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.report_close_title));
        dialog.setMessage(getString(R.string.report_close_msg));
        dialog.setPositiveButton(getString(R.string.report_discard), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialog.create().dismiss();
                finish();
            }
        });

        dialog.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.create().dismiss();
            }
        });

        dialog.create();
        dialog.show();
    }
}
