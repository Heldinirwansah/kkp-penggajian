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
 * @author ASUS
 */
public class Form_Tunjangan extends javax.swing.JFrame {
    String vId,vNm,vJbt,vTj;
    private DefaultTableModel model;
    private static Statement st;
    /**
     * Creates new form Form_Tunjangan
     */
    public Form_Tunjangan() {
        initComponents();
       
        model = new DefaultTableModel();
        tabeltunjangan.setModel(model);
        model.addColumn("ID Karyawan");
        model.addColumn("Nama");
        model.addColumn("Jabatan");
        model.addColumn("Tunjangan");
     
    
    getData();
        iniedit.setEnabled(false);
        inidelete.setEnabled(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((screenSize.width - frameSize.width)/2,(screenSize.height-frameSize.height)/2);
    }
  public void getData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
//        String k = (String)ktg.getSelectedItem();
        String c = "";
        c = cr.getText();
        try{
            st = (Statement) koneksi.getKoneksi().createStatement();
            String sql = "SELECT t.tunjanganID, k.nama, k.jabatan, sum(t.tunjangan) jumlah_tunjangan FROM tunjangan t, karyawan k "
                    + "WHERE k.karyawanID = t.karyawanID "
//                    + "AND k.nama LIKE '%"+c+"%' OR k.jabatan LIKE '%"+c+"%' "
                    + "GROUP BY k.karyawanID";
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
                Object[] obj = new Object[4];
                obj[0] = res.getString("tunjanganID");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("jabatan");
                obj[3] = res.getString("jumlah_tunjangan");
//                obj[4] = res.getString("penilaian");
//                obj[7] = res.getString("golongan");
                
                model.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    public String nmKry, jbtKry;
 
    public String getnmKry() {
        return nmKry;
    }
 
    public String getjbtKry() {
        return jbtKry;
    }
    
    public void loadData(){
        vId = tid.getText();
        vNm = tnama.getText();
//        String tampilan ="yyyy-MM-dd" ; 
//        SimpleDateFormat fm = new SimpleDateFormat(tampilan); 
        
        vTj = ttunjangan.getText();
        vJbt = tjabatan.getText();
        //vGol = (String)gol.getSelectedItem();
    }
    
    public void save(){
        loadData();
        try{
        st = (Statement)koneksi.getKoneksi().createStatement();
//        String sql = "Insert into karyawan(nama,tgl_lahir,jk,alamat,noHP,jabatan,golongan)"
//                +"values('"+vNm+"','"+vTgl+"','"+vJk+"','"+vAl+"','"+vHp+"','"+vJbt+"','"+vGol+"')";
        String sql = "Insert into tunjangan(id_karyawan,nama,jabatan,tunjangan)"
                 +"values('"+vId+"','"+vNm+"','"+vJbt+"','"+vTj+"')";
        PreparedStatement p = (PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
        p.executeUpdate(sql);
        getData();
        reset();
        tnama.requestFocus();
        JOptionPane.showMessageDialog(null, "Data Berhasil DiSimpan!");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, "Data Gagal DiSimpan!");
            reset();
        }
    }
    public void reset(){
        vId = "";
        vNm  = "";
        vJbt = "";
        vTj = "";

       // vGol = "";
        tid.setText(null);
        tnama.setText(null);
        tjabatan.setText(null);
        ttunjangan.setText(null);

        //gol.setSelectedIndex(0);
    }
    public void selectData(){
        int i = tabeltunjangan.getSelectedRow();
        if(i == -1){
            JOptionPane.showMessageDialog(null, "Tidak ada data terpilih!");
            return;
        }
        tnama.setText(""+model.getValueAt(i, 1));
        int index = tabeltunjangan.getSelectedRow();
        
        tjabatan.setText(""+model.getValueAt(i, 2));
        ttunjangan.setText(""+model.getValueAt(i, 3));
       
        //gol.setSelectedItem(""+model.getValueAt(i, 7));
        tid.setText(""+model.getValueAt(i, 0));
    }
    public void update(){
        loadData();
        try{
           st = (Statement)koneksi.getKoneksi().createStatement();
           String sql = "update tunjangan set nama = '"+vNm+"',"
                   + "jabatan='"+vJbt+"',"
                   + "tunjangan='"+vTj+"'where id_karyawan='"+vId+"'";
                  
        PreparedStatement p = (PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
        p.executeUpdate();
        getData();
        reset();
        tid.requestFocus();
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
                String sql = "Delete From tunjangan Where id_karyawan='"+vId+"'";
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
//    protected void kosong(){
//        tnama.setText("");
//        tid.setText("");
//        tjabatan.setText("");
//        
//        ttunjangan.setText("");
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tid = new javax.swing.JTextField();
        tnama = new javax.swing.JTextField();
        tjabatan = new javax.swing.JTextField();
        ttunjangan = new javax.swing.JTextField();
        inisave = new javax.swing.JButton();
        iniedit = new javax.swing.JButton();
        inidelete = new javax.swing.JButton();
        iniexit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabeltunjangan = new javax.swing.JTable();
        iniclear = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cr = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Yu Gothic", 1, 18)); // NOI18N
        jLabel1.setText("FORM TUNJANGAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(390, 390, 390)
                .addComponent(jLabel1)
                .addContainerGap(436, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(25, 25, 25))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1000, 100);

        jLabel2.setText("Nama");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(290, 180, 60, 14);

        jLabel3.setText("ID Karyawan");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(290, 130, 90, 14);

        jLabel4.setText("Jabatan");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(290, 240, 50, 14);

        jLabel5.setText("Tunjangan");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(280, 290, 80, 14);
        getContentPane().add(tid);
        tid.setBounds(420, 130, 100, 20);
        getContentPane().add(tnama);
        tnama.setBounds(420, 180, 100, 20);
        getContentPane().add(tjabatan);
        tjabatan.setBounds(420, 240, 100, 20);
        getContentPane().add(ttunjangan);
        ttunjangan.setBounds(420, 290, 100, 20);

        inisave.setText("SAVE");
        inisave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inisaveActionPerformed(evt);
            }
        });
        getContentPane().add(inisave);
        inisave.setBounds(570, 135, 80, 30);

        iniedit.setText("EDIT");
        iniedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inieditActionPerformed(evt);
            }
        });
        getContentPane().add(iniedit);
        iniedit.setBounds(570, 185, 80, 30);

        inidelete.setText("DELETE");
        inidelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inideleteActionPerformed(evt);
            }
        });
        getContentPane().add(inidelete);
        inidelete.setBounds(570, 235, 80, 30);

        iniexit.setText("EXIT");
        iniexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniexitActionPerformed(evt);
            }
        });
        getContentPane().add(iniexit);
        iniexit.setBounds(710, 195, 60, 40);

        tabeltunjangan.setModel(new javax.swing.table.DefaultTableModel(
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
        tabeltunjangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeltunjanganMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabeltunjangan);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(40, 420, 930, 120);

        iniclear.setText("CLEAR");
        iniclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniclearActionPerformed(evt);
            }
        });
        getContentPane().add(iniclear);
        iniclear.setBounds(570, 285, 80, 30);

        jLabel6.setText("Kata Kunci Pencarian");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(160, 350, 110, 30);
        getContentPane().add(cr);
        cr.setBounds(290, 350, 140, 30);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Text preview.gif"))); // NOI18N
        jButton6.setText("Search Karyawan");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6);
        jButton6.setBounds(700, 270, 137, 25);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Refresh.gif"))); // NOI18N
        jButton7.setText("Refresh");
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7);
        jButton7.setBounds(560, 340, 71, 43);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/View.gif"))); // NOI18N
        jButton5.setText("Search");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(460, 340, 65, 43);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inisaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inisaveActionPerformed
        // TODO add your handling code here:
        save();
