package com.swampmaster2160.swampmaster2160smod.client.mixins;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.src.client.renderer.entity.Render;

import com.swampmaster2160.swampmaster2160smod.entity.EntityRaccoonClient;
import com.swampmaster2160.swampmaster2160smod.entitymodel.ModelRaccoon;
import com.swampmaster2160.swampmaster2160smod.entityrenderer.RenderRaccoon;

import net.minecraft.src.client.renderer.entity.RenderManager;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager {
	@Shadow
	private Map<Class<?>, Render> entityRenderMap = new HashMap<Class<?>, Render>();

	@Inject(method = "<init>", at = @At("TAIL"), cancellable = true)
	public void RenderManager(CallbackInfo callbackInfo) {
		this.entityRenderMap.put(EntityRaccoonClient.class, new RenderRaccoon(new ModelRaccoon(), 0.5F));
		for(Render renderer : this.entityRenderMap.values()) {
			renderer.setRenderManager((RenderManager)(Object)this);
		}
	}
}
