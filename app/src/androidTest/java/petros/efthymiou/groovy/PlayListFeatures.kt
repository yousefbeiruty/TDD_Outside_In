package petros.efthymiou.groovy


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.CoreMatchers.allOf


import org.junit.Test

import petros.efthymiou.groovy.di.modules.idlingResource


class PlayListFeatures :BaseUITest(){

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlist")
    }

    @Test
    fun displayPlayList(){

        assertRecyclerViewItemCount(R.id.playlists_list,10)

        onView(allOf(withId(R.id.playlist_name),
        isDescendantOfA(nthChildOf(withId(R.id.playlists_list),0))))
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_category),
            isDescendantOfA(nthChildOf(withId(R.id.playlists_list),0))))
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_image),
            isDescendantOfA(nthChildOf(withId(R.id.playlists_list),0))))
            .check(matches(withDrawable(R.mipmap.playlist)))
            .check(matches(isDisplayed()))
    }
    @Test
    fun displayLoaderWhileFetchingDisplayList(){
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loaders)
    }

    @Test
    fun hideLoader(){
        assertNotDisplayed(R.id.loaders)
    }

    @Test
    fun displayRocImaageFromListItem(){

        onView(allOf(withId(R.id.playlist_image),
            isDescendantOfA(nthChildOf(withId(R.id.playlists_list),
                0))))
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_image),
            isDescendantOfA(nthChildOf(withId(R.id.playlists_list),
                3))))
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigateToDetailsScreen(){
        onView(allOf(withId(R.id.playlist_image),
            isDescendantOfA(nthChildOf(withId(R.id.playlists_list),
                 0)))).perform(click())

        assertDisplayed(R.id.plalist_details_root)
    }


}