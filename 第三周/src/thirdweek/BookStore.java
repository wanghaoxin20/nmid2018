package thirdweek;

/**
 * ������
 */
public class BookStore {

	private static final int MAX_AMOUNT = 3; //���惦��

	private int currentStore;

	@Override
	public String toString() {
		return "��ǰ�鱾����:" + currentStore;
	}

	/*
	 * ���췽��
	 **************************************/
	public BookStore() {
		this.currentStore = 1;
	}
	
	/**
	 * ����Ƿ�����
	 * */
	public boolean isFull() {
		boolean flag = false;
		if(currentStore == MAX_AMOUNT) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * �鼮�Ƿ�ɽ�
	 * */
	public boolean isCanBorrow() {
		boolean flag = true;
		if(currentStore == 0) {
			flag = false;
		}
		return flag;
	}
	/*
	 * ����ͻ���ķ���
	 *****************/
	public void borrow() {
		this.currentStore -= 1;
	}

	public void ret() {
		this.currentStore += 1;
	}

}
