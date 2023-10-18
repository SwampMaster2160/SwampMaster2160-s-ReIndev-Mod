package com.swampmaster2160.swampmaster2160smod.server.mixins;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.swampmaster2160.swampmaster2160smod.mixininterfaces.IMixinEntityListServer;

import net.minecraft.src.game.entity.EntityList;

@Mixin(EntityList.class)
public abstract class MixinEntityListServer implements IMixinEntityListServer {
	@Shadow
	private static void addMapping(Class<?> arg0, String arg1, int arg2) {}

	public void addMappingPublic(Class<?> arg0, String arg1, int arg2) {
		addMapping(arg0, arg1, arg2);
	}
}
