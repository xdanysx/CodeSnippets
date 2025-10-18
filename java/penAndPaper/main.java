import javax.swing.*;

import Entity.EntityTypes;

import java.util.function.Consumer;
import java.util.function.BiConsumer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Window {

    // GUI Code here
    // Instanzieren
    Window(){
        JFrame fenster = new JFrame(); // Objekt eines JFrames erstellen

		JPanel panel1 = new JPanel(); // Objekt eines JPanels erstellen
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		JButton button1 = new JButton();
		JTextField textField1 = new JTextField();
		JProgressBar progressBar1 = new JProgressBar();
		JSlider slider1 = new JSlider();
		JTable table1 = new JTable();
		JRadioButton radio1 = new JRadioButton();
		
		// Zuweisen
		panel1.setBackground(Color.red);
		panel1.setPreferredSize(new Dimension(400,300));
		panel1.setLayout(new BorderLayout());
		panel1.add(button1,BorderLayout.EAST);
		panel1.add(slider1,BorderLayout.SOUTH);
		
		panel2.setBackground(Color.green);
		panel2.setPreferredSize(new Dimension(400,300));
		panel2.setLayout(new GridLayout(1,2));
		panel2.add(textField1);
		panel2.add(table1);
		
		panel3.setBackground(Color.blue);
		panel3.setPreferredSize(new Dimension(400,300));
		panel3.setLayout(null);
		panel3.add(progressBar1);
		panel3.add(radio1);
		
		fenster.setTitle("Demonstration"); // Titel des Fensters einstellen
		fenster.setVisible(true); // Das Fenster sichtbar machen
		fenster.setResizable(true); // Es nicht ermoeglichen die Fenstergroesse einzustellen
		fenster.setSize(1000,500); // Groesse des Fensters einstellen
		fenster.setLocationRelativeTo(null); // Programm in der Mitte des Bildschirms positionieren
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Programm bei "x" schließen
		fenster.setLayout(new GridLayout(1,3));
		fenster.add(panel1);
		fenster.add(panel2);
		fenster.add(panel3);
		
		//fenster.pack();
    }
		
		

}

abstract class Entity{

    String name;
    int level = 0;
    int erfahrungsPunkte = 0;
    int momLeben = 0;
    int maxLeben = 0;
    enum EntityTypes {PLAYER, ENEMY};
    EntityTypes entityType;

    Inventory inventory;

    Skill[] skills = new Skill[4];

    List<Action> passiveActions = new ArrayList<>();

    public enum AttackIntention {HIT, LIGHTHIT, KRITHIT, AOEHIT, FLEE};
    AttackIntention attackIntention;
    public enum DefendStance {BLOCK, DODGE};
    DefendStance defendStance;

    Entity(){
        inventory = new Inventory("Rucksack",this);
    }

    int fistDamage(){
        int damage = skills[3].level + 5;
        return damage;
    }

    void heal(int p_HealAmount){
        momLeben += p_HealAmount;
        if(momLeben > maxLeben) momLeben = maxLeben;
    }

    private void applyDamage(int p_amount){
        if(actualStance == Entity.CombatStance.Block){
            block(p_amount);
        }else{
            dodge(p_amount);
        }
    }

    private void dodge(int p_damageAmount){
        // Geschicklichkeitslevel/Gegnerlevel * 100 Wahrscheinlichkeit auf Ausweichen => Kein Schaden
        if(Math.random() > skills[2].level/skills[2].maxLevel){
            System.out.println("Ausweichen fehlgeschlagen");
            takeDamage(p_damageAmount);
        }else{
            System.out.println("Ausgewichen");
        }
    }

    private void block(int p_damageAmount){
        // => Schaden wird halbiert
        takeDamage((int)(p_damageAmount/2));
    }

    private void takeDamage(int p_amount){
        if(p_amount > skills[2].level){
            if(p_amount > inventory.getArmorValue()){
                momLeben -= (p_amount - inventory.getArmorValue());
                System.out.println(name + " erleidet " + p_amount + " Schaden");
            }else{
                System.out.println(name + " hat keinen Schaden durch die Ruestung genommen!");
            }
        }else{
            System.out.println(name + " ist den Angriff ausgewichen!");
        }
        checkIfDead();
    }

    boolean checkIfDead(){
        if(momLeben <= 0) {
            die();
            return true;
        }else{
            return false;
        }
    }

    void die(){
        System.out.println(name +" ist gestorben.");
    }
}

class Enemy extends Entity {

    Enemy(String p_name, int p_level){
        super();
        level = p_level;
        skills[0] = new Skill("Vitalität", 5 + p_level * 2);
        skills[1] = new Skill("Intelligenz");
        skills[2] = new Skill("Geschicklichkeit", (int)(p_level / 2));
        skills[3] = new Skill("Stärke", 5 + p_level * 2);
        
        entityType = EntityTypes.ENEMY;
        name = p_name;
        maxLeben = 10 + p_level * 10 + (int)(Math.random() * 50);
        momLeben = maxLeben;
        erfahrungsPunkte = p_level * 10;
        inventory.gainGold(5 + 10 * level + (int)(Math.random() * 10));
        inventory.AddToInventory(new Armor((int)(p_level/4),Item.ItemTypes.HELMET));
        inventory.AddToInventory(new Armor((int)(p_level/4),Item.ItemTypes.CHESTPLATE));
        inventory.AddToInventory(new Armor((int)(p_level/4),Item.ItemTypes.PANTS));
        inventory.AddToInventory(new Armor((int)(p_level/4),Item.ItemTypes.SHOES));
    }


