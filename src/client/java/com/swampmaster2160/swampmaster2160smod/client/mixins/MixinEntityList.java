package com.swampmaster2160.swampmaster2160smod.client.mixins;

import org.spongepowered.asm.mixin.Mixin;

import com.swampmaster2160.swampmaster2160smod.block.entity.EntityRaccoonClient;

import net.minecraft.src.game.entity.EntityList;
import net.minecraft.src.game.entity.other.EntityItem;

@Mixin(EntityList.class)
public class MixinEntityList {
	static {
		addMapping(EntityRaccoonClient.class, "Raccoon", 300);
	}
}
