/* Author: Benjamin Fraeyman */
package Components;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.ibcn.gso.esf.Component;
public class WorldComponent implements Component {
    public Canvas canvas;
    public Image image;
    public WorldComponent(Canvas canvas, Image image){
        this.canvas = canvas;
        this.image = image;
    }
}