package com.swampmaster2160.swampmaster2160smod.block;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;

import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.entity.EntityLiving;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.level.World;

public class BlockTriStateBufferClient extends BlockTriStateClient {
	public BlockTriStateBufferClient(int id) {
		super(id);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player) {
		Direction6Enum rotation = determinePistonStyleOrientation(world, x, y, z, (EntityPlayer)player);
		world.setBlockMetadataWithNotify(x, y, z, rotation.intValue);
	}

	// Sets textures for each side of the block.
	@Override
	protected void allocateTextures() {
		// Get the name of the block
		String name = this.getBlockName().replace("tile.", "");
		// Get the name of the side textures
		String arrow_texture_name = name + "_arrow";
		// Set the texture for each side
		addTexture(name, Face.ALL);

		addTexture(arrow_texture_name, Face.NORTH, Direction6Enum.UP.intValue, false, true);
		addTexture(arrow_texture_name, Face.EAST,  Direction6Enum.UP.intValue, false, true);
		addTexture(arrow_texture_name, Face.SOUTH, Direction6Enum.UP.intValue, false, true);
		addTexture(arrow_texture_name, Face.WEST,  Direction6Enum.UP.intValue, false, true);

		addTexture(arrow_texture_name, Face.NORTH, Direction6Enum.DOWN.intValue, false, true);
		addTexture(arrow_texture_name, Face.EAST,  Direction6Enum.DOWN.intValue, false, true);
		addTexture(arrow_texture_name, Face.SOUTH, Direction6Enum.DOWN.intValue, false, true);
		addTexture(arrow_texture_name, Face.WEST,  Direction6Enum.DOWN.intValue, false, true);

		addTexture(arrow_texture_name, Face.TOP,    Direction6Enum.NORTH.intValue, false, true);
		addTexture(arrow_texture_name, Face.BOTTOM, Direction6Enum.NORTH.intValue, false, true);
		addTexture(arrow_texture_name, Face.NORTH,  Direction6Enum.NORTH.intValue, false, true);
		addTexture(arrow_texture_name, Face.SOUTH,  Direction6Enum.NORTH.intValue, false, true);

		addTexture(arrow_texture_name, Face.TOP,    Direction6Enum.SOUTH.intValue, false, true);
		addTexture(arrow_texture_name, Face.BOTTOM, Direction6Enum.SOUTH.intValue, false, true);
		addTexture(arrow_texture_name, Face.NORTH,  Direction6Enum.SOUTH.intValue, false, true);
		addTexture(arrow_texture_name, Face.SOUTH,  Direction6Enum.SOUTH.intValue, false, true);

		addTexture(arrow_texture_name, Face.TOP,    Direction6Enum.EAST.intValue, false, true);
		addTexture(arrow_texture_name, Face.BOTTOM, Direction6Enum.EAST.intValue, false, true);
		addTexture(arrow_texture_name, Face.EAST,   Direction6Enum.EAST.intValue, false, true);
		addTexture(arrow_texture_name, Face.WEST,   Direction6Enum.EAST.intValue, false, true);

		addTexture(arrow_texture_name, Face.TOP,    Direction6Enum.WEST.intValue, false, true);
		addTexture(arrow_texture_name, Face.BOTTOM, Direction6Enum.WEST.intValue, false, true);
		addTexture(arrow_texture_name, Face.EAST,   Direction6Enum.WEST.intValue, false, true);
		addTexture(arrow_texture_name, Face.WEST,   Direction6Enum.WEST.intValue, false, true);
	}

	@Override
	public int getRenderType() {
		return 16;
	}
}
