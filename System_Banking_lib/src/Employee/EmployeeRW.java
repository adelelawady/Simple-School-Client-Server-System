/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Employee;

import javax.swing.JOptionPane;

/**
 *
 * @author adelElawady
 */
public class EmployeeRW {
    public static String DONT_HAVE_REQUIRED_RIGHTS="You dont have Required Rights !";
    public static String DONT_HAVE_REQUIRED_Read_RIGHTS="You dont have Required Rights TO Update/Edit Clients\n";
    public static String DONT_HAVE_REQUIRED_Create_RIGHTS="You dont have Required Rights TO Create Client\n";
    public static String DONT_HAVE_REQUIRED_Delete_RIGHTS="You dont have Required Rights TO Delete Client\n";
    public static String DONT_HAVE_REQUIRED_CONTROL_UPDATE_RIGHTS="You dont have Required Rights TO Update/Edit an Employee\n";
    public static String DONT_HAVE_REQUIRED_Employee_Chat_RIGHTS="You dont have Required Rights TO Chat With Other Emplyees\n";
    public static String DONT_HAVE_REQUIRED_Admin_RIGHTS="You have to Be Admin To Do this\n";
    
    public static String RWMssageDialog(String ListRW){
        String TotalMessage="";
        
        if (ListRW.equals("Update")){
            TotalMessage=DONT_HAVE_REQUIRED_Read_RIGHTS+",";
        }
        if(ListRW.equals("Delete")){
            TotalMessage=DONT_HAVE_REQUIRED_Delete_RIGHTS+",";
        }
        if(ListRW.equals("Create")){
            TotalMessage=DONT_HAVE_REQUIRED_Create_RIGHTS+",";
        }
        if(ListRW.equals("CONTROL_CREATE")){
            TotalMessage=DONT_HAVE_REQUIRED_Create_RIGHTS+",";
        }
        if(ListRW.equals("CONTROL_UPDATE")){
            TotalMessage=DONT_HAVE_REQUIRED_CONTROL_UPDATE_RIGHTS+",";
        }
        if(ListRW.equals("Employee_Chat")){
            TotalMessage=DONT_HAVE_REQUIRED_CONTROL_UPDATE_RIGHTS+",";
        }
        if(ListRW.equals("Admin")){
            TotalMessage=DONT_HAVE_REQUIRED_CONTROL_UPDATE_RIGHTS+",";
        }
        return TotalMessage;
        
    }
}
