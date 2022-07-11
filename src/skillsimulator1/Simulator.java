/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import skillsimulator1.Armor.Arm;
import skillsimulator1.Armor.Armor;
import skillsimulator1.Armor.Charm;
import skillsimulator1.Armor.Chest;
import skillsimulator1.Armor.Helm;
import skillsimulator1.Armor.Leg;
import skillsimulator1.Armor.Waist;
import skillsimulator1.Decoration.Decoration;
import skillsimulator1.Skill.AffinityMultiplierSkill;
import skillsimulator1.Skill.AffinitySkill;
import skillsimulator1.Skill.AttackSkill;
import skillsimulator1.Skill.DamageAffinityUpSkill;
import skillsimulator1.Skill.DamageMultiplierSkill;
import skillsimulator1.Skill.DamageUpMultiplePreSkill;
import skillsimulator1.Skill.DamageUpSkill;
import skillsimulator1.Skill.SeriesSkill;
import skillsimulator1.Skill.Skill;
import skillsimulator1.Skill.UnknownSkill;


/**
 *
 * @author fes77
 */
public class Simulator {
    boolean active;
    List<Helm> helms;
    List<Chest> chests;
    List<Arm> arms;
    List<Waist> waists;
    List<Leg> legs;
    List<Charm> charms;
    
    List<Helm> selectedHelms;
    List<Chest> selectedChests;
    List<Arm> selectedArms;
    List<Waist> selectedWaists;
    List<Leg> selectedLegs;
    List<Charm> selectedCharms;
    
    List<Equipment> equipments;
    DoubleProperty progress;
    DoubleProperty skipRate;
    IntegerProperty skipValue;
    IntegerProperty cutValue;
    
    double totalExpectation;
    double totalScore;
    int chartCounter;
    
    ObservableList<Data<Integer, Double>> bestExpectationData;
    ObservableList<Data<Integer, Double>> worstExpectationData;
    ObservableList<Data<Integer, Double>> averageExpectationData;
    
    ObservableList<Data<Integer, Double>> bestScoreData;
    ObservableList<Data<Integer, Double>> worstScoreData;
    ObservableList<Data<Integer, Double>> averageScoreData;
    
    
    public static final Skill emptySkill
            = new UnknownSkill("", 10);
    public static final Skill attackBoost
            = new DamageUpMultiplePreSkill("攻撃", 2, new int[]{3, 6, 9, 7, 8, 9, 10}, new double[]{1, 1, 1, 1.05, 1.06, 1.08, 1.1});
    public static final Decoration attackJewel2
            = new Decoration(attackBoost, "攻撃珠【2】", 2, 1);
    
    public static final Skill peakPerformance
            = new DamageUpSkill("フルチャージ", 2, new int[]{5, 10, 20});
    public static final Decoration flawlessJewel2
            = new Decoration(peakPerformance, "無傷珠【2】", 2, 1);
    
    public static final Skill criticalEye
            = new AffinitySkill("見切り", 2, new int[]{5, 10, 15, 20, 25, 30, 40});
    public static final Decoration expertJewel2
            = new Decoration(criticalEye, "達人珠【2】", 2, 1);
    
    public static final Skill criticalBoost
            = new AffinityMultiplierSkill("超会心", 2, new double[]{1.3, 1.35, 1.4});
    public static final Decoration criticalJewel2
            = new Decoration(criticalBoost, "超心珠【2】", 2, 1);
    
    public static final Skill weaknessExploit
            = new AffinitySkill("弱点特効", 2, new int[]{15, 30, 50});
    public static final Decoration tenderizerJewel2
            = new Decoration(weaknessExploit, "痛撃珠【2】", 2, 1);
    
    public static final Skill criticalDraw
            = new AffinitySkill("抜刀術【技】", 3, new int[]{15, 30, 60});
    public static final Decoration drawJewel3
            = new Decoration(criticalDraw, "抜刀珠【3】", 3, 1);
    public static final Decoration drawJewel4
            = new Decoration(criticalDraw, "抜刀珠Ⅱ【4】", 4, 2);
    
    public static final Skill maximumMight
            = new AffinitySkill("渾身", 2, new int[]{10, 20, 30});
    public static final Decoration mightyJewel2
            = new Decoration(maximumMight, "渾身珠【2】", 2, 1);
    
    public static final Skill agitator
            = new DamageAffinityUpSkill("挑戦者", 2, new int[]{4, 8, 12, 16, 20}, new int[]{3, 5, 7, 10, 15});
    public static final Decoration challengerJewel2
            = new Decoration(agitator, "挑戦珠【2】", 2, 1);
    
    public static final Skill counterstrike
            = new DamageUpSkill("逆襲", 2, new int[]{10, 15, 25});
    public static final Decoration counterJewel2
            = new Decoration(counterstrike, "逆襲珠【2】", 2, 1);
    
