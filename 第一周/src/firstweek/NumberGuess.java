package firstweek;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author �����
 * ϵͳ�׶ο�ʼʱ�ж����뿪ʼ��Ϸ���߽�����Ϸ
 * ��Ϸ�׶ο�ʼ���ȳ�ʼ����������ɷ��ؿ��ΪMINAREA-MINAREA��һ������number
 * ÿ���������ֺ����û�¶�,��number���бȽ�,���ݴ�С��ʾ��Ӧ��Ϣ
 * �¶Ժ�
 */
public class NumberGuess {
	private static final int MINAREA = 64; // ��С��Χ���64
	private static final int MAXAREA = 10000; // ���Χ���10000
	private static final String KEYWORD_START = "begin";
	private static final String KEYWORD_END = "shutdown";

	private Scanner input;

	private int number; // ��ǰ�����ֵnumber

	private int currentGuess; // ��ǰ�µ�����

	private int guesstimes; // �Ѿ��µĴ���

	private int currentAreaStart; // ��ǰ��ʼ��Χ

	private int currentAreaEnd; // ��ǰ������Χ

	public static void main(String[] args) {
		new NumberGuess().sysStage();
	}

	public NumberGuess() {
		this.input = new Scanner(System.in);
		sysStage();
	}

	/**
	 * ϵͳ�׶�
	 */
	private void sysStage() {
		System.out.println("*********     ��������Ϸ        **********");
		System.out.println("-----------------------------------");
		System.out.println("----   ��Ϸ��ʼ������:begin     ------");
		System.out.println("----   ��Ϸ����������:shutdown  ------");
		System.out.println("-----------------------------------");
		String keyword;
		while (true) { //����ѭ����ֱ��������ǽ�����Ϸ�Ĺؼ���
			keyword = input.nextLine();
			System.out.println();
			if (keyword.equals(KEYWORD_START)) { // ��Ϸ��ʼ
				gameStage();
			} else if (keyword.equals(KEYWORD_END)) { // ��Ϸ����
				System.out.println("��Ϸ����!");
				System.exit(0);
			} else {
				System.out.println("�������,����������");
			}
		}
	}

	/**
	 * ��Ϸ�׶�
	 */
	private void gameStage() {
		System.out.println("��Ϸ��ʼ��...");
		initValues(); // ��ʼ��ֵ
		System.out.println("��Ϸ��ʼ��");
		long starttime = System.currentTimeMillis();
		System.out.println("���ַ�Χ��" + currentAreaStart + "--" + currentAreaEnd + "֮��");
		while (true) {
			System.out.println("������µ�����");
			currentGuess = input.nextInt();
			if (!isNum(Integer.toString(currentGuess))) { // ���벻Ϊ����
				System.out.println("�������,������һ������");
			} else { // ����Ϊ����
				if (currentGuess < currentAreaStart || currentGuess > currentAreaEnd) { // ����µ����ֲ�����ȷ��Χ��
					System.out.println("��������ȷ�Ĳ²ⷶΧ��");
				} else {
					guesstimes++; // �²������1
					if (currentGuess != number) { // ���û�¶�
						if (currentGuess > number) { // �µ����ֱȽϴ�
							System.out.println("��µ����ִ��ˣ�");
						} else { // �µ����ֱȽ�С
							System.out.println("��µ�����С�ˣ�");
						}
					} else { // �¶�
						System.out.println("��ϲ��¶���!һ������" + guesstimes + "��");
						
						//��¼log
						long endtime = System.currentTimeMillis();
						TreeSet<GameLog> loginfo = FileUtil.read_info("data");
						loginfo.add(new GameLog(starttime, endtime, number, guesstimes, currentAreaStart, currentAreaEnd));
						FileUtil.sava_log("data", loginfo);
						System.out.println("��ĿǰΪֹ�µĴ������ٵ�Ϊ:" + loginfo.first().getGuesstimes() + "��");
						System.out.println("������ʼ��Ϸ������begin,�˳���Ϸ����shutdown��");
						break;
					}
				}
			}
		}

	}
	
	/**
	 * ��ʼ����������ֵ
	 */
	private void initValues() {
		currentAreaStart = getRandom(1000); // ��������ʼ��Χ
		currentAreaEnd = currentAreaStart + getRandom(MINAREA, MAXAREA); // �����Ľ�����Χ ����������
		number = getRandom(currentAreaStart, currentAreaEnd); // �������һ���� ��currentAreaStart, currentAreaEnd֮��
		guesstimes = 0; // �µĴ�����ʼ��Ϊ0
	}

	/**
	 * �ж�һ���ַ����Ƿ�Ϊ����
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
	 * �Ե�ǰʱ�������Ϊ����,����һ��ָ������������
	 * 
	 * @return random ���ص������
	 */
	private int getRandom(int start, int end) {
		int random;
		long t = System.currentTimeMillis();
		Random r = new Random(t);
		random = Math.abs(r.nextInt()) % (end - start + 1) + start;
		return random;
	}

	/**
	 * ���� �Ե�ǰʱ�������Ϊ����,����һ��������ֵΪmax
	 * 
	 * @return random ���ص������
	 */
	private int getRandom(int max) {
		int random;
		long t = System.currentTimeMillis();
		Random r = new Random(t);
		random = Math.abs(r.nextInt()) % (max + 1);
		return random;
	}

}
