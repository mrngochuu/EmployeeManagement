/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Do Ngoc Huu
 */
public class EmployeeManager extends javax.swing.JFrame {

    String fileName = "Employee.txt";
    EmployeeTableModel<Employee> model;
    EmployeeTableModel<Employee> pageModel;
    Vector<Employee> searchList;
    Vector<Integer> indexList;
    boolean addNew = true;
    boolean changed = false;
    boolean search = false;
    int[] indexes = {0, 1, 4};
    String[] header = {"Code", "Name", "Salary"};
    int totalPage, currentPage, sizeOfPage = 5;

    /**
     * Creates new form EmployeeManager
     */
    public EmployeeManager() {
        initComponents();
        this.setLocationRelativeTo(null);
        model = new EmployeeTableModel<Employee>(header, indexes);
        this.tblEmp.setModel(model);
        loadData();
        if (search == false) {
            computeTotalPage(model.getData());
            loadPage(model.getData());
        } else {
            txtSearchKeyReleased(null);
        }
        txtCode.setEnabled(!changed);

        Thread autoSave = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        btnSaveToFileActionPerformed(null);
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }

                }
            }
        });
        autoSave.start();
    }

    private void loadPage(Vector<Employee> data) {
        currentPage = Integer.parseInt(txtCurrPage.getText());
        pageModel = new EmployeeTableModel<Employee>(header, indexes);

        //compute size of Page in current Page
        int indexEnd = 0;
        if (currentPage < totalPage) {
            indexEnd = (currentPage - 1) * sizeOfPage + 5;
        } else if (currentPage == totalPage && data.size() > 5) {
            indexEnd = ((currentPage - 1) * sizeOfPage) + (data.size() % sizeOfPage);
        } else if (currentPage == totalPage && data.size() <= 5) {
            indexEnd = ((currentPage - 1) * sizeOfPage) + data.size();
        }

        //up to tblEmp
        for (int i = (currentPage - 1) * sizeOfPage; i < indexEnd; i++) {
            pageModel.getData().add(data.get(i));
        }
        tblEmp.setModel(pageModel);
        tblEmp.updateUI();
    }

    private void computeTotalPage(Vector<Employee> data) {
        int size = data.size();
        if (size <= 5) {
            totalPage = 1;
        } else if (size % sizeOfPage == 0) {
            totalPage = size / sizeOfPage;
        } else {
            totalPage = size / sizeOfPage + 1;
        }
        txtTotalPage.setText(totalPage + "");
    }

    public void loadData() {
        try {
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            String S;
            while ((S = bf.readLine()) != null) {
                S = S.trim();
                if (S.length() > 0) {
                    StringTokenizer stk = new StringTokenizer(S, ",");
                    String code = stk.nextToken();
                    String name = stk.nextToken();
                    String addr = stk.nextToken();
                    String sexStr = stk.nextToken();
                    boolean sex = (sexStr.equalsIgnoreCase("MALE") == true);
                    int salary = Integer.parseInt(stk.nextToken());
                    model.getData().add(new Employee(code, name, addr, sex, salary));
                }
            }
            bf.close();
            fr.close();
            model.sortData();
            txtCurrPage.setText("1");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private boolean validData() {
        String S = "";
        if (addNew) {
            S = this.txtCode.getText().trim().toUpperCase();
            this.txtCode.setText(S);
            if (!S.matches("^E\\d{3}$")) {
                JOptionPane.showMessageDialog(this, "Code format E000");
                return false;
            }
            for (Employee employee : model.getData()) {
                if (employee.getCode().equalsIgnoreCase(S)) {
                    JOptionPane.showMessageDialog(this, "Code already exist.");
                    return false;
                }
            }
        }
        S = this.txtName.getText().trim();
        this.txtName.setText(S);
        if (S.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return false;
        }

        S = this.txtAddr.getText().trim();
        this.txtAddr.setText(S);
        if (S.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address is required.");
            return false;
        }

        S = this.txtSalary.getText().trim();
        this.txtSalary.setText(S);
        if (S.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Salary is required.");
            return false;
        }
        try {
            int salary = Integer.parseInt(S);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Salary is a digit.");
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupSex = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtAddr = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rbMale = new javax.swing.JRadioButton();
        rbFemale = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        txtSalary = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmp = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnSaveToFile = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        lblTime = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        txtCurrPage = new javax.swing.JLabel();
        txtTotalPage = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("Code:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("Name:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel3.setText("Address:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel4.setText("Sex:");

        btnGroupSex.add(rbMale);
        rbMale.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbMale.setSelected(true);
        rbMale.setText("Male");

        btnGroupSex.add(rbFemale);
        rbFemale.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbFemale.setText("Female");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel5.setText("Salary:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbMale, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rbFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(rbMale)
                    .addComponent(rbFemale))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tblEmp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmp);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Employee List");

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnSaveToFile.setText("Save to file");
        btnSaveToFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveToFileActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        lblTime.setForeground(new java.awt.Color(255, 51, 51));
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel7.setText("Search Employee name:");

        btnPrev.setText("<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        txtCurrPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtCurrPage.setText("1");

        txtTotalPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTotalPage.setText("1");

        jLabel8.setText("/");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnSaveToFile, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                                .addComponent(txtSearch))
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(btnPrev)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCurrPage, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel8)
                                .addGap(8, 8, 8)
                                .addComponent(txtTotalPage, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNext)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPrev)
                            .addComponent(btnNext)
                            .addComponent(txtCurrPage)
                            .addComponent(txtTotalPage)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSaveToFile, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpMouseClicked
        currentPage = Integer.parseInt(this.txtCurrPage.getText());
        int pos = 0;
        if (search == false) {
            pos = (currentPage - 1) * sizeOfPage + tblEmp.getSelectedRow();
        } else {
            // get position if txtSearch is not empty
            pos = indexList.get((currentPage - 1) * sizeOfPage + tblEmp.getSelectedRow());
        }
        Employee curEmp = model.getData().get(pos);
        this.txtCode.setText(curEmp.getCode());
        this.txtName.setText(curEmp.getName());
        this.txtAddr.setText(curEmp.getAddress());
        this.rbMale.setSelected(curEmp.isSex());
        this.rbFemale.setSelected(!curEmp.isSex());
        this.txtSalary.setText(curEmp.getSalary() + "");
        this.txtCode.setEditable(false);
        this.addNew = false;
    }//GEN-LAST:event_tblEmpMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        addNew = true;
        this.txtCode.setText("");
        this.txtName.setText("");
        this.txtAddr.setText("");
        this.rbMale.setSelected(true);
        this.txtSalary.setText("");
        this.txtCode.setEditable(true);
        this.txtCode.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (!validData()) {
            return;
        }
        String code = txtCode.getText();
        String name = txtName.getText();
        String addr = txtAddr.getText();
        boolean sex = this.rbMale.isSelected();
        int salary = Integer.parseInt(txtSalary.getText());
        Employee emp = new Employee(code, name, addr, sex, salary);
        if (addNew) {
            model.getData().add(emp);
        } else {
            currentPage = Integer.parseInt(this.txtCurrPage.getText());
            int pos = 0;
            if (search == false) {
                pos = (currentPage - 1) * sizeOfPage + tblEmp.getSelectedRow();
            } else {
                // get position if txtSearch is not empty
                pos = indexList.get((currentPage - 1) * sizeOfPage + tblEmp.getSelectedRow());
            }
            model.getData().set(pos, emp);
        }
        model.sortData();
        if (search == false) {
            computeTotalPage(model.getData());
            loadPage(model.getData());
        } else {
            txtSearchKeyReleased(null);
        }
        tblEmp.updateUI();

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        currentPage = Integer.parseInt(this.txtCurrPage.getText());
        int pos = 0;
        if (search == false) {
            pos = (currentPage - 1) * sizeOfPage + tblEmp.getSelectedRow();
        } else {
            // get position if txtSearch is not empty
            pos = indexList.get((currentPage - 1) * sizeOfPage + tblEmp.getSelectedRow());
        }
        if (pos >= 0) {
            String code = model.getData().get(pos).getCode();
            if (JOptionPane.showConfirmDialog(this, "Delete the " + code + " employee?") == JOptionPane.OK_OPTION) {
                model.getData().remove(pos);
                if (search == false) {
                    computeTotalPage(model.getData());
                    loadPage(model.getData());
                } else {
                    txtSearchKeyReleased(null);
                }
                tblEmp.updateUI();
                changed = true;
            }
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        if (changed == true) {
            if (JOptionPane.showConfirmDialog(this, "Data changed. Save Y/N ?") == JOptionPane.OK_OPTION) {
                btnSaveToFileActionPerformed(null);
            }
        }
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnSaveToFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveToFileActionPerformed
        try {
            File f = new File(fileName);
            PrintWriter pf = new PrintWriter(f);
            int n = model.getData().size();
            for (int i = 0; i < n; i++) {
                Employee e = model.getData().get(i);
                String S = e.getCode() + "," + e.getName() + "," + e.getAddress() + "," + e.isSex() + "," + e.getSalary();
                pf.println(S);
            }
            pf.close();
            changed = false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

        String time = new Date(System.currentTimeMillis()).toString();
        lblTime.setText("Last saved: " + time);
    }//GEN-LAST:event_btnSaveToFileActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String s = txtSearch.getText().trim().toUpperCase();
        if (!s.isEmpty()) {
            searchList = new Vector<>();
            indexList = new Vector<>();
            // add data into searchList
            // add index of data into indexList
            for (int i = 0; i < model.getData().size(); i++) {
                if (model.getData().get(i).getName().toUpperCase().contains(s)) {
                    searchList.add(model.getData().get(i));
                    indexList.add(i);
                }
            }

            // upload to View by paging technique
            computeTotalPage(searchList);
            loadPage(searchList);
            search = true;
        } else {
            computeTotalPage(model.getData());
            loadPage(model.getData());
            search = false;
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        currentPage = Integer.parseInt(txtCurrPage.getText());
        if (currentPage == 1) {
            return;
        }
        currentPage--;
        txtCurrPage.setText(String.valueOf(currentPage));
        loadPage(model.getData());
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        currentPage = Integer.parseInt(txtCurrPage.getText());
        if (currentPage == totalPage) {
            return;
        }
        currentPage++;
        txtCurrPage.setText(String.valueOf(currentPage));
        loadPage(model.getData());
    }//GEN-LAST:event_btnNextActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.ButtonGroup btnGroupSex;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSaveToFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTime;
    private javax.swing.JRadioButton rbFemale;
    private javax.swing.JRadioButton rbMale;
    private javax.swing.JTable tblEmp;
    private javax.swing.JTextField txtAddr;
    private javax.swing.JTextField txtCode;
    private javax.swing.JLabel txtCurrPage;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JLabel txtTotalPage;
    // End of variables declaration//GEN-END:variables
}