    @Override
    public String toString(){
        return  this.name +
                ": Level: " + this.level +
                ", Leben: (" + this.momLeben + "/" + this.maxLeben +
                "), Ruestung: " + inventory.getArmorValue() +
                ", Geschicklichkeit: " + skills[2].level +
                ", Staerke: " + skills[3].level + "\n";
    }
}

class Player extends Entity {

    int skillPunkte = 10;
    int energy = 3;
    Location currentLocation;

    Action showSkills = new Action("Skills ansehen", () -> {showSkills();});
    Action showStats = new Action("Status ansehen", () -> {showStats();});

    Player(String p_name){
        super();
        skills[0] = new Skill("Vitalität");
        skills[1] = new Skill("Intelligenz");
        skills[2] = new Skill("Geschicklichkeit");
        skills[3] = new Skill("Stärke");
        name = p_name;
        entityType = EntityTypes.PLAYER;
        inventory.gainGold(((int)(Math.random() * 30)+80));
        passiveActions.add(showSkills);
        passiveActions.add(showStats);
        this.maxLeben = 50 + skills[0].level * 10;
        heal(9000);
    }

    @Override
    public String toString(){
        return name + ": Level: " + level + ", Leben: (" + momLeben + "/" + maxLeben + ")";
    }     

    void checkLevelUp(){
        this.maxLeben = 50 + skills[0].level * 10;
        while(this.erfahrungsPunkte >= (1 + this.level * 100)){
            if(this.erfahrungsPunkte >= this.level * 100){
                this.erfahrungsPunkte -= this.level * 100;
                this.level++;
                this.skillPunkte++;
                heal(9999);
                System.out.println("Du bist jetzt auf Level " + level + " aufgestiegen und hast nun " + skillPunkte + " Skillpunkte übrig!");
            }
        }
    }
    
    void showStats(){
        System.out.println("Infos über " + name +":");
        System.out.println("Level: " + level);
        System.out.println("Erfahrungspunkte: " + erfahrungsPunkte + " / " + this.level * 100);
        System.out.println("Skillpunkte: " + skillPunkte);
        System.out.println("Leben: " + momLeben + " / " + maxLeben);
        System.out.println("Energie: " + energy);
        System.out.println("> [Enter]");
        Game.scanner.nextLine();
    }

    void gainXP(int xp){
        erfahrungsPunkte += xp;
        checkLevelUp();
    }

    boolean levelUpSkill(Skill skill){
        if(skill.LevelUp()){
            this.skillPunkte--;
            checkLevelUp();
            heal(9999);
            System.out.println("Geskilled!");
            return true;
        }else{
            System.out.println("Skillaufwertung nicht mehr möglich.");
            return false;
        }
    }

    void showSkills(){
        while(true){
            System.out.println("Deine " + skills.length + " Skills:");
            for(int i = 0; i < skills.length; i++) System.out.println(i + ": " + skills[i]);
            if(skillPunkte <= 0) {
                System.out.println("> [Enter]");
                Game.scanner.nextLine();
                break;
            }
            System.out.println("Skillpunkte übrig: " + skillPunkte + ", zum Aufwerten Nummer eingeben oder -1 zum Abbrechen");
            int choice = Game.scanner.nextInt();
            if(0 <= choice && choice < skills.length){
                levelUpSkill(skills[choice]);
            }
            if(choice == -1) break;
        }
    }

    void look(){
        System.out.println("Momentan befindest du dich am " + currentLocation);
        System.out.println("Du siehst in der Nähe befinden sich: ");
        currentLocation.showNeighbors();
        System.out.println("Enter drücken um zu fortsetzen.");
        Game.scanner.nextLine();
    }

    void changeLocation(Location p_location){
        currentLocation = p_location;
        if(currentLocation instanceof Dungeon) ((Dungeon)currentLocation).enter();
        System.out.println("Du hast den Ort gewechselt und befindest dich nun in "+ p_location.name);
    }

    void sleepTight(){
        // Erhöht Leben um 50% vom maxLeben + Tag vergeht, geht nur im Dorf
        int healAmount = (int)(maxLeben/2);
        heal(healAmount);
        energize(3+(int)(skills[3].level / 4));
        Main.game.nextDay();
        System.out.println("Du hast geschlafen und dich erhohlt, dein Leben hat sich um " + healAmount + " Punkte regeneriert.");
    }

    void sleepLight(){
        int healAmount = (int)(maxLeben/4);
        heal(healAmount);
        energize(3+(int)(skills[3].level / 4));
        Main.game.nextDay();
        System.out.println("Du hast geschlafen und dich erhohlt, dein Leben hat sich um " + healAmount + " Punkte regeneriert.");
    }

    void energize(int p_amount){
        energy += p_amount;
    }
}

class Inventory{

