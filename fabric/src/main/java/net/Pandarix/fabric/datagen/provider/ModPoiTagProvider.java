package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.villager.ModVillagers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;

import java.util.concurrent.CompletableFuture;

public class ModPoiTagProvider extends PoiTypeTagsProvider
{
    public ModPoiTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
        if(ModVillagers.ARCHEOLOGY_TABLE_POI.getKey() != null){
            this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                    .add(ModVillagers.ARCHEOLOGY_TABLE_POI.getKey());
        }
    }
}