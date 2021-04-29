package com.example.spendtrackernotes.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import com.example.spendtrackernotes.data.SpendsDatabase
import com.example.spendtrackernotes.data.SpendsTrackerDataSource
import com.example.spendtrackernotes.getOrAwaitValue1
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@Config(manifest = "src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner::class)
class SpendViewModelTest1 : TestCase() {

    private lateinit var spendsDatabase: SpendsDatabase
    private lateinit var viewModel: SpendViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        spendsDatabase = Room.inMemoryDatabaseBuilder(
            context, SpendsDatabase::class.java
        ).allowMainThreadQueries().build()
        val dataSource = SpendsTrackerDataSource(spendsDatabase.getSpendDao())
        viewModel = SpendViewModel(dataSource)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        spendsDatabase.close()
    }

    @Test
    fun testAddingSpend() {
        viewModel.addSpend(100, "Eggs")
        viewModel.getLast50Spends()
        val result = viewModel.last50SpendsLiveData.getOrAwaitValue1().find {
            it.amount == 100 && it.description == "Eggs"
        }
        assertThat(result != null).isTrue()
    }
}