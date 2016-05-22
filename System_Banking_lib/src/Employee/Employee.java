/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Employee;


import Main.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author adelElawady
 */
public class Employee implements Serializable{
    
    
    
    
    public boolean Read=true;
    public boolean Update=false;
    public boolean CanCreate=false;
    public boolean Delete=false;
    public  boolean CONTROL_CREATE=false;
    public  boolean CONTROL_UPDATE=false;
    public  boolean Employee_Chat=false;
    public boolean CONTROL_READ=true;
    public boolean admin=false;
    public boolean SERVER=false;
    public EmployeeSettings ClientGUISettings;
    public boolean IsOnline=false;
    public Object[] Messages;
    public  Employee(String User_Name, String _Password, String RWs,ArrayList<Employee> Employee_List){
        //Check if Employee Exsits--
        ClientGUISettings=new EmployeeSettings();
        if (!CheckEmployee(User_Name,_Password,Employee_List)){
            
            for(String R_w_s:RWs.split(",")){
                SetBool(R_w_s.trim());
            }
            
            this.UserName = User_Name;
            this.Password = _Password;
            try{
                
                
            }catch(Exception e){
                
                System.out.println(e.getMessage());
            }
            
        }else{
            System.out.println("User Exsits");
        }
        
    }
    public void Online(){IsOnline=true;}
    public void Offile(){IsOnline=false;}
    public String UserName,Password;
    public static Employee FindEmployeeByUserName(String User_Name,ArrayList<Employee> Employee_List) {
        
        Employee Result=null;
        for (Employee EMP : Employee_List) {
            if (EMP.UserName.equals(User_Name) ) {
                Result= EMP;break;
            }else{
                
                Result=null;
            }
        }
        return Result;
    }
    public String GetUserName(Employee Employee_User){
        //Chech If recipient for username has rw to read Employee_User's
        //  if (Employee_User.CheckAccess("CONTROL_READ")){
        return UserName;
//      }else{
//          return EmployeeRW.DONT_HAVE_REQUIRED_RIGHTS;
//      }
    }
    public String GetPassword(Employee Employee_User) {
        //Chech If recipient for username has rw to read Employee_User's
        // if (Employee_User.CheckAccess("CONTROL_CREATE,CONTROL_UPDATE")) {
        return Password;
//        } else {
//            return EmployeeRW.DONT_HAVE_REQUIRED_RIGHTS;
//        }
    }
    
    
//      public  Employee CreateEmployee(String User_Name,String _Password,String RW) {
//       //   if (RequiredEmployee.CheckAccess("CONTROL_CREATE")) {
//
//             // return EMP;
////          }else{
////              return null;
////          }
//      }
//
    
    
    public static  boolean CheckEmployee(String User_Name,String Password, ArrayList<Employee> Employee_List){
        
        
        try{
            boolean Result=false;
            for (Employee EMP : Employee_List) {
                if (EMP.UserName.equals(User_Name) && EMP.Password.equals(Password)) {
                    
                    Result=true;
                    break;
                }else{
                    Result = false;
                    
                }
                
            }
            
            return Result;
        }catch(Exception e){
            return false;
            
        }
        
        // }else{
        
        
    }
    
    public static String GetDocuments() {
        String StartUpPath = System.getProperty("user.dir");
        File DocumentsDir = FileSystemView.getFileSystemView().getDefaultDirectory();
        File SBP_Dir = new File(FileSystemView.getFileSystemView().getDefaultDirectory().toPath().toString() + "/Documents/SystemBankingProj");
        if (DocumentsDir.exists()) {
            
            if (!SBP_Dir.exists()) {
                
                if (SBP_Dir.mkdirs()) {
                    //new SysLog("Creating Folder [SystemBankingProj] in Documents");
                    
                    return (SBP_Dir.toPath().toString());
                } else {
                    // new SysLog("[ERROR] Creating Folder [SystemBankingProj] in Documents");
                    return DocumentsDir.toPath().toString();
                }
            } else {
                
                return (SBP_Dir.toPath().toString());
            }
            
        } else {
            return StartUpPath;
            
        }
        
    }
    public void SetBool(String BooleanName){
        boolean value=true;
        if (BooleanName.equals("Update")){
            this.Update=value;
        }
        if(BooleanName.equals("Delete")){
            this.Delete=value;
        }
        if(BooleanName.equals("Create")){
            this.CanCreate=value;
        }
        if(BooleanName.equals("CONTROL_CREATE")){
            this.CONTROL_CREATE=value;
        }
        if(BooleanName.equals("CONTROL_UPDATE")){
            this.CONTROL_UPDATE=value;
        }
        if(BooleanName.equals("Employee_Chat")){
            this.Employee_Chat=value;
        }
        if(BooleanName.equals("admin")){
            this.CanCreate=value;
            this.admin=value;
            this.Employee_Chat=value;
            this.CONTROL_UPDATE=value;
            this.CONTROL_CREATE=value;
            this.Delete=value;
            this.Update=value;
        }
        
    }
    
    
    public boolean CheckBool(String BooleanName){
        if (BooleanName.equals("Update"))
            return Update;
        if(BooleanName.equals("Delete"))
            return Delete;
        if(BooleanName.equals("Create"))
            return CanCreate;
        if(BooleanName.equals("CONTROL_CREATE"))
            return CONTROL_CREATE;
        if(BooleanName.equals("CONTROL_UPDATE"))
            return CONTROL_UPDATE;
        if(BooleanName.equals("Employee_Chat"))
            return Employee_Chat;
        if(BooleanName.equals("admin")){
            return admin;
        }else{
            
            return true;
            
        }
        
    }
    
    
    public static  ArrayList<Employee> LoadEmployeeList() {
        
        File file = new File(GetDocuments() + "/EmployeeList.bin");
        if (!file.exists()) {
            ArrayList<Employee> Employee_List = new ArrayList();
            Employee ee = new Employee("admin", "ELkonsol", "admin,SERVER,Update",Employee_List);
            Employee_List.add(ee);
            SaveEmployees(Employee_List);
            return Employee_List;
//   return null;
        } else {
            ObjectStream ObjStr = new ObjectStream();
            
            ArrayList<Employee> EmployeeList = (ArrayList<Employee>) ObjStr.ReadObject(file.toPath().toString());
//  if (!EmployeeList.equals(null)){
            
            return EmployeeList;
//  }else{
//   return null;
// }
            
        }
        
        
        
    }
    
    public static  void SaveEmployees(Object EmployeeList) {
        
        File file = new File(GetDocuments() + "/EmployeeList.bin");
        ObjectStream ObjStr = new ObjectStream();
        ObjStr.WriteObject(file.toPath().toString(), EmployeeList);
       
        
    }
}