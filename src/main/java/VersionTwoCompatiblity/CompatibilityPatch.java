package VersionTwoCompatiblity;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;


@SpirePatch(
        clz = CardCrawlGame.class,
        method = SpirePatch.CONSTRUCTOR
)
public class CompatibilityPatch {
    public static void Raw(CtBehavior ctBehavior) throws NotFoundException, CannotCompileException {
        System.out.println("\t\t- Performing 2.0 Compatibility Patch -");

        System.out.println("\t- Adding fields to AbstractCreature.");

        CtClass abstractCreature = ctBehavior.getDeclaringClass().getClassPool().get(AbstractCreature.class.getName());

        CtField damageFlash = CtField.make("public boolean damageFlash;", abstractCreature);
        CtField damageFlashFrames = CtField.make("public int damageFlashFrames;", abstractCreature);

        abstractCreature.addField(damageFlash, "false");
        System.out.println("\t\t- Added public field damageFlash.");
        abstractCreature.addField(damageFlashFrames, "0");
        System.out.println("\t\t- Added public field damageFlashFrames.");

        System.out.println("\t- Adding missing font fields.");

        CtClass fontHelper = ctBehavior.getDeclaringClass().getClassPool().get(FontHelper.class.getName());

        CtField font = CtField.make("public static com.badlogic.gdx.graphics.g2d.BitmapFont deckCountFont;", fontHelper);

        fontHelper.addField(font);
        System.out.println("\t\t- Added public static field deckCountFont.");

        /*
        PERF: Removed applyPowerFont. Using eventBodyText font instead.
        PERF: Removed bannerFont. Using losePowerFont instead.
        PERF: Removed eventBodyText font. Using panelNameFont instead.
        PERF: Removed SCP_cardTypeFont. Using panelNameFont instead.
        PERF: Removed speech_font. Using turnNumFont instead.
        PERF: Removed textOnlyEventTitle font. Using losePowerFont instead.
         */
        System.out.println("\n\t- v2.2 removed fonts:\n");
        font = CtField.make("public static com.badlogic.gdx.graphics.g2d.BitmapFont applyPowerFont;", fontHelper);
        fontHelper.addField(font);
        System.out.println("\t\t- Added public static field applyPowerFont.");

        font = CtField.make("public static com.badlogic.gdx.graphics.g2d.BitmapFont bannerFont;", fontHelper);
        fontHelper.addField(font);
        System.out.println("\t\t- Added public static field bannerFont.");

        font = CtField.make("public static com.badlogic.gdx.graphics.g2d.BitmapFont cardTitleFont_small;", fontHelper);
        fontHelper.addField(font);
        System.out.println("\t\t- Added public static field cardTitleFont_small.");
        //System.out.println("\t\t- cardTitleFont_small is not be defined, as it does not have a direct replacement.");

        font = CtField.make("public static com.badlogic.gdx.graphics.g2d.BitmapFont eventBodyText;", fontHelper);
        fontHelper.addField(font);
        System.out.println("\t\t- Added public static field eventBodyText.");

        font = CtField.make("public static com.badlogic.gdx.graphics.g2d.BitmapFont SCP_cardTypeFont;", fontHelper);
        fontHelper.addField(font);
        System.out.println("\t\t- Added public static field SCP_cardTypeFont.");

        font = CtField.make("public static com.badlogic.gdx.graphics.g2d.BitmapFont speech_font;", fontHelper);
        fontHelper.addField(font);
        System.out.println("\t\t- Added public static field speech_font.");

        font = CtField.make("public static com.badlogic.gdx.graphics.g2d.BitmapFont textOnlyEventTitle;", fontHelper);
        fontHelper.addField(font);
        System.out.println("\t\t- Added public static field textOnlyEventTitle.");


        System.out.println("\n\t- Adding methods to AbstractCard.");
        CtClass abstractCard = ctBehavior.getDeclaringClass().getClassPool().get(AbstractCard.class.getName());

        CtMethod modifyCostForTurn = CtNewMethod.make(
                "public void modifyCostForTurn(int amt) { $0.setCostForTurn($0.costForTurn + amt); }",
                abstractCard);

        abstractCard.addMethod(modifyCostForTurn);
        System.out.println("\t\t- Added method modifyCostForTurn.");

        /*
        System.out.println("\t- Fixing field access in creature classes.");
        for (ClassInfo classInfo : foundClasses) {
            CtClass ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());

            System.out.println("\t- Class: " + ctClass.getName());
            try {
                ctClass.instrument(new FlashFieldAccess());
                //ctClass.instrument(new GeneralCompatibility());
            } catch(CannotCompileException e) {
                logger.error("\t\t- Failure.\n");
                e.printStackTrace();
            }
        }*/
        System.out.println("\t- Done Patching.");
    }

    private static class FlashFieldAccess extends ExprEditor {
        @Override
        public void edit(FieldAccess f) throws CannotCompileException {
            if (f == null)
                return;

            if (f.getFieldName().equals("damageFlash")) {
                if (f.isWriter())
                {
                    System.out.println("\t\t- Removing Field Write: " + f.getFieldName());
                    f.replace("{}");
                }
                else if (f.isReader())
                {
                    System.out.println("\t\t- Replacing Field Read with Default Value: " + f.getFieldName());
                    f.replace("{ $_ = false; }");
                }
            }
            else {
                if (f.getFieldName().equals("damageFlashFrames"))
                {
                    if (f.isWriter())
                    {
                        System.out.println("\t\t- Removing Field Write: " + f.getFieldName());
                        f.replace("{}");
                    }
                    else if (f.isReader())
                    {
                        System.out.println("\t\t- Replacing Field Read with Default Value: " + f.getFieldName());
                        f.replace("{ $_ = 0; }");
                    }
                }
            }
        }
    }

    /*
    private static class GeneralCompatibility extends ExprEditor {
        @Override
        public void edit(FieldAccess f) throws CannotCompileException {
            if (f == null)
                return;

            f.getClassName()

            if (f.getFieldName().equals("damageFlash")) {
                if (f.isWriter())
                {
                    System.out.println("\t\t- Removing Field Write: " + f.getFieldName());
                    f.replace("{}");
                }
                else if (f.isReader())
                {
                    System.out.println("\t\t- Replacing Field Read with Default Value: " + f.getFieldName());
                    f.replace("{ $_ = false; }");
                }
            }
            else {
                if (f.getFieldName().equals("damageFlashFrames"))
                {
                    if (f.isWriter())
                    {
                        System.out.println("\t\t- Removing Field Write: " + f.getFieldName());
                        f.replace("{}");
                    }
                    else if (f.isReader())
                    {
                        System.out.println("\t\t- Replacing Field Read with Default Value: " + f.getFieldName());
                        f.replace("{ $_ = 0; }");
                    }
                }
            }
        }

        @Override
        public void edit(MethodCall m) throws CannotCompileException {
            if (m == null)
                return;

            if(m.getMethodName().equals("modifyCostForTurn")) {
                System.out.println("\t\t- Modifying Method Call: " + m.getMethodName());
                m.replace("{" +
                        "$0.setCostForTurn($0.costForTurn + $1);" +
                        "}");
            }
        }
    }*/
}
