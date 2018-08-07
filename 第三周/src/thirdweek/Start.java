package thirdweek;

import java.util.Scanner;

public class Start {

	public static void main(String[] args) {
		Object borrowLock = new Object(); //������
		BookStore bookStore = new BookStore(); //bookStore����
		System.out.println("������ÿ���̸߳���");
		int len = input();
		System.out.println("�������������");
		int doCount = input();
		System.out.println("�������߳��������ʱ��(ms)");
		int time = input();
		
		System.out.println("\n�����߳�:" + len);
		System.out.println("�����߳�:" + len);
		System.out.println("�������" + doCount);
		System.out.println("�̼߳������ʱ��:" + time + "ms\n");

		Object[] threads = new Object[len * 2];
		
		for (int i = 0; i < len * 2; i += 2) {
			BookBorrow bookBorrow = new BookBorrow(borrowLock, bookStore, "�����߳�" + i / 2);
			BookReturn bookReturn = new BookReturn(borrowLock, bookStore, "�����߳�" + i / 2);
			threads[i] = bookBorrow;
			threads[i + 1] = bookReturn;
		}
		
		System.out.println("������....\n");
		
		for (int i = 0; i < doCount; i++) { //ʹ�߳�����,ģ��������黹��
			threads = disorder(threads);
		}
		
		for (Object obj : threads) { //�߳�����,���ʱ�䷶ΧΪtime
			try {
				Thread.sleep((long)(Math.random() * (time + 1)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			((Thread) obj).start();
		}

	}

	/**
	 * ����intֵ�����벻��int��ֵ��Ĭ�Ϸ���20
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
	 * ʹ�߳�����
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
