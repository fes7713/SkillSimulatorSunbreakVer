/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package skillsimulator1;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import skillsimulator1.Armor.Arm;
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
import static skillsimulator1.DataLoader.createFile;
import static skillsimulator1.DataLoader.loadMyset;
import static skillsimulator1.DataLoader.saveMyset;
import skillsimulator1.Decoration.Decoration;
import static skillsimulator1.Simulator.NormalRapidUp;
import static skillsimulator1.Simulator.agitator;
import static skillsimulator1.Simulator.attackBoost;
import static skillsimulator1.Simulator.chainCrit;
import static skillsimulator1.Simulator.counterstrike;
import static skillsimulator1.Simulator.criticalBoost;
import static skillsimulator1.Simulator.criticalDraw;
import static skillsimulator1.Simulator.criticalEye;
import static skillsimulator1.Simulator.heroics;
import static skillsimulator1.Simulator.latentPower;
import static skillsimulator1.Simulator.maximumMight;
import static skillsimulator1.Simulator.offensiveGuard;
import static skillsimulator1.Simulator.peakPerformance;
import static skillsimulator1.Simulator.pierceUp;
import static skillsimulator1.Simulator.punishingDraw;
import static skillsimulator1.Simulator.rapidFireUp;
import static skillsimulator1.Simulator.resentment;
import static skillsimulator1.Simulator.resuscitate;
import static skillsimulator1.Simulator.spreadUp;
import static skillsimulator1.Simulator.weaknessExploit;
import skillsimulator1.Skill.Skill;
import skillsimulator1.Skill.SwordGunnerChoice;


/**
 *
 * @author fes77
 */
public class UImainController implements Initializable {
    Simulator sim;
    List<Skill> skillList;
    Thread thread;
    
    @FXML
    private Label label;
    
    @FXML
    private ComboBox<Integer> combobox;

    @FXML
    private LineChart chart;
    
    
    
    @FXML 
    private Button button;
    
    @FXML
    private CheckBox AttackBoostBox;
    @FXML
    private CheckBox PeakPerformanceBox;
    //死に活
    @FXML
    private CheckBox ResuscitateBox;
    @FXML
    private CheckBox CounterstrikeBox;
    @FXML
    private CheckBox ResentmentBox;
    @FXML
    private CheckBox OffensiveGuardBox;
    @FXML
    private CheckBox HeroicsBox;
    @FXML
    private CheckBox AgitatorBox;
    @FXML
    private CheckBox PunishingDrawBox;
    @FXML
    private CheckBox CriticalDrawBox;
    @FXML
    private CheckBox CriticalBoostBox;
    @FXML
    private CheckBox CriticalEyeBox;
    @FXML
    private CheckBox WeaknessExploitBox;
    @FXML
    private CheckBox MaximumMightBox;
    @FXML
    private CheckBox LatentPowerBox;
    @FXML
    private CheckBox ChainCritCheckBox;
    @FXML
    private RadioButton GunnerButton;
    @FXML
    private RadioButton NormalRapidUpRadioBox;
    @FXML
    private RadioButton PieceUpRadioBox;
    @FXML
    private RadioButton SpreadUpRadioBox;
    @FXML
    private CheckBox RapidFireUpCheckBox;
    
    private BooleanProperty bulletFlag;
//    @FXML
//    private GridPane requiredSkillGridPane;
    
    @FXML
    private ComboBox MasterTouchBox;
    @FXML
    private ComboBox HandicraftBox;
    @FXML
    private ComboBox RazorSharpBox;
    @FXML
    private ComboBox SpeedSharpingBox;
    @FXML
    private ComboBox ProtectivePolishBox;
    @FXML
    private ComboBox NormalRapidUpBox;
    @FXML
    private ComboBox PieceUpBox;
    @FXML
    private ComboBox SpreadUpBox;
    @FXML
    private ComboBox RapidFireUpBox;
    @FXML
    private ComboBox RecoilDownBox;
    @FXML
    private ComboBox ReloadSpeedBox;
    @FXML
    private ComboBox SpareShotBox;
    @FXML
    private ComboBox QuickSheathBox;
    @FXML
    private ComboBox FlinchFreeBox;
    @FXML
    private ComboBox StunResistanceBox;
    @FXML
    private ComboBox FreeMealBox;
    @FXML
    private ComboBox MushroomancerBox;
    @FXML
    private ComboBox WideRangeBox;
    @FXML
    private ComboBox AmmoUpBox;
    @FXML
    private ComboBox SluggerBox;
    @FXML
    private ComboBox CriticalDrawCmbBox;
    @FXML
    private ComboBox DefenseBoostBox;
    @FXML
    private ComboBox CriticalElementBox;
    @FXML
    private ComboBox KushalaBlessingBox;
    @FXML
    private ComboBox TeostraBlessingBox;
    @FXML
    private ComboBox RedirectionBox;
    @FXML
    private ComboBox BloodRiteBox;
    @FXML
    private ComboBox ChainCritBox;
    @FXML
    private ComboBox SpiribirdsCallBox;
    @FXML
    private ComboBox EvadeExtenderBox;
    @FXML
    private ComboBox EvadeWindowBox;
    @FXML
    private ComboBox RecoveryUpBox;
    @FXML
    private ComboBox RecoverySpeedBox;
    @FXML
    private ComboBox FocusBox;
    @FXML
    private ComboBox ConstitutionBox;
    @FXML
    private ComboBox SpeedEatingBox;
    @FXML
    private ComboBox BallisticsBox;
    @FXML
    private ComboBox BloodlustBox;
    @FXML
    private ComboBox DerelictionBox;
    @FXML
    private ComboBox MailOfHellfireBox;
    @FXML
    private ComboBox CoalescenceBox;
    @FXML
    private ComboBox Grinder_SBox;
    @FXML
    private ComboBox BladescaleHoneBox;
    @FXML
    private ComboBox ElementExploitBox;
    
