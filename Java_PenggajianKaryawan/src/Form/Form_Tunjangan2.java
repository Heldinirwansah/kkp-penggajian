/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;
import java.sql.*;
import koneksiDB.koneksi;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 *
 * @author syahrul
 */
public class Form_Karyawan extends javax.swing.JFrame {
    private DefaultTableModel model;
    //String vNm,vTgl,vJk,vAl,vJbt,vGol,vHp;
    String vId,vNm,vTgl,vJk,vAl,vJbt,vHp;
    
    private static Statement st;

    public Form_Karyawan() {
        initComponents();
        model = new DefaultTableModel();
        tbl.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("TglLahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Alamat");
        model.addColumn("NoHP");
        model.addColumn("Jabatan");
//        model.addColumn("Golongan");
        getData();
        buttonGroup1.add(lk);
        buttonGroup1.add(pr);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((screenSize.width - frameSize.width)/2,(screenSize.height-frameSize.height)/2);
    }
    public void getData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        String k = (String)ktg.getSelectedItem();
        String c = cr.getText();
        try{
            st = (Statement) koneksi.getKoneksi().createStatement();
            String sql = "SELECT * FROM karyawan WHERE "+k+" like '%"+c+"%'";
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
                Object[] obj = new Object[8];
                obj[0] = res.getString("karyawanID");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("tgl_lahir");
                obj[3] = res.getString("jk");
                obj[4] = res.getString("alamat");
                obj[5] = res.getString("noHP");
                obj[6] = res.getString("jabatan");
//                obj[7] = res.getString("golongan");
                
                model.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    public void loadData(){
        vId = tid.getText();
        vNm = nm.getText();
        String tampilan ="yyyy-MM-dd" ; 
        SimpleDateFormat fm = new SimpleDateFormat(tampilan); 
        vTgl = String.valueOf(fm.format(tg.getDate()));
        if(lk.isSelected()){
            vJk = "Laki-laki";
        }else{
            vJk = "Perempuan";
        }
        vAl = almt.getText();
        vHp = hp.getText();
        vJbt = (String)jbtt.getSelectedItem();
        //vGol = (String)gol.getSelectedItem();
    }
    public void save(){
        loadData();
        try{
        st = (Statement)koneksi.getKoneksi().createStatement();
//        String sql = "Insert into karyawan(nama,tgl_lahir,jk,alamat,noHP,jabatan,golongan)"
//                +"values('"+vNm+"','"+vTgl+"','"+vJk+"','"+vAl+"','"+vHp+"','"+vJbt+"','"+vGol+"')";
        String sql = "Insert into karyawan(karyawanID,nama,tgl_lahir,jk,alamat,noHP,jabatan)"
                 +"values('"+vId+"','"+vNm+"','"+vTgl+"','"+vJk+"','"+vAl+"','"+vHp+"','"+vJbt+"')";
        PreparedStatement p = (PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
        p.executeUpdate(sql);
        getData();
        reset();
        nm.requestFocus();
        JOptionPane.showMessageDialog(null, "Data Berhasil DiSimpan!");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, "Data Gagal DiSimpan!");
            reset();
        }
    }
    public void reset(){
        vId = "";
        vNm  = "";
        vTgl = "";
        vJk  = "";
        vAl  = "";
        vHp  = "";
        vJbt = "";
       // vGol = "";
        tid.setText(null);
        nm.setText(null);
        tg.setDate(null);
        pr.setSelected(false);
        lk.setSelected(false);
        almt.setText(null);
        hp.setText(null);
        jbtt.setSelectedIndex(0);
        //gol.setSelectedIndex(0);
    }
    public void selectData(){
        int i = tbl.getSelectedRow();
        if(i == -1){
            JOptionPane.showMessageDialog(null, "Tidak ada data terpilih!");
            return;
        }
        nm.setText(""+model.getValueAt(i, 1));
         try {
            int index = tbl.getSelectedRow();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(index, 2));
            tg.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(Form_Karyawan.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(model.getValueAt(i, 3).equals("P")){
            pr.setSelected(true);
            lk.setSelected(false);
        }else{
            pr.setSelected(false);
            lk.setSelected(true);
        }
        almt.setText(""+model.getValueAt(i, 4));
        hp.setText(""+model.getValueAt(i, 5));
        jbtt.setSelectedItem(""+model.getValueAt(i, 6));
        //gol.setSelectedItem(""+model.getValueAt(i, 7));
        tid.setText(""+model.getValueAt(i, 0));
    }
    public void update(){
        loadData();
        try{
           st = (Statement)koneksi.getKoneksi().createStatement();
           String sql = "update karyawan set nama = '"+vNm+"',"
                   + "tgl_lahir='"+vTgl+"',"
                   + "jk='"+vJk+"',"
                   + "alamat='"+vAl+"',"
                   + "noHP='"+vHp+"',"
                   + "jabatan='"+vJbt+"'where karyawanID='"+vId+"'";
                  
        PreparedStatement p = (PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
        p.executeUpdate();
        getData();
        reset();
        nm.requestFocus();
        JOptionPane.showMessageDialog(null, "Data Berhasil DiUpdate");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, "Data Gagal DiUpdate!");
            reset();
        }
    }
    public void delete(){
        loadData();
        int psn = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?","Konfirmasi",
                JOptionPane.OK_CANCEL_OPTION);
        if(psn == JOptionPane.OK_OPTION){
            try{
                st = (Statement) koneksi.getKoneksi().createStatement();
                String sql = "Delete From karyawan Where karyawanID='"+vId+"'";
                PreparedStatement p =(PreparedStatement) koneksi.getKoneksi().prepareCall(sql);
                p.executeUpdate();
                getData();
                reset();
                tid.requestFocus();
                JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, "Data Gagal DiHapus!");
                reset();
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        nm = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tid = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Delete.gif"))); // NOI18N
        jButton3.setText("Delete");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Exit.gif"))); // NOI18N
        jButton4.setText("Exit");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        nm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nmActionPerformed(evt);
            }
        });

        jLabel2.setText("Nama");

        jLabel8.setText("ID Karyawan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tid, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(nm))
                .addGap(42, 42, 42))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nm)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Save.gif"))); // NOI18N
        jButton1.setText("Save");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 766, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Modify.gif"))); // NOI18N
        jButton2.setText("Update");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_broom_32px.png"))); // NOI18N
        jButton6.setText("Clear");
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(16, 16, 16)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(120, 120, 120))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(108, 108, 108)))))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        save();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        selectData();
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        jButton1.setEnabled(false);
    }//GEN-LAST:event_tblMouseClicked
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        update();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        delete();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int konf = JOptionPane.showConfirmDialog(null, "Yakin Ingin menutup Form?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(konf == JOptionPane.YES_OPTION){
            this.dispose();
        }    
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        kosong();
    }//GEN-LAST:event_jButton6ActionPerformed

     protected void kosong(){
        nm.setText("");
        tid.setText("");
        almt.setText("");
        hp.setText("");
        
        lk.setSelected(false);
        pr.setSelected(false);

    }
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
            java.util.logging.Logger.getLogger(Form_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Karyawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nm;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField tid;
    // End of variables declaration//GEN-END:variables

}
