package recentview;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import helper.JedisHelper;
import redis.clients.jedis.Jedis;


public class RecentViewListV2 {
	private Jedis jedis;

	private static final String KEY_VIEW_SET = "recent:view:zset:";

	private static final int listMaxSize = 20;

	private String key;

	public RecentViewListV2(JedisHelper helper, String userNo) {
		this.jedis = helper.getConnection();
		//1
		this.key = KEY_VIEW_SET + userNo;
	}

	/**
	 * 최근 조회 상품 목록에 상품을 추가한다.
	 * @param productNo 상품 번호
	 * @return 저장된 상품 목록의 개수
	 */
	public Long add(String productNo) {
		//2
		Long result = jedis.zadd(this.key, System.nanoTime(), productNo);
		//3
		jedis.zremrangeByRank(this.key, -(listMaxSize + 1), -(listMaxSize + 1));

		return result;
	}

	/**
	 * 주어진 사용자의 저장된 최근 조회 상품목록을 조회한다.
	 * @return 조회된 상품목록
	 */
	public Set<String> getRecentViewList() {
		//4
//		return (Set<String>) jedis.zrevrange(this.key, 0, -1);
		return new LinkedHashSet<>(jedis.zrevrange(this.key, 0, -1));
	}

	/**
	 * 주어진 개수에 해당하는 최근 조회 상품목록을 조회한다.
	 * @param cnt 조회할 상품의 개수
	 * @return 조회된 상품목록
	 */
	public Set<String> getRecentViewList(int cnt) {
		//5
//		return (Set<String>) jedis.zrevrange(this.key, 0, cnt - 1);
		return new LinkedHashSet<>(jedis.zrevrange(this.key, 0, cnt - 1));
	}

	/**
	 * 저장 가능한 상품 목록의 최대 개수를 조회한다.
	 * @return 상품 목록의 최대 개수
	 */
	public int getListMaxSize() {
		return listMaxSize;
	}
}
