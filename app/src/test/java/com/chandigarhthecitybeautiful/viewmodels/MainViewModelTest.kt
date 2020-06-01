package com.chandigarhthecitybeautiful.viewmodels

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chandigarhthecitybeautiful.getOrAwaitValue
import com.chandigarhthecitybeautiful.model.Place
import com.chandigarhthecitybeautiful.source.FakePlacesDataRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P)
class MainViewModelTest {

    private lateinit var placesDataRepository: FakePlacesDataRepository
    private lateinit var mainViewModel: MainViewModel
    private var allPlaces: MutableLiveData<List<Place>> = MutableLiveData()
    private var message: MutableLiveData<String> = MutableLiveData()
    private val responseWithData: ArrayList<Place> = ArrayList()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        placesDataRepository = FakePlacesDataRepository(allPlaces, message)
        mainViewModel = MainViewModel(
            placesDataRepository
        )
        responseWithData.add(
            Place(
                1, "Sukhna Lake",
                "Sukhna Lake is a reservoir at the foothills (Shivalik hills) of the Himalayas",
                "14KM", "7AM-9PM", "imageUrl"
            )
        )
        responseWithData.add(
            Place(
                2, "Rock Garden",
                "Rock Garden is a perfect epitome of art pieces patchwork and various sculptures made from home, industrial or any waste",
                "13KM", "7AM-7PM", "imageUrl"
            )
        )
        responseWithData.add(
            Place(
                3, "Rose Garden",
                "Rose denotes love and who doesn't love to be in the garden loaded with the rarest rose species",
                "15KM", "8AM-8PM", "imageUrl"
            )
        )
    }


    @Test
    fun getAllPlacesTest() {
        placesDataRepository.allPlaces.postValue(responseWithData)
        val output = mainViewModel.allPlaces.getOrAwaitValue()
        assertEquals(responseWithData, output)
    }

    @Test
    fun getMsgTest() {
        val testMsg = "This is a test message"
        placesDataRepository.message.postValue(testMsg)
        val output = mainViewModel.msg.getOrAwaitValue()
        assertEquals(testMsg, output)
    }

    @Test
    fun syncDataFromServerTest() {
        placesDataRepository.responseFromServer = responseWithData
        mainViewModel.syncDataFromServer()
        val output = mainViewModel.allPlaces.getOrAwaitValue()
        assertEquals(responseWithData, output)
    }

    @Test
    fun setMessage() {
        val testMsg = "This is a test message"
        mainViewModel.setMessage(testMsg)
        val output = mainViewModel.msg.getOrAwaitValue()
        assertEquals(testMsg, output)
    }
}