package notebook;
//-------------------------------IMPORTS--------------------------------------------------------

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

public class Calc {

    // -------------CREATING FIELDS-------------------------------------------------
    JFrame frame = new JFrame("Nootebook");
    JMenuBar jmb = new JMenuBar();
    JMenu file, edit, font, appearance, size;
    JMenuItem open, save, saveas, close, undo, redo, backcolor, textcolor, dah, bist, si, chehel, edwardian, arial;
    JTextArea textArea = new JTextArea();
    JScrollPane jsp = new JScrollPane(textArea);
    String Font = "Arial";
    int fsize = 10;
    int i = 0;
    File openedfile;
    String text = "";
    UndoManager um = new UndoManager();

    Calc() {
        // --------SETTING FIELDS UP-------------------------------------------------
        file = new JMenu("File");
        edit = new JMenu("Edit");
        appearance = new JMenu("Appearance");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        saveas = new JMenuItem("Save As");
        close = new JMenuItem("Close");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        font = new JMenu("Font");
        size = new JMenu("Size");
        dah = new JMenuItem("10");
        bist = new JMenuItem("20");
        si = new JMenuItem("30");
        chehel = new JMenuItem("40");
        edwardian = new JMenuItem("Edwardian Script ITC");
        arial = new JMenuItem("Arial");
        backcolor = new JMenuItem("Background color");
        textcolor = new JMenuItem("Text color");
        frame.setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
        GroupLayout layout = new GroupLayout(frame);
        // --------ADDING COMPONENTS AND SETTINGS--------------------------------------
        layout.linkSize(SwingConstants.HORIZONTAL, jmb);
        frame.setSize(400, 400);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
        frame.add(jsp);
        frame.setJMenuBar(jmb);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        undo.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        redo.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        textArea.setVisible(true);
        textArea.setEnabled(true);
        textArea.setAutoscrolls(true);
        textArea.setFont(new Font("Arial", 0, 10));

        file.add(open);
        file.add(save);
        file.add(saveas);
        file.add(close);

        edit.add(undo);
        edit.add(redo);

        font.add(arial);
        font.add(edwardian);

        size.add(dah);
        size.add(bist);
        size.add(si);
        size.add(chehel);

        appearance.add(font);
        appearance.add(size);
        appearance.add(backcolor);
        appearance.add(textcolor);

        jmb.add(file);
        jmb.add(edit);
        jmb.add(appearance);
        // ------------ACTION LISTENER METHODS----------------------------------------
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        saveas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveas();
            }
        });

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });

        redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });

        arial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font = "Arial";
                font();
            }
        });

        edwardian.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font = "Edwardian Script ITC";
                font();
            }
        });

        dah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fsize = 10;
                font();
            }
        });

        bist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fsize = 20;
                font();
            }
        });

        si.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fsize = 30;
                font();
            }
        });

        chehel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fsize = 40;
                font();
            }
        });

        backcolor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backcolor();
            }
        });

        textcolor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtcolor();
            }
        });

        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                um.addEdit(e.getEdit());
            }
        });

    }

    // ---------MAIN METHODS----------------------------------------------------
    public void open() {
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose file");
            fileChooser.showOpenDialog(null);
            openedfile = fileChooser.getSelectedFile();
            if (openedfile == null || !openedfile.exists()) {
                JOptionPane.showMessageDialog(null, "Your file has problems", "Error", JOptionPane.ERROR_MESSAGE);
                openedfile = null;
                return;
            }
            Scanner reader = new Scanner(openedfile);
            String text = "";
            while (reader.hasNextLine()) {
                text += reader.nextLine() + "\n";
            }
            reader.close();
            textArea.setText(text);
            frame.setTitle(openedfile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void save() {
        try {
            if (openedfile == null) {
                JOptionPane.showMessageDialog(null, "no file saved recently", "Error", JOptionPane.ERROR_MESSAGE);
                openedfile = null;
                return;
            }
            Formatter form = new Formatter(openedfile);
            String text = textArea.getText();
            form.format("%s", text);
            form.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveas() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select a folder");
            chooser.showSaveDialog(null);
            openedfile = chooser.getSelectedFile();
            String text = "";
            Formatter form = new Formatter(openedfile);
            text = textArea.getText();
            form.format("%s", text);
            form.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            int input = JOptionPane.showConfirmDialog(null, "Do you want save before closing?", "Warning",
                    JOptionPane.YES_NO_OPTION);

            if (input == JOptionPane.YES_OPTION) {
                save();
            }
            textArea.setText(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void undo() {
        um.undo();
    }

    public void redo() {
        um.redo();
    }

    public void font() {
        textArea.setFont(new Font(Font, 0, fsize));
    }

    public void backcolor() {
        Color d = textArea.getBackground();
        JFrame f = new JFrame("Choose color");
        f.setSize(300, 300);
        Color color = JColorChooser.showDialog(f, "Select a color", d);
        textArea.setBackground(color);
    }

    public void txtcolor() {
        Color d = textArea.getBackground();
        JFrame f = new JFrame("Choose color");
        f.setSize(300, 300);
        Color color = JColorChooser.showDialog(f, "Select a color", d);
        textArea.setForeground(color);
    }

    // --------------MAIN METHOD---------------------------------------------------
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new Calc();
    }

}
