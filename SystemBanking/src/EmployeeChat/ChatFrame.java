package EmployeeChat;


import com.socket.Message;
import com.socket.SocketClient;
import com.ui.SystemBanking;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class ChatFrame extends javax.swing.JFrame {
    
    public SocketClient client;
    public int port;
    public String serverAddr, username, password;
    public Thread clientThread;
    public DefaultListModel model;
    public DefaultListModel model2;
    
    
    public ChatFrame() {
        initComponents();
        
        model.addElement("All");
        jList1.setSelectedIndex(0);
        updateallTable();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        
        this.addWindowListener(new WindowListener() {
            
            @Override public void windowOpened(WindowEvent e) {
                
                
             updateallTable();
                
                
            }
            @Override public void windowClosing(WindowEvent e) {
                
                
            }
            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });
        
        
    }
    
    public void updateallTable(){
         if (jList1.getSelectedIndex()==0){
            try {
                
                SystemBanking.EmployeeClient.send(new Message("RequestMSGList", username,"GetMSGLIST", jList1.getSelectedValue().toString()));
                
            } catch (Exception e) {
                
            }
        }else{
            
            
        }
    } 
    
    private ListCellRenderer<? super String> getRenderer(int TabIndex ) {
        return new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super
                        .getListCellRendererComponent(list, value, index, isSelected,
                                cellHasFocus);
                try{
                    
                    if (!value.equals("All") && TabIndex==0){
                        listCellRendererComponent.setIcon(new ImageIcon(getClass().getResource("/Images/Circle_Green.png")));
                    }else{
                        
                        listCellRendererComponent.setIcon(new ImageIcon(getClass().getResource("/Images/Circle_Red.png")));
                    }
                }catch(Exception e){
                    
                }
                
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0,
                        0, 1, 0, Color.BLACK));
                
                return listCellRendererComponent;
            }
        };
    }
    public boolean isWin32() {
        return System.getProperty("os.name").startsWith("Windows");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dynamicPanelList1 = new EmployeeChat.DynamicPanelList();
        dynamicPanelList2 = new EmployeeChat.DynamicPanelList();
        dynamicPanelList3 = new EmployeeChat.DynamicPanelList();
        dynamicPanelList4 = new EmployeeChat.DynamicPanelList();
        dynamicPanelList5 = new EmployeeChat.DynamicPanelList();
        dynamicPanelList6 = new EmployeeChat.DynamicPanelList();
        dynamicPanelList7 = new EmployeeChat.DynamicPanelList();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        dynamicPanelList8 = new EmployeeChat.DynamicPanelList();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel5.setText("Message : ");

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jButton4.setText("Send Message ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jList1.setModel((model = new DefaultListModel()));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ONLINE", jPanel1);

        jList2.setModel((model2 = new DefaultListModel()));
        jScrollPane3.setViewportView(jList2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("OFFILINE", jPanel2);

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dynamicPanelList8, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(211, 211, 211)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1)
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dynamicPanelList8, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Offline");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //SendMessage();
        
        
    }//GEN-LAST:event_jButton4ActionPerformed
    public void SendMessage(){
        try{
            String msg = jTextField4.getText();
            String target = jList1.getSelectedValue().toString();
            MSG NEWMSG=new MSG(Calendar.getInstance().getTime(),null,jTextField4.getText(),null,target,SystemBanking.UserEmployee.UserName);
            if(!msg.isEmpty() && !target.isEmpty()){
                jTextField4.setText("");
                SystemBanking.EmployeeClient.send(new Message("message", SystemBanking.UserEmployee.UserName,NEWMSG, target));
                if (!target.equals("All"))
                    this.dynamicPanelList8.ADDMessage(NEWMSG,false);
            }
        }catch(Exception e){
            System.out.println("ERROR "+e.getMessage());
        }
    }
    public JTabbedPane getTabbedPane(){
        return jTabbedPane1;
    }    public void REND(int TabIndex){
        if (TabIndex==0){
            jList1.setCellRenderer(getRenderer(TabIndex));
        }else{
            jList2.setCellRenderer(getRenderer(TabIndex));
        }
        
    }
    

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==10){
            SendMessage();
            
            
        }
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
       updateallTable();
    }//GEN-LAST:event_jList1MouseClicked
    public void AddMSGList(ArrayList<MSG> MSGLIST){
        
        
        try{
            
            
            dynamicPanelList8.mainList.removeAll();
            if ( jList1.getSelectedValue().equals("All")){
                for (MSG msg: MSGLIST){
                    if (!msg.equals(null))
                        if(msg.Sender.equals(SystemBanking.username)){
                            this.dynamicPanelList8.ADDMessage(msg,false);
                        }else {
                            this.dynamicPanelList8.ADDMessage(msg,true);
                        }
                }
            }
        }catch(Exception e){
            
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //HIS=new History();        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1ActionPerformed
    public void HandleMsg(Message msg,SystemBanking ui){
        //SystemBanking.UserEmployee.m
        for (int i=0;i<jList1.getModel().getSize();i++){
            
            if (model.getElementAt(i).equals(msg.sender)){
                jList1.setSelectedIndex(i);
                break;
            }
            
        }
        
         if(SystemBanking.UserEmployee.ClientGUISettings.Can_Show_message_windows_after_reciving_msg){
              this.setVisible(true);
         }
           
        if(msg.recipient.equals(ui.username)){
           
            //  jTextArea1.append("["+msg.sender +" > Me] : " + msg.content + "\n");
            MSG NEWMSG=(MSG)msg.content;
            NEWMSG.ReciveDate=Calendar.getInstance().getTime();
            if (!NEWMSG.equals(null))
                this.dynamicPanelList8.ADDMessage(NEWMSG,true);
        }
        else if (msg.recipient.equals("All")){
            jList1.setSelectedIndex(0);
            //    jTextArea1.append("["+ msg.sender +" > "+ msg.recipient +"] : " + msg.content + "\n");
            MSG NEWMSG=(MSG)msg.content;
            NEWMSG.ReciveDate=Calendar.getInstance().getTime();
            if (!NEWMSG.equals(null))
                this.dynamicPanelList8.ADDMessage(NEWMSG,false);
        }
    }
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception ex){
            System.out.println("Look & Feel exception");
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private EmployeeChat.DynamicPanelList dynamicPanelList1;
    private EmployeeChat.DynamicPanelList dynamicPanelList2;
    private EmployeeChat.DynamicPanelList dynamicPanelList3;
    private EmployeeChat.DynamicPanelList dynamicPanelList4;
    private EmployeeChat.DynamicPanelList dynamicPanelList5;
    private EmployeeChat.DynamicPanelList dynamicPanelList6;
    private EmployeeChat.DynamicPanelList dynamicPanelList7;
    private EmployeeChat.DynamicPanelList dynamicPanelList8;
    private javax.swing.JButton jButton1;
    public javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
