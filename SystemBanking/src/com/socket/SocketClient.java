/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.socket;

/**
 *
 * @author adelElawady
 */
import Customers.Client;
import Employee.Employee;
import EmployeeChat.ChatFrame;
import EmployeeChat.MSG;
import Main.*;
import com.ui.SystemBanking;
import java.awt.Color;
import java.awt.Component;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SocketClient implements Runnable {
    
    public int port;
    public String serverAddr;
    public Socket socket;
    
    public ObjectInputStream In;
    public ObjectOutputStream Out;
    public SystemBanking UI;
    //public History hist;
    
    public SocketClient(String _ip,int _port, SystemBanking SB) throws IOException {
        UI = SB;
        this.serverAddr = _ip;
        this.port = _port;
        socket = new Socket(InetAddress.getByName(serverAddr), port);
        
        Out = new ObjectOutputStream(socket.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(socket.getInputStream());
        
        //Check Server
        this.send(new Message("Connect", "ServerCheck", "CheckServer", "SERVER"));
        
        //  run();
        // hist = ui.hist;
    }
    
    @Override
    public void run() {
        boolean keepRunning = true;
        while (keepRunning) {
            
            try {
                
                Message msg=(Message) In.readObject();
                if (!msg.type.equals("newuser")&&!msg.type.equals("StatueLog")&&!msg.type.equals("OfflineUserList")){
                    SystemBanking.AddToServerLogList("[InComing] "+msg.toString());
                }
                if (msg.type.equals("Connect")){
                    // check if Server Is online
                    if(msg.content.equals("OK")){
                        //Get Thread Socket Port
                        UI.PortID=Integer.parseInt(msg.recipient);
                        ShowLoginDialog("*");
                    }
                    
                    
                    
                    //Employee Login
                } else if (msg.type.equals("Login")) {
                    if (msg.sender.equals("Success")){
                        //Login Success
                        
                        UI.AddToStatusList("Login Success");
                        UI.UserEmployee=(Employee) msg.content;
                        UI.EnableProfileButton();
                        UI.flashjtableBorder(Color.GREEN,3,1);
                        UI.LoadGUISettings();
                        
                    }else if(msg.content.equals("UserOnline")){
                        UI.AddToStatusList("Login Failed , User "+msg.recipient+" Online");
                        ShowLoginDialog("Login Failed , User "+msg.recipient+" Online");
                        
                    }else{
                        //Login Failed
                        UI.AddToStatusList("Login Failed , Try Agian");
                        ShowLoginDialog("Worng User/Password");
                        UI.flashjtableBorder(Color.red,3,2);
                        
                        
                    }//
                }else if(msg.type.equals("EmployeeListCP")){
                    if(!UI.EmpCP.equals(null)){
                        ArrayList<Employee> Employee_List = (ArrayList<Employee>) msg.content;
                        UI.EmpCP.UpdateEmployeeList(Employee_List);
                        //EmployeeListPrf
                    }
                }else if(msg.type.equals("EmployeeListPrf")){
                    if(!UI.Pref.equals(null)){
                        ArrayList<Employee> Employee_List = (ArrayList<Employee>) msg.content;
                        UI.Pref.UpdateEmployeeList(Employee_List);
                    }
                }else if(msg.type.equals("MSGList")){
                    try {
                        
                        
                        ArrayList<MSG>  UP_MSG_LIST=(ArrayList<MSG>) msg.content;
                        if(!UP_MSG_LIST.equals(null)||UP_MSG_LIST.toArray().length==0)
                            UI.CurrChatFrame.AddMSGList(UP_MSG_LIST);
                    } catch (Exception e) {
                        System.out.println("110 Client Server MSGList");
                    }
                }else if(msg.type.equals("message")){
                    //Recived New Message
                    UI.CurrChatFrame.HandleMsg(msg, UI);
                    
                    
                    
                }else if(msg.type.equals("UserList")){
                    ArrayList<Employee> Employee_List = (ArrayList<Employee>) msg.content;
                    
                    UI.CurrChatFrame=new ChatFrame();
                    for (Employee EMP:Employee_List){
                        if (!EMP.UserName.equals(UI.username)){
                            UI.CurrChatFrame.model.addElement(EMP.UserName);
                            
                        }
                    }
                    
                    
                    
                }else if(msg.type.equals("OfflineUserList")){
                    UI.CurrChatFrame.model2.removeAllElements();
                    ArrayList<Employee> Employee_List = (ArrayList<Employee>) msg.content;
                    for (Employee EMP:Employee_List){
                        
                        if (UI.CurrChatFrame.model.getSize()>1){
                            boolean IsOffile=false;
                            for(int i = 0; i < UI.CurrChatFrame.model.getSize(); i++){
                                if (!EMP.UserName.equals(UI.CurrChatFrame.model.getElementAt(i))){
                                    
                                    IsOffile=true;
                                }else{
                                    IsOffile=false;
                                    break;
                                }
                            }
                            
                            if(IsOffile){
                                if(!EMP.UserName.equals(UI.username)&&!EMP.UserName.equals(""))
                                    UI.CurrChatFrame.model2.addElement(EMP.UserName);
                            }
                        }else{
                            if(!EMP.UserName.equals(UI.username)&&!EMP.UserName.equals(""))
                                UI.CurrChatFrame.model2.addElement(EMP.UserName);
                        }
                    }
                    UI.CurrChatFrame.getTabbedPane().setTitleAt(1, "OFFLINE("+UI.CurrChatFrame.model2.getSize()+")");
                    UI.CurrChatFrame.REND(1);
                }else if(msg.type.equals("Removeuser")){
                    
                    boolean exists = false;
                    for(int i = 0; i < UI.CurrChatFrame.model.getSize(); i++){
                        if(UI.CurrChatFrame.model.getElementAt(i).equals(msg.content)){
                            exists = true;
                            UI.CurrChatFrame.model.remove(i);
                            break;
                        }
                    }
                    UI.CurrChatFrame.getTabbedPane().setTitleAt(0, "ONLINE("+(UI.CurrChatFrame.model.getSize()-1)+")");
                    UI.CurrChatFrame.REND(0);
                }else if(msg.type.equals("StatueLog")){
                    if(!UI.CurrChatFrame.isVisible()){
                        UI.AddToStatusList(msg.content.toString());
                    }
                    
                    
                    //StatueLog
                }else if(msg.type.equals("newuser")){
                    
                    if(!msg.content.equals(UI.username)){
                        boolean exists = false;
                        for(int i = 0; i < UI.CurrChatFrame.model.getSize(); i++){
                            if(UI.CurrChatFrame.model.getElementAt(i).equals(msg.content)){
                                exists = true; break;
                            }
                        }
                        if(!exists && !msg.content.toString().isEmpty()){ UI.CurrChatFrame.model.addElement(msg.content); }
                    }
                    UI.CurrChatFrame.getTabbedPane().setTitleAt(0, "ONLINE("+(UI.CurrChatFrame.model.getSize()-1)+")");
                    UI.CurrChatFrame.REND(0);
                }else if (msg.type.equals("ClientUpdated")) {
                    
                    Client CLT=(Client) msg.content ;
                    CLT.Update(UI.getTable());
                }else if (msg.type.contains("Response")) {
                    String ResponseType=msg.type.replace("Response_", "");
                    
                    //MSGList
                }else if(msg.type.equals("ClientList")){
                    
                    UI.ClearTableClients();
                    ArrayList<Client> CL= (ArrayList<Client>) msg.content;
                    
                    if(!CL.equals(null)){
                        UI.CLients_List=CL;
                        for (int i = 0; i <= UI.CLients_List.toArray().length-1; i++) {
                            
                            UI.AddClientToTable(UI.CLients_List.get(i));
                        }
                        
                    }else{
                        System.out.println("197 Socket Client null Client List");
                    }
                }
            } catch (Exception ex) {
                System.out.println("200 Socket Client "+ex.getMessage());
                UI.ClearTableClients();
                UI.ShowServerNotConnectedPanel(true);
                UI.PasueTimer=true;
                UI.SetIsSearchingLabel(true);
//            if (!SystemBanking.UserEmployee.equals(null)){
//                SystemBanking.UserEmployee.Offile();
//
//            }
                keepRunning = false;
                
            }
        }
    }
    public void ShowLoginDialog(String Notifi){
        com.ui.Login stuff = new com.ui.Login(UI, this,Notifi);
        stuff.setVisible(true);
    }
    
    
    public void send(Message msg) {
        try {
            Out.writeObject(msg);
            Out.flush();
            
            if (!msg.type.equals("newuser")&&!msg.type.equals("ClientLog")&&!msg.type.equals("OfflineUserList")){
                SystemBanking.AddToServerLogList("[OutGoing] " +msg.toString());
            }
            
        } catch (IOException ex) {
            
            System.out.println("Exception SocketClient send()");
        }
    }
    public void closeThread(Thread t) {
        t = null;
    }
}
