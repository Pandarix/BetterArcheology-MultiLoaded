package net.Pandarix.neoforge.compat.rei;

import me.shedaniel.rei.forge.REIPluginCommon;
import net.Pandarix.compat.rei.ReiPlugin;

/**
 * NeoForge finds REI plugins by annotation, Fabric by the rei_common/rei_client entrypoints in
 * fabric.mod.json. The annotations only exist in REI's NeoForge jar, which the common plugin
 * classes cannot see, so this subclass exists purely to be discovered.
 */
@REIPluginCommon
public class ReiPluginNeoForge extends ReiPlugin
{
}
