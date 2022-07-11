/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1.Decoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import skillsimulator1.Simulator;
import skillsimulator1.Skill.AttackSkill;
import skillsimulator1.Skill.Skill;


/**
 *
 * @author fes77
 */
public class Decoration {
    private Skill skill;
    private String name;
    private int level;
    private int cost;

    public Decoration(Skill skill, String name, int cost, int level) {
        this.skill = skill;
        this.name = name;
        this.level = level;
        this.cost = cost;
        
        skill.addDecoration(this);
    }

    public Skill getSkill() {
        return skill;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getCost() {
        return cost;
    }
   
    
    public static boolean decorationSimulator(
            int slot4, int slot3, int slot2, int slot1, //Info
            Map<Skill, Integer> remainingAttackSkills, // Info 
            Map<Skill, Integer> remainingRequiredSkills, // Info
//            Map<Decoration, Integer> attackDecorations,
            List<Decoration> ALL_DECORATIONS) //return
    {
        List<AttackSkill> attackSkills = 
                Simulator.ALL_ATTACK_SKILLS
                        .stream()
                        .filter(Skill::isActive)
                        .filter(AttackSkill.class::isInstance)
                        .map(AttackSkill.class::cast)
                        .collect(Collectors.toList());
        
        List<Skill> requiredSkills =
                Simulator.ALL_SKILLS
                        .stream()
                        .filter(Skill::isActive)
                        .filter(skill -> skill.getRequired() > 0)
                        .collect(Collectors.toList());
        
        List<Decoration> slot4RequiredDecorations = new ArrayList<>();
        List<Decoration> slot3RequiredDecorations = new ArrayList<>();
        List<Decoration> slot2RequiredDecorations = new ArrayList<>();
        List<Decoration> slot1RequiredDecorations = new ArrayList<>();
        
        List<Decoration> requiredDecorations = 
                ALL_DECORATIONS
                        .stream()
                        .filter(deco -> requiredSkills.contains(deco.getSkill()))
                        .collect(Collectors.toList());
        for(Decoration deco : requiredDecorations)
        {
            switch(deco.getCost())
            {
                case 1 ->  slot1RequiredDecorations.add(deco);
                case 2 ->  slot2RequiredDecorations.add(deco);
                case 3 ->  slot3RequiredDecorations.add(deco);
                case 4 ->  slot4RequiredDecorations.add(deco);
                default -> throw new IllegalArgumentException("Slot error");
            }
        }

        List<Decoration> slot4AttackDecorations = new ArrayList<>();
        List<Decoration> slot3AttackDecorations = new ArrayList<>();
        List<Decoration> slot2AttackDecorations = new ArrayList<>();
        List<Decoration> slot1AttackDecorations = new ArrayList<>();
        
        List<Decoration> attackDecorations = 
                ALL_DECORATIONS
                        .stream()
                        .filter(deco -> attackSkills.contains(deco.getSkill()))
                        .collect(Collectors.toList());
        for(Decoration deco : attackDecorations)
        {
            switch(deco.getCost())
            {
                case 1 ->  slot1AttackDecorations.add(deco);
                case 2 ->  slot2AttackDecorations.add(deco);
                case 3 ->  slot3AttackDecorations.add(deco);
                case 4 ->  slot4AttackDecorations.add(deco);
                default -> throw new IllegalArgumentException("Slot error");
            }
        }
        
        Map<Decoration, Integer> decorationMap = new HashMap();
        
//        for(int i = 0; i < )
//        {
            decorationLoop(slot4, slot3, slot2, slot1, remainingAttackSkills, remainingRequiredSkills, requiredSkills, 0, ALL_DECORATIONS);
//        }
        return true;
    }
    
    public static boolean decorationLoop(
            int slot4, int slot3, int slot2, int slot1, //Info
            Map<Skill, Integer> remainingAttackSkills, // Info 
            Map<Skill, Integer> remainingRequiredSkills, // Info
//            Map<Decoration, Integer> attackDecorations,
            List<Skill> requiredSkills,
            int currentIndex,
            List<Decoration> ALL_DECORATIONS) //return
    {
        Skill requiredSkill = requiredSkills.get(currentIndex);
        Decoration biggestDecoration = requiredSkill.getBiggestDecoration();
        int cost = biggestDecoration.getCost();
        int level = biggestDecoration.getCost();
        
        int requiredLevel = remainingRequiredSkills.get(requiredSkill);
        
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    public static void  main(String[] args)
    {
        
    }
}


