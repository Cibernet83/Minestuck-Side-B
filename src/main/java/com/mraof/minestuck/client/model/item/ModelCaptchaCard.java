package com.mraof.minestuck.client.model.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.inventory.captchalouge.CaptchalogueableItemStack;
import com.mraof.minestuck.inventory.captchalouge.ICaptchalogueable;
import com.mraof.minestuck.inventory.captchalouge.Modus;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import static com.mraof.minestuck.client.model.item.ModelCaptchaCard.LoaderCaptchaCard.getResource;

/*
 * I'm going to be completely honest here, there's little to no proper documentation on custom models so some of this code may look like garbage in the future
 * -Ciber, after *way* too much trial and error
 * PS: also, this was partially ripped from ModelDynBucket
 */

public final class ModelCaptchaCard implements IModel
{
	public static final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, "captcha_card"), "inventory");
	public static final ResourceLocation PUNCHED_LOCATION = new ResourceLocation(Minestuck.MODID, "items/captchalogue/punched_overlay");
	public static final ResourceLocation UNASSIGNED_CARD_LOCATION = new ResourceLocation(Minestuck.MODID, "items/captchalogue/card_unassigned");

	private static final HashMap<Modus, ResourceLocation> cardLocations = new HashMap<>();

	@Nullable
	private ResourceLocation cardLocation;
	@Nullable
	private ResourceLocation contentLocation;
	private boolean punched = false;

	// minimal Z offset to prevent depth-fighting
	private static final float NORTH_Z_PUNCHED = 7.496f / 16f;
	private static final float SOUTH_Z_PUNCHED = 8.504f / 16f;
	private static final float NORTH_Z_CONTENT = 7.498f / 16f;
	private static final float SOUTH_Z_CONTENT = 8.502f / 16f;

	public static final IModel MODEL = new ModelCaptchaCard();

	public ModelCaptchaCard()
	{

	}



	@Override
	public Collection<ResourceLocation> getTextures()
	{
		ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();

		if(cardLocation != null)
			builder.add(cardLocation);
		if(contentLocation != null)
			builder.add(contentLocation);
		if(punched)
			builder.add(PUNCHED_LOCATION);

		return builder.build();
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
	                        Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{

		ImmutableMap<TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);

		TRSRTransformation transform = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());
		TextureAtlasSprite particleSprite = null;
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		if (cardLocation != null)
		{
			//card base
			IBakedModel model = (new ItemLayerModel(ImmutableList.of(cardLocation))).bake(state, format, bakedTextureGetter);
			builder.addAll(model.getQuads(null, null, 0));
			particleSprite = model.getParticleTexture();
		}
		if(contentLocation != null)
		{
			//card contents

			TextureAtlasSprite sprite = bakedTextureGetter.apply(contentLocation);
			//builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, NORTH_Z_CONTENT, sprite, EnumFacing.NORTH, 0xFFFFFFFF, 2));
			builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, SOUTH_Z_CONTENT, sprite, EnumFacing.SOUTH, 0xFFFFFFFF, 2));
		}
		if(punched)
		{
			//punched overlay

			TextureAtlasSprite sprite = bakedTextureGetter.apply(PUNCHED_LOCATION);
			builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, NORTH_Z_PUNCHED, sprite, EnumFacing.NORTH, 0xFFFFFFFF, 2));
			builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, SOUTH_Z_PUNCHED, sprite, EnumFacing.SOUTH, 0xFFFFFFFF, 2));
		}

		return new BakedCaptchaCard(this, builder.build(), particleSprite, format, Maps.immutableEnumMap(transformMap), Maps.newHashMap(), transform.isIdentity());
	}

	public ModelCaptchaCard process(ItemStack stack)
	{
		cardLocation = AlchemyUtils.cardBelongsToModus(stack) ? cardLocations.get(AlchemyUtils.getCardModus(stack)) : UNASSIGNED_CARD_LOCATION;

		ICaptchalogueable contents = AlchemyUtils.hasDecodedItem(stack) ? new CaptchalogueableItemStack() : null;
		contentLocation = contents == null ? null : new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_"+contents.getTextureKey());
		punched = AlchemyUtils.isPunchedCard(stack);

		return this;
	}

	protected static String getCachedName(ItemStack stack)
	{
		Modus modus = AlchemyUtils.getCardModus(stack);
		ICaptchalogueable contents = AlchemyUtils.hasDecodedItem(stack) ? new CaptchalogueableItemStack() : null;
		boolean punched = AlchemyUtils.isPunchedCard(stack);

		return (modus == null ? "Unassigned" : modus.getRegistryName().toString()) + "~" + (contents == null ? "empty" : contents.getTextureKey()) + (punched ? "~punched" : "");
	}


	/* Blockstate stuff, i think i'd rather not mess with these
	 * Sets the fluid in the model.
	 * "fluid" - Name of the fluid in the fluid registry.
	 * "flipGas" - If "true" the model will be flipped upside down if the fluid is lighter than air. If "false" it won't.
	 * "applyTint" - If "true" the model will tint the fluid quads according to the fluid's base color.
	 * <p/>
	 * If the fluid can't be found, water is used.
	 *
	/
	@Override
	public ModelCaptchaCard process(ImmutableMap<String, String> customData)
	{
		String fluidName = customData.get("fluid");
		Fluid fluid = FluidRegistry.getFluid(fluidName);

		if (fluid == null) fluid = this.fluid;

		boolean flip = flipGas;
		if (customData.containsKey("flipGas"))
		{
			String flipStr = customData.get("flipGas");
			if (flipStr.equals("true")) flip = true;
			else if (flipStr.equals("false")) flip = false;
			else
				throw new IllegalArgumentException(String.format("DynCaptchaCard custom data \"flipGas\" must have value \'true\' or \'false\' (was \'%s\')", flipStr));
		}

		boolean tint = this.tint;
		if (customData.containsKey("applyTint"))
		{
			String string = customData.get("applyTint");
			switch (string)
			{
				case "true":  tint = true;  break;
				case "false": tint = false; break;
				default: throw new IllegalArgumentException(String.format("DynCaptchaCard custom data \"applyTint\" must have value \'true\' or \'false\' (was \'%s\')", string));
			}
		}

		// create new model with correct liquid
		return new ModelCaptchaCard(baseLocation, liquidLocation, coverLocation, fluid, flip, tint);
	}

	/**
	 * Allows to use different textures for the model.
	 * There are 3 layers:
	 * base - The empty bucket/container
	 * fluid - A texture representing the liquid portion. Non-transparent = liquid
	 * cover - An overlay that's put over the liquid (optional)
	 * <p/>
	 * If no liquid is given a hardcoded variant for the bucket is used.
	 /
	@Override
	public ModelCaptchaCard retexture(ImmutableMap<String, String> textures)
	{

		ResourceLocation base = baseLocation;
		ResourceLocation liquid = liquidLocation;
		ResourceLocation cover = coverLocation;

		if (textures.containsKey("base"))
			base = new ResourceLocation(textures.get("base"));
		if (textures.containsKey("fluid"))
			liquid = new ResourceLocation(textures.get("fluid"));
		if (textures.containsKey("cover"))
			cover = new ResourceLocation(textures.get("cover"));

		return new ModelCaptchaCard(base, liquid, cover, fluid, flipGas, tint);
	}
	*/

	public enum LoaderCaptchaCard implements ICustomModelLoader
	{
		INSTANCE;

		@Override
		public boolean accepts(ResourceLocation modelLocation)
		{
			return modelLocation.getResourceDomain().equals(Minestuck.MODID) && modelLocation.getResourcePath().contains("captcha_card");
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation)
		{
			return MODEL;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager)
		{
			// no need to clear cache since we create a new model instance
		}

		public void registerTextures(TextureMap map)
		{
			map.registerSprite(UNASSIGNED_CARD_LOCATION);
			map.registerSprite(PUNCHED_LOCATION);

			ResourceLocation def = new ResourceLocation(Minestuck.MODID, "items/captchalogue/card_default");
			map.registerSprite(def);

			for(Modus modus : Modus.REGISTRY.getValuesCollection())
			{
				IResource card = getResource(new ResourceLocation(modus.getRegistryName().getResourceDomain(), "textures/items/captchalogue/"+modus.getRegistryName().getResourcePath()+".png"));

				if(card == null)
					cardLocations.put(modus, def);
				else
				{
					ResourceLocation loc = new ResourceLocation(modus.getRegistryName().getResourceDomain(), "items/captchalogue/"+modus.getRegistryName().getResourcePath());
					map.registerSprite(loc);
					cardLocations.put(modus, loc);
				}
			}

			//TODO registerTextures contents automagically
			map.registerSprite(new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_item"));
			map.registerSprite(new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_ghost"));
			map.registerSprite(new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_red"));
			map.registerSprite(new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_abstract"));
			map.registerSprite(new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_invalid"));
		}

		@Nullable
		protected static IResource getResource(ResourceLocation resourceLocation)
		{
			try
			{
				return Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation);
			}
			catch (IOException ignored)
			{
				return null;
			}
		}
	}

	private static final class BucketBaseSprite extends TextureAtlasSprite
	{
		private final ResourceLocation bucket = new ResourceLocation("items/bucket_empty");
		private final ImmutableList<ResourceLocation> dependencies = ImmutableList.of(bucket);

		private BucketBaseSprite(ResourceLocation resourceLocation)
		{
			super(resourceLocation.toString());
		}

		@Override
		public boolean hasCustomLoader(@Nonnull IResourceManager manager, @Nonnull ResourceLocation location)
		{
			return true;
		}

		@Override
		public Collection<ResourceLocation> getDependencies()
		{
			return dependencies;
		}

		@Override
		public boolean load(@Nonnull IResourceManager manager, @Nonnull ResourceLocation location, @Nonnull Function<ResourceLocation, TextureAtlasSprite> textureGetter)
		{
			final TextureAtlasSprite sprite = textureGetter.apply(bucket);
			width = sprite.getIconWidth();
			height = sprite.getIconHeight();
			final int[][] pixels = sprite.getFrameTextureData(0);
			this.clearFramesTextureData();
			this.framesTextureData.add(pixels);
			return false;
		}
	}

	/**
	 * Creates a bucket cover sprite from the vanilla resource.
	 */
	private static final class BucketCoverSprite extends TextureAtlasSprite
	{
		private final ResourceLocation bucket = new ResourceLocation("items/bucket_empty");
		private final ResourceLocation bucketCoverMask = new ResourceLocation(ForgeVersion.MOD_ID, "items/vanilla_bucket_cover_mask");
		private final ImmutableList<ResourceLocation> dependencies = ImmutableList.of(bucket, bucketCoverMask);

		private BucketCoverSprite(ResourceLocation resourceLocation)
		{
			super(resourceLocation.toString());
		}

		@Override
		public boolean hasCustomLoader(@Nonnull IResourceManager manager, @Nonnull ResourceLocation location)
		{
			return true;
		}

		@Override
		public Collection<ResourceLocation> getDependencies()
		{
			return dependencies;
		}

		@Override
		public boolean load(@Nonnull IResourceManager manager, @Nonnull ResourceLocation location, @Nonnull Function<ResourceLocation, TextureAtlasSprite> textureGetter)
		{
			final TextureAtlasSprite sprite = textureGetter.apply(bucket);
			final TextureAtlasSprite alphaMask = textureGetter.apply(bucketCoverMask);
			width = sprite.getIconWidth();
			height = sprite.getIconHeight();
			final int[][] pixels = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
			pixels[0] = new int[width * height];

			try (
					IResource empty = getResource(new ResourceLocation("textures/items/bucket_empty.png"));
					IResource mask = getResource(new ResourceLocation(ForgeVersion.MOD_ID, "textures/items/vanilla_bucket_cover_mask.png"))
			) {
				// use the alpha mask if it fits, otherwise leave the cover texture blank
				if (empty != null && mask != null && Objects.equals(empty.getResourcePackName(), mask.getResourcePackName()) &&
						alphaMask.getIconWidth() == width && alphaMask.getIconHeight() == height)
				{
					final int[][] oldPixels = sprite.getFrameTextureData(0);
					final int[][] alphaPixels = alphaMask.getFrameTextureData(0);

					for (int p = 0; p < width * height; p++)
					{
						final int alphaMultiplier = alphaPixels[0][p] >>> 24;
						final int oldPixel = oldPixels[0][p];
						final int oldPixelAlpha = oldPixel >>> 24;
						final int newAlpha = oldPixelAlpha * alphaMultiplier / 0xFF;
						pixels[0][p] = (oldPixel & 0xFFFFFF) + (newAlpha << 24);
					}
				}
			}
			catch (IOException e)
			{
				FMLLog.log.error("Failed to close resource", e);
			}

			this.clearFramesTextureData();
			this.framesTextureData.add(pixels);
			return false;
		}
	}

	private static final class BakedCaptchaCardOverrideHandler extends ItemOverrideList
	{
		public static final BakedCaptchaCardOverrideHandler INSTANCE = new BakedCaptchaCardOverrideHandler();
		private BakedCaptchaCardOverrideHandler()
		{
			super(ImmutableList.of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
		{
			BakedCaptchaCard model = (BakedCaptchaCard)originalModel;

			String name = getCachedName(stack);

			if (!model.cache.containsKey(name))
			{
				IModel parent = model.parent.process(stack);
				Function<ResourceLocation, TextureAtlasSprite> textureGetter;
				textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

				IBakedModel bakedModel = parent.bake(new SimpleModelState(model.getTransforms()), model.format, textureGetter);
				model.cache.put(name, bakedModel);
				return bakedModel;
			}

			return model.cache.get(name);
		}
	}

	// the dynamic bucket is based on the empty bucket
	private static final class BakedCaptchaCard extends BakedItemModel
	{
		private final ModelCaptchaCard parent;
		private final Map<String, IBakedModel> cache; // contains all the baked models since they'll never change
		private final VertexFormat format;

		BakedCaptchaCard(ModelCaptchaCard parent,
		                 ImmutableList<BakedQuad> quads,
		                 TextureAtlasSprite particle,
		                 VertexFormat format,
		                 ImmutableMap<TransformType, TRSRTransformation> transforms,
		                 Map<String, IBakedModel> cache,
		                 boolean untransformed)
		{
			super(quads, particle, transforms, BakedCaptchaCardOverrideHandler.INSTANCE, untransformed);
			this.format = format;
			this.parent = parent;
			this.cache = cache;
		}

		public ImmutableMap<? extends IModelPart, TRSRTransformation> getTransforms() {
			return transforms;
		}
	}

}