package com.example.spendtrackernotes.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import com.example.spendtrackernotes.data.Spend
import com.example.spendtrackernotes.data.SpendsDatabase
import com.example.spendtrackernotes.data.SpendsTrackerDataSource
import com.example.spendtrackernotes.getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class SpendViewModelTest {

    private lateinit var viewModel: SpendViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, SpendsDatabase::class.java)
            .allowMainThreadQueries().build()
        val dataSource = SpendsTrackerDataSource(db.getSpendDao())
        viewModel = SpendViewModel(dataSource)
    }

    @Test
    fun testSpendViewModel(){
        viewModel.addSpend(170, "Bought some goods")
        viewModel.getLast50Spends()
        val result = viewModel.last50SpendsLiveData.getOrAwaitValue().find {
            it.amount == 170 && it.description == "Bought some goods"
        }
        assertThat(result != null).isTrue()
    }

}