    public static final Skill punishingDraw
            = new DamageUpSkill("抜刀術【力】", 2, new int[]{3, 5, 7});
    public static final Decoration gambitJewel2
            = new Decoration(punishingDraw, "抜打珠【2】", 2, 1);
    public static final Decoration gambitJewel4
            = new Decoration(punishingDraw, "抜打珠Ⅱ【4】", 4, 2);
    
    public static final Skill offensiveGuard
            = new DamageMultiplierSkill("攻めの守勢", 3, new double[]{1.05, 1.1, 1.15});
    public static final Decoration guardianJewel3
            = new Decoration(offensiveGuard, "守勢珠【3】", 3, 1);
    
    public static final Skill heroics
            = new DamageMultiplierSkill("火事場力", 2, new double[]{1, 1.05, 1.05, 1.1, 1.3});
    public static final Decoration potentialJewel2
            = new Decoration(heroics, "底力珠【2】", 2, 1);
    
    public static final Skill latentPower
            = new AffinitySkill("力の解放", 2, new int[]{10, 20, 30, 40, 50});
    public static final Decoration throttleJewel2
            = new Decoration(latentPower, "全開珠【2】", 2, 1);
    
    public static final Skill resentment
            = new DamageUpSkill("逆恨み", 2, new int[]{5, 10, 15, 20, 25});
    public static final Decoration furorJewel2
            = new Decoration(resentment, "逆上珠【2】", 2, 1);
    
    public static final Skill resuscitate
            = new DamageUpSkill("死中に活", 2, new int[]{5, 10, 20});
    public static final Decoration crisisJewel2
            = new Decoration(resuscitate, "窮地珠【2】", 2, 1);
    
    public static final Skill masterTouch
            = new Skill("達人芸", 2, 3);
    public static final Decoration masteryJewel2
            = new Decoration(masterTouch, "達芸珠【2】", 2, 1);
    
    public static final Skill handicraft
            = new Skill("匠", 3, 5);
    public static final Decoration handicraftJewel3
            = new Decoration(handicraft, "匠珠【3】", 3, 1);
    
    public static final Skill razorSharp
            = new Skill("業物", 2, 3);
    public static final Decoration razorJewel2
            = new Decoration(razorSharp, "斬鉄珠【2】", 2, 1);
    
    public static final Skill speedSharping
            = new Skill("砥石使用高速化", 1, 3);
    public static final Decoration hardGrinderJewel1
            = new Decoration(speedSharping, "研磨珠【1】", 1, 1);
    public static final Decoration hardGrinderJewel4
            = new Decoration(speedSharping, "研磨珠Ⅲ【4】", 4, 3);
    
    public static final Skill protectivePolish
            = new Skill("剛刃研磨", 2, 3);
    public static final Decoration sharpJewel2
            = new Decoration(protectivePolish, "剛刃珠【2】", 2, 1);
    
    public static final Skill NormalRapidUp
            = new Skill("通常弾・連射矢強化", 3, 3);
    public static final Decoration forceshotJewel3
            = new Decoration(NormalRapidUp, "強弾珠【3】", 3, 1);
    public static final Skill pierceUp
            = new Skill("貫通弾・貫通矢強化", 3, 3);
    public static final Decoration pierceJewel3
            = new Decoration(pierceUp, "貫通珠【3】", 3, 1);
    public static final Skill spreadUp
            = new Skill("散弾・拡散矢強化", 3, 3);
    public static final Decoration spreadJewel3
            = new Decoration(spreadUp, "散弾珠【3】", 3, 1);
    
    public static final Skill rapidFireUp
            = new Skill("速射強化", 3, 3);
    public static final Decoration salvoJewel3
            = new Decoration(rapidFireUp, "速射珠【3】", 3, 1);
    
    public static final Skill recoilDown
            = new Skill("反動軽減", 1, 3);
    public static final Decoration absorberJewel1
            = new Decoration(recoilDown, "抑反珠【1】", 1, 1);
    
    public static final Skill reloadSpeed
            = new Skill("装填速度", 1, 3);
    public static final Decoration quickloadJewel1
            = new Decoration(reloadSpeed, "早填珠【1】", 1, 1);
    
    public static final Skill spareShot
            = new Skill("弾丸節約", 2, 3);
    public static final Decoration thriftJewel2
            = new Decoration(spareShot, "節弾珠【2】", 2, 1);
    
    public static final Skill quickSheath
            = new Skill("納刀術", 2, 3);
    public static final Decoration sheathJewel2
            = new Decoration(quickSheath, "納刀珠【2】", 2, 1);
    
    public static final Skill flinchFree
            = new Skill("ひるみ軽減", 1, 3);
    public static final Decoration braceJewel1
            = new Decoration(flinchFree, "耐衝珠【1】", 1, 1);
    public static final Decoration braceJewel4
            = new Decoration(flinchFree, "耐衝珠Ⅲ【4】", 4, 3);
    
