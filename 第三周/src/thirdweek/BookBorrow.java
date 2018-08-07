package thirdweek;

import java.util.Date;

public class BookBorrow extends Thread {

	private BookStore bookStore; // bookStore����
	private Object borrowLock; // ֻ�г���borrowLock�����ܽ��н���

	public BookBorrow(Object borrowLock, BookStore bookStore, String name) {
		this.borrowLock = borrowLock;
		this.bookStore = bookStore;
		setName(name);
	}

	@Override
	public void run() {

		synchronized (borrowLock) { // bookStore�������
			if (bookStore.isCanBorrow()) { // �����ǰ�鼮����Ϊ0
				bookStore.borrow();
				System.out.println(new Date(System.currentTimeMillis()) + "  " + getName() + "  ����ɹ�,"
						+ bookStore + "--------------------");  //��ʾ
				borrowLock.notify(); // �ͷ���
			} else {
				try {
					// �ȴ�����֮���ٽ��л���
					System.out.println(new Date(System.currentTimeMillis()) + "  " + getName()
							+ "  �ȴ�����, " + bookStore + "==================");
					while (true) { // ѭ��������,���ͼ������Ϊ0,�ͷ����ȴ�,ֱ��ͼ��������Ϊ0
						if (!bookStore.isCanBorrow()) {
							borrowLock.wait();
						} else {
							break;
						}
					}
					bookStore.borrow(); // ִ�н������
					System.out.println(new Date(System.currentTimeMillis()) + "  " + getName()
							+ "  ����ɹ�," + bookStore + "==---------------==");
					borrowLock.notify(); // �ͷ���
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
