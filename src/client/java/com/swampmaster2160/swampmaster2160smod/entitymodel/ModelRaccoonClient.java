package com.swampmaster2160.swampmaster2160smod.entitymodel;

import org.lwjgl.opengl.GL11;

import com.swampmaster2160.swampmaster2160smod.entity.EntityRaccoonClient;

import net.minecraft.src.client.model.ModelBase;
import net.minecraft.src.client.model.Piece;
import net.minecraft.src.game.MathHelper;
import net.minecraft.src.game.entity.EntityLiving;

public class ModelRaccoonClient extends ModelBase {
	public Piece raccoonHeadMain;
	public Piece raccoonBody;
	public Piece raccoonLeg1;
	public Piece raccoonLeg2;
	public Piece raccoonLeg3;
	public Piece raccoonLeg4;
	public Piece raccoonRightEar;
	public Piece raccoonLeftEar;
	public Piece raccoonSnout;
	public Piece raccoonFloofyTail;
	protected float babyHeadAlignment = 8.0F;
	protected float adultHeadAlignment = 4.0F;

	public ModelRaccoonClient() {
		float unused = 14.5F;
		this.raccoonHeadMain = new Piece(0, 0);
		//this.raccoonHeadMain.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
		this.raccoonHeadMain.addBox(-1, -1.5F, -1.0F, 6, 6, 6, 0.0F);
		this.raccoonBody = new Piece(40, 0);
		//this.raccoonBody.addBox(-4.0F, -7.125F, -2.5F, 5, 10, 5, 0.0F);
		this.raccoonBody.addBox(-2.5F, -5, -2.5F, 5, 10, 5, 0.0F);
		this.raccoonLeg1 = new Piece(0, 18);
		this.raccoonLeg1.addBox(-1.0F, 1.0F, -1.0F, 2, 5, 2, 0.0F);
		this.raccoonLeg2 = new Piece(0, 18);
		this.raccoonLeg2.addBox(-1.0F, 1.0F, -1.0F, 2, 5, 2, 0.0F);
		this.raccoonLeg3 = new Piece(0, 18);
		this.raccoonLeg3.addBox(-1.0F, 1.0F, -1.0F, 2, 5, 2, 0.0F);
		this.raccoonLeg4 = new Piece(0, 18);
		this.raccoonLeg4.addBox(-1.0F, 1.0F, -1.0F, 2, 5, 2, 0.0F);
		this.raccoonFloofyTail = new Piece(40, 16);
		this.raccoonFloofyTail.addBox(-2.0F, -1.0F, -5.0F, 6, 10, 6, 0.0F);
		this.raccoonRightEar = new Piece(16, 14);
		this.raccoonRightEar.addBox(-2.5F, -4.875F, -1.0F, 2, 2, 1, 0.0F);
		this.raccoonLeftEar = new Piece(23, 14);
		this.raccoonLeftEar.addBox(2.5F, -4.875F, -1.0F, 2, 2, 1, 0.0F);
		this.raccoonSnout = new Piece(0, 12);
		this.raccoonSnout.addBox(-1.0F, 1.0F, -5.25F, 2, 2, 3, 0.0F);
		this.raccoonSnout.setRotationPoint(-2.0F, unused, -6.5F);
		this.raccoonHeadMain.setRotationPoint(-2.0F, unused, -6.5F);
		this.raccoonRightEar.setRotationPoint(-2.0F, unused, -6.5F);
		this.raccoonLeftEar.setRotationPoint(-2.0F, unused, -6.5F);
	}

