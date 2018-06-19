package filesystemsimulator;

import application.FileSystem;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.Directory;
import model.Element;
import model.File;

/**
 *
 * @author estuche
 */
public class FileSystemSimulatorGUI extends javax.swing.JFrame {
    
    private static final String FILE_PATH = "./FileSystem.txt";
    private static final int SECTOR_COUNT = 10;
    private static final int SECTOR_SIZE = 10;
    
    private final FileSystem fs;
    
    private String currentFile;
    private String clipBoardFile;
    private String clipboard;
    
    private Element elementClipboard;
    
    private boolean copy = true;

    /**
     * Creates new form FileSystemSimulatorGUI
     */
    public FileSystemSimulatorGUI() {
        initComponents();
        this.fs = new FileSystem(FILE_PATH, SECTOR_COUNT, SECTOR_SIZE);
        fillFileSystem();
        updateTree();
        updateTable(this.fs.getCurrentDirectory());
        
        // Taken from StackOverflow:
        // https://stackoverflow.com/questions/14852719/double-click-listener-on-jtable-in-java/19586049#19586049
        this.fileTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    TableModel model = table.getModel();
                    String directory = (String) model.getValueAt(row, 0);
                    fs.changeDirectory(directory);
                    updateTable(fs.getCurrentDirectory());
                }
                else if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    TableModel model = table.getModel();
                    currentFile = (String) model.getValueAt(row, 0);
                    String content = fs.readFile(currentFile);
                    
