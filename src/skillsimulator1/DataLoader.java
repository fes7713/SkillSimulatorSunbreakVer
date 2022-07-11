/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import skillsimulator1.Armor.Arm;
import skillsimulator1.Armor.Armor;
import skillsimulator1.Armor.Charm;
import skillsimulator1.Armor.Chest;
import skillsimulator1.Armor.Helm;
import skillsimulator1.Armor.Leg;
import skillsimulator1.Armor.UnknownArmor;
import skillsimulator1.Armor.Waist;
import skillsimulator1.Decoration.Decoration;
import skillsimulator1.Skill.Skill;
import skillsimulator1.Skill.UnknownSkill;


/**
 *
 * @author fes77
 */
public class DataLoader {
    private static final int NAME_INDEX = 0;
    private static final int SLOT1_INDEX = 3;
    private static final int SLOT2_INDEX = 4;
    private static final int SLOT3_INDEX = 5;
    private static final int SKILL1_INDEX = 14;
    private static final int SKILL1_SLOT_INDEX = 15;
    private static final int SKILL2_INDEX = 16;
    private static final int SKILL2_SLOT_INDEX = 17;
    private static final int SKILL3_INDEX = 18;
    private static final int SKILL3_SLOT_INDEX = 19;;
    
    final static Map<String, Skill> skillMapper = INIT_SKILL_MAPPER();
     
    private static Map<String, Skill> INIT_SKILL_MAPPER()
    {
        Map<String, Skill> map = new HashMap<>();
        for(Skill skill: Simulator.ALL_SKILLS)
        {
            map.put(skill.getName(), skill);
        }
        
        return map;
    }
    
    public static List<Helm> LoadHelmData(String f_name)
    {
        List<Helm> helms = new ArrayList<>();
        for(String row : loadTable(f_name))
            helms.add(processRow(row).convHelm());

        return helms;
    }
    
    public static List<Chest> LoadChestData(String f_name)
    {
        List<Chest> chests = new ArrayList<>();
        for(String row : loadTable(f_name))
            chests.add(processRow(row).convChest());

        return chests;
    }
    
    public static List<Arm> LoadArmData(String f_name)
    {
        List<Arm> arms = new ArrayList<>();
        for(String row : loadTable(f_name))
            arms.add(processRow(row).convArm());

        return arms;
    }
    
    public static List<Waist> LoadWaistData(String f_name)
    {
        List<Waist> waists = new ArrayList<>();
        for(String row : loadTable(f_name))
            waists.add(processRow(row).convWaist());

        return waists;
    }
    
    public static List<Leg> LoadLegData(String f_name)
    {
        List<Leg> legs = new ArrayList<>();
        for(String row : loadTable(f_name))
            legs.add(processRow(row).convLeg());

        return legs;
    }
    
    public static List<Charm> LoadCharmData(String f_name)
    {
        List<Charm> charms = new ArrayList<>();
        for(String row : loadTable(f_name))
            charms.add(processRow(row).convCharm());

        return charms;
    }
    
    private static List<String> loadTable(String f_name)
    {
        List<String> lines = null;
        try {
            InputStream inputStream = new FileInputStream("data/" + f_name);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            Stream<String> streamOfString= new BufferedReader(inputStreamReader).lines();
            lines = streamOfString.collect(Collectors.toList());
            lines.remove(0);
            
        } catch (IOException e) {

            e.printStackTrace();
        }
        if(lines == null)
            throw new RuntimeException("Error in loading");
        return lines;
    }
    private static UnknownArmor  processRow(String row)
    {
        String[] seraparted = row.strip().split("\t");
        if(seraparted.length < 18)
            System.out.println(row);
        
//        System.out.println(seraparted[SKILL1_INDEX]);
        
        Map<Skill, Integer> skillMap = new HashMap();
        addArmorSkillWrapper(skillMap, seraparted);
        int slot1 = Integer.parseInt(seraparted[SLOT1_INDEX]);
        int slot2 = Integer.parseInt(seraparted[SLOT2_INDEX]);
        int slot3 = Integer.parseInt(seraparted[SLOT3_INDEX]);
        
        
        
        UnknownArmor armor = new UnknownArmor(
                seraparted[NAME_INDEX], 
                skillMap, 
                (int)Stream.of(slot1, slot2, slot3).filter(x -> x == 4).count(),
                (int)Stream.of(slot1, slot2, slot3).filter(x -> x == 3).count(),
                (int)Stream.of(slot1, slot2, slot3).filter(x -> x == 2).count(),
                (int)Stream.of(slot1, slot2, slot3).filter(x -> x == 1).count()
        );
        return armor;
    }
    
