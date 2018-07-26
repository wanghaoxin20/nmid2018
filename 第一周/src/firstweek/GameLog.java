package firstweek;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ������Ϸ���������Ϣ��
 * */
public class GameLog implements Serializable,Comparable<GameLog>{
	private static int minguesstimes;//��С�²����
	
	private long starttime; //��ʼʱ��
	 
	private long endtime; //����ʱ��
	
	private int number; // �����ֵnumber
	
	private int guesstimes; // �µĴ���
	
	private int currentAreaStart; // ��ʼ��Χ
	
	private int currentAreaEnd; // ������Χ
	
	public GameLog(long starttime, long endtime, int number, int guesstimes, int currentAreaStart, int currentAreaEnd) {
		this.starttime = starttime;
		this.endtime = endtime;
		this.number = number;
		this.guesstimes = guesstimes;
		this.currentAreaStart = currentAreaStart;
		this.currentAreaEnd = currentAreaEnd;
	}
	
	//���򷽷�
	@Override
	public int compareTo(GameLog o) {
		return this.guesstimes - o.guesstimes;
	}
	
	@Override
	public String toString() {
		return "��ʼʱ��:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(starttime)) + "\n" +
				"����ʱ��:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(endtime)) + "\n" +
				"�������:" + number +"\n" +
				"�µĴ���:" + guesstimes + "\n" +
				"��ʼ��Χ:" + currentAreaStart + "\n" + 
				"������Χ:" + currentAreaEnd + "\n";
	}

	public static int getMinguesstimes() {
		return minguesstimes;
	}

	public long getStarttime() {
		return starttime;
	}

	public long getEndtime() {
		return endtime;
	}

	public int getNumber() {
		return number;
	}

	public int getGuesstimes() {
		return guesstimes;
	}

	public int getCurrentAreaStart() {
		return currentAreaStart;
	}

	public int getCurrentAreaEnd() {
		return currentAreaEnd;
	}
	
	
	
}
