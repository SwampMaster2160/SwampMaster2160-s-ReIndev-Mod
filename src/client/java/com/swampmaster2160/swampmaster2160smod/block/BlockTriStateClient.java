package com.swampmaster2160.swampmaster2160smod.block;

import java.util.Set;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.level.World;

public class BlockTriStateClient extends Block {
	public BlockTriStateClient(int id) {
		super(id, Material.rock);
	}

	public TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom) {
		return TriStateStateEnum.FLOATING;
	}

	public void setTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom, TriStateStateEnum newState) {

	}

	public void triStateStateMayNeedChanging(World world, int x, int y, int z) {

	}

	public TriStateStateEnum getTriStateStateFromSources(World world, int x, int y, int z, Direction6Enum directionFrom, Set<int[]> visited) {
		visited.add(new int[] { x, y, z });
		return TriStateStateEnum.FLOATING;
	}

	/*public int getSignalSourceCount(World world, int x, int y, int z, Set<int[]> visited) {
		//System.out.println("x: " + x + "y: " + y + "z: " + z);
		visited.add(new int[] { x, y, z });
		return 0;
	}

	public void addSignalSource(World world, int x, int y, int z, Direction6Enum directionFrom) {

	}*/
}
