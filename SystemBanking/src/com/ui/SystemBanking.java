package com.ui;


import Customers.Client;
import Employee.Employee;
import Employee.EmployeeRW;
import EmployeeChat.ChatFrame;
import Main.*;
import com.socket.SocketClient;
import com.socket.Message;
import com.socket.Socket_ip;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public  final class SystemBanking extends javax.swing.JFrame {
    
    public  ArrayList<Client> CLients_List ;
    
    
    public ChatFrame CurrChatFrame=new ChatFrame();
    public Preferences Pref =null;
    public EmployeeControlPanel EmpCP=null;
    public static SocketClient EmployeeClient;
    
    public Timer Statuetimer = null;
    
    public static ArrayList<String> StatusList = new ArrayList<String>();
    public static ArrayList<String> ServerLogList = new ArrayList<>();
    
    public static Employee UserEmployee=null;
    
    public  int port=1300,PortID=-1;
    public String serverAddr;
    public static  String username, password;
    public Thread clientThread;
    public boolean IsConnectedToServer=false;
    public Socket_ip  ConnectSearch;
  
    
    //Check if System is Windows Or Not
    public boolean isWin32() {
        return System.getProperty("os.name").startsWith("Windows");
        
    }
    
    /**
     * Clear All Rows From jTabel1  (Clients JTabel)
     */
    public void ClearTableClients()
    {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            int rows = model.getRowCount();
            for (int i = rows - 1; i >= 0; i--) {
                model.removeRow(i);
            }
        } catch (Exception e) {
            System.out.println("ERROR CLEARING LIST CLIENTS");
        }
        
        
    }
    
    public SystemBanking() {
        
        initComponents();
        
        jMenu4.setVisible(false);
        jMenu7.setVisible(false);
        jPanel3.setVisible(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        //Search And Connect To Server On Local Host
        AddToServerLogList("Searching For Server IP ....");
        ConnectSearch = new Socket_ip(this);
        
        
        
        
        //Add cell Editing Listener
        ADDCellEditingListener();
        
        //Start Clients log Timer
        StatueUpdater();
        
        //Start Server log Timer
        AddServerLog();
        
        //Update to Original Size
        this.setSize(new Dimension(1076, 549));
        
        
        
    }
    /**
     * Connect to Server
     */
    public void ConnectToServer(){
        try {
            AddToServerLogList("Connecting To Server ....");
            EmployeeClient = new SocketClient(serverAddr, port, this);
            clientThread = new Thread(EmployeeClient);
            clientThread.start();
            IsConnectedToServer=true;
            AddToServerLogList("Connected To Server Successfuly...");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            IsConnectedToServer = false;
            
        }
    }
    /**
     * Load GUI Employee Setting (After Login)
     */
    public void LoadGUISettings(){
        
        if (this.UserEmployee.ClientGUISettings.SMART_TABEL_VIEW){
            this.EnableSmartView();
        }else{
            this.DisableSmartView();
            
        }
        jTable1.setRowHeight(this.UserEmployee.ClientGUISettings.CLIENT_TABEL_ITEM_HIGHT);
        jMenu4.setVisible(true);
        jMenu7.setVisible(true);
        
    }
    /**
     * Enable JTabel Costume Render
     */
    public void EnableSmartView(){
        
        jTable1.setDefaultRenderer(Object.class, new ClientTableRender());
        jTable1.repaint();
    }
    /**
     * Enable JTabel Default Render
     */
    public void DisableSmartView(){
        
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
        
        jTable1.repaint();
    }
    /**
     * show Username label(After Login)
     */
    public void EnableProfileButton(){
        jLabel11.setEnabled(true);
        jLabel11.setToolTipText(username);
        jMenu3.setText("USER : "+username);
    }
    
    
    
    public boolean PasueTimer = false;
    public String NextSearchIp="0";
    /**
     * Show and Hide Reconnect & manual Panel
     */
    public void StartTimer(){
        int delay = 100; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //...Perform a task...
                if (!PasueTimer){
                    
                    
                    SetIsSearchingLabel(false);
                    
                }
            }
        };
        new Timer(delay, taskPerformer).start();
        
    }
    /**
     * Show Server Not Connected Panel
     * @param Show
     * if true reconnect Panel Hides JTabel Shows
     */
    public void ShowServerNotConnectedPanel(boolean Show){
        jPanel2.setVisible(Show);
        jTable1.setVisible(!Show);
        if (Show)
        {
            jPanel1.setComponentZOrder(jPanel2, 0);
        }
    }
    /**
     * Set Searching Label to Reconnect Panel
     * @param Show
     * if true  Searching for Server
     *    else Could not Connect To Server
     */
    public void SetIsSearchingLabel(boolean Show) {
        if(!Show){
            
            jLabel8.setText("Searching For Localhost Server Ip On Network ["+NextSearchIp+"]");
            jLabel9.setVisible(false);
            jLabel10.setVisible(false);
            jLabel12.setVisible(true);
        }else{
            
            jLabel8.setText("Could not Connect To Server ");
            jLabel9.setVisible(true);
            jLabel10.setVisible(true);
            jLabel12.setVisible(false);
        }
        
        
    }
    Timer ServerLogTimer=new Timer(0, null);
    /**
     * Start Server Log Timer
     */
    public void AddServerLog(){
        int delay = 1200;
        
        
        ActionListener taskPerformer = new ActionListener() {
            
            public void actionPerformed(ActionEvent evt) {
                try {
                    
                    
                    if (ServerLogList.toArray().length != 0 &&!UserEmployee.equals(null) && UserEmployee.ClientGUISettings.SERVER_LOG_ENABLED) {
                        jLabel1.setText(ServerLogList.get(0));
                        jPanel3.setVisible(true);
                        jPanel1.setComponentZOrder(jPanel3, 0);
                        jLabel1.setIconTextGap(13);
                        if (jLabel1.getText().contains("[Out")){
                            
                            jLabel1.setIcon(new ImageIcon(getClass().getResource("/Images/arrow-top-blue-16.png")));
                        }else if(jLabel1.getText().contains("[In")){
                            
                            jLabel1.setIcon(new ImageIcon(getClass().getResource("/Images/DownArrow.png")));
                        }
                        ServerLogList.remove(0);
                    } else {
                        jLabel1.setText("");
                        jPanel1.setComponentZOrder(jPanel3, 3);
                        jPanel3.setVisible(false);
                    }
                } catch (Exception e) {
                }
            }
        };
        ServerLogTimer = new Timer(delay, taskPerformer);
        ServerLogTimer.start();
    }
    
    /**
     * Add new Server Log
     * @param Title
     * Title
     */
    public static void AddToServerLogList(String Title) {
        ServerLogList.add(Title);
        
    }
    /**
     * Add new Statue Log
     * @param Title
     * Title
     */
    public static void AddToStatusList(String Title) {
        StatusList.add(Title);
    }
    /**
     * Start Status Updater timer
     */
    public void StatueUpdater() {
// Setting = Setting.LoadSettings();
        int delay = 1500;
        if (jLabel4.getText() != "Ready") {
            jLabel4.setText("");
        }
        ActionListener taskPerformer = new ActionListener() {
            
            
            public void actionPerformed(ActionEvent evt) {
                try{
                    if (StatusList.toArray().length != 0 &&UserEmployee.ClientGUISettings.CLIENT_LOG_ENABLED) {
                        jLabel4.setText(StatusList.get(0));
                        StatusList.remove(0);
                    } else {
                        jLabel4.setText("Ready");
                    }
                }catch(Exception e){
                }
            }
        };
        Statuetimer = new Timer(delay, taskPerformer);
        Statuetimer.start();
    }
    /**
     * Save All Clients Data
     */
    public void SaveAllClients() {
        SendCellUpdateToServer();
    }
    
    /**
     * Reload Clients TO JTabel
     * @param CLients_List
     * ArrayList<Clients> to Update JTabel From It
     */
    public void ReloadClientsTojTable(ArrayList<Client> CLients_List){
        
        ClearTableClients();
        
        CLients_List=CLients_List;
        for (int i = 0; i <= CLients_List.toArray().length-1; i++) {
            AddClientToTable(CLients_List.get(i));
        }
    }
    /**
     * add Cell Editing Listener TO Clients JTabel
     */
    public void ADDCellEditingListener() {
        jTable1.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent evt) {
                if (evt.getType() == 0) {
                    try {
                        String SelectedCell = "" + jTable1.getValueAt(jTable1.getSelectedRow(), jTable1.getSelectedColumn());
                        int Raw=jTable1.getSelectedRow();
                        int Col=evt.getColumn();
                        boolean CanUpdate=false;
                        
                        if (Col==1){
                            for (Client CLNT:CLients_List){
                                
                                if (SelectedCell.equals(CLNT.ClientName)){
                                    SysLog sysLog = new SysLog("Client Name Exists !");
                                    
                                    CanUpdate=false;
                                    break;
                                }else{
                                    CanUpdate=true;
                                }
                            }
                        }
                        
                        if (CanUpdate){
                            UpdateCell();
                            
                        }else{
                            ReloadClientsTojTable(CLients_List);
                            
                        }
                        
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                
                
            }
        });
    }
    boolean CanPauseFlashTimer=false;
    Timer TIm=new Timer(0, null);
    /**
     * Flash JTabel Function
     * @param C
     * Color OF Flash
     * @param Border_thickness
     * border thickness
     * @param LoopsCount
     * Flash loop
     */
    public void flashjtableBorder(Color C,int Border_thickness,int LoopsCount){
        CanPauseFlashTimer=false;
        int delay = 500; //milliseconds
        
        ActionListener taskPerformer = new ActionListener() {
            int loop=LoopsCount;
            public void actionPerformed(ActionEvent evt) {
                
                
                if (!CanPauseFlashTimer){
                    
                    jPanel1.setBorder(new LineBorder(C, Border_thickness, true));
                    
                    CanPauseFlashTimer=true;
                    
                }else{
                    
                    jPanel1.setBorder(new LineBorder(new Color(238,238,238), 2, true));
                    if(loop>1){
                        CanPauseFlashTimer=false;
                        TIm.restart();
                        loop--;
                    }else{
                        CanPauseFlashTimer=true;
                        TIm.stop();
                    }
                }
                
            }
        };
        TIm = new Timer(delay, taskPerformer);
        TIm.setRepeats(true);
        
        TIm.start();
    }
    
    
    
    
    
    /**
     * Log Out OF Current User
     */
    public void LogOut(){
        try{
            if (IsConnectedToServer && !this.UserEmployee.equals(null)){
                
                EmployeeClient.send(new Message("LogOut", UserEmployee.UserName,"LogOut Action", "SERVER"));
                UserEmployee=null;
                StatusList.clear();
                ServerLogList.clear();
                jLabel11.setEnabled(false);
                jLabel11.setToolTipText("None");
                jMenu3.setText("USER : None");
                jPanel1.setComponentZOrder(jPanel3, 3);
                jPanel3.setVisible(false);
                this.IsConnectedToServer=false;
                jMenu4.setVisible(false);
                jMenu7.setVisible(false);
            }
        }catch(Exception e){
            
        }
    }
    /**
     * Update Client Data Using jTabel
     */
    public void UpdateCell(){
        LogClientUpdate();
        GetSelectedClient().Update(jTable1);
        SaveAllClients();
        
    }
    /**
     * Send ClientUpdate With Client Data To Server
     */
    public void SendCellUpdateToServer(){
        EmployeeClient.send(new Message("ClientUpdate", UserEmployee.UserName,CLients_List, "SERVER"));
    }
    /**
     * Create new SysLog Of Updated Column
     */
    public void LogClientUpdate() {
        String SelectedCell = "" + jTable1.getValueAt(jTable1.getSelectedRow(), jTable1.getSelectedColumn());
        if (jTable1.getSelectedColumn() == 1&&!this.GetSelectedClient().ClientName.equals(SelectedCell)) {
            SysLog sysLog = new SysLog("Client [" + this.GetSelectedClient().ClientName + "] Name Changed to [" + SelectedCell + "]");
        } else if (jTable1.getSelectedColumn() == 5) {
            SysLog sysLog = new SysLog("Client [" + this.GetSelectedClient().ClientName + "] Number Changed to [" + SelectedCell + "]");
        } else if (jTable1.getSelectedColumn() == 6) {
            SysLog sysLog = new SysLog("Client [" + this.GetSelectedClient().ClientName + "] SecurityPin Changed to [" + this.GetSelectedClient().SecurityPin + "]");
        }
    }
    /**
     * add Client To JTabel
     * @param Clt
     * Client
     */
    public  void AddClientToTable(Client Clt) {
        Clt.AddToJtable(this.jTable1);
    }
    /**
     * @return
     *  Clients  JTabel
     */
    public JTable getTable() {
        return jTable1;
    }
    /**
     *
     * @return
     * Selected Client Of JTabel
     */
    public Client GetSelectedClient() {
        try {
            for (int I = 0; I <= CLients_List.toArray().length - 1; I++) {
                if (CLients_List.get(I).TableItemIndex == jTable1.getSelectedRow() + 1) {
                    return CLients_List.get(I);
                    
                }
            }
        } finally {
        }
        return null;
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        BankingSystem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        AddItem = new javax.swing.JMenuItem();
        DeleteItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        DepositItem = new javax.swing.JMenuItem();
        Withdrawalitem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        Change = new javax.swing.JMenu();
        ChangeName = new javax.swing.JMenuItem();
        ChangePhone = new javax.swing.JMenuItem();
        ChangePin = new javax.swing.JMenuItem();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPopupMenu1.setSelected(jTable1);

        BankingSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bank-32.png"))); // NOI18N
        BankingSystem.setText("BankingSystemProject\n");
        BankingSystem.setToolTipText("");
        BankingSystem.setEnabled(false);
        jPopupMenu1.add(BankingSystem);
        jPopupMenu1.add(jSeparator1);

        AddItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus-16.png"))); // NOI18N
        AddItem.setText("Add Client");
        AddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(AddItem);

        DeleteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/010_x-3-16.png"))); // NOI18N
        DeleteItem.setText("Delete");
        DeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(DeleteItem);
        jPopupMenu1.add(jSeparator2);

        DepositItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1_8-16.png"))); // NOI18N
        DepositItem.setText("Deposit");
        DepositItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepositItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(DepositItem);

        Withdrawalitem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/16-16.png"))); // NOI18N
        Withdrawalitem.setText("Withdrawal");
        Withdrawalitem.setToolTipText("");
        Withdrawalitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WithdrawalitemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Withdrawalitem);
        jPopupMenu1.add(jSeparator3);

        Change.setText("Change");

        ChangeName.setText("Name");
        ChangeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangeNameActionPerformed(evt);
            }
        });
        Change.add(ChangeName);

        ChangePhone.setText("ClientPhone");
        ChangePhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePhoneActionPerformed(evt);
            }
        });
        Change.add(ChangePhone);

        ChangePin.setText("SecurityPin");
        ChangePin.setToolTipText("");
        ChangePin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePinActionPerformed(evt);
            }
        });
        Change.add(ChangePin);

        jPopupMenu1.add(Change);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel2.setText("Search Clients");

        jLabel5.setFont(new java.awt.Font("Mshtakan", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setText("Advanced");
        jLabel5.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 2, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("secure banking solutions");
        jLabel6.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 2, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 153));
        jLabel7.setText("banking System");
        jLabel7.setToolTipText("");

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/100-32.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.setEnabled(false);
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 2, true));

        jTable1.setFont(new java.awt.Font("Lucida Sans", 2, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account No.", "Client Name", "Client Id", "Country", "Client Amount", "Client Number", "Securtiy Pin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setEditingColumn(0);
        jTable1.setEditingRow(0);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTable1MouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jTable1MouseDragged(evt);
            }
        });
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true));
        jPanel2.setForeground(new java.awt.Color(255, 51, 51));

        jLabel8.setText("Could not Connect To Server ");

        jLabel9.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 51));
        jLabel9.setText("Reconnect");
        jLabel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 51));
        jLabel10.setText("Manual");
        jLabel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 204, 51));
        jLabel12.setText("Cancel");
        jLabel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(51, 51, 51)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)))
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(26, 26, 26))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setText("Copyright © (ProjectTeam of The Project) 2015 [SystemBanking Project]");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Ready");

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(26, 188, 156), 2, true));
        jPanel3.setForeground(new java.awt.Color(52, 73, 94));

        jLabel1.setBackground(new java.awt.Color(52, 73, 94));
        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText(".....");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(385, 385, 385)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(238, 238, 238));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jMenu2.setText("File");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus-16.png"))); // NOI18N
        jMenuItem2.setText("Add Client");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Save all");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);
        jMenu2.add(jSeparator4);

        jMenuItem4.setText("Preferences");
        jMenuItem4.setToolTipText("");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);
        jMenu2.add(jSeparator5);

        jMenuItem5.setText("Quit ");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("help");

        jMenuItem7.setText("About");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu5.setText("|");
        jMenu5.setEnabled(false);
        jMenuBar1.add(jMenu5);

        jMenu3.setBackground(new java.awt.Color(204, 204, 204));
        jMenu3.setForeground(new java.awt.Color(0, 102, 102));
        jMenu3.setText("USER");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenu3MousePressed(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu4.setBackground(new java.awt.Color(255, 0, 0));
        jMenu4.setForeground(new java.awt.Color(255, 51, 0));
        jMenu4.setText("Logout");
        jMenu4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenu4MousePressed(evt);
            }
        });
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        jMenu6.setText("|");
        jMenu6.setEnabled(false);
        jMenuBar1.add(jMenu6);

        jMenu7.setBackground(new java.awt.Color(0, 153, 153));
        jMenu7.setForeground(new java.awt.Color(0, 153, 153));
        jMenu7.setText("Messages");
        jMenu7.setToolTipText("");
        jMenu7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenu7MousePressed(evt);
            }
        });
        jMenu7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu7ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addGap(181, 181, 181)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        About abut=new About();
        abut.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
