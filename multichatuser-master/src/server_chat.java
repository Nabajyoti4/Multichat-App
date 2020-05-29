
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */

// ClientHandler class 
class ClientHandler implements Runnable  
{ 
    Scanner scn = new Scanner(System.in); 
    private String name; 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    Socket s; 
    boolean isloggedin; 
      
    // constructor 
    public ClientHandler(Socket s, String name, 
                            DataInputStream dis, DataOutputStream dos) throws IOException { 
        this.dis = dis; 
        this.dos = dos; 
        this.name = name; 
        this.s = s; 
        this.isloggedin=true; 
        
        broadcast(name, "Joined");
    } 
  
    @Override
    public void run() { 
  
        String received; 
        while (true)  
        { 
            try
            { 
                // receive the string 
                received = dis.readUTF(); 
                  
                //System.out.println(received); 
                System.out.println(received);
                String[] st = received.split("#");
                String sender = st[0]; 
                String message = st[1]; 
                  
                if(message.equals("exit")){ 
                    broadcast(sender, "Leave the Chat");
                    File f = new File("C:\\Users\\Nabajyoti\\Downloads\\multichatuser-master (1)\\multichatuser-master\\Public Keys",sender+".txt");
                    f.delete();
                    this.isloggedin=false; 
                    this.s.close(); 
                    break; 
                } 
                
                server_chat.createFile(received);
                   
  
                // search for the recipient in the connected devices list. 
                // ar is the vector storing client of active users 
                for (ClientHandler mc : server_chat.ar)  
                {
                    if (mc.isloggedin == true && mc != this)
                    {
                        mc.dos.writeUTF(sender+":"+message+":"+st[2]); 
                    }
                } 
            } catch (IOException e) { 
                  
                e.printStackTrace(); 
            } 
              
        } 
        
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
    
    
    
    
public void broadcast(String sender, String message) throws IOException{
         for (ClientHandler mc : server_chat.ar)  
                {   // search for the recipient in the connected devices list. 
                    // ar is the vector storing client of active users
                    // serach for loggined users
                    // leave the user who send the message
                    if (mc.isloggedin == true && mc != this)
                    {
                        mc.dos.writeUTF(sender+":"+message); 
                    }
                } 
    }    
    
    
} 



public class server_chat extends javax.swing.JFrame {

    // Vector to store active clients 
    static Vector<ClientHandler> ar = new Vector<>(); 
      
    // counter for clients 
    static int i = 0;
    
    // file to store the encrypted chat session
    //static File encryptChat = new File("C:\\Users\\Nabajyoti\\Downloads\\multichatuser-master (1)\\multichatuser-master\\Public Keys","EncryptionFile.txt");
    /**
     * Creates new form server_chat
     */
    public server_chat() {
        initComponents();
    }
    
    
    
public static void createFile(String msg){
     
       
         StringTokenizer st = new StringTokenizer(msg, "#"); 
         String sender = st.nextToken(); 
         String message = st.nextToken();  
         
         msg_area.append(sender + " : " + message +"\n");
         
//         try {
//
//            if (encryptChat.createNewFile()) {
//                FileWriter myWriter = new FileWriter(encryptChat, true);
//                myWriter.write(sender + " : " + message +"\n");
//                myWriter.close();
//            } else {
//                FileWriter myWriter = new FileWriter(encryptChat, true);
//                myWriter.write(sender + " : " + message +"\n");
//                myWriter.close();
//            } 
//
//        } catch (IOException ex) {
//            System.out.println("file error" + ex.getMessage());
//        }
      
     
} 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.t
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        msg_text = new javax.swing.JTextField();
        msg_send = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        msg_send.setText("Send");
        msg_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_sendActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("SERVER");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(msg_send, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(msg_send))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_sendActionPerformed

            try {           // to handle the msg exception we got
                String msg="";     //variable declared to send message
                msg=msg_text.getText();//to store the message in the text area
                for (ClientHandler mc : server_chat.ar)  
                {
                    if (mc.isloggedin == true)
                    {
                        mc.dos.writeUTF("Server:"+msg); 
                    }
                } 
                msg_text.setText("");//after sending message, text area will be empty
                }catch(Exception e){
                            //handle the exception here
                }


        // TODO add your handling code here:
    }//GEN-LAST:event_msg_sendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(server_chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(server_chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(server_chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(server_chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new server_chat().setVisible(true);
            }
        });
        
        try {
            // server is listening on port 1201
            ServerSocket ss = new ServerSocket(1201); 

            Socket s; 

            // running infinite loop for getting 
            // client request 
            while (true)  
            { 
                // Accept the incoming request 
                s = ss.accept(); 

                msg_area.append("New client request received : " + s + "\n"); 

                // obtain input and output streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 

                msg_area.append("Creating a new handler for this client... \n"); 

                // Create a new handler object for handling this request. 
                ClientHandler mtch = new ClientHandler(s,"client " + i, dis, dos); 

                // Create a new Thread with this object. 
                Thread t = new Thread(mtch); 

                msg_area.append("Adding this client to active client list \n"); 

                // add this client to active clients list 
                ar.add(mtch); 

                // start the thread. 
                t.start(); 

                // increment i for new client. 
                // i is used for naming only, and can be replaced 
                // by any naming scheme 
                i++; 

            } 
        }
        catch(Exception ex) {
                    
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea msg_area;
    private javax.swing.JButton msg_send;
    private javax.swing.JTextField msg_text;
    // End of variables declaration//GEN-END:variables
}