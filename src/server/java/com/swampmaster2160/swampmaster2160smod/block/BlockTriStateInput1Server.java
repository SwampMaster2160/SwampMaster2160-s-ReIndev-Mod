package com.swampmaster2160.swampmaster2160smod.block;

import java.util.HashSet;
import java.util.Set;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModServer;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.entity.EntityLiving;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.level.World;

public class BlockTriStateInput1Server extends BlockTriStateServer {
	public BlockTriStateInput1Server(int id) {
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

	// Get the direction a input is facing
	Direction6Enum getDirectionFacing(World world, int x, int y, int z) {
		return Direction6Enum.fromInt(world.getBlockMetadata(x, y, z));
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
		if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
			BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
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
		if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
			BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
			return neighborBlock.getTriStateState(world, neighborX, neighborY, neighborZ, directionTowards, visited);
		}
		return TriStateStateEnum.FLOATING;
	}
}
