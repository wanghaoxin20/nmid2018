package seconedweek.entity.car;

/**
 * 载货车
 */
public class CarryCar extends Car {

	/**
	 * 属性
	 ******************************/
	public static final String CAR_TYPE = "CarryCar";

	private float maxCarry; // 最大载货

	/**
	 * 构造方法
	 *******************************/
	public CarryCar(int carID, String carName, float rentFee, float maxCarry) {
		super(carID, carName, rentFee);
		setMaxCarry(maxCarry);
	}

	/**
	 * 重写toString()方法，用于输出车的信息
	 *********************************/
	@Override
	public String toString() {
		return getCarID() + "\t载货车\t" + "\t" + getCarName() + "\t\t" + getRentFee() + "\t\t" + getMaxCarry();
	}

	@Override
	public String getCarType() {
		return this.CAR_TYPE;
	}

	/**
	 * get和set方法
	 *********************************/
	public float getMaxCarry() {
		return maxCarry;
	}

	public void setMaxCarry(float maxCarry) {
		this.maxCarry = maxCarry;
	}

}
