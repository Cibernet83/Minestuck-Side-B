package com.mraof.minestuck.item.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tileentity.TileEntityItemStack;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDowel extends MSItemBlock
{

	public ItemDowel(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
		MinestuckItems.items.add(this);
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		if (stack.hasTagCompound())
			return 16;
		else return 64;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (stack.hasTagCompound())
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();
			NBTTagString contentID = (NBTTagString) nbttagcompound.getTag("contentID");
			NBTTagInt contentMeta = (NBTTagInt) nbttagcompound.getTag("contentMeta");

			if (contentID != null && contentMeta != null && Item.REGISTRY.containsKey(new ResourceLocation(contentID.getString())))
			{
				tooltip.add("(" + (AlchemyUtils.getDecodedItem(stack)).getDisplayName() + ")");
				return;
			}
			else
			{
				tooltip.add("(" + I18n.translateToLocal("item.captchaCard.invalid") + ")");
			}
		}
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
	{
		if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState))
		{
			TileEntityItemStack te = (TileEntityItemStack) world.getTileEntity(pos);
			ItemStack newStack = stack.copy();
			newStack.setCount(1);
			te.setStack(newStack);
			return true;
		}
		else return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel()
	{
		ModelLoader.registerItemVariants(this, new ResourceLocation(Minestuck.MODID, "dowel_uncarved"), new ResourceLocation(Minestuck.MODID, "dowel_carved"));
		ModelLoader.setCustomMeshDefinition(this, (ItemStack stack) -> new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, (stack.hasTagCompound() && stack.getTagCompound().hasKey("contentID") ? "dowel_carved" : "dowel_uncarved")), "inventory"));
	}
}