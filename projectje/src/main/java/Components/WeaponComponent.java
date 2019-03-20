/* Author: Benjamin Fraeyman */
package Components;
import org.ibcn.gso.esf.Component;
import Loaders.SpriteLoader;
public class WeaponComponent implements Component {
    public int pellets = 1;
    public int spread = 1;
    public int ammocapacity = 12;
    public int ammo = 12;
    public boolean reloading = false;
    public int reloadspeed = 30;// 30 frames
    public String weaponType = "pistol";
    public SpriteLoader.Type weapon = SpriteLoader.Type.PISTOL;
}