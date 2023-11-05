package VersionTwoCompatiblity.eventtext;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.ui.DialogWord.AppearEffect;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class HeartAndVampiresCharacterText
{
	@SpirePatch(
			clz = Vampires.class,
			method = SpirePatch.CONSTRUCTOR
	)
	public static class VampirePatch {
		@SpirePostfixPatch
		public static void nullText(Vampires __instance, @ByRef String[] ___body) {
			if (___body[0] == null) {
				___body[0] = Vampires.DESCRIPTIONS[0];
			}
		}
	}

	@SpirePatch(
			clz = SpireHeart.class,
			method = "buttonEffect"
	)
	public static class HeartPatch {
		@SpireInstrumentPatch
		public static ExprEditor nullText() {
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("getSpireHeartText"))
					{
						m.replace("{" +
								"$_ = $proceed($$);" +
								"if ($_ == null) {" +
									"$_ = " + SpireHeart.class.getName() + ".DESCRIPTIONS[8];" +
									"}" +
								"}");
					}
					else if (m.getMethodName().equals("getSpireHeartSlashEffect"))
					{
						m.replace("{" +
								"$_ = $proceed($$);" +
								"if ($_ == null || $_.length == 0) {" +
									"$_ = " + HeartAndVampiresCharacterText.class.getName() + ".getDefaultSpireHeartSlashEffect();" +
								"}" +
								"}");
					}
					else if (m.getMethodName().equals("getSlashAttackColor"))
					{
						m.replace("{" +
								"$_ = $proceed($$);" +
								"if ($_ == null) {" +
								"$_ = " + Color.class.getName() + ".WHITE.cpy();" +
								"}" +
								"}");
					}
				}
			};

		}
	}

	public static AbstractGameAction.AttackEffect[] getDefaultSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};// 332
	}
}
