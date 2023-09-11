package com.swampmaster2160.swampmaster2160smod.block;

import org.jetbrains.annotations.Nullable;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModClient;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.texture.Face;
import net.minecraft.src.game.level.World;

public class BlockTriStateSignalClient extends BlockTriStateClient {
	public BlockTriStateSignalClient(int id) {
		super(id);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int arg5) {
		super.onNeighborBlockChange(world, x, y, z, arg5);
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
				TriStateStateEnum neighborState = getTriStateState(world, neighborX, neighborY, neighborZ, direction);
				TriStateStateEnum thisBlockState = getTriStateState(world, x, y, z, direction);
				if (neighborState != null && neighborState != thisBlockState) {
					world.setBlockMetadataWithNotify(x, y, z, neighborState.intValue);
				}
			}
		}
	}

	public @Nullable TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom) {
		return TriStateStateEnum.fromInt(world.getBlockMetadata(x, y, z));
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
		// Set the texture for each side
		this.addTexture(floating_texture_name, Face.ALL, TriStateStateEnum.FLOATING.intValue);
		this.addTexture(true_texture_name, Face.ALL, TriStateStateEnum.TRUE.intValue);
		this.addTexture(false_texture_name, Face.ALL, TriStateStateEnum.FALSE.intValue);
	}
}
