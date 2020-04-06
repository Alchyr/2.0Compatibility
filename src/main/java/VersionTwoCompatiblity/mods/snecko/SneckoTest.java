package VersionTwoCompatiblity.mods.snecko;

import com.megacrit.cardcrawl.cards.AbstractCard;
import sneckomod.SneckoMod;

public class SneckoTest {
    public static boolean test(AbstractCard c)
    {
        return c.hasTag(SneckoMod.SNEKPROOF);
    }
}
