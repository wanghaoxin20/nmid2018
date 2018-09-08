package biz;

import biz.impl.UserBizImpl;

/**
 * ��ȡUserBizImpl��ʵ��
 * */

public class UserBizFactory {
	/** ��������Ψһ�ı����ʵ��*/
	private static UserBizImpl me = null;
	
	/**
	 * ���UserBizImpl
	 * */
	public static UserBizImpl getInstance(){
		if(me == null){
			me = new UserBizImpl();
		}
		return me;
	}
	
}
