package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.repositories

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tihasg.br.github_repositories_pull_requests_viewer.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoriesActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(RepositoriesActivity::class.java)

    @Test
    fun testRepositoryListDisplayed() {
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRepositoryItemClick_opensPullRequestsActivity() {
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RepositoriesAdapter.RepositoryViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }
}
