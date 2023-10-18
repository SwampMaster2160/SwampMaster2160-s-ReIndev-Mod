package com.swampmaster2160.swampmaster2160smod.entityrenderer;

import com.swampmaster2160.swampmaster2160smod.entity.EntityRaccoonClient;

import net.minecraft.src.client.model.ModelBase;
import net.minecraft.src.client.renderer.entity.RenderLiving;
import net.minecraft.src.game.entity.Entity;
import net.minecraft.src.game.entity.EntityLiving;

public class RenderRaccoon extends RenderLiving {
	public RenderRaccoon(ModelBase modelBase, float arg2) {
		super(modelBase, arg2);
	}

	public void renderWolf(EntityRaccoonClient entityRaccoon, double arg2, double arg4, double arg6, float arg8, float arg9) {
		super.doRenderLiving(entityRaccoon, arg2, arg4, arg6, arg8, arg9);
	}

	protected float func_25004_a(EntityRaccoonClient entityRaccoon, float arg2) {
		return entityRaccoon.setTailRotation();
	}

	protected void func_25006_b(EntityRaccoonClient entityRaccoon, float arg2) {}

	@Override
	protected void preRenderCallback(EntityLiving entityLiving, float arg2) {
		this.func_25006_b((EntityRaccoonClient)entityLiving, arg2);
	}

	@Override
	protected float handleRotationFloat(EntityLiving entityLiving, float arg2) {
		return this.func_25004_a((EntityRaccoonClient)entityLiving, arg2);
	}

	@Override
	public void doRenderLiving(EntityLiving entityLiving, double arg2, double arg4, double arg6, float arg8, float arg9) {
		this.renderWolf((EntityRaccoonClient)entityLiving, arg2, arg4, arg6, arg8, arg9);
	}

	@Override
	public void doRender(Entity entity, double arg2, double arg4, double arg6, float arg8, float arg9) {
		this.renderWolf((EntityRaccoonClient)entity, arg2, arg4, arg6, arg8, arg9);
	}
}
