package seconedweek.entity.car;

/**
 * �ػ���
 */
public class CarryCar extends Car {

	/**
	 * ����
	 ******************************/
	public static final String CAR_TYPE = "CarryCar";

	private float maxCarry; // ����ػ�

	/**
	 * ���췽��
	 *******************************/
	public CarryCar(int carID, String carName, float rentFee, float maxCarry) {
		super(carID, carName, rentFee);
		setMaxCarry(maxCarry);
	}

	/**
	 * ��дtoString()�������������������Ϣ
	 *********************************/
	@Override
	public String toString() {
		return getCarID() + "\t�ػ���\t" + "\t" + getCarName() + "\t\t" + getRentFee() + "\t\t" + getMaxCarry();
	}

	@Override
	public String getCarType() {
		return this.CAR_TYPE;
	}

	/**
	 * get��set����
	 *********************************/
	public float getMaxCarry() {
		return maxCarry;
	}

	public void setMaxCarry(float maxCarry) {
		this.maxCarry = maxCarry;
	}

}
