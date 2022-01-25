package redislogger;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import helper.JedisHelper;
import redis.clients.jedis.Jedis;

public class LogReceiver {
	private static final JedisHelper helper = JedisHelper.getInstance();
	//1
	private static final String KEY_WAS_LOG = "was:log";
	//2
	private static final String LOG_FILE_NAME_PREFIX = "./waslog";
	//3
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH'.log'");
	//4
	private static int WAITING_TERM = 100;

	/**
	 * 레디스 서버에서 로그를 읽어서 파일로 저장한다. 
	 * 프로그램이 종료되기 전까지 무한히 실행한다.
	 */
	public void start() {
		Random random = new Random();
		Jedis jedis = helper.getConnection();
		//5
		while (true) {
			//6
			writeFile(jedis.getSet(KEY_WAS_LOG, ""));

			try {
				//7
				Thread.sleep(random.nextInt(WAITING_TERM));
			}
			catch (InterruptedException e) {
				// do nothing.
			}
		}
	}

	/**
	 * 메서드가 호출된 시간에 해당하는 로그파일명을 생성한다.
	 * @return 현재 시간에 해당하는 로그파일명
	 */
	//8
	public String getCurrentFileName() {
		return LOG_FILE_NAME_PREFIX + sdf.format(new Date());
	}

	private void writeFile(String log) {
		try {
			FileWriter writer = new FileWriter(getCurrentFileName(), true);

			//9
			writer.write(log);
			writer.flush();
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
