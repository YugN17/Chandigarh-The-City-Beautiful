package com.chandigarhthecitybeautiful.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.chandigarhthecitybeautiful.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlacesDataRepositoryTest {

    private lateinit var placesDataRepository: PlacesDataRepository
    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource

    private var allPlaces: MutableLiveData<List<Place>> = MutableLiveData()

    private val responseNoData: ArrayList<Place> = ArrayList()
    private val responseWithData: ArrayList<Place> = ArrayList()

    @ExperimentalCoroutinesApi
    val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun createRepository() {
        Dispatchers.setMain(testDispatcher)
        localDataSource = FakeLocalDataSource(allPlaces)
        remoteDataSource = FakeRemoteDataSource(false, responseNoData)
        placesDataRepository = PlacesDataRepository(
            localDataSource, remoteDataSource,
            Dispatchers.Main
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

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getAllPlacesTest() {
        localDataSource.allPlaces.postValue(responseWithData)
        val output = localDataSource.getPlaces().value
        assertEquals(responseWithData, output)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getMessage_Test() = runBlockingTest {
        remoteDataSource.isConnected = false
        placesDataRepository.updateDB()
        val output = placesDataRepository.message.value
        assertEquals("No Internet Connection", output)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateDB_No_Connection() = runBlockingTest {
        remoteDataSource.isConnected = false
        placesDataRepository.updateDB()
        assertEquals("No Internet Connection", placesDataRepository.message.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateDB_With_Connection_No_Data() = runBlockingTest {
        remoteDataSource.isConnected = true
        placesDataRepository.updateDB()
        assertEquals(0, placesDataRepository.allPlaces.value!!.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun updateDB_With_Connection_with_Data() = runBlockingTest {
        remoteDataSource.isConnected = true
        remoteDataSource.response = responseWithData
        placesDataRepository.updateDB()
        val actualUpdatedList = placesDataRepository.allPlaces.value!!
        assertEquals(3, actualUpdatedList.size)
        assertEquals(responseWithData[0].name, actualUpdatedList[0].name)
        assertEquals(responseWithData[2].name, actualUpdatedList[2].name)
        assertEquals(responseWithData[2].image, actualUpdatedList[2].image)
    }


    @Test
    fun getPlaceTest_ValidId() {
        localDataSource.allPlaces.postValue(responseWithData)
        val place = placesDataRepository.getPlace(1)
        assertEquals(responseWithData[0].name, place.name)
    }

    @Test
    fun getPlaceTest_InValidId() {
        localDataSource.allPlaces.postValue(responseWithData)
        val place = placesDataRepository.getPlace(-1)
        assertEquals("Invalid Name", place.name)
    }
}