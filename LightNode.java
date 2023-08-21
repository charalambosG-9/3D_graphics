import com.jogamp.opengl.*;
import gmaths.Vec4;
import gmaths.Vec3;
import gmaths.Mat4;

public class LightNode extends SGNode {

  protected Model model;
  protected Light light;
  protected ModelNode head;
  protected int lamp;

  public LightNode(String name, Light m, ModelNode h, int l) {
    super(name);
    light = m; 
    head = h;
    lamp = l;
  }

  public void draw(GL3 gl) {
    
    setLightPosition();

    setLightDirection();

    light.renderNode(gl, worldTransform);
    for (int i = 0; i < children.size(); i++) {
      children.get(i).draw(gl);
    }
  }

  public void setLightPosition(){

    Mat4 mat = new Mat4(0f);

    mat.set(0,3, 3);
    mat.set(1,3, 2);
    mat.set(2,3, 1);
    mat.set(3, 3, 1);
    
    Mat4 newmat = Mat4.multiply(worldTransform, mat);

    float[] result = newmat.toFloatArrayForGLSL();

    light.setPosition(new Vec3(result[12], result[13], result[14]));

  }

  public void setLightDirection(){

    Mat4 mat = new Mat4(0f);
    if(lamp == 1){
      mat.set(0,3, 2f);
      mat.set(1,3, 1f);
      mat.set(2,3, 0.55f);
      mat.set(3, 3, 1);
    }else{
      mat.set(0,3, 2f);
      mat.set(1,3, 1f);
      mat.set(2,3, 0.75f);
      mat.set(3, 3, 1);
    }
    
    Mat4 newmat = Mat4.multiply(head.getWorldTransform(), mat);

    float[] result = newmat.toFloatArrayForGLSL();
    Vec3 posHead = new Vec3(result[12], result[13], result[14]);
    Vec3 direction = Vec3.subtract(light.getPosition(), posHead);
    light.setDirection(direction);

  }

}