package com.oblivioussp.spartanweaponry.data;

import java.util.List;
import java.util.Set;

import com.oblivioussp.spartanweaponry.data.loot.ModBlockLoot;
import com.oblivioussp.spartanweaponry.data.loot.ModChestLoot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class ModLootTablesProvider extends LootTableProvider 
{
//	List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> lootTables = ImmutableList.of(Pair.of(ModBlockLoot::new, LootContextParamSets.BLOCK), Pair.of(ModChestLootTables::new, LootContextParamSets.CHEST));

	public ModLootTablesProvider(PackOutput output, Set<ResourceLocation> requiredTables) 
	{
		super(output, requiredTables, List.of(new LootTableProvider.SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK), 
											new LootTableProvider.SubProviderEntry(ModChestLoot::new, LootContextParamSets.CHEST)));
	}
}
