package com.ascendant.common.constants;

import java.util.Map;

/**
 * 计算机系统信息常量
 * @author qiaolin
 * @date 2018年10月23日
 *
 */

public interface SystemConstants {
	
	/** 计算机基本信息 */
	public static final Map<String, String> map = System.getenv();
	
	/** 计算机当前登录的 用户名 */
	public static final String USERNAME = map.get("USERNAME");
	
	/** 计算机名 */
	public static final String COMPUTERNAME = map.get("COMPUTERNAME");
	
	/** JavaHome*/
	public static final String JAVA_HOME = map.get("JAVA_HOME");
	
	/** 系统零时路径 */
	public static final String TEMP = map.get("TEMP");

}
