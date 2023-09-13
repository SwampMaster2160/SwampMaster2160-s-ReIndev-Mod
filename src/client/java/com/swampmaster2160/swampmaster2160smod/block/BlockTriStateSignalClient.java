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
	public TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionTowards) {
		// Get the blocks state from it's metadata
		return TriStateStateEnum.fromInt(world.getBlockMetadata(x, y, z));
	}

	@Override
	public void setTriStateState(World world, int x, int y, int z, Direction6Enum directionTowards, TriStateStateEnum newState) {
		// Set the block's state if the state is diffrent and then tell neighbors to change their state
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
	public TriStateStateEnum getTriStateStateFromSources(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		// Do not visit a block twice (that may cause an infinite loop and crash the game)
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return TriStateStateEnum.FLOATING;
		}
		super.getTriStateStateFromSources(world, x, y, z, directionTowards, visited);
		// Get the sources from the neighboring blocks and combine
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
	public void triStateStateMayNeedChanging(World world, int x, int y, int z, Set<int[]> visited) {
		// Do not visit a block twice (that may cause an infinite loop and crash the game)
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return;
		}
		super.triStateStateMayNeedChanging(world, x, y, z, visited);
		// Get the state from the sources that affect this block
		TriStateStateEnum stateFromSources = getTriStateStateFromSources(world, x, y, z, null, new HashSet<int[]>());
		setTriStateState(world, x, y, z, null, stateFromSources);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		// When we add a block we should see what state it should have and weather it affects other blocks
		triStateStateMayNeedChanging(world, x, y, z, new HashSet<int[]>());
	}

	@Override
	public void onBlockRemoval(World world, int x, int y, int z) {
		// Tell the neighbors that their state may have to change
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
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
