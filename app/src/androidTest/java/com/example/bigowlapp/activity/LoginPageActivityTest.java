package com.example.bigowlapp.activity;

import android.os.SystemClock;
import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.bigowlapp.R;
import com.example.bigowlapp.viewModel.LogInViewModel;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
public class LoginPageActivityTest {
    @Rule
    public ActivityScenarioRule<LoginPageActivity> activityRule = new ActivityScenarioRule<>(LoginPageActivity.class);

    @Mock
    private LogInViewModel mockLogInViewModel;

    private String userEmail = "john.doe@email.com";
    private String userPassword = "abc123";
    private String fakeUserEmail = "testuser1@email.com";
    LoginPageActivity loginPageActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    //No user and No password
    public void A_NoUserNoPasswordLoginUserScreenTest() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(replaceText(""));
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(""));

        onView(withId(R.id.button))
                .check(matches(withText("Sign In"))).perform(click());
        onView(withId(R.id.editTextTextEmailAddress)).check(matches(hasErrorText("Please enter a valid email")));
    }

    @Test
    //valid user and no password
    public void B_ValidUserNoPasswordLoginUserScreenTest() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(replaceText(fakeUserEmail));
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(""));

        onView(withId(R.id.button))
                .check(matches(withText("Sign In"))).perform(click());
        onView(withId(R.id.editTextTextPassword)).check(matches(hasErrorText("Please enter your password")));
    }

    @Test
    //valid user and wrong password
    public void C_ValidUserWrongPasswordLoginUserScreenTest() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(replaceText(fakeUserEmail));
        onView(withId(R.id.editTextTextPassword)).perform(replaceText("abc123"));

        onView(withId(R.id.button))
                .check(matches(withText("Sign In"))).perform(click());

        onView(withId(R.id.editTextTextEmailAddress)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextTextPassword)).check(matches(isDisplayed()));
    }

    @Test
    //valid user and valid password
    public void D_ValidUserValidPasswordLoginUserScreenTest() {
        onView(withId(R.id.editTextTextEmailAddress)).perform(replaceText(userEmail));
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(userPassword));

        onView(withId(R.id.button))
                .check(matches(withText("Sign In"))).perform(click());

        Espresso.onView(isRoot()).perform(waitFor(10000));
        Espresso.onView(withId(R.id.top_app_bar)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.user_avatar)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.user_first_name)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.user_last_name)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_logout))
                .check(matches(withText("Log out"))).perform(click());
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