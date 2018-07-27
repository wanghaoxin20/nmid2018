package seconedweek.biz;

import java.io.File;
import java.sql.Date;
import java.util.Map;


import java.util.Scanner;
import java.util.TreeMap;

import seconedweek.entity.car.Car;
import seconedweek.entity.record.RentRecord;
import seconedweek.util.FileUtil;

public class CarRentSystem {
	private Map<Integer, Car> carinfo; // 存放所有读取到的car的信息
	private String choose;
	private Scanner input = new Scanner(System.in);

	/**
	 * 构造方法
	 **********************/
	public CarRentSystem() {
		init();
		systemStart();
	}

	/**
	 * 系统初始化
	 ********************/
	private void init() {
		if ((carinfo = getCars()) == null || carinfo.isEmpty()) {
			System.out.println("********系统初始化失败或者为空!退出系统**********");
			System.exit(0);
		} else {
			System.out.println("**********系统初始化成功************");
			System.out.println("           欢迎使用XXXX租车系统\n");
		}
	}

	/**
	 * 系统运行
	 */
	private void systemStart() {
		while (true) {
			showMenu();
			choose = input.nextLine();// 输入选择
			switch (choose) {
			case "1":
				System.out.println("车辆信息如下:");
				showCar();
				break;
			case "2":
				System.out.println("租车服务");
				showCar();
				rentService();
				break;
			case "3":
				showRecord();
				break;
			case "#":
				System.exit(0);
				break;
			default:
				System.out.println("请输入正确的选择！");
				break;
			}
		}
	}

	/**
	 * 展示菜单
	 *********************/
	public void showMenu() {
		System.out.println("            Menu");
		System.out.println("******************************");
		System.out.println("\t显示车辆信息(1)");
		System.out.println("\t租车服务(2)");
		System.out.println("\t显示所有租车信息(3)");
		System.out.println("\t退出系统(#)");
		System.out.println("******************************\n");
	}

	/**
	 * 展示所有车辆信息
	 *********************/
	public void showCar() {
		System.out.println("序号\t车辆类型\t\t车辆名称\t\t租车费用\t\t最大载人\\货");
		System.out.println("********************************************************************");
		for (Map.Entry<Integer, Car> entry : carinfo.entrySet()) {
			System.out.println(entry.getValue());
		}
		System.out.println("********************************************************************\n");
	}

	/**
	 * 租车服务
	 */
	public void rentService() {
		System.out.println("请输入您的姓名");
		String usrName = input.nextLine();
		RentRecord record = new RentRecord(usrName, carinfo);

		for (Map.Entry<Integer, Car> entry : carinfo.entrySet()) {
			int renttime; // 租车时间
			int rentamount; // 租车数量
			int id = entry.getKey();
			Car car = entry.getValue();
			/**********************************************************/
			System.out.println("\n" + car);
			System.out.println("********************************************************************");
			System.out.println("请输入序号" + id + "(" + car.getCarName() + ")的租用数量");
			while (true) { // 直到输入正确的格式
				String value = input.nextLine();
				if (isNum(value)) { // 如果是数字
					rentamount = Integer.parseInt(value);
					// System.out.println("确定租用" + rentamount + "辆吗?(确定请输入Y/y)");
					// String ans = input.nextLine();
					// if(ans.toLowerCase().equals("y")) { //如果确定
					// break;
					// }else {
					// System.out.println("请重新输入租用数量");
					// }
					break;
				} else {
					System.out.println("输入错误！请重新输入");
				}
			}
			/***********************************************************/
			System.out.println("请输入序号" + id + "(" + car.getCarName() + ")的租用天数");
			while (true) { // 直到输入正确的格式
				String value = input.nextLine();
				if (isNum(value)) { // 如果是数字
					renttime = Integer.parseInt(value);
					// System.out.println("确定租用" + renttime + "天吗?(确定请输入Y/y)");
					// String ans = input.nextLine();
					// if(ans.toLowerCase().equals("y")) { //如果确定
					// break;
					// }else {
					// System.out.println("请重新输入租用天数");
					// }
					break;
				} else {
					System.out.println("输入错误！请重新输入");
				}
			}
			record.add(id, rentamount, renttime);
		}
		record.makeTotal(); // 结算
		System.out.println("录入信息完毕,当前租车总数量:" + record.getTotalCars() + "辆,租车总天数" + record.getTotalRentTime() + "天");
		System.out.println("请确定信息，确定请输入Y/y");
		String ans = input.nextLine();
		if (ans.toLowerCase().equals("y")) {
			record.setRentDate(new Date(System.currentTimeMillis()));
			FileUtil.writeRecord(record);
			System.out.println("\n结算信息如下\n" + record);// 展示结算信息
			System.out.println("当前租车成功,按任意键继续");
		} else {
			System.out.println("您取消了此次租车,按任意键继续");
		}
		input.nextLine();
	}

	/**
	 * 展示所有的租车记录
	 */
	private void showRecord() {
		File file = new File("../rent_record");
		File[] files = file.listFiles();
		RentRecord r = null;
		for (File f : files) {
			r = FileUtil.read_record(f);
			System.out.println(r);
		}
	}

	/**
	 * 判断是否是数字
	 */
	private boolean isNum(String value) {
		for (char c : value.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 读取车辆信息，并以图的方式返回
	 */
	private Map<Integer, Car> getCars() {
		return CarFactory.getAllCar();
	}

	public Map<Integer, Car> getCarinfo() {
		return carinfo;
	}

}
