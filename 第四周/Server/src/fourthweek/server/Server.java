package fourthweek.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server extends Thread{
	
	public static void main(String[] args) {
		new Server();
	}
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //���ڸ�ʽ
	
	private String ServerTxt; //Server.txt�ļ���·��
	
	private String recfilepath; //�����ļ����ļ���
	
	private ServerSocket serverSocket; //������socket
	
	private Socket currentSocket; //��ǰ���ӵ�socket
	
	private int port; //�����Ķ˿�
	
	private ThreadPoolExecutor executor; //�̳߳�
	
	/**
	 * �̳߳صĲ���
	 * */
	private int corePoolSize;
	private int maximumPoolSize;
	private int keepAliveTime;
	
	public Server() {
		if(init()) { //�����ʼ���ɹ�
			try {
				this.serverSocket = new ServerSocket(port); 
				this.executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, 
						new ArrayBlockingQueue<Runnable>(corePoolSize)); //�̳߳�
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("��������ʼ���ɹ�...\n");
			start();
		}else {
			System.out.println("ϵͳ��ʼ��ʧ��,�˳�ϵͳ!");
			System.exit(0); //�˳�ϵͳ
		}
	}
	
	
	@Override
	public void run() {
		while(true) {
			try {
				currentSocket = serverSocket.accept(); //�ȴ��ͻ�������
				System.out.println(sf.format(System.currentTimeMillis()) + ":����" + currentSocket.getInetAddress() + "������\n");
				executor.execute(new Task(currentSocket, ServerTxt, recfilepath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * ��������ʼ��,��ȡ�����ļ�
	 * */
	private boolean init() {
		Properties props = new Properties(); 
		BufferedReader br = null; 
		try {
			br = new BufferedReader(new FileReader("../prop/Server.prop"));
			props.load(br); //��ȡ�����ļ�
			this.port = Integer.parseInt(props.getProperty("Port")); //�������ļ��ж�ȡ�����Ķ˿ں�
			this.ServerTxt = props.getProperty("ServerTxt"); //��ȡServer.txt��·��
			this.recfilepath = props.getProperty("RecFilePath"); //�����ļ���·��
			/**�̳߳ز������*/
			this.corePoolSize = Integer.parseInt(props.getProperty("corePoolSize"));
			this.maximumPoolSize = Integer.parseInt(props.getProperty("maximumPoolSize"));
			this.keepAliveTime = Integer.parseInt(props.getProperty("keepAliveTime"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			return false;
		}finally {
			try {
				br.close(); //���ر���
			} catch (IOException e) {
				System.out.println("error:" + e.getMessage());
			}
		}
		return true;
	}
	

}
