package net.Pandarix.sound;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.Pandarix.BACommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class ModSounds
{
    // REGISTRY ───────────────────────────────────────────────────────────────────────
    public static final Registrar<SoundEvent> SOUNDS = BACommon.REGISTRIES.get().get(Registries.SOUND_EVENT);

    // SOUNDS ──────────────────────────────────────────────────────────────────────────
    public static final RegistrySupplier<SoundEvent> MUSIC_DISC_SWINGS = registerSound(BACommon.createRLoc("swings"));
    public static final ResourceKey<JukeboxSong> SWINGS_SONG_KEY = ResourceKey.create(Registries.JUKEBOX_SONG, BACommon.createRLoc("swings"));

    // REGISTERING ────────────────────────────────────────────────────────────────────
    private static RegistrySupplier<SoundEvent> registerSound(ResourceLocation id)
    {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }

    // LOAD ────────────────────────────────────────────────────────────────────────────
    public static void register()
    {
        BACommon.logRegistryEvent(SOUNDS);
    }
}
