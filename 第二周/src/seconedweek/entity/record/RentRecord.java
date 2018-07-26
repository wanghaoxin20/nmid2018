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
 * ���⳵���ļ�¼��
 */
public class RentRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1195540272133931542L;

	/**
	 * ����
	 *********************/
	private Map<Integer, Car> carinfo; // ������Ϣ

	private Map<Integer, Integer> Car_Amount; // ����id�ź����õ�����

	private Map<Integer, Integer> Car_RentTime; // ����id�źͶ�Ӧ���õ�ʱ��

	private String userName;// �⳵������

	private Date rentDate; // ������¼������

	private int totalManned; // һ����������

	private float totalCarry; // һ�����ػ���

	private float totalCars; // һ�����⳵����

	private float totalCost; // һ�����⳵��

	private int totalRentTime; // һ���⳵����

	/**
	 * ���췽��
	 */
	public RentRecord(String userName, Map<Integer, Car> carinfo) {
		this.userName = userName;
		this.carinfo = carinfo;
		Car_Amount = new TreeMap<>();
		Car_RentTime = new TreeMap<>();
	}

	/**
	 * �ô��⳵��Ϣ
	 */
	@Override
	public String toString() {
		StringBuffer mes = new StringBuffer();
		mes.append("����������:" + this.userName + "\n");
		mes.append("���\t\t��������\t\t��������\t\t����ʱ��\n");
		for (Map.Entry<Integer, Car> entry : carinfo.entrySet()) {
			mes.append(entry.getKey() + "\t\t" + entry.getValue().getCarName() + "\t\t" + Car_Amount.get(entry.getKey())
					+ "\t\t" + Car_RentTime.get(entry.getKey()) + "\n");
		}
		mes.append("�����⳵�����������:" + totalManned + "��\n");
		mes.append("�����⳵������ػ���:" + totalCarry + "��\n");
		mes.append("�����⳵����" + totalCost + "Ԫ\n");
		mes.append("�����⳵����" + totalRentTime + "��\n");
		mes.append("�����⳵����" + totalCars + "��\n");

		return mes.toString();
	}

	/**
	 * ����
	 ***********************************/
	public void makeTotal() {
		this.totalManned = 0;
		this.totalCarry = 0;
		this.totalCars = 0;
		this.totalCost = 0;
		for (Map.Entry<Integer, Car> entry : carinfo.entrySet()) { // ����������Ϣ
			int id = entry.getKey();
			Car car = entry.getValue();
			totalRentTime += Car_RentTime.get(id); // һ���⳵����
			totalCars += Car_Amount.get(id); // һ���⳵����
			totalCost += car.getRentFee() * Car_Amount.get(id) * Car_RentTime.get(id); // һ������
			switch (car.getCarType()) { // ���ݳ�������������ת����������н��� һ���ػ� һ������
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
	 * ����⳵��Ϣ id �������� ����ʱ��
	 *******************************/
	public void add(int id, int amount, int time) {
		this.Car_Amount.put(id, amount);
		this.Car_RentTime.put(id, time);
	}

	/**
	 * get��set����
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
