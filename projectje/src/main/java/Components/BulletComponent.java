/* Author: Benjamin Fraeyman */
package Components;
import org.ibcn.gso.esf.Component;

public class BulletComponent implements Component {
    public int damage = 10;
    public double range = 500;
    public double radius = 0;
    public boolean detonate = false;
    public String weaponType;
    public BulletComponent(String weaponType) { this.weaponType = weaponType; }
}