//        String sql = "insert into tunjangan values(?,?,?,?)";
//        try{
//            PreparedStatement stat = conn.prepareStatement(sql);
//            stat.setString(1, tid.getText());
//            stat.setString(2, tnama.getText());
//            
//           
//            stat.setString(3, tjabatan.getText());
//            
//            stat.setString(4, ttunjangan.getText());
//           
//            
//            stat.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Data Berhasil disimpan");
//            kosong();
//            tid.requestFocus();
//            datatable();
//            
//        } catch(SQLException e){
//            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan"+e);
//        }
    }//GEN-LAST:event_inisaveActionPerformed

    private void inieditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inieditActionPerformed
        // TODO add your handling code here:
         update();
        inisave.setEnabled(true);
        iniedit.setEnabled(false);
        inidelete.setEnabled(false);
//         try{
//            String sql = "update tunjangan set nama=?,jabatan=?,tunjangan=?,where id_karyawan=?";
//            PreparedStatement stat = conn.prepareStatement(sql);
//            stat.setString(1, tnama.getText());
//            
//          
//            stat.setString(2, tjabatan.getText());
//            
//            stat.setString(3, ttunjangan.getText());
//            
//            stat.setString(4, tid.getText());
//            
//            stat.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
//            kosong();
//            tid.requestFocus();
//            datatable();
//            
//        } catch(SQLException e){
//            JOptionPane.showMessageDialog(null, "Data Gagal Diubah"+e);
//        }
    }//GEN-LAST:event_inieditActionPerformed

    private void inideleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inideleteActionPerformed
        // TODO add your handling code here:
         delete();
        inisave.setEnabled(true);
        iniedit.setEnabled(false);
        inidelete.setEnabled(false);
