package seconedweek.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;

import seconedweek.entity.record.RentRecord;

public class FileUtil {

	public static final String record_path = "../rent_record";

	/**
	 * 将record写入文件
	 */
	public static void writeRecord(RentRecord record) {
		File folder = new File(record_path); 
		File file = new File(record_path + File.separator
				+ new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(record.getRentDate()));
		ObjectOutputStream objout;
		if(!folder.exists()){
			folder.mkdir();
		}
		try {
			objout = new ObjectOutputStream(new FileOutputStream(file));
			objout.writeObject(record);
			objout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读出object
	 */
	public static RentRecord read_record(File file) {
		RentRecord record = null;
		try {
			ObjectInputStream objin = new ObjectInputStream(new FileInputStream(file));
			record = (RentRecord) objin.readObject();
			objin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return record;
	}

}
