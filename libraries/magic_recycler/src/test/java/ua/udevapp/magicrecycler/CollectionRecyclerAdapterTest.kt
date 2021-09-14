package ua.udevapp.magicrecycler

import android.content.Context
import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import ua.udevapp.magicrecycler.data.MockRepository
import ua.udevapp.magicrecycler.recycler.adapter.MockCollectionRecyclerAdapter

@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.LOLLIPOP])
@RunWith(RobolectricTestRunner::class)
class CollectionRecyclerAdapterTest {

    private var context: Context? = null

    @Before
    fun setup() {
        context = RuntimeEnvironment.getApplication()
    }

    @Test
    fun `Collection method - clear`() {
        MockCollectionRecyclerAdapter().apply {
            addAll(MockRepository.getInitialMockData())
            assertTrue("Has data set before cleaning", itemCount > 0)

            clear()
            assertTrue(itemCount == 0)
        }
    }

    @Test
    fun `Collection method - addAll`() {
        MockCollectionRecyclerAdapter().apply {
            addAll(MockRepository.getInitialMockData())
            assertEquals("Has initialize with correct data", all, MockRepository.getInitialMockData())

            addAll(MockRepository.getPartialChangedMockData())
            assertEquals(
                all,
                MockRepository.getInitialMockData() + MockRepository.getPartialChangedMockData()
            )
        }
    }

    @Test
    fun `Collection method - addItem`() {
        MockCollectionRecyclerAdapter().apply {
            addItem(MockRepository.getUpdatedItemFromMockData())
            assertEquals("Has initialize with correct data", all, listOf(MockRepository.getUpdatedItemFromMockData()))

            addItem(MockRepository.getUpdatedItemFromMockData())
            assertEquals(
                all,
                listOf(
                    MockRepository.getUpdatedItemFromMockData(),
                    MockRepository.getUpdatedItemFromMockData()
                )
            )
        }
    }

    @Test
    fun `Collection method - updateAll`() {
        MockCollectionRecyclerAdapter().apply {
            updateAll(MockRepository.getInitialMockData())
            assertEquals("Has initialize with correct data",all, MockRepository.getInitialMockData())

            updateAll(MockRepository.getPartialChangedMockData())
            assertEquals(all, MockRepository.getUpdatedMockData())
        }

    }

    @Test
    fun `Collection method - clear and addAll`() {
        MockCollectionRecyclerAdapter().apply {
            clearAndAddAll(MockRepository.getInitialMockData())
            assertEquals("Has initialize with correct data", all, MockRepository.getInitialMockData())

            clearAndAddAll(MockRepository.getInitialMockData())
            assertEquals(all, MockRepository.getInitialMockData())
        }
    }
}