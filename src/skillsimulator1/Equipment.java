/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.SimpleDoubleProperty;
import skillsimulator1.Armor.Arm;
import skillsimulator1.Armor.Armor;
import skillsimulator1.Armor.Charm;
import skillsimulator1.Armor.Chest;
import skillsimulator1.Armor.Helm;
import skillsimulator1.Armor.Leg;
import skillsimulator1.Armor.Waist;
import static skillsimulator1.DataLoader.LoadArmData;
import static skillsimulator1.DataLoader.LoadCharmData;
import static skillsimulator1.DataLoader.LoadChestData;
import static skillsimulator1.DataLoader.LoadHelmData;
import static skillsimulator1.DataLoader.LoadLegData;
import static skillsimulator1.DataLoader.LoadWaistData;
import skillsimulator1.Decoration.Decoration;
import skillsimulator1.Skill.AttackSkill;
import skillsimulator1.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Equipment implements Comparable<Equipment>{
    private final Weapon weapon;
    protected final Helm helm;
    protected final Chest chest;
    protected final Arm arm;
    protected final Waist waist;
    protected final Leg leg;
    protected final Charm charm;
    private final List<Armor> armorList;
    private final List<Decoratable> decoratables;
    
    private int score;

    private final Map<Skill, Integer> skills;
    
    private Map<Decoration, Integer> decorations;
    protected final Map<Decoration, Integer> bestDecorations;
    private final Map<Decoration, Integer> bestRequiredDecorations;
    protected final SimpleDoubleProperty bestExpectation;
    protected Slot extraSlot;
    protected boolean calculated;
    
    public int counter = 0;
    public Equipment(Weapon weapon, Helm helm, Chest chest, Arm arm, Waist waist, Leg leg, Charm charm){
        this.weapon = weapon;
        this.helm = helm;
        this.chest = chest;
        this.arm = arm;
        this.waist = waist;
        this.leg = leg;
        this.charm = charm;
         
        armorList = Stream.of(helm, chest, arm, waist, leg, charm).toList();
        decoratables = new ArrayList(armorList);
        decoratables.add(weapon);
        
        calculated = false;
        skills = findSkillMap();
        decorations = new HashMap();
        bestDecorations = new HashMap<>();
        bestRequiredDecorations = new HashMap<>();
        bestExpectation = new SimpleDoubleProperty(0);
        updateScore();
    }

    public String getHelm() {
        return helm.getName();
    }

    public String getChest() {
        return chest.getName();
    }
    
    public String getArm() {
        return arm.getName();
    }

    public String getWaist() {
        return waist.getName();
    }

    public String getLeg() {
        return leg.getName();
    }

    public String getCharm() {
        return charm.getName();
    }
    
    public String getWeapon() {
        return weapon.getDamage() + "-" + weapon.getAffinity() + "-" + new Slot(weapon.getSlot4(), weapon.getSlot3(), weapon.getSlot2(), weapon.getSlot1());
    }
   
    public String getArmorRanking(Simulator sim)
    {
        return sim.getEquipmentScoreRankings( helm,  chest,  arm,  waist,  leg,  charm);
    }
    
    public SimpleDoubleProperty getBestExDoubleProperty()
    {
        return bestExpectation;
    }
    
    public void setBestExpectation(double value)
    {
        bestExpectation.set(value);
    }
    
    private Map<Skill, Integer> findSkillMap()
    {
         Map<Skill, Integer> skillMap = Stream.of(helm.getSkills(), chest.getSkills(), arm.getSkills(), waist.getSkills(), leg.getSkills(), charm.getSkills())
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.summingInt(Entry::getValue)));
        
         skillMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getMax() < entry.getValue())
                .forEach(entry-> entry.setValue(entry.getKey().getMax()));
         
        return skillMap;
    }
    
//    public Map<Decoration, Integer> getBestDecorationMap() {
//        return Stream
//                .of(bestDecorations, requiredSkills)
//                .flatMap(m -> m.entrySet().stream())
//                .collect(Collectors.groupingBy(Entry::getKey, Collectors.summingInt(Entry::getValue)));
//    }
    
    public Map<Skill, Integer> getSkillMap_NoDecoration()
    {
        return skills;
    }
    
    public Map<Skill, Integer> getSkillMap()
    {
//        if(calculated)
//            return skillsCombined;
        Map<Decoration, Integer>decorationsArranged = getBestDecorations();
        
        Map<Skill, Integer> decorationSkills = new HashMap();
        decorationsArranged.entrySet().stream().forEach(entry -> {
            Skill keySkill = entry.getKey().getSkill();
            if(decorationSkills.containsKey(keySkill))
            {
                decorationSkills.put(
                        keySkill, 
                        decorationSkills.get(keySkill) + entry.getKey().getLevel() * entry.getValue()) ;
            }
            else{
                decorationSkills.put(
                        keySkill, 
                        entry.getKey().getLevel() * entry.getValue()) ;
            }
        });
        
        return Stream.of(decorationSkills, skills)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.summingInt(Entry::getValue)));
