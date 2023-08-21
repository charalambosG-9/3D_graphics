import gmaths.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;



public class Egg {
    
    private Camera camera;
    private Light[] light;
    private GL3 gl;

    public Egg(GL3 gl, Camera camera, Light[] light) {
        this.camera = camera;
        this.light = light;
        this.gl = gl;
    }

    public SGNode makeEggStructure(float eggAngle, float eggAngleZ , float eggHeight, Texture[] textures) {

        float baseHeight = 0.5f;
        float baseWidth = 4f;
        float tableSize = 7f; 
        float tableHeight = 4f; 
        float tableThickness = 1f; 
        float legHeight = tableHeight-tableThickness; 
        float legWidth = 1f;  

        Vec3 eggSize = new Vec3(3f, 5f, 3f);

        Texture textureBase = textures[0];
        Texture textureEgg = textures[1];
        Texture textureEggSpecular = textures[2];
        Texture textureTable = textures[3];

        Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
        Shader shader = new Shader(gl, "vertex_shaders/vs_cube_04.txt", "fragment_shaders/fs_cube_04.txt");
        Material material = new Material(new Vec3(0.3f, 0.3f, 0.3f), new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.8f, 0.8f, 0.8f), 32.0f);
        Mat4 modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
        Model cubeBase = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureBase);

        mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
        shader = new Shader(gl, "vertex_shaders/vs_cube_04.txt", "fragment_shaders/fs_cube_04.txt");
        material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
        Model cubeTable = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureTable);

        mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
        shader = new Shader(gl, "vertex_shaders/vs_sphere_04.txt", "fragment_shaders/fs_sphere_04.txt");    
        material = new Material(new Vec3(0.3f, 0.3f, 0.3f), new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.8f, 0.8f, 0.8f), 32.0f);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(3,3,3), Mat4Transform.translate(0,0.5f,0));
        modelMatrix = Mat4.multiply(Mat4Transform.translate(0,4,0), modelMatrix);
        Model sphere = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureEgg, textureEggSpecular);

        SGNode eggStructure = new SGNode("Egg structure");
        
        TransformNode translateToOrigin = new TransformNode("translate", Mat4Transform.translate(0f, 0f,0f));

        Mat4 m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundY(20),m);
        
        TransformNode rotateAll  = new TransformNode("Initial rotation", m);

        NameNode tableBodyName = new NameNode("Table body");
        
        TransformNode tableBodyTranslate = new TransformNode("Translate table body", Mat4Transform.translate(0, tableHeight,0));

        TransformNode tableBodyScale = new TransformNode("Scale table body", Mat4Transform.scale(tableSize, tableThickness, tableSize));

        ModelNode tableBodyNode = new ModelNode("Table body node", cubeTable);

        NameNode tableBackLeftLegName = new NameNode("Table back left leg");

        TransformNode tableBackLeftLegTranslate = new TransformNode("Translate table back left leg", Mat4Transform.translate(-tableSize/2 + legWidth/2, -tableHeight/2 - tableThickness/4, -tableSize/2 + legWidth/2));

        TransformNode tableBackLeftLegScale = new TransformNode("Scale table back left leg", Mat4Transform.scale(legWidth, legHeight + tableThickness/2, legWidth));

        ModelNode tableBackLeftLegNode = new ModelNode("Table back left leg node", cubeTable);

        NameNode tableFrontLeftLegName = new NameNode("Table front left leg");

        TransformNode tableFrontLeftLegTranslate = new TransformNode("Translate table front left leg", Mat4Transform.translate(-tableSize/2 + legWidth/2, -tableHeight/2 - tableThickness/4, tableSize/2 - legWidth/2));

        TransformNode tableFrontLeftLegScale = new TransformNode("Scale table front left leg", Mat4Transform.scale(legWidth, legHeight + tableThickness/2, legWidth));

        ModelNode tableFrontLeftLegNode = new ModelNode("Table front left leg node", cubeTable);

        NameNode tableFrontRightLegName = new NameNode("Table front right leg");

        TransformNode tableFrontRightLegTranslate = new TransformNode("Translate table front right leg", Mat4Transform.translate(tableSize/2 - legWidth/2, -tableHeight/2 - tableThickness/4, tableSize/2 - legWidth/2));

        TransformNode tableFrontRightLegScale = new TransformNode("Scale table front right leg", Mat4Transform.scale(legWidth, legHeight + tableThickness/2, legWidth));

        ModelNode tableFrontRightLegNode = new ModelNode("Table front right leg node", cubeTable);

        NameNode tableBackRightLegName = new NameNode("Table back right leg");

        TransformNode tableBackRightLegTranslate = new TransformNode("Translate table back right leg", Mat4Transform.translate(tableSize/2 - legWidth/2, -tableHeight/2 - tableThickness/4, - tableSize/2 + legWidth/2));

        TransformNode tableBackRightLegScale = new TransformNode("Scale table back right leg", Mat4Transform.scale(legWidth, legHeight + tableThickness/2, legWidth));

        ModelNode tableBackRightLegNode = new ModelNode("Table back right leg node", cubeTable);

        NameNode eggBaseName = new NameNode("Egg base");

        TransformNode eggBaseTranslate = new TransformNode("Translate egg base", Mat4Transform.translate(0, tableThickness/2 + baseHeight/2, 0));

        TransformNode eggBaseScale = new TransformNode("Scale egg base", Mat4Transform.scale(baseWidth, baseHeight, baseWidth));

        ModelNode eggBaseNode = new ModelNode("Egg base node", cubeBase);

        NameNode eggName = new NameNode("Egg");

        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundY(eggAngle),m);
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(eggAngleZ),m);
        m = Mat4.multiply(Mat4Transform.rotateAroundX(eggAngleZ),m);

        TransformNode eggRotate = new TransformNode("Rotate lamp 1 left eye", m);

        TransformNode eggTranslate = new TransformNode("Translate egg", Mat4Transform.translate(0, baseHeight/2 + eggSize.y/2 + eggHeight, 0));

        TransformNode eggScale = new TransformNode("Scale egg", Mat4Transform.scale(eggSize));

        ModelNode eggNode = new ModelNode("Egg node", sphere);

          eggStructure.addChild(translateToOrigin);
            translateToOrigin.addChild(rotateAll);
              rotateAll.addChild(tableBodyTranslate);
                tableBodyTranslate.addChild(tableBodyName);
                  tableBodyName.addChild(tableBodyScale);
                    tableBodyScale.addChild(tableBodyNode);
                  tableBodyName.addChild(tableBackLeftLegTranslate);
                    tableBackLeftLegTranslate.addChild(tableBackLeftLegName);
                      tableBackLeftLegName.addChild(tableBackLeftLegScale);
                        tableBackLeftLegScale.addChild(tableBackLeftLegNode);
                  tableBodyName.addChild(tableFrontLeftLegTranslate);
                    tableFrontLeftLegTranslate.addChild(tableFrontLeftLegName);
                      tableFrontLeftLegName.addChild(tableFrontLeftLegScale);
                        tableFrontLeftLegScale.addChild(tableFrontLeftLegNode);
                  tableBodyName.addChild(tableFrontRightLegTranslate);
                    tableFrontRightLegTranslate.addChild(tableFrontRightLegName);
                      tableFrontRightLegName.addChild(tableFrontRightLegScale);
                        tableFrontRightLegScale.addChild(tableFrontRightLegNode);
                  tableBodyName.addChild(tableBackRightLegTranslate);
                    tableBackRightLegTranslate.addChild(tableBackRightLegName);
                      tableBackRightLegName.addChild(tableBackRightLegScale);
                        tableBackRightLegScale.addChild(tableBackRightLegNode);
                  tableBodyName.addChild(eggBaseTranslate);
                      eggBaseTranslate.addChild(eggBaseName);
                        eggBaseName.addChild(eggBaseScale);
                          eggBaseScale.addChild(eggBaseNode);
                        eggBaseName.addChild(eggRotate);
                          eggRotate.addChild(eggTranslate);
                            eggTranslate.addChild(eggName);
                              eggName.addChild(eggScale);
                                eggScale.addChild(eggNode);

          eggStructure.update();

        return eggStructure;

    }


    
}
