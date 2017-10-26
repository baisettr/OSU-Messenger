package com.osu.osu;

import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.ramak.myapplication.MainActivity;
import com.example.ramak.myapplication.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by ramak on 10/19/2017.
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest extends MainActivity{

    LoginTest l = new LoginTest();
    private EditText editTextUserId;
    private EditText editTextPassword;
    public LoginTest(){
        super();
        l.valUser();
        editTextUserId.setText("1");
        editTextPassword.setText("rk");
        assertNotNull(editTextUserId);
        assertNotNull(editTextPassword);
    }





}
