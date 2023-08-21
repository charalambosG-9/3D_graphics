import gmaths.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;


public class Cow {

    private Camera camera;
    private Light[] light;
    private GL3 gl;
    private float windowSize = 25f;
    SGNode cow;
    Vec3 cowSize = new Vec3(25f, 0.1f, 25f);

    public Cow(GL3 gl, Camera camera, Light[] light) {
        this.camera = camera;
        this.light = light;
        this.gl = gl;
    }

    public SGNode makeCow(Texture[] textures, float cowPosition){
        
        Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        Shader shader = new Shader(gl, "vertex_shaders/vs_cow.txt", "fragment_shaders/fs_cow.txt");
        Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);
        Mat4 modelMatrix = new Mat4(1);
        Model cowModel = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textures[0]);

        SGNode cow = new SGNode("cow");

        TransformNode translateToOrigin = new TransformNode("translate", Mat4Transform.translate(-8f, cowPosition-5, -20)); // 25/2

        modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
        //modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(cowPosition), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        TransformNode rotateAll  = new TransformNode("Initial rotation", modelMatrix);

        TransformNode cowScale = new TransformNode("scale", Mat4Transform.scale(cowSize));

        NameNode cowName = new NameNode("cow");

        ModelNode cowNode = new ModelNode("cow", cowModel);

        cow.addChild(translateToOrigin);
            translateToOrigin.addChild(rotateAll);
                rotateAll.addChild(cowName);
                    cowName.addChild(cowScale);
                        cowScale.addChild(cowNode);

        cow.update();

        return cow;

    }

    // private Mat4 getMforCow(float cowPosition) {
    //     Mat4 modelMatrix = new Mat4(1);
    //     modelMatrix = Mat4.multiply(Mat4Transform.scale(windowSize,1f,windowSize), modelMatrix);
    //     modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
    //     modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(cowPosition), modelMatrix);
    //     modelMatrix = Mat4.multiply(Mat4Transform.translate(0,windowSize*0.5f,-windowSize+5), modelMatrix);
    //     modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
    //     return modelMatrix;
    // }
    
}
