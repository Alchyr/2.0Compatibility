package VersionTwoCompatiblity.mods.relicfilter;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


@SpirePatch(
        optional = true,
        cls = "relicFilter.RelicFilterMod",
        method = "getAllRelics"
)
public class RelicFilterWatcherRelicsPatch {
    @SpirePostfixPatch
    public static ArrayList<AbstractRelic> dontForgetThese(ArrayList<AbstractRelic> __returnVal) {
        try
        {
            Field purple = RelicLibrary.class.getDeclaredField("purpleRelics");
            purple.setAccessible(true);

            HashMap<String, AbstractRelic> purpleRelics = (HashMap<String, AbstractRelic>) purple.get(null);
            if (purpleRelics != null) {
                //To ensure it doesn't break if relic filter is updated in the future.
                for (AbstractRelic r : purpleRelics.values())
                {
                    if (!__returnVal.contains(r))
                        __returnVal.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return __returnVal;
    }
}
