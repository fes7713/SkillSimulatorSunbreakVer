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
public class DamageMultiplierPreSkill extends DamageMultiplierSkill{

    public DamageMultiplierPreSkill(String title, int cost, double[] multiplerSequece) {
        super(title, cost, multiplerSequece);
    }
    
    @Override
    public void editExpectation(Expectation exp, int level){
        exp.multiplyPre(getDamageMultiplier(level));
    }
    
}
