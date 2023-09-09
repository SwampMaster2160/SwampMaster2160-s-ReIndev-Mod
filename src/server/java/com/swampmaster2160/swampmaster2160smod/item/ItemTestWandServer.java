package com.swampmaster2160.swampmaster2160smod.item;

import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.nbt.NBTTagCompound;

public class ItemTestWandServer extends Item {
	public ItemTestWandServer(int id) {
		super(id - 2304 + 2048);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int xCoord, int yCoord, int zCoord, int arg7, float x, float y, float z) {
		// Return if there is no NBT tags
		if (itemstack.nbtTagCompound == null) return false;
		// Get NBT tags
		NBTTagCompound nbt = itemstack.nbtTagCompound;
		// Set block keys
		if (nbt.hasKey("new_id_for_block")) {
			int id = nbt.getInteger("new_id_for_block");
			world.setBlockWithNotify(xCoord, yCoord, zCoord, id);
		}
		if (nbt.hasKey("new_metadata_for_block")) {
			int metadata = nbt.getInteger("new_metadata_for_block");
			world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata);
		}
		// Return that the item was used
		return true;
	}
}
