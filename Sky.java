import gmaths.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;


public class Sky {

    private Camera camera;
    private Light[] light;
    private GL3 gl;
    private SGNode skyRoot;

    public Sky(GL3 gl, Camera camera, Light[] light) {
        this.camera = camera;
        this.light = light;
        this.gl = gl;
    }

    public SGNode makeSky(float angle, Texture[] textures){

        Texture textureSkyMiddle = textures[0]; 
        Texture textureBuilding = textures[1];
        Texture textureSkyLeft= textures[2]; 
        Texture textureSkyTop = textures[3]; 
        Texture textureSkyBottom = textures[4]; 
        Texture textureSkyRight = textures[5]; 
        Texture textureSkyBack = textures[6]; 

      
        Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        Shader shader = new Shader(gl, "vertex_shaders/vs_sky.txt", "fragment_shaders/fs_sky.txt");
        Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);
        Model skyMiddle = new Model(gl, camera, light, shader, material, new Mat4(1), mesh, textureSkyMiddle);

        Model skyLeft = new Model(gl, camera, light, shader, material, new Mat4(1), mesh, textureSkyLeft);

        Model skyRight = new Model(gl, camera, light, shader, material, new Mat4(1), mesh, textureSkyRight);

        Model skyTop = new Model(gl, camera, light, shader, material, new Mat4(1), mesh, textureSkyTop);

        Model skyBottom = new Model(gl, camera, light, shader, material, new Mat4(1), mesh, textureSkyBottom);

        Model skyBack = new Model(gl, camera, light, shader, material, new Mat4(1), mesh, textureSkyBack);

        mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        shader = new Shader(gl, "vertex_shaders/vs_sky.txt", "fragment_shaders/fs_sky.txt");
        material = new Material(new Vec3(0.4f, 0.4f, 0.4f), new Vec3(0.4f, 0.4f, 0.4f), new Vec3(0.4f, 0.4f, 0.4f), 32.0f);
        Model building = new Model(gl, camera, light, shader, material, new Mat4(1), mesh, textureBuilding);

        float size = 60f;
        float buldingSize = 50f;

        skyRoot = new NameNode("sky root");

        TransformNode translateX = new TransformNode("translate", Mat4Transform.translate(0,0,0));
        