    Entity owner; // of this inventory
    String name;
    private int gold;
    private List<Item> items = new ArrayList<>();
    Action showInventory = new Action("Inventar ansehen", () -> {
        show();
        System.out.print("> [Enter]");
        Game.scanner.nextLine();
    });
    Action trashItem = new Action("Items wegwerfen", () -> {trashItem();});
    Action switchItem = new Action("Items anlegen", () -> {switchItem();});

    Inventory(String p_name, Entity p_Owner){
        owner = p_Owner;
        name = p_name;
        owner.passiveActions.add(showInventory);
        owner.passiveActions.add(trashItem);
        owner.passiveActions.add(switchItem);
    }

    void AddToInventory(Item p_item){
        p_item.setActive(false);
        items.add(p_item);
        System.out.println(p_item.name + " landet in deinem Rucksack.");
    }

    void AddToInventory(Weapon p_weapon){
        if(!checkIfItemTypeIsActive(p_weapon.itemTyp)){
            p_weapon.setActive(true);
            System.out.println(p_weapon.name + " wird ausgerüstet.");
        }
        else{
            p_weapon.setActive(false);
            System.out.println(p_weapon.name + " landet in deinem Rucksack.");
        }
        items.add(p_weapon);
    }

    void AddToInventory(Armor p_armor){
        if(!checkIfItemTypeIsActive(p_armor.itemTyp)){
            p_armor.setActive(true);
            System.out.println(p_armor.name + " wird ausgerüstet.");
        }
        else{
            p_armor.setActive(false);
            System.out.println(p_armor.name + " landet in deinem Rucksack.");
        }
        items.add(p_armor);
    }

    boolean checkIfItemTypeIsActive(Item.ItemTypes p_itemType){
        boolean variable = false;
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).itemTyp == p_itemType) variable = true;
        }
        return variable;
    }

    Item getItem(Item.ItemTypes p_ItemType){
        Item variable = null;
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).itemTyp == p_ItemType && checkIfItemTypeIsActive(p_ItemType)) variable = items.get(i);
        }
        return variable;
    }

    void sellItem(){
        if(items.size() == 0){
            System.out.println("Du hast nichts im Inventar, dass du verkaufen kannst.");
            System.out.print("> [Enter]");
            Game.scanner.nextLine();
            return;
        }
        show();
        if(((Player)owner).currentLocation instanceof Village){
            System.out.println("Welches Item möchtest du verkaufen? (Gib die Nummer ein oder -1 zum Beenden)");
            System.out.print("> ");
            int index = Game.scanner.nextInt();
            Game.scanner.nextLine();
            if(0 <= index && index < items.size() && items.get(index) != null){
                ((Village)((Player)owner).currentLocation).Buy(items.get(index));
                items.remove(index);
            }else{
                System.out.println("Ungültiger Index!");
            }
        }else{
            System.out.println("Du kannst hier nichts verkaufen, da du dich nicht in einem Shop befindest.");
        }
    }

    void trashItem(){
        if(items.size() == 0){
            System.out.println("Du hast nichts im Inventar, dass du entfernen kannst.");
            System.out.print("> [Enter]");
            Game.scanner.nextLine();
            return;
        }
        show();
        System.out.println("Welches Item möchtest du entfernen? (Gib die Nummer ein oder -1 zum Beenden)");
        System.out.print("> ");
        int index = Game.scanner.nextInt();
        Game.scanner.nextLine();
        if(0 <= index && index < items.size()){
            System.out.println(items.get(index).name + " aus dem Inventar entfernt.");
            items.remove(index);
            System.out.print("> [Enter]");
            Game.scanner.nextLine();
        }
    }

    void giveItem(Item p_item, Entity p_entity){

    }

    void switchItem(){

    }

    void show (){
        System.out.println(this + " von " + owner.name +" beinhaltet folgende Items.");
        System.out.println("Gold: " + gold);
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i) + ") " + items.get(i) + (items.get(i).isActive == true ? " Aktiv": " Nicht aktiv"));
        }
    }

    boolean changeGoldAmount(int p_Amount){
        if(gold + p_Amount >= 0){
            gold += p_Amount;
            return true;
        }else{
            return false;
        } 
    }

    int getGold(){
        return gold;
    }

    void gainGold(int p_amount){
        gold += p_amount;
    }

    int getArmorValue(){
        int armorValue = 0;
        for(int i = 0; i < items.size(); i++){
            if((items.get(i).itemTyp == Item.ItemTypes.HELMET || items.get(i).itemTyp == Item.ItemTypes.CHESTPLATE ||  items.get(i).itemTyp == Item.ItemTypes.PANTS ||  items.get(i).itemTyp == Item.ItemTypes.SHOES) && checkIfItemTypeIsActive(items.get(i).itemTyp)) armorValue += items.get(i).getValue();
        }
        return armorValue;
    }

    Weapon getWeapon(){
        Weapon placeHolder = null;
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).isActive && items.get(i).itemTyp == Item.ItemTypes.WEAPON) placeHolder = (Weapon)items.get(i);
        }
        return placeHolder;
    }

    @Override
    public String toString() {
        return name + " von " + owner.name;
    }
}

class Skill {
    String name;
    int level = 0;
    int maxLevel = 20;

    Skill(String p_name){
        this.name = p_name;
    }

    Skill(String p_name, int p_level){
        this.name = p_name;
        level = p_level;
    }

