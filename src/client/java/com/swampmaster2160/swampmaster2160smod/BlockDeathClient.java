package com.swampmaster2160.swampmaster2160smod;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.level.World;

// Clientside class for the death block
public class BlockDeathClient extends Block {

	public BlockDeathClient(int id) {
		super(id, Material.rock);
	}

	// When right clicked, kills player.
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		player.heal(Integer.MIN_VALUE);
		return true;
	}
	
}
