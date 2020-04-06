package VersionTwoCompatiblity;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@SpirePatch(
        clz = CardCrawlGame.class,
        method = SpirePatch.CONSTRUCTOR
)
public class CompatibilityPatch {
    private static Logger logger = LogManager.getLogger("2.0 Compatibility");

    public static void Raw(CtBehavior ctBehavior) throws NotFoundException, CannotCompileException {
        logger.info("\t\t- Performing 2.0 Compatibility Patch -");

        /*
        ClassFinder finder = new ClassFinder();

        finder.add(new File(Loader.STS_JAR));

        for (ModInfo modInfo : Loader.MODINFOS) {
            if (modInfo.jarURL != null) {
                try {
                    finder.add(new File(modInfo.jarURL.toURI()));
                } catch (URISyntaxException e) {
                    // do nothing
                }
            }
        }

        ClassFilter playerFilter = new AndClassFilter(
                new NotClassFilter(new InterfaceOnlyClassFilter()),
                new ClassModifiersClassFilter(Modifier.PUBLIC),
                new org.clapper.util.classutil.SubclassClassFilter(AbstractCreature.class)
                /*new PackageFilter(new String[] {
                        "com.megacrit.cardcrawl.",
                        /*"com.megacrit.cardcrawl.actions",
                        "com.megacrit.cardcrawl.audio",
                        "com.megacrit.cardcrawl.blights",
                        "com.megacrit.cardcrawl.cards.red",
                        "com.megacrit.cardcrawl.cards.green",
                        "com.megacrit.cardcrawl.cards.blue",
                        "com.megacrit.cardcrawl.cards.purple",
                        "com.megacrit.cardcrawl.cards.colorless",
                        "com.megacrit.cardcrawl.cards.status",
                        "com.megacrit.cardcrawl.cards.curse",
                        "com.megacrit.cardcrawl.cards.deprecated",
                        "com.megacrit.cardcrawl.cards.optionCards",
                        "com.megacrit.cardcrawl.cards.tempCards",
                        "com.megacrit.cardcrawl.characters",
                        "com.megacrit.cardcrawl.credits",
                        "com.megacrit.cardcrawl.cutscenes",
                        "com.megacrit.cardcrawl.daily",
                        "com.megacrit.cardcrawl.dungeon",
                        "com.megacrit.cardcrawl.events",
                        "com.megacrit.cardcrawl.exceptions",
                        "com.megacrit.cardcrawl.helpers",
                        "com.megacrit.cardcrawl.integrations",
                        "com.megacrit.cardcrawl.localization",
                        "com.megacrit.cardcrawl.map",
                        "com.megacrit.cardcrawl.metrics",
                        "com.megacrit.cardcrawl.monsters",
                        "com.megacrit.cardcrawl.neow",
                        "com.megacrit.cardcrawl.orbs",
                        "com.megacrit.cardcrawl.potions",
                        "com.megacrit.cardcrawl.powers",
                        "com.megacrit.cardcrawl.relics",
                        "com.megacrit.cardcrawl.rewards",
                        "com.megacrit.cardcrawl.rooms",
                        "com.megacrit.cardcrawl.saveAndContinue",
                        "com.megacrit.cardcrawl.scenes",
                        "com.megacrit.cardcrawl.screens",
                        "com.megacrit.cardcrawl.shop",
                        "com.megacrit.cardcrawl.stances",
                        "com.megacrit.cardcrawl.steamController",
                        "com.megacrit.cardcrawl.trials",
                        "com.megacrit.cardcrawl.ui",
                        "com.megacrit.cardcrawl.unlock",
                        "com.megacrit.cardcrawl.vfx",*//*
                        "de.robojumper.",
                        "com.badlogic.",
                        "com.esotericsoftware.",
                        "com.brashmonkey.",
                        "org.lwjgl.",
                        "com.codedisaster.",
                        "com.google.",
                        "org.apache.",
                        "io.sentry.",
                        "org.slf4j.",
                        "com.gikk.",
                        "com.sun.",
                        "net.arikia.",
                        "javazoom.jl.",
                        "com.jcraft.",
                        "net.java.",
                        "com.fasterxml.",
                        "VersionTwoCompatiblity.",
                        "basemod.",
                        "com.evacipated.cardcrawl.mod.stslib."
                })*/
        //);

        /*ArrayList<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, playerFilter);

        logger.info("- Found creature classes: " + foundClasses.size());
        logger.info("- Beginning patching.");*/

        //CtClass abstractPotion = ctBehavior.getDeclaringClass().getClassPool().get("com.megacrit.cardcrawl.potions.AbstractPotion");
        //abstractcreature =
        //abstractcard = ctBehavior.getDeclaringClass().getClassPool().get("com.megacrit.cardcrawl.cards.AbstractCard");

        logger.info("\t- Adding fields to AbstractCreature.");

        CtClass abstractCreature = ctBehavior.getDeclaringClass().getClassPool().get(AbstractCreature.class.getName());

        CtField damageFlash = CtField.make("public boolean damageFlash;", abstractCreature);
        CtField damageFlashFrames = CtField.make("public int damageFlashFrames;", abstractCreature);

        abstractCreature.addField(damageFlash, "false");
        logger.info("\t\t- Added public field damageFlash.");
        abstractCreature.addField(damageFlashFrames, "0");
        logger.info("\t\t- Added public field damageFlashFrames.");

        logger.info("\t- Adding missing font fields.");

        CtClass fontHelper = ctBehavior.getDeclaringClass().getClassPool().get(FontHelper.class.getName());

        CtField deckCountFont = CtField.make("public static com.badlogic.gdx.graphics.g2d.BitmapFont deckCountFont;", fontHelper);

        fontHelper.addField(deckCountFont);
        logger.info("\t\t- Added public static field deckCountFont.");

        logger.info("\t- Adding methods to AbstractCard.");
        CtClass abstractCard = ctBehavior.getDeclaringClass().getClassPool().get(AbstractCard.class.getName());

        CtMethod modifyCostForTurn = CtNewMethod.make(
                "public void modifyCostForTurn(int amt) { $0.setCostForTurn($0.costForTurn + amt); }",
                abstractCard);

        abstractCard.addMethod(modifyCostForTurn);
        logger.info("\t\t- Added method modifyCostForTurn.");

        /*
        logger.info("\t- Fixing field access in creature classes.");
        for (ClassInfo classInfo : foundClasses) {
            CtClass ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());

            logger.info("\t- Class: " + ctClass.getName());
            try {
                ctClass.instrument(new FlashFieldAccess());
                //ctClass.instrument(new GeneralCompatibility());
            } catch(CannotCompileException e) {
                logger.error("\t\t- Failure.\n");
                e.printStackTrace();
            }
        }*/
        logger.info("\t- Done Patching.");
    }

