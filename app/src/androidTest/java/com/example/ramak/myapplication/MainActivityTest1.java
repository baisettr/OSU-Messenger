package com.example.ramak.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by ramak on 10/19/2017.
 */
public class MainActivityTest1 {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;
    private String edit1 = "1";
    private String edit2 = "rk";
    private String edit3 = "rkk";
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(RegisterActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(HomeActivity.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

    }
    @Test
    public void checkUserIdNotNull(){
        View view = mActivity.findViewById(R.id.editTextUserId);
        assertNotNull(view);

    }
    @Test
    public void checkUserPasswordNotNull(){
        View view = mActivity.findViewById(R.id.editTextPassword);
        assertNotNull(view);

    }
    @Test
    public void checkSubmitButtonNotNull(){
        View view = mActivity.findViewById(R.id.submit_button);
        assertNotNull(view);

    }
    @Test
    public void checkRegisterButtonNotNull(){
        View view = mActivity.findViewById(R.id.register_button);
        assertNotNull(view);

    }
    @Test
    public void testLaunchOfRegisterActivity(){
        assertNotNull(mActivity.findViewById(R.id.register_button));
        onView(withId(R.id.register_button)).perform(click());
        Activity register = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(register);
        register.finish();

    }

    @Test
    public void validUserLogintestLaunchOfHomeActivity(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home = getInstrumentation().waitForMonitorWithTimeout(monitor1,5000);
        assertNotNull(home);
        home.finish();

    }
    @Test
    public void inValidUserLogintestLaunch(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit3));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        //Activity home = getInstrumentation().waitForMonitorWithTimeout(monitor1,5000);
        assertNotNull(mActivity);
        mActivity.finish();

    }
    @After
    public void tearDown() throws Exception {
        mActivity = null;

    }

}