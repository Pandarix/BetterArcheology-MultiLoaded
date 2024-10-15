package net.Pandarix.config;

import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColor;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColorValue;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigLink;
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue;
import net.Pandarix.BACommon;

public class BAConfigInfoProvider implements ResourcefulConfigInfo
{
    private final String id;

    public BAConfigInfoProvider(String id) {
        this.id = id;
    }

    @Override
    public TranslatableValue title() {
        return new TranslatableValue("BetterArcheology Config");
    }

    @Override
    public TranslatableValue description() {
        return new TranslatableValue("This is the common config of " + BACommon.MOD_ID + ".");
    }

    @Override
    public String icon() {
        return "circle";
    }

    @Override
    public ResourcefulConfigColor color() {
        return (ResourcefulConfigColorValue) () -> "#FF0000";
    }

    @Override
    public ResourcefulConfigLink[] links() {
        return new ResourcefulConfigLink[0];
    }

    @Override
    public boolean isHidden() {
        return false;
    }
}
