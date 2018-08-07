package thirdweek;

import java.util.Date;

public class BookReturn extends Thread {

	private BookStore bookStore; // bookStore������
	private Object borrowLock; // borrowLock��

	public BookReturn(Object borrowLock, BookStore bookStore, String name) {
		this.borrowLock = borrowLock;
		this.bookStore = bookStore;
		setName(name);
	}

	@Override
	public void run() {
		synchronized (bookStore) { // bookStore�������
			synchronized (borrowLock) {
				if (bookStore.isFull()) { // ����������
					System.out.println(new Date(System.currentTimeMillis()) + "  " + getName()
							+ "  ͼ��������,����Ҫ���� ******************");
				} else {
					bookStore.ret(); // �������
					System.out.println(new Date(System.currentTimeMillis()) + "  " + getName()
							+ "  ����ɹ�," + bookStore + "+++++++++++++++++++");
					borrowLock.notify(); // �ͷ���
				}

				bookStore.notify();
			}
		}
	}
}
