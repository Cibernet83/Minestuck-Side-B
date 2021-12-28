package com.mraof.minestuck.client.model.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.modus.Modus;
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
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.TRSRTransformer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

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
	public static final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, "captcha_card_dynamic"), "inventory");
	public static final ResourceLocation UNASSIGNED_CARD_LOCATION = new ResourceLocation(Minestuck.MODID, "items/captchalogue/card_unassigned");
	public static final ResourceLocation PUNCHED_LOCATION = new ResourceLocation(Minestuck.MODID, "items/captchalogue/punched_overlay");

	private static final HashMap<Modus, ResourceLocation> cardLocations = new HashMap<>();

	@Nullable
	private ResourceLocation cardLocation;
	@Nullable
	private ResourceLocation contentLocation;
	@Nullable
	private ResourceLocation punchedLocation;
	private boolean punched;
	private List<Modus> modi;

	// minimal Z offset to prevent depth-fighting
	private static final float NORTH_Z_PUNCHED = 7.496f / 16f;
	private static final float SOUTH_Z_PUNCHED = 8.504f / 16f;
	private static final float NORTH_Z_CONTENT = 7.498f / 16f;
	private static final float SOUTH_Z_CONTENT = 8.502f / 16f;

	public static final IModel MODEL = new ModelCaptchaCard();

	public ModelCaptchaCard()
	{
		this(new ResourceLocation(Minestuck.MODID, "items/captcha_card_base"), null, PUNCHED_LOCATION,
				false, new ArrayList<>());
	}

	public ModelCaptchaCard(ResourceLocation cardLocation, ResourceLocation contentLocation, ResourceLocation punchedLocation, boolean punched, List<Modus> modi)
	{
		this.cardLocation = cardLocation;
		this.contentLocation = contentLocation;
		this.punchedLocation = punchedLocation;
		this.punched = punched;
		this.modi = modi;
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();

		if(cardLocation != null)
			builder.add(cardLocation);
		if(contentLocation != null)
			builder.add(contentLocation);
		if(punchedLocation != null)
			builder.add(punchedLocation);

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

		if(!modi.isEmpty())
		{
			for(int i = modi.size()-1; i >= 0 ; i--)
			{
				Modus modus = modi.get(i);
				ResourceLocation loc = modus != null && getResource(new ResourceLocation(modus.getRegistryName().getResourceDomain(), "textures/items/captchalogue/"+modus.getRegistryName().getResourcePath()+".png")) != null ?
						new ResourceLocation(modus.getRegistryName().getResourceDomain(), "items/captchalogue/"+modus.getRegistryName().getResourcePath()) :
						new ResourceLocation(Minestuck.MODID, "items/captchalogue/card_default");
				TextureAtlasSprite sprite = bakedTextureGetter.apply(loc);
				builder.addAll(getQuadsForSprite(0, sprite, format, state.apply(Optional.empty()), i/(float)modi.size(), 0, (i+1)/(float)modi.size(), 1));

				particleSprite = sprite;
			}
		}
		else
		{
			TextureAtlasSprite sprite = bakedTextureGetter.apply(UNASSIGNED_CARD_LOCATION);
			builder.addAll(getQuadsForSprite(0, sprite, format, state.apply(Optional.empty()), 0, 0, 1, 1));
			particleSprite = sprite;
		}

		if(contentLocation != null)
		{
			//card contents
			TextureAtlasSprite sprite = bakedTextureGetter.apply(contentLocation);
			//builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, NORTH_Z_CONTENT, sprite, EnumFacing.NORTH, 0xFFFFFFFF, 2));
			builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, SOUTH_Z_CONTENT, sprite, EnumFacing.SOUTH, 0xFFFFFFFF, 2));
		}
		if(punched && punchedLocation != null)
		{
			//punched overlay

			TextureAtlasSprite sprite = bakedTextureGetter.apply(punchedLocation);
			builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, NORTH_Z_PUNCHED, sprite, EnumFacing.NORTH, 0xFFFFFFFF, 2));
			builder.add(ItemTextureQuadConverter.genQuad(format, transform, 0, 0, 16, 16, SOUTH_Z_PUNCHED, sprite, EnumFacing.SOUTH, 0xFFFFFFFF, 2));
		}

		return new BakedCaptchaCard(this, builder.build(), particleSprite, format, Maps.immutableEnumMap(transformMap), Maps.newHashMap(), transform.isIdentity());
	}

	protected static String getCachedKey(ItemStack stack)
	{
		return getModusKey(AlchemyUtils.getCardModi(stack)) + "~" + getContentsKey(stack) + (AlchemyUtils.isPunchedCard(stack) ? "~punched" : "");
	}

	protected static String getContentsKey(ItemStack stack)
	{
		return AlchemyUtils.hasCardContents(stack) ? AlchemyUtils.getCardContents(stack).getTextureKey() : "empty";
	}


	protected static String getModusKey(List<Modus> modi)
	{
		String modusKey = "Unassigned";

		if(!modi.isEmpty())
		{
			modusKey = "";
			boolean first = true;
			for(Modus modus : modi)
			{
				if(!first)
					modusKey += "/";
				modusKey += modus == null ? "Null" : modus.getRegistryName();
				first = false;
			}
		}

		return modusKey;
	}

	protected static List<Modus> getModiFromKey(String key)
	{
		List<Modus> modi = new ArrayList<>();

		if(!key.isEmpty() && !key.equals("Unassigned"))
			for(String str : key.split("/"))
				modi.add(Modus.REGISTRY.getValue(new ResourceLocation(str)));
		//modi.removeIf(modus -> modus == null);

		return modi;
	}

	@Override
	public ModelCaptchaCard process(ImmutableMap<String, String> customData)
	{
		List<Modus> modi = this.modi;

		if(customData.containsKey("modi"))
			modi = getModiFromKey(customData.get("modi"));

		ResourceLocation contentLocation = this.contentLocation;
		if(customData.containsKey("contents"))
			contentLocation = new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_"+customData.get("contents"));

		boolean punched = this.punched;
		if (customData.containsKey("punched"))
		{
			String string = customData.get("punched");
			switch (string)
			{
				case "true":  punched = true;  break;
				case "false": punched = false; break;
				default: throw new IllegalArgumentException(String.format("DynCaptchaCard custom data \"punched\" must have value \'true\' or \'false\' (was \'%s\')", string));
			}
		}

		// create new model with correct liquid
		return new ModelCaptchaCard(cardLocation, contentLocation, punchedLocation, punched, modi);
	}

	@Override //for some reason this isn't getting called :\ so i'm just defining tex locs from the constructor
	public ModelCaptchaCard retexture(ImmutableMap<String, String> textures)
	{
		ResourceLocation base = cardLocation;
		ResourceLocation content = contentLocation;
		ResourceLocation punched = punchedLocation;

		if (textures.containsKey("base"))
			base = new ResourceLocation(textures.get("base"));
		if (textures.containsKey("contents"))
			content = new ResourceLocation(textures.get("contents"));
		if (textures.containsKey("punched"))
			punched = new ResourceLocation(textures.get("punched"));

		return new ModelCaptchaCard(base, content, punched, this.punched, modi);
	}

	public enum LoaderCaptchaCard implements ICustomModelLoader
	{
		INSTANCE;

		@Override
		public boolean accepts(ResourceLocation modelLocation)
		{
			return modelLocation.getResourceDomain().equals(Minestuck.MODID) && modelLocation.getResourcePath().contains("captchalogue_card");
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

			//registerTextures contents automagically
			map.registerSprite(new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_empty"));
			map.registerSprite(new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_item"));
			map.registerSprite(new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_ghost"));
			map.registerSprite(new ResourceLocation(Minestuck.MODID, "items/captchalogue/content_entity"));
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
			ModelCaptchaCard.BakedCaptchaCard model = (ModelCaptchaCard.BakedCaptchaCard)originalModel;

			String key = getCachedKey(stack);

			if (!model.cache.containsKey(key))
			{

				ImmutableMap<String, String> map = ImmutableMap.of("modi", getModusKey(AlchemyUtils.getCardModi(stack)), "contents", getContentsKey(stack), "punched", AlchemyUtils.isPunchedCard(stack) ? "true" : "false");

				IModel parent = model.parent.process(map);
				Function<ResourceLocation, TextureAtlasSprite> textureGetter;
				textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

				IBakedModel bakedModel = parent.bake(new SimpleModelState(model.getTransforms()), model.format, textureGetter);
				model.cache.put(key, bakedModel);
				return bakedModel;
			}

			return model.cache.get(key);
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

	public static ImmutableList<BakedQuad> getQuadsForSprite(int tint, TextureAtlasSprite sprite, VertexFormat format, Optional<TRSRTransformation> transform, float minU, float minV, float maxU, float maxV)
	{
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		int uMin = (int) (sprite.getIconWidth()*minU);
		int vMin = (int) (sprite.getIconHeight()*minV);
		int uMax = (int) (sprite.getIconWidth()*maxU);
		int vMax = (int) (sprite.getIconHeight()*maxV);

		FaceData faceData = new FaceData(sprite.getIconWidth(), sprite.getIconHeight());
		boolean translucent = false;

		for(int f = 0; f < sprite.getFrameCount(); f++)
		{
			int[] pixels = sprite.getFrameTextureData(f)[0];
			boolean ptu;
			boolean[] ptv = new boolean[sprite.getIconWidth()];
			Arrays.fill(ptv, true);
			for(int v = 0; v < sprite.getIconHeight(); v++)
			{
				ptu = true;
				for(int u = 0; u < sprite.getIconWidth(); u++)
				{
					int alpha = getAlpha(pixels, sprite.getIconWidth(), sprite.getIconHeight(), u, v);
					boolean t = alpha / 255f <= 0.1f;

					if (!t && alpha < 255)
					{
						translucent = true;
					}

					if(ptu && !t) // left - transparent, right - opaque
					{
						faceData.set(EnumFacing.WEST, u, v);
					}
					if(!ptu && t) // left - opaque, right - transparent
					{
						faceData.set(EnumFacing.EAST, u-1, v);
					}
					if(ptv[u] && !t) // up - transparent, down - opaque
					{
						faceData.set(EnumFacing.UP, u, v);
					}
					if(!ptv[u] && t) // up - opaque, down - transparent
					{
						faceData.set(EnumFacing.DOWN, u, v-1);
					}

					ptu = t;
					ptv[u] = t;
				}
				if(!ptu) // last - opaque
				{
					faceData.set(EnumFacing.EAST, uMax-1, v);
				}
			}
			// last line
			for(int u = uMin; u < uMax; u++)
			{
				if(!ptv[u])
				{
					faceData.set(EnumFacing.DOWN, u, vMax-1);
				}
			}
		}

		// horizontal quads
		for (EnumFacing facing : HORIZONTALS)
		{
			for (int v = vMin; v < vMax; v++)
			{
				int uStart = uMin, uEnd = uMax;
				boolean building = false;
				for (int u = uMin; u < uMax; u++)
				{
					boolean face = faceData.get(facing, u, v);
					if (!translucent)
					{
						if (face)
						{
							if (!building)
							{
								building = true;
								uStart = u;
							}
							uEnd = u + 1;
						}
					}
					else
					{
						if (building && !face) // finish current quad
						{
							// make quad [uStart, u]
							int off = facing == EnumFacing.DOWN ? 1 : 0;
							builder.add(buildSideQuad(format, transform, facing, tint, sprite, uStart, v+off, u-uStart));
							building = false;
						}
						else if (!building && face) // start new quad
						{
							building = true;
							uStart = u;
						}
					}
				}
				if (building) // build remaining quad
				{
					// make quad [uStart, uEnd]
					int off = facing == EnumFacing.DOWN ? 1 : 0;
					builder.add(buildSideQuad(format, transform, facing, tint, sprite, uStart, v+off, uEnd-uStart));
				}
			}
		}

		// vertical quads
		for (EnumFacing facing : VERTICALS)
		{
			for (int u = uMin; u < uMax; u++)
			{
				int vStart = vMin, vEnd = vMax;
				boolean building = false;
				for (int v = vMin; v < vMax; v++)
				{
					boolean face = faceData.get(facing, u, v);
					if (!translucent)
					{
						if (face)
						{
							if (!building)
							{
								building = true;
								vStart = v;
							}
							vEnd = v + 1;
						}
					}
					else
					{
						if (building && !face) // finish current quad
						{
							// make quad [vStart, v]
							int off = facing == EnumFacing.EAST ? 1 : 0;
							builder.add(buildSideQuad(format, transform, facing, tint, sprite, u+off, vStart, v-vStart));
							building = false;
						}
						else if (!building && face) // start new quad
						{
							building = true;
							vStart = v;
						}
					}
				}
				if (building) // build remaining quad
				{
					// make quad [vStart, vEnd]
					int off = facing == EnumFacing.EAST ? 1 : 0;
					builder.add(buildSideQuad(format, transform, facing, tint, sprite, u+off, vStart, vEnd-vStart));
				}
			}
		}

		// front
		builder.add(buildQuad(format, transform, EnumFacing.NORTH, sprite, tint,
				minU, minV, 7.5f / 16f, sprite.getMinU() + (sprite.getMaxU()-sprite.getMinU())*minU, sprite.getMinV() + (sprite.getMaxV()-sprite.getMinV())*maxV,
				minU, maxV, 7.5f / 16f, sprite.getMinU() + (sprite.getMaxU()-sprite.getMinU())*minU, sprite.getMinV() + (sprite.getMaxV()-sprite.getMinV())*minV,
				maxU, maxV, 7.5f / 16f, sprite.getMinU() + (sprite.getMaxU()-sprite.getMinU())*maxU, sprite.getMinV() + (sprite.getMaxV()-sprite.getMinV())*minV,
				maxU, minV, 7.5f / 16f, sprite.getMinU() + (sprite.getMaxU()-sprite.getMinU())*maxU, sprite.getMinV() + (sprite.getMaxV()-sprite.getMinV())*maxV
		));
		// back
		builder.add(buildQuad(format, transform, EnumFacing.SOUTH, sprite, tint,
				minU, minV, 8.5f / 16f, sprite.getMinU() + (sprite.getMaxU()-sprite.getMinU())*minU, sprite.getMinV() + (sprite.getMaxV()-sprite.getMinV())*maxV,
				maxU, minV, 8.5f / 16f, sprite.getMinU() + (sprite.getMaxU()-sprite.getMinU())*maxU, sprite.getMinV() + (sprite.getMaxV()-sprite.getMinV())*maxV,
				maxU, maxV, 8.5f / 16f, sprite.getMinU() + (sprite.getMaxU()-sprite.getMinU())*maxU, sprite.getMinV() + (sprite.getMaxV()-sprite.getMinV())*minV,
				minU, maxV, 8.5f / 16f, sprite.getMinU() + (sprite.getMaxU()-sprite.getMinU())*minU, sprite.getMinV() + (sprite.getMaxV()-sprite.getMinV())*minV
		));

		return builder.build();
	}


	private static final EnumFacing[] HORIZONTALS = {EnumFacing.UP, EnumFacing.DOWN};
	private static final EnumFacing[] VERTICALS = {EnumFacing.WEST, EnumFacing.EAST};

	private static class FaceData
	{
		private final EnumMap<EnumFacing, BitSet> data = new EnumMap<>(EnumFacing.class);

		private final int vMax;

		FaceData(int uMax, int vMax)
		{
			this.vMax = vMax;

			data.put(EnumFacing.WEST, new BitSet(uMax * vMax));
			data.put(EnumFacing.EAST, new BitSet(uMax * vMax));
			data.put(EnumFacing.UP,   new BitSet(uMax * vMax));
			data.put(EnumFacing.DOWN, new BitSet(uMax * vMax));
		}

		public void set(EnumFacing facing, int u, int v)
		{
			data.get(facing).set(getIndex(u, v));
		}

		public boolean get(EnumFacing facing, int u, int v)
		{
			return data.get(facing).get(getIndex(u, v));
		}

		private int getIndex(int u, int v)
		{
			return v * vMax + u;
		}
	}

	private static int getAlpha(int[] pixels, int uMax, int vMax, int u, int v)
	{
		return pixels[u + (vMax - 1 - v) * uMax] >> 24 & 0xFF;
	}

	private static BakedQuad buildSideQuad(VertexFormat format, Optional<TRSRTransformation> transform, EnumFacing side, int tint, TextureAtlasSprite sprite, int u, int v, int size)
	{
		final float eps = 1e-2f;

		int width = sprite.getIconWidth();
		int height = sprite.getIconHeight();

		float x0 = (float) u / width;
		float y0 = (float) v / height;
		float x1 = x0, y1 = y0;
		float z0 = 7.5f / 16f, z1 = 8.5f / 16f;

		switch(side)
		{
			case WEST:
				z0 = 8.5f / 16f;
				z1 = 7.5f / 16f;
			case EAST:
				y1 = (float) (v + size) / height;
				break;
			case DOWN:
				z0 = 8.5f / 16f;
				z1 = 7.5f / 16f;
			case UP:
				x1 = (float) (u + size) / width;
				break;
			default:
				throw new IllegalArgumentException("can't handle z-oriented side");
		}

		float dx = side.getDirectionVec().getX() * eps / width;
		float dy = side.getDirectionVec().getY() * eps / height;

		float u0 = 16f * (x0 - dx);
		float u1 = 16f * (x1 - dx);
		float v0 = 16f * (1f - y0 - dy);
		float v1 = 16f * (1f - y1 - dy);

		return buildQuad(
				format, transform, remap(side), sprite, tint,
				x0, y0, z0, sprite.getInterpolatedU(u0), sprite.getInterpolatedV(v0),
				x1, y1, z0, sprite.getInterpolatedU(u1), sprite.getInterpolatedV(v1),
				x1, y1, z1, sprite.getInterpolatedU(u1), sprite.getInterpolatedV(v1),
				x0, y0, z1, sprite.getInterpolatedU(u0), sprite.getInterpolatedV(v0)
		);
	}

	private static EnumFacing remap(EnumFacing side)
	{
		// getOpposite is related to the swapping of V direction
		return side.getAxis() == EnumFacing.Axis.Y ? side.getOpposite() : side;
	}

	private static BakedQuad buildQuad(
			VertexFormat format, Optional<TRSRTransformation> transform, EnumFacing side, TextureAtlasSprite sprite, int tint,
			float x0, float y0, float z0, float u0, float v0,
			float x1, float y1, float z1, float u1, float v1,
			float x2, float y2, float z2, float u2, float v2,
			float x3, float y3, float z3, float u3, float v3)
	{
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);

		builder.setQuadTint(tint);
		builder.setQuadOrientation(side);
		builder.setTexture(sprite);

		boolean hasTransform = transform.isPresent() && !transform.get().isIdentity();
		IVertexConsumer consumer = hasTransform ? new TRSRTransformer(builder, transform.get()) : builder;

		putVertex(consumer, format, side, x0, y0, z0, u0, v0);
		putVertex(consumer, format, side, x1, y1, z1, u1, v1);
		putVertex(consumer, format, side, x2, y2, z2, u2, v2);
		putVertex(consumer, format, side, x3, y3, z3, u3, v3);

		return builder.build();
	}

	private static void putVertex(IVertexConsumer consumer, VertexFormat format, EnumFacing side, float x, float y, float z, float u, float v)
	{
		for(int e = 0; e < format.getElementCount(); e++)
		{
			switch(format.getElement(e).getUsage())
			{
				case POSITION:
					consumer.put(e, x, y, z, 1f);
					break;
				case COLOR:
					consumer.put(e, 1f, 1f, 1f, 1f);
					break;
				case NORMAL:
					float offX = (float) side.getFrontOffsetX();
					float offY = (float) side.getFrontOffsetY();
					float offZ = (float) side.getFrontOffsetZ();
					consumer.put(e, offX, offY, offZ, 0f);
					break;
				case UV:
					if(format.getElement(e).getIndex() == 0)
					{
						consumer.put(e, u, v, 0f, 1f);
						break;
					}
					// else fallthrough to default
				default:
					consumer.put(e);
					break;
			}
		}
	}
}