// TODO add your handling code here:
// SaveAllClients();
    }//GEN-LAST:event_formWindowClosing

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
// TODO add your handling code here
        System.out.println("Reconnect");
        try {
            PasueTimer=false;
            StartTimer();
            ConnectSearch = new Socket_ip(this);
            
            
        } catch (Exception e) {
            
            
        }

    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
// TODO add your handling code here:
        
// JtableCellColor adw= new JtableCellColor();
        if (IsConnectedToServer && !this.UserEmployee.equals(null)){
            EmployeeControlPanel EMPCPanel =new EmployeeControlPanel(this);
            this.EmpCP=EMPCPanel;
            EMPCPanel.setVisible(true);
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jMenu7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu7ActionPerformed
// TODO add your handling code here:

    }//GEN-LAST:event_jMenu7ActionPerformed

    private void jMenu7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu7MousePressed
        if (IsConnectedToServer && !this.UserEmployee.equals(null)){
            CurrChatFrame.setVisible(true);
        }
        
    }//GEN-LAST:event_jMenu7MousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        
    }//GEN-LAST:event_formMouseReleased

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        
    }//GEN-LAST:event_jMenu4ActionPerformed

    private void jMenu4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MousePressed
        LogOut();
    }//GEN-LAST:event_jMenu4MousePressed

    private void jMenu3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MousePressed
        if (IsConnectedToServer && !this.UserEmployee.equals(null)){
            EmployeeControlPanel EMPCPanel =new EmployeeControlPanel(this);
            this.EmpCP=EMPCPanel;
            EMPCPanel.setVisible(true);
        }
    }//GEN-LAST:event_jMenu3MousePressed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        try {
            if (!IsConnectedToServer){
                this.serverAddr= JOptionPane.showInputDialog(rootPane, "Enter the new Server Ip","192.168.1.");
                this.port=(Integer.parseInt(JOptionPane.showInputDialog(rootPane, "Enter the new Port","1300")));
                ConnectToServer();
                this.ShowServerNotConnectedPanel(false);
            }else{
                
                JOptionPane.showMessageDialog(rootPane, "You Are Already Connected To Server");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            PasueTimer=true;
            this.ShowServerNotConnectedPanel(false);
            this.SetIsSearchingLabel(true);
            
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        this.ConnectSearch.Stop();
        PasueTimer=true;
        this.SetIsSearchingLabel(true);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped
    
    public void JtableSelectionChanged() {
    }
    
    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {
    }
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        JtableSelectionChanged();
        int row = jTable1.rowAtPoint(evt.getPoint());
        int column = jTable1.columnAtPoint(evt.getPoint());
        jTable1.changeSelection(row, column, false, false);
        if (evt.getButton() == 3) {
            jPopupMenu1.show(jTable1, evt.getX(), evt.getY());
        }
    }
    
    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == 127) {
            DeleteItemActionPerformed(null);
        }
        JtableSelectionChanged();
    }
    
    private void jTable1MouseDragged(java.awt.event.MouseEvent evt) {
    }
    
    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {
    }
    
    private void DeleteItemActionPerformed(java.awt.event.ActionEvent evt) {
        if (UserEmployee.Delete){
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            try {
                for (int I = 0; I <= CLients_List.toArray().length - 1; I++) {
                    if (CLients_List.get(I).TableItemIndex == GetSelectedClient().TableItemIndex) {
                        new SysLog("Client " + CLients_List.get(I).ClientName + " Removed");
                        CLients_List.remove(I);
                    }
                }
            } catch (Exception e) {
            } finally {
                model.removeRow(jTable1.getSelectedRow());
                this.jTable1.setModel(model);
                SaveAllClients();
            }
        }else{
            AddToStatusList(EmployeeRW.RWMssageDialog("Delete"));
            flashjtableBorder(Color.red, 3, 2);
        }
    }
    
    private void ChangePinActionPerformed(java.awt.event.ActionEvent evt) {
        this.jTable1.editCellAt(jTable1.getSelectedRow(), 6);
    }
    
    private void AddItemActionPerformed(java.awt.event.ActionEvent evt) {
        Add NewItem = new Add(this);
        NewItem.setVisible(true);
    }
    
    private void ChangeNameActionPerformed(java.awt.event.ActionEvent evt) {
        this.jTable1.editCellAt(jTable1.getSelectedRow(), 1);
    }
    
    private void ChangePhoneActionPerformed(java.awt.event.ActionEvent evt) {
        this.jTable1.editCellAt(jTable1.getSelectedRow(), 5);
    }
    
    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {
    }
    
    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {
        String value = this.jTextField1.getText();
        int SearchResultsFound = 0;
        for (int row = 0; row <= this.jTable1.getRowCount() - 1; row++) {
            for (int col = 0; col <= this.jTable1.getColumnCount() - 1; col++) {
                if (value.toLowerCase().equals(this.jTable1.getValueAt(row, col).toString().toLowerCase())) {
                    this.jTable1.scrollRectToVisible(this.jTable1.getCellRect(row, 0, true));
                    this.jTable1.setRowSelectionInterval(row, row);
                    
                    SearchResultsFound++;
                    break;
                }
            }
        }
        if (SearchResultsFound > 0) {
            new SysLog("Search Results Found [" + SearchResultsFound + "] for [" + value + "]");
        }
    }
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        
        Add NewItem = new Add(this);
        NewItem.setVisible(true);
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        SaveAllClients();
    }
    
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
    }
    
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
        if (IsConnectedToServer && !this.UserEmployee.equals(null)){
            Preferences Prefx = new Preferences(this);
            this.Pref=Prefx;
            Prefx.setVisible(true);
        }
    }
    
    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {
        About Aboutus = new About();
        Aboutus.setVisible(true);
    }
    
    private void DepositItemActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String DepositCash = JOptionPane.showInputDialog(rootPane, "Enter Value To Deposit", "Deposit Money", 1);
            double d = Double.valueOf(DepositCash);
            int newValue=GetSelectedClient().ClientAmount += (int) d;
            if ((int) d>0 && d == (int) d) {
                
                this.jTable1.setValueAt(newValue, this.jTable1.getSelectedRow(), 4);
                GetSelectedClient().ClientAmount  = newValue;
//  SaveAllClients();
                new SysLog("Deposited [" + ((int) d) + "] to [" + GetSelectedClient().ClientName + "]");
            }
        } catch (Exception e) {
            
            if (e.getMessage().contains("For input string")){
                JOptionPane.showMessageDialog(rootPane, "Value Must Be a Number");
            }else{
                System.out.println(e.getMessage());
            }
            
        }
    }
    
    private void WithdrawalitemActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String Withdrawal = JOptionPane.showInputDialog(rootPane, "Enter Value To Withdrawal", "Withdrawal Money", 1);
            double d = Double.valueOf(Withdrawal);
            int newValue = GetSelectedClient().ClientAmount -= (int) d;
            if (Withdrawal != "" && Withdrawal != null && d == (int) d) {
                if (GetSelectedClient().ClientAmount > (int) d) {
                    this.jTable1.setValueAt(newValue, this.jTable1.getSelectedRow(), 4);
                    GetSelectedClient().ClientAmount = newValue;
                    new SysLog("A new Withdrawal [" + ((int) d) + "] Form [" + GetSelectedClient().ClientName + "]");
                } else if (GetSelectedClient().ClientAmount == (int) d && JOptionPane.showConfirmDialog(rootPane, "Client Amount Equals Withdrawal Amount \n    Sure You Want To Proceed") == 0) {
                    this.jTable1.setValueAt(0, this.jTable1.getSelectedRow(), 4);
                    GetSelectedClient().ClientAmount = 0;
                } else if (GetSelectedClient().ClientAmount < (int) d) {
                    new SysLog("Client Dont Have Enough Money");
                    JOptionPane.showMessageDialog(rootPane, "Client Dont Have Enough Money");
                }
                SaveAllClients();
            }
        } catch (Exception e) {
            if (!e.getMessage().equals(null) && e.getMessage().contains("For input string")){
                JOptionPane.showMessageDialog(rootPane, "Value Must Be a Number");
            }else{
                System.out.println(e.getCause());
            }
        }
    }
    
    
    
    
    
    
    
    
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                    
//Nimbus
//Windwos
//CDE/Motif
//metal
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SystemBanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SystemBanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SystemBanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SystemBanking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                new SystemBanking().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AddItem;
    private javax.swing.JMenuItem BankingSystem;
    private javax.swing.JMenu Change;
    private javax.swing.JMenuItem ChangeName;
    private javax.swing.JMenuItem ChangePhone;
    private javax.swing.JMenuItem ChangePin;
    private javax.swing.JMenuItem DeleteItem;
    private javax.swing.JMenuItem DepositItem;
    private javax.swing.JMenuItem Withdrawalitem;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
}
