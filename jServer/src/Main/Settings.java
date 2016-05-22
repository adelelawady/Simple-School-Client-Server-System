package Main;




import Main.*;
import Main.SysLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.filechooser.FileSystemView;

public class Settings implements Serializable {
    
    public Settings(){
        this.UpdateSettings();
        
    }
public String GetDocuments() {
    String StartUpPath = System.getProperty("user.dir");
    File DocumentsDir = FileSystemView.getFileSystemView().getDefaultDirectory();
    File SBP_Dir =new File(FileSystemView.getFileSystemView().getDefaultDirectory().toPath().toString()+"/Documents/SystemBankingProj");
    if (DocumentsDir.exists()){
      

        if (!SBP_Dir.exists()) {
             
            if (SBP_Dir.mkdirs()){
                new SysLog("Creating Folder [SystemBankingProj] in Documents");
                
                return (SBP_Dir.toPath().toString());
            }else{
                 new SysLog("[ERROR] Creating Folder [SystemBankingProj] in Documents");
                return DocumentsDir.toPath().toString();
            }
        }else{
            
            return (SBP_Dir.toPath().toString());
        }
        
        
    }else{
        return StartUpPath;
        
    }
    
}
    

    File file = new File(GetDocuments() + "/ClientsData");

    private int StatusDelay = 1500;

    private Boolean SaveTybe = false;

    private String LogSavingTybe = "statue";

    private String ObjectDataSaveLocation = file.toPath().toString() + ".bin";

    private String StringDataSaveLocation = file.toPath().toString() + ".txt";

    public void SetSaveTybe(Boolean Save_Tybe) {
        SaveTybe = Save_Tybe;
    }

    public Boolean GetSaveTybe() {
        return SaveTybe;
    }

    public void SetLogSavingTybe(String Log_Saving_Tybe) {
        LogSavingTybe = Log_Saving_Tybe;
    }

    public String GetLogSavingTybe() {
        return LogSavingTybe;
    }

    public void SetObjectDataSaveLocation(String Value) {
        ObjectDataSaveLocation = Value;
    }

    public String GetObjectDataSaveLocation() {
        return ObjectDataSaveLocation;
    }

    public void SetStringDataSaveLocation(String Value) {
        StringDataSaveLocation = Value;
    }

    public String GetStringDataSaveLocation() {
        return StringDataSaveLocation;
    }
   

    public void SetStatusDelay(int Value) {
        StatusDelay = Value;
    }

    public int GetStatusDelay() {
        return StatusDelay;
    }

//    public Settings LoadSettings() {
//        try {
//            File file = new File("Config.Properties");
//            if (!file.exists()) {
//                WriteSettings(new Settings());
//            }
//            ObjectStream ObjStr = new ObjectStream();
//            return (Settings) ObjStr.ReadObject(file.toPath().toString());
//        } catch (FileNotFoundException e) {
//            
//          // System.out.println (e.getMessage());
//           return null;
//        } catch (IOException | ClassNotFoundException e) {
//                   //    System.out.println (e.getMessage());
//
//            return null;
//        }
//    }

     public void UpdateSettings() {
        
            File file = new File(GetDocuments()+"/Config.Properties");
            if (!file.exists()) {
                WriteSettings(this);
                
            }else{
                 ObjectStream ObjStr = new ObjectStream();
            
          Settings NewSetting =(Settings) ObjStr.ReadObject(file.toPath().toString());
            this.LogSavingTybe=NewSetting.LogSavingTybe;
             this.ObjectDataSaveLocation=NewSetting.ObjectDataSaveLocation;
              this.SaveTybe=NewSetting.SaveTybe;
             this.StringDataSaveLocation=NewSetting.StringDataSaveLocation;
              this.StatusDelay=NewSetting.StatusDelay;
            }
           
              
      
    }
    
    
    public void WriteSettings(Settings Setting) {
       
            File file = new File(GetDocuments()+"/Config.Properties");
            ObjectStream ObjStr = new ObjectStream();
            ObjStr.WriteObject(file.toPath().toString(), Setting);
       
        
    }
}
