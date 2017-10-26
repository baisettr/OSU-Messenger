package com.osu.osu;

import android.widget.EditText;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    public EditText editTextUserId;
    public EditText editTextPassword;
    //editTextUserId..setText("1");
    //editTextPassword.setText("rk");
    @Test
    public void checkInputs() throws Exception{



            assertNull(editTextUserId);
            assertNull(editTextPassword);
    }
 }