package visitcount;
import static org.junit.Assert.*;
import java.util.List;

import helper.JedisHelper;
import org.junit.*;

public class VisitCountTest {
	static JedisHelper helper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//1
		helper = JedisHelper.getInstance(); 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//2
		helper.destroyPool();
	}

	@Test
	//3
	public void testAddVisit() { 
		VisitCount visitCount = new VisitCount(helper);
		//4
		assertNotNull(visitCount); 

		//5
		assertTrue(visitCount.addVisit("52") > 0); 
		assertTrue(visitCount.addVisit("180") > 0);
		assertTrue(visitCount.addVisit("554") > 0);
	}

	@Test
	//6
	public void testGetVisitCount() { 
		VisitCount visitCount = new VisitCount(helper); 
		assertNotNull(visitCount);

		//7
		List<String> result = visitCount.getVisitCount("52", "180", "554"); 
		assertNotNull(result);
		//8
		assertTrue(result.size() == 3); 

		long sum = 0;
		for (String count : result) {
			sum = sum + Long.parseLong(count);
		}
		String totalCount = visitCount.getVisitTotalCount();

		//9
		assertEquals(String.valueOf(sum), totalCount); 
	}
}
