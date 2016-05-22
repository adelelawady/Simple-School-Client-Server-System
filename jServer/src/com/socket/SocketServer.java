package com.socket;


import Customers.Client;
import Customers.Functions;
import Employee.Employee;
import EmployeeChat.History;
import EmployeeChat.MSG;
import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;

class ServerThread extends Thread {
    
    public SocketServer server = null;
    public Socket socket = null;
    public int ID = -1;
    public String username = "";
    public Employee UserEmployee=null;
    public ObjectInputStream streamIn  =  null;
    public ObjectOutputStream streamOut = null;
    public ServerFrame ui;
    public Color WhiteColor = new Color(236, 240, 241);
    public ServerThread(SocketServer _server, Socket _socket){
        super();
        server = _server;
        socket = _socket;
        
        ID     = socket.getPort();
        ui = _server.ui;
    }
    
    public void send(Message msg){
        try {
            
            server.AnnounceIN_OUT(msg,true);
            streamOut.writeObject(msg);
            streamOut.flush();
            
        }
        catch (IOException ex) {
            System.out.println("Exception [SocketClient : send(...)]");
        }
    }
    
    public int getID(){
        return ID;
    }
    
    @SuppressWarnings("deprecation")
    public void run(){
        server.AnnounceServer("\n[Client] ", new Color(46, 204, 113));
        server.AnnounceServer("Thread " + ID + " Connected.\n",WhiteColor);
        
        while (true){
            try{
                
                Message msg = (Message) streamIn.readObject();
                // server.AnnounceIN_OUT("Outgoing : " + msg.toString(),true);
                //   if(!msg.equals(null)){
                server.AnnounceIN_OUT(msg,false);
                
                server.handle(ID, msg);
                
                // }
                
            }catch(Exception ioe){
                
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);
                stop();
                
                
            }
        }
    }
    
    public void open() throws IOException {
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamOut.flush();
        streamIn = new ObjectInputStream(socket.getInputStream());
    }
    
    public void close() throws IOException {
        socket.close();
        streamIn.close();
        streamOut.close();
    }
}





public class SocketServer implements Runnable {
    public Color WhiteColor = new Color(236, 240, 241);
    public Color UserColor = new Color(230, 126, 34);
    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread       thread = null;
    public int clientCount = 0, port = 1300;
    public ServerFrame ui;
    // public Database db;
    
