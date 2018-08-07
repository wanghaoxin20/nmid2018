package thirdweek;

/**
 * 书店对象
 */
public class BookStore {

	private static final int MAX_AMOUNT = 3; //最大存儲量

	private int currentStore;

	@Override
	public String toString() {
		return "当前书本数量:" + currentStore;
	}

	/*
	 * 构造方法
	 **************************************/
	public BookStore() {
		this.currentStore = 1;
	}
	
	/**
	 * 库存是否已满
	 * */
	public boolean isFull() {
		boolean flag = false;
		if(currentStore == MAX_AMOUNT) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 书籍是否可借
	 * */
	public boolean isCanBorrow() {
		boolean flag = true;
		if(currentStore == 0) {
			flag = false;
		}
		return flag;
	}
	/*
	 * 借书和还书的方法
	 *****************/
	public void borrow() {
		this.currentStore -= 1;
	}

	public void ret() {
		this.currentStore += 1;
	}

}
