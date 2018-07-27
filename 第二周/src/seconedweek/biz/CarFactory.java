package seconedweek.biz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Map;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;

import seconedweek.entity.car.Car;
import seconedweek.entity.car.CarryCar;
import seconedweek.entity.car.PassengerCar;
import seconedweek.entity.car.PickupCar;

public class CarFactory {
	/**
	 * ������г����͵ļ���
	 */
	public static Map<Integer, Car> getAllCar() {
		Map<Integer, Car> carinfo = new TreeMap<>();
		// ͨ�������ļ���ø�id��������
		Collection<Object> cars = new HashSet<>();
		Properties props = new Properties();
		FileInputStream fin = null;
		try {
			fin = new FileInputStream("cars.properties");
			props.load(fin);
			cars = props.values();
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println("�����ļ�û�ҵ���");
			return null;
		} catch (IOException e) {
			System.out.println("��ȡ�����ļ�ʱio�쳣��");
			return null;
		}

		// ���ݵ�һ���ؼ���ʵ������ͬ�������� ����ӵ�carinfo��
		for (Object obj : cars) {
			String[] attribute = ((String) obj).split(",");
			switch (attribute[0]) {
			case "PassengerCar":
				carinfo.put(Integer.parseInt(attribute[1]), new PassengerCar(Integer.parseInt(attribute[1]),
						attribute[2], Float.parseFloat(attribute[3]), Integer.parseInt(attribute[4])));
				break;
			case "CarryCar":
				carinfo.put(Integer.parseInt(attribute[1]), new CarryCar(Integer.parseInt(attribute[1]), attribute[2],
						Float.parseFloat(attribute[3]), Float.parseFloat(attribute[4])));
				break;
			case "PickupCar":
				carinfo.put(Integer.parseInt(attribute[1]),
						new PickupCar(Integer.parseInt(attribute[1]), attribute[2], Float.parseFloat(attribute[3]),
								Integer.parseInt(attribute[4]), Float.parseFloat(attribute[5])));
				break;
			default:
				break;
			}
		}
		return carinfo;
	}

	/**
	 * ����id������Ӧ�ĳ�����
	 */
	public static Car getCar(int id) {
		// ͨ�������ļ���ø�id��������
		Properties props = new Properties();
		FileInputStream fin = null;
		String value = null;
		try {
			fin = new FileInputStream("cars.properties");
			props.load(fin);
			value = props.getProperty("id" + id);
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println("�����ļ�û�ҵ���");
			return null;
		} catch (IOException e) {
			System.out.println("��ȡ�����ļ�ʱio�쳣��");
			return null;
		}

		if (value == null) {
			return null;
		}
		// ���ݵ�һ���ؼ���ʵ������ͬ��������
		String[] attribute = value.split(",");
		switch (attribute[0]) {
		case "PassengerCar":
			return new PassengerCar(id, attribute[1], Float.parseFloat(attribute[2]), Integer.parseInt(attribute[3]));
		case "CarryCar":
			return new CarryCar(id, attribute[1], Float.parseFloat(attribute[2]), Float.parseFloat(attribute[3]));
		case "PickupCar":
			return new PickupCar(id, attribute[1], Float.parseFloat(attribute[2]), Integer.parseInt(attribute[3]),
					Float.parseFloat(attribute[4]));
		default:
			return null;
		}

	}
}
