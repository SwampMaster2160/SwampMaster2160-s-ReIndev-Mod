package com.swampmaster2160.swampmaster2160smod.item;

import org.jetbrains.annotations.Nullable;

import com.swampmaster2160.swampmaster2160smod.entity.EntityRaccoonServer;

//import net.minecraft.src.server.renderer.block.icon.IconRegister;
import net.minecraft.src.game.Facing;
import net.minecraft.src.game.entity.EntityLiving;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;

public class ItemSMMSpawnEggServer extends Item {
	private @Nullable String entity = null;

	public ItemSMMSpawnEggServer(int id) {
		super(id - 256);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	public void setFlavor(String flavor) {
		this.entity = flavor;
	}

	/*@Override
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon("spawn_egg_" + this.entity);
	}*/

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int facing, float xVec, float yVec, float zVec) {
		x += Facing.offsetXForSide[facing];
		y += Facing.offsetYForSide[facing];
		z += Facing.offsetZForSide[facing];
		/*if (spawnMob(world, itemstack, x, y, z, this.entity) && !player.capabilities.isCreativeMode) {
			player.inventory.decrStackSize(player.inventory.currentItem, 1);
		}*/

		return true;
	}

	public static boolean spawnMob(World world, ItemStack itemstack, int x, int y, int z, String flavor) {
		if (/*!world.multiplayerWorld*/true) {
			@Nullable EntityLiving spawnedEntity = null;
			switch (flavor) {
				case "raccoon":
					spawnedEntity = new EntityRaccoonServer(world);
					break;
			}
			if (spawnedEntity == null) return false;
			spawnedEntity.setLocationAndAngles((double)x + 0.5, (double)y + 0.05, (double)z + 0.5, 0.0F, 0.0F);
			if (itemstack.hasDisplayName()) spawnedEntity.setNameTag(itemstack.getDisplayName());
			world.entityJoinedWorld(spawnedEntity);
		}

		return true;
	}
}