    boolean LevelUp(){
        if(level < maxLevel && Game.player.skillPunkte > 0){
            this.level++;
            return true;
        }else{
            return false;
        }
    }

    @Override 
    public String toString(){
        return name + ": Level: (" + level + "/" + maxLevel + ")";
    }
}

class Combat {

    int roundnumber = 1;

    Player player;
    private List<Entity> enemies = new ArrayList<>();
    private List<Action> intentions = new ArrayList<>();
    Action aoeHit = new Action("Flächenschaden", () -> {player.actualIntention = Entity.AttackIntention.AOEHIT;});
    Action kritHit = new Action("Kritischer Schlag", () -> {player.actualIntention = Entity.AttackIntention.KRITHIT;});
    Action hit = new Action("Normaler Schlag", () -> {player.actualIntention = Entity.AttackIntention.HIT;});
    Action lightHit = new Action("2 Leichte Schläge", () -> {player.actualIntention = Entity.AttackIntention.LIGHTHIT;});
    Action flee = new Action("Flüchten", () -> {
        flee();
        player.actualIntention = Entity.AttackIntention.FLEE;
    });
    Action dodge = new Action("Angriff ausweichen", () -> {player.defendStance = Entity.DefendStance.DODGE;});
    Action block = new Action("Angriff blockieren", () -> {player.defendStance = Entity.DefendStance.BLOCK;});
    boolean playerDidFlee = false;

    Combat(int p_Level){
        player = Game.player;
        enemies.add(new Enemy("Goblin", p_Level));
        CombatHandler();
    }

    void ShowAllFighters(){
        System.out.println(player);
        System.out.println("VS.");
        for (Entity enemy : enemies) {
            System.out.println(enemy);
        }
    }

    void CombatHandler(){
        
        System.out.println("Ein Kampf beginnt!");
        ShowAllFighters();
        // Bestimme Initiativ-Reihenfolge (false = Spieler zuerst, true = Gegner zuerst)
        // boolean enemyFirst = Math.random() < 0.5;
        
        while(player.momLeben > 0 && !checkAllPlayersDead() && !playerDidFlee){
            System.out.println("Runde " + roundnumber + ":");
            ShowAllFighters();
            if(roundnumber % 2 == 1){
                // Spieler greift an
                playerAttackMenu();
                // Gegner verteigt
                enemiesDefend();
                //
            }else{
                // Gegner greift an
                enemiesAttack();
                // Spieler verteidigt
                playerDefendMenu();
            }
            roundResult();
            roundnumber++; // nächste Runde rufen
        }
        // Endergebnis
        if(playerDidFlee){
            System.out.println("Du bist dem Kampf erfolgreich entflohen.");
        }else{
            System.out.println("Du hast den Kampf gewonnen!");
        }
    }

    void playerAttackMenu(){
        intentions.clear();
        intentions.add(fistHit);
        if(player.inventory.getWeapon() != null){
            if(player.inventory.getWeapon().canLightHit) intentions.add(lightHit);
            if(player.inventory.getWeapon().canKrit) intentions.add(kritHit);
            if(player.inventory.getWeapon().canAOE) intentions.add(aoeHit);
            intentions.add(hit);
        }
        System.out.println("Was ist deine Intention?");
        // Intentionen auflisten
        for(int i = 0; i < intentions.size(); i++){
            System.out.println(i + ") " + intentions.get(i).name);
        }
        System.out.println("Gebe eine Zahl ein: ");
        System.out.print("> ");
        int choice = Game.scanner.nextInt();
        if(0 <= choice && choice < intentions.size()){
            
        }
        
        System.out.println("Gegen welchen Gegner möchtest du diese Aktion ausüben");
        for(int i = 0; i < enemies.size(); i++){
            System.out.println(i + ") " + enemies.get(i));
        }
        System.out.println("Gebe eine Zahl ein: ");
        System.out.print("> ");
        choice = Game.scanner.nextInt();
        if(0 <= choice && choice < intentions.size()){
            
        }
    }

    void playerDefendMenu(){
        intentions.clear();
        intentions.add(block);
        intentions.add(dodge);
        intentions.add(flee);
        System.out.println("Was ist deine Intention?");
        for(int i = 0; i < intentions.size(); i++){
            System.out.println(i + ") " + intentions.get(i).name);
        }
        System.out.println("Gebe eine Zahl ein: ");
        System.out.print("> ");
        int choice = Game.scanner.nextInt();
        if(0 <= choice && choice < intentions.size()){
            
        }
        
    }

    void enemiesAttack(){
        int random = 0;
        for(int i = 0; i < enemies.size(); i++){
            random = (int)(Math.random()*Entity.AttackIntention.values().length);
            switch (random) {
                case 0:
                    enemies.get(i).attackIntention = Entity.AttackIntention.HIT;
                    break;
                case 1:
                    enemies.get(i).attackIntention = Entity.AttackIntention.KRITHIT;
                    break;
                case 2:
                    enemies.get(i).attackIntention = Entity.AttackIntention.LIGHTHIT;
                    break;
                case 3:
                    enemies.get(i).attackIntention = Entity.AttackIntention.AOEHIT;
                    break;
                default:
                    break;
            }
        }
    }

