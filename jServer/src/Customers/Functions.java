package Customers;

import Main.FileReadWrite;
import Main.ObjectStream;
import Main.GetTextBtween;
import com.socket.*;
import Main.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Functions {

    boolean SaveTybe = false;

    Settings Setting = new Settings();

    public Functions() {
        Intilize();
    }

    public void Intilize() {
        try {
            Setting.UpdateSettings();
            SaveTybe = Setting.GetSaveTybe();
        } catch (Exception e) {
        }
    }

    public void SaveAllObject(ArrayList<Client> ClientList) {
        Intilize();

        //    File file = new File(Setting.GetObjectDataSaveLocation());
        ObjectStream ObjWriter = new ObjectStream();
        ObjWriter.WriteObject(Setting.GetObjectDataSaveLocation(), ClientList);

      //  new SysLog("Client List Saved ObjectMode");

    }
//
//    private void SaveAllString(ArrayList<Client> ClientList) {
//        Intilize();
//        File file = new File(Setting.GetStringDataSaveLocation());
//        file.delete();
//        for (int i = 0; i <= ClientList.toArray().length - 1; i++) {
//            ClientList.get(i).SaveString(Setting.GetStringDataSaveLocation());
//        }
//      //  new SysLog("Client List Saved StringMode");
//    }

    private void LoadClientsObject(ServerFrame UI)  {
        Intilize();
     try{
        String StartUpPath = System.getProperty("user.dir");
        File file = new File(Setting.GetObjectDataSaveLocation());
    //     System.out.println(Setting.GetObjectDataSaveLocation());
        if (file.exists()) {
            ObjectStream ObjReader = new ObjectStream();
            UI.CLients_List = (ArrayList<Client>) ObjReader.ReadObject(file.toPath().toString());
           
//            for (int i = 0; i < ClientList.toArray().length; i++) {
//                //     System.out.println(ClientList.get(i).AccountNO);
//                ClientList.get(i).AddToJtable(Table);
//            }
            
        }else{
            ArrayList<Client> NewClientList =new ArrayList<Client>();
            
             Client CLNT=new Client();
             CLNT.AccountNO=32423;
             CLNT.ClientAmount=5000000;
              CLNT.ClientID=30;
             CLNT.ClientName="ADMIN";
              CLNT.ClientNumber="500234230000";
              CLNT.SecurityPin=304;
             CLNT.TableItemIndex=0;  
             CLNT.Country="Egypt";
              NewClientList.add(CLNT);
              
               UI.CLients_List = NewClientList;
            file.createNewFile();
            
            SaveAllObject(NewClientList);        
        }
    }
    catch (IOException e) {
            System.out.println(e.getMessage());
    }
       
    }

//    private void LoadClientsString(ArrayList<Client> ClientList) {
//        Intilize();
//        try {
//            String StartUpPath = System.getProperty("user.dir");
//            File file = new File(Setting.GetStringDataSaveLocation());
//            if (file.exists()) {
//                FileReadWrite FR = new FileReadWrite();
//                GetTextBtween GTB1 = new GetTextBtween();
//                String[] ClientsList = GTB1.SplitAll(FR.readFile(Setting.GetStringDataSaveLocation()), "«»");
//                for (int i = 1; i <= ClientsList.length - 1; i++) {
//                    Client NCLient = new Client();
//                    NCLient.Load(ClientsList[i]);
//                    ClientList.add(NCLient);
//                   // NCLient.AddToJtable(Table);
//                }
//                ServerFrame.CLients_List = ClientList;
//             
//            } else {
//                file.createNewFile();
//                LoadClientsString(ClientList);
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public  void SaveClients(ArrayList<Client> ClientList) {
        Intilize();
        
       
       // if (SaveTybe) {
            SaveAllObject(ClientList);
//        } else {
//            SaveAllString(ClientList);
//        }
    }

    public void LoadClients(ServerFrame UI) {
        Intilize();
     //   if (SaveTybe) {
            LoadClientsObject(UI);
//        } else {
//            LoadClientsString(UI);
//        }
    }
}