    private final ObservableList<Equipment> equipmentData = FXCollections.observableArrayList();
    private DoubleProperty bestExpectation;
    
    @FXML 
    private TableView equipmentTable;
    @FXML 
    private TableColumn<Equipment, Double> attackCol;
    @FXML 
    private TableColumn helmCol;
    @FXML 
    private TableColumn chestCol;
    @FXML 
    private TableColumn armCol;
    @FXML 
    private TableColumn waistCol;
    @FXML 
    private TableColumn legCol;
    @FXML 
    private TableColumn charmCol;
    
    private final ObservableList<Myset> mysetData = FXCollections.observableArrayList();
    @FXML 
    private TableView<Myset> mysetTable;
    @FXML 
    private TableColumn<Equipment, Double> mysetAttackCol;
    @FXML 
    private TableColumn mysetHelmCol;
    @FXML 
    private TableColumn mysetChestCol;
    @FXML 
    private TableColumn mysetArmCol;
    @FXML 
    private TableColumn mysetWaistCol;
    @FXML 
    private TableColumn mysetLegCol;
    @FXML 
    private TableColumn mysetCharmCol;
    @FXML 
    private Button mysetSave;
    @FXML 
    private TextField mysetTitle;
    
    private ObservableList<SkillItem> skillData;
    @FXML 
    private TableView skillTable;
    @FXML 
    private TableColumn skillNameCol;
    @FXML 
    private TableColumn skillLevelCol;
    
    private ObservableList<DecorationItem> decorationData;
    @FXML 
    private TableView decorationlTable;
    @FXML 
    private TableColumn decorationNameCol;
    @FXML 
    private TableColumn decorationLevelCol;
    
    private ObservableList<Slot> extraSlotData;
    @FXML 
    private TableView extraSlotTable;
    @FXML 
    private TableColumn extraSlot4Col;
    @FXML 
    private TableColumn extraSlot3Col;
    @FXML 
    private TableColumn extraSlot2Col;
    @FXML 
    private TableColumn extraSlot1Col;
    
    private ObservableList<String> equipmentDetailData;
    @FXML 
    private ListView<String> equipmentDetailList;
    
    
    XYChart.Series<Integer, Double> bestSeries;
    XYChart.Series<Integer, Double> avgSeries;
    XYChart.Series<Integer, Double> worstSeries;
    XYChart.Series<Integer, Double> avgScoreSeries;
    XYChart.Series<Integer, Integer> firstScoreSeries;
    XYChart.Series<Integer, Integer> lastScoreSeries;
    
    @FXML
    private TableView<Charm> charmTable;
    private ObservableList<Charm> charmData;
    
    @FXML
    private ComboBox<Skill> charmSkill1Box;
    @FXML
    private ComboBox<Integer> charmSkill1LevelBox;
    @FXML
    private ComboBox<Skill> charmSkill2Box;
    @FXML
    private ComboBox<Integer> charmSkill2LevelBox;
    @FXML
    private ComboBox<Slot> charmSkillSlotBox;
    @FXML
    private Button charmAddButton;
    @FXML 
    private TableColumn<Charm, Skill> charmSkill1Col;
    @FXML 
    private TableColumn<Charm, Skill> charmSkill2Col;
    @FXML 
    private TableColumn<Charm, Integer> charmSkill1LevelCol;
    @FXML 
    private TableColumn<Charm, Integer> charmSkill2LevelCol;
    @FXML 
    private TableColumn<Charm, Slot> charmSlotCol;
    
    
    private Weapon weapon;
    @FXML
    private Spinner<Integer> weaponDamageSpinner;
    @FXML
    private Spinner<Integer> weaponAffinitySpinner;
    @FXML
    private ComboBox<Slot> weaponSlotBox;
    @FXML
    private ComboBox<Skill> elementBox;
    @FXML
    private ComboBox<Integer> elementLevelBox;
    
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressBar skipRateBar;
    @FXML
    private Spinner<Integer> skipRateScoreSpinner;
    @FXML
    private Spinner<Integer> cutScoreSpinner;
    
    
    @FXML
    private ComboBox<Helm> helmFixCmbBox;
    @FXML
    private ComboBox<Chest> chestFixCmbBox;
    @FXML
    private ComboBox<Arm> armFixCmbBox;
    @FXML
    private ComboBox<Waist> waistFixCmbBox;
    @FXML
    private ComboBox<Leg> legFixCmbBox;
    @FXML
    private ComboBox<Charm> charmFixCmbBox;
    
