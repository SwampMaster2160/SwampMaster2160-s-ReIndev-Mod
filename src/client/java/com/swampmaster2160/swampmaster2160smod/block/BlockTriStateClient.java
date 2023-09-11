package com.swampmaster2160.swampmaster2160smod.block;

import java.util.Set;

import org.jetbrains.annotations.Nullable;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.level.World;

public class BlockTriStateClient extends Block {
	public BlockTriStateClient(int id) {
		super(id, Material.rock);
	}

	public @Nullable TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom) {
		return null;
	}

	public void setTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom, TriStateStateEnum newState) {

	}

	/*public int getSignalSourceCount(World world, int x, int y, int z, Set<int[]> visited) {
		//System.out.println("x: " + x + "y: " + y + "z: " + z);
		visited.add(new int[] { x, y, z });
		return 0;
	}

	public void addSignalSource(World world, int x, int y, int z, Direction6Enum directionFrom) {

	}*/
}
