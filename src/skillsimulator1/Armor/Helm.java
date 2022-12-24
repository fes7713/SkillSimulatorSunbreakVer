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
public class Helm extends Armor{

    public Helm(String title, Map<Skill, Integer> skills, int slot4, int slot3, int slot2, int slot1) {
        super(title, skills, slot4, slot3, slot2, slot1);
    }

    public Helm(String title, int slot4, int slot3, int slot2, int slot1) {
        super(title, slot4, slot3, slot2, slot1);
    }

    @Override
    public String toString() {
        return getName();
    }
    
}
