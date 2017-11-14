package com.example.ramak.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

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
    public ActivityTestRule<ProfileActivity> profileActivityTestRule = new ActivityTestRule<ProfileActivity>(ProfileActivity.class);
    public ActivityTestRule<MessageActivity> inBoxActivityTestRule = new ActivityTestRule<MessageActivity>(MessageActivity.class);
    public ActivityTestRule<MapLocationActivity> mapActivityTestRule = new ActivityTestRule<MapLocationActivity>(MapLocationActivity.class);

    private MainActivity mActivity = null;
    private MessageActivity messageActivity = null;
    private ProfileActivity profileActivity = null;
    private MapLocationActivity mapLocationActivity = null;
    private String edit1 = "rk1";
    private String edit2 = "rk";
    private String edit3 = "rkk";
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(RegisterActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(HomeActivity1.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(MapLocationActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor3 = getInstrumentation().addMonitor(ProfileActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor4 = getInstrumentation().addMonitor(MessageActivity1.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor5 = getInstrumentation().addMonitor(ProfileViewActivity.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        messageActivity = inBoxActivityTestRule.getActivity();
        profileActivity = profileActivityTestRule.getActivity();
        mapLocationActivity = mapActivityTestRule.getActivity();
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
    public void validUserLogintestLaunchOfHomeActivityAndLogout(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home = getInstrumentation().waitForMonitorWithTimeout(monitor1,5000);
        Espresso.onView(withId(R.id.logout)).perform(click());
        assertNotNull(mActivity);
        home.finish();

    }
    @Test
    public void validUserLogintestLaunchOfHomeActivityAndMapLocations(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home1 = getInstrumentation().waitForMonitorWithTimeout(monitor2,5000);
        Espresso.onView(withId(R.id.scan)).perform(click());
        assertNotNull(home1);
        home1.finish();

    }
    @Test
    public void validUserLogintestLaunchOfHomeActivityAndMapLocationsAndRefresh(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home1 = getInstrumentation().waitForMonitorWithTimeout(monitor2,5000);
        Espresso.onView(withId(R.id.scan)).perform(click());
        assertNotNull(home1);
        //Espresso.onView(withId(R.id.refresh)).perform(click());
        assertNotNull(home1);
        home1.finish();

    }
    @Test
    public void validUserLogintestLaunchOfHomeActivityAndMapLocationsAndSearch(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home1 = getInstrumentation().waitForMonitorWithTimeout(monitor2,5000);
        Espresso.onView(withId(R.id.scan)).perform(click());
        assertNotNull(home1);
        //Espresso.onView(withId(R.id.search)).perform(click());
        assertNotNull(home1);
        home1.finish();

    }
    @Test
    public void validUserLogintestLaunchOfHomeActivityAndMapLocationsAndUserAndRequest(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home1 = getInstrumentation().waitForMonitorWithTimeout(monitor2,5000);
        Espresso.onView(withId(R.id.scan)).perform(click());
        assertNotNull(home1);
        //Espresso.onView(withId(R.id.marker(view marker with title))).perform(click());
        Activity home2 = getInstrumentation().waitForMonitorWithTimeout(monitor5,5000);
        Espresso.onView(withId(R.id.request)).perform(click());
        assertNotNull(home2);
        home2.finish();
        home1.finish();

    }
    @Test
    public void validUserLogintestLaunchOfHomeActivityAndMapLocationsAndUserAndAccept(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home1 = getInstrumentation().waitForMonitorWithTimeout(monitor2,5000);
        Espresso.onView(withId(R.id.scan)).perform(click());
        assertNotNull(home1);
        //Espresso.onView(withId(R.id.marker(view marker with title))).perform(click());
        Activity home2 = getInstrumentation().waitForMonitorWithTimeout(monitor5,5000);
        Espresso.onView(withId(R.id.accept)).perform(click());
        assertNotNull(home2);
        home2.finish();
        home1.finish();

    }
    @Test
    public void validUserLogintestLaunchOfHomeActivityAndMapLocationsAndUserAndMessage(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home1 = getInstrumentation().waitForMonitorWithTimeout(monitor2,5000);
        Espresso.onView(withId(R.id.scan)).perform(click());
        assertNotNull(home1);
        //Espresso.onView(withId(R.id.marker(view marker with title))).perform(click());
        Activity home2 = getInstrumentation().waitForMonitorWithTimeout(monitor5,5000);
        Espresso.onView(withId(R.id.message)).perform(click());
        Activity home3 = getInstrumentation().waitForMonitorWithTimeout(monitor4,5000);
        assertNotNull(home3);
        home3.finish();
        home2.finish();
        home1.finish();

    }
    @Test
    public void validUserLogintestLaunchOfHomeActivityAndProfile(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home2 = getInstrumentation().waitForMonitorWithTimeout(monitor3,5000);
        Espresso.onView(withId(R.id.profile)).perform(click());
        assertNotNull(home2);
        home2.finish();

    }
    @Test
    public void validUserLogintestLaunchOfHomeActivityAndProfileActivityAndUpdateProfile(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home2 = getInstrumentation().waitForMonitorWithTimeout(monitor3,5000);
        Espresso.onView(withId(R.id.profile)).perform(click());
        assertNotNull(home2);
        Espresso.onView(withId(R.id.update)).perform(click());
        assertNotNull(home2);
        home2.finish();

    }
    @Test
    public void validUserLogintestLaunchOfHomeActivityAndMessage(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home3 = getInstrumentation().waitForMonitorWithTimeout(monitor4,5000);
        Espresso.onView(withId(R.id.message)).perform(click());
        assertNotNull(home3);
        home3.finish();

    }
    @Test
    public void inValidUserLogintestLaunch(){
        Espresso.onView(withId(R.id.editTextUserId)).perform(typeText(edit1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(edit3));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.submit_button)).perform(click());
        Activity home = getInstrumentation().waitForMonitorWithTimeout(monitor1,5000);
        assertNotNull(mActivity);
        mActivity.finish();

    }
    @After
    public void tearDown() throws Exception {
        mActivity = null;

    }

}