                    if (content == null) {
                        previewTextArea.setText("");
                        previewTextArea.setEnabled(false);
                        saveButton.setEnabled(false);
                        cancelButton.setEnabled(false);
                    }
                    else {
                        previewTextArea.setEnabled(true);
                        saveButton.setEnabled(true);
                        cancelButton.setEnabled(true);
                        previewTextArea.setText(content);
                    }
                }
            }
        });
    }
    
    private void fillFileSystem() {
        fs.makeDirectory("Cats");
        fs.makeDirectory("Dogs");
        fs.makeDirectory("Horses");
        fs.makeDirectory("Don't open");
        fs.changeDirectory("Don't open");
        fs.makeDirectory("Why?");
        fs.changeDirectory("..");
    }
    
    private void updateTree() {
        DefaultTreeModel model = (DefaultTreeModel) this.fileTree.getModel();
        model.setRoot(updateTree(this.fs.getRoot()));
    }
    
    private DefaultMutableTreeNode updateTree(Directory directory) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(directory.getName());
        
        ArrayList<Directory> directories = directory.getDirectories();
        
        for (Directory dir: directories) {
            DefaultMutableTreeNode node = updateTree(dir);
            root.add(node);
        }
        
        ArrayList<File> files = directory.getFiles();
        
        for (File file: files) {
            root.add(new DefaultMutableTreeNode(file.getName()));
        }
        
        return root;
    }
    
    private void updateTable(Directory directory) {
        DefaultTableModel model = (DefaultTableModel) this.fileTable.getModel();
        model.setRowCount(0);
        
        ArrayList<Directory> directories = directory.getDirectories();
        
        for (Directory dir: directories) {
            model.addRow(new Object[]{dir.getName(), dir.getModifiedDateTime(), "<DIR>", ""});
        }
        
        ArrayList<File> files = directory.getFiles();
        
        for (File file: files) {
            model.addRow(new Object[]{file.getName(), file.getModifiedDateTime(), file.getFileExtension(), file.getSize()});
        }
    }
    
    private String readFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemSimulatorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void writeFile(String path, String content) {
        try (PrintWriter out = new PrintWriter(path)) {
            out.println(content);
        } catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
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

        backButton = new javax.swing.JButton();
        pathTextField = new javax.swing.JTextField();
        searchTextField = new javax.swing.JTextField();
        newDirectoryButton = new javax.swing.JButton();
        newFileButton = new javax.swing.JButton();
        copyRealVirtualButton = new javax.swing.JButton();
        copyVirtualRealButton = new javax.swing.JButton();
        copyVirtualVirtualButton = new javax.swing.JButton();
        moveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        previewTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        fileTree = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        fileTable = new javax.swing.JTable();
        pasteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        newDirectoryButton.setText("New Directory");
        newDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newDirectoryButtonActionPerformed(evt);
            }
        });

        newFileButton.setText("New File");
        newFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileButtonActionPerformed(evt);
            }
        });

        copyRealVirtualButton.setText("Copy R-V");
        copyRealVirtualButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyRealVirtualButtonActionPerformed(evt);
            }
        });

        copyVirtualRealButton.setText("Copy V-R");
        copyVirtualRealButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyVirtualRealButtonActionPerformed(evt);
            }
        });

        copyVirtualVirtualButton.setText("Copy");
        copyVirtualVirtualButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyVirtualVirtualButtonActionPerformed(evt);
            }
        });

        moveButton.setText("Move");
        moveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        previewTextArea.setColumns(20);
        previewTextArea.setRows(5);
        jScrollPane1.setViewportView(previewTextArea);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        fileTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(fileTree);

        fileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Date modified", "Type", "Size"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(fileTable);

        pasteButton.setText("Paste");
        pasteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pathTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(newDirectoryButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyRealVirtualButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyVirtualRealButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyVirtualVirtualButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(moveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pasteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(searchTextField))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(pathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(newDirectoryButton)
                        .addComponent(newFileButton)
                        .addComponent(copyRealVirtualButton)
                        .addComponent(copyVirtualRealButton)
                        .addComponent(copyVirtualVirtualButton)
                        .addComponent(moveButton)
                        .addComponent(pasteButton)
                        .addComponent(deleteButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(saveButton))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newDirectoryButtonActionPerformed
        String path = JOptionPane.showInputDialog("New directory name");
        this.fs.makeDirectory(path);
        this.updateTree();
        this.updateTable(this.fs.getCurrentDirectory());
    }//GEN-LAST:event_newDirectoryButtonActionPerformed

    private void newFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileButtonActionPerformed
        String path = JOptionPane.showInputDialog("New file name");
        this.fs.createFile(path);
        this.updateTree();
        this.updateTable(this.fs.getCurrentDirectory());
    }//GEN-LAST:event_newFileButtonActionPerformed

    private void copyRealVirtualButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyRealVirtualButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            String content = readFile(selectedFile.getAbsolutePath());
            if (content == null) {
                return;
            }
            this.fs.createFile(selectedFile.getName());
            this.fs.writeFile(selectedFile.getName(), content);
        }
        this.updateTable(this.fs.getCurrentDirectory());
        this.updateTree();
    }//GEN-LAST:event_copyRealVirtualButtonActionPerformed

    private void copyVirtualRealButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyVirtualRealButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            TableModel model = this.fileTable.getModel();
            String name = (String) model.getValueAt(fileTable.getSelectedRow(), 0);
            String content = this.fs.readFile(name);
            this.writeFile(selectedFile.getPath(), content);
            System.out.println("Save file: " + selectedFile.getAbsolutePath());
        }
    }//GEN-LAST:event_copyVirtualRealButtonActionPerformed

    private void copyVirtualVirtualButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyVirtualVirtualButtonActionPerformed
        TableModel model = this.fileTable.getModel();
        this.clipBoardFile = (String) model.getValueAt(fileTable.getSelectedRow(), 0);
        this.clipboard = this.fs.readFile(clipBoardFile);
        this.pasteButton.setEnabled(true);
        this.copy = true;
    }//GEN-LAST:event_copyVirtualVirtualButtonActionPerformed

    private void moveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveButtonActionPerformed
        TableModel model = this.fileTable.getModel();
        String name = (String) model.getValueAt(fileTable.getSelectedRow(), 0);
        this.elementClipboard = this.fs.getCurrentDirectory().cutElement(name);
        if (this.elementClipboard == null) {
            this.pasteButton.setEnabled(false);
            return;
        }
        this.pasteButton.setEnabled(true);
        this.copy = false;
        
        this.updateTable(this.fs.getCurrentDirectory());
        this.updateTree();
    }//GEN-LAST:event_moveButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        TableModel model = this.fileTable.getModel();
        String name = (String) model.getValueAt(fileTable.getSelectedRow(), 0);
        this.fs.deleteDirectory(name);
        this.fs.deleteFile(name);
        updateTable(this.fs.getCurrentDirectory());
        updateTree();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        this.fs.changeDirectory("..");
        this.updateTable(this.fs.getCurrentDirectory());
        
        previewTextArea.setText("");
        previewTextArea.setEnabled(false);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }//GEN-LAST:event_backButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        String content = this.previewTextArea.getText();
        this.fs.writeFile(currentFile, content);
        updateTable(fs.getCurrentDirectory());
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        String content = fs.readFile(currentFile);
        previewTextArea.setText(content);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void pasteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteButtonActionPerformed
        if (copy) {
            this.fs.createFile(this.clipBoardFile);
            this.fs.writeFile(this.clipBoardFile, this.clipboard);
        } else {
            this.fs.getCurrentDirectory().pasteElement(elementClipboard);
        }
        
        this.updateTable(this.fs.getCurrentDirectory());
        this.updateTree();
    }//GEN-LAST:event_pasteButtonActionPerformed
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
            java.util.logging.Logger.getLogger(FileSystemSimulatorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FileSystemSimulatorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FileSystemSimulatorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FileSystemSimulatorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FileSystemSimulatorGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton copyRealVirtualButton;
    private javax.swing.JButton copyVirtualRealButton;
    private javax.swing.JButton copyVirtualVirtualButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTable fileTable;
    private javax.swing.JTree fileTree;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton moveButton;
    private javax.swing.JButton newDirectoryButton;
    private javax.swing.JButton newFileButton;
    private javax.swing.JButton pasteButton;
    private javax.swing.JTextField pathTextField;
    private javax.swing.JTextArea previewTextArea;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration//GEN-END:variables
}
