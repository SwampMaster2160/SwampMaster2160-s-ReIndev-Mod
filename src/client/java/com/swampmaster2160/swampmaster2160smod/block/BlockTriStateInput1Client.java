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

public class BlockTriStateInput1Client extends BlockTriStateClient {
	public BlockTriStateInput1Client(int id) {
		super(id);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player) {
		// Get what direction the buffer should face
		Direction6Enum rotation = determinePistonStyleOrientation(world, x, y, z, (EntityPlayer)player);
		// Set the direction
		world.setBlockMetadataWithNotify(x, y, z, rotation.intValue);
		// If placed then tell all the neighbor blocks except the block the buffer has it's back towards that they may need to change
		triStateStateMayNeedChanging(world, x, y, z, new HashSet<int[]>());
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

	// Get the direction a input is facing
	Direction6Enum getDirectionFacing(World world, int x, int y, int z) {
		return Direction6Enum.fromInt(world.getBlockMetadata(x, y, z));
	}

	@Override
	public int getRenderType() {
		// Render like a piston base
		return 16;
	}

	@Override
	public void triStateStateMayNeedChanging(World world, int x, int y, int z, Set<int[]> visited) {
		// Don't bother checking if we've already checked this block
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return;
		}
		super.triStateStateMayNeedChanging(world, x, y, z, visited);
		// Tell the block the input is facing that it may need to change
		Direction6Enum direction = getDirectionFacing(world, x, y, z);
		int neighborX = x + direction.xOffset;
		int neighborY = y + direction.yOffset;
		int neighborZ = z + direction.zOffset;
		int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
		if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
			BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
			neighborBlock.triStateStateMayNeedChanging(world, neighborX, neighborY, neighborZ, visited);
		}
	}

	@Override
	public void onBlockRemoval(World world, int x, int y, int z) {
		// If removed then tell all the neighbor blocks except the block the buffer has it's back towards that they may need to change
		triStateStateMayNeedChanging(world, x, y, z, new HashSet<int[]>());
	}

	@Override
	public void setTriStateState(World world, int x, int y, int z, Direction6Enum directionTowards, TriStateStateEnum newState, Set<int[]> visited) {
		// If set then tell all the neighbor blocks except the block the buffer has it's back towards that they may need to change
		triStateStateMayNeedChanging(world, x, y, z, visited);
	}

	@Override
	public int getInputType(World world, int x, int y, int z, Direction6Enum directionTowards) {
		Direction6Enum direction = getDirectionFacing(world, x, y, z);
		if (directionTowards == direction.getOpposite()) return 1;
		return 0;
	}

	@Override
	public TriStateStateEnum getTriStateInput(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		// Don't bother checking if we've already checked this block
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return TriStateStateEnum.ERROR;
		}
		super.getTriStateInput(world, x, y, z, directionTowards, visited);
		Direction6Enum direction = getDirectionFacing(world, x, y, z);
		Direction6Enum backDirection = direction.getOpposite();
		if (directionTowards != backDirection) return null;
		int neighborX = x + backDirection.xOffset;
		int neighborY = y + backDirection.yOffset;
		int neighborZ = z + backDirection.zOffset;
		int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
		if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
			BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
			return neighborBlock.getTriStateState(world, neighborX, neighborY, neighborZ, directionTowards, visited);
		}
		return TriStateStateEnum.FLOATING;
	}
}
