/**
 * The GUI from the notes (p.15 Chapter 11 Slides)
 * 
 * @author Lynn Marshall
 * @version Skeleton: November 17, 2012
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class NotesGui implements ActionListener, ChangeListener, ListSelectionListener
{   
   private JFrame frame;
   private JPanel btnPanel; //panel containing button elements
   private JPanel panel;    //main panel
   private JPanel txtPanel; //panel containing text(PRESS ENTER TO DISPLAY)
   private JPanel barPanel; //panel containing progress bar
   private JPanel slctPanel;    //panel where items are selcted
   private JPanel lstPanel; //panel containing the list
   private JPanel boxPanel; //panel containing the drop down menu
   private JPanel sldrPanel;    //panel containing the slider
   private JPanel spnrPanel;    //panel containing the spinner
   private JPanel btnsPanel;    //panel containing regular buttons
   private JPanel chkPanel; //panel containing checkboxes
   private JPanel rdioPanel;    //panel containing radio buttons
   private JPanel upperPanel;   //panel containing upper part of slctPanel
   private JPanel lowerPanel;   //panel containing lower part of slctPanel
   
   private JToggleButton toggleBtn; //buttons
   private JButton btn;
   private JRadioButton r1, r2; //radio buttons
   private JCheckBox c1, c2; //checkboxes
   private JProgressBar bar;    //progress bar
   private JLabel label;    //label
   private JTextField text; //text box
   private JSlider slider;  //slider
   private JSpinner spinner;    //spinner
   private JComboBox<String> box;   //drop down menu
   private JList list;  //list
   private JScrollPane scrollPane;  //scrollpane for list
   private GridBagConstraints gbc;
   
   public NotesGui() {
      frame = new JFrame("Swing Component Demo");
      Container contentPane = frame.getContentPane();
      panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      btnPanel = new JPanel();
      txtPanel = new JPanel();
      barPanel = new JPanel();
      slctPanel = new JPanel();
      lstPanel = new JPanel();
      boxPanel = new JPanel();
      sldrPanel = new JPanel();
      spnrPanel = new JPanel();
      btnsPanel = new JPanel();
      btnsPanel.setLayout(new BoxLayout(btnsPanel, BoxLayout.Y_AXIS));
      chkPanel = new JPanel();
      chkPanel.setLayout(new BoxLayout(chkPanel, BoxLayout.Y_AXIS));
      rdioPanel = new JPanel();
      rdioPanel.setLayout(new BoxLayout(rdioPanel, BoxLayout.Y_AXIS));
      upperPanel = new JPanel();
      lowerPanel = new JPanel();
      
      gbc = new GridBagConstraints();  //initalizes constraints
      
      //creates borders for the panels and gives them a title
      btnPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Buttons"));
      btnsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
      chkPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), ""));
      rdioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "A ButtonGroup"));
      txtPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Labels and Text Entry"));
      barPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "A JProgress Bar"));
      slctPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Components for Selecting"));
      sldrPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "A JSlider"));
      spnrPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "A JSpinner"));
      lstPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "A JList in a JScrollPane"));
      boxPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "A JComboBox"));
      
      toggleBtn = new JToggleButton("A JToggleButton");
      toggleBtn.addActionListener(this);
      btn = new JButton("A JButton");
      btn.addActionListener(this);
      r1 = new JRadioButton("A JRadioButton");
      r1.addActionListener(this);
      r2 = new JRadioButton("Another JradioButton");
      r2.addActionListener(this);
      c1 = new JCheckBox("checkbox 1");
      c1.addActionListener(this);
      c2 = new JCheckBox("checkbox 2");
      c2.addActionListener(this);
      
      bar = new JProgressBar();
      label = new JLabel("A JLabel with a text label");
      text = new JTextField("You can type text in an editable JTextField");
      text.addActionListener(this);
      text.addFocusListener(new FocusListener(){
          public void focusGained(FocusEvent e){

              if(text.getText().equals("You can type text in an editable JTextField")) {

                  text.setText("");

                }

            }
          public void focusLost(FocusEvent arg0) {

              if(text.getText().equals("")) {

                   text.setText("You can type text in an editable JTextField");

                }  

            }

        });
        
      //creates 10 items fro the list and drop down menu
      String[] items = new String[10];
      for (int i = 0; i<10; i++){
          items[i] = "Item"+(i+1);
        }
        
      box = new JComboBox(items);
      
      list = new JList(items);
      list.addListSelectionListener(this);
      scrollPane = new JScrollPane(list);
      
      //spinner goes until 10 in increments of 1
      slider = new JSlider(0,10,1);
      slider.setMajorTickSpacing(5); 
      slider.setMinorTickSpacing(1);
      slider.setPaintTicks(true); 
      slider.setPaintLabels(true);
      slider.setValue(4);
      slider.addChangeListener(this);
      
      bar.setValue(75);
      bar.setForeground(Color.black);
      bar.setStringPainted(true);
      
      //spinner goes until 999 in increments of 1
      spinner = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
      spinner.addChangeListener(this);
      
      gbc.gridy = 0;
      gbc.insets = new Insets(10, 10, 10, 10);
      btnsPanel.add(btn, gbc);
      gbc.gridx = 1;
      gbc.insets = new Insets(10, 10, 10, 10);
      btnsPanel.add(toggleBtn, gbc);
      
      //add items to respective panels
      chkPanel.add(c1);
      chkPanel.add(c2);
      rdioPanel.add(r1);
      rdioPanel.add(r2);
      
      btnPanel.add(btnsPanel);
      btnPanel.add(chkPanel);
      btnPanel.add(rdioPanel);
      
      barPanel.add(bar);
      txtPanel.add(label);
      txtPanel.add(text);
      
      spnrPanel.add(spinner);
      sldrPanel.add(slider);
      boxPanel.add(box);
      lstPanel.add(scrollPane);
      
      
      upperPanel.add(sldrPanel);
      upperPanel.add(spnrPanel);
      lowerPanel.add(lstPanel);
      lowerPanel.add(boxPanel);
      slctPanel.add(upperPanel);
      slctPanel.add(lowerPanel);
      
      panel.add(btnPanel);
      panel.add(txtPanel);
      panel.add(barPanel);
      panel.add(slctPanel);
      
      //add main panel to frame
      frame.add(panel);
      
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.pack();
      frame.setSize(500,600);
      frame.setResizable(true);
      frame.setVisible(true);
   }
   
   /** 
    * This action listener is called when the user clicks / enters
    * information using the GUI. 
    */
    public void actionPerformed(ActionEvent e)
    {
        //get source of listener
        Object o = e.getSource();
        
        //do corresponding action
        if (o == btn){
            System.out.println("Button clicked.");
        }
        if (o == toggleBtn){
            System.out.println("Toggle Button clicked.");
        }
        if (o == c1){
            System.out.println("Checkbox 1 clicked.");
        }
        if (o == c2){
            System.out.println("Checkbox 2 clicked.");
        }
        if (o == r1){
            System.out.println("Radio Button 1 clicked.");
        }
        if (o == r2){
            System.out.println("Radio Button 2 clicked.");
        }
        if (o == text){
            System.out.println("The text '"+text.getText()+"' was entered.");
        }
   }   
   
   /** 
    * This change listener is called when the user clicks / enters
    * information using the GUI. 
    */
    public void stateChanged(ChangeEvent e)
    {
        //gets source of change
        Object o = e.getSource();
        
        //does corresponding action
        if (o == slider) {
            System.out.println("Slider was set to "+slider.getValue());
        }  
        if (o == spinner) {
            System.out.println("Spinner was set to "+spinner.getValue());
        }
    }
    
    /** 
    * This list listener is called when the user clicks / enters
    * information using the GUI. 
    */
    public void valueChanged(ListSelectionEvent e)
    {
        //gets source of change
        Object o = e.getSource();
        
        //does corresponding action
        if (o == list) {
            System.out.println("List item selcted: "+list.getSelectedValue());
        }
    }
}