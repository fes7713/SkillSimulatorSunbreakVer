/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1.Skill;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author fes77
 */
public class SwordGunnerDamageUpSkill extends DamageUpSkill implements SwordGunnerChoice{
    int [] gunnerDamageSequence;
    
    BooleanProperty gunner;
    
    public SwordGunnerDamageUpSkill(String title, int cost, int[] swordDamageSequence, int[] gunnerDamageSequence) {
        super(title, cost, swordDamageSequence);
        this.gunnerDamageSequence = gunnerDamageSequence;
        gunner = new SimpleBooleanProperty(false);
    }

    public BooleanProperty getGunnerProperty()
    {
        return gunner;
    }
    
    @Override
    public int getGunnerPoint(int level) {
        if(level == 0)
            return 0;
        if(level < 0)
            System.out.println("hello mo");
        return gunnerDamageSequence[level - 1];
    }

    @Override
    public int getDamageUp(int level) {
        if(gunner.get())
            return getGunnerPoint(level);
        else
            return super.getDamageUp(level);
    }
    
}
