import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;    

public class ViewStudentPanel extends JPanel {
    JLabel Message = new JLabel();

    ViewStudentPanel(){
        this.setBackground(new Color(164, 164, 164));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.fetch("Select * from student");
        this.add(Message);
    }




    public void fetch(String query){
        this.setVisible(false);
        this.removeAll();
        System.out.println("Fetching Data......");
        try {
            Message.setText(null);
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9100);
			DB student = (DB) registry.lookup("d");
			ArrayList<Student> list = student.getQueryResult(query);
            
            for (Student queryResult : list) {

                JPanel StudentQueue = new JPanel();
                StudentQueue.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
                StudentQueue.setPreferredSize(new Dimension(1000, 150));
                JLabel name = new JLabel("Name: "+ queryResult.getName());
                name.setFont(new Font("Arial", Font.BOLD, 15));
                JLabel age = new JLabel("Age: "+ queryResult.getAge());
                age.setFont(new Font("Arial", Font.BOLD, 15));
                JLabel address = new JLabel("Address: "+ queryResult.getAddress());
                address.setFont(new Font("Arial", Font.BOLD, 15));
                JLabel contact = new JLabel("Contact: "+ queryResult.getContact());
                contact.setFont(new Font("Arial", Font.BOLD, 15));
                JButton Delete = new JButton("Delete");
                JButton Update = new JButton("Update");

                Delete.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            student.QueryBuild("Delete from student where id="+queryResult.getID());
                            
                        } catch (Exception b) {
                            b.printStackTrace();
                        }
                        fetch("Select * from student");
                    }
                    
                    
                });

                Update.addActionListener(new ActionListener() {

                    JLabel NameLabel = new JLabel("Full Name: ");
                    JLabel AgeLabel = new JLabel("AGE: ");
                    JLabel AddressLabel = new JLabel("Address: ");
                    JLabel ContactLabel = new JLabel("Contact Number: ");
                    
                    JButton Submit = new JButton("Submit");
                    
                    JTextField Name = new JTextField();
                    JTextField Age = new JTextField();
                    JTextField Address = new JTextField();
                    JTextField Contact = new JTextField();

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame updateinput = new JFrame("Update");
                        updateinput.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        updateinput.setVisible(true);
                        updateinput.setLocationRelativeTo(null);
                        updateinput.setSize(500, 500);
                        updateinput.setResizable(false);

                        JPanel updatePanel = new JPanel();


                        updatePanel.setBackground(new Color(164, 164, 164));
                        Submit.setBounds(200, 380 , 100, 65);
                        Submit.setBackground(Color.white);
                        Submit.setBorder(null);
                        Submit.setFocusable(false);
                        Submit.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {

                                    student.QueryBuild("UPDATE student SET NAME='"+Name.getText()+"', AGE ="+Age.getText()+", ADDRESS = '"+Address.getText()+"',  CONTACT_NUMBER = '"+Contact.getText()+"' WHERE id=" + queryResult.getID());
                                    updateinput.dispose();
                                    System.out.println("Updating Student done.........");
                                    fetch("Select * from student");
                                    
                                } catch (Exception a) {
                                    a.printStackTrace();
                                }
                            }
                            
                        });


                        NameLabel.setBounds(10, 40, 100, 35);
                        Name.setBounds(110, 40 , 300 , 35);
                        Name.setText(queryResult.getName());
                       

                        AgeLabel.setBounds(10, 100, 100, 35);
                        Age.setBounds(110, 100 , 300 , 35);
                        Age.setText(Integer.toString(queryResult.getAge()));

                        AddressLabel.setBounds(10, 160, 100, 35);
                        Address.setBounds(110, 160, 300 , 35);
                        Address.setText(queryResult.getAddress());

                        ContactLabel.setBounds(10, 220, 100, 35);
                        Contact.setBounds(110, 220 , 300 , 35);
                        Contact.setText(queryResult.getContact());
                        
                        updatePanel.add(NameLabel);
                        updatePanel.add(AgeLabel);
                        updatePanel.add(AddressLabel);
                        updatePanel.add(ContactLabel);
                        updatePanel.add(Name);
                        updatePanel.add(Age);
                        updatePanel.add(Address);
                        updatePanel.add(Contact);
                        updatePanel.add(Submit);

                        updatePanel.setLayout(null);
                        updateinput.add(updatePanel);
                    }
                    
                });

                StudentQueue.add(name);
                StudentQueue.add(age);
                StudentQueue.add(address);
                StudentQueue.add(contact);
                StudentQueue.add(Update);
                StudentQueue.add(Delete);

                this.add(StudentQueue);
                
                System.out.println(queryResult.getName());
                System.out.println(list.size());
                if(list.size() == 0){

                    Message.setText("There is no record.");
                    this.add(Message);
                }
            }

        } catch (Exception e) {
            Message.setText("There is no connection in the Server.");
            this.add(Message);
            e.printStackTrace();
        }

        this.setVisible(true);
    }
    
}
