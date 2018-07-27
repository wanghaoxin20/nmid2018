package seconedweek.biz;

import java.io.File;
import java.sql.Date;
import java.util.Map;


import java.util.Scanner;
import java.util.TreeMap;

import seconedweek.entity.car.Car;
import seconedweek.entity.record.RentRecord;
import seconedweek.util.FileUtil;

public class CarRentSystem {
	private Map<Integer, Car> carinfo; // ������ж�ȡ����car����Ϣ
	private String choose;
	private Scanner input = new Scanner(System.in);

	/**
	 * ���췽��
	 **********************/
	public CarRentSystem() {
		init();
		systemStart();
	}

	/**
	 * ϵͳ��ʼ��
	 ********************/
	private void init() {
		if ((carinfo = getCars()) == null || carinfo.isEmpty()) {
			System.out.println("********ϵͳ��ʼ��ʧ�ܻ���Ϊ��!�˳�ϵͳ**********");
			System.exit(0);
		} else {
			System.out.println("**********ϵͳ��ʼ���ɹ�************");
			System.out.println("           ��ӭʹ��XXXX�⳵ϵͳ\n");
		}
	}

	/**
	 * ϵͳ����
	 */
	private void systemStart() {
		while (true) {
			showMenu();
			choose = input.nextLine();// ����ѡ��
			switch (choose) {
			case "1":
				System.out.println("������Ϣ����:");
				showCar();
				break;
			case "2":
				System.out.println("�⳵����");
				showCar();
				rentService();
				break;
			case "3":
				showRecord();
				break;
			case "#":
				System.exit(0);
				break;
			default:
				System.out.println("��������ȷ��ѡ��");
				break;
			}
		}
	}

	/**
	 * չʾ�˵�
	 *********************/
	public void showMenu() {
		System.out.println("            Menu");
		System.out.println("******************************");
		System.out.println("\t��ʾ������Ϣ(1)");
		System.out.println("\t�⳵����(2)");
		System.out.println("\t��ʾ�����⳵��Ϣ(3)");
		System.out.println("\t�˳�ϵͳ(#)");
		System.out.println("******************************\n");
	}

	/**
	 * չʾ���г�����Ϣ
	 *********************/
	public void showCar() {
		System.out.println("���\t��������\t\t��������\t\t�⳵����\t\t�������\\��");
		System.out.println("********************************************************************");
		for (Map.Entry<Integer, Car> entry : carinfo.entrySet()) {
			System.out.println(entry.getValue());
		}
		System.out.println("********************************************************************\n");
	}

	/**
	 * �⳵����
	 */
	public void rentService() {
		System.out.println("��������������");
		String usrName = input.nextLine();
		RentRecord record = new RentRecord(usrName, carinfo);

		for (Map.Entry<Integer, Car> entry : carinfo.entrySet()) {
			int renttime; // �⳵ʱ��
			int rentamount; // �⳵����
			int id = entry.getKey();
			Car car = entry.getValue();
			/**********************************************************/
			System.out.println("\n" + car);
			System.out.println("********************************************************************");
			System.out.println("���������" + id + "(" + car.getCarName() + ")����������");
			while (true) { // ֱ��������ȷ�ĸ�ʽ
				String value = input.nextLine();
				if (isNum(value)) { // ���������
					rentamount = Integer.parseInt(value);
					// System.out.println("ȷ������" + rentamount + "����?(ȷ��������Y/y)");
					// String ans = input.nextLine();
					// if(ans.toLowerCase().equals("y")) { //���ȷ��
					// break;
					// }else {
					// System.out.println("������������������");
					// }
					break;
				} else {
					System.out.println("�����������������");
				}
			}
			/***********************************************************/
			System.out.println("���������" + id + "(" + car.getCarName() + ")����������");
			while (true) { // ֱ��������ȷ�ĸ�ʽ
				String value = input.nextLine();
				if (isNum(value)) { // ���������
					renttime = Integer.parseInt(value);
					// System.out.println("ȷ������" + renttime + "����?(ȷ��������Y/y)");
					// String ans = input.nextLine();
					// if(ans.toLowerCase().equals("y")) { //���ȷ��
					// break;
					// }else {
					// System.out.println("������������������");
					// }
					break;
				} else {
					System.out.println("�����������������");
				}
			}
			record.add(id, rentamount, renttime);
		}
		record.makeTotal(); // ����
		System.out.println("¼����Ϣ���,��ǰ�⳵������:" + record.getTotalCars() + "��,�⳵������" + record.getTotalRentTime() + "��");
		System.out.println("��ȷ����Ϣ��ȷ��������Y/y");
		String ans = input.nextLine();
		if (ans.toLowerCase().equals("y")) {
			record.setRentDate(new Date(System.currentTimeMillis()));
			FileUtil.writeRecord(record);
			System.out.println("\n������Ϣ����\n" + record);// չʾ������Ϣ
			System.out.println("��ǰ�⳵�ɹ�,�����������");
		} else {
			System.out.println("��ȡ���˴˴��⳵,�����������");
		}
		input.nextLine();
	}

	/**
	 * չʾ���е��⳵��¼
	 */
	private void showRecord() {
		File file = new File("../rent_record");
		File[] files = file.listFiles();
		RentRecord r = null;
		for (File f : files) {
			r = FileUtil.read_record(f);
			System.out.println(r);
		}
	}

	/**
	 * �ж��Ƿ�������
	 */
	private boolean isNum(String value) {
		for (char c : value.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ��ȡ������Ϣ������ͼ�ķ�ʽ����
	 */
	private Map<Integer, Car> getCars() {
		return CarFactory.getAllCar();
	}

	public Map<Integer, Car> getCarinfo() {
		return carinfo;
	}

}
