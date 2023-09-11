package com.swampmaster2160.swampmaster2160smod.block;

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
}
