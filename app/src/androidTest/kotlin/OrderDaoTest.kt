import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foundbook.data.BookOrderDatabase
import com.example.foundbook.data.OrderDao
import org.junit.Before
import org.junit.runner.RunWith
import android.content.Context
import com.example.foundbook.data.Order
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class OrderDaoTest {

    private lateinit var orderDao: OrderDao
    private lateinit var bookOrderDatabase: BookOrderDatabase

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()

        bookOrderDatabase = Room.inMemoryDatabaseBuilder(context, BookOrderDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        orderDao = bookOrderDatabase.orderDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        bookOrderDatabase.close()
    }

    private var order1 = Order(1, "Book1", "Receiver1", "2023-01-01", false)
    private var order2 = Order(2, "Book2", "Receiver2", "2023-01-02", false)

    private suspend fun addOneOrderToDb(){
        orderDao.insert(order1)
    }

    private suspend fun addTwoOrdersToDb(){
        orderDao.insert(order1)
        orderDao.insert(order2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsOrderIntoDB() = runBlocking {
        addOneOrderToDb()

        val allOrders = orderDao.getAllOrders().first()
        assertEquals(allOrders[0], order1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllOrders_returnsAllOrdersFromDB() = runBlocking {
        addTwoOrdersToDb()

        val allOrders = orderDao.getAllOrders().first()
        assertEquals(allOrders[0], order1)
        assertEquals(allOrders[1], order2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateOrders_updatesOrdersInDB() = runBlocking {
        addTwoOrdersToDb()

        orderDao.update(Order(1, "Book1", "Receiver1", "2023-01-01", true))
        orderDao.update(Order(2, "Book2", "Receiver2", "2023-01-02", true))

        val allOrders = orderDao.getAllOrders().first()
        assertEquals(allOrders[0], Order(1, "Book1", "Receiver1", "2023-01-01", true))
        assertEquals(allOrders[1], Order(2, "Book2", "Receiver2", "2023-01-02", true))

    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteOrders_deletesOrdersFromDB() = runBlocking {
        addTwoOrdersToDb()

        orderDao.delete(order1)
        orderDao.delete(order2)

        val allOrders = orderDao.getAllOrders().first()
        assertTrue(allOrders.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetOrder_returnsOrderFromDB() = runBlocking {
        addOneOrderToDb()

        val order = orderDao.getOrder(1)

        assertEquals(order.first(), order1)
    }
}