    private ObservableList<Helm> helmList;
    private ObservableList<Chest> chestList;
    private ObservableList<Arm> armList;
    private ObservableList<Waist> waistList;
    private ObservableList<Leg> legList;
    private ObservableList<Charm> charmList;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println(weapon);
        System.out.println(attackBoost.getRequired());

        bestSeries.getData().clear();
        avgSeries.getData().clear();
        worstSeries.getData().clear();
        avgScoreSeries.getData().clear();
        firstScoreSeries.getData().clear();
        lastScoreSeries.getData().clear();

        System.out.println("Updated");
        
        if(thread.isAlive())
        {
            sim.setActive(false);
            try
            {
                thread.join();
                System.out.println("Thread joined");
            }catch(InterruptedException e)
            {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
        
        
        bestExpectation.set(0);
        thread = new Thread(){
            @Override
            public void run(){
                sim.setActive(true);
                sim.run(weapon, 100, 
                        helmFixCmbBox.getValue(), 
                        chestFixCmbBox.getValue(), 
                        armFixCmbBox.getValue(), 
                        waistFixCmbBox.getValue(), 
                        legFixCmbBox.getValue(), 
                        charmFixCmbBox.getValue()
                );
                
            }
        };
        thread.start();
            
        
    }
    
    @FXML
    private void updateSkillList(Equipment e) {
        
        Map<Skill, Integer> skills = e.getSkillMap();
        Map<Decoration, Integer> decorations = e.getBestDecorations()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != 0)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        
        skillData.clear();
        decorationData.clear();
        extraSlotData.clear();
        equipmentDetailData.clear();
        
        System.out.println("Entred");
        for(Entry<Skill, Integer> entry : skills.entrySet())
        {
            if(entry.getValue() == 0)
                continue;
            skillData.add(new SkillItem(entry.getKey(), entry.getValue()));
            
        }
        
        for(Entry<Decoration, Integer> entry : decorations.entrySet())
        {
            if(entry.getValue() == 0)
                continue;
            decorationData.add(new DecorationItem(entry.getKey(), entry.getValue()));
        }
        
        extraSlotData.add(e.getExtraSlot());
        
        if(e instanceof Myset)
        {
            equipmentDetailData.add("タイトル　→" + ((Myset)e).getTitle());
            equipmentDetailData.add("武器　　　→" + ((Myset)e).getWeapon());
        }
        else{
            equipmentDetailData.add("スコア　→" + e.getScore());
            equipmentDetailData.add("ランク　→" + e.getArmorRanking(sim));
            
        }
        
        System.out.println(e);
       
        Collections.sort(skillData, (s1, s2) -> {
            return s2.getLevel() - s1.getLevel();
        });
        
        Collections.sort(decorationData, (d1, d2) -> {
            return d2.getLevel() - d1.getLevel();
        });
         
   }
    
    @FXML
    private void onCharmAddClicked(ActionEvent event) {
        System.out.println("Adding charm");
        Slot slot = charmSkillSlotBox.getValue();
        Skill skill1 = charmSkill1Box.getValue();
        Skill skill2 = charmSkill2Box.getValue();
        Integer skill1Level = charmSkill1LevelBox.getValue();
        Integer skill2Level = charmSkill2LevelBox.getValue();
        
        Map<Skill, Integer> skillMap = new HashMap();
        StringBuilder sb = new StringBuilder();
        
        if(skill1 != Simulator.emptySkill)
        {
            skillMap.put(skill1, skill1Level);
            sb.append(skill1.getName());
            sb.append(skill1Level);
        }
        if(skill2 != Simulator.emptySkill)
        {
            skillMap.put(skill2, skill2Level);
            sb.append(skill2.getName());
            sb.append(skill2Level);
        }
        sb.append(slot);
        
        
        Charm charm = new Charm(sb.toString(), skillMap, slot.getSlot4(), slot.getSlot3(), slot.getSlot2(), slot.getSlot1());
        DataLoader.appendRow(
                "data/MHR_EQUIP_CHARM.tsv", 
                DataLoader.outputRow(charm));
        charmData.add(charm);
        sim.addCharm(charm);
    }
    
    @FXML
    private void onCharmDelete(ActionEvent event)
    {
        
        createFile("data/MHR_EQUIP_CHARM.tsv");
        
        Charm seletcedCharm = charmTable.getSelectionModel().getSelectedItem();
        System.out.println(seletcedCharm);
        charmData.remove(seletcedCharm);
        
        charmData.stream()
                .forEach(
                        c -> DataLoader.appendRow(
                                "data/MHR_EQUIP_CHARM.tsv", 
                                DataLoader.outputRow(c)
                        )
                );
        sim.getCharms().remove(seletcedCharm);
    }
    
