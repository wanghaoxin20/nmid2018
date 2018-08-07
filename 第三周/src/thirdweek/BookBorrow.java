package thirdweek;

import java.util.Date;

public class BookBorrow extends Thread {

	private BookStore bookStore; // bookStore对象
	private Object borrowLock; // 只有持有borrowLock锁才能进行借书

	public BookBorrow(Object borrowLock, BookStore bookStore, String name) {
		this.borrowLock = borrowLock;
		this.bookStore = bookStore;
		setName(name);
	}

	@Override
	public void run() {

		synchronized (borrowLock) { // bookStore对象加锁
			if (bookStore.isCanBorrow()) { // 如果当前书籍量不为0
				bookStore.borrow();
				System.out.println(new Date(System.currentTimeMillis()) + "  " + getName() + "  借书成功,"
						+ bookStore + "--------------------");  //提示
				borrowLock.notify(); // 释放锁
			} else {
				try {
					// 等待还书之后再进行还书
					System.out.println(new Date(System.currentTimeMillis()) + "  " + getName()
							+ "  等待借书, " + bookStore + "==================");
					while (true) { // 循环竞争锁,如果图书库存量为0,释放锁等待,直到图书库存量不为0
						if (!bookStore.isCanBorrow()) {
							borrowLock.wait();
						} else {
							break;
						}
					}
					bookStore.borrow(); // 执行借书操作
					System.out.println(new Date(System.currentTimeMillis()) + "  " + getName()
							+ "  借书成功," + bookStore + "==---------------==");
					borrowLock.notify(); // 释放锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
