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
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //日期格式
	
	private String ServerTxt; //Server.txt文件的路径
	
	private String recfilepath; //接受文件的文件夹
	
	private ServerSocket serverSocket; //服务器socket
	
	private Socket currentSocket; //当前连接的socket
	
	private int port; //监听的端口
	
	private ThreadPoolExecutor executor; //线程池
	
	/**
	 * 线程池的参数
	 * */
	private int corePoolSize;
	private int maximumPoolSize;
	private int keepAliveTime;
	
	public Server() {
		if(init()) { //如果初始化成功
			try {
				this.serverSocket = new ServerSocket(port); 
				this.executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, 
						new ArrayBlockingQueue<Runnable>(corePoolSize)); //线程池
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("服务器初始化成功...\n");
			start();
		}else {
			System.out.println("系统初始化失败,退出系统!");
			System.exit(0); //退出系统
		}
	}
	
	
	@Override
	public void run() {
		while(true) {
			try {
				currentSocket = serverSocket.accept(); //等待客户端连接
				System.out.println(sf.format(System.currentTimeMillis()) + ":来自" + currentSocket.getInetAddress() + "的连接\n");
				executor.execute(new Task(currentSocket, ServerTxt, recfilepath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 服务器初始化,读取配置文件
	 * */
	private boolean init() {
		Properties props = new Properties(); 
		BufferedReader br = null; 
		try {
			br = new BufferedReader(new FileReader("../prop/Server.prop"));
			props.load(br); //读取配置文件
			this.port = Integer.parseInt(props.getProperty("Port")); //从配置文件中读取监听的端口号
			this.ServerTxt = props.getProperty("ServerTxt"); //读取Server.txt的路径
			this.recfilepath = props.getProperty("RecFilePath"); //接收文件的路径
			/**线程池参数相关*/
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
				br.close(); //最后关闭流
			} catch (IOException e) {
				System.out.println("error:" + e.getMessage());
			}
		}
		return true;
	}
	

}