    void enemiesDefend(){
        for(int i = 0; i < enemies.size(); i++){
            if(Math.random() > 0.5){
                enemies.get(i).defendStance = Entity.DefendStance.BLOCK;
            }else{
                enemies.get(i).defendStance = Entity.DefendStance.DODGE;
            }
        }
    }

    void roundResult(){
        // Intentionen auswerten
        // Ergebnis ermitteln
    }

    void aoeDamage(Entity p_attacker, List<Entity> p_entities){
        int damage = (int)(overallDamage(p_attacker)/p_entities.size());
        for(int i = 0; i < p_entities.size(); i++){
            applyDamage(p_entities.get(i), damage);
            System.out.println(p_entities.get(i).name + " um " + damage + " LP verletzt."); 
        }
    }

    void doLightHit(Entity p_attacker, Entity p_defender1, Entity p_defender2){
        int actualDamage = (int)(overallDamage(p_attacker)/2);
        applyDamage(p_defender1, actualDamage);
        applyDamage(p_defender2, actualDamage);
        System.out.println("Du hast " + p_defender1.name + " und " + p_defender2.name + " um " + actualDamage + " LP verletzt."); 
    }

    void doHit(Entity p_attacker, Entity p_defender){
        int actualDamage = overallDamage(p_attacker);
        applyDamage(p_defender, actualDamage);
        System.out.println("Du hast " + p_defender.name + " um " + actualDamage + " LP verletzt."); 
    }

    void doKritHit(Entity p_attacker, Entity p_defender){
        System.out.println("Wie Stark möchtest du ausholen? (Gebe die Wahrscheinlichkeit als Zahl von 1-100 in % an)");
        System.out.println("Je niedriger die Wahrscheinlichkeit desto stärker der Schlag");
        System.out.print("> ");
        int index = Game.scanner.nextInt();
        if(Math.random() < (index/100)){
            int actualDamage = p_attacker.fistDamage() + (int)((100/index)*p_attacker.inventory.getWeapon().getDamage());
            applyDamage(p_defender, actualDamage);
            System.out.println("Die Attacke war erfolgreich! Du hast " + p_defender.name + " um " + actualDamage + " LP verletzt!"); 
        }else{
            System.out.println("Die Attacke ist Misslungen!");
        }
    }

    void flee(){
        System.out.println("Du versuchst dem Kampf zu entfliehen.");
        playerDidFlee = true;
    }

    private int overallDamage(Entity p_Entity){
        int damage = 0;
        damage += p_Entity.fistDamage();
        damage += p_Entity.inventory.getWeapon().getDamage();
        return damage;
    }

    boolean checkAllPlayersDead(){
        boolean variable = false;
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).checkIfDead()) variable = true;
        }
        return variable;
    }
}

class Item{

    String name;
    protected int cost; // in Gold
    int level = 0; // auch level requirement
    protected int wert; // ruestungswert bei ruestungen, heilwert bei potions
    public enum ItemTypes {WEAPON, HELMET, CHESTPLATE, PANTS, SHOES, POTION, MISC};
    ItemTypes itemTyp;
    boolean isActive = false; // Wenn nicht aktiv dann auch nicht für Kämpfe genutzt

    Item(int p_Level){
        if(p_Level < 0) this.level = 0;
        else this.level = p_Level;
        cost = 11 + this.level * 20 + (int)(Math.random() * 20) - 10 + this.level;
    }

    int getCost(){
        return cost;
    }

    int getValue(){
        return wert;
    }

    void setActive(boolean p_bool){
        isActive = p_bool;
    }

    boolean isActive(){
        return isActive;
    }

    @Override 
    public String toString(){
        return name + ": (Level: " + level + " Wert: "+ wert + " Kosten: " + cost + ")";
    }
}

class Weapon extends Item {

    //private List<Action> abilities = new ArrayList<>();
    List<Action> combatActions = new ArrayList<>();

    boolean canAOE = false;
    boolean canKrit = false;
    boolean canLightHit = false;
    boolean canDOT = false;

    int baseDamage;
    int w6amount;
    int w20amount;

    Weapon(int p_Level) {
        super(p_Level);
        if(Math.random() > 0.5){
            name = "Schwert und Schild";
        }else{
            name = "Bogen";
        }
        itemTyp = ItemTypes.WEAPON;
        damageCalculator();
        canAOE = Math.random() > 0.5;
        canKrit = Math.random() > 0.5;
        canLightHit = Math.random() > 0.5;
        canDOT = Math.random() > 0.5;

    }

    void damageCalculator(){
        int punkte = level;
        while(punkte >= 0){
            double zufallsZahl = Math.random();
            if(zufallsZahl < 0.33){
                if(punkte > 20){
                    w20amount++;
                    punkte -= 20;
                }
            }
            else if(zufallsZahl < 0.66){
                if(punkte > 6){
                    w6amount++;
                    punkte -= 6;
                }
            }
            else {
                baseDamage++;
                punkte--;
            }
        }
    }

    int getDamage(){
        int aktuellerSchaden = baseDamage;
        for(int i = 0; i < w6amount; i++){
            aktuellerSchaden += (int)(Math.random() * 6) + 1;
        }
        for(int i = 0; i < w20amount; i++){
            aktuellerSchaden += (int)(Math.random() * 20) + 1;
        }
        return aktuellerSchaden;
    }

