/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import java.io.Serializable;

/**
 *
 * @author adelElawady
 */
public class EmployeeSettings implements Serializable{
    
    public EmployeeSettings(){
        
    }
    public boolean CLIENT_LOG_ENABLED=true;//show or hide clients log notifi
     public boolean SERVER_LOG_ENABLED=true;//show or hide server log notifi
     public int CLIENT_TABEL_ITEM_HIGHT=20; //the hight of each row of tabel
     public boolean SMART_TABEL_VIEW=true; // MY slow down the application if {ENabeld}
     public String LogSavingTybe = "statue";
     public boolean Can_Show_message_windows_after_reciving_msg=false;
     public String CurrServerIP="129.168.1.1";
     public int port=1300;
     public boolean AutoConnect=true;
     
}