    private static void addArmorSkillWrapper(Map<Skill, Integer> skillMap, String[] seraparted)
    {
        addArmorSkill(skillMap, seraparted, SKILL1_INDEX, 0);
    }
    
    private static void addArmorSkill(Map<Skill, Integer> skillMap, String[] seraparted, int index, int maxDepth)
    {
        if(maxDepth >= 5)
            return;
        if(seraparted.length <= index)
            return;
            
        if(seraparted[index] != "")
        {
            int level = Integer.parseInt(seraparted[index + 1]);
            if(!skillMapper.containsKey(seraparted[index]))
            {
                Skill newSkill = new UnknownSkill(seraparted[index], 1);
                skillMapper.put(seraparted[index], newSkill);
                System.out.println("New Skill Created : " + newSkill);
            }
            skillMap.put(
                        skillMapper.get(seraparted[index]), 
                        level
                );
        }
        addArmorSkill(skillMap, seraparted, index + 2, maxDepth + 1);
    }
    
    public static String outputRow(Armor armor)
    {
        StringBuilder sb = new StringBuilder();
        
        int counter = 0;
        sb.append(armor.getName());
        IntStream.range(NAME_INDEX, SLOT1_INDEX).forEach(x -> sb.append("\t"));
        
        List<Integer> numSlots = new ArrayList();
        IntStream.range(0, armor.getSlot4()).forEach(x -> sb.append(4).append("\t"));
        IntStream.range(0, armor.getSlot3()).forEach(x -> sb.append(3).append("\t"));
        IntStream.range(0, armor.getSlot2()).forEach(x -> sb.append(2).append("\t"));
        IntStream.range(0, armor.getSlot1()).forEach(x -> sb.append(1).append("\t"));
        IntStream.range(0, 3 - armor.getSlot4() - armor.getSlot3() - armor.getSlot2() - armor.getSlot1()).forEach(x -> sb.append(0).append("\t"));
        
        
        IntStream.range(
                SLOT3_INDEX + 1, 
                SKILL1_INDEX).forEach(x -> sb.append("\t"));
        
        for(Entry entry: armor.getSkills().entrySet())
        {
            sb.append(entry.getKey());
            sb.append("\t");
            sb.append(entry.getValue());
            sb.append("\t");
        }
        
        return sb.toString();
    }
    
    public static void appendRow(String f_name, String data)
    {
        try {
            File fp = new File(f_name);
            if (!fp.exists()){
                System.out.println("File does not exist");
            }
            FileWriter file = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));
            
            //ファイルに追記する
            pw.println(data);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void createFile(String f_name)
    {
        try {
            File fp = new File(f_name);
            if (!fp.exists()){
                System.out.println("File does not exist");
            }
            String header = "#名前	性別(0=両,1=男,2=女)	レア度	スロット1	スロット2	スロット3	入手時期	初期防御力	最終防御力	火耐性	水耐性	雷耐性	氷耐性	龍耐性	スキル系統1	スキル値1	スキル系統2	スキル値2	スキル系統3	スキル値3	スキル系統4	スキル値4	スキル系統5	スキル値5	生産素材1	個数	生産素材2	個数	生産素材3	個数	生産素材4	個数	カスタム強化防御力	カスタム強化素材1	個数1	カスタム強化素材2	個数2	ワンセット防具	仮番号";
            FileWriter file = new FileWriter(fp);
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));
            