    @FXML
    private void onMysetSave(ActionEvent event)
    {
        int index = equipmentTable.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        
        
        if(index >= 0)
        {
            
            System.out.println("Saving myset");
            String title = mysetTitle.getText();
            mysetTitle.setText("");
            List<Myset> mysets = loadMyset(sim);
            mysets.add(new Myset(title, equipmentData.get(index)));
            saveMyset(title, equipmentData.get(index), equipmentData.get(index).getExpectation());
            mysetData.clear();
            mysetData.addAll(mysets);
        }
    }
    
    @FXML
    private void onMysetDelete(ActionEvent event)
    {
        List<Myset> mysets = loadMyset(sim);
        createFile("data/MHR_EQUIP_MYSET.tsv");
        
        Myset selectedMyset = mysetTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedMyset);
        mysets.remove(selectedMyset);
        mysetData.clear();
        mysetData.addAll(mysets);
        
        mysetData.stream()
                .forEach(
                        m -> DataLoader.saveMyset(m.getTitle(), m, m.getExpectation())
                );
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        thread = new Thread();
        bulletFlag = new SimpleBooleanProperty();
        
//        equipmentData;

        equipmentTable.itemsProperty().setValue(equipmentData);
        equipmentTable.setItems(equipmentData);
        equipmentTable.setRowFactory(tv -> {
            TableRow<Equipment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()) {
                    Equipment rowData = row.getItem();
                    updateSkillList(rowData);
                }
            });
            return row ;
        });
    
        attackCol.setCellValueFactory(e -> e.getValue().getBestExDoubleProperty().asObject());
        helmCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("helm"));
        chestCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("chest"));
        armCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("arm"));
        waistCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("waist"));
        legCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("leg"));
        charmCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("charm"));
        
        // myset
        mysetTable.itemsProperty().setValue(mysetData);
        mysetTable.setItems(mysetData);
        mysetTable.setRowFactory(tv -> {
            TableRow<Myset> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()) {
                    Equipment rowData = row.getItem();
                    updateSkillList(rowData);
                }
            });
            
            return row ;
        });
        mysetAttackCol.setCellValueFactory(e -> e.getValue().getBestExDoubleProperty().asObject());
        mysetHelmCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("helm"));
        mysetChestCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("chest"));
        mysetArmCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("arm"));
        mysetWaistCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("waist"));
        mysetLegCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("leg"));
        mysetCharmCol.setCellValueFactory(new PropertyValueFactory<Equipment, String>("charm"));
        
        // Skill table
        skillData = FXCollections.observableArrayList();
        skillTable.itemsProperty().setValue(skillData);
        skillTable.setItems(skillData);
        skillNameCol.setCellValueFactory(new PropertyValueFactory<SkillItem, String>("name"));
        skillLevelCol.setCellValueFactory(new PropertyValueFactory<SkillItem, Integer>("level"));
        
        // Decoration Data
        decorationData = FXCollections.observableArrayList();
        decorationlTable.itemsProperty().setValue(decorationData);
        decorationlTable.setItems(decorationData);
        decorationNameCol.setCellValueFactory(new PropertyValueFactory<SkillItem, String>("decoration"));
        decorationLevelCol.setCellValueFactory(new PropertyValueFactory<SkillItem, Integer>("level"));
        
        // extra slot setting
        extraSlotData = FXCollections.observableArrayList();
        extraSlotTable.itemsProperty().setValue(extraSlotData);
        extraSlotTable.setItems(extraSlotData);
        extraSlot4Col.setCellValueFactory(new PropertyValueFactory<Slot, Integer>("slot4"));
        extraSlot3Col.setCellValueFactory(new PropertyValueFactory<Slot, Integer>("slot3"));
        extraSlot2Col.setCellValueFactory(new PropertyValueFactory<Slot, Integer>("slot2"));
        extraSlot1Col.setCellValueFactory(new PropertyValueFactory<Slot, Integer>("slot1"));

        equipmentDetailData = FXCollections.observableArrayList();
        equipmentDetailList.itemsProperty().setValue(equipmentDetailData);
        equipmentDetailList.setItems(equipmentDetailData);

        
        //defining a bestSeries
        bestSeries = new XYChart.Series();
        avgSeries = new XYChart.Series();
        worstSeries = new XYChart.Series();
        avgScoreSeries = new XYChart.Series();
        firstScoreSeries = new XYChart.Series();
        lastScoreSeries = new XYChart.Series();
        
        bestSeries.setName("Best");
        avgSeries.setName("Average"); 
        worstSeries.setName("Worst");
        avgScoreSeries.setName("Average Score");
        firstScoreSeries.setName("First Score");
        lastScoreSeries.setName("Last Score");

        chart.getData().add(bestSeries);
        chart.getData().add(avgSeries);
        chart.getData().add(worstSeries);
        chart.getData().add(avgScoreSeries);
        chart.getData().add(firstScoreSeries);
        chart.getData().add(lastScoreSeries);
        
        
        
        
        // Condition skill setting
        AttackBoostBox.selectedProperty().bindBidirectional(attackBoost.activeProperty());
        AgitatorBox.selectedProperty().bindBidirectional(agitator.activeProperty());
        CounterstrikeBox.selectedProperty().bindBidirectional(counterstrike.activeProperty());
        CriticalBoostBox.selectedProperty().bindBidirectional(criticalBoost.activeProperty());
        CriticalDrawBox.selectedProperty().bindBidirectional(criticalDraw.activeProperty());
        CriticalEyeBox.selectedProperty().bindBidirectional(criticalEye.activeProperty());
        HeroicsBox.selectedProperty().bindBidirectional(heroics.activeProperty());
        LatentPowerBox.selectedProperty().bindBidirectional(latentPower.activeProperty());
        MaximumMightBox.selectedProperty().bindBidirectional(maximumMight.activeProperty());
        OffensiveGuardBox.selectedProperty().bindBidirectional(offensiveGuard.activeProperty());
        PeakPerformanceBox.selectedProperty().bindBidirectional(peakPerformance.activeProperty());
        PunishingDrawBox.selectedProperty().bindBidirectional(punishingDraw.activeProperty());
        ResentmentBox.selectedProperty().bindBidirectional(resentment.activeProperty());
        ResuscitateBox.selectedProperty().bindBidirectional(resuscitate.activeProperty());
        WeaknessExploitBox.selectedProperty().bindBidirectional(weaknessExploit.activeProperty());
        ChainCritCheckBox.selectedProperty().bindBidirectional(chainCrit.activeProperty());
        GunnerButton.selectedProperty().bindBidirectional(((SwordGunnerChoice)chainCrit).getGunnerProperty());
        
        NormalRapidUpRadioBox.selectedProperty().bindBidirectional(NormalRapidUp.activeProperty());
        PieceUpRadioBox.selectedProperty().bindBidirectional(pierceUp.activeProperty());
        SpreadUpRadioBox.selectedProperty().bindBidirectional(spreadUp.activeProperty());
        RapidFireUpCheckBox.selectedProperty().bindBidirectional(rapidFireUp.activeProperty());
      
        SpreadUpRadioBox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("Old -> " + oldValue);
            System.out.println("New -> " + newValue);
            
        }));
        
        Stream.of(NormalRapidUpRadioBox, PieceUpRadioBox, SpreadUpRadioBox)
                .forEach(box -> box.setOnMousePressed(
                (event) -> {
//                    System.out.println("State " + SpreadUpRadioBox.selectedProperty().get());
//                    if(SpreadUpRadioBox.selectedProperty().get())
//                        SpreadUpRadioBox.setSelected(false);
                    bulletFlag.set(box.selectedProperty().get());
        }));
        
        Stream.of(NormalRapidUpRadioBox, PieceUpRadioBox, SpreadUpRadioBox)
                .forEach(box -> box.setOnMouseReleased(
                (event) -> {
//                    System.out.println("State " + SpreadUpRadioBox.selectedProperty().get());
                    if(bulletFlag.get())
                    {
                        bulletFlag.set(false);
                        box.selectedProperty().set(false);
                    }
        }));
