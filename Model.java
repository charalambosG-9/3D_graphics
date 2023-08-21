import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;

public class Model{
  
  private Mesh mesh;
  private Texture textureId1; 
  private Texture textureId2; 
  private Material material;
  private Shader shader;
  private Mat4 modelMatrix;
  private Camera camera;
  private Light[] light;
  
  public Model(GL3 gl, Camera camera, Light[] light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, Texture textureId1, Texture textureId2) {
    this.mesh = mesh;
    this.material = material;
    this.modelMatrix = modelMatrix;
    this.shader = shader;
    this.camera = camera;
    this.light = light;
    this.textureId1 = textureId1;
    this.textureId2 = textureId2;
  }
  
  public Model(GL3 gl, Camera camera, Light[] light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, Texture textureId1) {
    this(gl, camera, light, shader, material, modelMatrix, mesh, textureId1, null);
  }
  
  public Model(GL3 gl, Camera camera, Light[] light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh) {
    this(gl, camera, light, shader, material, modelMatrix, mesh, null, null);
  }
  
  public void setModelMatrix(Mat4 m) {
    modelMatrix = m;
  }
  
  public void setCamera(Camera camera) {
    this.camera = camera;
  }
  
  public void setLight(Light[] light) {
    this.light = light;
  }

  public void render(GL3 gl, Mat4 modelMatrix) {
    Mat4 mvpMatrix = Mat4.multiply(camera.getPerspectiveMatrix(), Mat4.multiply(camera.getViewMatrix(), modelMatrix));

    shader.use(gl);
    shader.setFloatArray(gl, "model", modelMatrix.toFloatArrayForGLSL());
    shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());
    
    shader.setVec3(gl, "viewPos", camera.getPosition());

    shader.setVec3(gl, "pointLights[0].position", light[0].getPosition());
    shader.setVec3(gl, "pointLights[0].ambient", light[0].getMaterial().getAmbient());
    shader.setVec3(gl, "pointLights[0].diffuse", light[0].getMaterial().getDiffuse());
    shader.setVec3(gl, "pointLights[0].specular", light[0].getMaterial().getSpecular());
    shader.setFloat(gl,"pointLights[0].constant", 1.0f);
    shader.setFloat(gl,"pointLights[0].linear", 0.09f);
    shader.setFloat(gl,"pointLights[0].quadratic", 0.032f);

    shader.setVec3(gl, "pointLights[1].position", light[1].getPosition());
    shader.setVec3(gl, "pointLights[1].ambient", light[1].getMaterial().getAmbient());
    shader.setVec3(gl, "pointLights[1].diffuse", light[1].getMaterial().getDiffuse());
    shader.setVec3(gl, "pointLights[1].specular", light[1].getMaterial().getSpecular());
    shader.setFloat(gl,"pointLights[1].constant", 1.0f);
    shader.setFloat(gl,"pointLights[1].linear", 0.09f);
    shader.setFloat(gl,"pointLights[1].quadratic", 0.032f);

    shader.setVec3(gl, "spotLights[0].position", light[2].getPosition());
    shader.setVec3(gl, "spotLights[0].direction", light[2].getDirection());
    shader.setFloat(gl, "spotLights[0].cutOff", (float) Math.cos(12.4f));
    shader.setFloat(gl, "spotLights[0].outerCutOff", (float) Math.cos(17.5f));
    shader.setVec3(gl, "spotLights[0].ambient", light[2].getMaterial().getAmbient());
    shader.setVec3(gl, "spotLights[0].diffuse", light[2].getMaterial().getDiffuse());
    shader.setVec3(gl, "spotLights[0].specular", light[2].getMaterial().getSpecular());

    shader.setVec3(gl, "spotLights[1].position", light[3].getPosition());
    shader.setVec3(gl, "spotLights[1].direction", light[3].getDirection());
    shader.setFloat(gl, "spotLights[1].cutOff", (float) Math.cos(12.4f));
    shader.setFloat(gl, "spotLights[1].outerCutOff", (float) Math.cos(17.5f));
    shader.setVec3(gl, "spotLights[1].ambient", light[3].getMaterial().getAmbient());
    shader.setVec3(gl, "spotLights[1].diffuse", light[3].getMaterial().getDiffuse());
    shader.setVec3(gl, "spotLights[1].specular", light[3].getMaterial().getSpecular());
    
    shader.setVec3(gl, "material.ambient", material.getAmbient());
    shader.setVec3(gl, "material.diffuse", material.getDiffuse());
    shader.setVec3(gl, "material.specular", material.getSpecular());
    shader.setFloat(gl, "material.shininess", material.getShininess());  

    if (textureId1!=null) {
      shader.setInt(gl, "first_texture", 0);
      gl.glActiveTexture(GL.GL_TEXTURE0);
      textureId1.bind(gl);
    }
    if (textureId2!=null) {
      shader.setInt(gl, "second_texture", 1);
      gl.glActiveTexture(GL.GL_TEXTURE1);
      textureId2.bind(gl);
    }
    mesh.render(gl);
  } 
  
  public void render(GL3 gl) {
    render(gl, modelMatrix);
  }
  
  public void dispose(GL3 gl) {
    mesh.dispose(gl);
    if (textureId1!=null) textureId1.destroy(gl);
    if (textureId2!=null) textureId2.destroy(gl);
  }
  
}