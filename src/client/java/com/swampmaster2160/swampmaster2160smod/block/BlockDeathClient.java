package com.swampmaster2160.swampmaster2160smod.block;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.block.texture.Face;
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

	// Sets textures for each side of the block.
	@Override
	protected void allocateTextures() {
		// Get the name of the block
		String name = this.getBlockName().replace("tile.", "");
		// Get the name of the side textures
		String top_texture_name = name + "_top";
		String side_texture_name = name + "_side";
		// Set the texture for each side
		this.addTexture(side_texture_name, Face.ALL, 0);
		this.addTexture(top_texture_name, Face.TOP, 0);
		this.addTexture(top_texture_name, Face.BOTTOM, 0);
	}
}
