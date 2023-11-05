package VersionTwoCompatiblity.mods.marisa;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        optional = true,
        cls = "ThMod.cards.Marisa.GravityBeat",
        method = SpirePatch.CONSTRUCTOR
)
public class SafeLocalizationPatch {
    private static boolean checked = false;

    @SpirePostfixPatch
    public static void generateFakeStrings(AbstractCard __instance)
    {
        if (!checked && Loader.isModLoaded("TS05_Marisa"))
        {
            ClassSafeGravityBeatModifier.fix(__instance);
        }
        checked = true;
    }
}
