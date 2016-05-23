/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.socket;

import com.ui.SystemBanking;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author adelElawady
 */
public class Socket_ip implements Runnable {
    SystemBanking Ui;
    Boolean CanStop=false;
    Thread thisThread ;
    public void Stop(){
        thisThread.stop();
    }
    public Socket_ip(SystemBanking ui){
        //Could not Connect To Server 
//        Ui.SetIsSearchingLabel(false);
//        Ui.ShowServerNotConnectedPanel(false);
        try{
          
      
        Ui.AddToStatusList("Searching For Localhost Server Ip On Network ....");
        Thread t = new Thread(this);
        thisThread=t;
        t.start();
        Ui=ui;
        }catch(Exception e){
            
        }
     
    }
   
    @Override
    public void run() {
        try {
            
       
        int ip1 = 192;  // check ur ip for Local Network if ur ip is 192.168.1.101
            int ip2 = 168;
            int ip3 = 1;
            String ip = "";
            
            for (int i = 1; i < 255; i++) {
                ip = ip1 + "." + ip2 + "." + ip3 + "." + i;

                if (portIsOpen(ip, Ui.port, 100)) {
                    Ui.serverAddr = ip;
                 Ui.ShowServerNotConnectedPanel(false);
                   Ui.AddToStatusList("Server Found On ip ["+ip+":1300]");
                  
                 
                   Ui.ConnectToServer();
                   
                    break;
                } else {
                    Ui.NextSearchIp=ip1 + "." + ip2 + "." + ip3 + "." + (i+1);
                     Ui.ShowServerNotConnectedPanel(true);
                    Ui.StartTimer();
                 //   Ui.AddToStatusList("Checking Server on : "+ip);
                    ip = "";
                    //System.out.println(ip + " Tested");
                }
            }
            if(ip==""){
                 Ui.PasueTimer = true;
               Ui.SetIsSearchingLabel(true);
               
                // Ui.AddToStatusList("Server Was not Found On LocalHost");
            }else{
                
               
                
                //Ui.ConnectToServer();
              
                
            }
      
         } catch (Exception e) {
             System.out.println(e.getMessage());
        }
    }
    

    public static boolean portIsOpen(String ip, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.close();
            return true;
        } catch (Exception ex) {
         //   JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
    }

  
    
}
