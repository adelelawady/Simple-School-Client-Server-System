package Customers;

import com.socket.*;
import Main.*;


import java.io.Serializable;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

public class Client implements Serializable {

    public int AccountNO, ClientID, ClientAmount, SecurityPin, TableItemIndex;

    public String ClientName, Country, CreditCardData, ClientNumber;

    public Boolean HideInfo = false;

    public int identityNumber;
    public Client() {
        Random RND =new Random();
        identityNumber=RND.nextInt(9999999);
    }

//    public String STRING_Data() {
//        String MainClientData = ("«»[AccountNO='" + AccountNO + "',ClientID='" + ClientID + "',ClientAmount='" + ClientAmount + "',SecurityPin='" + SecurityPin + "',ClientName='" + ClientName + "',Country='" + Country + "',CreditCardData=[&/" + CreditCardData + "/&]," + "ClientNumber='" + ClientNumber + "'HideInfo='" + HideInfo.toString() + "']#");
//        return MainClientData;
//    }

    public void Load(String STRING_Data) {
        GetTextBtween GTB = new GetTextBtween();
        this.AccountNO = Integer.parseInt(GTB.GetTxtBtween(STRING_Data, "AccountNO='", "'"));
        this.ClientID = Integer.parseInt(GTB.GetTxtBtween(STRING_Data, "ClientID='", "'"));
        this.ClientAmount = Integer.parseInt(GTB.GetTxtBtween(STRING_Data, "ClientAmount='", "'"));
        this.SecurityPin = Integer.parseInt(GTB.GetTxtBtween(STRING_Data, "SecurityPin='", "'"));
        this.ClientName = GTB.GetTxtBtween(STRING_Data, "ClientName='", "'");
        this.Country = GTB.GetTxtBtween(STRING_Data, "Country='", "'");
        this.CreditCardData = GTB.GetTxtBtween(STRING_Data, "CreditCardData=[&/", "/&]");
        this.ClientNumber = GTB.GetTxtBtween(STRING_Data, "ClientNumber='", "'");
    }

//    public void SaveString(String path) {
//        String StartUpPath = System.getProperty("user.dir");
//        FileReadWrite Writer = new FileReadWrite();
//        Writer.FileWriter(path, STRING_Data(), true);
//    }

    public void Update(javax.swing.JTable Table) {
        String SelectedCell = "" + Table.getValueAt(Table.getSelectedRow(), Table.getSelectedColumn());
        if (Table.getSelectedColumn() == 1) {
            this.ClientName = SelectedCell;
        } else if (Table.getSelectedColumn() == 5) {
            this.ClientNumber = SelectedCell;
        } else if (Table.getSelectedColumn() == 6) {
            this.SecurityPin = Integer.parseInt(SelectedCell);
        }
    }

    public void AddToJtable(javax.swing.JTable Table) {
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        Object[] row = new Object[7];
        row[0] = this.AccountNO;
        row[1] = this.ClientName;
        row[2] = this.ClientID;
        row[3] = this.Country;
        row[4] = this.ClientAmount;
        row[5] = this.ClientNumber;
        row[6] = this.SecurityPin;
        model.addRow(row);
        this.TableItemIndex = model.getRowCount();
        Table.setModel(model);
    }
}
