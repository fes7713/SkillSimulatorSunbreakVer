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
public class AffinityMultiplierSkill extends AttackSkill implements AffinityMultiplier{
    private double[] multiplierSequence;

    public AffinityMultiplierSkill(String title, int cost, double[] multiplierSequence) {
        super(title, cost, multiplierSequence.length);
        this.multiplierSequence = multiplierSequence;
    }

    @Override
    public double getAffinityMultiplier(int level) {
        if(level == 0)
            return 1.25;
        return multiplierSequence[level - 1];
    }

    @Override
    public void editExpectation(Expectation exp, int level) {
        exp.setAffinityMultiplier(getAffinityMultiplier(level));
    }
    
    
}
