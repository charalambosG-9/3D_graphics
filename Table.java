import gmaths.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;

public class Table {

    private Camera camera;
    private Light[] light;
    private GL3 gl;
    SGNode table;

    public Table(GL3 gl, Camera camera, Light[] light) {
        this.camera = camera;
        this.light = light;
        this.gl = gl;
    }

    public SGNode makeTable(float tableSize, float tableHeight, float tableThickness, float legHeight, float legWidth, Texture textureTable) {

        Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
        Shader shader = new Shader(gl, "vertex_shaders/vs_cube_04.txt", "fragment_shaders/fs_cube_04.txt");
        Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
        Mat4 modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
        Model cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureTable);

        //maybe change scale notes for legs with variables

        table = new NameNode("table");
        TransformNode translateToOrigin = new TransformNode("translate", Mat4Transform.translate(0,0,0));
        Mat4 m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.rotateAroundY(20),m);
        TransformNode rotateAll  = new TransformNode("Initial rotation", m);
        
        NameNode tableTopName = new NameNode("Table top");
        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.scale(tableSize, tableThickness, tableSize), m);
        m = Mat4.multiply(Mat4Transform.translate(0,tableHeight,0), m);
        TransformNode tableTopTransform  = new TransformNode("Transform table top", m);
        ModelNode tableTopNode = new ModelNode("Table top", cube);
        
        NameNode frontRightLegName = new NameNode("Front right leg");
        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.scale(legWidth, 0.5f + legHeight, legWidth), m);
        m = Mat4.multiply(Mat4Transform.translate(0.5f - legWidth/2 , 0.5f/2 -(tableHeight + tableThickness)/2, 0.5f - legWidth/2), m);
        TransformNode frontRightLegTransform  = new TransformNode("Transform front right leg", m);
        ModelNode frontRightLegNode = new ModelNode("Front right leg", cube);
        
        NameNode backRightLegName = new NameNode("Back right leg");
        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.scale(legWidth, legHeight, legWidth), m);
        m = Mat4.multiply(Mat4Transform.translate(0.5f - legWidth/2 , 0.5f -(tableHeight + tableThickness)/2, -(0.5f - legWidth/2)), m);
        TransformNode backRightLegTransform  = new TransformNode("Transform back right leg", m);
        ModelNode backRightLegNode = new ModelNode("Back right leg", cube);
        
        NameNode backLeftLegName = new NameNode("Back left leg");
        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.scale(legWidth, 0.5f + legHeight, legWidth), m);
        m = Mat4.multiply(Mat4Transform.translate(-(0.5f - legWidth/2), 0.5f -(tableHeight + tableThickness)/2, -(0.5f - legWidth/2)), m);
        TransformNode backLeftLegTransform  = new TransformNode("Transform back left leg", m);
        ModelNode backLeftLegNode = new ModelNode("Back left leg", cube);
        
        NameNode frontLeftLegName = new NameNode("Front left leg");
        m = new Mat4(1);
        m = Mat4.multiply(Mat4Transform.scale(legWidth, 0.5f + legHeight, legWidth), m);
        m = Mat4.multiply(Mat4Transform.translate(-(0.5f - legWidth/2), 0.5f -(tableHeight + tableThickness)/2, 0.5f - legWidth/2), m);
        TransformNode frontLeftLegTransform  = new TransformNode("Transform front left leg", m);
        ModelNode frontLeftLegNode = new ModelNode("Front left leg", cube);
        
        table.addChild(translateToOrigin);
          translateToOrigin.addChild(rotateAll);
            rotateAll.addChild(tableTopName);
              tableTopName.addChild(tableTopTransform);
                tableTopTransform.addChild(tableTopNode);
                  tableTopNode.addChild(frontRightLegName);
                    frontRightLegName.addChild(frontRightLegTransform);
                      frontRightLegTransform.addChild(frontRightLegNode);
                  tableTopNode.addChild(backRightLegName);
                    backRightLegName.addChild(backRightLegTransform);
                      backRightLegTransform.addChild(backRightLegNode);
                  tableTopNode.addChild(backLeftLegName);
                    backLeftLegName.addChild(backLeftLegTransform);
                      backLeftLegTransform.addChild(backLeftLegNode);
                  tableTopNode.addChild(frontLeftLegName);
                    frontLeftLegName.addChild(frontLeftLegTransform);
                      frontLeftLegTransform.addChild(frontLeftLegNode);

          table.update();
        
        
          return table;

    }
    
}
