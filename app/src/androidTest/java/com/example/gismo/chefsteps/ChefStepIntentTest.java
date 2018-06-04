package com.example.gismo.chefsteps;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.VerificationMode;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.ui.view.RecipeView;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.Serializable;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.gismo.chefsteps.adapter.RecipeAdapter.RECIPE;
import static com.example.gismo.chefsteps.ui.view.RecipeView.START_RECIPE_DETAILS_ACTIVITY;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by gismo on 2018. 05. 30..
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChefStepIntentTest {

    @Rule
    public IntentsTestRule<RecipesListActivity> mActivityRule = new IntentsTestRule<>(
            RecipesListActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void click_On_Recipe_Sends_Recipe_Data_In_Intent() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(hasAction(START_RECIPE_DETAILS_ACTIVITY), hasExtra(equalTo(RECIPE), any(Recipe.class))));
    }
}
