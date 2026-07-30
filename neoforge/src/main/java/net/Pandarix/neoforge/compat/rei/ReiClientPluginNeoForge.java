package net.Pandarix.neoforge.compat.rei;

import me.shedaniel.rei.forge.REIPluginClient;
import net.Pandarix.compat.rei.ReiClientPlugin;

/**
 * Client half of {@link ReiPluginNeoForge}. Registers the Identifying category and its
 * workstation. Without it REI on NeoForge has no Archeology Table tab at all.
 */
@REIPluginClient
public class ReiClientPluginNeoForge extends ReiClientPlugin
{
}
