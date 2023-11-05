package VersionTwoCompatiblity.mods.marksman;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import javassist.CtBehavior;


@SpirePatch(
        optional = true,
        cls = "marksman.patches.AscensionPatch",
        method = "Insert"
)
public class MarksmanAscensionPatchPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = "chosenClass"
    )
    public static void youNoNull(Object __obj_instance, @ByRef AbstractPlayer.PlayerClass[] chosenClass)
    {
        if (__obj_instance instanceof CharacterOption)
            chosenClass[0] = ((CharacterOption) __obj_instance).c.chosenClass;
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.PlayerClass.class, "toString");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