            //ファイルに追記する
            pw.println(header);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveMyset(String title, Equipment equi, double exp)
    {
        // title \t\t // damage t\t weapon \t\t equipment \t\t decoration \t\t required
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append("\t\t");
        sb.append(exp);
        sb.append("\t\t");
        sb.append(equi.getWeapon());
        sb.append("\t\t");
        Stream.of(equi.getHelm(), equi.getChest(), equi.getArm(), equi.getWaist(), equi.getLeg(), equi.getCharm())
                .forEach(str -> 
                    {
                        sb.append(str);
                        sb.append("\t");
                    }    
                );
        sb.append("\t");
        Stream.of(equi.getExtraSlot().getSlot4(), equi.getExtraSlot().getSlot3(), equi.getExtraSlot().getSlot2(), equi.getExtraSlot().getSlot1())
                .forEach(str -> 
                    {
                        sb.append(str);
                        sb.append("\t");
                    }
                );
        sb.append("\t");
        equi.getBestDecorations().entrySet()
                .stream()
                .forEach(entry -> {
                    sb.append(entry.getKey());
                    sb.append("\t");
                    sb.append(entry.getValue());
                    sb.append("\t");
                });
        
        appendRow("data/MHR_EQUIP_MYSET.tsv", sb.toString());
    }
    
    public static List<Myset> loadMyset(Simulator sim)
    {
        List<Myset> mysets = new ArrayList<>();
        
        List<String> lines = loadTable("MHR_EQUIP_MYSET.tsv");
        lines.stream().forEach(row -> {
            String[] separated = row.split("\t\t");
            String title = separated[0];
            double exp = Double.parseDouble(separated[1]);
            String weapon = separated[2];
            System.out.println("Equipment");
            
            System.out.println(separated[3]);
            String[] equipments = separated[3].split("\t");
            Helm helm = sim.findHelmByName(equipments[0]);
            Chest chest = sim.findChestByName(equipments[1]);
            Arm arm = sim.findArmByName(equipments[2]);
            Waist waist = sim.findWaistByName(equipments[3]);
            Leg leg = sim.findLegByName(equipments[4]);
            Charm charm = sim.findCharmByName(equipments[5]);
            
            String[] extraSlotSeparated = separated[4].split("\t");
            Slot extraSlot = new Slot(
                    Integer.parseInt(extraSlotSeparated[0]), 
                    Integer.parseInt(extraSlotSeparated[1]), 
                    Integer.parseInt(extraSlotSeparated[2]), 
                    Integer.parseInt(extraSlotSeparated[3]));
            
            String[] decorationSeparated = separated[5].split("\t");
            
            Map<Decoration, Integer> decorations = new HashMap<>();
            
            for(int i = 0; i < decorationSeparated.length / 2; i++)
            {
                Decoration deco = sim.findDecorationByName(decorationSeparated[i * 2]);
                if(deco == null)
                {
                    System.out.println("Unknown decoration -> " + decorationSeparated[i * 2]);
                    continue;
                }
                decorations.put(deco, Integer.parseInt(decorationSeparated[i * 2 + 1]));
            }
            
            mysets.add(new Myset(title, exp, weapon, helm, chest, arm, waist, leg, charm, extraSlot, decorations));
            
            
        });
        return mysets;
    }
    
    public static void main(String[] args)
    {
        List<Helm> helms = LoadHelmData("MHR_EQUIP_HEAD - 頭.tsv");
        System.out.println();
        Armor a = processRow(outputRow(helms.get(0)));
        System.out.println(a);
//        List<Chest> chests = LoadChestData("MHR_EQUIP_BODY - 胴.tsv");
//        List<Arm> arms = LoadArmData("MHR_EQUIP_ARM - 腕.tsv");
//        List<Waist> waists = LoadWaistData("MHR_EQUIP_WST - 腰.tsv");
//        List<Leg> legs = LoadLegData("MHR_EQUIP_HEAD - 頭.tsv");
//        List<Charm> charms = LoadCharmData("MHR_EQUIP_LEG - 脚.tsv");
        
//        System.out.println(outputRow(helms.get(0)));
//        Armor a = processRow(outputRow(helms.get(0)));
//        System.out.println(a);
//        appendRow("data/MHR_EQUIP_CHARM.tsv", outputRow(helms.get(0)));
//        
//        List<Charm> charms = LoadCharmData("MHR_EQUIP_CHARM.tsv");
//        System.out.println(charms);
//        createFile("data/MHR_EQUIP_CHARM.tsv");
    }
}
