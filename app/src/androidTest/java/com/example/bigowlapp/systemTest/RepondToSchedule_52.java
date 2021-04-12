package com.example.bigowlapp.systemTest;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.bigowlapp.R;
import com.example.bigowlapp.activity.WelcomePageActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RepondToSchedule_52 {

    @Rule
    public ActivityTestRule<WelcomePageActivity> mActivityTestRule = new ActivityTestRule<>(WelcomePageActivity.class);

//    @Rule
//    public GrantPermissionRule mGrantPermissionRule =
//            GrantPermissionRule.grant(
//                    "android.permission.SEND_SMS",
//                    "android.permission.RECEIVE_SMS",
//                    "android.permission.ACCESS_FINE_LOCATION",
//                    "android.permission.READ_SMS",
//                    "android.permission.READ_PHONE_NUMBERS",
//                    "android.permission.ACCESS_BACKGROUND_LOCATION",
//                    "android.permission.READ_PHONE_STATE");

    @Test
    public void repondToSchedule_52() throws InterruptedException{
//        Thread.sleep(5000);
//
//        ViewInteraction appCompatEditText = onView(
//                allOf(withId(R.id.editTextTextEmailAddress),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                2)));
//        appCompatEditText.perform(scrollTo(), replaceText("joe2@email.com"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.editTextTextPassword),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        appCompatEditText2.perform(scrollTo(), replaceText("joedoe2"), closeSoftKeyboard());
//
//        ViewInteraction appCompatButton2 = onView(
//                allOf(withId(R.id.button), withText("Sign In"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                4)));
//        appCompatButton2.perform(scrollTo(), click());
        Thread.sleep(5000);

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.action_notification), withContentDescription("notification"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.top_app_bar),
                                        0),
                                6),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        Thread.sleep(2000);

//        ViewInteraction appCompatImageButton2 = onView(
//                allOf(withId(R.id.action_notification), withContentDescription("notification"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.top_app_bar),
//                                        0),
//                                6),
//                        isDisplayed()));
//        appCompatImageButton2.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recyclerview_notifications),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                2)));
        recyclerView3.perform(actionOnItemAtPosition(1, click()));
        Thread.sleep(2000);

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_accept), withText("Accept"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        5),
                                1),
                        isDisplayed()));
        appCompatButton3.perform(click());
        Thread.sleep(2000);

//        ViewInteraction appCompatButton4 = onView(
//                allOf(withId(android.R.id.button1), withText("Ok"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.buttonPanel),
//                                        0),
//                                3)));
//        appCompatButton4.perform(scrollTo(), click());
//
//        ViewInteraction appCompatButton5 = onView(
//                allOf(withId(android.R.id.button1), withText("Ok"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.buttonPanel),
//                                        0),
//                                3)));
//        appCompatButton5.perform(scrollTo(), click());

        pressBack();
        Thread.sleep(2000);

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}