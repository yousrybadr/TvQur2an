package com.pentavalue.tvquran.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pentavalue.tvquran.R;
import com.pentavalue.tvquran.network.listeneres.OnContactListener;
import com.pentavalue.tvquran.network.managers.SettingManager;
import com.pentavalue.tvquran.ui.activities.ParentActivity;
import com.pentavalue.tvquran.utils.ValidatorUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ContactUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactUsFragment extends BaseFragment implements View.OnClickListener, OnContactListener {


    @Bind(R.id.userName)
    EditText userName;
    @Bind(R.id.emailAddress)
    EditText emailAddress;
    @Bind(R.id.message)
    EditText message;
    @Bind(R.id.send)
    Button send;


    public ContactUsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContuctUsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contuct_us, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        send.setOnClickListener(this);
        ((ParentActivity) getActivity()).setTitleTxt(getString(R.string.title_contactUS));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
              /*  if (ValidatorUtils.isEmpty(message.getText().toString())||
                        ValidatorUtils.isEmpty(emailAddress.getText().toString() )||
                ValidatorUtils.isEmpty(userName.getText().toString()))
                {

                }
                else{*/
                    if (ValidatorUtils.isValidEmail(emailAddress.getText().toString())) {
                        showLoadingDialog();
                        SettingManager.getInstance(getActivity()).contactUs(userName.getText().toString()
                                , emailAddress.getText().toString(), message.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), "Please write valid email", Toast.LENGTH_SHORT).show();
                    }

              //  }

                break;

            default:
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (userName.getText().length() > 0 && emailAddress.getText().length() > 0 && message.getText().length() > 0) {
                    send.setEnabled(true);
                    send.setBackgroundDrawable(getResources().getDrawable((R.drawable.enabled_btn)));
                } else {
                    send.setEnabled(false);
                    send.setBackgroundDrawable(getResources().getDrawable((R.drawable.rounded_txt)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (userName.getText().length() > 0 && emailAddress.getText().length() > 0 && message.getText().length() > 0) {
                    send.setEnabled(true);
                    send.setBackgroundDrawable(getResources().getDrawable((R.drawable.enabled_btn)));

                } else {
                    send.setEnabled(false);
                    send.setBackgroundDrawable(getResources().getDrawable((R.drawable.rounded_txt)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (userName.getText().length() > 0 && emailAddress.getText().length() > 0 && message.getText().length() > 0) {
                    send.setEnabled(true);
                    send.setBackgroundDrawable(getResources().getDrawable((R.drawable.enabled_btn)));

                } else {
                    send.setEnabled(false);
                    send.setBackgroundDrawable(getResources().getDrawable((R.drawable.rounded_txt)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        SettingManager.getInstance(getActivity()).addListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        SettingManager.getInstance(getActivity()).removeListener(this);
    }

    @Override
    public void onException(Exception exception) {
        hideLoadingDialog();
        exception.printStackTrace();
    }

    @Override
    public void onSuccess() {
        hideLoadingDialog();
        Toast.makeText(getActivity(), "Message Send", Toast.LENGTH_LONG).show();
        clearForm();
    }

    private void clearForm() {

        emailAddress.getText().clear();
        userName.getText().clear();
        message.getText().clear();
        send.setEnabled(false);
        send.setBackgroundDrawable(getResources().getDrawable((R.drawable.rounded_txt)));
    }


}
