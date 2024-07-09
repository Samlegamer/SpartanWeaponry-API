package com.oblivioussp.spartanweaponry.item.crafting;

import com.google.gson.JsonObject;
import com.oblivioussp.spartanweaponry.init.ModCapabilities;
import com.oblivioussp.spartanweaponry.init.ModRecipeSerializers;
import com.oblivioussp.spartanweaponry.item.QuiverBaseItem;
import com.oblivioussp.spartanweaponry.util.Log;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

public class QuiverUpgradeRecipe extends SmithingTransformRecipe
{
	public QuiverUpgradeRecipe(ResourceLocation recipeId, Ingredient template, Ingredient base, Ingredient addition, ItemStack result)
	{
		super(recipeId, template, base, addition, result);
	}

	@Override
	public ItemStack assemble(Container inv, RegistryAccess registryAccessIn) 
	{
		ItemStack origOutputStack = getResultItem(registryAccessIn);
		ItemStack outputStack = super.assemble(inv, registryAccessIn);
		// Resize the output tag
		// NOTE: More consistent, but inefficient
		outputStack.getCapability(ModCapabilities.QUIVER_ITEM_CAPABILITY).ifPresent((itemHandler) -> {
			itemHandler.resize(origOutputStack.getOrCreateTagElement(QuiverBaseItem.NBT_AMMO).getInt("Size"));
		});
//		outputStack.getOrCreateTagElement(QuiverBaseItem.NBT_AMMO).putInt("Size", origOutputStack.getOrCreateTagElement(QuiverBaseItem.NBT_AMMO).getInt("Size"));
		
		return outputStack;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return ModRecipeSerializers.QUIVER_UPGRADE_SMITHING.get();
	}

	@Override
	public RecipeType<?> getType() 
	{
		return RecipeType.SMITHING;
	}

	public static class Serializer implements RecipeSerializer<QuiverUpgradeRecipe>
	{
		public Serializer() {}

		@Override
		public QuiverUpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json)
		{
	        Ingredient templateIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "template"));
	        Ingredient baseIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
	        Ingredient additionIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
	        ItemStack resultStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			return new QuiverUpgradeRecipe(recipeId, templateIngredient, baseIngredient, additionIngredient, resultStack);
		}

		@Override
		public QuiverUpgradeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) 
		{
			try
			{
		        Ingredient templateIngredient = Ingredient.fromNetwork(buffer);
		        Ingredient baseIngredient = Ingredient.fromNetwork(buffer);
		        Ingredient additionIngredient = Ingredient.fromNetwork(buffer);
		        ItemStack resultStack = buffer.readItem();
				return new QuiverUpgradeRecipe(recipeId, templateIngredient, baseIngredient, additionIngredient, resultStack);
			}
			catch(Exception e)
			{
				Log.error("Failed to read a Quiver Upgrade Smithing Recipe from a packet!");
				throw e;
			}
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, QuiverUpgradeRecipe recipe) 
		{
			try
			{
				RecipeSerializer.SMITHING_TRANSFORM.toNetwork(buffer, recipe);
			}
			catch(Exception e)
			{
				Log.error("Failed to write a Quiver Upgrade Smithing Recipe from a packet!");
				throw e;
			}
		}
	}
}
