package fourthweek.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

/**
 * �ͻ����࣬�̳���Thread
 */
public class Client extends Thread {
	public static void main(String[] args) {
		new Client();
	}

	private String host; // ������ַ

	private int port; // �����˿ں�

	private Socket socket; // �ͻ����ڵ�socket����

	private InetSocketAddress ISAddress; // ��������ip�׽��ֵ�ַ

	private DataInputStream din; // ������

	private DataOutputStream dout; // �����

	private String sendfilepath; // �����ļ����ļ���

	private String getfilepath; // �ӷ���˻�ȡ���ļ����λ��

	Scanner input;

	public Client() {
		if (init_info()) { // ϵͳ��ʼ���ɹ�
			System.out.println("������Ϣ��ʼ���ɹ�...");
			init_system();
			start();
		} else { // ���ϵͳ��ʼ��ʧ��
			System.out.println("������Ϣ��ʼ��ʧ��,�˳�ϵͳ!");
			System.exit(0); // �˳�ϵͳ
		}

	}

	// �ͻ��˿�ʼ����
	@Override
	public void run() {
		try {
			System.out.println("���ӷ�������......");
			socket.connect(ISAddress, 5000); // ���ӷ�����,��ʱʱ��Ϊ5s
			System.out.println("���ӳɹ�");
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			while (true) {
				System.out.println();
				String mes = input.nextLine();

				// ������֤�������Ϣ
				String m[] = mes.split("=>");
				if (m[0].equals("mes") && m.length > 1) { // ���Ҫ����������Ϣ
					doMes(mes);
				} else if (m[0].equals("cmd") && m.length > 1) { // cmd����
					doCmd(mes);
				} else if (m[0].equals("sendfile") && m.length > 1) {
					doSendFile(mes, m[1]);
				} else if (m[0].equals("getfile") && m.length > 1) {
					doGetFile(mes, m[1]);
				} else if (m[0].equals("#shut") && m.length == 1) { // �˳�ϵͳ
					dout.writeUTF("#shut");
					socket.close();
					break;
				} else {
					System.out.println("��������ȷ�����");
				}

			}
			System.out.println("ϵͳ�˳�!");
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("���Ӵ���");
		}
	}

	/**
	 * ��������
	 */
	private void doMes(String mesContent) {
		String rmes = null;
		try {
			dout.writeUTF(mesContent);
			rmes = din.readUTF();
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("��������:IO����");
		}
		System.out.println("���Է�����:" + rmes);
	}
	
	/**
	 * �ӷ���˻�ȡ�ļ�
	 */
	private void doGetFile(String mes, String filename) {
		long filelength = 0;
		try {
			dout.writeUTF(mes);
			filelength = din.readLong();
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("��ȡ�ļ�����");
			return;
		}
		if (filelength == -1) {
			System.out.println("������ļ�������");
		} else {
			File file = new File(getfilepath);
			if (!file.exists()) { // ����ļ��в�����, �����ļ���
				file.mkdir();
			}
			File getfile = new File(getfilepath + File.separator + filename);
			while (getfile.exists()) { // ������͵��ļ��Ѵ���,�ļ�������,ֱ��������
				getfile = new File(
						getfilepath + File.separator + getfile.getName().split("\\.")[0] + "+." + getfile.getName().split("\\.")[1]); // ���ļ����ƺ��һ��+
			}
			byte[] data = new byte[1024]; // ����Ϊ1024���ֽ�����
			int length = 0;
			long currentlength = 0; // �ѽ��ܳ���
			System.out.println("�����ļ���...");
			try (FileOutputStream fout = new FileOutputStream(getfile);) { // �����ļ������,�Զ��ͷ���Դ
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
				System.err.println("��ȡ�ļ����մ���");
			}
		}
	}

	/**
	 * �����ļ���������
	 * 
	 */
	private void doSendFile(String mes, String filename) {
		File file = new File(sendfilepath + File.separator + filename);
		byte[] data = new byte[1024];
		try (FileInputStream fin = new FileInputStream(file);) { // �������������Զ��ر���Դ
			if(filename.split(".").length <= 1) {
				mes = mes + ".data";  //���û�к�׺�� ���.data��׺
			}
			dout.writeUTF(mes);
			long filelength = file.length();
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
			System.out.println("�ļ�������!\n");
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("�����ļ�:IO����");
		}
	}

	/**
	 * �Է�������cmd����
	 */
	private void doCmd(String mesContent) {
		try {
			dout.writeUTF(mesContent);
			int length = 0;
			System.out.println("������:");
			int mesLength = din.readInt(); // ��ȡ���ݵĳ���
			byte[] data = new byte[mesLength]; // �����ֽ�����
			while (length < mesLength) {
				data[length] = din.readByte();
				length++;
			}
			System.out.println(new String(data, 0, data.length, "UTF-8"));

			System.out.println("���");
			System.out.println();
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("cmd����:IO����");
		}
	}

	/**
	 * ��ʼ��ϵͳ
	 */
	private void init_system() {
		this.socket = new Socket();
		this.ISAddress = new InetSocketAddress(host, port);
		input = new Scanner(System.in);
	}

	/**
	 * ��ʼ����Ϣ,��ȡ�����ļ�
	 */
	private boolean init_info() {
		Properties props = new Properties();
		try (BufferedReader br = new BufferedReader(new FileReader("../prop/Client.prop"));) {
			props.load(br); // ��ȡ�����ļ�
			this.host = props.getProperty("ServerHost"); // �������ļ��ж�ȡ��������ַ
			this.port = Integer.parseInt(props.getProperty("ServerPort")); // �������ļ��ж�ȡ�������Ķ˿ں�
			this.sendfilepath = props.getProperty("SendFilePath");
			this.getfilepath = props.getProperty("GetFilePath");
		} catch (FileNotFoundException e) {
			System.out.println("�����ļ�û�ҵ���");
			return false;
		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			return false;
		}
		return true;

	}

}
