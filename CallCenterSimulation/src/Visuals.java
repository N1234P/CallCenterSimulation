import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.*;
import org.apache.commons.lang3.time.DateUtils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

/**
 * 
 * Purpose of this class is to provide a simulator view for the client. 
 *
 */
@SuppressWarnings("serial")
public class Visuals extends JPanel implements ActionListener {
	QueueInitializer queue; 
	JFrame frame;
	
	boolean finished = false;
	
	SystemBehavior simulate = new SystemBehavior();
	Date simulatorTime = new Date();
	Date initialTime = new Date();
	
	
	public Visuals(QueueInitializer queue) {
		this.queue = queue;
	}
	
	public void beginSimulation() {
		frame = new JFrame("Simulation");
		frame.setSize(1500, 1200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		simulatorTime = Calendar.getInstance().getTime();
		initialTime = simulatorTime;
		
		frame.setBackground(Color.DARK_GRAY);
		frame.add(this);
	    
        Timer t = new Timer(500, this);		
		t.restart();
		
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
	
	/**
	 * Dispatcher chart that displays various statistics using the DispatcherStats class
	 * @param graphic
	 */
	public void drawBoard(Graphics graphic) {
		int x = 1200;
		int y = 30;
		List<DispatcherStats> dispatcherStats = simulate.getDStats();
		int ref = 0;
		
		graphic.drawString("Dispatcher #", x, y-3);
		graphic.drawString("Finished Calls", x+75, y-3);
		graphic.drawString("In Call?", x+170, y-3);
		graphic.drawString("QueueCount", x+230, y-3);
		
		for(int i = 0; i<queue.getCenter().getNumberOfDispatchers(); i++) {
			x = 1200;
			for(int j = 0; j<4; j++) {
				graphic.drawRect(x, y, 75, 15);
                x += 75;				 
			}
			
			graphic.drawString(String.valueOf(dispatcherStats.get(ref).getDispatcherNumber()+1), x - 67 * 4, y+13);
			graphic.drawString(String.valueOf(dispatcherStats.get(ref).getCallsFinished()), x - 67 * 4 + 75, y+13);
			graphic.drawString(String.valueOf(dispatcherStats.get(ref).getInCall()), x - 67 * 4 + 150, y+13);
			graphic.drawString(String.valueOf(dispatcherStats.get(ref).getQueueCount()), x - 67 * 4 + 225, y+13);
				 
			y+=18;
			ref++; 
		}
	}
	
	/**
	 * Pie chart, shows each state distributions
	 * @param graphic
	 */
	public void drawPie(Graphics graphic) {
		
	    Color[] colors = {Color.green, Color.red, Color.orange, Color.DARK_GRAY};
	    int start = 0;
	    double total = simulate.getBalking() + simulate.getFinished() + simulate.getInCall() + simulate.getInQueue();
	    
	    if(total == 0) 
	    	return;
	    
	    double[] values = {(simulate.getInCall() / total) * 360, (simulate.getInQueue() / total) * 360, (simulate.getBalking() / total) * 360, 
	    		(simulate.getFinished() / total) * 360};
	    
	    for(int i = 0; i<values.length; i++) {
	    	graphic.setColor(colors[i%colors.length]);
	    	if(values[i] > 0) 
	    		values[i] += 1;
	    	
	    	graphic.fillArc(830, 5, 150, 150, start, (int) values[i]);
	    	start = start + (int) values[i];
	    }
	    
	}
	
	/**
	 * Continuously repaints the simulator view & updates real-time.
	 */
	public void paint(Graphics graphic) {
		simulate.analyzeQueue(queue.getCenter(), simulatorTime); // logic call
		
		graphic.setFont(new Font("TimesRoman", Font.BOLD, 10)); 
		graphic.clearRect(0, 0, 2000, 200);
		graphic.clearRect(1195, 200, 405, 1400);
		graphic.drawString(String.valueOf(simulatorTime), 10, 10);
		
		graphic.setFont(new Font("Arial", Font.BOLD, 11)); 
		
		graphic.setColor(Color.GREEN);
		graphic.drawString("Number of Callers being attended to: " + String.valueOf(simulate.getInCall()),  50, 40);
		
		graphic.setColor(Color.RED);
		graphic.drawString("Number of Callers in the Queue: " + String.valueOf(simulate.getInQueue()), 322, 40);
		
		graphic.setColor(Color.ORANGE);
		graphic.drawString("Number of Hang Ups (Balking): " + String.valueOf(simulate.getBalking()), 570, 40);
		
		graphic.setColor(Color.DARK_GRAY);
		graphic.drawString("Number of Finished  Calls: " + simulate.getFinished(), 200, 120);
		
		graphic.setColor(Color.black);
		graphic.drawString("Total Time Elapsed: " + String.valueOf((simulatorTime.getTime() - initialTime.getTime()) / 60000) + " minutes", 450, 120);
		
		

		drawBoard(graphic);
		drawPie(graphic);
	     
		for(Customer customer : simulate.getCustomerData().keySet()) 
			customer.paint(graphic, simulate.getCustomerData());
					
		checkFinished();
		
	}
	
	/**
	 * This method will indicate when should the time elapsed and simulator time should be stopped.
	 */
	public void checkFinished() {
		if(queue.getCustomerCount() == (simulate.getFinished() + simulate.getBalking())) {
			finished = true;
		}
	}
	
	/**
	 * will keep calling the paint method and adjusts the simulatorTime.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!finished) {
		    simulatorTime = DateUtils.addMinutes(simulatorTime, 1);
		    repaint();
		}
	}
	
	
	
	
	

	
	
}
