package VersionTwoCompatiblity.mods.snecko;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.ConfusionPower;

@SpirePatch(
        clz = ConfusionPower.class,
        method = "onCardDraw"
)
public class SneckoproofPatch {
    @SpirePrefixPatch
    public static SpireReturn<?> sneckoProof(ConfusionPower __instance, AbstractCard c)
    {
        if (Loader.isModLoaded("SneckoMod"))
        {
            if (SneckoTest.test(c))
            {
                return SpireReturn.Return(null);
            }
        }
        return SpireReturn.Continue();
    }
}
