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
public class Form_Kesejahteraan extends javax.swing.JFrame {
         String vId,vNm,vJbt,vKls,vJmlh;
    private DefaultTableModel model;
    private static Statement st;
  
    public Form_Kesejahteraan() {
        initComponents();
        model = new DefaultTableModel();
        tabelsejahtera.setModel(model);
        model.addColumn("ID");
        model.addColumn("nama");
        model.addColumn("Jabatan");
        model.addColumn("kelas");
        model.addColumn("jumlah");
     
    
    getData();
        bedit.setEnabled(false);
        bdelete.setEnabled(false);
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
            String sql = "SELECT * FROM kesejahteraan WHERE nama like '%"+c+"%'";
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
                Object[] obj = new Object[5];
                obj[0] = res.getString("id_karyawan");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("jabatan");                
                obj[3] = res.getString("kelas_bpjs");
                obj[4] = res.getString("jumlah");
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
        vNm = nama.getText();
        vJbt = jabatan.getText();
        
//        String tampilan ="yyyy-MM-dd" ; 
//        SimpleDateFormat fm = new SimpleDateFormat(tampilan); 
        
        vKls = kelasbpjs.getText();
        vJmlh = jml.getText();

        //vGol = (String)gol.getSelectedItem();
    }
     public void save(){
        loadData();
        try{
        st = (Statement)koneksi.getKoneksi().createStatement();
//        String sql = "Insert into karyawan(nama,tgl_lahir,jk,alamat,noHP,jabatan,golongan)"
//                +"values('"+vNm+"','"+vTgl+"','"+vJk+"','"+vAl+"','"+vHp+"','"+vJbt+"','"+vGol+"')";
        String sql = "Insert into kesejahteraan(id_karyawan,nama,jabatan,kelas_bpjs,jumlah)"
                 +"values('"+vId+"','"+vNm+"','"+vJbt+"','"+vKls+"','"+vJmlh+"')";
        PreparedStatement p = (PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
        p.executeUpdate(sql);
        getData();
        reset();
        nama.requestFocus();
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
        vKls = "";
        vJmlh = "";


       // vGol = "";
        tid.setText(null);
        nama.setText(null);
        jabatan.setText(null);
        kelasbpjs.setText(null);
        jml.setText(null);


        //gol.setSelectedIndex(0);
    }
     public void selectData(){
        int i = tabelsejahtera.getSelectedRow();
        if(i == -1){
            JOptionPane.showMessageDialog(null, "Tidak ada data terpilih!");
            return;
        }
        nama.setText(""+model.getValueAt(i, 1));
        int index = tabelsejahtera.getSelectedRow();
        
        jabatan.setText(""+model.getValueAt(i, 2));
        kelasbpjs.setText(""+model.getValueAt(i, 3));
        jml.setText(""+model.getValueAt(i, 4));

       
        //gol.setSelectedItem(""+model.getValueAt(i, 7));
        tid.setText(""+model.getValueAt(i, 0));
    }
    public void update(){
        loadData();
        try{
           st = (Statement)koneksi.getKoneksi().createStatement();
           String sql = "update kesejahteraan set nama = '"+vNm+"',"
                   + "jabatan='"+vJbt+"',"
                   + "kelas_bpjs='"+vJbt+"',"
                   + "jumlah='"+vJmlh+"'where id_karyawan='"+vId+"'";
                  
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
                String sql = "Delete From kesejahteraan Where id_karyawan='"+vId+"'";
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
//    protected void aktif(){
//        nama.setEnabled(true);
//        tid.setEnabled(true);
//        jabatan.setEnabled(true);
//        kelasbpjs.setEnabled(true);
//        jml.setEnabled(true);
//
//    }
//    protected void kosong(){
//        nama.setText("");
//        tid.setText("");
//        jabatan.setText("");
//        kelasbpjs.setText("");
//        jml.setText("");
//    }
//    protected void datatable(){
//        Object[] Baris ={"Id_Karyawan","Nama","Jabatan","kelas","benefit"  
//        };
//        tabmode = new DefaultTableModel(null, Baris);
//        tabelsejahtera.setModel(tabmode);
//        String sql= "select * from kesejahteraan";
//        try{
//            java.sql.Statement stat = conn.createStatement();
//            ResultSet hasil = stat.executeQuery(sql);
//            while(hasil.next()){
//                String a = hasil.getString("id_karyawan");
//                String b = hasil.getString("nama");
//                String c = hasil.getString("jabatan");
//                String d = hasil.getString("kelas_bpjs");
//                String e = hasil.getString("jumlah");
//                
//                String[] data ={a,b,c,d,e};
//                tabmode.addRow(data);
//            }
//        } catch(Exception e){
//            
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tid = new javax.swing.JTextField();
        nama = new javax.swing.JTextField();
        jabatan = new javax.swing.JTextField();
        jml = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        kelasbpjs = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelsejahtera = new javax.swing.JTable();
        bsave = new javax.swing.JButton();
        bedit = new javax.swing.JButton();
        bdelete = new javax.swing.JButton();
        bclear = new javax.swing.JButton();
        bexit = new javax.swing.JButton();
        src = new javax.swing.JButton();
        ref = new javax.swing.JButton();
        cr = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        ktg = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Yu Gothic", 1, 18)); // NOI18N
        jLabel1.setText("FORM KESEJAHTERAAN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(jLabel1)
                .addContainerGap(393, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(40, 40, 40))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 990, 114);

        jLabel2.setText("Nama");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(390, 220, 33, 16);

        jLabel3.setText("Id Karyawan");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(390, 180, 71, 16);

        jLabel4.setText("Jabatan");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(390, 270, 44, 16);

        jLabel5.setText("JUMLAH Benefit");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(390, 350, 100, 16);
        getContentPane().add(tid);
        tid.setBounds(530, 172, 110, 30);
        getContentPane().add(nama);
        nama.setBounds(530, 212, 110, 30);
        getContentPane().add(jabatan);
        jabatan.setBounds(530, 262, 110, 30);
        getContentPane().add(jml);
        jml.setBounds(530, 342, 110, 30);

        jLabel6.setText("Kelas BPJS");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(390, 310, 70, 16);
        getContentPane().add(kelasbpjs);
        kelasbpjs.setBounds(530, 302, 110, 30);

        tabelsejahtera.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelsejahtera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsejahteraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelsejahtera);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 460, 950, 90);

        bsave.setText("SAVE");
        bsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsaveActionPerformed(evt);
            }
        });
        getContentPane().add(bsave);
        bsave.setBounds(710, 190, 100, 30);

        bedit.setText("EDIT");
        bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditActionPerformed(evt);
            }
        });
        getContentPane().add(bedit);
        bedit.setBounds(710, 230, 100, 30);

        bdelete.setText("DELETE");
        bdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdeleteActionPerformed(evt);
            }
        });
        getContentPane().add(bdelete);
        bdelete.setBounds(710, 270, 100, 30);

        bclear.setText("CLEAR");
        bclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bclearActionPerformed(evt);
            }
        });
        getContentPane().add(bclear);
        bclear.setBounds(710, 315, 100, 30);

        bexit.setText("EXIT");
        bexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bexitActionPerformed(evt);
            }
        });
        getContentPane().add(bexit);
        bexit.setBounds(710, 355, 100, 30);

        src.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/View.gif"))); // NOI18N
        src.setText("Search");
        src.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        src.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        src.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srcActionPerformed(evt);
            }
        });
        getContentPane().add(src);
        src.setBounds(560, 400, 73, 45);

        ref.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Refresh.gif"))); // NOI18N
        ref.setText("Refresh");
        ref.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ref.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ref.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refActionPerformed(evt);
            }
        });
        getContentPane().add(ref);
        ref.setBounds(650, 400, 77, 45);
        getContentPane().add(cr);
        cr.setBounds(380, 400, 160, 30);

        jLabel7.setText("Kategori");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(180, 410, 60, 16);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Text preview.gif"))); // NOI18N
        jButton6.setText("Search Karyawan");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6);
        jButton6.setBounds(120, 290, 153, 25);

        ktg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "nama", "jabatan" }));
        getContentPane().add(ktg);
        ktg.setBounds(270, 400, 70, 22);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelsejahteraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsejahteraMouseClicked
        // TODO add your handling code here:
         selectData();
        bedit.setEnabled(true);
        bdelete.setEnabled(true);
        bsave.setEnabled(false);

        
    }//GEN-LAST:event_tabelsejahteraMouseClicked

    private void bexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bexitActionPerformed
        // TODO add your handling code here:
         int konf = JOptionPane.showConfirmDialog(null, "Yakin Ingin menutup Form?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(konf == JOptionPane.YES_OPTION){
            this.dispose();
        }    
    }//GEN-LAST:event_bexitActionPerformed

    private void bclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bclearActionPerformed
        // TODO add your handling code here:
        kosong();
        
    }//GEN-LAST:event_bclearActionPerformed
    protected void kosong(){
        nama.setText("");
        tid.setText("");
        jabatan.setText("");
        kelasbpjs.setText("");
        jml.setText("");

    }                          
    private void srcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_srcActionPerformed
        getData();
    }//GEN-LAST:event_srcActionPerformed

    private void refActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refActionPerformed
        cr.setText(null);
        getData();
    }//GEN-LAST:event_refActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Data_Search5 DS = new Data_Search5();
        DS.fk = this;
        DS.setVisible(true);
        DS.setResizable(false);
        kelasbpjs.requestFocus();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void beditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditActionPerformed
        // TODO add your handling code here:
        update();
        bsave.setEnabled(true);
        bedit.setEnabled(false);
        bdelete.setEnabled(false);
    }//GEN-LAST:event_beditActionPerformed

    private void bsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsaveActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_bsaveActionPerformed

    private void bdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdeleteActionPerformed
        // TODO add your handling code here:
         delete();
        bsave.setEnabled(true);
        bedit.setEnabled(false);
        bdelete.setEnabled(false);
    }//GEN-LAST:event_bdeleteActionPerformed
    public void itemTerpilih(){                              
        Data_Search5 DS = new Data_Search5();
        DS.fk = this;
        nama.setText(nmKry);
        jabatan.setText(jbtKry);
        

        //gol.setText(golKry);
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
            java.util.logging.Logger.getLogger(Form_Kesejahteraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Kesejahteraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Kesejahteraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Kesejahteraan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Kesejahteraan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bclear;
    private javax.swing.JButton bdelete;
    private javax.swing.JButton bedit;
    private javax.swing.JButton bexit;
    private javax.swing.JButton bsave;
    private javax.swing.JTextField cr;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jabatan;
    private javax.swing.JTextField jml;
    private javax.swing.JTextField kelasbpjs;
    private javax.swing.JComboBox ktg;
    private javax.swing.JTextField nama;
    private javax.swing.JButton ref;
    private javax.swing.JButton src;
    private javax.swing.JTable tabelsejahtera;
    private javax.swing.JTextField tid;
    // End of variables declaration//GEN-END:variables
}
