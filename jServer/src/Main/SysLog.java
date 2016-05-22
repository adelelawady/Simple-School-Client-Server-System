package Main;


import com.socket.ServerFrame;
import com.socket.SocketServer;
import java.awt.Color;
import java.io.File;

public class SysLog {

  
public SysLog(String Log) {
    //setting.Update();
    try{
       // System.out.println(Log);
        
       // ServerFrame.server.AnnounceClients("\n[Log] SERVER" + Log,ServerFrame.server.WhiteColor);
        
        ServerFrame.server.AnnounceClients("\n[Log] ",Color.RED);
        
                  ServerFrame.server.AnnounceClients("SERVER ",ServerFrame.server.UserColor);
                  
                  ServerFrame.server.AnnounceClients(Log+"\n", ServerFrame.server.WhiteColor);
        
    }catch(Exception e){ 
        System.out.println(e.getMessage());
    } 
    }
}