    private static class FlashFieldAccess extends ExprEditor {
        @Override
        public void edit(FieldAccess f) throws CannotCompileException {
            if (f == null)
                return;

            if (f.getFieldName().equals("damageFlash")) {
                if (f.isWriter())
                {
                    logger.info("\t\t- Removing Field Write: " + f.getFieldName());
                    f.replace("{}");
                }
                else if (f.isReader())
                {
                    logger.info("\t\t- Replacing Field Read with Default Value: " + f.getFieldName());
                    f.replace("{ $_ = false; }");
                }
            }
            else {
                if (f.getFieldName().equals("damageFlashFrames"))
                {
                    if (f.isWriter())
                    {
                        logger.info("\t\t- Removing Field Write: " + f.getFieldName());
                        f.replace("{}");
                    }
                    else if (f.isReader())
                    {
                        logger.info("\t\t- Replacing Field Read with Default Value: " + f.getFieldName());
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
                    logger.info("\t\t- Removing Field Write: " + f.getFieldName());
                    f.replace("{}");
                }
                else if (f.isReader())
                {
                    logger.info("\t\t- Replacing Field Read with Default Value: " + f.getFieldName());
                    f.replace("{ $_ = false; }");
                }
            }
            else {
                if (f.getFieldName().equals("damageFlashFrames"))
                {
                    if (f.isWriter())
                    {
                        logger.info("\t\t- Removing Field Write: " + f.getFieldName());
                        f.replace("{}");
                    }
                    else if (f.isReader())
                    {
                        logger.info("\t\t- Replacing Field Read with Default Value: " + f.getFieldName());
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
                logger.info("\t\t- Modifying Method Call: " + m.getMethodName());
                m.replace("{" +
                        "$0.setCostForTurn($0.costForTurn + $1);" +
                        "}");
            }
        }
    }*/
}
