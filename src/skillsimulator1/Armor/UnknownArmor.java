/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1.Armor;

import java.util.Map;
import skillsimulator1.Skill.Skill;

/**
 *
 * @author fes77
 */
public class UnknownArmor extends Armor{

    public UnknownArmor(String title, Map<Skill, Integer> skills, int slot4, int slot3, int slot2, int slot1) {
        super(title, skills, slot4, slot3, slot2, slot1);
    }

    public UnknownArmor(String title, int slot4, int slot3, int slot2, int slot1) {
        super(title, slot4, slot3, slot2, slot1);
    }
    
    public Helm convHelm()
    {
        return new Helm(getName(), getSkills(), getSlot4(), getSlot3(), getSlot2(), getSlot1());
    }
    
    public Chest convChest()
    {
        return new Chest(getName(), getSkills(), getSlot4(), getSlot3(), getSlot2(), getSlot1());
    }
    
    public Arm convArm()
    {
        return new Arm(getName(), getSkills(), getSlot4(), getSlot3(), getSlot2(), getSlot1());
    }
    
    public Waist convWaist()
    {
        return new Waist(getName(), getSkills(), getSlot4(), getSlot3(), getSlot2(), getSlot1());
    }
    
    public Leg convLeg()
    {
        return new Leg(getName(), getSkills(), getSlot4(), getSlot3(), getSlot2(), getSlot1());
    }
    public Charm convCharm()
    {
        return new Charm(getName(), getSkills(), getSlot4(), getSlot3(), getSlot2(), getSlot1());
    }
}