//        SpreadUpRadioBox.setOn
        
        
        ToggleGroup group = new ToggleGroup();
        group.getToggles().add(NormalRapidUpRadioBox);
        group.getToggles().add(PieceUpRadioBox);
        group.getToggles().add(SpreadUpRadioBox);

//        ObjectProperty<Toggle> selectedToggle = new SimpleObjectProperty<>();
//        selectedToggle.bind(group.selectedToggleProperty());
//        selectedToggle.addListener(listener);
        
        
        AgitatorBox.setSelected(true);
        AttackBoostBox.setSelected(true);
        CounterstrikeBox.setSelected(false);
        CriticalBoostBox.setSelected(true);
        CriticalDrawBox.setSelected(false);
        CriticalEyeBox.setSelected(true);
        HeroicsBox.setSelected(false);
        LatentPowerBox.setSelected(false);
        MaximumMightBox.setSelected(true);
        OffensiveGuardBox.setSelected(false);
        PeakPerformanceBox.setSelected(false);
        PunishingDrawBox.setSelected(false);
        ResentmentBox.setSelected(false);
        ResuscitateBox.setSelected(false);
        WeaknessExploitBox.setSelected(true);
        ChainCritCheckBox.setSelected(false);
        GunnerButton.setSelected(false);
        NormalRapidUpRadioBox.setSelected(false);
        PieceUpRadioBox.setSelected(false);
        SpreadUpRadioBox.setSelected(false);
        RapidFireUpCheckBox.setSelected(false);
        
        Map<ComboBox, Skill> comboboxMap = new HashMap();
        comboboxMap.put(MasterTouchBox, Simulator.masterTouch);
        comboboxMap.put(HandicraftBox, Simulator.handicraft);
        comboboxMap.put(RazorSharpBox, Simulator.razorSharp);
        comboboxMap.put(SpeedSharpingBox, Simulator.speedSharping);
        comboboxMap.put(ProtectivePolishBox, Simulator.protectivePolish);
        comboboxMap.put(NormalRapidUpBox, Simulator.NormalRapidUp);
        comboboxMap.put(PieceUpBox, Simulator.pierceUp);
        comboboxMap.put(SpreadUpBox, Simulator.spreadUp);
        comboboxMap.put(RapidFireUpBox, Simulator.rapidFireUp);
        comboboxMap.put(RecoilDownBox, Simulator.recoilDown);
        comboboxMap.put(ReloadSpeedBox, Simulator.reloadSpeed);
        comboboxMap.put(SpareShotBox, Simulator.spareShot);
        comboboxMap.put(QuickSheathBox, Simulator.quickSheath);
        comboboxMap.put(FlinchFreeBox, Simulator.flinchFree);
        comboboxMap.put(StunResistanceBox, Simulator.stunResistance);
        comboboxMap.put(FreeMealBox, Simulator.freeMeal);
        comboboxMap.put(MushroomancerBox, Simulator.mushroomancer);
        comboboxMap.put(WideRangeBox, Simulator.wideRange);
        comboboxMap.put(AmmoUpBox, Simulator.ammoUp);
        comboboxMap.put(SluggerBox, Simulator.slugger);
        comboboxMap.put(CriticalElementBox, Simulator.criticalElement);
        comboboxMap.put(CriticalDrawCmbBox, Simulator.criticalDraw);
        comboboxMap.put(DefenseBoostBox, Simulator.defenseBoost);
        comboboxMap.put(KushalaBlessingBox, Simulator.kushalaBlessing);
        comboboxMap.put(TeostraBlessingBox, Simulator.teostraBlessing);
        comboboxMap.put(BloodRiteBox, Simulator.bloodRite);
        comboboxMap.put(RedirectionBox, Simulator.redirection);
        comboboxMap.put(ChainCritBox, Simulator.chainCrit);
        comboboxMap.put(SpiribirdsCallBox, Simulator.spiribirdsCall);
        comboboxMap.put(EvadeExtenderBox, Simulator.evadeExtender);
        comboboxMap.put(EvadeWindowBox, Simulator.evadeWindow);
        comboboxMap.put(RecoveryUpBox, Simulator.recoveryUp);
        comboboxMap.put(RecoverySpeedBox, Simulator.recoverySpeed);
        comboboxMap.put(FocusBox, Simulator.focus);
        comboboxMap.put(ConstitutionBox, Simulator.constitution);
        comboboxMap.put(SpeedEatingBox, Simulator.speedEating);
        comboboxMap.put(BallisticsBox, Simulator.ballistics);
        comboboxMap.put(BloodlustBox, Simulator.bloodlust);
        comboboxMap.put(DerelictionBox, Simulator.dereliction);
        comboboxMap.put(MailOfHellfireBox, Simulator.mailOfHellfire);
        comboboxMap.put(CoalescenceBox, Simulator.coalescence);
        comboboxMap.put(Grinder_SBox, Simulator.Grinder_s);
        comboboxMap.put(BladescaleHoneBox, Simulator.BladescaleHone);
        comboboxMap.put(ElementExploitBox, Simulator.elementExploit);


        // Setting up cmbboxes
        for(Entry<ComboBox, Skill> entry :comboboxMap.entrySet())
        {
            ComboBox cmb = entry.getKey();
            Skill skill = entry.getValue();
//            System.out.println(skill);
//            System.out.println(cmb);
            
            IntStream.rangeClosed(0, skill.getMax()).forEach(x -> cmb.getItems().add(x));
            cmb.setValue(0);

            skill.requiredProperty().bind(cmb.getSelectionModel().selectedItemProperty());
            skill.activeProperty().addListener(
                    (o)->{
                        if(!skill.isActive())
                            cmb.setValue(0);
                    }
            );
        }
        
