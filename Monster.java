import gmaths.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;


public class Monster {

    private Camera camera;
    private Light[] light;
    private GL3 gl;
    SGNode monster;
    Vec3 monsterSize = new Vec3(25f, 0.1f, 25f);

    public Monster(GL3 gl, Camera camera, Light[] light) {
        this.camera = camera;
        this.light = light;
        this.gl = gl;
    }

    public SGNode makeMonster(Texture[] textures, float monsterPositionY, float monsterPositionX){
        
        Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        Shader shader = new Shader(gl, "vertex_shaders/vs_monster.txt", "fragment_shaders/fs_monster.txt");
        Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);
        Mat4 modelMatrix = new Mat4(1);
        Model monsterModel = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textures[0]);

        monster = new SGNode("Monster");

        TransformNode translateToOrigin = new TransformNode("translate", Mat4Transform.translate(0, monsterPositionY, -25f));

        modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        TransformNode rotateAll  = new TransformNode("Initial rotation", modelMatrix);

        NameNode monsterName = new NameNode("Monster name");

        TransformNode monsterTransform = new TransformNode("Monster transform", Mat4Transform.translate(monsterPositionX, 0, 0));

        TransformNode monsterScale = new TransformNode("Monster scale", Mat4Transform.scale(monsterSize));

        ModelNode monsterNode = new ModelNode("Monster node", monsterModel);

        monster.addChild(translateToOrigin);
            translateToOrigin.addChild(rotateAll);
                rotateAll.addChild(monsterTransform);
                    monsterTransform.addChild(monsterName);
                        monsterName.addChild(monsterScale);
                            monsterScale.addChild(monsterNode);

        monster.update();

        return monster;

    }
    
}
