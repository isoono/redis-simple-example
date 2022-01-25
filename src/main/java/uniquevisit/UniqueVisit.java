package uniquevisit;

import java.text.SimpleDateFormat;
import java.util.Date;

import helper.JedisHelper;
import redis.clients.jedis.*;

public class UniqueVisit {
	private Jedis jedis;
	//1
	private static final String KEY_PAGE_VIEW = "page:view:";
	//2
	private static final String KEY_UNIQUE_VISITOR = "unique:visitors:";

	public UniqueVisit(JedisHelper helper) {
		this.jedis = helper.getConnection();
	}

	/**
	 * 특정 사용자의 순 방문횟수와 누적 방문횟수를 증가 시킨다.
	 * @param userNo 사용자번호
	 */
	public void visit(int userNo) {
		//3
		Pipeline p = this.jedis.pipelined();
		p.incr(KEY_PAGE_VIEW + getToday());
		//4
		p.setbit(KEY_UNIQUE_VISITOR + getToday(), userNo, true);
		p.sync();
	}

	/**
	 * 요청된 날자의 누적 방문자수를 조회한다.
	 * @param date 조회대상 날자
	 * @return 누적 방문자수
	 */
	public int getPVCount(String date) {
		int result = 0;
		try {
			//5
			result = Integer.parseInt(jedis.get(KEY_PAGE_VIEW + date));
		}
		catch (Exception e) {
			result = 0;
		}
		return result;
	}

	/**
	 * 요청된 날자의 순 방문자수를 조회한다.
	 * @param date 조회대상 날자
	 * @return 순 방문자수
	 */
	public Long getUVCount(String date) {
		//6
		return jedis.bitcount(KEY_UNIQUE_VISITOR + date);
	}

	private String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
}
