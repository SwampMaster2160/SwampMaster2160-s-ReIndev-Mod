package com.swampmaster2160.swampmaster2160smod.entity;

import net.minecraft.src.game.MathHelper;
//import net.minecraft.src.game.achievements.AchievementList;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.effect.Effect;
import net.minecraft.src.game.entity.Entity;
import net.minecraft.src.game.entity.EntityLiving;
import net.minecraft.src.game.entity.PathEntity;
import net.minecraft.src.game.entity.animals.EntityAnimal;
import net.minecraft.src.game.entity.other.EntityArrow;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemCookie;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.nbt.NBTTagCompound;

public class EntityRaccoonServer extends EntityAnimal {
	private boolean looksWithInterest = false;
	private float field_25048_b;
	private float field_25054_c;
	private boolean isRaccoonShaking;
	private boolean field_25052_g;
	private float timeRaccoonIsShaking;
	private float prevTimeRaccoonIsShaking;

	public EntityRaccoonServer(World world) {
		super(world);
		this.texture = "/mob/animals/raccoon/raccoon_generic.png";
		this.setSize(0.8F, 0.8F);
		this.moveSpeed = 1.0F;
		this.health = 12;
	}

	private EntityRaccoonServer prepareBabyAnimal() {
		EntityRaccoonServer babyRaccoon = new EntityRaccoonServer(this.worldObj);
		String owner = this.getRaccoonOwner();
		babyRaccoon.health = 12;
		if (owner != null && owner.trim().length() > 0) {
			babyRaccoon.setOwner(owner);
			babyRaccoon.setTamed(true);
		}

		return babyRaccoon;
	}

	/*@Override
	public Item getSpawnEgg() {
		return SwampMaster2160sModServer.raccoonSpawnEgg;
	}*/

	protected EntityRaccoonServer makeBaby(EntityAnimal animal) {
		return this.prepareBabyAnimal();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, (byte)0);
		this.dataWatcher.addObject(17, "");
		this.dataWatcher.addObject(18, this.health);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void playLivingSound() {
		super.playLivingSound();
	}

	/*@Override
	public String getEntityTexture() {
		if (this.isRaccoonTamed() && !this.isRaccoonSitting()) {
			return "/mob/animals/raccoon/raccoon_tame.png";
		} else if (this.isRaccoonTamed() && this.isRaccoonSitting()) {
			return "/mob/animals/raccoon/raccoon_sleeping.png";
		} else {
			return this.isRaccoonAngry() ? "/mob/animals/raccoon/raccoon_angry.png" : super.getEntityTexture();
		}
	}*/

