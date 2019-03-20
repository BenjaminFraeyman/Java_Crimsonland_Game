/* Author: Benjamin Fraeyman */
package Components;
import org.ibcn.gso.esf.Component;
import java.util.ArrayList;
public class KeyPressedComponent implements Component {
    public ArrayList<String> keyCodeList = new ArrayList<>();
    public double mouseXPosition;
    public double mouseYPosition;
    public boolean mousePressed;
}