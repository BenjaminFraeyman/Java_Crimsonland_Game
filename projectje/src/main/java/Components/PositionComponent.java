/* Author: Benjamin Fraeyman */
package Components;
import org.ibcn.gso.esf.Component;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
public class PositionComponent implements Component{
    public double xPosition;
    public double yPosition;
    public double rPosition;
    //public Point2D xyPosition;
    //public Rectangle2D hitBox;
    public PositionComponent(double x,double y) {
        xPosition = x;
        yPosition = y;
    }
}