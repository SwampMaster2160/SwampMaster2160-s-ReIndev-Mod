package com.swampmaster2160.swampmaster2160smod.block;

import org.jetbrains.annotations.Nullable;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModClient;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.level.World;

public class BlockTriStateTrueClient extends BlockTriStateClient {
	public BlockTriStateTrueClient(int id) {
		super(id);
	}

	public @Nullable TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom) {
		return TriStateStateEnum.TRUE;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
				BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
				neighborBlock.setTriStateState(world, neighborX, neighborY, neighborZ, direction, TriStateStateEnum.TRUE);
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
}
