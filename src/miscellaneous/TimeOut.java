package miscellaneous;

import java.util.Timer;
import java.util.TimerTask;

class TimeOut extends TimerTask{

    private Thread t;
    private Timer timer;

    void TimeOutTask(Thread t, Timer timer){
        this.t = t;
        this.timer = timer;
    }
 
    public void run() {
        if (this.t != null && this.t.isAlive()) {
            this.t.interrupt();
            this.timer.cancel();
        }
    }
}
