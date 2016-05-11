package com.github.prudencioj.mockingtest;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class SampleFlakyTest {

    @Before
    public void setup() {
        openApp("com.github.prudencioj.mockingtest");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLogin() throws UiObjectNotFoundException, InterruptedException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject emailField = device.findObject(new UiSelector()
                .resourceId("com.github.prudencioj.mockingtest:id/email"));
        UiObject passwordField = device.findObject(new UiSelector()
                .resourceId("com.github.prudencioj.mockingtest:id/password"));
        UiObject loginButton = device.findObject(new UiSelector()
                .resourceId("com.github.prudencioj.mockingtest:id/email_sign_in_button"));
        UiObject progressBar = device.findObject(new UiSelector()
                .resourceId("com.github.prudencioj.mockingtest:id/login_progress"));
        UiObject loginMessage = device.findObject(new UiSelector()
                .resourceId("android:id/message"));

        // populate login fields and click the button
        emailField.waitForExists(2000);
        emailField.setText("joao@gmail.com");
        passwordField.setText("qwerty");
        loginButton.click();

        // wait for a server response
        progressBar.waitForExists(2000);
        progressBar.waitUntilGone(15000);
        loginMessage.waitForExists(2000);

        // check if the user is logged in
        assertEquals("Failed to login the user", "Success!", loginMessage.getText());
    }

    private void openApp(String packageName) {
        // Initialize UiDevice instance
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                5000);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(packageName);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)),
                5000);
    }
}