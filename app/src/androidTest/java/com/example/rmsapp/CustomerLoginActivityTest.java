package com.example.rmsapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class CustomerLoginActivityTest {

    //Rule to specify activity is launched
    @Rule
    public ActivityTestRule<CustomerLoginActivity> mActivityTestRule = new ActivityTestRule<CustomerLoginActivity>(CustomerLoginActivity.class);
    private CustomerLoginActivity mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Signup.class.getName(),null,false);



    //Called before test case
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        //Check if app has launched successfully by checking if email entity is present in view
        View view = mActivity.findViewById(R.id.loginEmail);
        assertNotNull(view); //if not null - launched successfully
    }

    @Test
    //check if clicking 'New User- Signup' redirects and launches signup activity
    public void testLaunchofSignupActivity(){
        assertNotNull(mActivity.findViewById(R.id.txtSignup)); //give id of login
        onView(withId(R.id.txtSignup)).perform(click()); //Espresso method
        Activity signupActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(signupActivity);

        signupActivity.finish();
    }


    //Called after test case
    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}