	@Override
	public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
		super.writeEntityToNBT(nBTTagCompound);
		nBTTagCompound.setBoolean("Angry", this.isRaccoonAngry());
		nBTTagCompound.setBoolean("Sitting", this.isRaccoonSitting());
		if (this.getRaccoonOwner() == null) {
			nBTTagCompound.setString("Owner", "");
		} else {
			nBTTagCompound.setString("Owner", this.getRaccoonOwner());
		}

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
		super.readEntityFromNBT(nBTTagCompound);
		this.setRaccoonAngry(nBTTagCompound.getBoolean("Angry"));
		this.setRaccoonSitting(nBTTagCompound.getBoolean("Sitting"));
		String var2 = nBTTagCompound.getString("Owner");
		if (var2.length() > 0) {
			this.setOwner(var2);
			this.setTamed(true);
		}

	}

	@Override
	public boolean canDespawn() {
		return !this.isRaccoonTamed();
	}

	@Override
	protected String getLivingSound() {
		if (this.isRaccoonAngry()) {
			return "mob.fox.aggro";
		} else if (this.rand.nextInt(3) != 0) {
			return this.isRaccoonSitting() ? "mob.fox.sleep" : "mob.fox.idle";
		} else {
			return this.isRaccoonTamed() && this.dataWatcher.getWatchableObjectInteger(18) < 10
				? "mob.fox.whine"
				: "mob.fox.idle";
		}
	}

	@Override
	protected String getHurtSound() {
		return "mob.fox.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.fox.death";
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	protected int getDropItemId() {
		return -1;
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		if (!this.hasAttacked && !this.hasPath() && this.isRaccoonTamed() && this.ridingEntity == null) {
			EntityPlayer entityPlayer = this.worldObj.getPlayerEntityByName(this.getRaccoonOwner());
			if (entityPlayer != null) {
				float var2 = entityPlayer.getDistanceToEntity(this);
				if (var2 > 5.0F) {
					this.getPathOrWalkableBlock(entityPlayer, var2);
				}
			} else if (!this.isInWater()) {
				this.setRaccoonSitting(true);
			}
		} /*else if (this.playerToAttack == null
			&& !this.hasPath()
			&& !this.isRaccoonTamed()
			&& this.worldObj.rand.nextInt(100) == 0) {
			List<?> list = this.worldObj
				.getEntitiesWithinAABB(
					EntityChicken.class,
					AxisAlignedBB.getBoundingBoxFromPool(
							this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0
						)
						.expand(16.0, 4.0, 16.0)
				);
			if (!list.isEmpty()) {
				this.setTarget((Entity)list.get(this.worldObj.rand.nextInt(list.size())));
			}
		}*/ /*else if (this.playerToAttack == null
			&& !this.hasPath()
			&& !this.isRaccoonSitting()
			&& this.worldObj.rand.nextInt(20) == 0) {
			List<?> var1 = this.worldObj
				.getEntitiesWithinAABB(
					EntityBall.class,
					AxisAlignedBB.getBoundingBoxFromPool(
							this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0
						)
						.expand(16.0, 4.0, 16.0)
				);
			if (!var1.isEmpty()) {
				this.setTarget((Entity)var1.get(this.worldObj.rand.nextInt(var1.size())));
			}
		}*/

		if (this.isInWater()) {
			this.setRaccoonSitting(false);
		}

		/*if (!this.worldObj.multiplayerWorld) {
			this.dataWatcher.updateObject(18, this.health);
		}*/

	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.looksWithInterest = false;
		if (this.hasCurrentTarget() && !this.hasPath() && !this.isRaccoonAngry()) {
			Entity entity = this.getCurrentTarget();
			if (entity instanceof EntityPlayer) {
				EntityPlayer entityPlayer = (EntityPlayer)entity;
				ItemStack itemStack = entityPlayer.inventory.getCurrentItem();
				if (itemStack != null
					&& (this.isRaccoonTamed() || !(itemStack.itemID == Item.porkRaw.itemID || itemStack.itemID == Item.porkCooked.itemID))
					&& this.isRaccoonTamed()
					&& Item.itemsList[itemStack.itemID] instanceof ItemCookie) {
					//this.looksWithInterest = ((ItemCookie)Item.itemsList[itemStack.itemID]).getIsWolfsFavoriteMeat();
				}
			}
		}

		/*if (!this.isMultiplayerEntity && this.isRaccoonShaking && !this.field_25052_g && !this.hasPath() && this.onGround) {
			this.field_25052_g = true;
			this.timeRaccoonIsShaking = 0.0F;
			this.prevTimeRaccoonIsShaking = 0.0F;
			this.worldObj.func_9425_a(this, (byte)8);
		}*/

		if (this.isRaccoonTamed() && this.isRaccoonSitting()) {
			for(int var3 = 0; var3 < 1; ++var3) {
				if (this.rand.nextInt(7) == 0) {
					double var4 = this.rand.nextGaussian() * 0.02;
					double var6 = this.rand.nextGaussian() * 0.02;
					double var8 = this.rand.nextGaussian() * 0.02;
					this.worldObj
						.spawnParticle(
							"sleep",
							this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width,
							this.posY + 0.125 + (double)(this.rand.nextFloat() * this.height),
							this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width,
							var4,
							var6,
							var8
						);
				}
			}
		}

	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		this.field_25054_c = this.field_25048_b;
		if (this.looksWithInterest) {
			this.field_25048_b += (1.0F - this.field_25048_b) * 0.4F;
		} else {
			this.field_25048_b += (0.0F - this.field_25048_b) * 0.4F;
		}

		if (this.looksWithInterest) {
			this.numTicksToChaseTarget = 10;
		}

		if (this.isWet()) {
			this.isRaccoonShaking = true;
			this.field_25052_g = false;
			this.timeRaccoonIsShaking = 0.0F;
			this.prevTimeRaccoonIsShaking = 0.0F;
		} else if ((this.isRaccoonShaking || this.field_25052_g) && this.field_25052_g) {
			if (this.timeRaccoonIsShaking == 0.0F) {
				this.worldObj
					.playSoundAtEntity(
						this,
						"mob.wolf.shake",
						this.getSoundVolume(),
						(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F
					);
			}

			this.prevTimeRaccoonIsShaking = this.timeRaccoonIsShaking;
			this.timeRaccoonIsShaking += 0.05F;
			if (this.prevTimeRaccoonIsShaking >= 2.0F) {
				this.isRaccoonShaking = false;
				this.field_25052_g = false;
				this.prevTimeRaccoonIsShaking = 0.0F;
				this.timeRaccoonIsShaking = 0.0F;
			}

			if (this.timeRaccoonIsShaking > 0.4F) {
				float var1 = (float)this.boundingBox.minY;
				int var2 = (int)(MathHelper.sin((this.timeRaccoonIsShaking - 0.4F) * (float) Math.PI) * 7.0F);

				for(int var3 = 0; var3 < var2; ++var3) {
					float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					this.worldObj
						.spawnParticle(
							"splash",
							this.posX + (double)var4,
							(double)(var1 + 0.8F),
							this.posZ + (double)var5,
							this.motionX,
							this.motionY,
							this.motionZ
						);
				}
			}
		}

	}

	public boolean getRaccoonShaking() {
		return this.isRaccoonShaking;
	}

	public float getShadingWhileShaking(float arg1) {
		return 0.75F
			+ (this.prevTimeRaccoonIsShaking + (this.timeRaccoonIsShaking - this.prevTimeRaccoonIsShaking) * arg1) / 2.0F * 0.25F;
	}

	public float getShakeAngle(float arg1, float arg2) {
		float var3 = (this.prevTimeRaccoonIsShaking + (this.timeRaccoonIsShaking - this.prevTimeRaccoonIsShaking) * arg1 + arg2)
			/ 1.8F;
		if (var3 < 0.0F) {
			var3 = 0.0F;
		} else if (var3 > 1.0F) {
			var3 = 1.0F;
		}

		return MathHelper.sin(var3 * (float) Math.PI)
			* MathHelper.sin(var3 * (float) Math.PI * 11.0F)
			* 0.15F
			* (float) Math.PI;
	}

	public float getInterestedAngle(float arg1) {
		return (this.field_25054_c + (this.field_25048_b - this.field_25054_c) * arg1) * 0.15F * (float) Math.PI;
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.8F;
	}

	/*@Override
	protected int func_25026_x() {
		return this.isRaccoonSitting() ? 20 : super.func_25026_x();
	}*/

	private void getPathOrWalkableBlock(Entity entity, float arg2) {
		PathEntity pathEntity = this.worldObj.getPathToEntity(this, entity, 16.0F);
		if (pathEntity == null && arg2 > 12.0F) {
			int var4 = MathHelper.floor_double(entity.posX) - 2;
			int var5 = MathHelper.floor_double(entity.posZ) - 2;
			int var6 = MathHelper.floor_double(entity.boundingBox.minY);

			for(int var7 = 0; var7 <= 4; ++var7) {
				for(int var8 = 0; var8 <= 4; ++var8) {
					if ((var7 < 1 || var8 < 1 || var7 > 3 || var8 > 3)
						&& this.worldObj.isBlockNormalCube(var4 + var7, var6 - 1, var5 + var8)
						&& !this.worldObj.isBlockNormalCube(var4 + var7, var6, var5 + var8)
						&& !this.worldObj.isBlockNormalCube(var4 + var7, var6 + 1, var5 + var8)) {
						this.setLocationAndAngles(
							(double)((float)(var4 + var7) + 0.5F),
							(double)var6,
							(double)((float)(var5 + var8) + 0.5F),
							this.rotationYaw,
							this.rotationPitch
						);
						return;
					}
				}
			}
		} else {
			this.setPathToEntity(pathEntity);
		}

	}

	@Override
	protected boolean isMovementCeased() {
		return this.isRaccoonSitting() || this.field_25052_g;
	}

	@Override
	public boolean attackEntityFrom(Entity entity, int arg2) {
		this.setRaccoonSitting(false);
		if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
			arg2 = (arg2 + 1) / 2;
		}

		if (!super.attackEntityFrom(entity, arg2)) {
			return false;
		} else {
			if (!this.isRaccoonTamed() && !this.isRaccoonAngry()) {
				if (entity instanceof EntityPlayer) {
					this.setRaccoonAngry(true);
					this.playerToAttack = entity;
				}

				if (entity instanceof EntityArrow && ((EntityArrow)entity).shootingEntity != null) {
					entity = ((EntityArrow)entity).shootingEntity;
				}

				/*if (entity instanceof EntityLiving) {
					for(Entity var5 : this.worldObj
						.getEntitiesWithinAABB(
							EntityRaccoonServer.class,
							AxisAlignedBB.getBoundingBoxFromPool(
									this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0
								)
								.expand(16.0, 4.0, 16.0)
						)) {
						EntityRaccoonServer entityRaccoon = (EntityRaccoonServer)var5;
						if (!entityRaccoon.isRaccoonTamed() && entityRaccoon.playerToAttack == null) {
							entityRaccoon.playerToAttack = entity;
							if (entity instanceof EntityPlayer) {
								entityRaccoon.setRaccoonAngry(true);
							}
						}
					}
				}*/
			} else if (entity != this && entity != null) {
				if (this.isRaccoonTamed()
					&& entity instanceof EntityPlayer
					&& ((EntityPlayer)entity).username.equalsIgnoreCase(this.getRaccoonOwner())) {
					return true;
				}

				this.playerToAttack = entity;
			}

			return true;
		}
	}

	@Override
	protected Entity findPlayerToAttack() {
		return this.isRaccoonAngry() ? this.worldObj.getClosestPlayerToEntity(this, 16.0) : null;
	}

	@Override
	protected void attackEntity(Entity entity, float arg2) {
		if (!(arg2 > 2.0F) || !(arg2 < 6.0F) || this.rand.nextInt(10) != 0) {
			if ((double)arg2 < 1.5
				&& entity.boundingBox.maxY > this.boundingBox.minY
				&& entity.boundingBox.minY < this.boundingBox.maxY) {
				this.attackTime = 20;
				byte var3 = 2;
				if (this.isRaccoonTamed()) {
					var3 = 4;
				}

				entity.attackEntityFrom(this, var3);
				if (entity instanceof EntityLiving) ((EntityLiving)entity).applyEffect(Effect.poison, 100, (byte)1);
			} else {
				super.attackEntity(entity, arg2);
			}
		} else if (this.onGround) {
			double var8 = entity.posX - this.posX;
			double var5 = entity.posZ - this.posZ;
			float var7 = MathHelper.sqrt_double(var8 * var8 + var5 * var5);
			this.motionX = var8 / (double)var7 * 0.5 * 0.8F + this.motionX * 0.2F;
			this.motionZ = var5 / (double)var7 * 0.5 * 0.8F + this.motionZ * 0.2F;
			this.motionY = 0.4F;
		}

	}

	@Override
	public boolean interact(EntityPlayer entityPlayer) {
		ItemStack itemStack = entityPlayer.inventory.getCurrentItem();
		if (super.interact(entityPlayer)) {
			return true;
		} else {
			if (!this.isRaccoonTamed()) {
				if (itemStack != null && (itemStack.itemID == Item.porkRaw.itemID || itemStack.itemID == Item.porkCooked.itemID) && !this.isRaccoonAngry()) {
					this.worldObj
						.playSoundAtEntity(
							this,
							"mob.fox.eat",
							this.getSoundVolume(),
							(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F
						);
					if (/*!entityPlayer.capabilities.isCreativeMode*/true) {
						--itemStack.stackSize;
					}

					if (itemStack.stackSize <= 0) {
						entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
					}

					/*if (!this.worldObj.multiplayerWorld) {
						if (this.rand.nextInt(3) == 0) {
							this.setTamed(true);
							entityPlayer.triggerAchievement(AchievementList.tameFox);
							this.setPathToEntity(null);
							this.setRaccoonSitting(true);
							this.health = 20;
							this.setOwner(entityPlayer.username);
							this.showHeartsOrSmokeFX(true);
							this.worldObj.func_9425_a(this, (byte)7);
						} else {
							this.showHeartsOrSmokeFX(false);
							this.worldObj.func_9425_a(this, (byte)6);
						}
					}*/

					return true;
				}
			} else {
				if (itemStack != null && Item.itemsList[itemStack.itemID] instanceof ItemCookie) {
					ItemCookie itemCookie = (ItemCookie)Item.itemsList[itemStack.itemID];
					if (itemCookie.getIsFoxesFavoriteFood() && this.dataWatcher.getWatchableObjectInteger(18) == 20) {
						super.interact(entityPlayer);
						return true;
					}

					if (itemCookie.getIsFoxesFavoriteFood() && this.dataWatcher.getWatchableObjectInteger(18) < 20) {
						this.worldObj
							.playSoundAtEntity(
								this,
								"mob.fox.eat",
								this.getSoundVolume(),
								(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F
							);
						if (/*!entityPlayer.capabilities.isCreativeMode*/true) {
							--itemStack.stackSize;
						}

						if (itemStack.stackSize <= 0) {
							entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
						}

						this.heal(((ItemCookie)Item.blueberry).getTripledHealAmount());
						return true;
					}
				}

				if (entityPlayer.username.equalsIgnoreCase(this.getRaccoonOwner())) {
					/*if (!this.worldObj.multiplayerWorld) {
						if (itemStack != null && itemStack.itemID == Item.bucketEmpty.itemID) {
							ItemStack foxBucket = new ItemStack(Item.bucketFox);
							NBTTagCompound nbt = new NBTTagCompound();
							nbt.setString("RaccoonName", this.getNameTag());
							nbt.setString("RaccoonOwner", this.getRaccoonOwner());
							nbt.setInteger("RaccoonHealth", this.health);
							foxBucket.setTagCompound(nbt);
							entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, foxBucket);
							this.setEntityDead();
							return true;
						}

						this.setRaccoonSitting(!this.isRaccoonSitting());
						this.isJumping = false;
						this.setPathToEntity(null);
					}*/

					return true;
				}
			}

			return false;
		}
	}

	/*private void showHeartsOrSmokeFX(boolean arg1) {
		String var2 = "heart";
		if (!arg1) {
			var2 = "smoke";
		}

		for(int var3 = 0; var3 < 7; ++var3) {
			double var4 = this.rand.nextGaussian() * 0.02;
			double var6 = this.rand.nextGaussian() * 0.02;
			double var8 = this.rand.nextGaussian() * 0.02;
			this.worldObj
				.spawnParticle(
					var2,
					this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width,
					this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height),
					this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width,
					var4,
					var6,
					var8
				);
		}

	}*/

	/*@Override
	public void handleHealthUpdate(byte arg1) {
		if (arg1 == 7) {
			this.showHeartsOrSmokeFX(true);
		} else if (arg1 == 6) {
			this.showHeartsOrSmokeFX(false);
		} else if (arg1 == 8) {
			this.field_25052_g = true;
			this.timeRaccoonIsShaking = 0.0F;
			this.prevTimeRaccoonIsShaking = 0.0F;
		} else {
			super.handleHealthUpdate(arg1);
		}

	}*/

	public float setTailRotation() {
		if (this.isRaccoonAngry()) {
			return 1.5393804F;
		} else {
			return this.isRaccoonTamed()
				? (0.55F - (float)(20 - this.dataWatcher.getWatchableObjectInteger(18)) * 0.02F) * (float) Math.PI
				: (float) (Math.PI / 5);
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 8;
	}

	public String getRaccoonOwner() {
		return this.dataWatcher.getWatchableObjectString(17);
	}

	public void setOwner(String arg1) {
		this.dataWatcher.updateObject(17, arg1);
	}

	public boolean isRaccoonSitting() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setRaccoonSitting(boolean arg1) {
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);
		if (arg1) {
			this.dataWatcher.updateObject(16, (byte)(var2 | 1));
		} else {
			this.dataWatcher.updateObject(16, (byte)(var2 & -2));
		}

	}

	public boolean isRaccoonAngry() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	public void setRaccoonAngry(boolean arg1) {
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);
		if (arg1) {
			this.dataWatcher.updateObject(16, (byte)(var2 | 2));
		} else {
			this.dataWatcher.updateObject(16, (byte)(var2 & -3));
		}

	}

	public boolean isRaccoonTamed() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 4) != 0;
	}

	public void setTamed(boolean arg1) {
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);
		if (arg1) {
			this.dataWatcher.updateObject(16, (byte)(var2 | 4));
		} else {
			this.dataWatcher.updateObject(16, (byte)(var2 & -5));
		}

	}

	@Override
	protected float getBlockPathWeight(int x, int y, int z) {
		int id = this.worldObj.getBlockId(x, y - 1, z);
		return id != Block.grass.blockID && id != Block.snowBlock.blockID
			? this.worldObj.getLightBrightness(x, y, z) - 0.5F
			: 10.0F;
	}

	@Override
	public boolean getCanSpawnHere() {
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.boundingBox.minY);
		int z = MathHelper.floor_double(this.posZ);
		int id = this.worldObj.getBlockId(x, y - 1, z);
		return (id == Block.grass.blockID || id == Block.snowBlock.blockID)
			&& this.worldObj.getLightBrightness(x, y, z) > 8
			&& this.getBlockPathWeight(x, y, z) >= 0.0F
			&& this.worldObj.checkIfAABBIsClear(this.boundingBox)
			&& this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0
			&& !this.worldObj.getIsAnyLiquid(this.boundingBox);
	}
}
