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
public class DataReader {
	private Jedis jedis;

	/**
	 * 데이터 조회를 위한 Reader 클래스 생성자
	 * @param jedis 데이터를 조회할 서버에 대한 제디스 연결
	 */
	public DataReader(Jedis jedis) {
		this.jedis = jedis;
	}

	/**
	 * 주어진 키에 저장된 데이터를 조회한다
	 * @param key 데이터 조회를 위한 키
	 * @return 조회된 데이터. 만약 키가 존재하지 않으면 null 을 반환.
	 */
	public String get(String key) {
//		this.jedis.auth("password");
		return this.jedis.get(key);
	}
}
