/******************************************************
 * author : Kris Jeong
 * published : 2013. 6. 15.
 * project name : redis-book
 *
 * Email : smufu@naver.com, smufu1@hotmail.com
 *
 * Develop JDK Ver. 1.6.0_13
 *
 * Issue list.
 *
 * 	function.
 *     1.
 *
 ********** process *********
 *
 ********** edited **********
 *
 ******************************************************/
package replication;

import static org.junit.Assert.*;

import helper.ShardedJedisHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.ShardedJedis;


/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 *
 * class desc
 */
public class ShardingTest {
	private static final int TEST_COUNT = 500;

	static ShardedJedisHelper helper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		helper = ShardedJedisHelper.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		helper.destoryPool();
	}

	@Test
	public void testSharding() {
		ShardedJedis jedis = helper.getConnection();

		for (int i = 0; i < TEST_COUNT; i++) {
			String testValue = "Test Value " + i;
			ShardTestKeyMaker keyMaker = new ShardTestKeyMaker(i);
			jedis.set(keyMaker.getKey(), testValue);
			assertEquals(testValue, jedis.get(keyMaker.getKey()));
		}
	}
}
