import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class Client extends JFrame{

    public static void main(String[] args){
        //Panels
        ViewStudentPanel studentList = new ViewStudentPanel();

        JScrollPane scroll = new JScrollPane(studentList);
        scroll.setPreferredSize(new Dimension(1020, 500));

        JMenuBar NavBar = new JMenuBar();


        JMenu files = new JMenu("Options");
        JMenuItem Refresh = new JMenuItem("Refresh");
        JMenuItem Extract = new JMenuItem("Extract XML");

        files.add(Refresh);
        files.add(Extract);


        JMenu Sort = new JMenu("Sort");
        JMenuItem SortN = new JMenuItem("Sort By Name");
        JMenuItem  SortA = new JMenuItem("Sort by Age");

        Sort.add(SortN);
        Sort.add(SortA);



        NavBar.add(files);
        NavBar.add(Sort);
    


        // main Frame 
        Client mainFrame = new Client();
        //mainFrame.setSize(1000, 700);
        mainFrame.setTitle("Student Client");
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);


        mainFrame.setJMenuBar(NavBar);
        mainFrame.add(scroll);
        mainFrame.pack();   
        mainFrame.setVisible(true);

        
        

        //Actions 
        Refresh.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                studentList.fetch("Select * from student");
            }
            
        });

        Extract.addActionListener(new ActionListener() {

            
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File xmlFile = new File("xml/StudentList.xml");
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(xmlFile);
                    doc.getDocumentElement().normalize();


                    NodeList list = doc.getElementsByTagName("Student");

                    for(int i = 0; i < list.getLength(); i++){
                        Node nNode =  list.item(i);

                        if(nNode.getNodeType() == Node.ELEMENT_NODE){
                            Element element = (Element) nNode;
                            Student xmlStudent = new Student();
                            System.out.println("Student ID: " + element.getAttribute("id"));
                            System.out.println("    Student Name: " + element.getElementsByTagName("name").item(0).getTextContent());
                            System.out.println("    Student Age: " + element.getElementsByTagName("age").item(0).getTextContent());
                            System.out.println("    Student Address: " + element.getElementsByTagName("address").item(0).getTextContent());
                            System.out.println("    Student Contact: " + element.getElementsByTagName("contact").item(0).getTextContent());

                            xmlStudent.setName(element.getElementsByTagName("name").item(0).getTextContent());
                            xmlStudent.setAge(Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()));
                            xmlStudent.setAddress(element.getElementsByTagName("address").item(0).getTextContent());
                            xmlStudent.setContact(element.getElementsByTagName("contact").item(0).getTextContent());

                            try {
                                Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9100);
                                DB student = (DB) registry.lookup("d");
                                student.QueryBuild("INSERT INTO student(NAME, AGE, ADDRESS, CONTACT_NUMBER) VALUES('"+xmlStudent.getName()+"', "+xmlStudent.getAge()+", '"+xmlStudent.getAddress()+"', '"+xmlStudent.getContact()+"')");
                                System.out.println("Data is Successfully Migrated.........");
                            } catch (Exception c) {

                                c.printStackTrace();
                            }
                    
                        }
                        
                    }
                    System.out.println("XML Extracting done.........");
                    
                    
                } catch (Exception b) {
                    
                    b.printStackTrace();
                }
                studentList.fetch("Select * from student");

            }
            
        });

        SortN.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        studentList.fetch("Select * from student Order by NAME");
                    }
                    
        });


        SortA.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        studentList.fetch("Select * from student Order by AGE");
                    }
                    
        });
        
    }
}