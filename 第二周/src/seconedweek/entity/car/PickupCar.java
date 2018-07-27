package seconedweek.entity.car;

public class PickupCar extends Car {

	/**
	 * ����
	 *****************/
	public static final String CAR_TYPE = "PickupCar";

	private int maxManned; // ���������

	private float maxCarry; // ����ػ�

	/**
	 * ���췽��
	 **********************/
	public PickupCar(int carID, String carName, float rentFee, int maxManned, float maxCarry) {
		super(carID, carName, rentFee);
		setMaxManned(maxManned);
		setMaxCarry(maxCarry);
	}

	/**
	 * ��дtoString()�������������������Ϣ
	 *********************************/
	@Override
	public String toString() {
		return getCarID() + "\tƤ����\t" + "\t" + getCarName() + "\t\t" + getRentFee() + "\t\t" + getMaxManned() + "\t"
				+ getMaxCarry();
	}

	@Override
	public String getCarType() {
		return this.CAR_TYPE;
	}

	/**
	 * get��set����
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
