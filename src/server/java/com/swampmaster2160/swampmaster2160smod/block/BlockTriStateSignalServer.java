package com.swampmaster2160.swampmaster2160smod.block;

import java.util.Set;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModServer;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.level.World;
import java.util.HashSet;

public class BlockTriStateSignalServer extends BlockTriStateServer {
	public BlockTriStateSignalServer(int id) {
		super(id);
	}

	@Override
	public TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom) {
		return TriStateStateEnum.fromInt(world.getBlockMetadata(x, y, z));
	}

	@Override
	public void setTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom, TriStateStateEnum newState) {
		TriStateStateEnum oldState = TriStateStateEnum.fromInt(world.getBlockMetadata(x, y, z));
		if (newState != oldState) {
			world.setBlockMetadataWithNotify(x, y, z, newState.intValue);
			for (int i = 0; i < 6; i++) {
				Direction6Enum direction = Direction6Enum.fromInt(i);
				int neighborX = x + direction.xOffset;
				int neighborY = y + direction.yOffset;
				int neighborZ = z + direction.zOffset;
				int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
				if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
					BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
					neighborBlock.setTriStateState(world, neighborX, neighborY, neighborZ, direction, newState);
				}
			}
		}
	}

	@Override
	public TriStateStateEnum getTriStateStateFromSources(World world, int x, int y, int z, Direction6Enum directionFrom, Set<int[]> visited) {
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return TriStateStateEnum.FLOATING;
		}
		super.getTriStateStateFromSources(world, x, y, z, directionFrom, visited);
		TriStateStateEnum out = TriStateStateEnum.FLOATING;
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
				BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
				TriStateStateEnum neighborStateFromSource = neighborBlock.getTriStateStateFromSources(world, neighborX, neighborY, neighborZ, direction, visited);
				out = out.combine(neighborStateFromSource);
			}
		}
		return out;
	}

	@Override
	public void triStateStateMayNeedChanging(World world, int x, int y, int z) {
		TriStateStateEnum stateFromSources = getTriStateStateFromSources(world, x, y, z, null, new HashSet<int[]>());
		setTriStateState(world, x, y, z, null, stateFromSources);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		triStateStateMayNeedChanging(world, x, y, z);
	}

	@Override
	public void onBlockRemoval(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
				BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
				neighborBlock.triStateStateMayNeedChanging(world, neighborX, neighborY, neighborZ);
			}
		}
	}
}
