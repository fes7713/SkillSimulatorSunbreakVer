/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author fes77
 */
public class Slot implements Decoratable{
    private final int slot4;
    private final int slot3;
    private final int slot2;
    private final int slot1;

    public Slot(int slot4, int slot3, int slot2, int slot1){
        this.slot4 = slot4;
        this.slot3 = slot3;
        this.slot2 = slot2;
        this.slot1 = slot1;
    }

    @Override
    public int getSlot4() {
        return slot4;
    }
    
    @Override
    public int getSlot3() {
        return slot3;
    }

    @Override
    public int getSlot2() {
        return slot2;
    }

    @Override
    public int getSlot1() {
        return slot1;
    }

    public boolean betterThan(Slot slot)
    {
        if(slot.getSlot4() + slot.getSlot3() + slot.getSlot2() + slot.getSlot1() <= slot4 + slot3 + slot2 + slot1)
        {
            if(slot.getSlot4() + slot.getSlot3() + slot.getSlot2() <= slot4 + slot3 + slot2)
            {
                if(slot.getSlot4() + slot.getSlot3() <= slot4 + slot3)
                {
                    if(slot.getSlot4() <= slot4)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean betterThan(int a_slot4, int a_slot3, int a_slot2, int a_slot1)
    {
        if(a_slot4 + a_slot3+ a_slot2 + a_slot1 <= slot4 + slot3 + slot2 + slot1)
        {
            if(a_slot4 + a_slot3 + a_slot2 <= slot4 + slot3 + slot2)
            {
                if(a_slot4 + a_slot3 <= slot4 + slot3)
                {
                    if(a_slot4 <= slot4)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean worseThan(Slot slot)
    {
        if(slot.getSlot4() + slot.getSlot3() + slot.getSlot2() + slot.getSlot1() >= slot4 + slot3 + slot2 + slot1)
        {
            if(slot.getSlot4() + slot.getSlot3() + slot.getSlot2() >= slot4 + slot3 + slot2)
            {
                if(slot.getSlot4() + slot.getSlot3() >= slot4 + slot3)
                {
                    if(slot.getSlot4() >= slot4)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean worseThan(int a_slot4, int a_slot3, int a_slot2, int a_slot1)
    {
        if(a_slot4 + a_slot3+ a_slot2 + a_slot1 >= slot4 + slot3 + slot2 + slot1)
        {
            if(a_slot4 + a_slot3 + a_slot2 >= slot4 + slot3 + slot2)
            {
                if(a_slot4 + a_slot3 >= slot4 + slot3)
                {
                    if(a_slot4 >= slot4)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public Slot add(Slot slot)
    {
        return new Slot(slot4 + slot.getSlot4(), slot3 + slot.getSlot3(), slot2 + slot.getSlot2(), slot1 + slot.getSlot1());
    }
    
    public Slot subtract(Slot slot)
    {
        if(slot.worseThan(slot4, slot3, slot2, slot1))
        {
            int newSlot1 = Math.max(
                    slot1 - slot.getSlot1(), 
                    0);
            int newSlot2 = Math.max(
                    slot1 + slot2 - (slot.getSlot1() + slot.getSlot2()) - newSlot1,
                    0);
            int newSlot3 = Math.max(
                    slot1 + slot2 + slot3 - (slot.getSlot1() + slot.getSlot2() + slot.getSlot3()) - newSlot1 - newSlot2, 
                    0);
            int newSlot4 = Math.max(
                    slot1 + slot2 + slot3 + slot4 - (slot.getSlot1() + slot.getSlot2() + slot.getSlot3() + slot.getSlot4()) - newSlot1 - newSlot2 - newSlot3, 
                    0);
            
            return new Slot(newSlot4, newSlot3, newSlot2, newSlot1);
        }
        return null;
    }
    
    public Slot subtract(int a_slot4, int a_slot3, int a_slot2, int a_slot1)
    {
        return subtract(new Slot(a_slot4, a_slot3, a_slot2, a_slot1));
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Integer> numStack = new ArrayList();
        IntStream.range(0, slot4).forEach(x -> numStack.add(4));
        IntStream.range(0, slot3).forEach(x -> numStack.add(3));
        IntStream.range(0, slot2).forEach(x -> numStack.add(2));
        IntStream.range(0, slot1).forEach(x -> numStack.add(1));
        
        for(int i = 0; i < numStack.size(); i++)
        {
            sb.append(numStack.get(i));
            if(i != numStack.size() - 1)
                sb.append("-");
        }
        
        return sb.toString();
    }
    
    public static void main(String[] args)
    {
        Slot slot = new Slot(1, 2, 2, 6);
        Slot slot2 = new Slot(1, 1, 2, 4);
        System.out.println(slot);
        System.out.println(slot.betterThan(slot2));
        System.out.println(slot.worseThan(slot2));
        System.out.println(slot.subtract(slot2));
    }

    
    
}