    @Override public String toString(){
        return this.name + ": (Level " + this.level + ", Grundschaden: " + baseDamage + ",  " +  w6amount + "x W6 + " + w20amount + "x W20) - Kosten: " + cost;
    }
}

class Armor extends Item {

    ItemTypes[] ruestungstypen = {ItemTypes.HELMET, ItemTypes.CHESTPLATE, ItemTypes.PANTS, ItemTypes.SHOES};

    Armor(int p_Level) {
        super(p_Level);
        int zufallsZahl = (int)(Math.random() * ruestungstypen.length);
        switch(ruestungstypen[zufallsZahl]){
            case HELMET:
                name = "Helm";
                break;
            case CHESTPLATE:
                name = "Harnisch";
                break;
            case PANTS:
                name = "Hose";
                break;
            case SHOES:
                name = "Schuhe";
                break;
            default:
                name = "Fehler";
                break;
        }
        itemTyp = ruestungstypen[zufallsZahl];
        wert = 3 - (int)(Math.random() * 2) + (this.level);
    }

    Armor(int p_level, Item.ItemTypes p_ItemTypes){
        super(p_level);
        switch(p_ItemTypes){
            case HELMET:
                name = "Helm";
                break;
            case CHESTPLATE:
                name = "Harnisch";
                break;
            case PANTS:
                name = "Hose";
                break;
            case SHOES:
                name = "Schuhe";
                break;
            default:
                name = "Fehler";
                break;
        }
        wert = 3 - (int)(Math.random() * 2) + (this.level);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return name + ": (Level: " + level + ", Rüstungswert: "+ wert + ") - Kosten: " + cost;
    }
}

class Potion extends Item {

    Potion(int p_Level) {
        super(p_Level);
        itemTyp = ItemTypes.POTION;
        this.name = "Heiltrank";
        this.wert = (int)(Math.random() * 20) + level * 10;
    }

    void healThroughPotion(Entity p_entity){
        p_entity.heal(getValue());
        System.out.println(p_entity.name + " verwendet einen Heiltrank.");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return name + ": (Level: " + level + ", Heilwert: "+ wert + ") - Kosten: " + cost;
    }
}

class Location {
    String name;
    String description;
    int level;
    enum LocationTypes {TOWN, DUNGEON, WILDERNESS};
    LocationTypes locationType;
    List<Location> neighbors = new ArrayList<>();
    List<Action> actions = new ArrayList<>();
    int maxWegPunkte;
    int aktuellerWegpunkt = 0;
    int wegPunktGesaeubert = 0;

    Location(String p_name, String p_description, int p_level){
        this.level = p_level;
        this.name = p_name;
        this.description = p_description;
        actions.addAll(Game.player.passiveActions);
        actions.add(new Action("Dich umschauen", () -> {showLocation();}));
        actions.add(new Action("Draußen ausruhen.", () -> {Game.player.sleepLight();}));
    }

    void showNeighbors(){
        
    }

    void showMenu(){
        System.out.println("Was möchtest du tun?");
        for (int i = 0; i < actions.size(); i++) {
            System.out.println((i) + ") " + actions.get(i).name);
        }

        System.out.print("> ");
        int choice = Game.scanner.nextInt();
        Game.scanner.nextLine(); // Rest der Zeile wegwerfen

        if (0 <= choice && choice < actions.size()) {
            actions.get(choice).execute();
        }
    }

    void addNeighbor(Location p_Location){
        if(neighbors.size() == 0) actions.add(new Action("Ort wechseln", () -> {showTravel();}));
        if(p_Location.neighbors.size() == 0) p_Location.actions.add(new Action("Ort wechseln", () -> {p_Location.showTravel();}));
        neighbors.add(p_Location);
        p_Location.neighbors.add(this);
    }

    void showTravel(){
        System.out.println("Wo möchtest du hin?");
        System.out.println("In der Nähe befinden sich: ");
        for(int i = 0; i < neighbors.size(); i++){
            System.out.println(i + ") " + neighbors.get(i));
        }
        System.out.println("-1) Hier bleiben.");
        System.out.print("> ");
        int choice = Game.scanner.nextInt();
        Game.scanner.nextLine(); // Rest der Zeile wegwerfen
        if (0 <= choice && choice < neighbors.size()) {
            Game.player.changeLocation(neighbors.get(choice));
        }
    }

    void showLocation(){
        System.out.println("Du befindest dich momentan in: " + name + ". " + description);
        System.out.print("> [Enter]");
        Game.scanner.nextLine();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.name + ": " + this.description + ", Level: " + level + ", Erkundet: " + wegPunktGesaeubert + "/" + maxWegPunkte;
    }

}

class Dungeon extends Location{

    double wahrscheinlichkeit; // Auf gegner zu treffen
    Item belohnungsItem;

    Dungeon(String p_name, String p_description, int p_level){
        super(p_name, p_description, p_level);
        maxWegPunkte = 5 + (int)(p_level/8);
        wahrscheinlichkeit = (double)((p_level-40.0)/80.0)+0.5;
        actions.add(new Action("Ort erkunden", () -> {walk();}));
    }

    void enter(){
        aktuellerWegpunkt = 0;
    }