    public static final Skill stunResistance
            = new Skill("気絶耐性", 1, 3);
    public static final Decoration steadfastJewel1
            = new Decoration(stunResistance, "耐絶珠【1】", 1, 1);
    
    public static final Skill freeMeal
            = new Skill("満足感", 1, 3);
    public static final Decoration satiatedJewel1
            = new Decoration(freeMeal, "節食珠【1】", 1, 1);
    public static final Decoration satiatedJewel4
            = new Decoration(freeMeal, "節食珠Ⅲ【4】", 4, 3);
    
    public static final Skill mushroomancer
            = new Skill("キノコ大好き", 3, 3);
    public static final Decoration fungiformJewel3
            = new Decoration(mushroomancer, "茸好珠【3】", 3, 1);
    public static final Decoration fungiformJewel4
            = new Decoration(mushroomancer, "茸好珠Ⅱ【4】", 4, 2);
    
    public static final Skill wideRange
            = new Skill("広域化", 2, 5);
    public static final Decoration friendshipJewel2
            = new Decoration(wideRange, "友愛珠【2】", 2, 1);
    public static final Decoration friendshipJewel3
            = new Decoration(wideRange, "友愛珠Ⅱ【3】", 3, 3);
    public static final Decoration friendshipJewel4
            = new Decoration(wideRange, "友愛珠Ⅲ【4】", 4, 4);
    
    public static final Skill ammoUp
            = new Skill("装填拡張", 3, 3);
    public static final Decoration capacityJewel3
            = new Decoration(ammoUp, "装填珠【3】", 3, 1);
    
    public static final Skill slugger
            = new Skill("ＫＯ術", 2, 3);
    public static final Decoration KOJewel2
            = new Decoration(slugger, "ＫＯ珠【2】", 2, 1);
    public static final Decoration KOJewel4
            = new Decoration(slugger, "ＫＯ珠Ⅱ【4】", 4, 2);
    
    public static final Skill defenseBoost
            = new Skill("防御", 1, 5);
    public static final Decoration defenseJewel1
            = new Decoration(defenseBoost, "防御珠【1】", 1, 1);
    public static final Decoration defenseJewel2
            = new Decoration(defenseBoost, "防御珠Ⅱ【2】", 2, 2);
    public static final Decoration defenseJewel3
            = new Decoration(defenseBoost, "防御珠Ⅲ【3】", 3, 3);
    public static final Decoration defenseJewel4
            = new Decoration(defenseBoost, "防御珠Ⅴ【4】", 4, 5);
    
    public static final Skill criticalElement
            = new Skill("会心撃【属性】", 2, 3);
    public static final Decoration critElementJewel2
            = new Decoration(criticalElement, "属会珠【2】", 2, 1);
    public static final Decoration critElementJewel4
            = new Decoration(criticalElement, "属会珠Ⅱ【4】", 4, 2);
    
    public static final Skill fireAttack
            = new Skill("火属性攻撃強化", 1, 5);
    public static final Decoration blazeJewel1
            = new Decoration(fireAttack, "火炎珠【1】", 1, 1);
    public static final Decoration blazeJewel2
            = new Decoration(fireAttack, "火炎珠Ⅱ【2】", 2, 2);
    public static final Decoration blazeJewel3
            = new Decoration(fireAttack, "火炎珠Ⅲ【2】", 3, 3);
    
    public static final Skill thunderAttack
            = new Skill("雷属性攻撃強化", 1, 5);
    public static final Decoration boltJewel1
            = new Decoration(thunderAttack, "雷光珠【1】", 1, 1);
    public static final Decoration boltJewel2
            = new Decoration(thunderAttack, "雷光珠Ⅱ【2】", 2, 2);
    public static final Decoration boltJewel3
            = new Decoration(thunderAttack, "雷光珠Ⅲ【3】", 3, 3);
    
    public static final Skill waterAttack
            = new Skill("水属性攻撃強化", 1, 5);
    public static final Decoration streamJewel1
            = new Decoration(waterAttack, "流水珠【1】", 1, 1);
    public static final Decoration streamJewel2
            = new Decoration(waterAttack, "流水珠Ⅱ【2】", 2, 2);
    public static final Decoration streamJewel3
            = new Decoration(waterAttack, "流水珠Ⅲ【3】", 3, 3);
    
    public static final Skill iceAttack
            = new Skill("氷属性攻撃強化", 1, 5);
    public static final Decoration frostJewel1
            = new Decoration(iceAttack, "氷結珠【1】", 1, 1);
    public static final Decoration frostJewel2
            = new Decoration(iceAttack, "氷結珠Ⅱ【2】", 2, 2);
    public static final Decoration frostJewel3
            = new Decoration(iceAttack, "氷結珠Ⅲ【3】", 3, 3);
    
