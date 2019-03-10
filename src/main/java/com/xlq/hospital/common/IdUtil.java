package com.xlq.hospital.common;

import java.util.UUID;
/**
 * 工具类：获取唯一不重复的id
 * @author JAY
 *
 */
public final class IdUtil {
   public static String getRandomId() {
	   UUID uuid = UUID.randomUUID();
	   //截取前12位
	   String id = uuid.toString().replaceAll("-", "").substring(0, 12);
	   return id;
   }
}

