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
 * ���ļ��ж�ȡ��Ϸ��¼���ߴ洢��Ϸ��¼���ļ�
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
	 * ��ʾlog
	 */
	public static void displog() {
		// ��ʾlog
		for (GameLog g : read_info("data")) {
			System.out.println(g);
		}
	}

	/**
	 * ����Ϸ��־��ͼ����ʽ�洢���ļ���
	 */
	public static boolean sava_log(String filepath, TreeSet<GameLog> loginfo) {
		File log = new File(filepath);
		try {
			ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(log)); // ���������
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
	 * ����Ϸ��־��ͼ����ʽ������
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
