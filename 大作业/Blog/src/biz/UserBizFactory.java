package biz;

import biz.impl.UserBizImpl;

/**
 * 获取UserBizImpl的实例
 * */

public class UserBizFactory {
	/** 用来保存唯一的本类的实例*/
	private static UserBizImpl me = null;
	
	/**
	 * 获得UserBizImpl
	 * */
	public static UserBizImpl getInstance(){
		if(me == null){
			me = new UserBizImpl();
		}
		return me;
	}
	
}