//        NormalRapidUp.
//        Stream.of(NormalRapidUp, pierceUp, spreadUp, rapidFireUp)
//                .forEach(skill -> 
//                {
//                    skill.requiredProperty().bind(
//                    Bindings.and(
//                    textField.textProperty().isEqualTo(""),
//                    textField2.textProperty().isEqualTo("")));
//                });
        
        
        // Weapon
        weapon = new Weapon("weapon", 200, 0, 0, 0, 0, 0);
        SpinnerValueFactory<Integer> damageValueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 200);
        SpinnerValueFactory<Integer> affinityValueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(-30, 100, 0);
        weaponDamageSpinner.setValueFactory(damageValueFactory);
        weaponAffinitySpinner.setValueFactory(affinityValueFactory);
        weapon.getDamageProperty().bind(weaponDamageSpinner.valueProperty());
        weapon.getAffinityProperty().bind(weaponAffinitySpinner.valueProperty());
//        weaponDamageSpinner.valueProperty().addListener((observable) -> {
//            System.out.println(weapon.getDamage());
//        });
        for(int a = 0; a <= 1; a++)
        {
            for(int i = 0; i <= 1; i++)
            {
                for(int j = 0; j <= 2; j++)
                {
                    for(int k = 0; k <= 3; k++)
                    {
                        if(a + i + j + k > 3)
                            continue;
                        if(a == 1 && i == 1)
                            continue;
                        if((i == 1 || a == 1) && j >= 3)
                            continue;
                        weaponSlotBox.getItems().add(new Slot(a, i, j, k));
                        charmSkillSlotBox.getItems().add(new Slot(a, i, j, k));
                    }
                }
            }
        }
        
        weaponSlotBox.setValue(new Slot(0, 0, 0, 0));
        charmSkillSlotBox.setValue(new Slot(0, 0, 0, 0));
        weapon.getSlotProperty().bind(weaponSlotBox.valueProperty());
        
        // element
        Stream.of(
                Simulator.emptySkill,
                Simulator.fireAttack, 
                Simulator.thunderAttack, 
                Simulator.waterAttack, 
                Simulator.iceAttack, 
                Simulator.dragonAttack, 
                Simulator.blastAttack, 
                Simulator.paralysisAttack,
                Simulator.poisonAttack)
                .forEach(elementBox.getItems()::add);
        
        elementBox.setValue(elementBox.getItems().get(0));
        elementBox.valueProperty().addListener((observable) -> {
            elementLevelBox.getItems().clear();
            elementBox.getItems().stream().forEach(skill -> skill.setRequired(0));
            
            if(elementBox.valueProperty().get() == Simulator.emptySkill)
                elementLevelBox.getItems().add(0);
            else
            {
                IntStream.rangeClosed(1, elementBox.getValue().getMax()).forEach(x -> elementLevelBox.getItems().add(x));
            }
            elementLevelBox.setValue(elementLevelBox.getItems().get(0));
            elementBox.getValue().setRequired(elementLevelBox.getValue());
        });
        elementLevelBox.valueProperty().addListener((observable) -> {
            elementBox.getValue().setRequired(elementLevelBox.getValue());
        });
        
