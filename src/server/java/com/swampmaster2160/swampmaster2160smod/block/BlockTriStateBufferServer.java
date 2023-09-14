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

public class BlockTriStateBufferServer extends BlockTriStateServer {
	public BlockTriStateBufferServer(int id) {
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

	// Get the direction a buffer is facing
	Direction6Enum getDirectionFacing(World world, int x, int y, int z) {
		return Direction6Enum.fromInt(world.getBlockMetadata(x, y, z));
	}

	@Override
	public TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		// Don't bother checking if we've already checked this block
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return TriStateStateEnum.ERROR;
		}
		super.getTriStateState(world, x, y, z, directionTowards, visited);
		// Get buffer direction
		Direction6Enum directionFacing = getDirectionFacing(world, x, y, z);
		Direction6Enum directionTakingStateFrom = getDirectionFacing(world, x, y, z).getOpposite();
		// Getting the state as the block the buffer has it's back towards gives a floating state
		if (directionTowards == directionFacing) {
			return TriStateStateEnum.FLOATING;
		}
		// Get the state from the block the buffer is facing away from or return a floating state if there is no tri-state block there
		int neighborX = x + directionTakingStateFrom.xOffset;
		int neighborY = y + directionTakingStateFrom.yOffset;
		int neighborZ = z + directionTakingStateFrom.zOffset;
		int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
		if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
			BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
			return neighborBlock.getTriStateState(world, neighborX, neighborY, neighborZ, directionTakingStateFrom, visited);
		}
		return TriStateStateEnum.FLOATING;
	}

	@Override
	public TriStateStateEnum getTriStateStateFromSources(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		// The buffer is a source so give the state of the block it's facing away from
		return getTriStateState(world, x, y, z, directionTowards, visited);
	}

	@Override
	public void triStateStateMayNeedChanging(World world, int x, int y, int z, Set<int[]> visited) {
		// Don't bother checking if we've already checked this block
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return;
		}
		super.triStateStateMayNeedChanging(world, x, y, z, visited);
		// Tell all the blocks except the block the buffer has it's back towards that they may need to change
		Direction6Enum directionTakingStateFrom = getDirectionFacing(world, x, y, z).getOpposite();
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			if (direction == directionTakingStateFrom) continue;
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
				BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
				neighborBlock.triStateStateMayNeedChanging(world, neighborX, neighborY, neighborZ, visited);
			}
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
}