    public static final Skill dragonAttack
            = new Skill("龍属性攻撃強化", 1, 5);
    public static final Decoration dragonJewel1
            = new Decoration(dragonAttack, "破龍珠【1】", 1, 1);
    public static final Decoration dragonJewel2
            = new Decoration(dragonAttack, "破龍珠Ⅱ【2】", 2, 2);
    public static final Decoration dragonJewel3
            = new Decoration(dragonAttack, "破龍珠Ⅲ【3】", 3, 3);
    
    public static final Skill blastAttack
            = new Skill("爆破属性強化", 1, 5);
    public static final Decoration blastJewel2
            = new Decoration(blastAttack, "爆破珠【1】", 1, 1);
    
    public static final Skill paralysisAttack
            = new Skill("麻痺属性強化", 2, 5);
    public static final Decoration paralyzerJewel2
            = new Decoration(paralysisAttack, "麻痺珠【2】", 2, 1);
    
    public static final Skill evadeExtender
            = new Skill("回避距離UP", 2, 3);
    public static final Decoration JumpingJewel2
            = new Decoration(evadeExtender, "跳躍珠【2】", 2, 1);
    public static final Decoration JumpingJewel4
            = new Decoration(evadeExtender, "跳躍珠Ⅱ【4】", 4, 2);
    
    public static final Skill evadeWindow
            = new Skill("回避性能", 2, 5);
    public static final Decoration evasionJewel2
            = new Decoration(evadeWindow, "回避珠【2】", 2, 1);
    public static final Decoration evasionJewel4
            = new Decoration(evadeWindow, "回避珠Ⅱ【4】", 4, 2);
    
    public static final Skill recoveryUp
            = new Skill("体力回復量UP", 2, 3);
    public static final Decoration medicineJewel2
            = new Decoration(recoveryUp, "治癒珠【2】", 2, 1);
    public static final Decoration medicineJewel4
            = new Decoration(recoveryUp, "治癒珠Ⅱ【4】", 4, 2);
    
    public static final Skill recoverySpeed
            = new Skill("回復速度", 1, 3);
    public static final Decoration recoveryJewel1
            = new Decoration(recoverySpeed, "早復珠【2】", 1, 1);
    public static final Decoration hardRecoveryJewel4
            = new Decoration(recoverySpeed, "早復Ⅲ【4】", 4, 3);
    
    public static final Skill focus
            = new Skill("集中", 2, 3);
    public static final Decoration chargerJewel2
            = new Decoration(focus, "短縮珠【2】", 2, 1);
    public static final Decoration chargerJewel4
            = new Decoration(focus, "短縮珠Ⅱ【4】", 4, 2);
    
    public static final Skill constitution
            = new Skill("体術", 2, 5);
    public static final Decoration physiqueJewel2
            = new Decoration(constitution, "体術珠【2】", 2, 1);
    public static final Decoration physiqueJewel4
            = new Decoration(constitution, "体術珠Ⅱ【4】", 4, 2);
    
    public static final Skill speedEating
            = new Skill("早食い", 2, 3);
    public static final Decoration gobblerJewel2
            = new Decoration(speedEating, "早食珠【2】", 2, 1);
    public static final Decoration gobblerJewel4
            = new Decoration(speedEating, "早食珠Ⅱ【4】", 4, 2);
    
    public static final Skill ballistics
            = new Skill("弾導強化", 2, 3);
    public static final Decoration preciseJewel2
            = new Decoration(ballistics, "射法珠【2】", 2, 1);
    public static final Decoration preciseJewel4
            = new Decoration(ballistics, "射法珠Ⅱ【4】", 4, 2);
    
    public static final SeriesSkill kushalaBlessing
            = new SeriesSkill("鋼殻の恩恵", 4);
    public static final SeriesSkill teostraBlessing
            = new SeriesSkill("炎鱗の恩恵", 4);
    public static final SeriesSkill redirection
            = new SeriesSkill("合気", 3);
    public static final SeriesSkill bloodRite
            = new SeriesSkill("血氣", 3);
    public static final SeriesSkill chainCrit
            = new SeriesSkill("連撃", 3);
    public static final SeriesSkill spiribirdsCall
            = new SeriesSkill("供応", 1);
    
    public static final SeriesSkill bloodlust 
            = new SeriesSkill("狂竜症【蝕】", 3);
    public static final SeriesSkill dereliction
            = new SeriesSkill("伏魔響命", 3);
    public static final SeriesSkill mailOfHellfire
            = new SeriesSkill("業鎧【修羅】", 3);
    public static final SeriesSkill coalescence 
            = new SeriesSkill("災禍転福", 3);
    
