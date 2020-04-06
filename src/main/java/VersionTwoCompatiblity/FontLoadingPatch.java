package VersionTwoCompatiblity;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.lang.reflect.Field;

@SpirePatch(
        clz = FontHelper.class,
        method = "initialize"
)
public class FontLoadingPatch {
    @SpirePostfixPatch
    public static void assignFonts() throws NoSuchFieldException, IllegalAccessException {
        Field f = FontHelper.class.getDeclaredField("deckCountFont");

        f.set(null, FontHelper.speech_font);
    }
}
