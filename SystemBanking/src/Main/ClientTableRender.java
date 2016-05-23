/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author adelElawady
 */

public  class ClientTableRender implements TableCellRenderer {
    boolean CanRend=true;
    public ClientTableRender(){
        
    
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
      //  if (CanRend){
            JTextField editor = new JTextField();
            if (value != null){
                
                
                if (table.getSelectedRow()==row){
                    
                    editor.setBackground(new Color(52, 152, 219) );
                }else{
                    
                    editor.setBackground((row % 2 == 0) ? Color.white : new Color(189, 195, 199));
                    
                }
                editor.setText(value.toString());
                
            }
            return editor;
     //   }
       
    }
}