    public static final List<Skill> ALL_SKILLS = getAllSkills();
    public static final List<Skill> ALL_ATTACK_SKILLS = getAllAttackSkills();
    public static final List<SeriesSkill> ALL_SERIESE_SKILLS = getALLSeriesSkills();
    
    public Simulator() {
        this(new ArrayList<>());
    }
    
    public Simulator(List<Equipment> equipments) {
        helms = new ArrayList<>();
        chests = new ArrayList<>();
        arms = new ArrayList<>();
        waists = new ArrayList<>();
        legs = new ArrayList<>();
        charms = new ArrayList<>();
        
        selectedHelms = new ArrayList<>();
        selectedChests = new ArrayList<>();
        selectedArms = new ArrayList<>();
        selectedWaists = new ArrayList<>();
        selectedLegs = new ArrayList<>();
        selectedCharms = new ArrayList<>();
        
        this.equipments = equipments;
        progress = new SimpleDoubleProperty(0);
        skipRate = new SimpleDoubleProperty(0);
        skipValue = new SimpleIntegerProperty(0);
        cutValue = new SimpleIntegerProperty(0);
        
        bestExpectationData = FXCollections.observableArrayList();
        averageExpectationData = FXCollections.observableArrayList();
        worstExpectationData = FXCollections.observableArrayList();
        bestScoreData = FXCollections.observableArrayList();
        averageScoreData = FXCollections.observableArrayList();
        worstScoreData = FXCollections.observableArrayList();
        
        
//        DEFAULT_ACTIVE_SKILLS();
        setActive(false);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void activateSkill(Skill skill) {
//        activeSkills.add(skill);
        skill.setActive(true);
    }
    
    public void diactivateSkill(Skill skill) {
//        activeSkills.remove(skill);
        skill.setActive(false);
    }
    
    private void DEFAULT_ACTIVE_SKILLS() {
        diactivateSkill(punishingDraw);
        diactivateSkill(counterstrike);
        diactivateSkill(criticalDraw);
    }
    
    public void addHelm(Helm helm) {
        helms.add(helm);
    }
    
    public void addChest(Chest chest) {
        chests.add(chest);
    }
    
    public void addArm(Arm arm) {
        arms.add(arm);
    }
    
    public void addWaist(Waist waist) {
        waists.add(waist);
    }
    
    public void addLeg(Leg leg) {
        legs.add(leg);
    }
    
    public void addCharm(Charm charm) {
        charms.add(charm);
    }
    
    public void clearCharms() {
        charms.clear();
    }
    
    public List<Charm> getCharms()
    {
        return charms;
    }
    
    public Helm findHelmByName(String name)
    {
        for(Helm helm : helms)
        {
            if(name.equals(helm.getName()))
            {
                return helm;
            }
        }
        return null;
    }
    
    public Chest findChestByName(String name)
    {
        for(Chest chest : chests)
        {
            if(name.equals(chest.getName()))
            {
                return chest;
            }
        }
        return null;
    }
    
    public Arm findArmByName(String name)
    {
        for(Arm arm : arms)
        {
            if(name.equals(arm .getName()))
            {
                return arm ;
            }
        }
        return null;
    }
    
    public Waist findWaistByName(String name)
    {
        for(Waist waist : waists)
        {
            if(name.equals(waist.getName()))
            {
                return waist;
            }
        }
        return null;
    }
    
    public Leg findLegByName(String name)
    {
        for(Leg leg : legs)
        {
            if(name.equals(leg.getName()))
            {
                return leg;
            }
        }
        return null;
    }
    
    public Charm findCharmByName(String name)
    {
        for(Charm charm : charms)
        {
            if(name.equals(charm.getName()))
            {
                return charm;
            }
        }
        return null;
    }
    
    public Decoration findDecorationByName(String name)
    {
        for(Skill skill : ALL_SKILLS)
        {
            for(Decoration deco : skill.getDecorations())
            {
                if(name.equals(deco.getName()))
                    return deco;
            }
        }
        return null;
    }
    
    public int findRankingByArmor(Armor armor)
    {
        if(armor instanceof Helm)
            return selectedHelms.indexOf(armor);
        if(armor instanceof Chest)
            return selectedChests.indexOf(armor);
        if(armor instanceof Arm)
            return selectedArms.indexOf(armor);
        if(armor instanceof Waist)
            return selectedWaists.indexOf(armor);
        if(armor instanceof Leg)
            return selectedLegs.indexOf(armor);
        if(armor instanceof Charm)
            return selectedCharms.indexOf(armor);
        return 0;
    }
    
    public String getEquipmentScoreRankings(Helm helm, Chest chest, Arm arm, Waist waist, Leg leg, Charm charm)
    {
        return "Helms[" + selectedHelms.indexOf(helm) + "/" + selectedHelms.size() 
                + "] Chests["  + selectedChests.indexOf(chest) + "/" + selectedChests.size() 
                + "] Arms[" + selectedArms.indexOf(arm) + "/" + selectedArms.size() 
                + "] Waists[" + selectedWaists.indexOf(waist) + "/" + selectedWaists.size() 
                + "] Legs[" + selectedLegs.indexOf(leg) + "/" + selectedLegs.size()
                + "] Charms[" + selectedCharms.indexOf(charm) + "/" + selectedCharms.size() + "]";
    }
    
    public DoubleProperty getProgressProperty()
    {
        return progress;
    }
    
    public DoubleProperty getSkipRateProperty()
    {
        return skipRate;
    }
    
    public IntegerProperty getSkipValueProperty()
    {
        return skipValue;
    }
    
    public IntegerProperty getCutValueProperty()
    {
        return cutValue;
    }

    public ObservableList<Data<Integer, Double>> getBestExpectationData() {
        return bestExpectationData;
    }

    public ObservableList<Data<Integer, Double>> getWorstExpectationData() {
        return worstExpectationData;
    }

    public ObservableList<Data<Integer, Double>> getAverageExpectationData() {
        return averageExpectationData;
    }

    public ObservableList<Data<Integer, Double>> getAverageScoreData() {
        return averageScoreData;
    }
    
    
    
    private <T extends Armor> void select(List<T> armors, List<T> selected, int limit) {
        selected.clear();
        
        boolean addFlag;
        for (T armor : armors) {
            addFlag = true;
            if(armor.getScore() < limit)
            {
                addFlag = false;
                continue;
            }
            
            for (int i = 0; i < selected.size();) {
                
                int compare = armor.isStrongerThan(selected.get(i));
                if (compare > 0) {
                    Armor selectedArmor = selected.remove(i);
                    System.out.println("Removing " + selectedArmor);
                } else if (compare < 0) {
                    System.out.println("Not adding " + armor);
                    addFlag = false;
                    break;
                } else {
                    i++;
                }
            }
            if (addFlag) {
                System.out.println("Adding " + armor);
                selected.add(armor);
            }
        }
        
        if(selected.isEmpty() && !armors.isEmpty())
        {
            selected.add(armors.get(0));
        }
        
        
    }
    
    public void run(Weapon weapon, int listSize) {
        setActive(true);
        equipments.clear();
        progress.set(0);
        skipRate.set(0);
//        skipValue.set(combinationScoreLimit);
        helms.stream().forEach(Armor::updateScore);
        chests.stream().forEach(Armor::updateScore);
        arms.stream().forEach(Armor::updateScore);
        waists.stream().forEach(Armor::updateScore);
        legs.stream().forEach(Armor::updateScore);
        charms.stream().forEach(Armor::updateScore);
        
        select(helms, selectedHelms, cutValue.get());
        select(chests, selectedChests, cutValue.get());
        select(arms, selectedArms, cutValue.get());
        select(waists, selectedWaists, cutValue.get());
        select(legs, selectedLegs, cutValue.get());
        select(charms, selectedCharms, cutValue.get());
        
        Collections.sort(selectedHelms, Comparator.reverseOrder());
        Collections.sort(selectedChests, Comparator.reverseOrder());
        Collections.sort(selectedArms, Comparator.reverseOrder());
        Collections.sort(selectedWaists, Comparator.reverseOrder());
        Collections.sort(selectedLegs, Comparator.reverseOrder());
        Collections.sort(selectedCharms, Comparator.reverseOrder());
        
        selectedHelms.subList(Math.min(20, selectedHelms.size()), selectedHelms.size()).clear();
        selectedChests.subList(Math.min(20, selectedChests.size()), selectedChests.size()).clear();
        selectedArms.subList(Math.min(20, selectedArms.size()), selectedArms.size()).clear();
        selectedWaists.subList(Math.min(20, selectedWaists.size()), selectedWaists.size()).clear();
        selectedLegs.subList(Math.min(20, selectedLegs.size()), selectedLegs.size()).clear();
        selectedCharms.subList(Math.min(20, selectedCharms.size()), selectedCharms.size()).clear();
//        List<Skill> activeSkills = getActiveSkills();
        
        int combinations = 
                selectedHelms.size() *
                selectedChests.size() * 
                selectedArms.size() *
                selectedWaists.size() *
                selectedLegs.size() *
                selectedCharms.size();
        
        System.out.println(combinations + " Patterns");
        System.out.println("Helms[" + selectedHelms.size() 
                + "] Chests[" + selectedChests.size() 
                + "] Arms[" + selectedArms.size() 
                + "] Waists[" + selectedWaists.size() 
                + "] Legs[" + selectedLegs.size()
                + "] Charms[" + selectedCharms.size() + "]");
        
        int progressCounter = 0;
        int skipCounter = 0;
        for (int i = 0; i < selectedHelms.size(); i++) {
            for (int j = 0; j < selectedChests.size(); j++) {
                for (int k = 0; k < selectedArms.size(); k++) {
                    for (int x = 0; x < selectedWaists.size(); x++) {
                        for (int y = 0; y < selectedLegs.size(); y++) {
                            for (int w = 0; w < selectedCharms.size(); w++) {
                                if(!isActive())
                                {
                                    System.out.println("Exiting");
                                    return;
                                }
//                                i = 0;j = 3;k = 1; x = 9; y = 1;w = 0;
//                                
//                                if(i == 1 && j == 1 && k == 1 && x == 11 && y == 16 && w == 4)
//                                    System.out.println("Helo here");
                                progress.set(progressCounter++/(double)combinations);
                                
                                

                                if(Equipment.findScoreSum(
                                        selectedHelms.get(i),
                                        selectedChests.get(j),
                                        selectedArms.get(k),
                                        selectedWaists.get(x),
                                        selectedLegs.get(y),
                                        selectedCharms.get(w)) < skipValue.get())
                                {
//                                    skipCounter += selectedCharms.size() - w;
//                                    progressCounter += selectedCharms.size() - w - 1;
//                                    skipRate.set(skipCounter/(double)progressCounter);
//                                    break;
                                    skipRate.set(skipCounter++/(double)progressCounter);
                                    continue;
                                }
                                
                                Equipment e = new Equipment(
                                        weapon,
                                        selectedHelms.get(i),
                                        selectedChests.get(j),
                                        selectedArms.get(k),
                                        selectedWaists.get(x),
                                        selectedLegs.get(y),
                                        selectedCharms.get(w));
                                
//                                if(!(i == 0 && j == 3 && k == 1 && x == 9 && y == 1 && w == 0))
//                                    continue;
                                
                                if(e.updateBestDecoration()){
                                    if(equipments.size() < listSize)
                                    {
                                        pushExpectation(e);
//                                        if(e.getExpectation() == 0)
//                                        {
//                                            System.out.println("Zero");
//                                            e.updateBestDecoration();
//                                        }
                                    }
                                    else{
                                        if(equipments.get(equipments.size() - 1).getExpectation() < e.getExpectation())
                                        {
                                            Equipment removed = equipments.remove(equipments.size() - 1);
//                                            totalExpectation -= removed.getExpectation();
//                                            totalScore -= removed.getScore();
                                            pushExpectation(e);
                                        }
                                    }
                                    
                                }
//                                if(i == 0 && j == 3 && k == 1 && x == 9 && y == 1 && w == 0)
//                                {
//                                    
//                                    System.out.println("Helo here");
//                                    return;
//                                }
                            }
                        }
                    }
                }
                
            }
        }
        Collections.sort(equipments, Comparator.reverseOrder());
//        equipments.stream().forEach(e -> System.out.println(e.getExpectation()));
        for(Equipment e : equipments)
        {
            System.out.println(e.getSkillMap());
            System.out.println(e.getExpectation());
        }
        System.out.println(equipments.get(0));
        System.out.println(equipments.get(0).getExpectation());
        setActive(false);
        
    }
    
    private void pushExpectation(Equipment e)
    {
//        chartCounter++;
//        totalExpectation += e.getExpectation();
//        totalScore += e.getScore();
        
        synchronized(equipments)
        {
            equipments.add(e);
            Collections.sort(equipments, Comparator.reverseOrder());
        }
        
        
//        Platform.runLater(new Runnable(){
//            @Override
//            public void run() {
//                bestExpectationData.add(
//                        new XYChart.Data<>(chartCounter, equipments.get(0).getExpectation())
//                );
//                worstExpectationData.add(
//                        new XYChart.Data<>(chartCounter, equipments.get(equipments.size() - 1).getExpectation())
//                );
//
//                averageExpectationData.add(
//                        new XYChart.Data<>(chartCounter, totalExpectation / (double)equipments.size())
//                );
//                averageScoreData.add(
//                        new XYChart.Data<>(chartCounter, totalScore / (double)equipments.size())
//                );
//            }
//        });
    }
    
    public static List<SeriesSkill> getALLSeriesSkills()
    {
        return List.of(kushalaBlessing, teostraBlessing);
    }
    
//    private List<Skill> getActiveSkills() {
//        return ALL_SKILLS.stream().filter(Skill::isActive).toList();
//    }
    
    private static List<Skill> getAllSkills() {
        return List.of(emptySkill,
                attackBoost, 
                peakPerformance, 
                criticalEye, 
                criticalBoost, 
                weaknessExploit, 
                criticalDraw, 
                maximumMight, 
                agitator, 
                counterstrike, 
                punishingDraw, 
                offensiveGuard, 
                resuscitate, 
                resentment, 
                latentPower, 
                heroics, 
                masterTouch,
                handicraft,
                razorSharp,
                speedSharping,
                protectivePolish,
                NormalRapidUp,
                pierceUp,
                spreadUp,
                rapidFireUp,
                recoilDown,
                reloadSpeed,
                spareShot,
                quickSheath,
                flinchFree,
                stunResistance,
                freeMeal,
                mushroomancer,
                wideRange,
                ammoUp,
                slugger,
                criticalElement,
                defenseBoost,
                fireAttack,
                thunderAttack,
                waterAttack,
                iceAttack,
                dragonAttack,
                blastAttack,
                paralysisAttack,
                kushalaBlessing,
                teostraBlessing,
                redirection,
                bloodRite,
                chainCrit,
                spiribirdsCall,
                evadeExtender,
                evadeWindow,
                recoveryUp,
                recoverySpeed,
                focus,
                constitution,
                speedEating,
                ballistics,
                bloodlust,
                dereliction,
                mailOfHellfire,
                coalescence
               );
       

    }
    
    public static List<Skill> getAllAttackSkills()
    {
        return getAllSkills().stream().filter(AttackSkill.class::isInstance).toList();
    }
    
    public static void main(String[] args) {
        Simulator simu = new Simulator();
        masterTouch.setRequired(0);
        
        Helm helm = new Helm("Helm", 0, 0, 1, 0);
        helm.addSkill(attackBoost, 2);
        Helm helm1 = new Helm("Helm1", 0, 0, 2, 1);
        helm1.addSkill(criticalEye, 1);
        Helm helm2 = new Helm("Helm2", 0, 0, 0, 2);
        helm2.addSkill(peakPerformance, 1);
        helm2.addSkill(criticalBoost, 1);
        Helm helm3 = new Helm("Helm3", 0, 0, 2, 1);
        helm3.addSkill(maximumMight, 1);
        Helm helm4 = new Helm("Helm4", 0, 0, 0, 3);
        helm4.addSkill(weaknessExploit, 2);
        Helm helm5 = new Helm("Helm5", 0, 0, 1, 0);
        helm5.addSkill(agitator, 2);
        helm5.addSkill(counterstrike, 1);
        Helm helm6 = new Helm("Helm6", 0, 0, 1, 1);
        helm6.addSkill(criticalEye, 1);
        Helm helm7 = new Helm("Helm7", 0, 0, 0, 2);
        helm7.addSkill(peakPerformance, 2);
        helm7.addSkill(criticalBoost, 1);
        
        simu.addHelm(helm);
        simu.addHelm(helm1);
        simu.addHelm(helm2);
        simu.addHelm(helm3);
        simu.addHelm(helm4);
        simu.addHelm(helm5);
        simu.addHelm(helm6);
        simu.addHelm(helm7);
        
        Chest chest = new Chest("Chest", 0, 0, 1, 0);
        chest.addSkill(criticalEye, 1);
        chest.addSkill(criticalBoost, 1);
        Chest chest1 = new Chest("Chest1", 0, 0, 1, 0);
        chest1.addSkill(criticalEye, 2);
        Chest chest2 = new Chest("Chest2", 0, 0, 0, 3);
        chest2.addSkill(attackBoost, 1);
        chest2.addSkill(criticalEye, 1);
        Chest chest3 = new Chest("Chest3", 0, 0, 1, 0);
        chest3.addSkill(weaknessExploit, 1);
        Chest chest4 = new Chest("Chest4", 0, 0, 0, 0);
        chest4.addSkill(attackBoost, 2);
        Chest chest5 = new Chest("Chest5", 0, 0, 0, 0);
        chest5.addSkill(attackBoost, 1);
        Chest chest6 = new Chest("Chest6", 0, 0, 1, 2);
        chest6.addSkill(criticalBoost, 1);
        chest6.addSkill(criticalDraw, 1);
        
        simu.addChest(chest);
        simu.addChest(chest1);
        simu.addChest(chest2);
        simu.addChest(chest3);
        simu.addChest(chest4);
        simu.addChest(chest5);
        simu.addChest(chest6);
        
        Arm arm = new Arm("arm", 0, 0, 0, 0);
        arm.addSkill(attackBoost, 2);
        Waist waist = new Waist("waist", 0, 0, 2, 0);
        waist.addSkill(criticalEye, 1);
        Leg leg = new Leg("leg", 0, 0, 0, 1);
        leg.addSkill(peakPerformance, 1);
        Charm charm = new Charm("charm", 0, 0, 1, 2);
        charm.addSkill(criticalEye, 1);
        
        simu.addArm(arm);
        simu.addWaist(waist);
        simu.addLeg(leg);
        simu.addCharm(charm);
        
        simu.run(new Weapon("Name", 200, 0), 10);
        
        System.out.println("Simu finished");
    }
}
