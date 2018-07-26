package firstweek;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 单局游戏结束后的信息类
 * */
public class GameLog implements Serializable,Comparable<GameLog>{
	private static int minguesstimes;//最小猜测次数
	
	private long starttime; //起始时间
	 
	private long endtime; //结束时间
	
	private int number; // 随机数值number
	
	private int guesstimes; // 猜的次数
	
	private int currentAreaStart; // 起始范围
	
	private int currentAreaEnd; // 结束范围
	
	public GameLog(long starttime, long endtime, int number, int guesstimes, int currentAreaStart, int currentAreaEnd) {
		this.starttime = starttime;
		this.endtime = endtime;
		this.number = number;
		this.guesstimes = guesstimes;
		this.currentAreaStart = currentAreaStart;
		this.currentAreaEnd = currentAreaEnd;
	}
	
	//排序方法
	@Override
	public int compareTo(GameLog o) {
		return this.guesstimes - o.guesstimes;
	}
	
	@Override
	public String toString() {
		return "开始时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(starttime)) + "\n" +
				"结束时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(endtime)) + "\n" +
				"随机数字:" + number +"\n" +
				"猜的次数:" + guesstimes + "\n" +
				"起始范围:" + currentAreaStart + "\n" + 
				"结束范围:" + currentAreaEnd + "\n";
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
