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
 * @author Wahyu Pratama
 */
public class Form_PenilaianKaryawan extends javax.swing.JFrame {
//private  Connection conn = new koneksi ().connect();
    String vId,vNm,vJbt,vmsk,vpk;
    private DefaultTableModel model;
    private static Statement st;
    /**
     * Creates new form form_penilaiankaryawan
     */
    public Form_PenilaianKaryawan() {
        initComponents();
        model = new DefaultTableModel();
        tabelpenilaiankaryawan.setModel(model);
        model.addColumn("ID");
        model.addColumn("nama");
        
        model.addColumn("Jabatan");
        model.addColumn("jumlah_masuk");
        model.addColumn("Penilaian_karyawan");
    
    getData();
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
            String sql = "SELECT * FROM penilaian_karyawan WHERE nama like '%"+c+"%'";
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
                Object[] obj = new Object[5];
                obj[0] = res.getString("id_karyawan");
                obj[1] = res.getString("nama");
                
                obj[2] = res.getString("jabatan");
                obj[3] = res.getString("jumlah_masuk");
                obj[4] = res.getString("penilaian");
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
        
        vmsk = tjabsen.getText();
        vpk = (String)tpk.getSelectedItem();
        vJbt = tjabatan.getText();
        //vGol = (String)gol.getSelectedItem();
    }
    public void save(){
        loadData();
        try{
        st = (Statement)koneksi.getKoneksi().createStatement();
//        String sql = "Insert into karyawan(nama,tgl_lahir,jk,alamat,noHP,jabatan,golongan)"
//                +"values('"+vNm+"','"+vTgl+"','"+vJk+"','"+vAl+"','"+vHp+"','"+vJbt+"','"+vGol+"')";
        String sql = "Insert into penilaian_karyawan(id_karyawan,nama,jabatan,jumlah_masuk,penilaian)"
                 +"values('"+vId+"','"+vNm+"','"+vJbt+"','"+vmsk+"','"+vpk+"')";
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
         vmsk  = "";
          vpk  = "";
       // vGol = "";
        tid.setText(null);
        tnama.setText(null);
        
        tjabatan.setText(null);
        tjabsen.setText(null);
        tpk.setSelectedIndex(0);
        //gol.setSelectedIndex(0);
    }
    public void selectData(){
        int i = tabelpenilaiankaryawan.getSelectedRow();
        if(i == -1){
            JOptionPane.showMessageDialog(null, "Tidak ada data terpilih!");
            return;
        }
        tnama.setText(""+model.getValueAt(i, 1));
        int index = tabelpenilaiankaryawan.getSelectedRow();
        
        tjabatan.setText(""+model.getValueAt(i, 2));
        tjabsen.setText(""+model.getValueAt(i, 3));
        tpk.setSelectedItem(""+model.getValueAt(i, 4));
        //gol.setSelectedItem(""+model.getValueAt(i, 7));
        tid.setText(""+model.getValueAt(i, 0));
    }
    public void update(){
        loadData();
        try{
           st = (Statement)koneksi.getKoneksi().createStatement();
           String sql = "update penilaian_karyawan set nama = '"+vNm+"',"
                   + "jabatan='"+vJbt+"',"
                   + "jumlah_masuk='"+vmsk+"',"
                   + "penilaian='"+vpk+"'where id_karyawan='"+vId+"'";
//                   + "jabatan='"+vJbt+"'
                  
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
                String sql = "Delete From penilaian_karyawan Where id_karyawan='"+vId+"'";
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
    public void itemTerpilih(){                              
        Data_Search4 DS = new Data_Search4();
        DS.fpkry = this;
        tnama.setText(nmKry);
        tjabatan.setText(jbtKry);
        

        //gol.setText(golKry);
    }
//    protected void aktif(){
//        tid.setEnabled(true);
//        tnama.setEnabled(true);
//        tjabatan.setEnabled(true);
//        tabsen.setEnabled(true);
//        tid.requestFocus();
//     } 
//       protected void kosong(){
//        tid.setText("");
//        tnama.setText("");
//        tjabatan.setText("");
//        tabsen.setText("");
//        tpk.setSelectedIndex(0);
//       }
//       
//       protected void datatable(){
//        Object[] Baris ={"ID Karyawan","Nama","Jabatan","Jumlah Masuk Dalam 1 Bulan","Penilaian Karyawan",};
//        tabmode = new DefaultTableModel(null,Baris);
//        tabelpenilaiankaryawan.setModel(tabmode);
//        String sql = "select * from penilaian_karyawan";
//        try{
//            java.sql.Statement stat = conn.createStatement();
//            ResultSet hasil = stat.executeQuery(sql);
//            while(hasil.next()){
//                String a = hasil.getString("id_karyawan");
//                String b = hasil.getString("nama");
//                String c = hasil.getString("jabatan");
//                String d = hasil.getString("jumlah_masuk");
//                String e = hasil.getString("penilaian_karyawan");
//                
//                String[] data={a,b,c,d,e};
//                tabmode.addRow (data);
//            }
//            
//        } catch (Exception e){
//        }
//       }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tid = new javax.swing.JTextField();
        tnama = new javax.swing.JTextField();
        tjabatan = new javax.swing.JTextField();
        tjabsen = new javax.swing.JTextField();
        tpk = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpenilaiankaryawan = new javax.swing.JTable();
        bclear = new javax.swing.JButton();
        bexit = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        ktg = new javax.swing.JComboBox();
        cr = new javax.swing.JTextField();
        src = new javax.swing.JButton();
        ref = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setText("ID Karyawan");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(302, 89, 72, 16);

        jLabel2.setText("Nama");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(302, 129, 33, 16);

        jLabel3.setText("Jabatan");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(302, 166, 44, 16);

        jLabel4.setText("Jumlah Masuk Dalam 1 Bulan");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(302, 209, 166, 16);

        jLabel5.setText("Penilaian Karyawan");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(302, 249, 111, 16);
        getContentPane().add(tid);
        tid.setBounds(523, 86, 145, 22);
        getContentPane().add(tnama);
        tnama.setBounds(523, 126, 145, 22);
        getContentPane().add(tjabatan);
        tjabatan.setBounds(523, 166, 145, 22);

        tjabsen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tjabsenMouseClicked(evt);
            }
        });
        getContentPane().add(tjabsen);
        tjabsen.setBounds(523, 206, 145, 22);

