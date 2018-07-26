package seconedweek.entity.record;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;
import java.util.TreeMap;

import seconedweek.entity.car.Car;
import seconedweek.entity.car.CarryCar;
import seconedweek.entity.car.PassengerCar;
import seconedweek.entity.car.PickupCar;

/**
 * 出租车辆的记录类
 */
public class RentRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1195540272133931542L;

	/**
	 * 属性
	 *********************/
	private Map<Integer, Car> carinfo; // 车辆信息

	private Map<Integer, Integer> Car_Amount; // 车的id号和租用的辆数

	private Map<Integer, Integer> Car_RentTime; // 车的id号和对应租用的时间

	private String userName;// 租车人姓名

	private Date rentDate; // 该条记录的日期

	private int totalManned; // 一共的载人量

	private float totalCarry; // 一共的载货量

	private float totalCars; // 一共的租车数量

	private float totalCost; // 一共的租车费

	private int totalRentTime; // 一共租车天数

	/**
	 * 构造方法
	 */
	public RentRecord(String userName, Map<Integer, Car> carinfo) {
		this.userName = userName;
		this.carinfo = carinfo;
		Car_Amount = new TreeMap<>();
		Car_RentTime = new TreeMap<>();
	}

	/**
	 * 该次租车信息
	 */
	@Override
	public String toString() {
		StringBuffer mes = new StringBuffer();
		mes.append("租用人姓名:" + this.userName + "\n");
		mes.append("序号\t\t车辆名称\t\t租用辆数\t\t租用时间\n");
		for (Map.Entry<Integer, Car> entry : carinfo.entrySet()) {
			mes.append(entry.getKey() + "\t\t" + entry.getValue().getCarName() + "\t\t" + Car_Amount.get(entry.getKey())
					+ "\t\t" + Car_RentTime.get(entry.getKey()) + "\n");
		}
		mes.append("总体租车的最大载人量:" + totalManned + "人\n");
		mes.append("总体租车的最大载货量:" + totalCarry + "吨\n");
		mes.append("总体租车费用" + totalCost + "元\n");
		mes.append("总体租车天数" + totalRentTime + "天\n");
		mes.append("总体租车辆数" + totalCars + "辆\n");

		return mes.toString();
	}

	/**
	 * 结算
	 ***********************************/
	public void makeTotal() {
		this.totalManned = 0;
		this.totalCarry = 0;
		this.totalCars = 0;
		this.totalCost = 0;
		for (Map.Entry<Integer, Car> entry : carinfo.entrySet()) { // 遍历车辆信息
			int id = entry.getKey();
			Car car = entry.getValue();
			totalRentTime += Car_RentTime.get(id); // 一共租车天数
			totalCars += Car_Amount.get(id); // 一共租车数量
			totalCost += car.getRentFee() * Car_Amount.get(id) * Car_RentTime.get(id); // 一共花费
			switch (car.getCarType()) { // 根据车辆类型来向下转换成子类进行结算 一共载货 一共载人
			case "PassengerCar":
				totalManned += ((PassengerCar) car).getMaxManned() * Car_Amount.get(id);
				break;
			case "CarryCar":
				totalCarry += ((CarryCar) car).getMaxCarry() * Car_Amount.get(id);
				break;
			case "PickupCar":
				totalCarry += ((PickupCar) car).getMaxCarry() * Car_Amount.get(id);
				totalManned += ((PickupCar) car).getMaxManned() * Car_Amount.get(id);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 添加租车信息 id 租用辆数 租用时间
	 *******************************/
	public void add(int id, int amount, int time) {
		this.Car_Amount.put(id, amount);
		this.Car_RentTime.put(id, time);
	}

	/**
	 * get和set方法
	 **************/
	public int getTotalManned() {
		return totalManned;
	}

	public float getTotalCarry() {
		return totalCarry;
	}

	public float getTotalCars() {
		return totalCars;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public int getTotalRentTime() {
		return totalRentTime;
	}

	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

}
