package com.mapelli.simone.githubclient.UI;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mapelli.simone.githubclient.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.arch.core.executor.testing.CountingTaskExecutorRule;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

// TODO :
//  On Xiaomi redmi 2 : test problem : java.lang.RuntimeException: Could not launch intent Intent... etc.
// Even by bypassing SplashScreenActivity and starting app directly through SearchUsersActivity
// --> investigating what app thread or whatever is blocking starting, no background task present :
// - animations are stopped
// - not in stand-by/lock
// - drainTasks below doesn't work
// - splashscreen and progressBar disabled
// - ViewModel/Livedata observer disabled
// ------
// On Huawei 9 lite : it's ok (but searchView/Toolbar doesn't show : INCOMPATIBILITY ERROR)
// ------
// On emulator sdk 26 : can't find lens button, even if toolbar is present in screen,
// maybe is a matter of description
// No views in hierarchy found matching: (with class name: is "androidx.appcompat.widget.AppCompatImageView" and with content description text: is "Cerca" and Child at position 1 in parent (with class name: is "android.widget.LinearLayout" and Child at position 0 in parent with id: 2131230923) and is displayed on the screen to the user)
// ------

@SmallTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class GenericItemViewExistenceTest {
    SearchUsersActivity searchUsersActivity;

    @Rule
    public ActivityTestRule<SearchUsersActivity> mActivityTestRule = new ActivityTestRule<>(SearchUsersActivity.class);

    @Rule
    public CountingTaskExecutorRule mCountingTaskExecutorRule = new CountingTaskExecutorRule();


    @Before
    public void init(){
        searchUsersActivity   = mActivityTestRule.getActivity();
    }

    @Test
    public void checkSearchBtnTest() throws Throwable {
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
