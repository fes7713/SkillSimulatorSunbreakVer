/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1.Skill;

import skillsimulator1.Expectation;



/**
 *
 * @author fes77
 */
public class DamageUpSkill extends AttackSkill implements DamageUp{
    private final int[] damageSequence;

    public DamageUpSkill(String title, int cost, int[] damageSequence) {
        super(title, cost, damageSequence.length);
        this.damageSequence = damageSequence;
    }
    
    @Override
    public int getDamageUp(int level) {
        if(level == 0)
            return 0;
        if(level < 0)
            System.out.println("hello mo");
        return damageSequence[level - 1];
    }
    
    @Override
    public void editExpectation(Expectation exp, int level){
        exp.addDamageIncrease(getDamageUp(level));
    }
}
