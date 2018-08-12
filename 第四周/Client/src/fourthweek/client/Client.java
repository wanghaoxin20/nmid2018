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
 * 客户端类，继承自Thread
 */
public class Client extends Thread {
	public static void main(String[] args) {
		new Client();
	}

	private String host; // 主机地址

	private int port; // 主机端口号

	private Socket socket; // 客户端内的socket对象

	private InetSocketAddress ISAddress; // 服务器的ip套接字地址

	private DataInputStream din; // 输入流

	private DataOutputStream dout; // 输出流

	private String sendfilepath; // 传输文件的文件夹

	private String getfilepath; // 从服务端获取的文件存放位置

	Scanner input;

	public Client() {
		if (init_info()) { // 系统初始化成功
			System.out.println("配置信息初始化成功...");
			init_system();
			start();
		} else { // 如果系统初始化失败
			System.out.println("配置信息初始化失败,退出系统!");
			System.exit(0); // 退出系统
		}

	}

	// 客户端开始运行
	@Override
	public void run() {
		try {
			System.out.println("连接服务器中......");
			socket.connect(ISAddress, 5000); // 连接服务器,超时时间为5s
			System.out.println("连接成功");
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			while (true) {
				System.out.println();
				String mes = input.nextLine();

				// 本地验证输入的消息
				String m[] = mes.split("=>");
				if (m[0].equals("mes") && m.length > 1) { // 如果要传输文字信息
					doMes(mes);
				} else if (m[0].equals("cmd") && m.length > 1) { // cmd命令
					doCmd(mes);
				} else if (m[0].equals("sendfile") && m.length > 1) {
					doSendFile(mes, m[1]);
				} else if (m[0].equals("getfile") && m.length > 1) {
					doGetFile(mes, m[1]);
				} else if (m[0].equals("#shut") && m.length == 1) { // 退出系统
					dout.writeUTF("#shut");
					socket.close();
					break;
				} else {
					System.out.println("请输入正确的命令！");
				}

			}
			System.out.println("系统退出!");
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("连接错误");
		}
	}

	/**
	 * 传输文字
	 */
	private void doMes(String mesContent) {
		String rmes = null;
		try {
			dout.writeUTF(mesContent);
			rmes = din.readUTF();
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("传输文字:IO错误");
		}
		System.out.println("来自服务器:" + rmes);
	}
	
	/**
	 * 从服务端获取文件
	 */
	private void doGetFile(String mes, String filename) {
		long filelength = 0;
		try {
			dout.writeUTF(mes);
			filelength = din.readLong();
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("获取文件错误");
			return;
		}
		if (filelength == -1) {
			System.out.println("服务端文件不存在");
		} else {
			File file = new File(getfilepath);
			if (!file.exists()) { // 如果文件夹不存在, 创建文件夹
				file.mkdir();
			}
			File getfile = new File(getfilepath + File.separator + filename);
			while (getfile.exists()) { // 如果发送的文件已存在,文件重命名,直到不重名
				getfile = new File(
						getfilepath + File.separator + getfile.getName().split("\\.")[0] + "+." + getfile.getName().split("\\.")[1]); // 在文件名称后加一个+
			}
			byte[] data = new byte[1024]; // 缓存为1024的字节数组
			int length = 0;
			long currentlength = 0; // 已接受长度
			System.out.println("接收文件中...");
			try (FileOutputStream fout = new FileOutputStream(getfile);) { // 创建文件输出流,自动释放资源
				System.out.println("文件大小:" + filelength / 1024 + "KB");

				while (currentlength < filelength) { // 接收文件
					length = din.read(data, 0, data.length);
					fout.write(data, 0, length);
					fout.flush();
					currentlength += length;
				}
				System.out.println("接收完成");
			} catch (IOException e) {
				System.out.println("error:" + e.getMessage());
				System.err.println("获取文件接收错误");
			}
		}
	}

	/**
	 * 传送文件给服务器
	 * 
	 */
	private void doSendFile(String mes, String filename) {
		File file = new File(sendfilepath + File.separator + filename);
		byte[] data = new byte[1024];
		try (FileInputStream fin = new FileInputStream(file);) { // 构造输入流，自动关闭资源
			if(filename.split(".").length <= 1) {
				mes = mes + ".data";  //如果没有后缀名 添加.data后缀
			}
			dout.writeUTF(mes);
			long filelength = file.length();
			dout.writeLong(filelength); // 发送文件长度
			int length = 0;
			System.out.println("发送文件中");
			System.out.println("文件大小:" + filelength / 1024 + "KB");
			while ((length = fin.read(data, 0, data.length)) != -1) { // 读取文件并发送
				dout.write(data, 0, length);
				dout.flush();
			}
			System.out.println("发送完成");
		} catch (FileNotFoundException e) {
			System.out.println("文件不存在!\n");
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("传输文件:IO错误");
		}
	}

	/**
	 * 对服务器的cmd命令
	 */
	private void doCmd(String mesContent) {
		try {
			dout.writeUTF(mesContent);
			int length = 0;
			System.out.println("命令结果:");
			int mesLength = din.readInt(); // 读取数据的长度
			byte[] data = new byte[mesLength]; // 构建字节数组
			while (length < mesLength) {
				data[length] = din.readByte();
				length++;
			}
			System.out.println(new String(data, 0, data.length, "UTF-8"));

			System.out.println("完成");
			System.out.println();
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("cmd命令:IO错误");
		}
	}

	/**
	 * 初始化系统
	 */
	private void init_system() {
		this.socket = new Socket();
		this.ISAddress = new InetSocketAddress(host, port);
		input = new Scanner(System.in);
	}

	/**
	 * 初始化信息,读取配置文件
	 */
	private boolean init_info() {
		Properties props = new Properties();
		try (BufferedReader br = new BufferedReader(new FileReader("../prop/Client.prop"));) {
			props.load(br); // 读取配置文件
			this.host = props.getProperty("ServerHost"); // 从配置文件中读取服务器地址
			this.port = Integer.parseInt(props.getProperty("ServerPort")); // 从配置文件中读取服务器的端口号
			this.sendfilepath = props.getProperty("SendFilePath");
			this.getfilepath = props.getProperty("GetFilePath");
		} catch (FileNotFoundException e) {
			System.out.println("配置文件没找到！");
			return false;
		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
			return false;
		}
		return true;

	}

}
