/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1.Skill;

/**
 *
 * @author fes77
 */
public abstract class DamageMultiplierSkill  extends AttackSkill implements DamageMultiplier{
    private final double[] multiplerSequece;

    public DamageMultiplierSkill(String title, int cost, double[] multiplerSequece) {
        super(title, cost, multiplerSequece.length);
        this.multiplerSequece = multiplerSequece;
    }

    @Override
    public double getDamageMultiplier(int level) {
        if(level == 0)
            return 1;
        return multiplerSequece[level - 1];
    }
}

    