package com.swampmaster2160.swampmaster2160smod.block;

import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModClient;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.level.World;

public class BlockTriStateFalseClient extends BlockTriStateClient {
	public BlockTriStateFalseClient(int id) {
		super(id);
	}

	public @Nullable TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom) {
		return TriStateStateEnum.FALSE;
	}

	/*@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
				BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
				Set<int[]> visited = new HashSet<int[]>();
				visited.add(new int[] { x, y, z });
				System.out.println(neighborBlock.getSignalSourceCount(world, neighborX, neighborY, neighborZ, visited));
				neighborBlock.setTriStateState(world, neighborX, neighborY, neighborZ, direction, TriStateStateEnum.FALSE);
			}
		}
	}

	@Override
	public void onBlockRemoval(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
				BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
				neighborBlock.setTriStateState(world, neighborX, neighborY, neighborZ, direction, TriStateStateEnum.FLOATING);
			}
		}
	}

	@Override
	public int getSignalSourceCount(World world, int x, int y, int z, Set<int[]> visited) {
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return 0;
		}
		super.getSignalSourceCount(world, x, y, z, visited);
		return 1;
	}*/
}
