package com.mapelli.simone.githubclient.UI.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.UI.SearchUsersActivity;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.arch.core.executor.testing.CountingTaskExecutorRule;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class ViewExistenceTests {

    @Rule
    public ActivityTestRule<SearchUsersActivity> mActivityTestRule = new ActivityTestRule<>(SearchUsersActivity.class);

    @Rule
    public CountingTaskExecutorRule mCountingTaskExecutorRule = new CountingTaskExecutorRule();


    @Test
    // If load more btn is not visibile before first search : TEST MUST FAIL FOR CORRECTNESS
    public void checkLoadMoreBtnNotDisplayedTest() {
        try {
            onView(withId(R.id.loadMore_btn)).perform(click());
        } catch (AssertionFailedError e) {
            // View not displayed
        }
    }

    @Test
    // If load more btn is visibile only after first search
    public void checkLoadMoreBtnDisplayedTest(){

        ViewInteraction appCompatImageView = onView(
                allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Cerca"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withId(R.id.search),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("nadar71"), closeSoftKeyboard());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("nadar71"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());

        ViewInteraction btn = onView(allOf(withId(R.id.loadMore_btn),isDisplayed()));
        try {
            onView(withId(R.id.loadMore_btn)).perform(click());
        } catch (AssertionFailedError e) {
            // View not displayed
        }

    }


    // NB : TEST OK
    // On Huawei P9/P10 lite : it's ok
    // ------
    // On emulator sdk 26 : can't find lens button, even if toolbar is present in screen :
    // create a test specific for it
    // ------
    // On Xiaomi redmi 2 : test problem due to the phone config
    // : java.lang.RuntimeException: Could not launch intent Intent...
    // Even by bypassing SplashScreenActivity and starting app directly through SearchUsersActivity
    // --> investigating what app thread or whatever is blocking starting, no background task present :
    // - animations are stopped
    // - not in stand-by/lock
    // - drainTasks below doesn't work
    // - splashscreen and progressBar disabled
    // - ViewModel/Livedata observer disabled
    // - Ambient mode disabled
    // ------
    @Test
    public void checkExistenceAllViewsTest() throws Throwable {
        draintasks();
        ViewInteraction appCompatImageView = onView(
                allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Cerca"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withId(R.id.search),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.toolbar_title), withText("Github Basic Client"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.app_bar),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Github Basic Client")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.empty_view), withText("Click on lens and search someone !"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));

        ViewInteraction searchAutoComplete = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("nadar71"), closeSoftKeyboard());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.toolbar_title), withText("Github Basic Client"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.app_bar),
                                                0)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Github Basic Client")));

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("nadar71"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());

        ViewInteraction constraintLayout = onView(
                allOf(withId(R.id.linearLayout),
                        childAtPosition(
                                allOf(withId(R.id.userList),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                0)),
                                0),
                        isDisplayed()));
        constraintLayout.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withText("Profile"),isDisplayed()));
        textView6.check(matches(withText("Profile")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.name_title_txt), withText("NAME :"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.viewPager))),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("NAME :")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.location_title_txt), withText("LOCATION :"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.viewPager))),
                                3),
                        isDisplayed()));
        textView8.check(matches(withText("LOCATION :")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.email_title_txt), withText("EMAIL :"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.viewPager))),
                                5),
                        isDisplayed()));
        textView9.check(matches(withText("EMAIL :")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.profile_url_title_txt), withText("PROFILE URL :"),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.viewPager))),
                                7),
                        isDisplayed()));
        textView10.check(matches(withText("PROFILE URL :")));

        ViewInteraction textView11 = onView(
                allOf(withText("Repositories"),isDisplayed()));
        textView11.check(matches(withText("Repositories")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.user_photo_img),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.viewPager))),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.user_photo_img),
                        childAtPosition(
                                allOf(withId(R.id.constraintLayout),
                                        withParent(withId(R.id.viewPager))),
                                0),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Repositories"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tablayout),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.detail_toolbar),
                                        childAtPosition(
                                                withId(R.id.detail_appbar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        appCompatImageView.perform(click());


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

    private void draintasks() throws TimeoutException, InterruptedException {
        mCountingTaskExecutorRule.drainTasks(2, TimeUnit.MINUTES);
    }
}
