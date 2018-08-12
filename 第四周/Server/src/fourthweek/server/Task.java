package fourthweek.server;
/**
 * ����ÿ���ͻ��˷����������������
 * */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class Task implements Runnable {

	private final Object writelock = new Object(); // д���ļ�����
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ���ڸ�ʽ

	private String ServerTxt; // Server.txt�ļ���·��

	private DataInputStream din;

	private DataOutputStream dout;

	private Socket socket; // ��������������socket����


	private String recfilepath; // �����ļ����ļ���

	public Task(Socket socket, String ServerTxt, String RecFilePath) {
		this.socket = socket;
		this.ServerTxt = ServerTxt;
		this.recfilepath = RecFilePath;
		if (init()) { // ��ʼ���ɹ���ʼ����
			System.out.println("�ͻ���" + socket.getInetAddress() + "�����ʼ���ɹ�");
		} else { // �������������Ϣ
			System.out.println(sf.format(System.currentTimeMillis()) + ":�����ʼ��ʧ��");
		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				String mes = null; // ��Ŷ�ȡ��mes
				mes = din.readUTF(); // ��ȡ�ͻ��˵���Ϣmes
				long time = System.currentTimeMillis(); // ���ܵ�ʱ��
				String m[] = mes.split("=>"); // ��ȡ�ؼ���.m[0]Ϊ�ؼ���
				String mesType = null;
				if (m[0].equals("mes")) { // �����Ϣ������mes
					mesType = "������Ϣ";
					doMes(time, mesType, m[1]);
				} else if (m[0].equals("cmd")) { // �����Ϣ��cmd
					mesType = "ִ��cmd����";
					doCmd(time, mesType, m[1]);
				} else if (m[0].equals("sendfile")) { // �����Ϣ�Ƿ����ļ�,����
					mesType = "���ܿͻ��˷��͵��ļ�";
					doRecFile(time, mesType, m[1]);
				} else if (m[0].equals("getfile")) {
					mesType = "�ӷ���˻�ȡ�ļ�" + m[1];
					doGetFile(time, mesType, m[1]);
				} else if (m[0].equals("#shut")) { // ��ϢΪ#shutdown�Ͽ�����
					socket.close();
					System.out.println(MesInfo(time, "�ͻ��������Ͽ�����", "#shut"));
					break;
				}
				System.out.println("��¼�ôβ�����Ϣ.....");
				synchronized (writelock) { // ��д��Server.txt����,��¼��Ϣ
					if (writeToTxt(MesInfo(time, mesType, m[1]) + System.lineSeparator())) {
						System.out.println("��¼�ɹ�\n");
					} else {
						System.out.println("��¼ʧ��\n");
					}
				}
			} catch (IOException e) {
				System.out.println("error:" + e.getMessage());
				System.out.println(sf.format(System.currentTimeMillis()) + ":�ͻ���" + socket.getInetAddress() + "�����ӹر�!");
				break;
			}
		}
	}

	/**
	 * ������Ϣ����Ϊmes������
	 */
	private void doMes(long time, String mesType, String mesContent) {
		System.out.print(MesInfo(time, mesType, mesContent)); // ���������Ϣ
		System.out.println("������Ϣ��...");
		try {
			dout.writeUTF("���IP��ַ:" + socket.getInetAddress());
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("����mes:IO����");
		}
		System.out.println("�������");
	}

	/**
	 * ����ӷ���˻�ȡ�ļ�
	 */
	private void doGetFile(long time, String mesType, String mesContent) {
		System.out.print(MesInfo(time, mesType, mesContent)); // ���������Ϣ
		File getfile = new File(recfilepath + File.separator + mesContent);
		byte[] data = new byte[1024];
		try (FileInputStream fin = new FileInputStream(getfile);) { // �������������Զ��ر���Դ
			long filelength = getfile.length(); // ��ȡ�ļ�����
			dout.writeLong(filelength); // �����ļ�����
			int length = 0;
			System.out.println("�����ļ���");
			System.out.println("�ļ���С:" + filelength / 1024 + "KB");
			while ((length = fin.read(data, 0, data.length)) != -1) { // ��ȡ�ļ�������
				dout.write(data, 0, length);
				dout.flush();
			}
			System.out.println("�������");
		} catch (FileNotFoundException e) {
			try {
				dout.writeLong(-1); // �ļ������ڸ��ͻ��˷���-1
			} catch (IOException e1) {
				System.out.println("error:" + e.getMessage());
			}
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("�ӷ���˻�ȡ�ļ�:IO����");
		}

	}

	/**
	 * ����ӿͻ��˽����ļ�
	 */
	private void doRecFile(long time, String mesType, String mesContent) {
		System.out.print(MesInfo(time, mesType, mesContent)); // ���������Ϣ
		File file = new File(recfilepath);
		if (!file.exists()) { // ����ļ��в�����, �����ļ���
			file.mkdir();
		}
		File recfile = new File(recfilepath + File.separator + mesContent);
		while (recfile.exists()) { // ������͵��ļ��Ѵ���,�ļ�������,ֱ��������
			recfile = new File(
					recfilepath + File.separator + recfile.getName().split("\\.")[0] + "+." + recfile.getName().split("\\.")[1]); // ���ļ����ƺ��һ��+
		}
		byte[] data = new byte[1024]; // ����Ϊ1024���ֽ�����
		int length = 0;
		long currentlength = 0; // �ѽ��ܳ���
		System.out.println("�����ļ���...");
		try (FileOutputStream fout = new FileOutputStream(recfile);) { // �����ļ������,�Զ��ͷ���Դ
			long filelength = din.readLong(); // ��ȡ�ļ�����
			System.out.println("�ļ���С:" + filelength / 1024 + "KB");

			while (currentlength < filelength) { // �����ļ�
				length = din.read(data, 0, data.length);
				fout.write(data, 0, length);
				fout.flush();
				currentlength += length;
			}
			System.out.println("�������");
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("�����ļ�:IO����");
		}

	}

	/**
	 * ������Ϣ����Ϊcmd������
	 */
	private void doCmd(long time, String mesType, String mesContent) {
		System.out.print(MesInfo(time, mesType, mesContent)); // ���������Ϣ
		Runtime r = Runtime.getRuntime();
		try {
			System.out.println("����cmd������....");
			Process p = r.exec("cmd /c " + mesContent); // ����cmd����
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuffer sBuffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) { // ��ȡ����ķ��ؽ��
				sBuffer.append(line + System.lineSeparator());
			}
			byte[] data = sBuffer.toString().getBytes("UTF-8"); // �����ת��Ϊ�ֽ�����
			int mesLenth = data.length; // �ֽ����鳤��
			dout.writeInt(mesLenth); // �������ݳ���
			int length = 0;
			while (length < mesLenth) { // ��������
				dout.writeByte(data[length]);
				length++;
			}
			System.out.println("���ݷ������");
			br.close();
			p.destroy();
			System.out.println("�������");
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("����cmd��IO����");
		}
	}

	/**
	 * ����Ϣд��Server.txt
	 */
	private boolean writeToTxt(String info) {
		boolean flag = true;
		File file = new File(ServerTxt);
		FileWriter fw = null;
		try {
			if (!file.exists()) { // ���Server.txt������,����
				file.createNewFile();
			}
			fw = new FileWriter(file, true); // ׷��ģʽд���ַ�
			fw.write(info);
			fw.close();
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			flag = false; // �����򷵻�false
		}
		return flag;
	}

	/**
	 * �ô��������Ϣ *
	 */
	private String MesInfo(long time, String mesType, String mesContent) {
		return "ʱ��:" + sf.format(time) + System.lineSeparator() + "��Ϣ��Դ:" + socket.getInetAddress()
				+ System.lineSeparator() + "��Ϣ����:" + mesType + System.lineSeparator() + "��Ϣ����:" + mesContent
				+ System.lineSeparator();
	}

	/**
	 * �����ʼ��
	 */
	private boolean init() {
		try {
			din = new DataInputStream(socket.getInputStream()); // ������
			dout = new DataOutputStream(socket.getOutputStream()); // �����
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			return false;
		}
		return true;
	}

}
