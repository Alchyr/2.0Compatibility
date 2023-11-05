package VersionTwoCompatiblity.mods.relicfilter;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch(
        optional = true,
        cls = "relicFilter.patches.com.megacrit.cardcrawl.relics.AbstractRelic.AbstractRelicConstructorPatch",
        method = "Insert"
)
public class RelicFilterNeverNullPatch {
    @SpirePostfixPatch
    public static void pleaseDoNotNullTheTier(AbstractRelic __instance, String relicName, String relicImg, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        if (__instance.tier == null)
            __instance.tier = tier != null ? tier : AbstractRelic.RelicTier.COMMON;
    }
}