    boolean walk(){
        if(wegPunktGesaeubert < maxWegPunkte){
            aktuellerWegpunkt++;
            System.out.println("Du erkundest weiter die Gegend...");
            double zufallsZahl = Math.random();
            if(zufallsZahl < wahrscheinlichkeit){
                // Kampf
                //System.out.println(zufallsZahl + " < " + wahrscheinlichkeit);
                System.out.println("Monster kommen hervor!");
                new Combat(level);
                
            }else{
                System.out.println("Alles scheint ruhig an diesem Ort zu sein. Du hast nichts gefunden.");
            }
            wegPunktGesaeubert++;
            return true;
        }else{
            System.out.println("Du hast die ganze Gegend bereits erkundet. Hier gibt es nichts mehr zu finden.");
            return false;
        }
    }
}

class Village extends Location{
    // Liste mit allen Items im Shop
    private List<Item> items = new ArrayList<>();

    Village(String p_name, String p_description, int p_level) {
        super(p_name, p_description, p_level);
        actions.add(new Action("Geschäft betreten.",() -> {buyMenu();}));
        actions.add(new Action("In einer Gaststädte ruhen.", () -> {Game.player.sleepTight();}));
        wegPunktGesaeubert = maxWegPunkte;

        for (int i = (level-1) ; i <= level + 2; i++) {
            items.add(new Weapon(i)); // Beispiel: Waffen hinzufügen
        }
        for (int i = (level-1) ; i <= level + 3; i++) {
            items.add(new Armor(i)); // Beispiel: Waffen hinzufügen
        }
        for (int i = (level-1); i <= level + 1; i++) {
            items.add(new Potion(i)); // Beispiel: Waffen hinzufügen
        }
    }

    void buyMenu() {
        while(true){
            System.out.println("\nWillkommen im Laden! (Level: " + level +") Hier sind die verfügbaren Items: Dein Gold: " + Game.player.inventory.getGold());
            for (int i = 0; i < items.size(); i++) {
                System.out.println(i + ") " + items.get(i));
            }
            System.out.println("-1) Geschäft verlassen.");
            System.out.println("-2) Items verkaufen.");
            System.out.print("> ");
            int choice = Game.scanner.nextInt();
            Game.scanner.nextLine();
            if (0 <= choice && choice < items.size()) {
                Sell(items.get(choice));   // hier List statt Array
            } else if (choice == -1) {
                System.out.println("Danke für deinen Besuch!");
                break;
            } else if (choice == -2) {
                Game.player.inventory.sellItem();
                break;
            } else {
                System.out.println("Ungültige Eingabe!");
            }
        }
    }

    // Kaufen und Verkaufen aus der sicht des Shops 
    void Buy(Item p_item){
        // Verkaufen (egal ob Waffe, Rüstung, Trank, ...)
        Game.player.inventory.changeGoldAmount(p_item.getCost());
        System.out.println(p_item + " verkauft!");
        System.out.print("> [Enter]");
        Game.scanner.nextLine();
    }

    void Sell(Item p_item){
        // Kaufen (egal ob Waffe, Rüstung, Trank, ...)
        if (Game.player.inventory.changeGoldAmount(-p_item.getCost())) {
            System.out.println(p_item + " gekauft!");
            Game.player.inventory.AddToInventory(p_item);
            items.remove(p_item); // aus dem Shop entfernen
        } else {
            System.out.println("Dein Geld reicht für " + p_item + " nicht aus!");
        }
        System.out.print("> [Enter]");
        Game.scanner.nextLine();
    }
}

class Arena extends Location{
    
    Arena(String p_name, String p_description, int p_level){
        super(p_name, p_description, p_level);
    }
}

class Carnival extends Location {

    Carnival(String p_name, String p_description, int p_level){
        super(p_name, p_description, p_level);
    }
}

class Event 
{
    String name;
    String description;
    List<Action> actions = new ArrayList<>();

    Event(String p_name, String p_description, List<Action> p_Actions){
        name = p_name;
        description = p_description;
    }

}
 
class Action {

    String name;
    Runnable noArgEffect;
    Consumer<Entity> oneArgEffect;
    BiConsumer<Entity, Entity> twoArgEffect;

    // Konstruktor ohne Parameter
    Action(String p_name, Runnable p_effect) {
        this.name = p_name;
        this.noArgEffect = p_effect;
    }

    // Konstruktor mit 1 Parameter
    Action(String p_name, Consumer<Entity> p_effect) {
        this.name = p_name;
        this.oneArgEffect = p_effect;
    }

    // Konstruktor mit 2 Parametern
    Action(String p_name, BiConsumer<Entity, Entity> p_effect) {
        this.name = p_name;
        this.twoArgEffect = p_effect;
    }

    // Ausführen ohne Parameter
    void execute() {
        if (noArgEffect != null) noArgEffect.run();
        else System.out.println("Diese Aktion benötigt Ziele!");
    }

    // Ausführen mit 1 Parameter
    void execute(Entity e1) {
        if (oneArgEffect != null) oneArgEffect.accept(e1);
        else if (noArgEffect != null) noArgEffect.run();
        else System.out.println("Keine passende Variante für 1 Ziel!");
    }