        tpk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sangat Baik", "Baik", "Kurang Baik", "Sangat Tidak Baik" }));
        getContentPane().add(tpk);
        tpk.setBounds(523, 246, 145, 22);

        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(302, 329, 82, 38);

        jButton2.setText("EDIT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(410, 330, 82, 39);

        jButton3.setText("DELETE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(520, 330, 82, 36);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("FORM PENILAIAN KARYAWAN PT. PUTRA TUNGGAL JAKARTA");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(186, 13, 596, 55);

        tabelpenilaiankaryawan.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelpenilaiankaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpenilaiankaryawanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelpenilaiankaryawan);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 480, 880, 100);

        bclear.setText("CLEAR");
        bclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bclearActionPerformed(evt);
            }
        });
        getContentPane().add(bclear);
        bclear.setBounds(620, 330, 80, 40);

        bexit.setText("EXIT");
        bexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bexitActionPerformed(evt);
            }
        });
        getContentPane().add(bexit);
        bexit.setBounds(710, 330, 80, 40);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        jLabel7.setText("Kategori");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(80, 10, 47, 16);

        ktg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "nama", "jabatan" }));
        jPanel1.add(ktg);
        ktg.setBounds(150, 10, 80, 22);
        jPanel1.add(cr);
        cr.setBounds(230, 10, 130, 22);

        src.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/View.gif"))); // NOI18N
        src.setText("Search");
        src.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        src.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        src.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srcActionPerformed(evt);
            }
        });
        jPanel1.add(src);
        src.setBounds(390, 10, 80, 45);

        ref.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Refresh.gif"))); // NOI18N
        ref.setText("Refresh");
        ref.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ref.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ref.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refActionPerformed(evt);
            }
        });
        jPanel1.add(ref);
        ref.setBounds(480, 10, 100, 45);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(70, 380, 790, 70);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gif/16x16/Text preview.gif"))); // NOI18N
        jButton6.setText("Search Karyawan");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6);
        jButton6.setBounds(710, 140, 153, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        update();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here
        delete();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
//        int ok = JOptionPane.showConfirmDialog(null,"Hapus","Konfirmasi Dialog", JOptionPane.YES_NO_CANCEL_OPTION);
//        if (ok==0){
//            String sql ="delete from penilaian_karyawan where id_karyawan='"+tid.getText()+"'";
//            try {
//               PreparedStatement stat = conn.prepareStatement(sql);
//               stat.executeUpdate();
//               JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
//               kosong();
//               tid.requestFocus();
//               datatable();
//            } catch (SQLException e){
//                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
//            }
//    }                        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tabelpenilaiankaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpenilaiankaryawanMouseClicked
        // TODO add your handling code here:
        selectData();
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        jButton1.setEnabled(false);
               

    }//GEN-LAST:event_tabelpenilaiankaryawanMouseClicked

    private void bclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bclearActionPerformed
        // TODO add your handling code here:
       kosong();
    }//GEN-LAST:event_bclearActionPerformed
    protected void kosong(){
        tnama.setText("");
        tid.setText("");
        tjabatan.setText("");
        tjabsen.setText("");
        
    }
    private void bexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bexitActionPerformed
     int konf = JOptionPane.showConfirmDialog(null, "Yakin Ingin menutup Form?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(konf == JOptionPane.YES_OPTION){
            this.dispose();
        }    
    }//GEN-LAST:event_bexitActionPerformed

    private void tjabsenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tjabsenMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tjabsenMouseClicked

    private void srcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_srcActionPerformed
        getData();
    }//GEN-LAST:event_srcActionPerformed

    private void refActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refActionPerformed
        cr.setText(null);
        getData();
    }//GEN-LAST:event_refActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Data_Search4 DS = new Data_Search4();
        DS.fpkry= this;
        DS.setVisible(true);
        DS.setResizable(false);    
        tjabsen.requestFocus();
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(Form_PenilaianKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_PenilaianKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_PenilaianKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_PenilaianKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_PenilaianKaryawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bclear;
    private javax.swing.JButton bexit;
    private javax.swing.JTextField cr;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox ktg;
    private javax.swing.JButton ref;
    private javax.swing.JButton src;
    private javax.swing.JTable tabelpenilaiankaryawan;
    private javax.swing.JTextField tid;
    private javax.swing.JTextField tjabatan;
    private javax.swing.JTextField tjabsen;
    private javax.swing.JTextField tnama;
    private javax.swing.JComboBox tpk;
    // End of variables declaration//GEN-END:variables
}
