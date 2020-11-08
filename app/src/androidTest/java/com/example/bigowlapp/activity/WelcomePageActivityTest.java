package com.example.bigowlapp.activity;

import android.os.SystemClock;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.bigowlapp.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class WelcomePageActivityTest {

    @Rule
    public ActivityTestRule<WelcomePageActivity> mActivityActivityTestRule
            = new ActivityTestRule<>(WelcomePageActivity.class, false, false);

    @Test
    public void executeWelcomePageAtStartTest() {
        mActivityActivityTestRule.launchActivity(null);
        Espresso.onView(isRoot()).perform(waitFor(3000));
        Espresso.onView(withId(R.id.button)).check(matches(isDisplayed()));
    }

    private ViewAction waitFor(final long ms) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait";
            }

            @Override
            public void perform(UiController uiController, View view) {
                SystemClock.sleep(ms);
            }
        };
    }
}
