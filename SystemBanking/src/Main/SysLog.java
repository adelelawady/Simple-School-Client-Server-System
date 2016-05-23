package Main;


import com.socket.Message;
import com.ui.SystemBanking;
import java.io.File;

public class SysLog {

  Employee.EmployeeSettings setting =SystemBanking.UserEmployee.ClientGUISettings;
public SysLog(String Log) {
    //setting.Update();
        if (setting.LogSavingTybe.toLowerCase().equals("println")) {
            System.out.println(Log);
            } else if (setting.LogSavingTybe.equals("statue")) {
            SystemBanking.AddToStatusList(Log);
//            SystemBanking.EmployeeClient.send(new Message("awdawd", Log, Log, Log));
        } else if (setting.LogSavingTybe.equals("Savelocal")) {
           File file = new File("SystemBankingLog.Logfile");
            FileReadWrite Writer = new FileReadWrite();
            Writer.FileWriter(file.toPath().toString(), "Log : " + Log + "\n", true);
        }
        try{
            
             SystemBanking.EmployeeClient.send(new Message("ClientLog", SystemBanking.UserEmployee.UserName,Log , "SERVER"));
    
        }catch(Exception e){
                    
            }
        
}
}
