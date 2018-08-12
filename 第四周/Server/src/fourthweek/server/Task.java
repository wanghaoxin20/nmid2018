package fourthweek.server;
/**
 * 处理每个客户端发过来请求的任务类
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

	private final Object writelock = new Object(); // 写入文件的锁
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式

	private String ServerTxt; // Server.txt文件的路径

	private DataInputStream din;

	private DataOutputStream dout;

	private Socket socket; // 服务器传过来的socket对象


	private String recfilepath; // 传输文件的文件夹

	public Task(Socket socket, String ServerTxt, String RecFilePath) {
		this.socket = socket;
		this.ServerTxt = ServerTxt;
		this.recfilepath = RecFilePath;
		if (init()) { // 初始化成功开始任务
			System.out.println("客户端" + socket.getInetAddress() + "任务初始化成功");
		} else { // 否则输出错误信息
			System.out.println(sf.format(System.currentTimeMillis()) + ":任务初始化失败");
		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				String mes = null; // 存放读取的mes
				mes = din.readUTF(); // 读取客户端的信息mes
				long time = System.currentTimeMillis(); // 接受的时间
				String m[] = mes.split("=>"); // 提取关键字.m[0]为关键字
				String mesType = null;
				if (m[0].equals("mes")) { // 如果消息类型是mes
					mesType = "文字消息";
					doMes(time, mesType, m[1]);
				} else if (m[0].equals("cmd")) { // 如果消息是cmd
					mesType = "执行cmd命令";
					doCmd(time, mesType, m[1]);
				} else if (m[0].equals("sendfile")) { // 如果消息是发送文件,接受
					mesType = "接受客户端发送的文件";
					doRecFile(time, mesType, m[1]);
				} else if (m[0].equals("getfile")) {
					mesType = "从服务端获取文件" + m[1];
					doGetFile(time, mesType, m[1]);
				} else if (m[0].equals("#shut")) { // 消息为#shutdown断开连接
					socket.close();
					System.out.println(MesInfo(time, "客户端主动断开连接", "#shut"));
					break;
				}
				System.out.println("记录该次操作信息.....");
				synchronized (writelock) { // 给写入Server.txt加锁,记录信息
					if (writeToTxt(MesInfo(time, mesType, m[1]) + System.lineSeparator())) {
						System.out.println("记录成功\n");
					} else {
						System.out.println("记录失败\n");
					}
				}
			} catch (IOException e) {
				System.out.println("error:" + e.getMessage());
				System.out.println(sf.format(System.currentTimeMillis()) + ":客户端" + socket.getInetAddress() + "的连接关闭!");
				break;
			}
		}
	}

	/**
	 * 处理消息类型为mes的请求
	 */
	private void doMes(long time, String mesType, String mesContent) {
		System.out.print(MesInfo(time, mesType, mesContent)); // 输出操作信息
		System.out.println("反馈消息中...");
		try {
			dout.writeUTF("你的IP地址:" + socket.getInetAddress());
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("反馈mes:IO错误");
		}
		System.out.println("反馈完成");
	}

	/**
	 * 处理从服务端获取文件
	 */
	private void doGetFile(long time, String mesType, String mesContent) {
		System.out.print(MesInfo(time, mesType, mesContent)); // 输出操作信息
		File getfile = new File(recfilepath + File.separator + mesContent);
		byte[] data = new byte[1024];
		try (FileInputStream fin = new FileInputStream(getfile);) { // 构造输入流，自动关闭资源
			long filelength = getfile.length(); // 获取文件长度
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
			try {
				dout.writeLong(-1); // 文件不存在给客户端返回-1
			} catch (IOException e1) {
				System.out.println("error:" + e.getMessage());
			}
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("从服务端获取文件:IO错误");
		}

	}

	/**
	 * 处理从客户端接收文件
	 */
	private void doRecFile(long time, String mesType, String mesContent) {
		System.out.print(MesInfo(time, mesType, mesContent)); // 输出操作信息
		File file = new File(recfilepath);
		if (!file.exists()) { // 如果文件夹不存在, 创建文件夹
			file.mkdir();
		}
		File recfile = new File(recfilepath + File.separator + mesContent);
		while (recfile.exists()) { // 如果发送的文件已存在,文件重命名,直到不重名
			recfile = new File(
					recfilepath + File.separator + recfile.getName().split("\\.")[0] + "+." + recfile.getName().split("\\.")[1]); // 在文件名称后加一个+
		}
		byte[] data = new byte[1024]; // 缓存为1024的字节数组
		int length = 0;
		long currentlength = 0; // 已接受长度
		System.out.println("接收文件中...");
		try (FileOutputStream fout = new FileOutputStream(recfile);) { // 创建文件输出流,自动释放资源
			long filelength = din.readLong(); // 读取文件长度
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
			System.out.println("接受文件:IO错误！");
		}

	}

	/**
	 * 处理消息类型为cmd的请求
	 */
	private void doCmd(long time, String mesType, String mesContent) {
		System.out.print(MesInfo(time, mesType, mesContent)); // 输出操作信息
		Runtime r = Runtime.getRuntime();
		try {
			System.out.println("处理cmd命令中....");
			Process p = r.exec("cmd /c " + mesContent); // 运行cmd命令
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuffer sBuffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) { // 读取命令的返回结果
				sBuffer.append(line + System.lineSeparator());
			}
			byte[] data = sBuffer.toString().getBytes("UTF-8"); // 将结果转换为字节数组
			int mesLenth = data.length; // 字节数组长度
			dout.writeInt(mesLenth); // 发送数据长度
			int length = 0;
			while (length < mesLenth) { // 发送数据
				dout.writeByte(data[length]);
				length++;
			}
			System.out.println("数据发送完成");
			br.close();
			p.destroy();
			System.out.println("处理完成");
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			System.out.println("处理cmd：IO错误");
		}
	}

	/**
	 * 将信息写入Server.txt
	 */
	private boolean writeToTxt(String info) {
		boolean flag = true;
		File file = new File(ServerTxt);
		FileWriter fw = null;
		try {
			if (!file.exists()) { // 如果Server.txt不存在,创建
				file.createNewFile();
			}
			fw = new FileWriter(file, true); // 追加模式写入字符
			fw.write(info);
			fw.close();
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			flag = false; // 错误则返回false
		}
		return flag;
	}

	/**
	 * 该次请求的信息 *
	 */
	private String MesInfo(long time, String mesType, String mesContent) {
		return "时间:" + sf.format(time) + System.lineSeparator() + "消息来源:" + socket.getInetAddress()
				+ System.lineSeparator() + "消息类型:" + mesType + System.lineSeparator() + "消息内容:" + mesContent
				+ System.lineSeparator();
	}

	/**
	 * 任务初始化
	 */
	private boolean init() {
		try {
			din = new DataInputStream(socket.getInputStream()); // 输入流
			dout = new DataOutputStream(socket.getOutputStream()); // 输出流
		} catch (IOException e) {
			System.out.println("error:" + e.getMessage());
			return false;
		}
		return true;
	}

}
