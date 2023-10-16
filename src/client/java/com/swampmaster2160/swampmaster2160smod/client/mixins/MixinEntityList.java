package com.swampmaster2160.swampmaster2160smod.client.mixins;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

//import com.swampmaster2160.swampmaster2160smod.block.entity.EntityRaccoonClient;
import com.swampmaster2160.swampmaster2160smod.mixininterfaces.IMixinEntityList;

import net.minecraft.src.game.entity.EntityList;

@Mixin(EntityList.class)
public abstract class MixinEntityList/* implements IMixinEntityList*/ {
	@Shadow
	private static Map<String, Class<?>> stringToClassMapping = new HashMap<String, Class<?>>();
	@Shadow
	private static Map<Class<?>, String> classToStringMapping = new HashMap<Class<?>, String>();
	@Shadow
	private static Map<Integer, Class<?>> IDtoClassMapping = new HashMap<Integer, Class<?>>();
	@Shadow
	private static Map<Class<?>, Integer> classToIDMapping = new HashMap<Class<?>, Integer>();

	/*static {
		addMapping(EntityRaccoonClient.class, "Raccoon", 300);
	}*/

	/*@Shadow
	private static void addMapping(Class<?> arg0, String arg1, int arg2) {
		System.out.println("AAAAAA");
	}

	public static void addMappingPublic(Class<?> arg0, String arg1, int arg2) {
		addMapping(arg0, arg1, arg2);
	}*/
	
	public static void addMapping(Class<?> arg0, String arg1, int arg2) {
		stringToClassMapping.put(arg1, arg0);
		classToStringMapping.put(arg0, arg1);
		IDtoClassMapping.put(arg2, arg0);
		classToIDMapping.put(arg0, arg2);
		System.out.println(stringToClassMapping);
	}
}
