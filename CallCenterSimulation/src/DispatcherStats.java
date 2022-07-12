/**
 * This class will primarily contain getters and setters. It will keep track of each dispatcher's statistics.
 * @author patel
 *
 */
public class DispatcherStats {
    private int dispatcherNumber;
    private int callsFinished;
    private boolean inCall; // or waiting for a call
    private int queueCount;
    
    public DispatcherStats() {
    	dispatcherNumber = 0;
    	callsFinished = 0;
    	inCall = false;
    	queueCount = 0;
    }
    
    public DispatcherStats(int dispatcherNumber, int callsFinished, boolean inCall, int queueCount) {
    	this.dispatcherNumber = dispatcherNumber;
    	this.callsFinished = callsFinished;
    	this.inCall = inCall;
    	this.queueCount = queueCount;
    }
    
    public int getDispatcherNumber() {
    	return dispatcherNumber;
    }
    
    public int getCallsFinished() {
    	return callsFinished;
    }
    
    public boolean getInCall() {
    	return inCall;
    }
    
    public int getQueueCount() {
    	return queueCount;
    }
    
    public void setFinished(int prevFinish) {
    	callsFinished += prevFinish;
    }
    
    public void setQueueCount(int prevQueueCount) {
    	queueCount = prevQueueCount;
    }
    
    public void setInCall(boolean prevInCall) {
    	inCall = prevInCall;
    }
}
