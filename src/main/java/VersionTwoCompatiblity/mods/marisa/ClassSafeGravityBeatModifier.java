package VersionTwoCompatiblity.mods.marisa;

import ThMod.cards.Marisa.GravityBeat;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassSafeGravityBeatModifier {
    public static void fix(AbstractCard c)
    {
        if (c.cardID.equals(GravityBeat.ID) && GravityBeat.EX_DESC == null)
        {
            try
            {
                Field EX_DESC = GravityBeat.class.getDeclaredField("EX_DESC");
                EX_DESC.setAccessible(true);

                Field modifiers = Field.class.getDeclaredField("modifiers");
                modifiers.setAccessible(true);

                modifiers.setInt(EX_DESC, EX_DESC.getModifiers() & ~Modifier.FINAL);

                final String[] s = new String[] {
                        " NL (",
                        " time(s))."
                };
                EX_DESC.set(null, s);
                System.out.println("Fixed null text in GravityBeat.");

                modifiers.setInt(EX_DESC, EX_DESC.getModifiers() & Modifier.FINAL);
            }
            catch (Exception e)
            {
                System.out.println("Failed to fix null text in GravityBeat.");
            }
        }
        else
        {
            System.out.println("GravityBeat extended description is not null.");
        }
    }
}