//        return skillsCombined;
    }
    
    public Slot getExtraSlot()
    {
        return extraSlot;
    }
    
    public void updateScore()
    {
//        score = 0;
        score = armorList.stream().map(Armor::getScore).reduce(0, Integer::sum);
    }
    
    public static int findScoreSum(Helm helm, Chest chest, Arm arm, Waist waist, Leg leg, Charm charm)
    {
        return Stream.of(helm, chest, arm, waist, leg, charm).map(Armor::getScore).reduce(0, Integer::sum);
    }
    
    interface Function
    {
        public void function(Slot slot);
    }
    
    public void requiredSkillLooperWrapper(Map<Skill, Integer> requiredLevelMap, 
            int slot4, int slot3, int slot2, int slot1, Function function)
    {
        if(requiredLevelMap.isEmpty())
            function.function(new Slot(slot4, slot3, slot2, slot1));
        else
            requiredSkillLooper(requiredLevelMap, requiredLevelMap.keySet().stream().collect(Collectors.toList()), 0, new Slot(slot4, slot3, slot2, slot1), function);
    }
    
    private void requiredSkillLooper(
            Map<Skill, Integer> requiredLevelMap, List<Skill> requiredSkills, int index, 
            Slot slot, Function function)
    {
        Skill requiredSkill = requiredSkills.get(index);
        
        for(Map<Decoration, Integer> combMap : requiredSkill.getDecorationCombinationList(requiredLevelMap.get(requiredSkill)))
        {
            Slot requiredSlot = Skill.EvalSlot(combMap);
            Slot newSlot = slot.subtract(requiredSlot);
            if(newSlot != null)
            {
                decorations.putAll(combMap);
                if(index != requiredSkills.size() - 1)
                    requiredSkillLooper(requiredLevelMap, requiredSkills, index + 1, newSlot, function);
                else{
//                    bestDecorations.clear();
//                    bestDecorations.putAll(decorations);
                    function.function(newSlot);
                }
                decorations.keySet().removeIf(deco -> deco.getSkill() == requiredSkill);
            }
        }
    }
            
    public void skillLooperWrapper(Map<AttackSkill, Integer> skillSizeMap, Slot slot)
    {
        List<Decoration> slot4Decorations = new ArrayList();
        List<Decoration> slot3Decorations = new ArrayList();
        List<Decoration> slot2Decorations = new ArrayList();
        
        skillSizeMap.keySet().stream().forEach(skill -> {
            skill.getDecorations().stream().forEach(deco -> {
                switch(deco.getCost())
                {
                    case 2 -> slot2Decorations.add(deco);
                    case 3 -> slot3Decorations.add(deco);
                    case 4 -> slot4Decorations.add(deco);
                    default -> throw new IllegalArgumentException("Error ");
                }
            });
        });
        
        int remainingSlot2SkillSum = skillSizeMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getCost() == 2)
                .map(Entry::getValue)
                .reduce(0, Integer::sum);
        int slotSum = slot.getSlot2() + slot.getSlot3() + slot.getSlot4();
        skillLooper(new HashMap(), skillSizeMap, slot4Decorations, slot3Decorations, slot2Decorations, 4, 0, slot, remainingSlot2SkillSum, slotSum);
    }
    
    private void skillLooper(
            Map<Decoration, Integer>decorationMap, 
            Map<AttackSkill, Integer> skillSizeMap, 
//            Map<AttackSkill, Integer> skillSizeMap, 
            List<Decoration> slot4Decorations, 
            List<Decoration> slot3Decorations, 
            List<Decoration> slot2Decorations, 
            int slotSearching,
            int index,
            Slot slot,
            int skillSizeSum,
            int slotSum
    )
    {
        
        Decoration decoration;
        int remainingSlot; 
        boolean last = false;
        switch(slotSearching)
        {
            case 2 -> {
                if(slot2Decorations.isEmpty())
                {
                    System.out.println("Empty");
                    return;
                }
                    
                
                if(index == slot2Decorations.size() - 1)
                    last = true;
                decoration = slot2Decorations.get(index);
                remainingSlot = slot.getSlot2() + slot.getSlot3() + slot.getSlot4();
            }
            case 3 -> {
                if(slot3Decorations.isEmpty())
                {
                    skillLooper(decorationMap, skillSizeMap, slot4Decorations, slot3Decorations, slot2Decorations, 2, 0, slot, skillSizeSum, slotSum);
                    return;
                }
                if(index == slot3Decorations.size() - 1)
                    last = true;
                decoration = slot3Decorations.get(index);
                remainingSlot = slot.getSlot3() + slot.getSlot4();
            }
            case 4 -> {
                if(slot4Decorations.isEmpty())
                {
                    skillLooper(decorationMap, skillSizeMap, slot4Decorations, slot3Decorations, slot2Decorations, 3, 0, slot, skillSizeSum, slotSum);
                    return;
                }
                if(index == slot4Decorations.size() - 1)
                    last = true;
                decoration = slot4Decorations.get(index);
                remainingSlot = slot.getSlot4();
            }
            default -> throw new IllegalArgumentException("Error ");
        }
        AttackSkill skill = (AttackSkill)decoration.getSkill();
        int remainingSkillSize = skillSizeMap.get(skill);// - decorations.stream;
        int neededDecorationSize = remainingSkillSize / decoration.getLevel();
        
//        System.out.println("Slot searching -> " + slotSearching);
//        System.out.println("Skill -> " + skill);
//        System.out.println("見切り　-> " + skillSizeMap.get(Simulator.criticalEye));
        if(remainingSkillSize % decoration.getLevel() != 0)
            neededDecorationSize++;
        
//        System.out.println("neededDecorationSize -> " + neededDecorationSize);
//        System.out.println("decoration.getLevel() -> " + decoration.getLevel());
//        System.out.println("remainingSkillSize -> " + remainingSkillSize);
//        System.out.println(slot);
//        System.out.println(skillSizeMap);
        if(last)
        {
            if(slotSearching == 2)
            {
                int level = Math.min(neededDecorationSize, remainingSlot);
                decorationMap.put(decoration, level);
//                System.out.println("Put1 " + decoration + " -> " + level);
                skillSizeMap.put(
                        skill, 
                        Math.max(remainingSkillSize - decoration.getLevel() * level, 0));
//                System.out.println("Record1 " + decoration + " -> " + Math.max(remainingSkillSize - decoration.getLevel() * level, 0));
                // eval
                
                setBestDecoration(decorationMap, decorations, slot.subtract(0, 0, level, 0));
//                System.out.println("Remove1 " + decoration);
                decorationMap.remove(decoration);
                skillSizeMap.put(
                        skill, 
                        remainingSkillSize);
//                System.out.println("Restore1 " + skill + " -> " + remainingSkillSize);
                return;
            }
        }
        
        
        
        int start = 0;
        if(slotSearching == 2)
        {
            // Speed up mode
            
            if(slotSum >= skillSizeSum && slotSum != 0)
            {
//                System.out.println("-> Speeding");
                for(int i = index; i < slot2Decorations.size(); i++)
                {
                    Skill speedingSkill = slot2Decorations.get(i).getSkill();
                    decorationMap.put(slot2Decorations.get(i), skillSizeMap.get(speedingSkill));
//                    System.out.println("Put2 " + slot2Decorations.get(i) + " -> " + skillSizeMap.get(speedingSkill));
//                    skillSizeMap.put(
//                        (AttackSkill)speedingSkill, 
//                        0);
//                    System.out.println("Record2 " + speedingSkill + " -> " + 0);
                }
//                System.out.println("-> Speeding Ended");
                // Eval
                setBestDecoration(decorationMap, decorations, slot.subtract(0, 0, skillSizeSum, 0));
//                System.out.println("-> Restore Speeding");
                for(int i = index; i < slot2Decorations.size(); i++)
                {
                    Skill speedingSkill = slot2Decorations.get(i).getSkill();
                    int level = decorationMap.remove(slot2Decorations.get(i));
//                    System.out.println("Remove2 " + slot2Decorations.get(i));
//                    System.out.println("Restore2 " + speedingSkill + " -> " + (skillSizeMap.get(speedingSkill) + slot2Decorations.get(i).getLevel() * level));
//                    System.out.println("Restore2 (Level) -> " + level );
//                    System.out.println("Restore2 (skillSizeMap) -> " + skillSizeMap.get(speedingSkill) );
//                    skillSizeMap.put(
//                        (AttackSkill)speedingSkill, 
//                        skillSizeMap.get(speedingSkill) + slot2Decorations.get(i).getLevel() * level);
                }
//                System.out.println("-> Restore Speeding Ended");
                return;
            }
            if(slotSum >= skillSizeSum - remainingSkillSize)
            {
                start = Math.max(slotSum - skillSizeSum + remainingSkillSize, 0);
            }
        }
        int level = Math.min(neededDecorationSize, remainingSlot);
        for(int i = start; i <= level; i++)
        {
            Slot newSlot;
            int skillSizeSumDown = 0;
            switch(slotSearching)
            {
                case 2 -> {
                    newSlot = slot.subtract(0, 0, i, 0);
                    skillSizeSumDown = remainingSkillSize;
                }
                case 3 -> newSlot = slot.subtract(0, i, 0, 0);
                case 4 -> newSlot = slot.subtract(i, 0, 0, 0);
                default -> throw new IllegalArgumentException("Error ");
            }
            
            decorationMap.put(decoration, i);
//            System.out.println("Put3 " + decoration + " -> " + i);
            skillSizeMap.put(
                skill,
                Math.max(remainingSkillSize - decoration.getLevel() * i, 0));
//            System.out.println("Record3 " + decoration + " -> " + Math.max(remainingSkillSize - decoration.getLevel() * i, 0));
            if(last)
                skillLooper(decorationMap, skillSizeMap, slot4Decorations, slot3Decorations, slot2Decorations, slotSearching - 1, 0, newSlot, skillSizeSum, slotSum - i);
            else
                skillLooper(decorationMap, skillSizeMap, slot4Decorations, slot3Decorations, slot2Decorations, slotSearching, index + 1, newSlot, skillSizeSum - skillSizeSumDown, slotSum - i);
            decorationMap.remove(decoration);
//            System.out.println("Remove3 " + decoration);
            skillSizeMap.put(
                skill, 
                remainingSkillSize);
//            System.out.println("Restore3 " + skill + " -> " + remainingSkillSize);
        }
        
    }
    
    public boolean updateBestDecoration(Skill... activeSkills)
    {
        return updateBestDecoration(Arrays.asList(activeSkills));
    }
    
    public boolean updateBestDecoration()
    {
        return updateBestDecoration(Simulator.ALL_SKILLS);
    }
    
    public boolean updateBestDecoration(List<Skill> activeSkills)
    {
        if(Simulator.ALL_SERIESE_SKILLS
                .stream()
                .anyMatch(skill -> skill.getRequired() != 0 && skills.getOrDefault(skill, 0) < skill.getRequired()))
        {
            return false;
        }
        
        activeSkills = activeSkills
                .stream()
//                .filter(AttackSkill.class::isInstance)
                .filter(Skill::isActive)
                .toList();
        List<AttackSkill> attackSkills = activeSkills
                .stream()
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .toList();
        
        int availableSlots4 = decoratables.stream().map(deco -> deco.getSlot4()).reduce(0, (a, b) -> a + b);
        int availableSlots3 = decoratables.stream().map(deco -> deco.getSlot3()).reduce(0, (a, b) -> a + b);
        int availableSlots2 = decoratables.stream().map(deco -> deco.getSlot2()).reduce(0, (a, b) -> a + b);
        int availableSlots1 = decoratables.stream().map(deco -> deco.getSlot1()).reduce(0, (a, b) -> a + b);

//        System.out.println("availableSlots4 -> " + availableSlots4);
//        System.out.println("availableSlots3 -> " + availableSlots3);
//        System.out.println("availableSlots2 -> " + availableSlots2);
//        System.out.println("availableSlots1 -> " + availableSlots1);
        Map<Skill, Integer>requiredSkills = activeSkills
                .stream()
                .filter(skill -> skill.getRequired() - skills.getOrDefault(skill, 0) > 0)
                .collect(Collectors.toMap(
                        skill->skill, 
                        skill->skill.getRequired() - skills.getOrDefault(skill, 0))
                );
        
        Map<AttackSkill, Integer> remainingSkills = skills.keySet()
                .stream()
                .filter(Skill::isActive)
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .collect(
                        Collectors.toMap(
                                key->key, 
                                key -> key.getMax() - skills.get(key)
                        )
                );
        attackSkills
                .stream()
                .filter(skill -> !remainingSkills.containsKey(skill))
                .map(AttackSkill.class::cast)
                .forEach(skill -> remainingSkills.put(skill, skill.getMax()));
        
        requiredSkills
                .keySet()
                .stream()
                .filter(skill -> remainingSkills.containsKey(skill))
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .forEach(skill -> remainingSkills.put(skill, remainingSkills.get(skill) - requiredSkills.get(skill)));
        
        remainingSkills.values().removeIf(v -> v == 0);
        

        bestDecorations.clear();
        decorations.clear();
        bestExpectation.set(0);
        bestRequiredDecorations.clear();
        calculated = false;
        
        requiredSkillLooperWrapper(requiredSkills, 
            availableSlots4, availableSlots3, availableSlots2, availableSlots1, 
            slot -> {
//                System.out.println("Hello");
                skillLooperWrapper(remainingSkills, slot);
            }
        );
        
        if(!requiredSkills.isEmpty() && bestRequiredDecorations.isEmpty())
            return false;
//        System.out.println("Patterns -> " + counter);
        calculated = true;
        return true;
    }
    
    public Map<Decoration, Integer> getBestDecorations()
    {
        return Stream.of(bestDecorations, bestRequiredDecorations)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.summingInt(Entry::getValue)));
    }
    
    private void setBestDecoration(Map<Decoration, Integer> decorations, Map<Decoration, Integer> requireds, Slot extraSlot)
    {
        counter++;
        double exp = getExpectation(decorations);
        
//        System.out.println(exp);
//        System.out.println("------");
//        System.out.println("Exp -> " + exp);
//        for(Entry e : decorations.entrySet())
//        {
//            System.out.println(e.getKey() + " -> " + e.getValue());
//        }
//
//        System.out.println("Required -> ");
//        for(Entry e : requireds.entrySet())
//        {
//            System.out.println(e.getKey() + " -> " + e.getValue());
//        }
//        System.out.println("\n\n");
        if(exp > bestExpectation.get())
        {
            
                
            bestExpectation.set(exp);
            bestDecorations.clear();
            bestDecorations.putAll(decorations);
            bestRequiredDecorations.clear();
            bestRequiredDecorations.putAll(requireds);
            this.extraSlot = extraSlot;
        }
        
    }
    
    
    
