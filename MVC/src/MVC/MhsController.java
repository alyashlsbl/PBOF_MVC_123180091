
package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.*;

public class MhsController {
    MhsModel mhsmodel;
    MhsView mhsview;
    MhsDAO mhsdao;
    
    public MhsController(MhsModel mhsmodel, MhsView mhsview, MhsDAO mhsdao){
        this.mhsmodel = mhsmodel;
        this.mhsview = mhsview;
        this.mhsdao = mhsdao;
        
        if(mhsdao.getJmlData()!=0) {
            String dataMahasiswa[][] = mhsdao.readMahasiswa();
            mhsview.tabel.setModel((new JTable(dataMahasiswa, mhsview.namaKolom)).getModel());
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak ada");
        }
        
        mhsview.simpan.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String nim = mhsview.getNim();
                String nama = mhsview.getNama();
                String alamat = mhsview.getAlamat();
                if(nim.isEmpty() || nama.isEmpty() || alamat.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Harap isi semua field");
                } else {
                    mhsmodel.setMhsModel(nim, nama, alamat);
                    mhsdao.Insert(mhsmodel);
                    
                    String dataMahasiswa[][] = mhsdao.readMahasiswa();
                    mhsview.tabel.setModel((new JTable(dataMahasiswa,  mhsview.namaKolom)).getModel());
                }
            }
        });
        
        
        mhsview.tabel.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e){
               super.mouseClicked(e);
               int baris = mhsview.tabel.getSelectedRow();
               int kolom = mhsview.tabel.getSelectedColumn();  
               
               String dataterpilih = mhsview.tabel.getValueAt(baris, 0).toString();
               mhsview.delete.setEnabled(true);
               mhsview.delete.addActionListener((ActionEvent f) -> {
                  mhsmodel.setNim(dataterpilih);
                  mhsdao.Delete(mhsmodel);
                  String dataMahasiswa[][] = mhsdao.readMahasiswa();
                mhsview.tabel.setModel(new JTable(dataMahasiswa,mhsview.namaKolom).getModel());
                }); 
           }
        });
        
        
    }
    
}
