package redislogger;

import java.text.SimpleDateFormat;
import java.util.Date;

import helper.JedisHelper;
import redis.clients.jedis.Jedis;

public class LogWriter {
	//1
	private static final String KEY_WAS_LOG = "was:log";
	//2
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS ");

	public JedisHelper helper;

	/**
	 * 레디스에 로그를 기록하기 위한 Logger의 생성자
	 * @param helper 제디스 도우미 객체
	 */
	public LogWriter(JedisHelper helper) {
		this.helper = helper;
	}

	/**
	 * 레디스에 로그를 기록하는 메서드. 
	 * @param log 저장할 로그문자열
	 * @return 저장된 후의 레디스에 저장된 로그 문자열의 길이.
	 */
	public Long log(String log) {
		//3
		Jedis jedis = this.helper.getConnection();
		//4
		Long rtn = jedis.append(KEY_WAS_LOG, sdf.format(new Date()) + log + "\n");

		this.helper.returnResource(jedis);

		return rtn;
	}
}
