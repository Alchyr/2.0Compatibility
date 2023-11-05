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
        System.out.println("\t- Loading removed fonts.");
        Field f = FontHelper.class.getDeclaredField("deckCountFont");
        f.set(null, FontHelper.turnNumFont);
        System.out.println("\t\t- Assigned turnNumFont for deckCountFont.");

        /*
        PERF: Removed applyPowerFont. Using eventBodyText font instead.
        PERF: Removed bannerFont. Using losePowerFont instead.
        PERF: Removed cardTitleFont_small font. Using cardTitleFont scaled down instead. (scale 0.85f)
        PERF: Removed eventBodyText font. Using panelNameFont instead.
        PERF: Removed SCP_cardTypeFont. Using panelNameFont instead.
        PERF: Removed speech_font. Using turnNumFont instead.
        PERF: Removed textOnlyEventTitle font. Using losePowerFont instead.
         */
        f = FontHelper.class.getDeclaredField("applyPowerFont");
        f.set(null, FontHelper.panelNameFont);
        System.out.println("\t\t- Assigned panelNameFont for applyPowerFont.");

        f = FontHelper.class.getDeclaredField("bannerFont");
        f.set(null, FontHelper.losePowerFont);
        System.out.println("\t\t- Assigned losePowerFont for bannerFont.");

        f = FontHelper.class.getDeclaredField("cardTitleFont_small");
        f.set(null, FontHelper.cardTitleFont);
        System.out.println("\t\t- Assigned turnNumFont for cardTitleFont_small.");

        f = FontHelper.class.getDeclaredField("eventBodyText");
        f.set(null, FontHelper.panelNameFont);
        System.out.println("\t\t- Assigned panelNameFont for eventBodyText.");

        f = FontHelper.class.getDeclaredField("SCP_cardTypeFont");
        f.set(null, FontHelper.panelNameFont);
        System.out.println("\t\t- Assigned panelNameFont for SCP_cardTypeFont.");

        f = FontHelper.class.getDeclaredField("speech_font");
        f.set(null, FontHelper.turnNumFont);
        System.out.println("\t\t- Assigned turnNumFont for speech_font.");

        f = FontHelper.class.getDeclaredField("textOnlyEventTitle");
        f.set(null, FontHelper.losePowerFont);
        System.out.println("\t\t- Assigned losePowerFont for textOnlyEventTitle.");
    }
}
