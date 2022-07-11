/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1.Skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import skillsimulator1.Decoration.Decoration;
import skillsimulator1.Slot;

/**
 *
 * @author fes77
 */
public class Skill {
    private String name;
    protected int maxLevel;
    private int cost;
    protected int score;

    private SimpleIntegerProperty required;
    private BooleanProperty activeProperty;
    
    private List<Decoration> decorations;
    private Map<Integer, List<Map<Decoration, Integer>>> requiredLevDecMap; //requiredLevelPossibleDecorationCombinationMap
    
        
    public Skill(String name, int cost, int maxLevel) {
        if(maxLevel <= 0)
            throw new IllegalArgumentException("Skill max level should be more than 0");
        this.name = name;
        this.maxLevel = maxLevel;
        this.cost = cost;
 
        
        activeProperty = new SimpleBooleanProperty(true);
        required = new SimpleIntegerProperty(0);
        
        decorations = new ArrayList<>();
        
        requiredLevDecMap = new HashMap();
        updateScore();
        updateRequiredLevelMap();
    }
    
    public void setActive(boolean active)
    {
        activeProperty.set(active);
        updateScore();
    }
    
    public int getMax()
    {
        return maxLevel;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public int getCost()
    {
        if(activeProperty.get())
        {
            if(decorations.isEmpty())
                return 2;
            else
                return decorations.get(decorations.size() - 1).getCost();
        }
        else
            return 0;
    }

    public boolean isActive() {
        return activeProperty.get();
    }

    public int getRequired() {
        return required.get();
    }

    public void setRequired(int required) {
        if(required <= 0)
            this.required.set(0);
        else if(required >= maxLevel)
            this.required.set(maxLevel);
        else
            this.required.set(required);
        updateScore();
    }
    
    public int getScore()
    {
        return score;
    }

    public List<Decoration> getDecorations()
    {
        return decorations;
    }
    
    public BooleanProperty activeProperty()
    {
        return activeProperty;
    }
    
    public SimpleIntegerProperty requiredProperty()
    {
        return required;
    }
    
    public void updateScore() {
        score = (getRequired() > 0 ? 1 : 0) * getCost() * (getCost() >= 2 ? 2 : 1);
    }
    
    public Decoration getBiggestDecoration()
    {
        return decorations.get(0);
    }
    
    public void addDecoration(Decoration deco)
    {
        decorations.add(deco);
        Collections.sort(decorations, (Decoration d1, Decoration d2) -> {
           return  d2.getCost() - d1.getCost();
        });
        updateRequiredLevelMap();
    }

    public void updateRequiredLevelMap()
    {
        requiredLevDecMap.clear();
        IntStream.rangeClosed(1, maxLevel).forEach(x -> requiredLevDecMap.put(x, new ArrayList<>()));
        
        combinationLooperWrapper();
        for(Integer required : requiredLevDecMap.keySet())
        {
            System.out.println("Requred skill level -> " + required);
            for(Map<Decoration, Integer> map : requiredLevDecMap.get(required))
            {
                System.out.println("--");
                for(Entry<Decoration, Integer> entry : map.entrySet())
                {
                    System.out.println(entry.getKey() + " -> " + entry.getValue());
                }
            }
            System.out.println("\n\n");
        }
    }
    
    private boolean combinationLooperWrapper()
    {
        if(decorations.isEmpty())
            return false;
        for(int i = 1; i <= maxLevel; i++)
            combinationLooper(new HashMap(), i, 0, i);
        return true;
    }
    
    private void combinationLooper(Map<Decoration, Integer> decorationMap, int neededLevel, int decorationIndex, int requiredLevel)
    {   
        // reach end
        if(neededLevel == 0)
        {
            System.out.println("zero");
            Map<Decoration, Integer> map = new HashMap();
            map.putAll(decorationMap);
            map.values().removeIf(x -> x == 0);
            requiredLevDecMap.get(requiredLevel).add(map);
            return;
        }
        
        // eg) needed skill level is 3 but available jewel is 2
        // so you may want to apply 2 * 2 jewels makes 4, which is over 3
        if(neededLevel % decorations.get(decorationIndex).getLevel() != 0)
        {
            decorationMap.put(decorations.get(decorationIndex), neededLevel / decorations.get(decorationIndex).getLevel() + 1);
            Map<Decoration, Integer> map = new HashMap();
            map.putAll(decorationMap);
            map.values().removeIf(x -> x == 0);
            requiredLevDecMap.get(requiredLevel).add(map);
        }
        
        // Last decoration available
        if(decorationIndex != decorations.size() - 1)
            {for(int i = 0; i <= neededLevel / decorations.get(decorationIndex).getLevel(); i++)
            {
                decorationMap.put(decorations.get(decorationIndex), i);
                combinationLooper(decorationMap, neededLevel - i * decorations.get(decorationIndex).getLevel(), decorationIndex + 1, requiredLevel);
            }
        }
        // Not the last decoration
        else{
            if(neededLevel % decorations.get(decorationIndex).getLevel() == 0)
            {
                decorationMap.put(decorations.get(decorationIndex), neededLevel / decorations.get(decorationIndex).getLevel());
                Map<Decoration, Integer> map = new HashMap();
                map.putAll(decorationMap);
                map.values().removeIf(x -> x == 0);
                requiredLevDecMap.get(requiredLevel).add(map);
            }
        }
        decorationMap.put(decorations.get(decorationIndex), 0);
    }
    
    public List<Map<Decoration, Integer>> getDecorationCombinationList(int requiredLevel)
    {
        return requiredLevDecMap.get(requiredLevel);
    }
    
    public static Slot EvalSlot(Map<Decoration, Integer> decorationMap)
    {
        int slot4 = 0, slot3 = 0, slot2 = 0, slot1 = 0;
        
        for(Entry<Decoration, Integer> e : decorationMap.entrySet())
        {
            switch(e.getKey().getCost())
            {
                case 4 -> slot4 += e.getValue();
                case 3 -> slot3 += e.getValue();
                case 2 -> slot2 += e.getValue();
                case 1 -> slot1 += e.getValue();
                default -> throw new IllegalArgumentException("Invalid cases here");
            }
        }
        return new Slot(slot4, slot3, slot2, slot1);
    }
    
    public static void main(String[] args)
    {
        System.out.println("Hello");
        Skill skill = new Skill("Attack", 2, 7);
        
        Decoration deco1 = new Decoration(skill, "Jewel1", 2, 1);
        Decoration deco2 = new Decoration(skill, "Jewel2", 3, 2);
        Decoration deco3 = new Decoration(skill, "Jewel3", 4, 3);
        
        
        skill.updateRequiredLevelMap();
    }
    
}
