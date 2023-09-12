package com.swampmaster2160.swampmaster2160smod.block;

import java.util.Set;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModClient;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.level.World;
import java.util.HashSet;

public class BlockTriStateSignalClient extends BlockTriStateClient {
	public BlockTriStateSignalClient(int id) {
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
				if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
					BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
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
			if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
				BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
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
			if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
				BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
				neighborBlock.triStateStateMayNeedChanging(world, neighborX, neighborY, neighborZ);
			}
		}
	}

	// Sets textures for each side of the block.
	@Override
	protected void allocateTextures() {
		// Get the name of the block
		String name = this.getBlockName().replace("tile.", "");
		// Get the name of the side textures
		String floating_texture_name = name + "_floating";
		String true_texture_name = name + "_true";
		String false_texture_name = name + "_false";
		String error_texture_name = name + "_error";
		// Set the texture for each side
		this.addTexture(floating_texture_name, Face.ALL, TriStateStateEnum.FLOATING.intValue);
		this.addTexture(true_texture_name, Face.ALL, TriStateStateEnum.TRUE.intValue);
		this.addTexture(false_texture_name, Face.ALL, TriStateStateEnum.FALSE.intValue);
		this.addTexture(error_texture_name, Face.ALL, TriStateStateEnum.ERROR.intValue);
	}
}
