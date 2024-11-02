import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.swing.*;
import java.applet.Applet;

public class MaFenetre extends Applet{

    public File[] file;

    File folder;
    List fileList,fileListSizeF;
    int n,nfile;
    JLabel jLabelNfiles,jlabelWarnings;
    ItemListener itemListener;
    String previousFolderSelected;
    Boolean flacSelected
            , mp4Selected
            , ogvSelected
            , aviSelected
            , webmSelected=true;
    JCheckBox jCheckBoxMp4 = new JCheckBox("Mp4",true);
    JCheckBox jCheckBoxFlac = new JCheckBox("Flac",true);
    JCheckBox jCheckBoxOgv = new JCheckBox("Ogv",true);
    JCheckBox jCheckBoxAvi = new JCheckBox("Avi",true);
    JCheckBox jCheckBoxWebm = new JCheckBox("Webm",true);

    public MaFenetre() {
        //fileList.add("");
        fileList= new List();
        fileListSizeF = new List();
        JTextField jTextField1 = new JTextField("Folder : ", 50);
      //  JTextArea jTextArea = new JTextArea("",150,140);
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.isDirectorySelectionEnabled();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        Runtime rt = Runtime.getRuntime();

        JFrame jframe = new JFrame("Videos To Mp3 From a Folder to that folder ");
        jframe.setLayout(new BorderLayout());

        JPanel jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();



        jframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jframe.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        JButton jbuttonConvert = new JButton("Convert");
        jbuttonConvert.setForeground(Color.RED);
        JButton jbuttonSearch = new JButton("Search a directory");
        jbuttonSearch.setForeground(Color.BLUE);
        JButton jbuttonRmSelectedFile = new JButton("Remove Selected File from list");
        jbuttonRmSelectedFile.setForeground(Color.RED);

        jLabelNfiles = new JLabel();
        jLabelNfiles.setText(" "+ n + " Files to Convert to Mp3");
        jlabelWarnings = new JLabel();
        jlabelWarnings.setText("Search, Sort and Convert");


        fileList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
               // jlabelWarnings.setText(String.valueOf(e));
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    jbuttonRmSelectedFile.setForeground(Color.BLUE);

                }

            }
        });

        jCheckBoxFlac.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {flacSelected=true; } else flacSelected=false;

            }
        });
        jCheckBoxMp4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)  mp4Selected=true; else mp4Selected=false;
            }
        });
        jCheckBoxOgv.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)  ogvSelected=true; else ogvSelected=false;
            }
        });
        jCheckBoxAvi.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)  aviSelected=true; else aviSelected=false;
            }
        });
        jCheckBoxWebm.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)  webmSelected=true; else webmSelected=false;
            }
        });

        jbuttonRmSelectedFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileList.getSelectedIndex()>-1) {
                    fileListSizeF.remove(fileList.getSelectedIndex());
                    fileList.remove(fileList.getSelectedIndex());

                    n--;
                    jLabelNfiles.setText(" "+ n + " Files to Convert to Mp3");
                }else jlabelWarnings.setText("You cant remove what you have not select ^^ ");



            }
        });
        jbuttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                int returnVal = jFileChooser.showOpenDialog(jframe);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    if (folder==null && fileList.getItemCount()!=0) {
                        fileList.remove(0);
                         if (fileListSizeF.getItemCount()!=0) {fileListSizeF.remove(0); }
                    }

                    jTextField1.setText(String.valueOf(jFileChooser.getSelectedFile()));
                    if  ( (!Objects.equals(previousFolderSelected, String.valueOf(jFileChooser.getSelectedFile()) )) ||
                            ( (Objects.equals(previousFolderSelected, String.valueOf(jFileChooser.getSelectedFile())) &&
                                    fileList.getItemCount()==0)) )
                    {
                        if (fileList.getItemCount()!=0) { fileList.removeAll();
                                                          fileListSizeF.removeAll();}
                        folder = new File(String.valueOf(jFileChooser.getSelectedFile()));
                        previousFolderSelected = String.valueOf(jFileChooser.getSelectedFile());

                        jbuttonConvert.setForeground(Color.GREEN);
                        jbuttonSearch.setForeground(Color.GREEN);
                        file = folder.listFiles();
                        //fileList = new List(10);
                        n = 0;
                        for (int i = 0; i < file.length; i++) {
                            String ext = file[i].getName().substring(file[i].getName().length() - 4, file[i].getName().length());

                            if (
                                    (ext.equalsIgnoreCase("flac") && jCheckBoxFlac.isSelected()) ||
                                            (ext.equalsIgnoreCase(".mp4") && jCheckBoxMp4.isSelected() ) ||
                                            (ext.equalsIgnoreCase(".ogv") && jCheckBoxOgv.isSelected()) ||
                                            (ext.equalsIgnoreCase(".avi") && jCheckBoxAvi.isSelected()) ||
                                            (ext.equalsIgnoreCase("webm") && jCheckBoxWebm.isSelected())
                                            ||
                                            (ext.equalsIgnoreCase(".mp3") )
                            )
                            {
                                fileList.add(file[i].getName());
                                fileListSizeF.add(i+"Taille : "+String.valueOf((file[i].length()/1024)/1024));
                                n++;
                                jLabelNfiles.setText(" " + n + " Files to Convert to Mp3");


                            }
                        }
                    }
                }
            }
        });

        jbuttonConvert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 for (int i = 0; i <n ; i++) {
                    String str=new String("\""+folder.getPath() + "/" +
                            fileList.getItem(i)  );
                    String command = new String("ffmpeg -i " +str +
                            "\" " + str.replace(".mp4", ".mp3\"")   );
                    try {
                       // Runtime runtime = Runtime.getRuntime();
                      //  Process process = Runtime.getRuntime().exec("xfce4-terminal --command="+ command, null, folder);
                        ProcessBuilder processBuilder = new ProcessBuilder();
                        processBuilder.command("xfce4-terminal", "--command="+command);
                        Process process = processBuilder.start();
                        System.out.println("commandes : "+command);
                        System.out.println(fileList.getItem(i).replace(".mp4", ""));

                       // rt.exec("cmd.exe /c start " + "ffmpeg -i '" +folder.getPath()+"\\"+ fileList.getItem(i)+ "' '" + folder.getPath()+"\\"+ fileList.getItem(i).replaceAll("\\s+","")+ ".mp3'", null, folder);
                     //   rt.exec("cmd.exe /c start "
                     //                   + "ffmpeg -i " + file[i].getName() + " " + file[i].getName().replaceAll("\\s+",
                     //                   "") + i + ".mp3",
                     //           null, folder);
                    }  catch (IOException ex) {
                    } // end catch



                    // System.out.println(file[i].getName() + " " + (file[i].getName().replaceAll("\\s+", "")));
                } // end for

            }
        });

        gridBagConstraints.gridx= 1;
        gridBagConstraints.gridy= 0;
        gridBagConstraints.weighty = 6;
        gridBagConstraints.weightx = 6;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(jTextField1,gridBagConstraints);

        gridBagConstraints.ipady = 0;
        gridBagConstraints.gridx= 1;
        gridBagConstraints.gridy= 1;
        gridBagConstraints.gridheight = 3;
        jPanel.add(fileList,gridBagConstraints);
        gridBagConstraints.gridheight = 1;

        gridBagConstraints.ipady = 0;
        gridBagConstraints.gridx= 2;
        gridBagConstraints.gridy= 1;
        gridBagConstraints.gridheight =3;
        jPanel.add(fileListSizeF,gridBagConstraints);
        gridBagConstraints.gridheight = 1;

        fileList.add("Files List");

        gridBagConstraints.ipady = 0;
        gridBagConstraints.gridx= 1;
        gridBagConstraints.gridy= 2;
        gridBagConstraints.gridheight = 1;
        jPanel.add(jLabelNfiles,gridBagConstraints);
        gridBagConstraints.gridheight = 1;

        gridBagConstraints.gridx= 1;
        gridBagConstraints.gridy= 4;
        jPanel.add(jlabelWarnings,gridBagConstraints);
        gridBagConstraints.gridheight = 1;

        gridBagConstraints.gridx= 5;
        gridBagConstraints.gridy= 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        jPanel.add(jCheckBoxMp4,gridBagConstraints);

        gridBagConstraints.gridx= 5;
        gridBagConstraints.gridy= 1;
        jPanel.add(jCheckBoxFlac,gridBagConstraints);

        gridBagConstraints.gridx= 5;
        gridBagConstraints.gridy= 2;
        jPanel.add(jCheckBoxOgv,gridBagConstraints);

        gridBagConstraints.gridx= 5;
        gridBagConstraints.gridy= 3;
        jPanel.add(jCheckBoxAvi,gridBagConstraints);

        gridBagConstraints.gridx= 5;
        gridBagConstraints.gridy= 4;
        jPanel.add(jCheckBoxWebm,gridBagConstraints);

        gridBagConstraints.gridx= 2;
        gridBagConstraints.gridy= 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets =  new Insets(00,0,0,0);
        jPanel.add(jbuttonRmSelectedFile,gridBagConstraints);

        gridBagConstraints.gridx= 2;
        gridBagConstraints.gridy= 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets =  new Insets(00,0,0,0);
        jPanel.add(jbuttonSearch,gridBagConstraints);

        gridBagConstraints.gridx= 2;
        gridBagConstraints.gridy= 6;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets =  new Insets(00,0,0,0);
        jPanel.add(jbuttonConvert,gridBagConstraints);
        jframe.add(jPanel);
        jframe.pack();
        jframe.setVisible(true);

    }
    public static void main(String[] args) {
       new MaFenetre();

        }
    }
