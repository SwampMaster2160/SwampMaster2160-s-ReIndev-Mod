package com.swampmaster2160.swampmaster2160smod.mixininterfaces;

import java.util.HashMap;
import java.util.Map;

public abstract interface IMixinEntityList {
	public static Map<String, Class<?>> stringToClassMapping = new HashMap<String, Class<?>>();
	public static Map<Class<?>, String> classToStringMapping = new HashMap<Class<?>, String>();
	public static Map<Integer, Class<?>> IDtoClassMapping = new HashMap<Integer, Class<?>>();
	public static Map<Class<?>, Integer> classToIDMapping = new HashMap<Class<?>, Integer>();

	public static void addMapping(Class<?> arg0, String arg1, int arg2) {};
}
