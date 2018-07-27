package seconedweek.entity.car;

public class PickupCar extends Car {

	/**
	 * 属性
	 *****************/
	public static final String CAR_TYPE = "PickupCar";

	private int maxManned; // 最大载人数

	private float maxCarry; // 最大载货

	/**
	 * 构造方法
	 **********************/
	public PickupCar(int carID, String carName, float rentFee, int maxManned, float maxCarry) {
		super(carID, carName, rentFee);
		setMaxManned(maxManned);
		setMaxCarry(maxCarry);
	}

	/**
	 * 重写toString()方法，用于输出车的信息
	 *********************************/
	@Override
	public String toString() {
		return getCarID() + "\t皮卡车\t" + "\t" + getCarName() + "\t\t" + getRentFee() + "\t\t" + getMaxManned() + "\t"
				+ getMaxCarry();
	}

	@Override
	public String getCarType() {
		return this.CAR_TYPE;
	}

	/**
	 * get和set方法
	 *************************/
	public int getMaxManned() {
		return maxManned;
	}

	public void setMaxManned(int maxManned) {
		this.maxManned = maxManned;
	}

	public float getMaxCarry() {
		return maxCarry;
	}

	public void setMaxCarry(float maxCarry) {
		this.maxCarry = maxCarry;
	}

}
