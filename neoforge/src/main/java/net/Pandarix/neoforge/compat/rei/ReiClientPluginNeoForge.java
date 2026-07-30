package net.Pandarix.neoforge.compat.rei;

import me.shedaniel.rei.forge.REIPluginClient;
import net.Pandarix.compat.rei.ReiClientPlugin;

/**
 * NeoForge finds REI plugins by annotation, Fabric by the rei_client entrypoint in
 * fabric.mod.json. The annotations only exist in REI's NeoForge jar, which the common plugin
 * class cannot see, so this subclass exists purely to be discovered. Without it REI on NeoForge
 * has no Archeology Table tab at all.
 */
@REIPluginClient
public class ReiClientPluginNeoForge extends ReiClientPlugin
{
}
