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
	 * 获得所有车类型的集合
	 */
	public static Map<Integer, Car> getAllCar() {
		Map<Integer, Car> carinfo = new TreeMap<>();
		// 通过配置文件获得各id车的数据
		Collection<Object> cars = new HashSet<>();
		Properties props = new Properties();
		FileInputStream fin = null;
		try {
			fin = new FileInputStream("cars.properties");
			props.load(fin);
			cars = props.values();
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println("配置文件没找到！");
			return null;
		} catch (IOException e) {
			System.out.println("读取配置文件时io异常！");
			return null;
		}

		// 根据第一个关键词实例化不同车的类型 并添加到carinfo中
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
	 * 根据id返回相应的车类型
	 */
	public static Car getCar(int id) {
		// 通过配置文件获得各id车的数据
		Properties props = new Properties();
		FileInputStream fin = null;
		String value = null;
		try {
			fin = new FileInputStream("cars.properties");
			props.load(fin);
			value = props.getProperty("id" + id);
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println("配置文件没找到！");
			return null;
		} catch (IOException e) {
			System.out.println("读取配置文件时io异常！");
			return null;
		}

		if (value == null) {
			return null;
		}
		// 根据第一个关键词实例化不同车的类型
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
