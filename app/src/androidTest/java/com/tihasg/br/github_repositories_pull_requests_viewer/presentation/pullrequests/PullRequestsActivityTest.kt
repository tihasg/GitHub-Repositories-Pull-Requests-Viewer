package com.tihasg.br.github_repositories_pull_requests_viewer.presentation.pullrequests

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tihasg.br.core.utils.Constants.EXTRA_OWNER
import com.tihasg.br.core.utils.Constants.EXTRA_REPO
import com.tihasg.br.github_repositories_pull_requests_viewer.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PullRequestsActivityTest {

    @Test
    fun testPullRequestsListDisplayed() {
        val intent = Intent(
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext,
            PullRequestsActivity::class.java
        ).apply {
            putExtra(EXTRA_OWNER, "dummy_owner")
            putExtra(EXTRA_REPO, "dummy_repo")
        }

        ActivityScenario.launch<PullRequestsActivity>(intent)

        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }
}