//        // Seriese skills
//        serieseSkillBox.getItems().add(Simulator.emptySkill);
//        Simulator.ALL_SERIESE_SKILLS.stream().forEach(serieseSkillBox.getItems()::add);
//        serieseSkillBox.setValue(serieseSkillBox.getItems().get(0));
//        serieseSkillBox.valueProperty().addListener((observable) -> {
//            serieseSkillLivelBox.getItems().clear();
//            serieseSkillBox.getItems().stream().forEach(skill -> skill.setRequired(0));
//            
//            if(serieseSkillBox.valueProperty().get() == Simulator.emptySkill)
//                serieseSkillLivelBox.getItems().add(0);
//            else
//            {
//                IntStream.rangeClosed(1, serieseSkillBox.getValue().getMax()).forEach(x -> serieseSkillLivelBox.getItems().add(x));
//            }
//            serieseSkillLivelBox.setValue(serieseSkillLivelBox.getItems().get(0));
//            serieseSkillBox.getValue().setRequired(serieseSkillLivelBox.getValue());
//        });
//        serieseSkillLivelBox.valueProperty().addListener((observable) -> {
//            serieseSkillBox.getValue().setRequired(serieseSkillLivelBox.getValue());
//        });
        
        // Charm setting
        Simulator.ALL_SKILLS.stream().forEach(charmSkill1Box.getItems()::add);
        charmSkill1Box.setValue(charmSkill1Box.getItems().get(0));
        charmSkill1Box.valueProperty().addListener((observable) -> {
            charmSkill1LevelBox.getItems().clear();
            if(charmSkill1Box.valueProperty().get() == Simulator.emptySkill)
                charmSkill1LevelBox.getItems().add(0);
            else
                IntStream.rangeClosed(1, charmSkill1Box.getValue().getMax()).forEach(x -> charmSkill1LevelBox.getItems().add(x));
            charmSkill1LevelBox.setValue(charmSkill1LevelBox.getItems().get(0));
        });
        
        Simulator.ALL_SKILLS.stream().forEach(charmSkill2Box.getItems()::add);
        charmSkill2Box.setValue(charmSkill2Box.getItems().get(0));
        charmSkill2Box.valueProperty().addListener((observable) -> {
            charmSkill2LevelBox.getItems().clear();
            if(charmSkill2Box.valueProperty().get() == Simulator.emptySkill)
                charmSkill2LevelBox.getItems().add(0);
            else
                IntStream.rangeClosed(1, charmSkill2Box.getValue().getMax()).forEach(x -> charmSkill2LevelBox.getItems().add(x));
            charmSkill2LevelBox.setValue(charmSkill2LevelBox.getItems().get(0));
        });
        
        charmData = FXCollections.observableArrayList();
        charmTable.itemsProperty().setValue(charmData);
        charmTable.setItems(charmData);
        charmSkill1Col.setCellValueFactory(new PropertyValueFactory("skill1"));
        charmSkill1LevelCol.setCellValueFactory(new PropertyValueFactory("skill1Level"));
        charmSkill2Col.setCellValueFactory(new PropertyValueFactory("skill2"));
        charmSkill2LevelCol.setCellValueFactory(new PropertyValueFactory("skill2Level"));
        charmSlotCol.setCellValueFactory(new PropertyValueFactory("slot"));

        
        
        sim = new Simulator(equipmentData);
        bestExpectation = new SimpleDoubleProperty(0);
        bestExpectation.set(0);
        equipmentData.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                synchronized(equipmentData)
                {
                    if(equipmentData.size() > 0)
                    {

                        bestExpectation.set(equipmentData.get(0).getExpectation());
                        Platform.runLater(() -> {
                            bestSeries.getData().add(new XYChart.Data<>(bestSeries.getData().size(), bestExpectation.get()));
                            worstSeries.getData().add(
                                    new XYChart.Data<>(
                                            worstSeries.getData().size(), 
                                            equipmentData.get(
                                                    equipmentData.size() - 1).getExpectation()
                                    )
                            );

                            avgSeries.getData().add(
                                    new XYChart.Data<>(avgSeries.getData().size(), 
                                    equipmentData
                                            .stream().map(e -> e.getExpectation())
                                            .mapToDouble(x -> x)
                                            .sum() / equipmentData.size()
                                    )
                            );
                            avgScoreSeries.getData().add(
                                    new XYChart.Data<>(avgScoreSeries.getData().size(), 
                                    equipmentData
                                            .stream().map(e -> e.getScore())
                                            .mapToInt(x -> x)
                                            .sum() / (double)equipmentData.size()
                                    )
                            );
                            firstScoreSeries.getData().add(
                                    new XYChart.Data<>(firstScoreSeries.getData().size(), 
                                    Collections.max(
                                            equipmentData, 
                                            (Equipment first, Equipment second) -> first.getScore() - second.getScore()
                                    ).getScore()
                                )
                            );
                            lastScoreSeries.getData().add(
                                    new XYChart.Data<>(firstScoreSeries.getData().size(), 
                                    Collections.min(
                                            equipmentData, 
                                            (Equipment first, Equipment second) -> first.getScore() - second.getScore()
                                    ).getScore()
                                )
                            );
                        });
                    }
                }
            }
        });
        
        
        // Progress bar
        sim.getProgressProperty().bindBidirectional(progressBar.progressProperty());
        sim.getSkipRateProperty().bindBidirectional(skipRateBar.progressProperty());
        SpinnerValueFactory<Integer> skipValueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(-20, 200, 55); //(int)(componentLimit * 6 * 1.5)
        skipRateScoreSpinner.setValueFactory(skipValueFactory);
        sim.getSkipValueProperty().bind(skipRateScoreSpinner.valueProperty());
        
        SpinnerValueFactory<Integer> cutValueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 10); //(int)(componentLimit * 6 * 1.5)
        cutScoreSpinner.setValueFactory(cutValueFactory);
        sim.getCutValueProperty().bind(cutScoreSpinner.valueProperty());

        
        helmList = FXCollections.observableArrayList(LoadHelmData("MHR_EQUIP_HEAD - 頭.tsv"));
        helmList.stream().forEach(armor -> sim.addHelm(armor));
        chestList = FXCollections.observableArrayList(LoadChestData("MHR_EQUIP_BODY - 胴.tsv"));
        chestList.stream().forEach(armor -> sim.addChest(armor));
        armList = FXCollections.observableArrayList(LoadArmData("MHR_EQUIP_ARM - 腕.tsv"));
        armList.stream().forEach(armor -> sim.addArm(armor));
        waistList = FXCollections.observableArrayList(LoadWaistData("MHR_EQUIP_WST - 腰.tsv"));
        waistList.stream().forEach(armor -> sim.addWaist(armor));
        legList = FXCollections.observableArrayList(LoadLegData("MHR_EQUIP_LEG - 脚.tsv"));
        legList.stream().forEach(armor -> sim.addLeg(armor));
        charmList = FXCollections.observableArrayList(LoadCharmData("MHR_EQUIP_CHARM.tsv"));
        charmList.stream().forEach(armor -> sim.addCharm(armor));
        
        sim.getCharms().stream().forEach(charmData::add);
//        sim.run();
        if(sim.getCharms().isEmpty())
            sim.addCharm(new Charm("charm", 0, 0, 0, 0));
        
        
        mysetData.addAll(loadMyset(sim));
        
        helmFixCmbBox.itemsProperty().setValue(helmList);
        chestFixCmbBox.itemsProperty().setValue(chestList);
        armFixCmbBox.itemsProperty().setValue(armList);
        waistFixCmbBox.itemsProperty().setValue(waistList);
        legFixCmbBox.itemsProperty().setValue(legList);
        charmFixCmbBox.itemsProperty().setValue(charmList);
        
        Stream
                .of(helmFixCmbBox, chestFixCmbBox, armFixCmbBox, waistFixCmbBox, legFixCmbBox, charmFixCmbBox)
                .forEach(
                        cmbbox -> {
                            cmbbox.getItems().add(0, null);
                            cmbbox.setValue(null);
                        }
                );

        
    }    
}
