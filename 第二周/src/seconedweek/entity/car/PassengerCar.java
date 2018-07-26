package seconedweek.entity.car;

/**
 * 载客车类
 */
public class PassengerCar extends Car {

	/**
	 * 属性
	 ******************************/
	private static final String CAR_TYPE = "PassengerCar";

	private int maxManned;// 最大载人数

	/**
	 * 构造方法
	 *********************************/
	public PassengerCar(int carID, String carName, float rentFee, int maxManned) {
		super(carID, carName, rentFee);
		setMaxManned(maxManned);
	}

	/**
	 * 重写toString()方法，用于输出车的信息
	 *********************************/
	@Override
	public String toString() {
		return getCarID() + "\t载客车\t" + "\t" + getCarName() + "\t\t" + getRentFee() + "\t\t" + getMaxManned();
	}

	@Override
	public String getCarType() {
		return this.CAR_TYPE;
	}

	/**
	 * get和set方法
	 **************************************/
	public int getMaxManned() {
		return maxManned;
	}

	public void setMaxManned(int maxManned) {
		this.maxManned = maxManned;
	}

}
