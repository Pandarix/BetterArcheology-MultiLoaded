package net.Pandarix.fabric.datagen.provider;

import net.Pandarix.BACommon;
import net.Pandarix.fabric.datagen.ModDataGenerators;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Structure sets generated through datagen. The older sets are hand-written in resources.
 */
public class ModStructureSetProvider extends FabricDynamicRegistryProvider
{
    // Catacombs get their own set so the explorer map (100 chunk search) can find one; in the shared
    // archeology_sites set they were often thousands of blocks apart. Same placement as vanilla's
    // woodland mansion, which keeps them about as rare as before but evenly spread.
    private static final int CATACOMBS_SPACING = 80;
    private static final int CATACOMBS_SEPARATION = 20;
    private static final int CATACOMBS_SALT = 1876240577;

    public ModStructureSetProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries)
    {
        // The catacombs structure is our own hand-written data, not bound in the datagen lookup, so reference it by key.
        Holder<Structure> catacombs = Holder.Reference.createStandAlone(
                ModDataGenerators.serializableOwner(registries.lookupOrThrow(Registries.STRUCTURE)),
                BACommon.createRKey(Registries.STRUCTURE, "catacombs"));

        entries.add(BACommon.createRKey(Registries.STRUCTURE_SET, "catacombs"), new StructureSet(catacombs,
                new RandomSpreadStructurePlacement(CATACOMBS_SPACING, CATACOMBS_SEPARATION, RandomSpreadType.TRIANGULAR, CATACOMBS_SALT)));
    }

    @Override
    public @NotNull String getName()
    {
        return BACommon.MOD_NAME + " Structure Sets";
    }
}
