import com.jogamp.opengl.*;

public class ModelNodeHead extends SGNode {

  protected Model model;
  protected Light light;

  public ModelNodeHead(String name, Model m, Light l) {
    super(name);
    model = m; 
    light = l;
  }

  public void draw(GL3 gl) {
    light.setHeadDirection(worldTransform);
    model.render(gl, worldTransform);
    for (int i = 0; i < children.size(); i++) {
      children.get(i).draw(gl);
    }
  }

}