        Mat4 m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundX(90),m);
        m = Mat4.multiply(Mat4Transform.rotateAroundY(20), m);
        
        TransformNode rotateAll  = new TransformNode("Initial rotation", m);

        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(-angle), m);

        TransformNode rotateSky  = new TransformNode("Slowyly rotate the sky", m);

        NameNode backWallName = new NameNode("Back wall");
        
        m = new Mat4(1);
        
        TransformNode backWallRotate = new TransformNode("Back wall rotate", m);

        TransformNode backWallTransform = new TransformNode("Back wall transform", Mat4Transform.translate(0, -size * 0.5f, -size * 0.11f));

        TransformNode backWallScale = new TransformNode("Back wall scale", Mat4Transform.scale(size, 1f, size));
        
        ModelNode backWallNode = new ModelNode("Back wall", skyMiddle);
        
        NameNode rightWallName = new NameNode("Right wall");

        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(90),m);

        TransformNode rightWallRotate = new TransformNode("Right wall rotate", m);

        TransformNode rightWallTransform = new TransformNode("Right wall transform", Mat4Transform.translate(0, -size * 0.5f, -size * 0.11f));

        TransformNode rightWallScale = new TransformNode("Right wall scale", Mat4Transform.scale(size, 1f, size));
        
        ModelNode rightWallNode = new ModelNode("Right wall", skyRight);

        NameNode leftWallName = new NameNode("Left wall");

        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(-90),m);

        TransformNode leftWallRotate = new TransformNode("Left wall rotate", m);

        TransformNode leftWallTransform = new TransformNode("Left wall transform", Mat4Transform.translate(0, -size * 0.5f, -size * 0.11f));

        TransformNode leftWallScale = new TransformNode("Left wall scale", Mat4Transform.scale(size, 1f, size));

        ModelNode leftWallNode = new ModelNode("Left wall", skyLeft);

        NameNode frontWallName = new NameNode("Front wall");

        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(-180),m);

        TransformNode frontWallRotate = new TransformNode("Front wall rotate", m);

        TransformNode frontWallTransform = new TransformNode("Front wall transform", Mat4Transform.translate(0, -size * 0.5f, -size * 0.11f));

        TransformNode frontWallScale = new TransformNode("Front wall scale", Mat4Transform.scale(size, 1f, size));

        ModelNode frontWallNode = new ModelNode("Front wall", skyBack);

        NameNode ceillingName = new NameNode("Ceilling");

        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundX(90),m);

        TransformNode ceillingRotate = new TransformNode("Ceilling rotate", m);

        TransformNode ceillingTransform = new TransformNode("Ceilling transform", Mat4Transform.translate(0, -size + 25f, 0));

        TransformNode ceillingScale = new TransformNode("Ceilling scale", Mat4Transform.scale(size, 1f, size));

        ModelNode ceillingNode = new ModelNode("Ceilling", skyTop);

        NameNode bottomName = new NameNode("River");

        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(180),m);
        m = Mat4.multiply(Mat4Transform.rotateAroundX(90),m);

        TransformNode bottomRotate = new TransformNode("River rotate", m);

        TransformNode bottomTransform = new TransformNode("River transform", Mat4Transform.translate(0, -size * 0.5f + 7f , 0));

        TransformNode bottomScale = new TransformNode("River scale", Mat4Transform.scale(size, 1f, size));

        ModelNode bottomNode = new ModelNode("River", skyBottom);

        NameNode building1Name = new NameNode("Building 1 name");

        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(-90),m);

        TransformNode building1Rotate = new TransformNode("Building 1 rotate", m);

        TransformNode building1Transform = new TransformNode("Building 1 transform", Mat4Transform.translate(-5f, -buldingSize * 0.33f, -buldingSize * 0.11f));

        TransformNode building1Scale = new TransformNode("Building 1 scale", Mat4Transform.scale(buldingSize, 1 , buldingSize));

        ModelNode building1Node = new ModelNode("Building 1", building);

        NameNode building2Name = new NameNode("Building 2 name");

        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(90),m);

        TransformNode building2Rotate = new TransformNode("Building 2 rotate", m);

        TransformNode building2Transform = new TransformNode("Building 2 transform", Mat4Transform.translate(5f, -buldingSize * 0.33f, -buldingSize * 0.11f));

        TransformNode building2Scale = new TransformNode("Building 2 scale", Mat4Transform.scale(buldingSize, 1, buldingSize));

        ModelNode building2Node = new ModelNode("Building 2", building);


        skyRoot.addChild(translateX);
          translateX.addChild(rotateAll);
            rotateAll.addChild(rotateSky);
              rotateSky.addChild(backWallRotate);
                backWallRotate.addChild(backWallTransform);
                  backWallTransform.addChild(backWallName);
                    backWallName.addChild(backWallScale);
                      backWallScale.addChild(backWallNode);
            rotateAll.addChild(rotateSky);
              rotateSky.addChild(rightWallRotate);
                rightWallRotate.addChild(rightWallTransform);
                  rightWallTransform.addChild(rightWallName);
                    rightWallName.addChild(rightWallScale);
                      rightWallScale.addChild(rightWallNode);
            rotateAll.addChild(rotateSky);
              rotateSky.addChild(leftWallRotate);
                leftWallRotate.addChild(leftWallTransform);
                  leftWallTransform.addChild(leftWallName);
                    leftWallName.addChild(leftWallScale);
                      leftWallScale.addChild(leftWallNode);
            rotateAll.addChild(rotateSky);
              rotateSky.addChild(frontWallRotate);
                frontWallRotate.addChild(frontWallTransform);
                  frontWallTransform.addChild(frontWallName);
                    frontWallName.addChild(frontWallScale);
                      frontWallScale.addChild(frontWallNode);
            rotateAll.addChild(rotateSky);
              rotateSky.addChild(ceillingRotate);
                ceillingRotate.addChild(ceillingTransform);
                  ceillingTransform.addChild(ceillingName);
                    ceillingName.addChild(ceillingScale);
                      ceillingScale.addChild(ceillingNode);
            rotateAll.addChild(rotateSky);
              rotateSky.addChild(bottomRotate);
                bottomRotate.addChild(bottomTransform);
                  bottomTransform.addChild(bottomName);
                    bottomName.addChild(bottomScale);
                      bottomScale.addChild(bottomNode);
            rotateAll.addChild(building1Rotate);
              building1Rotate.addChild(building1Transform);
                building1Transform.addChild(building1Name);
                  building1Name.addChild(building1Scale);
                    building1Scale.addChild(building1Node);
            rotateAll.addChild(building2Rotate);
              building2Rotate.addChild(building2Transform);
                building2Transform.addChild(building2Name);
                  building2Name.addChild(building2Scale);
                    building2Scale.addChild(building2Node);
         
          skyRoot.update();

        return skyRoot;

    }

}
