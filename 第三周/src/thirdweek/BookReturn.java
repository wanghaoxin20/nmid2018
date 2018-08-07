package thirdweek;

import java.util.Date;

public class BookReturn extends Thread {

	private BookStore bookStore; // bookStore对象锁
	private Object borrowLock; // borrowLock锁

	public BookReturn(Object borrowLock, BookStore bookStore, String name) {
		this.borrowLock = borrowLock;
		this.bookStore = bookStore;
		setName(name);
	}

	@Override
	public void run() {
		synchronized (bookStore) { // bookStore对象加锁
			synchronized (borrowLock) {
				if (bookStore.isFull()) { // 如果库存已满
					System.out.println(new Date(System.currentTimeMillis()) + "  " + getName()
							+ "  图书库存已满,不需要还书 ******************");
				} else {
					bookStore.ret(); // 还书操作
					System.out.println(new Date(System.currentTimeMillis()) + "  " + getName()
							+ "  还书成功," + bookStore + "+++++++++++++++++++");
					borrowLock.notify(); // 释放锁
				}

				bookStore.notify();
			}
		}
	}
}
