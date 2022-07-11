/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1;

import java.util.Map;
import skillsimulator1.Armor.Arm;
import skillsimulator1.Armor.Charm;
import skillsimulator1.Armor.Chest;
import skillsimulator1.Armor.Helm;
import skillsimulator1.Armor.Leg;
import skillsimulator1.Armor.Waist;
import skillsimulator1.Decoration.Decoration;

/**
 *
 * @author fes77
 */
public class Myset extends Equipment{
    String title;
    String weapon;
    
    public Myset(String title, double expectation, String weapon, Helm helm, Chest chest, Arm arm, Waist waist, Leg leg, Charm charm, Slot extraSlot, Map<Decoration, Integer> decorations) {
        super(null, helm, chest, arm, waist, leg, charm);
        this.title = title;
        this.weapon = weapon;
        this.extraSlot = extraSlot;
        calculated = true;
        bestDecorations.putAll(decorations);
        bestExpectation.set(expectation);
    }
    
    public Myset(String title, Equipment equipment)
    {
        this(title, equipment.getExpectation(), equipment.getWeapon(), 
                equipment.helm, equipment.chest, equipment.arm, equipment.waist, equipment.leg, equipment.charm,
                equipment.getExtraSlot(), equipment.getBestDecorations());
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public String getWeapon() {
        return weapon;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Myset)
        {
            Myset myset = (Myset)obj;
            if(!myset.getTitle().equals(title))
                return false;
            
            if(!myset.getHelm().equals(getHelm()))
                return false;
            if(!myset.getChest().equals(getChest()))
                return false;
            if(!myset.getArm().equals(getArm()))
                return false;
            if(!myset.getWaist().equals(getWaist()))
                return false;
            if(!myset.getLeg().equals(getLeg()))
                return false;
            if(!myset.getCharm().equals(getCharm()))
                return false;
            if(bestExpectation.get() != myset.getExpectation())
                return false;
            return true;
        }
        return false;
    }
    
    
}
