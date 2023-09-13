package com.swampmaster2160.swampmaster2160smod.block;

import java.util.HashSet;
import java.util.Set;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModClient;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Block;
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

	Direction6Enum getDirectionFacing(World world, int x, int y, int z) {
		return Direction6Enum.fromInt(world.getBlockMetadata(x, y, z));
	}

	@Override
	public int getRenderType() {
		return 16;
	}

	@Override
	public TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom) {
		Direction6Enum directionFacing = getDirectionFacing(world, x, y, z);
		Direction6Enum directionTakingStateFrom = getDirectionFacing(world, x, y, z).getOpposite();
		if (directionFrom == directionFacing) {
			return TriStateStateEnum.FLOATING;
		}
		int neighborX = x + directionTakingStateFrom.xOffset;
		int neighborY = y + directionTakingStateFrom.yOffset;
		int neighborZ = z + directionTakingStateFrom.zOffset;
		int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
		if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
			BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
			return neighborBlock.getTriStateState(world, neighborX, neighborY, neighborZ, directionTakingStateFrom);
		}
		return TriStateStateEnum.FLOATING;
	}

	@Override
	public TriStateStateEnum getTriStateStateFromSources(World world, int x, int y, int z, Direction6Enum directionFrom, Set<int[]> visited) {
		super.getTriStateStateFromSources(world, x, y, z, directionFrom, visited);
		return getTriStateState(world, x, y, z, directionFrom);
	}

	@Override
	public void triStateStateMayNeedChanging(World world, int x, int y, int z, Set<int[]> visited) {
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return;
		}
		super.triStateStateMayNeedChanging(world, x, y, z, visited);
		Direction6Enum directionFacing = getDirectionFacing(world, x, y, z);
		Direction6Enum directionTakingStateFrom = getDirectionFacing(world, x, y, z).getOpposite();
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			if (direction == directionTakingStateFrom) continue;
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
				BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
				neighborBlock.triStateStateMayNeedChanging(world, neighborX, neighborY, neighborZ, new HashSet<int[]>());
			}
		}
	}

	@Override
	public void setTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom, TriStateStateEnum newState) {
		triStateStateMayNeedChanging(world, x, y, z, new HashSet<int[]>());
	}
}
