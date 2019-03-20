/* Author: Benjamin Fraeyman */
package Components;
import org.ibcn.gso.esf.Component;
public class PlayerComponent implements Component{
    public int hp = 100;
    public int maxHp = 100;
    public int score = 0;
    public int scoreNeeded = 100;
    public double damageMultiplier = 1;
    public int killCounter = 0;
    //public boolean started = false;
    public PlayerComponent() {}
}