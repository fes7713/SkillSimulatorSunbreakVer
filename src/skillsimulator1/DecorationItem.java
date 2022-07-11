/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsimulator1;

import skillsimulator1.Decoration.Decoration;

/**
 *
 * @author fes77
 */
public class DecorationItem {
    private String decoration;
    private int level;

    public DecorationItem(Decoration decoration, int level) {
        this.decoration = decoration.getName();
        this.level = level;
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }
}
