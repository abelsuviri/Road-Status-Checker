package com.abelsuviri.roadstatus.test;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.abelsuviri.roadstatus.R;
import com.abelsuviri.roadstatus.ui.MainActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.abelsuviri.roadstatus.test.TestUtils.hasTextInputLayoutErrorText;
import static org.junit.Assert.assertNotNull;

/**
 * @author Abel Suviri
 */

@RunWith(AndroidJUnit4.class)
public class SearchRoadSteps {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private Activity activity;

    @Before
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    /**
     * Test for valid data
     */
    @Given("^the user is in MainActivity")
    public void the_user_is_in_MainActivity() {
        assertNotNull(activity);
    }

    @When("^the user types a valid id (.*)$")
    public void the_user_types_a_valid_id(String id) {
        TestUtils.onEditTextWithinTilWithId(R.id.roadIdInput).perform(typeText(id));
    }

    @When("^the user clicks on search button$")
    public void the_user_clicks_on_search_button() {
        onView(withId(R.id.searchButton)).perform(click());
    }

    @Then("^road name (.*) is displayed$")
    public void road_name_is_displayed(String roadName) {
        IdlingPolicies.setMasterPolicyTimeout(5000 * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(5000 * 2, TimeUnit.MILLISECONDS);

        IdlingResource idlingResource = new TestUtils.ElapsedTimeIdlingResource(5000);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withId(R.id.roadNameLabel)).check(matches(withText(roadName)));

        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    /**
     * Test for invalid data
     */

    @When("^the user types an invalid id (.*)$")
    public void the_user_types_an_invalid_id(String id) {
        TestUtils.onEditTextWithinTilWithId(R.id.roadIdInput).perform(typeText(id));
    }

    @Then("^an error message (.*) is displayed$")
    public void an_error_message_is_displayed(String error) {
        IdlingPolicies.setMasterPolicyTimeout(5000 * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(5000 * 2, TimeUnit.MILLISECONDS);

        IdlingResource idlingResource = new TestUtils.ElapsedTimeIdlingResource(5000);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withId(R.id.roadIdInput)).check(matches(hasTextInputLayoutErrorText(error)));

        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}