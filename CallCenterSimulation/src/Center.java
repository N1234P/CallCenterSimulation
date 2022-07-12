import java.util.*;
/**
 * This class will be the outermost level of the multi-queue system 
 *
 */
public class Center {
    private List<Dispatcher> dispatchers;
    
    public Center() {
    	dispatchers = new ArrayList<Dispatcher>();
    }
    
    public List<Dispatcher> getDispatchers() {
    	return dispatchers;
    }
    
    public void addDispatcher(Dispatcher dispatcher) {
    	dispatchers.add(dispatcher);
    }
    
    public int getNumberOfDispatchers() {
    	return dispatchers.size();
    }
    
    public Dispatcher getDispatcher(int index) { 
    	return dispatchers.get(index);
    }
}
