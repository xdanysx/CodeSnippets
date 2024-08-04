using UnityEngine;

public enum ItemType
{
    Consumable,
    Equipment,
    Weapon,
    Other
}

public class Item : ScriptableObject
{
    public GameObject prefab;
    public ItemType type;
    public string itemName;
    public int weight;
}

public class OtherItem : Item
{
    public void Awake()
    {
        type = ItemType.Other;
    }
}

public class ConsumableObject : Item
{
    /*
    public Attribute effect;
    public int value;

    public void Awake()
    {
        type = ItemType.Consumable;
    }
    */
}

public class WeaponObject : Item
{

    public int damage;
    public float range;
    public float zoomfactor;
    public bool isMelee;

    public void Awake()
    {
        type = ItemType.Weapon;
    }

}

public class EquipmentObject : Item
{
    public enum EquipmentType
    {
        Helmet, Chestplate, Leggings, Shoes
    }
    public EquipmentType equipmentType;

    public void Awake()
    {
        type = ItemType.Equipment;
    }
}
