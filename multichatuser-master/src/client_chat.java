
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Arrays;
import java.util.Base64;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class client_chat extends javax.swing.JFrame {

    static Socket s;
    static DataInputStream dis;
    static DataOutputStream dout;
    static String userName;

    static RSA rsa = new RSA();

    /**
     * Creates new form client_chat
     */
    public client_chat() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        msg_text = new javax.swing.JTextField();
        msg_send = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("CLIENT");

        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        msg_send.setText("Send");
        msg_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_sendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(msg_text)
                                .addGap(18, 18, 18)
                                .addComponent(msg_send, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msg_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(msg_send))
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_sendActionPerformed

        try {           // to handle the msg exception we got
            String msg = "";     //variable declared to send message
            msg = msg_text.getText();//to store the message in the text area

            String[] pathnames;

            // Creates a new File instance by converting the given pathname string
            // into an abstract pathname
            File f = new File("C:\\Users\\Nabajyoti\\Downloads\\multichatuser-master (1)\\multichatuser-master\\Public Keys");

            // Populates the array with names of files and directories
            pathnames = f.list();

            // For each pathname in the pathnames array
            if (!msg.equals("exit")) {
                for (String pathname : pathnames) {
                    // Print the names of files and directories
                    if (!pathname.equals(userName + ".txt")) {
                        System.out.println(pathname);
                        byte[] encryptMsg = rsa.encrypt(msg.getBytes(), pathname);

                        System.out.println("Encrpyt bytes " + encryptMsg);
                        String em = Base64.getEncoder().encodeToString(encryptMsg);

                        System.out.println("Encypte msg" + em);

                        dout.writeUTF(userName + "#" + em + "#" + pathname);
                    }
                }
            } else {
                dout.writeUTF(userName + "#" + msg);
            }
            
            msg_area.append("\t\t\t\t\t"+userName+ ":"+msg+"\n");

            msg_text.setText("");//after sending message, text area will be empty
        } catch (Exception e) {
            //handle the exception here
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_msg_sendActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        userName = JOptionPane.showInputDialog("Please enter your name: ");

        if (userName == null || userName.isEmpty()) {
            userName = "Guest";
        }
        jLabel1.setText(userName);

        rsa.generateKey(userName);

    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(client_chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(client_chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(client_chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(client_chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new client_chat().setVisible(true);
            }
        });

        try {                          // as the port number may be used by some other appliction so to handle the exception we use try catch

            String msgin = "";    // to read message

            s = new Socket("localhost", 1201); //create a new socket object and pass the parameter ip address and port no. 
            dis = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (!msgin.equals("Server:exit")) {                  //to read the message continuously
                msgin = dis.readUTF();
                //how many message is received by the socket is stored in UTF.UTF is a method of DataInputStream.
                String decrypted_msg = decryptMsg(msgin);

                if(!decrypted_msg.isEmpty()){
                    msg_area.append(decrypted_msg + "\n");
                }    
            }  //in a new line without deleting the previous chat

            System.out.println("deleting file" + userName);
            File f = new File("C:\\Users\\Nabajyoti\\Downloads\\multichatuser-master (1)\\multichatuser-master\\Public Keys", userName + ".txt");
            f.delete();

        } catch (Exception e) {
            //handle the exception here
        }

    }

    // method to send the encrpyted message to rsa decrypt method
    // returns decrypted message
    public static String decryptMsg(String msg) {
        int flag = 0;
        String[] msgWords = new String[3];
        String dcyMsg = "";

        msgWords = msg.split(":");

        if (msgWords[0].equals("Server")) {
            dcyMsg = msgWords[1];
            flag = 0;

        } else if (!msgWords[1].equals("Joined")) {
            if (msgWords[2].equals(userName + ".txt")) {
                // store the list of public key files 
                String[] pathnames;
                File f = new File("C:\\Users\\Nabajyoti\\Downloads\\multichatuser-master (1)\\multichatuser-master\\Public Keys");

                // Populates the array with names of files and directories
                pathnames = f.list();

                for (String pathname : pathnames) {
                    System.out.println(pathname);
                    // Print the names of files and directories
                    if (pathname.equals(msgWords[2])) {
                        System.out.println(pathname);
                        String word = msgWords[1];
                        byte[] dm = Base64.getDecoder().decode(word);
                        dcyMsg = new String(rsa.decrypt(dm));
                        flag = 0;
                        break;
                    } else {
                        flag++;
                    }
                }
            } else {
                flag++;
            }

        } else {
            dcyMsg = msgWords[1];
            flag = 0;
        }

        if (flag == 0) {
            return msgWords[0] + " : " + dcyMsg;
        }
        return dcyMsg;

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea msg_area;
    private javax.swing.JButton msg_send;
    private javax.swing.JTextField msg_text;
    // End of variables declaration//GEN-END:variables
}