//        int ok =JOptionPane.showConfirmDialog(null,"hapus","Konfirmasi Dialog",JOptionPane.YES_NO_CANCEL_OPTION);
//        if(ok==0){
//            String sql ="delete from tunjangan where id_karyawan='"+tid.getText()+"'";
//            try{
//                PreparedStatement stat = conn.prepareStatement(sql);
//                stat.executeUpdate();
//                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
//                kosong();
//                tid.requestFocus();
//                datatable();
//            }catch (SQLException e){
//                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus"+e);
//            }
//        }
    }//GEN-LAST:event_inideleteActionPerformed
    public void itemTerpilih(){                              
        Data_Search3 DS = new Data_Search3();
        DS.ft = this;
       tnama.setText(nmKry);
        tjabatan.setText(jbtKry);
        

        //gol.setText(golKry);
    }
    private void iniclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniclearActionPerformed
        // TODO add your handling code here:
         kosong();
    }
    protected void kosong(){
        tnama.setText("");
        tid.setText("");
        tjabatan.setText("");
        ttunjangan.setText("");
    }//GEN-LAST:event_iniclearActionPerformed

    private void iniexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniexitActionPerformed
        // TODO add your handling code here:
         int konf = JOptionPane.showConfirmDialog(null, "Yakin Ingin menutup Form?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(konf == JOptionPane.YES_OPTION){
            this.dispose();
        }    
    }//GEN-LAST:event_iniexitActionPerformed

    private void tabeltunjanganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeltunjanganMouseClicked
        // TODO add your handling code here:
         selectData();
        iniedit.setEnabled(true);
        inidelete.setEnabled(true);
        inisave.setEnabled(false);
//        int bar = tabeltunjangan.getSelectedRow();
//        String a = tabmode.getValueAt(bar,0).toString();
//        String b = tabmode.getValueAt(bar,1).toString();
//        String c = tabmode.getValueAt(bar,2).toString();
//        String d = tabmode.getValueAt(bar,3).toString();
//        
//        tid.setText(a);
//        tnama.setText(b);
//        
//        
//    
//        tjabatan.setText(c);
//        ttunjangan.setText(d);

    }//GEN-LAST:event_tabeltunjanganMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Data_Search3 DS = new Data_Search3();
        DS.ft = this;
        DS.setVisible(true);
        DS.setResizable(false);
       ttunjangan.requestFocus();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        cr.setText(null);
        getData();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        getData();
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Tunjangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Tunjangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Tunjangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Tunjangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Tunjangan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cr;
    private javax.swing.JButton iniclear;
    private javax.swing.JButton inidelete;
    private javax.swing.JButton iniedit;
    private javax.swing.JButton iniexit;
    private javax.swing.JButton inisave;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabeltunjangan;
    private javax.swing.JTextField tid;
    private javax.swing.JTextField tjabatan;
    private javax.swing.JTextField tnama;
    private javax.swing.JTextField ttunjangan;
    // End of variables declaration//GEN-END:variables
}
