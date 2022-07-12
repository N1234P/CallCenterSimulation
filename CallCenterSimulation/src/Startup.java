import javax.swing.JButton;
    
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * User window class, this class will determine the parameters used for QueueInitializer for dispatcher count & customer count
 *
 */

@SuppressWarnings("serial")
public class Startup extends JPanel implements ActionListener {
		public Startup() {
			 JFrame frameStartup = new JFrame("Simulation Startup");
			 frameStartup.setSize(400, 400);
			 
			 JLabel entityLabel = new JLabel("Customers to simulate?");
             entityLabel.setBounds(20, 20, 150, 50);
             
			 JTextField entityQuery = new JTextField();
			 entityQuery.setBounds(180, 35, 200, 25);
			 
			 
			 JLabel dispatcherLabel = new JLabel("Number of Dispatchers? (45 MAX)");
             dispatcherLabel.setBounds(20, 70, 300, 50);
			 
			 JTextField dispatcherQuery = new JTextField();
			 dispatcherQuery.setBounds(240, 85, 140, 25);
			 
			 
			 JButton startButton = new JButton("Start");
			 startButton.setBounds(150,300,100,30);
			 
			 
			
			 startButton.addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent e) {
					    Integer customerCount = -1;
					    Integer dispatcherCount = -1;
					    
					    try {
					    	customerCount = Integer.valueOf(entityQuery.getText());
					    	if(customerCount < 1) 
					    		customerCount = 200; // 200 set to default if user input is not valid.
					    }
					    catch(Exception exception) {
					    	customerCount = 200; 
					
					    }
					    
					    try {
					    	dispatcherCount = Integer.valueOf(dispatcherQuery.getText()); 
					    	if(dispatcherCount > 45 || dispatcherCount < 1) {
					    		throw new Exception();
					    	}
					    }
					    catch(Exception exception) {
					    	RandomVM random = new RandomVM();
					    	dispatcherCount = random.nextInt(10, 20); // randomly generate if user input is not valid.
					    }
					    
						QueueInitializer queue = new QueueInitializer(customerCount, dispatcherCount);
						queue.printEntities();
						Visuals visual = new Visuals(queue);
						visual.beginSimulation();
						frameStartup.dispose();
				 }
			 });
			 
			 frameStartup.add(entityLabel);
			 frameStartup.add(entityQuery);
			 frameStartup.add(dispatcherLabel);
			 frameStartup.add(dispatcherQuery);
			 frameStartup.add(startButton);
			 frameStartup.add(this);
			 frameStartup.setVisible(true);
			 
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			return;
		}
			
}


		