    // Ausführen mit 2 Parametern
    void execute(Entity e1, Entity e2) {
        if (twoArgEffect != null) twoArgEffect.accept(e1, e2);
        else if (oneArgEffect != null) oneArgEffect.accept(e1);
        else if (noArgEffect != null) noArgEffect.run();
        else System.out.println("Keine Wirkung definiert!");
    }

}

class Game {
    public static Scanner scanner = new Scanner(System.in);

    // Erstelle Spieler
    public static Player player;

    // Manager und Spielhandler
    int day = 1;

    // Set up world
    Location strand;
    Village dorf;
    Dungeon wald;
    Dungeon felder;
    Dungeon burg;
    Dungeon friedhof;
    Dungeon hoehle;
    Dungeon sumpf;
    Dungeon observatorium;
    Dungeon berg;
    Dungeon schloss;

    
    Action changeLocation = new Action("Ort wechseln", () -> {
        if(player.currentLocation instanceof Dungeon && incrementActionCounter(1)){
            ((Dungeon)player.currentLocation).walk();
            scanner.nextLine();
        }
    });
    
  
    
    
    

    void Setup(){

        System.out.println("Wie soll dein Charakter lauten?");
        System.out.print("> ");
        String input = scanner.nextLine();
        player = new Player(input);

        strand = new Location("Der Strand", "Ein klarer schöner Strand mit hellem Sand.",0);
        dorf = new Village("Ein kleines Dorf", "Ein kleines Dorf mit einem Geschäft.", 0);
        wald = new Dungeon("Der Wald", "Ein ruhig aussehender Wald",5);
        felder = new Dungeon("Die Felder", "Felder in denen was angebaut wurde, schöne Landschaft.",10);
        burg = new Dungeon("Die Burg","Eine alte aber noch gut erhaltene Burg.",20);
        friedhof = new Dungeon("Der Friedhof", "Ein gruseliger Ort, nachts eher vermeiden.",30);
        hoehle = new Dungeon("Die Tropfsteinhöhle","Es tropft..",40);
        sumpf = new Dungeon("Die Sümpfe","Eigenartige Geräusche sind dort zu hören",50);
        observatorium = new Dungeon("Das Observatorium","Ein interessantes Gebäude.",60);
        berg = new Dungeon("Der Berg","Von oben hat man einen schönen Ausblick von dort oben.",70);
        schloss = new Dungeon("Das Schloss", "Ein riesiges Schloss an der Klippe des Berges.",80);

        strand.addNeighbor(dorf);
        strand.addNeighbor(wald);
        wald.addNeighbor(felder);
        felder.addNeighbor(burg);
        felder.addNeighbor(friedhof);
        friedhof.addNeighbor(hoehle);
        friedhof.addNeighbor(sumpf);
        sumpf.addNeighbor(observatorium);
        sumpf.addNeighbor(berg);
        berg.addNeighbor(schloss);

        player.currentLocation = strand;
        
    }

    Game(){
        Setup();

        
        
        while(player.momLeben > 0){
            player.currentLocation.showMenu();
        }
        
        scanner.close();

    }

    // Player can play

    // Win?
    /* 
    void ExploringInterface(){

        

        if(player.momLeben > 0){
            System.out.println("Was möchtest du tun, " + player.name+ "?");
            for(int i = 0; i < aktionen.size(); i++)System.out.println(i + ": " + aktionen.get(i));

            int choice = Game.scanner.nextInt();

            switch (choice) {
                case 0:
                    player.look();
                    break;
                case 1:
                    if(player.currentLocation instanceof Dungeon && incrementActionCounter(1)){
                        ((Dungeon)player.currentLocation).walk();
                        scanner.nextLine();
                    }
                    break;
                case 2:
                    if(player.travel() && incrementActionCounter(3)) {
                        System.out.println("Du bist nach " + player.currentLocation + " gereist.");
                    }
                    break;
                case 3:
                    player.showStats();
                    break;
                case 4:
                    player.inventory.show();
                    break;
                case 5:
                    player.showSkills();
                    break;
                case 6:
                    player.sleep();
                    nextDay();
                    break;
                default:
                    System.out.println("Ungültige Eingabe!");
                    break;
            }
            scanner.nextLine();
        }else{
            System.out.println("Deine Kraft geht zur neige - du musst dich ausruhen.");
            System.out.println("Enter drücken um fortzusetzen.");
            scanner.nextLine();
        }
    }
    */

    void nextDay(){
        day++;
        System.out.println("Du bist nun an Tag " + day + " deiner Reise.");
        System.out.println("Enter drücken um zu fortsetzen.");
        System.out.print("> ");
        scanner.nextLine();
    }

    boolean incrementActionCounter(int p_amount){
        if(player.energy - p_amount >= 0){
            player.energy -= p_amount;
            return true;
        }else{
            System.out.println("Du hast nicht genug Energie um dies zu tun. Du solltest dich ausruhen, damit du mehr Energie hast.");
            scanner.nextLine();
            return false;
        }
        
    }
}

class Main {
    
    public static Game game;
    public static void main(String[] args) {
        
        System.out.print("Willkommen im Pen and Paper abenteuer!\n");
        game = new Game();
        System.out.print("Vielen Dank fürs Spielen!");
    }    

}
