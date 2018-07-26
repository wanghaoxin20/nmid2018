package seconedweek.entity.car;

import java.io.Serializable;

/**
 * ���г��ĸ��� ӵ�л������� carID name rentFee
 */
public class Car implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4939025393292946193L;

	/**
	 * ����
	 *************************/

	private int carID; // �����

	private String carName; // ��������

	private float rentFee; // �⳵����

	/*****************************/

	/**
	 * ���췽��
	 */
	public Car(int carID, String carName, float rentFee) {
		setCarID(carID);
		setCarName(carName);
		setRentFee(rentFee);
	}

	/**
	 * ��ȡ�ó�������
	 */
	public String getCarType() {
		return null;
	}

	/**
	 * get��set����
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
