package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.R;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button mResetButton = null;
    TextView mResetPasswordTV = null;
    AutoCompleteTextView mOldPasswordET = null;
    AutoCompleteTextView mNewPasswordET = null;
    AutoCompleteTextView mConfirmPasswordET = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
    }

    void initView() {
        mResetButton = (Button) findViewById(R.id.reset_btn);
        mResetPasswordTV = (TextView) findViewById(R.id.reset_pwd_tv);
        mOldPasswordET = (AutoCompleteTextView) findViewById(R.id.old_pwd_et);
        mNewPasswordET = (AutoCompleteTextView) findViewById(R.id.new_pwd_et);
        mConfirmPasswordET = (AutoCompleteTextView) findViewById(R.id.confirm_pwd_et);

        mResetButton.setOnClickListener(this);

        mConfirmPasswordET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    mResetButton.performClick();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {


        hideKeyboard();

        String oldPassword = mOldPasswordET.getText().toString();
        String newPassword = mNewPasswordET.getText().toString();
        String confirmPassword = mConfirmPasswordET.getText().toString();

        if (!validatePassword(oldPassword)) {
            mOldPasswordET.setError("Password should be more than 6 character");
        } else if (!validatePassword(newPassword)) {
            mNewPasswordET.setError("Password should be more than 6 character");
        } else if (!validatePassword(confirmPassword)) {
            mConfirmPasswordET.setError("Password should be more than 6 character");
        } else {
            mOldPasswordET.setError(null);
            mNewPasswordET.setError(null);
            mConfirmPasswordET.setError(null);
            Intent intent = new Intent(ResetPasswordActivity.this,ChangePasswordActivity.class);
            startActivity(intent);
        }

    }

    public boolean validatePassword(String password) {
        return password.length() > 6;
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
