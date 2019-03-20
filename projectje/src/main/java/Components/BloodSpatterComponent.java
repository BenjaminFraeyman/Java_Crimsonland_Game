/* Author: Benjamin Fraeyman */
package Components;
import org.ibcn.gso.esf.Component;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BloodSpatterComponent implements Component {
    public List<Point2D.Double> spatterList = new ArrayList<>();
}