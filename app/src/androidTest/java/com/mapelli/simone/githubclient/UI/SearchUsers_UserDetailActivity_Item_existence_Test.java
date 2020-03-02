package com.mapelli.simone.githubclient.UI;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mapelli.simone.githubclient.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

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

// TODO : test problem : check the same as GenericItemViewExistenceTest
@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class SearchUsers_UserDetailActivity_Item_existence_Test {

    // @Rule
    // public ActivityTestRule<SplashScreenActivity> mActivityTestRule = new ActivityTestRule<>(SplashScreenActivity.class);

    @Rule
    public ActivityTestRule<SearchUsersActivity> mActivityTestRule = new ActivityTestRule<>(SearchUsersActivity.class);

    @Test
    public void searchUsers_UserDetailActivity_Item_existence_Test() {
        ViewInteraction imageView = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.ImageView.class), withContentDescription("Cerca"),
                        childAtPosition(
                                allOf(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        childAtPosition(
                                                withId(R.id.search),
                                                0)),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

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
        textView2.check(matches(withText("Click on lens and search someone !")));

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

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Torna indietro"),
                        childAtPosition(
                                allOf(withId(R.id.detail_toolbar),
                                        childAtPosition(
                                                withId(R.id.detail_appbar),
                                                0)),
                                0),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withText("nadar71"),
                        childAtPosition(
                                allOf(withId(R.id.detail_toolbar),
                                        childAtPosition(
                                                withId(R.id.detail_appbar),
                                                0)),
                                1),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withText("nadar71"),
                        childAtPosition(
                                allOf(withId(R.id.detail_toolbar),
                                        childAtPosition(
                                                withId(R.id.detail_appbar),
                                                0)),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("nadar71")));

        ViewInteraction textView6 = onView(
                allOf(withText("PROFILE"),
                        childAtPosition(
                                allOf(withContentDescription("Profile"),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("PROFILE")));

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
                allOf(withText("REPOSITORIES"),
                        childAtPosition(
                                allOf(withContentDescription("Repositories"),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView11.check(matches(withText("REPOSITORIES")));

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
                allOf(withContentDescription("Torna indietro"),
                        childAtPosition(
                                allOf(withId(R.id.detail_toolbar),
                                        childAtPosition(
                                                withId(R.id.detail_appbar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction viewGroup = onView(
                allOf(withId(R.id.detail_toolbar),
                        childAtPosition(
                                allOf(withId(R.id.detail_appbar),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                0)),
                                0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));
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
