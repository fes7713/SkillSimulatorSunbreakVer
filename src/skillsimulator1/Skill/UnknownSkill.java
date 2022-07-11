/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1.Skill;

/**
 *
 * @author fes77
 */
public class UnknownSkill extends Skill{

    public UnknownSkill(String name, int cost) {
        super(name, cost, 1);
    }
    
    public int getMax()
    {
        return 5;
    }
    
}
