/* Author: Benjamin Fraeyman */
package Components;
import org.ibcn.gso.esf.Component;
import java.awt.geom.Rectangle2D;
public class HitBoxComponent  implements Component {
    public Rectangle2D hitbox;
    public HitBoxComponent(double x, double y, double w, double h) {
        this.hitbox = new Rectangle2D.Double(x, y, w, h);
    }
}