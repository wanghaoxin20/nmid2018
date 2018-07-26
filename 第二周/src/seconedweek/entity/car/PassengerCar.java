package seconedweek.entity.car;

/**
 * �ؿͳ���
 */
public class PassengerCar extends Car {

	/**
	 * ����
	 ******************************/
	private static final String CAR_TYPE = "PassengerCar";

	private int maxManned;// ���������

	/**
	 * ���췽��
	 *********************************/
	public PassengerCar(int carID, String carName, float rentFee, int maxManned) {
		super(carID, carName, rentFee);
		setMaxManned(maxManned);
	}

	/**
	 * ��дtoString()�������������������Ϣ
	 *********************************/
	@Override
	public String toString() {
		return getCarID() + "\t�ؿͳ�\t" + "\t" + getCarName() + "\t\t" + getRentFee() + "\t\t" + getMaxManned();
	}

	@Override
	public String getCarType() {
		return this.CAR_TYPE;
	}

	/**
	 * get��set����
	 **************************************/
	public int getMaxManned() {
		return maxManned;
	}

	public void setMaxManned(int maxManned) {
		this.maxManned = maxManned;
	}

}
