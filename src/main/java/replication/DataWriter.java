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

import redis.clients.jedis.Jedis;


/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 *
 * class desc
 */
public class DataWriter {
	private Jedis jedis;

	/**
	 * 데이터 저장을 위한 Writer 클래스 생성자
	 * @param jedis 데이터를 저장할 서버에 대한 제디스 연결
	 */
	public DataWriter(Jedis jedis) {
		this.jedis = jedis;

	}

	/**
	 * 주어진 키에 데이터를 저장한다.
	 * @param key 데이터 저장을 위한 레디스의 키
	 * @param value 저장될 데이터
	 */
	public void set(String key, String value) {
		this.jedis.set(key, value);
	}
}
