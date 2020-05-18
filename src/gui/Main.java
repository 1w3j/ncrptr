package gui;

import ciphers.AES;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import net.iharder.dnd.FileDrop;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author 1w3j
 */
public class Main extends javax.swing.JFrame {

    private DefaultListModel dlm;
    private int[] lastListSelected = null;
    private final Border droppedBorder = BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED, null, null, Color.BLUE, Color.BLUE);
    private final Icon GREENCIRCLE = new ImageIcon(getClass().getResource("/imgs/green_btn_mac_os_x-s.png"));
    private final Icon YELLOWCIRCLE = new ImageIcon(getClass().getResource("/imgs/yellow_btn_mac_os_x-s.png"));
    private final Icon REDCIRCLE = new ImageIcon(getClass().getResource("/imgs/red_btn_mac_os_x-s.png"));
    private ExecutorService infoExecutor = Executors.newFixedThreadPool(2);
    private ExecutorService encryptionExecutor = Executors.newSingleThreadExecutor();
    private ExecutorService shutdownExecutor = Executors.newSingleThreadExecutor();
    private List<Future<?>> infoFutures = new ArrayList<>();
    private List<Future<?>> encryptionFutures = new ArrayList<>();

    public Main() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.err.println(e.getMessage());
        }
        initComponents();
        getFrames()[0].setTitle("Music Converter");
        l.setModel(new DefaultListModel<>());
        PromptSupport.setPrompt("Rename the zip", xn);
        PromptSupport.setPrompt("Even more secure", xp);
        dlm = new DefaultListModel();
        FileDrop dropTabbed = new FileDrop(getRootPane(), droppedBorder, (File[] files) -> {
            for (File file : files) {
                dlm.addElement(file.getAbsolutePath());
            }
            l.setModel(dlm);
            if (lastListSelected != null) {
                l.setSelectedIndices(lastListSelected);
            }
        });
        ((PlainDocument) xpass.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String lastTextInserted, AttributeSet attrs) throws BadLocationException {
                System.out.println("lastTextInserted = " + lastTextInserted);
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + lastTextInserted;
                System.out.println("string = " + string);
                if (string.length() <= 16) {
                    super.replace(fb, offset, length, lastTextInserted, attrs);
                }
            }
        });
        ((PlainDocument) xp.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String lastTextInserted, AttributeSet attrs) throws BadLocationException {
                System.out.println("lastTextInserted = " + lastTextInserted);
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + lastTextInserted;
                System.out.println("string = " + string);
                if (string.length() <= 32) {
                    super.replace(fb, offset, length, lastTextInserted, attrs);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        l = new javax.swing.JList<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        ball = new javax.swing.JToggleButton();
        rnc = new javax.swing.JRadioButton();
        pnlCmprss = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmq = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        xn = new javax.swing.JTextField();
        xp = new javax.swing.JPasswordField();
        bd = new javax.swing.JButton();
        linf = new javax.swing.JLabel();
        img = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        xpass = new javax.swing.JPasswordField();
        pnlBottom = new javax.swing.JPanel();
        pnlButtons = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        a = new javax.swing.JButton();
        b = new javax.swing.JButton();
        pnlBar = new javax.swing.JPanel();
        bar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(455, 310));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(400, 210));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 250));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(380, 237));

        l.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "/path/to/example/file" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        l.setToolTipText("");
        l.setMaximumSize(new java.awt.Dimension(0, 0));
        l.setMinimumSize(new java.awt.Dimension(0, 0));
        l.setName(""); // NOI18N
        l.setPreferredSize(null);
        l.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(l);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel2.add(jPanel5, gridBagConstraints);

        jPanel4.setMinimumSize(new java.awt.Dimension(240, 190));
        jPanel4.setPreferredSize(new java.awt.Dimension(245, 240));
        jPanel4.setRequestFocusEnabled(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        ball.setText("Select All");
        ball.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ballActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel4.add(ball, gridBagConstraints);

        rnc.setText("Compress");
        rnc.setToolTipText("");
        rnc.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rncStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(rnc, gridBagConstraints);

        pnlCmprss.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 3;
        pnlCmprss.add(jPanel8, gridBagConstraints);

        jLabel2.setText("Name:");
        jLabel2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCmprss.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Quality:");
        jLabel3.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCmprss.add(jLabel3, gridBagConstraints);

        cmq.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fastest", "Best", "Normal", "No compression" }));
        cmq.setSelectedItem("Normal");
        cmq.setToolTipText("Be aware of setting up passwords to zip files, it removes compression");
        cmq.setEnabled(false);
        cmq.setPreferredSize(new java.awt.Dimension(157, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 4, 0);
        pnlCmprss.add(cmq, gridBagConstraints);

        jLabel4.setText("Pass:");
        jLabel4.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        pnlCmprss.add(jLabel4, gridBagConstraints);

        xn.setEnabled(false);
        xn.setPreferredSize(new java.awt.Dimension(160, 31));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 4, 0);
        pnlCmprss.add(xn, gridBagConstraints);

        xp.setToolTipText("This removes deflating but strengthen security");
        xp.setEnabled(false);
        xp.setPreferredSize(new java.awt.Dimension(160, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 4, 0);
        pnlCmprss.add(xp, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 4;
        jPanel4.add(pnlCmprss, gridBagConstraints);

        bd.setText("Remove");
        bd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel4.add(bd, gridBagConstraints);

        linf.setBackground(new java.awt.Color(0, 108, 148));
        linf.setText("Drop your files and conv:");
        linf.setPreferredSize(new java.awt.Dimension(205, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 7, 0);
        jPanel4.add(linf, gridBagConstraints);

        img.setIcon(YELLOWCIRCLE);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel4.add(img, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel1.setText("Secret pass:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel4.add(jLabel1, gridBagConstraints);

        xpass.setColumns(9);
        xpass.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel4.add(xpass, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        jPanel2.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel2, gridBagConstraints);

        pnlBottom.setPreferredSize(new java.awt.Dimension(640, 50));
        pnlBottom.setLayout(new java.awt.CardLayout());

        pnlButtons.setPreferredSize(new java.awt.Dimension(635, 60));
        pnlButtons.setLayout(new java.awt.GridBagLayout());

        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(630, 48));
        jSplitPane1.setRequestFocusEnabled(false);

        a.setText("Convert");
        a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aActionPerformed(evt);
            }
        });
        jSplitPane1.setLeftComponent(a);

        b.setText("De-convert");
        b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bActionPerformed(evt);
            }
        });
        jSplitPane1.setRightComponent(b);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlButtons.add(jSplitPane1, gridBagConstraints);

        pnlBottom.add(pnlButtons, "pnlButtons");

        pnlBar.setLayout(new java.awt.GridBagLayout());

        bar.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        bar.setValue(75);
        bar.setPreferredSize(new java.awt.Dimension(620, 36));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        pnlBar.add(bar, gridBagConstraints);

        pnlBottom.add(pnlBar, "pnlBar");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        getContentPane().add(pnlBottom, gridBagConstraints);

        setSize(new java.awt.Dimension(665, 345));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aActionPerformed
        try {
            if (l.getSelectedIndices().length > 0) {
                if (xpass.getPassword().length > 0) {
                    if(rnc.isSelected()){//radio button to compress files
                        encryptionFutures.add(encryptionExecutor.submit(()->{
                            showBar();
                            bar.setStringPainted(true);
                            bar.setString("Compressing files...");
                            bar.setIndeterminate(true);
                            try {
                                String zippedFile = zipFiles(l.getSelectedValuesList().toArray(new String[0]));
                                bar.setIndeterminate(false);
                                bar.setValue(0);
                                AES.encryptFile(zippedFile, new String(xpass.getPassword()), bar);
                                showGreenIcon(2);
                            } catch (Exception ex) {
                                showErrorInfo(3, 2, ex.getMessage());
                            }
                            bar.setStringPainted(false);
                            bar.setValue(0);
                            showButtons();
                        }));
                    }else{
                        for (String file : l.getSelectedValuesList().toArray(new String[0])) {
                            encryptionFutures.add(encryptionExecutor.submit(() -> {
                                showBar();
                                bar.setStringPainted(true);
                                bar.setIndeterminate(false);
                                bar.setValue(0);
                                try {
                                    AES.encryptFile(file, new String(xpass.getPassword()), bar);
                                    showGreenIcon(2);
                                } catch (Exception ex) {
                                    showErrorInfo(3, 2, ex.getMessage());
                                }
                                bar.setStringPainted(false);
                                bar.setValue(0);
                                showButtons();
                            }));
                        }
                    }
                    System.out.println("Work finished!");
                } else {
                    showErrorInfo(3, 1, "Missing password");
                }
            } else {
                showErrorInfo(3, 1, "Didn't select any file");
            }
        } catch (Exception ex) {
            showErrorInfo(3, 1, ex.getMessage());
        }
    }//GEN-LAST:event_aActionPerformed

    private void bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bActionPerformed
        try {
            if (l.getSelectedIndices().length > 0) {
                if (xpass.getPassword().length > 0) {
                    for (String file : l.getSelectedValuesList().toArray(new String[0])) {
                        encryptionFutures.add(encryptionExecutor.submit(() -> {
                            showBar();
                            bar.setStringPainted(true);
                            bar.setIndeterminate(false);
                            bar.setValue(0);
                            try {
                                AES.decryptFile(file, new String(xpass.getPassword()), bar);
                                showGreenIcon(2);
                            }
                            catch (BadPaddingException bpe){
                                showErrorInfo(3, 2, "Possibly wrong key");
                            }
                            catch (Exception ex) {
                                System.err.println("Error on decryption\nFile: '" + file + "'\n" + ex.getMessage());
                                showErrorInfo(3, 2, ex.getMessage());
                            }
                            bar.setStringPainted(false);
                            bar.setValue(0);
                            showButtons();
                        }));
                    }
                    System.out.println("Work finished!");
                } else {
                    showErrorInfo(2, 1, "Missing password");
                }
            } else {
                showErrorInfo(2, 1, "Didn't select any file");
            }
        } catch (Exception ex) {
            showErrorInfo(3, 1, ex.getMessage());
        }
    }//GEN-LAST:event_bActionPerformed

    private void lValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lValueChanged
        System.out.println("Selection Changed: last: " + evt.getLastIndex() + "\tfirst: " + evt.getFirstIndex());
        EventQueue.invokeLater(() -> {
            lastListSelected = l.getSelectedIndices();
            System.out.println("lastListSelect Lenght=" + lastListSelected.length + "\tdlm.getSize=" + dlm.getSize());
            if (lastListSelected.length == 0 && lastListSelected.length != dlm.getSize()) {
                System.out.println("ball set selected FALSE");
                ball.setSelected(false);
            }
            System.out.println("lastListSelect updated " + Arrays.toString(lastListSelected));
        });
    }//GEN-LAST:event_lValueChanged

    private void ballActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ballActionPerformed
        if (ball.isSelected()) {
            System.out.println("DEACTIVATING ball");
            l.getSelectionModel().setSelectionInterval(0, dlm.getSize() - 1);
        } else {
            System.out.println("ACTIVATING ball");
            l.getSelectionModel().clearSelection();
        }
    }//GEN-LAST:event_ballActionPerformed

    private void rncStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rncStateChanged
        enableComponents(pnlCmprss, rnc.isSelected());
    }//GEN-LAST:event_rncStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(encryptionExecutorFinished() && infoExecutorFinished()){
            shutdownExecutor.submit(()->{
                if(!bar.isIndeterminate())bar.setIndeterminate(true);
                if(!bar.isStringPainted())bar.setStringPainted(true);
                bar.setString("Shutting down everything...");
            });
            shutdownExecutor.submit(()->{
                try{
                    System.out.println("Waiting for encryptionExecutor to terminate");
                    System.out.println("encryption: attempt to shutdown infoExecutor");
                    encryptionExecutor.shutdown();
                    encryptionExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
                    System.out.println("encryptionExecutor terminated!");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    if (!encryptionExecutor.isTerminated()) {
                        System.err.println("encryptionExecutor: cancel non-finished tasks");
                    }
                    encryptionExecutor.shutdownNow();
                    System.out.println("encryptionExecutor: shutdown finished");
                }
            });
            shutdownExecutor.submit(()->{
                try {
                    System.out.println("Waiting for infoExecutor to terminate");
                    System.out.println("infoExecutor: attempt to shutdown infoExecutor");
                    infoExecutor.shutdown();
                    infoExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
                    System.out.println("infoExecutor terminated!");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    if (!infoExecutor.isTerminated()) {
                        System.err.println("infoExecutor: cancel non-finished tasks");
                    }
                    infoExecutor.shutdownNow();
                    System.out.println("infoExecutor: shutdown finished");
                }
            });
            EventQueue.invokeLater(()->{
                try {
                    System.out.println("Waiting for shutdownExecutor to terminate");
                    System.out.println("shutdownExecutor: attempt to shutdown shutdownExecutor");
                    shutdownExecutor.shutdown();
                    shutdownExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
                    System.out.println("shutdownExecutor terminated!");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }finally {
                    if (!shutdownExecutor.isTerminated()) {
                        System.err.println("shutdownExecutor: cancel non-finished tasks");
                    }
                    shutdownExecutor.shutdownNow();
                    System.out.println("shutdownExecutor: shutdown finished");
                }
                if (infoExecutor.isTerminated() && infoExecutor.isShutdown() &&
                    encryptionExecutor.isTerminated() && encryptionExecutor.isShutdown() &&
                    shutdownExecutor.isTerminated() && shutdownExecutor.isShutdown()) {
                    System.exit(0);
                }//else....?
            });
        }
    }//GEN-LAST:event_formWindowClosing

    private void bdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdActionPerformed
        if (l.getSelectedIndices().length > 0) {
            for (int i = l.getSelectedIndices().length - 1; i >= 0; i--) {
                System.out.println("SelectedIndices: " + Arrays.toString(l.getSelectedIndices()));
                dlm.removeElementAt(l.getSelectedIndices()[i]);
            }
        } else {
            System.out.println("No files selected");
            showErrorInfo(2, 1, "Didn't select any file");
        }
    }//GEN-LAST:event_bdActionPerformed

    private boolean encryptionExecutorFinished(){
        boolean allDone=true;
        for(Future<?>future:encryptionFutures){
            allDone &= future.isDone();
        }
        return allDone;
    }
    
    private boolean infoExecutorFinished(){
        boolean allDone=true;
        for(Future<?>future:infoFutures){
            allDone &= future.isDone();
        }
        return allDone;
    }
    
    private void showBar() {
        ((CardLayout) pnlBottom.getLayout()).show(pnlBottom, "pnlBar");
    }

    private void showButtons() {
        ((CardLayout) pnlBottom.getLayout()).show(pnlBottom, "pnlButtons");
    }

    private synchronized String zipFiles(String[] paths) throws Exception {
        final ArrayList<File> fileList = new ArrayList<>();
        for (String path : paths) {
            fileList.add(new File(path));
        }
        boolean sameParent = true;
        String parent = fileList.get(0).getParentFile().getName();
        for (String path : paths) {
            if (!new File(path).getParentFile().getName().equals(parent)) {
                sameParent = false;
            }
        }
        String zipFilePath;
        if (xn.getText().trim().length() == 0) {//default name
            if (sameParent) {
                zipFilePath = fileList.get(0).getAbsolutePath().substring(0, fileList.get(0).getAbsolutePath().lastIndexOf(File.separator)) + File.separator + fileList.get(0).getParentFile().getName() + ".zip";
            } else {
                zipFilePath = fileList.get(0).getParentFile().getAbsolutePath() + File.separator + fileList.get(0).getParentFile().getName();
            }
        } else {
            zipFilePath = fileList.get(0).getParentFile().getAbsolutePath() + File.separator + xn.getText() + ".zip";
        }
        ZipParameters params = new ZipParameters();
        params.setEncryptFiles(false);
        params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
        params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        if (new String(((JPasswordField) xp).getPassword()).trim().length() > 0) {
            params.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            params.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            params.setEncryptFiles(true);
            params.setPassword(xp.getText().toCharArray());
        }
        if (cmq.getSelectedItem().equals("Best")) {
            params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
            params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        }
        if (cmq.getSelectedItem().equals("No Compression")) {
            params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
            params.setCompressionMethod(Zip4jConstants.COMP_STORE);
        }
        if (cmq.getSelectedItem().equals("Normal")) {
            params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        }
        if (cmq.getSelectedItem().equals("Fastest")) {
            params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
            params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        }
        ZipFile zip = new ZipFile(zipFilePath);
        if (!zip.getFile().exists()) {
            if (fileList.get(0).isDirectory()) {
                zip.createZipFileFromFolder(fileList.get(0), params, false, 0);
            } else {
                zip.createZipFile(fileList.get(0), params);
            }
            if (fileList.size() > 1) {
                for (int i = 1; i < fileList.size(); i++) {
                    if (fileList.get(i).isDirectory()) {
                        zip.addFolder(fileList.get(i), params);
                    } else {
                        zip.addFile(fileList.get(i), params);
                    }
                }
            }
            if (zip.isValidZipFile()) {
                fileList.forEach((File archivo) -> {//delete original files
                    if (!archivo.isDirectory()) {
                        archivo.delete();
                    } else {
                        deleteDir(archivo);
                    }
                });
            }
        } else {
            throw new Exception("Archive " + zipFilePath + " already exists, rename it");
        }
        return zipFilePath;
    }

    private static void deleteDir(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteDir(f);
            }
        }
        file.delete();
    }

    public void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container) component, enable);
            }
        }
    }

    private synchronized void showErrorInfo(int secInfo, int secIcon, String msg) {
        if(!infoExecutor.isTerminated()){
            linf.setText(msg);
            linf.setForeground(Color.RED);
            img.setIcon(REDCIRCLE);
            if (secInfo != 0) {
                infoFutures.add(infoExecutor.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(secInfo);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex.getMessage());
                    }
                    linf.setText("Drop your files and conv:");
                    linf.setForeground(Color.BLACK);
                }));
            }
            if (secIcon != 0) {
                infoFutures.add(infoExecutor.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(secIcon);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex.getMessage());
                    }
                    img.setIcon(YELLOWCIRCLE);
                }));
            }
        }
    }
    private synchronized void showGreenIcon(int secIcon) {
        if(!infoExecutor.isTerminated()){
            img.setIcon(GREENCIRCLE);
            if (secIcon != 0) {
                infoFutures.add(infoExecutor.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(secIcon);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex.getMessage());
                    }
                    img.setIcon(YELLOWCIRCLE);
                }));
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton a;
    private javax.swing.JButton b;
    private javax.swing.JToggleButton ball;
    private javax.swing.JProgressBar bar;
    private javax.swing.JButton bd;
    private javax.swing.JComboBox<String> cmq;
    private javax.swing.JLabel img;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList<String> l;
    private javax.swing.JLabel linf;
    private javax.swing.JPanel pnlBar;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlCmprss;
    private javax.swing.JRadioButton rnc;
    private javax.swing.JTextField xn;
    private javax.swing.JTextField xp;
    private javax.swing.JPasswordField xpass;
    // End of variables declaration//GEN-END:variables
}
