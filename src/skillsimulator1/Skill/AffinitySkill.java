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
public class AffinitySkill extends AttackSkill implements AffinityUp{
    private final int[] affinitySequence;

    public AffinitySkill(String title, int cost, int[] affinitySequence) {
        super(title, cost, affinitySequence.length);
        this.affinitySequence = affinitySequence;
    }

    @Override
    public int getAffinityUp(int level) {
        if(level == 0)
            return 0;
        return affinitySequence[level - 1];
    }

    @Override
    public void editExpectation(Expectation exp, int level) {
        exp.addAffinity(getAffinityUp(level));
    }
    
    
}
