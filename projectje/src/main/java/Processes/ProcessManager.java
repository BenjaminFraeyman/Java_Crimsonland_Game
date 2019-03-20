package Processes;

import java.util.ArrayList;
import java.util.List;

public class ProcessManager {
    private List<Process> procesList = new ArrayList();

    public boolean addProcess(Process proces ,int priority)
    {
        if(proces.started()){
            procesList.add(proces);
            return true;
        }
        return false;
    }

    public void Update()
    {
        for (Process proces : procesList){
            proces.Update();
        }
    }

    public void Remove(Process process){
        process.End();
        procesList.remove(process);
    }
}
