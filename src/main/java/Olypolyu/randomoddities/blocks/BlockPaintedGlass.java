package Olypolyu.randomoddities.blocks;

import Olypolyu.randomoddities.particle.EntityPaintFX;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockGlass;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import static Olypolyu.randomoddities.items.ItemPaintBrush.colourValues;

public class BlockPaintedGlass extends BlockGlass {
    public BlockPaintedGlass(String key, int i, Material material) {
        super(key, i, material);
    }

	public int getRenderBlockPass() {
        return 1;
    }

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xPlaced, double yPlaced) {
		ItemStack item = player.inventory.mainInventory[player.inventory.currentItem];

		if (item.getItem() == Item.cloth) {
			Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
			Color particleColour = new Color();
			particleColour.setARGB(colourValues[world.getBlockMetadata(x, y, z)]);
			for (int particle = 0; particle < 24; particle++) {
				mc.effectRenderer.addEffect(
					new EntityPaintFX(world.rand.nextInt(4),
						particleColour.getRed() / 255F,
						particleColour.getBlue() / 255F,
						particleColour.getGreen() / 255F,
						world,
						x + world.rand.nextDouble(),
						y + world.rand.nextDouble(),
						z + world.rand.nextDouble(),
						0, 0, 0
					)
				);
			}
			player.swingItem();
			world.playSoundAtEntity(player, player, "mob.slime", 0.3F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			world.setBlockWithNotify(x, y, z, Block.glass.id);

			item.consumeItem(player);
			if (item.stackSize <= 0) {
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
		}

		return super.onBlockRightClicked(world, x, y, z, player, side, xPlaced, yPlaced);
	}
}
