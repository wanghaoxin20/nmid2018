package firstweek;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 从文件中读取游戏记录或者存储游戏记录到文件
 * */
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class FileUtil implements Serializable {

	public static void main(String[] args) {
		displog();
	}

	/**
	 * 显示log
	 */
	public static void displog() {
		// 显示log
		for (GameLog g : read_info("data")) {
			System.out.println(g);
		}
	}

	/**
	 * 将游戏日志以图的形式存储到文件中
	 */
	public static boolean sava_log(String filepath, TreeSet<GameLog> loginfo) {
		File log = new File(filepath);
		try {
			ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(log)); // 构造输出流
			objout.writeObject(loginfo);
			objout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 将游戏日志以图的形式读出来
	 */
	@SuppressWarnings("unchecked")
	public static TreeSet<GameLog> read_info(String filepath) {
		TreeSet<GameLog> loginfo = null;
		File log = new File(filepath);
		try {
			if (!log.exists()) {
				log.createNewFile();
				return new TreeSet<GameLog>();
			}
			ObjectInputStream objin = new ObjectInputStream(new FileInputStream(log));
			loginfo = (TreeSet<GameLog>) objin.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return loginfo;
	}

}
