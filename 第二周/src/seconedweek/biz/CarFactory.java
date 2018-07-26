package seconedweek.biz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import seconedweek.entity.car.Car;
import seconedweek.entity.car.CarryCar;
import seconedweek.entity.car.PassengerCar;
import seconedweek.entity.car.PickupCar;

public class CarFactory {

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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
