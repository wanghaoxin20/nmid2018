package seconedweek.entity.car;

import java.io.Serializable;

/**
 * 所有车的父类 拥有基础属性 carID name rentFee
 */
public class Car implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4939025393292946193L;

	/**
	 * 属性
	 *************************/

	private int carID; // 车序号

	private String carName; // 车辆名称

	private float rentFee; // 租车费用

	/*****************************/

	/**
	 * 构造方法
	 */
	public Car(int carID, String carName, float rentFee) {
		setCarID(carID);
		setCarName(carName);
		setRentFee(rentFee);
	}

	/**
	 * 获取该车的类型
	 */
	public String getCarType() {
		return null;
	}

	/**
	 * get和set方法
	 *****************************/
	public int getCarID() {
		return carID;
	}

	public void setCarID(int carID) {
		this.carID = carID;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public float getRentFee() {
		return rentFee;
	}

	public void setRentFee(float rentFee) {
		this.rentFee = rentFee;
	}

	/***********************************/

}
