import gmaths.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;


public class Room {

    private Camera camera;
    private Light[] light;
    private GL3 gl;
    private float windowSize = 25f;
    private float borderSize = 1f;

    public Room(GL3 gl, Camera camera, Light[] light) {
        this.camera = camera;
        this.light = light;
        this.gl = gl;
    }

    public Model[] makeRoom(Texture[] textures){
        
        Mesh m = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        Shader shader = new Shader(gl, "vertex_shaders/vs_floor.txt", "fragment_shaders/fs_floor.txt");
        Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);
        Model floor = new Model(gl, camera, light, shader, material, new Mat4(1), m, textures[0]);
        floor.setModelMatrix(getMforFloor());

        m = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        shader = new Shader(gl, "vertex_shaders/vs_tt_03.txt", "fragment_shaders/fs_tt_03.txt");
        material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);
        Model rightWall = new Model(gl, camera, light, shader, material, new Mat4(1), m, textures[1]);
        
        Model leftWall = new Model(gl, camera, light, shader, material, new Mat4(1), m, textures[1]);

        rightWall.setModelMatrix(getMforRightWall());
        leftWall.setModelMatrix(getMforLeftWall()); 

        m = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        shader = new Shader(gl, "vertex_shaders/vs_window.txt", "fragment_shaders/fs_window.txt");
        material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);
        Model window = new Model(gl, camera, light, shader, material, new Mat4(1), m, textures[2]);

        window.setModelMatrix(getMforBackWall());

        m = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        shader = new Shader(gl, "vertex_shaders/vs_tt_03.txt", "fragment_shaders/fs_tt_03.txt");
        material = new Material(new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);
        Mat4 modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(windowSize , borderSize * 0.5f, borderSize), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(0, borderSize * 0.5f, - windowSize * 0.5f + 0.01f), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        Model windowBottomFrame = new Model(gl, camera, light, shader, material, modelMatrix, m, textures[3]);

        m = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        shader = new Shader(gl, "vertex_shaders/vs_tt_03.txt", "fragment_shaders/fs_tt_03.txt");
        material = new Material(new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);  
        modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(windowSize, borderSize * 0.5f, borderSize), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(0, windowSize - borderSize * 0.5f, - windowSize * 0.5f + 0.01f), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        Model windowTopFrame = new Model(gl, camera, light, shader, material, modelMatrix, m, textures[3]);

        m = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        shader = new Shader(gl, "vertex_shaders/vs_tt_03.txt", "fragment_shaders/fs_tt_03.txt");
        material = new Material(new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);        
        modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(borderSize, windowSize - borderSize * 2, windowSize - borderSize * 2), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(-windowSize * 0.5f + borderSize * 0.5f, windowSize * 0.5f, - windowSize * 0.5f + 0.01f), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        Model windowLeftFrame = new Model(gl, camera, light, shader, material, modelMatrix, m, textures[3]);

        m = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
        shader = new Shader(gl, "vertex_shaders/vs_tt_03.txt", "fragment_shaders/fs_tt_03.txt");
        material = new Material(new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.8f, 0.8f, 0.8f), new Vec3(0.3f, 0.3f, 0.3f), 9.0f);
        modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(borderSize, windowSize - borderSize * 2, windowSize - borderSize * 2), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(windowSize * 0.5f - borderSize * 0.5f, windowSize * 0.5f, - windowSize * 0.5f + 0.01f), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        Model windowRightFrame = new Model(gl, camera, light, shader, material, modelMatrix, m, textures[3]);
        
        return new Model[] {floor, leftWall, rightWall, window, windowBottomFrame, windowTopFrame, windowLeftFrame, windowRightFrame};

    }

    // Model matrix for floor
    private Mat4 getMforFloor() {
        Mat4 modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(windowSize,1f, windowSize), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        return modelMatrix;
    }
  
    // Model matrix for back wall
    private Mat4 getMforBackWall() {
        Mat4 modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(windowSize,1f,windowSize), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(0, windowSize * 0.5f, -windowSize * 0.5f), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        return modelMatrix;
    }

    // Model matrix for left wall
    private Mat4 getMforLeftWall() {
        Mat4 modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(windowSize,1f,windowSize), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(-windowSize * 0.5f, windowSize * 0.5f,0), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        return modelMatrix;
    }

    // Model matrix for right wall
    private Mat4 getMforRightWall() {
        Mat4 modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(windowSize,1f,windowSize), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(windowSize * 0.5f,windowSize * 0.5f,0), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(20), modelMatrix);
        return modelMatrix;
    }
    
}