    public SocketServer(ServerFrame frame){
        
        clients = new ServerThread[50];
        ui = frame;
        
        try{
            server = new ServerSocket(port);
            //  port = server.getLocalPort();
            AnnounceServer("\n[Server] ", Color.red);
            AnnounceServer("startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort()+"\n", WhiteColor);
            start();
        }
        catch(IOException ioe){
            AnnounceServer("\n[Error] ", Color.red);
            AnnounceServer("Can not bind to port : " + port + " Retrying", WhiteColor);
            ui.RetryStart(0);
        }
    }
    
    public SocketServer(ServerFrame frame, int Port){
        
        clients = new ServerThread[50];
        ui = frame;
        port = Port;
        
        try{
            server = new ServerSocket(port);
            port = server.getLocalPort();
            AnnounceServer("\n[Server] ", Color.red);
            AnnounceServer("startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort()+ "\n", WhiteColor);
            start();
        }
        catch(IOException ioe){
            AnnounceServer("\n[Error] ", Color.red);
            AnnounceServer("Can not bind to port " + port + ": " + ioe.getMessage(), WhiteColor);
        }
    }
    
    public void run(){
        while (thread != null){
            try{
                
                addThread(server.accept());
            }
            catch(Exception ioe){
                AnnounceServer("[\nError] ", Color.red);
                AnnounceServer("Server accept error: \n", WhiteColor);
                ui.RetryStart(0);
            }
        }
    }
    
    public void start(){
        if (thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
    
    @SuppressWarnings("deprecation")
    public void stop(){
        if (thread != null){
            thread.stop();
            thread = null;
        }
    }
    
    private int findClient(int ID){
        for (int i = 0; i < clientCount; i++){
            if (clients[i].getID() == ID){
                return i;
            }
        }
        return -1;
    }
    
    
    public synchronized void handle(int ID, Message msg){
        // System.out.println("Incoming : ");
        if (msg.content.equals(".bye")){
            Announce("signout", "SERVER", msg.sender);
            remove(ID);
            
        }else{
            
            if(msg.type.equals("Login")){
                
                
                if(findUserThread(msg.sender) == null){
                    if(Employee.CheckEmployee(msg.sender,(String) msg.content,ServerFrame.Employee_List)){
                        //Login Success Server
                        
                        
                        Employee EMP=Employee.FindEmployeeByUserName(msg.sender, ServerFrame.Employee_List) ;
                        
                        clients[findClient(ID)].username = msg.sender;
                        
                        
                        
                        clients[findClient(ID)].send(new Message("Login", "Success",EMP, msg.sender));
                        clients[findClient(ID)].UserEmployee=EMP;
                        //CLients_List
                        clients[findClient(ID)].send(new Message("ClientList", "SERVER",ui.CLients_List, msg.sender));
                        
                        Announce("newuser", "SERVER", msg.sender);
                        SendUserList(msg.sender);
                        //OfflineUserList
                        AnnounceServer("\n[Login]",new Color(46, 204, 113));
                        AnnounceServer(" ["+EMP.UserName+"]", UserColor);
                        AnnounceServer(" Logged in\n", WhiteColor);
                    } else {
                        //Login Failed Server
                        clients[findClient(ID)].send(new Message("Login", "SERVER", "Failed", msg.sender));
                    }
                }else{
                    clients[findClient(ID)].send(new Message("Login", "SERVER", "UserOnline", msg.sender));
                }
                //CreateEmployee
            }else if (msg.type.equals("GetOwenEmployeeList")) {
                
                if (msg.content.equals("ControlPanel")){
                    
                    ServerFrame.Employee_List=Employee.LoadEmployeeList();
                    clients[findClient(ID)].send(new Message("EmployeeListCP", "Server",ServerFrame.Employee_List, msg.sender));
                }else{
                    
                    ServerFrame.Employee_List=Employee.LoadEmployeeList();
                    clients[findClient(ID)].send(new Message("EmployeeListPrf", "Server",ServerFrame.Employee_List, msg.sender));
                    
                    
                }
                
            } else if (msg.type.equals("CreateEmployee")) {
                Main.GetTextBtween GETBTW =new Main.GetTextBtween();
                String NewUserName=GETBTW.GetTxtBtween((String)msg.content, "UserName='", "'");
                String NewPassword=GETBTW.GetTxtBtween((String)msg.content, "Password='", "'");
                String RWS =GETBTW.GetTxtBtween((String)msg.content, "RW(", ")");
                
                Employee NEWEmp =new Employee(NewUserName, NewPassword, RWS, ServerFrame.Employee_List);
                //RW(
                ServerFrame.Employee_List.add(NEWEmp);
                
                
                clients[findClient(ID)].send(new Message("EmployeeList", "Server",ServerFrame.Employee_List, msg.sender));
                Announce("newuser", "SERVER", msg.sender);
                SendUserList(msg.sender);
                Employee.SaveEmployees(ServerFrame.Employee_List);
            }else if(msg.type.equals("message")){
                try {
                    
                    ServerFrame.HIS=new History();
                    
                    if(msg.recipient.equals("All")){
                        ArrayList<MSG>  UP_MSG_LIST;
                        
                        Announce("StatueLog", msg.sender, msg.sender+" Send You A Message");
                        UP_MSG_LIST = ServerFrame.HIS.GetMSGList("All");
                        
                        MSG NEWMSG=(MSG)msg.content;
                        NEWMSG.ReciveDate=Calendar.getInstance().getTime();
                        UP_MSG_LIST.add(NEWMSG);
                        ServerFrame.HIS.UpdateUserMSGList("All", UP_MSG_LIST);
                        ServerFrame.HIS.SaveHistroy();
                        Announce("message", msg.sender, msg.content);
                        
                        
                    }else{
                        
                        findUserThread(msg.recipient).send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                        clients[findClient(ID)].send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                        Announce("StatueLog", msg.sender, msg.sender+" Send You A Message");
                        
//                        ArrayList<MSG>  UP_MSG_LIST=new ArrayList<MSG>();
//                          if (ServerFrame.HIS.CheckUserMSGList(msg.sender+">"+msg.recipient)){
//                        UP_MSG_LIST = ServerFrame.HIS.GetMSGList(msg.sender+">"+msg.recipient);
//                          }
//                        UP_MSG_LIST.add((MSG) msg.content);
//                        ServerFrame.HIS.UpdateUserMSGList(msg.sender+">"+msg.recipient, UP_MSG_LIST);
//                        ServerFrame.HIS.SaveHistroy();
//
                    }
                } catch (Exception e) {
                    System.out.println("271 " +e.getMessage());
                }
            }else if(msg.type.equals("RequestMSGList")){
                try {
                    ServerFrame.HIS=new History();
                    
                    ArrayList<MSG>  UP_MSG_LIST=new ArrayList<MSG>();
                    
                    if(msg.recipient.toLowerCase().equals("all")){
                        UP_MSG_LIST = ServerFrame.HIS.GetMSGList("All");
                        
                        if (!UP_MSG_LIST.equals(null))
                            
                            clients[findClient(ID)].send(new Message("MSGList", "Server", UP_MSG_LIST, "All"));
                    }else{
                        //  if (ServerFrame.HIS.CheckUserMSGList(msg.sender+">"+msg.recipient)){
//                        System.out.println(msg.sender+">"+msg.recipient);
//
//                        UP_MSG_LIST = ServerFrame.HIS.GetMSGList(msg.sender+">"+msg.recipient);
//                        System.out.println("here "+ UP_MSG_LIST.get(0).MsgContent);
//
//                            clients[findClient(ID)].send(new Message("MSGList", "Server", UP_MSG_LIST, msg.sender));
//
//
//
                    }
                    
                } catch (Exception e) {
                    System.out.println("291 Socket Server Error"+ e.getStackTrace().toString());
                }
            } else if (msg.type.equals("ClientUpdate")) {
                
                ArrayList<Client> newClientList = (ArrayList<Client>) msg.content;
                if(!newClientList.equals(null)){
                    Customers.Functions Fun =new Functions();
                    ui.CLients_List = newClientList;
                    Announce("ClientList", "SERVER", newClientList );
                    Fun.SaveClients(newClientList);
                }else{
                    System.out.println("256 Socket Server null Client List");
                }
                
                
                
                
                
            } else if (msg.type.equals("LogOut")) {
                try {
                    clients[findClient(ID)].close();
                } catch (IOException ex) {
                    
                }
            } else if (msg.type.equals("UpdateOwenEmployeeList")) {
                
                ServerFrame.Employee_List=(ArrayList<Employee>)msg.content;
                Employee.SaveEmployees(ServerFrame.Employee_List);
            } else if (msg.type.equals("ClientLog")) {
                //Receive User Log
                AnnounceClients("\n[Log] ",Color.RED);
                AnnounceClients("["+msg.sender.toString()+"] ", UserColor);
                AnnounceClients((String)msg.content+"\n", WhiteColor);
                
                
            }else if (msg.type.equals("Connect")) {
                //Login OK
                
                
                clients[findClient(ID)].send(new Message("Connect", "SERVER", "OK", ""+ID));
                
                
            }
        }
    }
    
    
    
    public void Announce(String type, String sender, Object content) {
        Message msg = new Message(type, sender, content, "All");
        for (int i = 0; i < clientCount; i++) {
            clients[i].send(msg);
        }
    }
    public void Announce(String type, String sender, Object content,String _sender) {
        Message msg = new Message(type, sender, content, "All");
        for (int i = 0; i < clientCount; i++)
            if (!clients[i].username.equals(_sender)){
                clients[i].send(msg);
                System.out.println(clients[i].username);
            }
    }
    public void AnnounceServer(String Content,Color color) {
        ui.AppandPanel(Content,color);
        
    }
    public void AnnounceClients(String Content, Color color) {
        
        ui.AppandPanel2(Content, color);
        
    }
    public void AnnounceIN_OUT(Message msg,boolean ISOUT) {
        
        if(ISOUT){
            //out going
            ui.AppandPanel3("\n[Outgoing] ", new Color(0,153,153));
            ui.AppandPanel3("'"+msg.sender+"' [" , this.UserColor);
            
        }else{
            ui.AppandPanel3("\n[InComing] : ", new Color(46, 204, 113));
            ui.AppandPanel3("'"+msg.sender+"' [" , this.UserColor);
        }
        ui.AppandPanel3("Content Type : ", new Color(149, 165, 166));
        if (msg.content.getClass().getSimpleName().equals("ArrayList")){
            Object Obj =new Object();
            ArrayList adel =(ArrayList)msg.content;
            ui.AppandPanel3("('"+msg.content.getClass().getSimpleName()+"'-"+msg.type+"<"+adel.get(0).getClass().getSimpleName()+">|",new Color(236, 240, 241));
            
        }else{
            
            ui.AppandPanel3("('"+msg.content.getClass().getSimpleName()+"'-"+msg.type+")|",new Color(236, 240, 241));
            
        }
        
        ui.AppandPanel3("Recipient: ", new Color(149, 165, 166));
        ui.AppandPanel3("'"+msg.recipient+"'",new Color(236, 240, 241));
        ui.AppandPanel3("]\n" , this.UserColor);
    }
    public void SendUserList(String toWhom){
        for(int i = 0; i < clientCount; i++){
            
            findUserThread(toWhom).send(new Message("newuser", "SERVER", clients[i].username, toWhom));
        }
        Announce("OfflineUserList",toWhom, ServerFrame.Employee_List);
    }
    
    public ServerThread findUserThread(String usr){
        for(int i = 0; i < clientCount; i++){
            if(clients[i].username.equals(usr)){
                return clients[i];
            }
        }
        return null;
    }
//      for(int i=0 ;i<clientCount;i++){
//            try {
//                clients[findClient(ID)].close();
//                AnnounceClients(clients[findClient(ID)].username+" Logged Out", UserColor);
//            } catch (IOException ex) {
//            }
//        }
    @SuppressWarnings("deprecation")
    public void remove(int ID){
        int pos = findClient(ID);
        if (pos >= 0){
            
            ServerThread toTerminate = clients[pos];
            
            ui.AppandPanel("\n[Server] ", Color.red);
            ui.AppandPanel("Removing client thread " + ID + " at " + pos+"\n", WhiteColor);
            
            if (!toTerminate.username.isEmpty()){
                
                
                AnnounceServer("\n[LogOut] ",Color.RED);
                AnnounceServer("["+toTerminate.UserEmployee.UserName+"] ", UserColor);
                AnnounceServer("Logged Out\n", WhiteColor);
                
            }
            
            if (pos < clientCount-1){
                for (int i = pos+1; i < clientCount; i++){
                    clients[i-1] = clients[i];
                }
            }
            clientCount--;
            try{
                toTerminate.close();
                
                Announce("Removeuser", "SERVER", toTerminate.username);
                Announce("OfflineUserList","All", ServerFrame.Employee_List);
            }
            catch(IOException ioe){
                AnnounceServer("[\nError] ", Color.red);
                AnnounceServer("Error closing thread: " + ioe,WhiteColor);
            }
            toTerminate.stop();
        }
    }
    
    private void addThread(Socket socket){
        if (clientCount < clients.length){
            // AnnounceServer("\nClient accepted: " + socket);
            clients[clientCount] = new ServerThread(this, socket);
            try{
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            }
            catch(IOException ioe){
                //AnnounceServer("\nError opening thread: " + ioe);
            }
        }
        else{
            AnnounceServer("\n[Error] ", Color.red);
            AnnounceServer("Client refused: maximum " + clients.length + " reached.\n", WhiteColor);
        }
    }
}
