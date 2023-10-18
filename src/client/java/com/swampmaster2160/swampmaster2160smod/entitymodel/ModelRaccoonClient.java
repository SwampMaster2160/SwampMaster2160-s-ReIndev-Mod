package com.swampmaster2160.swampmaster2160smod.entitymodel;

import org.lwjgl.opengl.GL11;

import com.swampmaster2160.swampmaster2160smod.entity.EntityRaccoonClient;

import net.minecraft.src.client.model.ModelBase;
import net.minecraft.src.client.model.Piece;
import net.minecraft.src.game.MathHelper;
import net.minecraft.src.game.entity.EntityLiving;

public class ModelRaccoonClient extends ModelBase {
	// Positions on the raccoon texture sheet that the textures for the parts are.
	public Piece raccoonBody       = new Piece(40, 0 );
	public Piece raccoonHeadMain   = new Piece(0,  0 );
	public Piece raccoonSnout      = new Piece(0,  12);
	public Piece raccoonLeftEar    = new Piece(23, 14);
	public Piece raccoonRightEar   = new Piece(16, 14);
	public Piece raccoonLeg1       = new Piece(0,  18);
	public Piece raccoonLeg2       = new Piece(0,  18);
	public Piece raccoonLeg3       = new Piece(0,  18);
	public Piece raccoonLeg4       = new Piece(0,  18);
	public Piece raccoonFloofyTail = new Piece(40, 16);
	// Is the raccoon sleeping?
	private boolean isSleeping = false;

	public ModelRaccoonClient() {
		// Set the offsets from the rotation points and the sizes of the parts
		this.raccoonBody      .addBox(-2.5F, -5, -21 + 18.5F, 5, 10, 5, 0.0F);
		this.raccoonHeadMain  .addBox(-3, -3, -6, 6, 6, 6, 0.0F);
		this.raccoonSnout     .addBox(-1, 0, -9, 2, 2, 3, 0.0F);
		this.raccoonLeftEar   .addBox(-3, -5F, -3.5F, 2, 2, 1, 0.0F);
		this.raccoonRightEar  .addBox(1, -5F, -3.5F, 2, 2, 1, 0.0F);
		this.raccoonLeg1      .addBox(-1.0F, -1, -1.0F, 2, 5, 2, 0.0F);
		this.raccoonLeg2      .addBox(-1.0F, -1, -1.0F, 2, 5, 2, 0.0F);
		this.raccoonLeg3      .addBox(-1.0F, -1, -1.0F, 2, 5, 2, 0.0F);
		this.raccoonLeg4      .addBox(-1.0F, -1, -1.0F, 2, 5, 2, 0.0F);
		this.raccoonFloofyTail.addBox(-3.0F, -0F, -3.0F, 6, 10, 6, 0.0F);
	}

	@Override
	public void render(float forward, float haltDelta, float tick, float yaw, float pitch, float unused) {
		// Does nothing
		super.render(forward, haltDelta, tick, yaw, pitch, unused);
		// Set how each part is rotated
		this.setRotationAngles(forward, haltDelta, tick, yaw, pitch, unused);
		// Render parts
		this.raccoonHeadMain  .renderWithRotation(unused);
		this.raccoonRightEar  .renderWithRotation(unused);
		this.raccoonLeftEar   .renderWithRotation(unused);
		this.raccoonSnout     .renderWithRotation(unused);
		this.raccoonBody      .render            (unused);
		this.raccoonFloofyTail.renderWithRotation(unused);
		// Legs will be hidden is the raccoon is sleeping
		if (!isSleeping) {
			this.raccoonLeg1.render(unused);
			this.raccoonLeg2.render(unused);
			this.raccoonLeg3.render(unused);
			this.raccoonLeg4.render(unused);
		}
	}

