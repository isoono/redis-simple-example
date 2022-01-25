package recentview;
import static org.junit.Assert.*;
import java.util.List;

import helper.JedisHelper;
import org.junit.*;

public class RecentViewListTest {
	static JedisHelper helper;
	private RecentViewList viewList;
	//1
	private static final String TEST_USER = "123";
	private int listMaxSize;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		helper = JedisHelper.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		helper.destroyPool();
	}

	@Before
	public void setUp() throws Exception {
		this.viewList = new RecentViewList(helper, TEST_USER);
		assertNotNull(this.viewList);
		//2
		this.listMaxSize = this.viewList.getListMaxSize();
	}

	@Test
	public void testAdd() {
		for (int i = 1; i <= 50; i++) {
			//3
			this.viewList.add(String.valueOf(i));
		}
	}

	@Test
	public void checkMaxSize() {
		int storedSize = this.viewList.getRecentViewList().size();
		//4
		assertEquals(this.listMaxSize, storedSize);
	}

	@Test
	public void checkRecentSize() {
		int checkSize = 4;
		int redisSize = this.viewList.getRecentViewList(checkSize).size();

		//5
		assertEquals(redisSize, checkSize);
	}

	@Test
	public void checkProductNo() {
		//6
		this.viewList.add("50");

		//7
		assertEquals(this.viewList.getRecentViewList().size(), this.listMaxSize);

		//8
		List<String> itemList = this.viewList.getRecentViewList(5);
		//9
		assertEquals("50", itemList.get(0));
		
		for(String item: itemList)	{
			//10
			System.out.println(item);
		}
	}
}
