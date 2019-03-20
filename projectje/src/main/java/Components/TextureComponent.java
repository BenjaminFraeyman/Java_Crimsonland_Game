package Components;

import javafx.scene.image.Image;
import org.ibcn.gso.esf.Component;

public class TextureComponent implements Component {
    public Image image;

    public TextureComponent(Image image){
        this.image = image;
    }
}