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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redislogger.KeyMaker;

import java.util.Arrays;


/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 * <p>
 * class desc
 */
public class ReplicationTest {
    private static final int TEST_COUNT = 100000;

    static Jedis master;

    static Jedis slave;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        //1
//        master = new Jedis("192.168.10.252", 6300);
        master = new Jedis("127.0.0.1", 6300);
        //2
//        slave = new Jedis("192.168.10.252", 6301);
        slave = new Jedis("127.0.0.1", 6301);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        master.disconnect();
        slave.disconnect();
    }

    @Test
    public void replicationTest() {
        DataWriter writer = new DataWriter(master);
        DataReader reader = new DataReader(slave);

        for (int i = 0; i < TEST_COUNT; i++) {
            KeyMaker keyMaker = new ReplicationKeyMaker(i);
            String value = "test value" + i;
            writer.set(keyMaker.getKey(), value);

            for (int j = 0; j < 3; j++) {
                String result = reader.get(keyMaker.getKey());

                if (value.equals(result)) {
                    //3
                    // success case
                } else {
                    //4
                    fail("The value Not match with a result. [" + value + "][" + result + "]");
                }
            }
        }
    }
}