	@Override
	public void render(float forward, float haltDelta, float tick, float yaw, float pitch, float unused) {
		super.render(forward, haltDelta, tick, yaw, pitch, unused);
		this.setRotationAngles(forward, haltDelta, tick, yaw, pitch, unused);
		if (this.isChild) {
			float scale2 = 2.0F;
			float scale = 1.25F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F / scale);
			GL11.glTranslatef(0.0F, this.babyHeadAlignment * unused, this.adultHeadAlignment * unused);
			this.raccoonHeadMain.renderWithRotation(unused);
			this.raccoonRightEar.renderWithRotation(unused);
			this.raccoonLeftEar.renderWithRotation(unused);
			this.raccoonSnout.renderWithRotation(unused);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / scale2, 1.0F / scale2, 1.0F / scale2);
			GL11.glTranslatef(0.0F, 24.0F * unused, 0.0F);
			this.raccoonBody.render(unused);
			this.raccoonLeg1.render(unused);
			this.raccoonLeg2.render(unused);
			this.raccoonLeg3.render(unused);
			this.raccoonLeg4.render(unused);
			this.raccoonFloofyTail.renderWithRotation(unused);
			GL11.glPopMatrix();
		} else {
			this.raccoonHeadMain.renderWithRotation(unused);
			this.raccoonRightEar.renderWithRotation(unused);
			this.raccoonLeftEar.renderWithRotation(unused);
			this.raccoonSnout.renderWithRotation(unused);
			this.raccoonBody.render(unused);
			this.raccoonLeg1.render(unused);
			this.raccoonLeg2.render(unused);
			this.raccoonLeg3.render(unused);
			this.raccoonLeg4.render(unused);
			this.raccoonFloofyTail.renderWithRotation(unused);
		}

	}

	@Override
	public void setLivingAnimations(EntityLiving entity, float forward, float haltDelta, float deltaTicks) {
		EntityRaccoonClient raccoon = (EntityRaccoonClient)entity;
		if (raccoon.isRaccoonAngry()) {
			this.raccoonFloofyTail.rotateAngleY = 0.0F;
		} else if (!raccoon.isRaccoonSitting()) {
			this.raccoonFloofyTail.rotateAngleY = MathHelper.cos(forward * 0.6662F) * 1.4F * haltDelta;
		}

		float headPos = 20.5F;
		float headPosTamed = 14.5F;
		float headPosBabySitting = 19.0F;
		if (raccoon.isRaccoonSitting()) {
			if (!raccoon.isChild()) {
				this.raccoonSnout.setRotationPoint(-6.5F, headPos, -3.0F);
				this.raccoonHeadMain.setRotationPoint(-6.5F, headPos, -3.0F);
				this.raccoonRightEar.setRotationPoint(-6.5F, headPos, -3.0F);
				this.raccoonLeftEar.setRotationPoint(-6.5F, headPos, -3.0F);
				this.raccoonBody.setRotationPoint(0.0F, 21.0F, 0.0F);
				this.raccoonFloofyTail.setRotationPoint(3.25F, 19.0F, 0.75F);
				this.raccoonLeg1.setRotationPoint(-4.0F, 20.5F, 1.5F);
				this.raccoonLeg2.setRotationPoint(-4.0F, 20.5F, 1.5F);
				this.raccoonLeg3.setRotationPoint(-4.0F, 20.5F, 1.5F);
				this.raccoonLeg4.setRotationPoint(-4.0F, 20.5F, 1.5F);
			} else {
				this.raccoonSnout.setRotationPoint(-3.0F, headPosBabySitting, -5.5F);
				this.raccoonHeadMain.setRotationPoint(-3.0F, headPosBabySitting, -5.5F);
				this.raccoonRightEar.setRotationPoint(-3.0F, headPosBabySitting, -5.5F);
				this.raccoonLeftEar.setRotationPoint(-3.0F, headPosBabySitting, -5.5F);
				this.raccoonBody.setRotationPoint(3.0F, 21.0F, 0.0F);
				this.raccoonFloofyTail.setRotationPoint(6.25F, 19.0F, 0.75F);
				this.raccoonLeg1.setRotationPoint(-3.0F, 20.5F, 1.5F);
				this.raccoonLeg2.setRotationPoint(-3.0F, 20.5F, 1.5F);
				this.raccoonLeg3.setRotationPoint(-3.0F, 20.5F, 1.5F);
				this.raccoonLeg4.setRotationPoint(-3.0F, 20.5F, 1.5F);
			}

			this.raccoonHeadMain.rotateAngleX = 0.0F;
			this.raccoonHeadMain.rotateAngleY = -0.7F;
			this.raccoonBody.rotateAngleY = 1.39F;
			this.raccoonFloofyTail.rotateAngleY = 3.75F;
			this.raccoonBody.rotateAngleX = (float) (Math.PI / 2);
			this.raccoonLeg1.rotateAngleY = -1.7F;
			this.raccoonLeg2.rotateAngleY = -1.7F;
			this.raccoonLeg3.rotateAngleY = -1.7F;
			this.raccoonLeg4.rotateAngleY = -1.7F;
			this.raccoonLeg1.rotateAngleX = 5.0F;
			this.raccoonLeg2.rotateAngleX = 5.0F;
			this.raccoonLeg3.rotateAngleX = 5.0F;
			this.raccoonLeg4.rotateAngleX = 5.0F;
		} else if (raccoon.isRaccoonAngry()) {
			this.raccoonSnout.setRotationPoint(-2.0F, headPosTamed, -6.5F);
			this.raccoonHeadMain.setRotationPoint(-2.0F, headPosTamed, -6.5F);
			this.raccoonRightEar.setRotationPoint(-2.0F, headPosTamed, -6.5F);
			this.raccoonLeftEar.setRotationPoint(-2.0F, headPosTamed, -6.5F);
			this.raccoonBody.setRotationPoint(0.0F, 16.0F, 2.0F);
			this.raccoonBody.rotateAngleX = (float) (Math.PI / 2);
			this.raccoonBody.rotateAngleY = 0.0F;
			this.raccoonFloofyTail.setRotationPoint(-1.0F, 14.0F, 5.5F);
			this.raccoonLeg1.setRotationPoint(-2.5F, 17.0F, 5.0F);
			this.raccoonLeg2.setRotationPoint(0.5F, 17.0F, 5.0F);
			this.raccoonLeg3.setRotationPoint(-2.5F, 17.0F, -4.0F);
			this.raccoonLeg4.setRotationPoint(0.5F, 17.0F, -4.0F);
			this.raccoonLeg1.rotateAngleY = 0.0F;
			this.raccoonLeg2.rotateAngleY = 0.0F;
			this.raccoonLeg3.rotateAngleY = 0.0F;
			this.raccoonLeg4.rotateAngleY = 0.0F;
			this.raccoonLeg1.rotateAngleX = MathHelper.cos(forward * 0.6662F) * 1.4F * haltDelta;
			this.raccoonLeg2.rotateAngleX = MathHelper.cos(forward * 0.6662F + (float) Math.PI) * 1.4F * haltDelta;
			this.raccoonLeg3.rotateAngleX = MathHelper.cos(forward * 0.6662F + (float) Math.PI) * 1.4F * haltDelta;
			this.raccoonLeg4.rotateAngleX = MathHelper.cos(forward * 0.6662F) * 1.4F * haltDelta;
		} else if (raccoon.isRaccoonTamed() && !raccoon.isRaccoonSitting()) {
			if (!raccoon.isChild()) {
				this.raccoonSnout.setRotationPoint(-2.0F, headPosTamed, -6.5F);
				this.raccoonHeadMain.setRotationPoint(-2.0F, headPosTamed, -6.5F);
				this.raccoonRightEar.setRotationPoint(-2.0F, headPosTamed, -6.5F);
				this.raccoonLeftEar.setRotationPoint(-2.0F, headPosTamed, -6.5F);
			} else {
				this.raccoonSnout.setRotationPoint(-2.0F, headPosTamed, -8.5F);
				this.raccoonHeadMain.setRotationPoint(-2.0F, headPosTamed, -8.5F);
				this.raccoonRightEar.setRotationPoint(-2.0F, headPosTamed, -8.5F);
				this.raccoonLeftEar.setRotationPoint(-2.0F, headPosTamed, -8.5F);
			}

			this.raccoonBody.setRotationPoint(0.0F, 16.0F, 2.0F);
			this.raccoonBody.rotateAngleX = (float) (Math.PI / 2);
			this.raccoonBody.rotateAngleY = 0.0F;
			this.raccoonFloofyTail.setRotationPoint(-1.0F, 14.0F, 5.5F);
			this.raccoonLeg1.setRotationPoint(-2.5F, 17.0F, 5.0F);
			this.raccoonLeg2.setRotationPoint(0.5F, 17.0F, 5.0F);
			this.raccoonLeg3.setRotationPoint(-2.5F, 17.0F, -4.0F);
			this.raccoonLeg4.setRotationPoint(0.5F, 17.0F, -4.0F);
			this.raccoonLeg1.rotateAngleY = 0.0F;
			this.raccoonLeg2.rotateAngleY = 0.0F;
			this.raccoonLeg3.rotateAngleY = 0.0F;
			this.raccoonLeg4.rotateAngleY = 0.0F;
			this.raccoonLeg1.rotateAngleX = MathHelper.cos(forward * 0.6662F) * 1.4F * haltDelta;
			this.raccoonLeg2.rotateAngleX = MathHelper.cos(forward * 0.6662F + (float) Math.PI) * 1.4F * haltDelta;
			this.raccoonLeg3.rotateAngleX = MathHelper.cos(forward * 0.6662F + (float) Math.PI) * 1.4F * haltDelta;
			this.raccoonLeg4.rotateAngleX = MathHelper.cos(forward * 0.6662F) * 1.4F * haltDelta;
		} else {
			this.raccoonSnout.setRotationPoint(-2.0F, headPosTamed, -6.5F);
			this.raccoonHeadMain.setRotationPoint(-2.0F, headPosTamed, -6.5F);
			this.raccoonRightEar.setRotationPoint(-2.0F, headPosTamed, -6.5F);
			this.raccoonLeftEar.setRotationPoint(-2.0F, headPosTamed, -6.5F);
			this.raccoonBody.setRotationPoint(0.0F, 16.0F, 2.0F);
			this.raccoonBody.rotateAngleX = (float) (Math.PI / 2);
			this.raccoonBody.rotateAngleY = 0.0F;
			this.raccoonFloofyTail.setRotationPoint(-1.0F, 14.0F, 8.0F);
			this.raccoonLeg1.setRotationPoint(-2.5F, 17.0F, 5.0F);
			this.raccoonLeg2.setRotationPoint(0.5F, 17.0F, 5.0F);
			this.raccoonLeg3.setRotationPoint(-2.5F, 17.0F, -4.0F);
			this.raccoonLeg4.setRotationPoint(0.5F, 17.0F, -4.0F);
			this.raccoonLeg1.rotateAngleY = 0.0F;
			this.raccoonLeg2.rotateAngleY = 0.0F;
			this.raccoonLeg3.rotateAngleY = 0.0F;
			this.raccoonLeg4.rotateAngleY = 0.0F;
			this.raccoonLeg1.rotateAngleX = MathHelper.cos(forward * 0.6662F) * 1.4F * haltDelta;
			this.raccoonLeg2.rotateAngleX = MathHelper.cos(forward * 0.6662F + (float) Math.PI) * 1.4F * haltDelta;
			this.raccoonLeg3.rotateAngleX = MathHelper.cos(forward * 0.6662F + (float) Math.PI) * 1.4F * haltDelta;
			this.raccoonLeg4.rotateAngleX = MathHelper.cos(forward * 0.6662F) * 1.4F * haltDelta;
		}

		float combinedAngles = raccoon.getInterestedAngle(deltaTicks) + raccoon.getShakeAngle(deltaTicks, 0.0F);
		this.raccoonHeadMain.rotateAngleZ = combinedAngles;
		this.raccoonRightEar.rotateAngleZ = combinedAngles;
		this.raccoonLeftEar.rotateAngleZ = combinedAngles;
		this.raccoonSnout.rotateAngleZ = combinedAngles;
		this.raccoonBody.rotateAngleZ = raccoon.getShakeAngle(deltaTicks, -0.16F);
		this.raccoonFloofyTail.rotateAngleZ = raccoon.getShakeAngle(deltaTicks, -0.2F);
		if (raccoon.getRaccoonShaking()) {
			float light = raccoon.getEntityBrightness(deltaTicks) * raccoon.getShadingWhileShaking(deltaTicks);
			GL11.glColor3f(light, light, light);
		}

	}

	@Override
	public void setRotationAngles(float forward, float haltDelta, float tick, float yaw, float pitch, float unused) {
		super.setRotationAngles(forward, haltDelta, tick, yaw, pitch, unused);
		this.raccoonHeadMain.rotateAngleX = pitch / 180.0F / (float)Math.PI;
		this.raccoonHeadMain.rotateAngleY = yaw / 180.0F / (float)Math.PI;
		this.raccoonRightEar.rotateAngleY = this.raccoonHeadMain.rotateAngleY;
		this.raccoonRightEar.rotateAngleX = this.raccoonHeadMain.rotateAngleX;
		this.raccoonLeftEar.rotateAngleY = this.raccoonHeadMain.rotateAngleY;
		this.raccoonLeftEar.rotateAngleX = this.raccoonHeadMain.rotateAngleX;
		this.raccoonSnout.rotateAngleY = this.raccoonHeadMain.rotateAngleY;
		this.raccoonSnout.rotateAngleX = this.raccoonHeadMain.rotateAngleX;
		this.raccoonFloofyTail.rotateAngleX = tick;
	}
}
