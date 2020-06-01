package com.chandigarhthecitybeautiful.fragments

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.chandigarhthecitybeautiful.R
import com.chandigarhthecitybeautiful.ServiceLocator
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.FakeAndroidPlacesDataRepository
import com.chandigarhthecitybeautiful.ui.fragments.PlaceDetails
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@MediumTest
@RunWith(AndroidJUnit4::class)
class PlaceDetailsTest {
    private lateinit var placesDataRepository: FakeAndroidPlacesDataRepository

    @Before
    fun initRepository() {
        placesDataRepository =
            FakeAndroidPlacesDataRepository()
        ServiceLocator.iPlacesDataRepository = placesDataRepository
    }

    @After
    fun cleanUp() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun withData() {
        val places = ArrayList<Place>()
        places.add(
            Place(
                1, "Sukhna Lake",
                "Sukhna Lake is a reservoir at the foothills (Shivalik hills) of the Himalayas",
                "14KM", "7AM-9PM", "lake.png"
            )
        )
        places.add(
            Place(
                2, "Rock Garden",
                "Rock Garden is a perfect epitome of art pieces patchwork and various sculptures made from home, industrial or any waste",
                "13KM", "7AM-7PM", "rock_garden2.png"
            )
        )
        places.add(
            Place(
                3, "Rose Garden",
                "Rose denotes love and who doesn't love to be in the garden loaded with the rarest rose species",
                "15KM", "8AM-8PM", "rose-garden-1.jpg"
            )
        )
        placesDataRepository.setupData(places, "")
        val bundle = Bundle()
        bundle.putInt("placeId", 1)
        bundle.putString("placeLabel", "Sukhna Lake")
        launchFragmentInContainer<PlaceDetails>(
            bundle,
            R.style.AppTheme
        )
        Espresso.onView(withId(R.id.timing))
            .check(ViewAssertions.matches(ViewMatchers.withText("Timing: " + places[0].timing)))
        Espresso.onView(withId(R.id.distance))
            .check(ViewAssertions.matches(ViewMatchers.withText("Distance: " + places[0].distance)))
        Espresso.onView(withId(R.id.description))
            .check(ViewAssertions.matches(ViewMatchers.withText(places[0].description)))
    }

}