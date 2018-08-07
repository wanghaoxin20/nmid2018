package thirdweek;

import java.util.Scanner;

public class Start {

	public static void main(String[] args) {
		Object borrowLock = new Object(); //借书锁
		BookStore bookStore = new BookStore(); //bookStore对象
		System.out.println("请输入每个线程个数");
		int len = input();
		System.out.println("请输入乱序次数");
		int doCount = input();
		System.out.println("请输入线程启动间隔时间(ms)");
		int time = input();
		
		System.out.println("\n借书线程:" + len);
		System.out.println("还书线程:" + len);
		System.out.println("乱序次数" + doCount);
		System.out.println("线程间隔休眠时间:" + time + "ms\n");

		Object[] threads = new Object[len * 2];
		
		for (int i = 0; i < len * 2; i += 2) {
			BookBorrow bookBorrow = new BookBorrow(borrowLock, bookStore, "借书线程" + i / 2);
			BookReturn bookReturn = new BookReturn(borrowLock, bookStore, "还书线程" + i / 2);
			threads[i] = bookBorrow;
			threads[i + 1] = bookReturn;
		}
		
		System.out.println("乱序中....\n");
		
		for (int i = 0; i < doCount; i++) { //使线程乱序,模拟随机借书还书
			threads = disorder(threads);
		}
		
		for (Object obj : threads) { //线程启动,间隔时间范围为time
			try {
				Thread.sleep((long)(Math.random() * (time + 1)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			((Thread) obj).start();
		}

	}

	/**
	 * 输入int值，输入不是int的值就默认返回20
	 */
	public static int input() {
		Scanner input = new Scanner(System.in);
		String value = input.nextLine();
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 20;
		}
	}

	/**
	 * 使线程乱序
	 */
	public static Object[] disorder(Object... objs) {
		int length = objs.length;
		for (int i = 0; i < length; i++) {
			int exchange1 = (int) (Math.random() * length);
			int exchange2 = (int) (Math.random() * length);
			Object temp = objs[exchange1];
			objs[exchange1] = objs[exchange2];
			objs[exchange2] = temp;
		}
		return objs;
	}

}
