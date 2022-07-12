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
public abstract class AttackSkill extends Skill{

    
    public AttackSkill(String title, int cost, int maxLevel) {
        super(title, cost, maxLevel);
        
    }
    
    public void evalExpectation(Expectation exp, int level)
    {
        if(isActive())
            editExpectation(exp, level);
    }
    
    abstract protected void editExpectation(Expectation exp, int level);
    
    @Override
    public void updateScore()
    {
        score =  getCost() * (getCost() >= 2 ? 2 : 1) * (isActive() || requiredProperty().get() > 0 ? 1 : 0);
    }
}
