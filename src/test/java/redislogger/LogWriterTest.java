package redislogger;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import helper.JedisHelper;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogWriterTest {
	static JedisHelper helper;
	static LogWriter logger;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// 1
		helper = JedisHelper.getInstance();
		// 2
		logger = new LogWriter(helper);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// 3
		helper.destroyPool();
	}

	@Test
	public void testLogger() {
		Random random = new Random(System.currentTimeMillis());
		// 4
		for (int i = 0; i < 100; i++) {
			// 5
			assertTrue(logger.log(i + ",This is test log message") > 0);

			try {
				// 6
				Thread.sleep(random.nextInt(50));
			}
			catch (InterruptedException e) {
				// do nothing.
			}
		}
	}
}
