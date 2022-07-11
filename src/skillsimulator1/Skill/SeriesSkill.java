/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1.Skill;

/**
 *
 * @author fes77
 */
public class SeriesSkill extends Skill{

    public SeriesSkill(String name, int maxLevel) {
        super(name, 2, maxLevel);
    }
    
    @Override
    public void updateScore()
    {
        score = getRequired() * getCost() * getCost() * 3;
    }
}
