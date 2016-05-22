/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package EmployeeChat;

import Employee.Employee;
import static Employee.Employee.GetDocuments;
import Main.ObjectStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeArray.map;

/**
 *
 * @author adelElawady
 */

public class History {
    public HashMap<String,ArrayList<MSG>> MSG_hmap = new HashMap< String,ArrayList<MSG>>();
    public ArrayList<MSG>  UserMSGArrayList=new ArrayList<MSG>();
    public boolean H_MapLoaded=false;
    /**
     *
     * @param UserName
     * Employee UserName To Restore Its MSG's
     */
    public History(){
        H_MapLoaded=true;
        LoadHistroy();
    }
    
    public void SaveHistroy(){
        
        String StartUpPath = System.getProperty("user.dir");
        File file = new File(GetDocuments() + "/MSGHistory.Dat");
        ObjectStream ObjStr = new ObjectStream();
        
        ObjStr.WriteObject(file.toPath().toString(),  MSG_hmap);
        
        
    }
    public ArrayList<MSG> GetMSGList(String UserName) {
        if(!MSG_hmap.get(UserName).equals(null)){
            return MSG_hmap.get(UserName);
        }
        return null;
    }
    public boolean CheckUserMSGList(String UserName){
        ArrayList<MSG> value = MSG_hmap.get(UserName);
        if (value != null) {
            return true;
        }else{
            return false;
        }
        
    }
    public void UpdateUser(String UserName){
        UpdateUserMSGList(UserName,  GetMSGList(UserName));
    }
    public void UpdateUserMSGList(String UserName, ArrayList<MSG> NewMSgList){
        MSG_hmap.put(UserName, NewMSgList);
    }
    public void LoadHistroy(){
        
        String StartUpPath = System.getProperty("user.dir");
        File file = new File(GetDocuments() + "/MSGHistory.Dat");
        ObjectStream ObjStr = new ObjectStream();
        if (!file.exists()){
            try {
                
                file.createNewFile();
                
                MSG_hmap = new HashMap< String,ArrayList<MSG>>();
                ArrayList<MSG>  x= new ArrayList<MSG>();
                x.add(new MSG(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),null,"Hi","All","Admin"));
                
                MSG_hmap.put("All", x);
                
                this.SaveHistroy();
                
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }else{
            
            MSG_hmap=(HashMap< String,ArrayList<MSG>>) ObjStr.ReadObject(file.toPath().toString());
            //System.out.println(MSG_hmap.get("All").get(0).Sender);
            this.SaveHistroy();
            // System.out.println(MSG_hmap.get("admin>adel").get(0).MsgContent);
        }
        
        
    }
}
