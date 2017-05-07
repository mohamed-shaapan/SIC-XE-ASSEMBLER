package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class AssemblyGUI {

    private JFrame frmAssembler;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AssemblyGUI window = new AssemblyGUI();
                    window.frmAssembler.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public AssemblyGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmAssembler = new JFrame();
        frmAssembler.setResizable(false);
        frmAssembler.setTitle("Assembler");
        frmAssembler.setBounds(100, 100, 1115, 690);
        frmAssembler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmAssembler.getContentPane().setLayout(null);
        final JFileChooser fc = new JFileChooser();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 1109, 21);
        frmAssembler.getContentPane().add(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

            }
        });
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        fileMenu.add(open);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        fileMenu.add(save);

        JMenu assemblyMenu = new JMenu("Assembly");
        menuBar.add(assemblyMenu);

        JMenuItem assemble = new JMenuItem("Assemble");
        assemblyMenu.add(assemble);

        JMenuItem intermediate = new JMenuItem("Intermediate File");
        assemblyMenu.add(intermediate);

        JMenuItem listing = new JMenuItem("Listing File");
        assemblyMenu.add(listing);

        JMenuItem object = new JMenuItem("Object File");
        assemblyMenu.add(object);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setBounds(0, 21, 1109, 641);
        frmAssembler.getContentPane().add(editorPane);
    }
}