	@Override
	public void setLivingAnimations(EntityLiving entity, float forward, float haltDelta, float deltaTicks) {
		// Get the raccoon and weather its sleeping
		EntityRaccoonClient raccoon = (EntityRaccoonClient)entity;
		isSleeping = raccoon.isRaccoonSitting();
		// Get the height offset for the parts
		float height_offset = raccoon.isRaccoonSitting()? 21.5F: 17.5F;
		// Set the position that each part should rotate around
		this.raccoonSnout.setRotationPoint(0, height_offset, -5);
		this.raccoonHeadMain.setRotationPoint(0, height_offset, -5);
		this.raccoonRightEar.setRotationPoint(0, height_offset, -5);
		this.raccoonLeftEar.setRotationPoint(0, height_offset, -5);
		this.raccoonBody.setRotationPoint(0.0F, height_offset, 0.0F);
		this.raccoonFloofyTail.setRotationPoint(0, height_offset, 4);
		this.raccoonLeg1.setRotationPoint(-1.5F, height_offset + 3.5F, 3.5F);
		this.raccoonLeg2.setRotationPoint(1.5F, height_offset + 3.5F, 3.5F);
		this.raccoonLeg3.setRotationPoint(-1.5F, height_offset + 3.5F, -3.5F);
		this.raccoonLeg4.setRotationPoint(1.5F, height_offset + 3.5F, -3.5F);
		// Get how much each part has been rotated around said points depending on the entity data
		this.raccoonBody.rotateAngleX = (float) (Math.PI / 2);
		this.raccoonLeg1.rotateAngleX = MathHelper.cos(forward * 0.6662F) * 1.4F * haltDelta;
		this.raccoonLeg2.rotateAngleX = MathHelper.cos(forward * 0.6662F + (float) Math.PI) * 1.4F * haltDelta;
		this.raccoonLeg3.rotateAngleX = MathHelper.cos(forward * 0.6662F + (float) Math.PI) * 1.4F * haltDelta;
		this.raccoonLeg4.rotateAngleX = MathHelper.cos(forward * 0.6662F) * 1.4F * haltDelta;
		this.raccoonBody.rotateAngleY = 0.0F;
		this.raccoonLeg1.rotateAngleY = 0.0F;
		this.raccoonLeg2.rotateAngleY = 0.0F;
		this.raccoonLeg3.rotateAngleY = 0.0F;
		this.raccoonLeg4.rotateAngleY = 0.0F;
		// Head rotations
		float combinedAngles = raccoon.getInterestedAngle(deltaTicks) + raccoon.getShakeAngle(deltaTicks, 0.0F);
		this.raccoonHeadMain.rotateAngleZ = combinedAngles;
		this.raccoonRightEar.rotateAngleZ = combinedAngles;
		this.raccoonLeftEar.rotateAngleZ = combinedAngles;
		this.raccoonSnout.rotateAngleZ = combinedAngles;
		// Handle shaking when coming out of water
		this.raccoonBody.rotateAngleZ = raccoon.getShakeAngle(deltaTicks, -0.16F);
		if (raccoon.getRaccoonShaking()) {
			float light = raccoon.getEntityBrightness(deltaTicks) * raccoon.getShadingWhileShaking(deltaTicks);
			GL11.glColor3f(light, light, light);
		}

	}

	@Override
	public void setRotationAngles(float forward, float haltDelta, float tick, float yaw, float pitch, float unused) {
		// Does nothing
		super.setRotationAngles(forward, haltDelta, tick, yaw, pitch, unused);
		// Rotate points depending on data given to the model
		this.raccoonHeadMain.rotateAngleX = isSleeping? 0F: pitch / 180.0F * (float)Math.PI;
		this.raccoonHeadMain.rotateAngleY = yaw / 180.0F * (float)Math.PI;
		this.raccoonRightEar.rotateAngleY = this.raccoonHeadMain.rotateAngleY;
		this.raccoonRightEar.rotateAngleX = this.raccoonHeadMain.rotateAngleX;
		this.raccoonLeftEar.rotateAngleY = this.raccoonHeadMain.rotateAngleY;
		this.raccoonLeftEar.rotateAngleX = this.raccoonHeadMain.rotateAngleX;
		this.raccoonSnout.rotateAngleY = this.raccoonHeadMain.rotateAngleY;
		this.raccoonSnout.rotateAngleX = this.raccoonHeadMain.rotateAngleX;
		this.raccoonFloofyTail.rotateAngleX = (float)Math.PI * (isSleeping? 0.5F: 0.4F);
	}
}
