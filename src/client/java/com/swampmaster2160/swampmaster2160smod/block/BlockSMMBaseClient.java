package com.swampmaster2160.swampmaster2160smod.block;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;

import net.minecraft.src.game.MathHelper;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.level.World;

/**
 * Base class for blocks in SwampMaster2160's Mod with useful methods
 */
public abstract class BlockSMMBaseClient extends Block {
	/**
	 * For registering a block.
	 * @param id the that is being assigned for registration
	 * @param material The material for the block, eg. Material.rock
	 */
	protected BlockSMMBaseClient(int id, Material material) {
		super(id, material);
	}

	/**
	 * Determines the direction the block should face when placed if we want it to face the same way a piston would.
	 * @param world The world that the block is being placed in
	 * @param x The x coordinate the block is being placed at
	 * @param y The y coordinate the block is being placed at
	 * @param z The z coordinate the block is being placed at
	 * @param player The player placing the block
	 * @return The direction the block should face
	 */
	protected static Direction6Enum determinePistonStyleOrientation(World world, int x, int y, int z, EntityPlayer player) {
		if (MathHelper.abs((float)player.posX - (float)x) < 2.0F && MathHelper.abs((float)player.posZ - (float)z) < 2.0F) {
			double playery = player.posY + 1.82 - (double)player.yOffset;
			if (playery - (double)y > 2.0) {
				return Direction6Enum.DOWN;
			}

			if ((double)y - playery > 0.0) {
				return Direction6Enum.UP;
			}
		}

		int rotation = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5) & 3;
		switch (rotation) {
			case 0:
				return Direction6Enum.SOUTH;
			case 1:
				return Direction6Enum.WEST;
			case 2:
				return Direction6Enum.NORTH;
			case 3:
				return Direction6Enum.EAST;
			default:
				return Direction6Enum.UP;
		}
	}
}
