package firstweek;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author 王昊鑫
 * 系统阶段开始时判断输入开始游戏或者结束游戏
 * 游戏阶段开始，先初始化，随机生成返回跨度为MINAREA-MINAREA的一个数字number
 * 每次输入数字后，如果没猜对,与number进行比较,根据大小提示响应信息
 * 猜对后，
 */
public class NumberGuess {
	private static final int MINAREA = 64; // 最小范围跨度64
	private static final int MAXAREA = 10000; // 最大范围跨度10000
	private static final String KEYWORD_START = "begin";
	private static final String KEYWORD_END = "shutdown";

	private Scanner input;

	private int number; // 当前随机数值number

	private int currentGuess; // 当前猜的数字

	private int guesstimes; // 已经猜的次数

	private int currentAreaStart; // 当前起始范围

	private int currentAreaEnd; // 当前结束范围

	public static void main(String[] args) {
		new NumberGuess().sysStage();
	}

	public NumberGuess() {
		this.input = new Scanner(System.in);
		sysStage();
	}

	/**
	 * 系统阶段
	 */
	private void sysStage() {
		System.out.println("*********     猜数字游戏        **********");
		System.out.println("-----------------------------------");
		System.out.println("----   游戏开始请输入:begin     ------");
		System.out.println("----   游戏结束请输入:shutdown  ------");
		System.out.println("-----------------------------------");
		String keyword;
		while (true) { //无限循环，直到输入的是结束游戏的关键词
			keyword = input.nextLine();
			System.out.println();
			if (keyword.equals(KEYWORD_START)) { // 游戏开始
				gameStage();
			} else if (keyword.equals(KEYWORD_END)) { // 游戏结束
				System.out.println("游戏结束!");
				System.exit(0);
			} else {
				System.out.println("输入错误,请重新输入");
			}
		}
	}

	/**
	 * 游戏阶段
	 */
	private void gameStage() {
		System.out.println("游戏初始化...");
		initValues(); // 初始化值
		System.out.println("游戏开始！");
		long starttime = System.currentTimeMillis();
		System.out.println("数字范围在" + currentAreaStart + "--" + currentAreaEnd + "之间");
		while (true) {
			System.out.println("请输入猜的数字");
			currentGuess = input.nextInt();
			if (!isNum(Integer.toString(currentGuess))) { // 输入不为数字
				System.out.println("输入错误,请输入一个数！");
			} else { // 输入为数字
				if (currentGuess < currentAreaStart || currentGuess > currentAreaEnd) { // 如果猜的数字不再正确范围内
					System.out.println("才输入正确的猜测范围！");
				} else {
					guesstimes++; // 猜测次数加1
					if (currentGuess != number) { // 如果没猜对
						if (currentGuess > number) { // 猜的数字比较大
							System.out.println("你猜的数字大了！");
						} else { // 猜的数字比较小
							System.out.println("你猜的数字小了！");
						}
					} else { // 猜对
						System.out.println("恭喜你猜对了!一共猜了" + guesstimes + "次");
						
						//记录log
						long endtime = System.currentTimeMillis();
						TreeSet<GameLog> loginfo = FileUtil.read_info("data");
						loginfo.add(new GameLog(starttime, endtime, number, guesstimes, currentAreaStart, currentAreaEnd));
						FileUtil.sava_log("data", loginfo);
						System.out.println("到目前为止猜的次数最少的为:" + loginfo.first().getGuesstimes() + "次");
						System.out.println("继续开始游戏请输入begin,退出游戏输入shutdown！");
						break;
					}
				}
			}
		}

	}
	
	/**
	 * 初始化各参数的值
	 */
	private void initValues() {
		currentAreaStart = getRandom(1000); // 猜数的起始范围
		currentAreaEnd = currentAreaStart + getRandom(MINAREA, MAXAREA); // 猜数的结束范围 跨度随机生成
		number = getRandom(currentAreaStart, currentAreaEnd); // 随机生成一个数 在currentAreaStart, currentAreaEnd之间
		guesstimes = 0; // 猜的次数初始化为0
	}

	/**
	 * 判断一个字符串是否为数字
	 */
	public boolean isNum(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 以当前时间毫秒数为种子,返回一个指定区间的随机数
	 * 
	 * @return random 返回的随机数
	 */
	private int getRandom(int start, int end) {
		int random;
		long t = System.currentTimeMillis();
		Random r = new Random(t);
		random = Math.abs(r.nextInt()) % (end - start + 1) + start;
		return random;
	}

	/**
	 * 重载 以当前时间毫秒数为种子,返回一随机数最大值为max
	 * 
	 * @return random 返回的随机数
	 */
	private int getRandom(int max) {
		int random;
		long t = System.currentTimeMillis();
		Random r = new Random(t);
		random = Math.abs(r.nextInt()) % (max + 1);
		return random;
	}

}
