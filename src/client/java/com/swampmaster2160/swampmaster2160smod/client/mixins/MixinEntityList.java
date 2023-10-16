package com.swampmaster2160.swampmaster2160smod.client.mixins;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.swampmaster2160.swampmaster2160smod.mixininterfaces.IMixinEntityList;

import net.minecraft.src.game.entity.EntityList;

@Mixin(EntityList.class)
public abstract class MixinEntityList implements IMixinEntityList {
	/*@Shadow
	private static Map<String, Class<?>> stringToClassMapping = new HashMap<String, Class<?>>();
	@Shadow
	private static Map<Class<?>, String> classToStringMapping = new HashMap<Class<?>, String>();
	@Shadow
	private static Map<Integer, Class<?>> IDtoClassMapping = new HashMap<Integer, Class<?>>();
	@Shadow
	private static Map<Class<?>, Integer> classToIDMapping = new HashMap<Class<?>, Integer>();*/

	/*static {
		addMapping(EntityRaccoonClient.class, "Raccoon", 300);
	}*/

	@Shadow
	private static void addMapping(Class<?> arg0, String arg1, int arg2) {
		//System.out.println("AAAAAA");
	}

	public void addMappingPublic(Class<?> arg0, String arg1, int arg2) {
		addMapping(arg0, arg1, arg2);
		//System.out.println(stringToClassMapping);
	}
	
	/*public static void addMapping(Class<?> arg0, String arg1, int arg2) {
		stringToClassMapping.put(arg1, arg0);
		classToStringMapping.put(arg0, arg1);
		IDtoClassMapping.put(arg2, arg0);
		classToIDMapping.put(arg0, arg2);
		System.out.println(stringToClassMapping);
	}*/

	/*@Inject(method = "createEntity", at = @At("HEAD"), cancellable = true)
	private static void createEntity(int arg0, World world, CallbackInfoReturnable<Entity> info) {
		System.out.println("TEST 1");
		System.out.println(stringToClassMapping);
	}

	@Inject(method = "createEntityFromNBT", at = @At("HEAD"), cancellable = true)
	private static void createEntityFromNBT(NBTTagCompound nbt, World world, CallbackInfoReturnable<Entity> info) {
		System.out.println("TEST 2");
		System.out.println(stringToClassMapping);
	}

	@Inject(method = "createEntityInWorld", at = @At("HEAD"), cancellable = true)
	private static void createEntityInWorld(String arg0, World world, CallbackInfoReturnable<Entity> info) {
		System.out.println("TEST 3");
		System.out.println(stringToClassMapping);
	}*/
}