//     private void skillLooper(
//            Map<AttackSkill, Integer> skillSizeMap, 
//            List<Skill> slot3Skills, 
//            List<Skill> slot2Skills, 
//            int slotSearching,
//            int skillIndex, 
//            int remainingSlot3Count,
//            int remainingSlot2Count,
//            int remainingSlot3SkillSum,
//            int remainingSlot2SkillSum)
//    {
//        List<Skill> skillKeys;
//        int remainingSlotCount;
//        
//        switch (slotSearching) {
//            case 2:
//                skillKeys = slot2Skills;
//                // if available skill is zero then finish
////                if(remainingSlot2SkillSum == 0)
////                    return;
//                
//                remainingSlotCount = remainingSlot3Count + remainingSlot2Count;
//                break;
//            case 3:
//                skillKeys = slot3Skills;
//                // if available skill is zero then go to search next slot
//                if(remainingSlot3SkillSum == 0)
//                {
//                    skillLooper(skillSizeMap, slot3Skills, slot2Skills, 2, 0, remainingSlot3Count, remainingSlot2Count, remainingSlot3SkillSum, remainingSlot2SkillSum);
//                    return;
//                }
//                remainingSlotCount = remainingSlot3Count;
//                
//                break;
//            default:
//                throw new RuntimeException("Error with choosing slot");
//        }
//        
//        Skill keySkill = skillKeys.get(skillIndex);
//        int skillSize = skillSizeMap.get(keySkill);
//        
//        
//        if(remainingSlotCount < 0)
//            throw new RuntimeException("Negative slot");
//        
//        // Reaching the end of skill list
//        if(skillIndex == skillKeys.size() - 1)
//        {
//            // Assign remaining slots
//            if(slotSearching == 2)
//            {
//                decorations.put(keySkill.get, Math.min(remainingSlotCount, skillSize));
//                double currentExp = getExpectation();
//
//                if(currentExp > bestExpectation.get())
//                {
//                    bestExpectation.set(currentExp);
//                    setBestDecoration(decorations);
//                }
//                return;
//            }
//            
//            
//        }
//        
//        int skillLoopStart = 0;
//        
//        // All of available slots are pushed to the end so apply all of remaining slot to the rest of skills
//        // Zero is not allowed.
//        if(slotSearching == 3)
//        {
//            if(remainingSlotCount > remainingSlot3SkillSum - skillSize)
//            {
//                if(remainingSlotCount > remainingSlot2SkillSum)
//                    throw new RuntimeException("Error detected");
//                int difference = remainingSlotCount - (remainingSlot3SkillSum - skillSize);
//                int acceptableOverflowInSlot2 = remainingSlot2SkillSum - remainingSlot2Count;
//                if(acceptableOverflowInSlot2 <= 0 )
//                    skillLoopStart = difference;
//                else if(difference - acceptableOverflowInSlot2 > skillSize)
//                    skillLoopStart = skillSize;
//                else
//                    if(difference - acceptableOverflowInSlot2 > 0)
//                        skillLoopStart = difference - acceptableOverflowInSlot2;
//            }
//        }
//        else if(slotSearching == 2)
//        {
//            if(remainingSlotCount > remainingSlot2SkillSum - skillSize)
//            {
//                // Max slot assignment
//                if(remainingSlotCount > remainingSlot2SkillSum)
//                    skillLoopStart = skillSize;
//                else{
//                    int difference = remainingSlotCount - (remainingSlot2SkillSum - skillSize);
//                    skillLoopStart = difference;
//                }
//            }
//        }
//        else{
//            throw new RuntimeException("Error");
//        }
//
//        
//        for(int i = skillLoopStart; i <= Math.min(remainingSlotCount, skillSize); i++)
//        {
//            decorations.put(keySkill, i);
//            if(slotSearching == 3)
//            {
//                // End of slot 3 skill list
//                // Go to slot 2
//                if(skillIndex == skillKeys.size() - 1)
//                    skillLooper(skillSizeMap, slot3Skills, slot2Skills, 2, 0, remainingSlot3Count - i, remainingSlot2Count, remainingSlot3SkillSum - skillSize, remainingSlot2SkillSum);
//                else
//                    skillLooper(skillSizeMap, slot3Skills, slot2Skills, 3, skillIndex + 1, remainingSlot3Count - i, remainingSlot2Count, remainingSlot3SkillSum - skillSize, remainingSlot2SkillSum);
//            }
//            else if(slotSearching == 2)
//                skillLooper(skillSizeMap, slot3Skills, slot2Skills, 2, skillIndex + 1, remainingSlot3Count, remainingSlot2Count - i, remainingSlot3SkillSum, remainingSlot2SkillSum - skillSize);
//        }
//    }
     
    
    
    public double getExpectation()
    {
        if(calculated)
            return bestExpectation.get();
        Expectation exp = new Expectation(weapon);
        
        Map<Skill, Integer> newSkills = getSkillMap();

        int availableSlots3 = decoratables.stream().map(deco -> deco.getSlot3()).reduce(0, (a, b) -> a + b);
        int availableSlots2 = decoratables.stream().map(deco -> deco.getSlot2()).reduce(0, (a, b) -> a + b);
        
        newSkills.keySet()
                .stream()
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .forEach(skill -> skill.evalExpectation(exp, newSkills.get(skill)));
        
        return exp.getExpectation();
    }
    
    public double getExpectation(Map<Decoration, Integer> decorations)
    {
        Expectation exp = new Expectation(weapon);
        
        Map<Skill, Integer> newSkills = new HashMap(skills);
        
        decorations.entrySet()
                .stream()
                .forEach(
                        entry -> {
                            newSkills.put(
                                entry.getKey().getSkill(), 
                                Math.min(
                                    newSkills.getOrDefault(entry.getKey().getSkill(), 0) + entry.getKey().getLevel() * decorations.get(entry.getKey()),
                                    entry.getKey().getSkill().getMax())
                                );
                        }
                );
        
        newSkills.keySet()
                .stream()
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .forEach(skill -> skill.evalExpectation(exp, newSkills.get(skill)));
        
        return exp.getExpectation();
    }
    
    @Override
    public int compareTo(Equipment o) {
        if(calculated)
            return (int)((getExpectation() - o.getExpectation()) * 100);
        return score - o.getScore();
    }

    public int getScore() {
        return score;
    }
    
            
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Armor]");
        for(Armor armor: armorList)
        {
            sb.append(armor.getName()).append("\t");
        }
        
        sb.append("\n[Skill]");
        Map<Skill, Integer> skillMap = getSkillMap();
        for(Skill skill: getSkillMap().keySet())
        {
            sb.append("\n\t")
                    .append(skill)
                    .append("->")
                    .append(skillMap.get(skill));
        }
        for(Armor a : armorList)
        {
            System.out.println(a);
        }
        
                
        return sb.toString();
    }
    
    public static void main(String[] args)
    {
        
//        List<Skill>skills = new ArrayList();
//
//        skills.add(attackBoost);
//        skills.add(peakPerformance);
//        skills.add(criticalEye);
//        skills.add(criticalBoost);
//        skills.add(weaknessExploit);
//        skills.add(maximumMight);
//        skills.add(agitator);
//        skills.add(criticalDraw);
//        skills.add(offensiveGuard);
//        
//        
        Weapon weapon = new Weapon("Sord", 200, 0);
//        Helm helm3 = new Helm("Helm3", 0, 0, 2, 1);
//        helm3.addSkill(maximumMight, 1);
//
//        Chest chest = new Chest("Chest", 0, 0, 1, 0);
//        chest.addSkill(criticalEye, 1);
//        chest.addSkill(criticalBoost, 1);
//        Arm arm = new Arm("arm", 0, 0, 0, 0);
//        arm.addSkill(attackBoost, 2);
//        Waist waist = new Waist("waist", 0, 0, 2, 0);
//        waist.addSkill(criticalEye, 1);
//        Leg leg = new Leg("leg", 0, 0, 0, 1);
//        leg.addSkill(peakPerformance, 1);
//        Charm charm = new Charm("charm", 0, 0, 1, 2);
//        charm.addSkill(criticalEye, 1);
//        
//        Equipment equipment = new Equipment(weapon, helm3, chest, arm, waist, leg, charm);
//        equipment.updateBestDecoration(skills);
//        double exp = equipment.getExpectation();
//        System.out.println(exp);
//        System.out.println(equipment);
        
        
//        Skill attack = new Skill("Attack", 2, 7);
//        Decoration attackJewel1 = new Decoration(attack, "AttackJewel1", 2, 1);
//        Decoration attackJewel2 = new Decoration(attack, "AttackJewel2", 4, 2);
//        
//        Skill defence = new Skill("Defence", 2, 7);
//        Decoration defenceJewel1 = new Decoration(defence, "DefenceJewel1", 1, 1);
//        Decoration defenceJewel2 = new Decoration(defence, "DefenceJewel2", 2, 2);
//        
//        Skill speed = new Skill("Speed", 2, 5);
//        Decoration speedJewel1 = new Decoration(speed, "SpeedJewel1", 2, 1);
//        Decoration speedJewel2 = new Decoration(speed, "SpeedJewel2", 3, 2);
//        
//        Helm helm = new Helm("Helm", 1, 0, 1, 0);
//        Chest chelm = new Chest("Chest", 0, 1, 0, 1);
//        Arm arm = new Arm("Arm", 0, 0, 2, 0);
//        Waist waist = new Waist("Waist", 0, 0, 1, 0);
//        Leg leg = new Leg("Leg", 1, 0, 0, 1);
//        Charm charm = new Charm("Charm", 0, 1, 0, 0);
//        
////        Map<Skill, Integer> requiredLevelMap = new HashMap();
////        requiredLevelMap.put(speed, 3);
////        requiredLevelMap.put(attack, 2);
////        requiredLevelMap.put(defence, 7);
//        speed.setRequired(3);
//        Equipment equipment = new Equipment(weapon, helm, chelm, arm, waist, leg, charm);
////        equipment.requiredSkillLooperWrapper(requiredLevelMap, 3, 2, 4, 1);;
//        
//        AttackSkill destroy = new DamageUpSkill("Destroy", 2, new int[] {1,2,3,4,5,6,7});
//        Decoration destroyJewel1 = new Decoration(destroy, "Destroy Jewel1", 2, 1);
//        Decoration destroyJewel2 = new Decoration(destroy, "Destroy Jewel2 [4]", 4, 2);
//        
//        AttackSkill acid = new DamageUpSkill("Acid", 2, new int[] {1,2,3, 4, 5});
//        Decoration acidJewel1 = new Decoration(acid, "Acid Jewel1", 2, 1);
//        Decoration acidJewel2 = new Decoration(acid, "Acid Jewel2 [3]", 3, 2);
//        
//        AttackSkill laser = new DamageUpSkill("Laser", 2, new int[] {1,2,3});
//        Decoration laserJewel1 = new Decoration(laser, "Laser Jewel1", 2, 1);
//        
//        Map<AttackSkill, Integer> attackSkillMap = new HashMap();
////        attackSkillMap.put(destroy, destroy.getMax());
////        attackSkillMap.put(acid, acid.getMax());
////        attackSkillMap.put(laser, laser.getMax());
//        equipment.updateBestDecoration(attack, speed, defence, destroy, acid, laser);
//        
//        
////        equipment.skillLooperWrapper(attackSkillMap, new Slot(4, 3, 3, 1));
////        System.out.println(equipment.counter);
////        
////        Map<Decoration, Integer> decorationMap = new HashMap();
//////        decorationMap.put(acidJewel1, 2);
//////        decorationMap.put(laserJewel1, 2);
//////        decorationMap.put(destroyJewel1, 4);
//////        System.out.println(equipment.getExpectation(decorationMap));
////        
//        System.out.println("-s-----");
//        for(Entry e : equipment.getBestDecorations().entrySet())
//        {
//            System.out.println(e.getKey() + " -> " + e.getValue());
//        }
//        System.out.println("\n\n");
//        
//        Skill attackBoost
//            = new DamageUpMultiplePreSkill("攻撃", 2, new int[]{3, 6, 9, 7, 8, 9, 10}, new double[]{1, 1, 1, 1.05, 1.06, 1.08, 1.1});
//        Decoration attackBoostJewel = new Decoration(attackBoost, "攻撃珠", 2, 1);
//        Skill criticalEye
//            = new AffinitySkill("見切り", 2, new int[]{5, 10, 15, 20, 25, 30, 40});
//        Decoration criticalEyeJewel = new Decoration(criticalEye, "達人珠", 2, 1);
//        Skill criticalBoost
//            = new AffinityMultiplierSkill("超会心", 2, new double[]{1.3, 1.35, 1.4});
//        Decoration criticalBoostJewel = new Decoration(criticalBoost, "超心珠", 2, 1);
//        Skill weaknessExploit
//            = new AffinitySkill("弱点特効", 2, new int[]{15, 30, 50});
//        Decoration weaknessExploitJewel = new Decoration(weaknessExploit, "痛撃珠", 2, 1);
//        Skill criticalDraw
//            = new AffinitySkill("抜刀術【技】", 3, new int[]{15, 30, 60});
//        Decoration criticalDrawJewel = new Decoration(criticalDraw, "抜刀珠", 2, 1);
//        Skill maximumMight
//            = new AffinitySkill("渾身", 2, new int[]{10, 20, 30});
//        Decoration maximumMightJewel = new Decoration(maximumMight, "渾身珠", 2, 1);
//        
//        Helm helm1 = new Helm("カイザー", 0, 0, 0, 1);
//        helm1.addSkill(criticalEye, 3);
//        helm1.addSkill(criticalBoost, 1);
//        Chest chest1 = new Chest("べリオ", 0, 1, 1, 1);
////        chest1.addSkill(maximumMight, 1);
//        Arm arm1 = new Arm("reusu", 0, 0, 1, 0);
//        arm1.addSkill(attackBoost, 2);
//        Waist waist1 = new Waist("カイザー", 0, 0, 1, 2);
//        waist1.addSkill(attackBoost, 2);
//        Leg leg1 = new Leg("ra-jann", 0, 0, 0, 2);
//        leg1.addSkill(criticalBoost, 2);
////        leg1.addSkill(criticalEye, 2);
//        Charm charm1 = new Charm("Charm", 0, 1, 0, 2);
//        charm1.addSkill(attackBoost, 3);
//        charm1.addSkill(weaknessExploit, 2);
//        
//        Equipment testE = new Equipment(weapon, helm1, chest1, arm1, waist1, leg1, charm1);
//        Simulator.ALL_ATTACK_SKILLS.stream().forEach(skill -> skill.setActive(false));
//        Stream.of(criticalEye, criticalBoost, maximumMight, weaknessExploit, attackBoost).forEach(skill -> skill.setActive(true));
//        testE.updateBestDecoration();
//        
//        System.out.println(testE.getExpectation());
//        System.out.println("-s-----");
//        for(Entry e : testE.getBestDecorations().entrySet())
//        {
//            System.out.println(e.getKey() + " -> " + e.getValue());
//        }
//        System.out.println("\n\n");
//        
//        Map<String, Integer> map = new HashMap<>();
// 
//        map.put("C++", 1980);
//        map.put("Java", 1995);
//        map.put("Ruby", 1995);
// 
//        map.keySet().removeIf(key -> key.equals("C++"));
//        System.out.println(map);

        List<Helm> helms = LoadHelmData("MHR_EQUIP_HEAD - 頭.tsv");
        List<Chest> chests = LoadChestData("MHR_EQUIP_BODY - 胴.tsv");
        List<Arm> arms = LoadArmData("MHR_EQUIP_ARM - 腕.tsv");
        List<Waist> waists = LoadWaistData("MHR_EQUIP_WST - 腰.tsv");
        List<Leg> legs = LoadLegData("MHR_EQUIP_LEG - 脚.tsv");
        List<Charm> charms = LoadCharmData("MHR_EQUIP_CHARM.tsv");
        
        Simulator.getAllAttackSkills().stream().forEach(skill -> skill.setActive(false));
        
        Stream.of(
                Simulator.attackBoost,
                Simulator.criticalEye,
                Simulator.criticalBoost,
                Simulator.maximumMight,
                Simulator.weaknessExploit,
                Simulator.agitator).forEach(skill -> skill.setActive(true));
        
        Stream.of(
                Simulator.speedSharping,
                Simulator.freeMeal
                ).forEach(skill -> skill.setRequired(3));
        
        helms.stream().forEach(Armor::updateScore);
        chests.stream().forEach(Armor::updateScore);
        arms.stream().forEach(Armor::updateScore);
        waists.stream().forEach(Armor::updateScore);
        legs.stream().forEach(Armor::updateScore);
        charms.stream().forEach(Armor::updateScore);
        
        Collections.sort(helms, Comparator.reverseOrder());
        Collections.sort(chests, Comparator.reverseOrder());
        Collections.sort(arms, Comparator.reverseOrder());
        Collections.sort(waists, Comparator.reverseOrder());
        Collections.sort(legs, Comparator.reverseOrder());
        Collections.sort(charms, Comparator.reverseOrder());
        
        Equipment equipment = new Equipment(weapon, helms.get(0), chests.get(3), arms.get(0), waists.get(0), legs.get(0), charms.get(0));
        
        equipment.updateBestDecoration();
        
    }

    
}
