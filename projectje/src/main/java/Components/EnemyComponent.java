/* Author: Benjamin Fraeyman */
package Components;
import org.ibcn.gso.esf.Component;
import Loaders.SpriteLoader;
public class EnemyComponent implements Component{
    public int hp;
    public int damage;
    public int xpReward;
    public double speed;
    public String enemyType;
    public SpriteLoader.Type type;
    public EnemyComponent(String enemyType) {
        this.enemyType = enemyType;
    }
}