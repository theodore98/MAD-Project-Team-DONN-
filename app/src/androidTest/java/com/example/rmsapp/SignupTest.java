package com.example.rmsapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class SignupTest {

    //Rule
    @Rule
    public ActivityTestRule<Signup> mActivityTestRule = new ActivityTestRule<Signup>(Signup.class);
    private Signup mActivity = null;

    private String mName = "dihan123";
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(CustomerLoginActivity.class.getName(),null,false);

    //Called before test case
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        //Check if app has launched successfully by checking if email entity is present in view
        View view = mActivity.findViewById(R.id.emailUser);
        assertNotNull(view); //if not null - launched successfully
    }

    @Test
    //UI testing with Espresso - check if enter password view is working
    public void testUserInput(){
        //input text in edit text
        onView(withId(R.id.signupPassword)).perform(typeText(mName)); //string is typed automatically by espresso
        //close onscreen keyboard
        Espresso.closeSoftKeyboard();
    }

    @Test
    //check if clicking 'Already have account' redirects and launches login activity
    public void testLaunchofLoginActivity(){
        assertNotNull(mActivity.findViewById(R.id.redirectLogin)); //give id of login
        onView(withId(R.id.redirectLogin)).perform(click());
        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(loginActivity);

        loginActivity.finish();
    }

    //Called after test case
    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}