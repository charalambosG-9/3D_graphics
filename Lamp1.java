import gmaths.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;


public class Lamp1 {

    private Camera camera;
    private Light[] light;
    private GL3 gl;
    
    float targetLowerPartYAngle;
    float targetLowerPartZAngle;
    float targetUpperPartAngle;
    float targetHeadAngle;
    float currentLowerPartYAngle;
    float currentLowerPartZAngle;
    float currentUpperPartAngle;
    float currentHeadAngle;
    float turnSpeed = 0.5f;

    public Lamp1(GL3 gl, Camera camera, Light[] light) {
        this.camera = camera;
        this.light = light;
        this.gl = gl;
    }

    public SGNode makeLamp1(Light spotLight, Boolean spotlight1On, int angle, Texture[] textures){

      // Angles for the lamp which change depending on it's state
      if (angle == 1){
        targetLowerPartYAngle = 0;
        targetLowerPartZAngle = 0;
        targetUpperPartAngle = 0;
        targetHeadAngle = 0;

      }else if (angle == 2){
        targetLowerPartYAngle = 50;
        targetLowerPartZAngle = -30;
        targetUpperPartAngle = 70;
        targetHeadAngle = 0;
      
      }else{
        targetLowerPartYAngle = -70;
        targetLowerPartZAngle = 40;
        targetUpperPartAngle = -30;
        targetHeadAngle = 0;
      }

      // Textures
      Texture textureAlienBody = textures[0];
      Texture textureAlienEyes = textures[1];

      // Models
      Mesh mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
      Shader shader = new Shader(gl, "vertex_shaders/vs_cube_04.txt", "fragment_shaders/fs_cube_04.txt");
      Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
      Mat4 modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
      Model cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureAlienBody);

      mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
      shader = new Shader(gl, "vertex_shaders/vs_sphere_04.txt", "fragment_shaders/fs_sphere_04.txt");    
      material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
      modelMatrix = Mat4.multiply(Mat4Transform.scale(3,3,3), Mat4Transform.translate(0,0.5f,0));
      modelMatrix = Mat4.multiply(Mat4Transform.translate(0,4,0), modelMatrix);
      Model sphere = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureAlienBody);

      Model sphereEye = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureAlienEyes);

      // Lamp dimensions
      Vec3 lamp1Base = new Vec3(2f,1f,2f);
      Vec3 lamp1Lower = new Vec3(0.7f,3f,0.7f);
      Vec3 lamp1Connection = new Vec3(0.7f,0.7f,0.7f);
      Vec3 lamp1Tail = new Vec3(0.3f,2f,0.3f);
      Vec3 lamp1TailEnd = new Vec3(0.5f,0.5f,0.5f);
      Vec3 lamp1Upper = new Vec3(0.7f,3f,0.7f);
      Vec3 lamp1Head = new Vec3(2f,1f,1f);
      Vec3 lamp1Horn = new Vec3(0.5f,1f,0.5f);
      Vec3 lamp1Eye = new Vec3(1.5f,0.75f,0.75f);
      Vec3 lamp1Light = new Vec3(0.5f,0.5f,0.5f);

      // Scene graph nodes
      SGNode lamp1 = new SGNode("Lamp 1");
      TransformNode translateToOrigin = new TransformNode("translate", Mat4Transform.translate(7f, 0f,-3f));
      Mat4 m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundY(20),m);
      TransformNode rotateAll  = new TransformNode("Initial rotation", m);
        
      NameNode lamp1BaseName = new NameNode("Lamp 1 base");
      m = Mat4Transform.scale(lamp1Base);
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode lamp1BaseTransform  = new TransformNode("Transform lamp 1 base", m);
      ModelNode lamp1BaseNode= new ModelNode("Lamp 1 base", cube);
        
      NameNode lamp1BottomPartName = new NameNode("Lamp 1 bottom part");
      
      m = new Mat4(1);

      if(targetLowerPartZAngle > currentLowerPartZAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(currentLowerPartZAngle),m);
        currentLowerPartZAngle += turnSpeed;
      } else if(targetLowerPartZAngle < currentLowerPartZAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(currentLowerPartZAngle),m);
        currentLowerPartZAngle -= turnSpeed;
      } else if(targetLowerPartZAngle == currentLowerPartZAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(currentLowerPartZAngle),m);
      }

      if(targetLowerPartYAngle > currentLowerPartYAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundY(currentLowerPartYAngle),m);
        currentLowerPartYAngle += turnSpeed;
      } else if(targetLowerPartYAngle < currentLowerPartYAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundY(currentLowerPartYAngle),m);
        currentLowerPartYAngle -= turnSpeed;
      } else if(targetLowerPartYAngle == currentLowerPartYAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundY(currentLowerPartYAngle),m);
      }
      
      TransformNode lamp1BottomPartRotate  = new TransformNode("Rotate lamp 1 bottom part", m);
      
      TransformNode lamp1BottomPartTranslate  = new TransformNode("Translate lamp 1 bottom part", Mat4Transform.translate(0, lamp1Base.y + lamp1Lower.y/2,0));
   
      TransformNode lamp1BottomPartScale  = new TransformNode("Scale lamp 1 bottom part", Mat4Transform.scale(lamp1Lower));
      
      ModelNode lamp1BottomPartNode= new ModelNode("Lamp 1 bottom part", sphere);
        
      NameNode lamp1ConnectionName = new NameNode("Lamp 1 connection part");
      
      m = new Mat4(1);

      TransformNode lamp1ConnectionTranslate = new TransformNode("Translate lamp 1 connection part", Mat4Transform.translate(0, lamp1Lower.y/2 + lamp1Connection.y/2,0));

      TransformNode lamp1ConnectionScale = new TransformNode("Scale lamp 1 connection part", Mat4Transform.scale(lamp1Connection));

      ModelNode lamp1ConnectionNode = new ModelNode("Lamp 1 connection part", sphere);

      NameNode lamp1TailName = new NameNode("Lamp 1 tail");
      
      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundZ(-70),m);
      
      TransformNode lamp1TailRotate = new TransformNode("Rotate lamp 1 tail", m);

      TransformNode lamp1TailTranslate = new TransformNode("Translate lamp 1 tail", Mat4Transform.translate(0, lamp1Connection.y/2 + lamp1Tail.y/2,0));

      TransformNode lamp1TailScale = new TransformNode("Scale lamp 1 tail", Mat4Transform.scale(lamp1Tail));

      ModelNode lamp1TailNode = new ModelNode("Lamp 1 tail", sphere);

      NameNode lamp1TailEndName = new NameNode("Lamp 1 tail left split");
     
      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundZ(0),m);
      
      TransformNode lamp1TailEndRotate = new TransformNode("Rotate lamp 1 tail left split", m);

      TransformNode lamp1TailEndTranslate = new TransformNode("Translate lamp 1 tail left split", Mat4Transform.translate(0, lamp1Tail.y/2 + lamp1TailEnd.y/2,0));

      TransformNode lamp1TailEndScale = new TransformNode("Scale lamp 1 tail left split", Mat4Transform.scale(lamp1TailEnd));

      ModelNode lamp1TailEndNode= new ModelNode("Lamp 1 tail left split", sphere);

      NameNode lamp1UpperPartName = new NameNode("Lamp 1 upper part");
      m = new Mat4(1);

      if(targetUpperPartAngle > currentUpperPartAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(currentUpperPartAngle),m);
        currentUpperPartAngle += turnSpeed;
      } else if(targetUpperPartAngle < currentUpperPartAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(currentUpperPartAngle),m);
        currentUpperPartAngle -= turnSpeed;
      } else if(targetUpperPartAngle == currentUpperPartAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(currentUpperPartAngle),m);
      }

      TransformNode lamp1UpperPartRotate = new TransformNode("Rotate lamp 1 upper part", m);

      TransformNode lamp1UpperPartTranslate = new TransformNode("Translate lamp 1 upper part", Mat4Transform.translate(0, lamp1Connection.y/2 + lamp1Upper.y/2,0));

      TransformNode lamp1UpperPartScale = new TransformNode("Scale lamp 1 upper part", Mat4Transform.scale(lamp1Upper));

      ModelNode lamp1UpperPartNode= new ModelNode("Lamp 1 upper part", sphere);
        
      NameNode lamp1HeadName = new NameNode("Lamp 1 head");
      
      m = new Mat4(1);

      if(targetHeadAngle > currentHeadAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(currentHeadAngle),m);
        currentHeadAngle += turnSpeed;
      } else if(targetHeadAngle < currentHeadAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(currentHeadAngle),m);
        currentHeadAngle -= turnSpeed;
      } else if(targetHeadAngle == currentHeadAngle){
        m = Mat4.multiply(Mat4Transform.rotateAroundZ(currentHeadAngle),m);
      }

      TransformNode lamp1HeadRotate = new TransformNode("Rotate lamp 1 head", m);

      TransformNode lamp1HeadTranslate = new TransformNode("Translate lamp 1 head", Mat4Transform.translate(0, lamp1Upper.y/2 + lamp1Head.y/2,0));

      TransformNode lamp1HeadScale = new TransformNode("Scale lamp 1 head", Mat4Transform.scale(lamp1Head));
      
      ModelNode lamp1HeadNode= new ModelNode("Lamp 1 head", cube);
      
      NameNode lamp1RightHornName = new NameNode("Lamp 1 right horn");
      
      m = new Mat4(1);

      TransformNode lamp1RightHornRotate = new TransformNode("Rotate lamp 1 right horn", m);

      TransformNode lamp1RightHornTranslate = new TransformNode("Translate lamp 1 right horn", Mat4Transform.translate(lamp1Head.x/3, lamp1Head.y/2 + lamp1Horn.y/2, lamp1Head.z/2));

      TransformNode lamp1RightHornScale = new TransformNode("Scale lamp 1 right horn", Mat4Transform.scale(lamp1Horn));

      ModelNode lamp1RightHornNode= new ModelNode("Lamp 1 left horn", sphere);

      NameNode lamp1LeftHornName = new NameNode("Lamp 1 right horn");
      
      m = new Mat4(1);

      TransformNode lamp1LeftHornRotate = new TransformNode("Rotate lamp 1 right horn", m);

      TransformNode lamp1LeftHornTranslate = new TransformNode("Translate lamp 1 right horn", Mat4Transform.translate(lamp1Head.x/3, lamp1Head.y/2 + lamp1Horn.y/2, -lamp1Head.z/2));

      TransformNode lamp1LeftHornScale = new TransformNode("Scale lamp 1 right horn", Mat4Transform.scale(lamp1Horn));

      ModelNode lamp1LeftHornNode= new ModelNode("Lamp 1 left horn", sphere);

      NameNode lamp1LeftEyeName = new NameNode("Lamp 1 left eye");

      m = new Mat4(1);

      TransformNode lamp1LeftEyeRotate = new TransformNode("Rotate lamp 1 left eye", m);

      TransformNode lamp1LeftEyeTranslate = new TransformNode("Translate lamp 1 left eye", Mat4Transform.translate(-lamp1Head.x/2 + lamp1Eye.x/2, 0, lamp1Head.z/2 + lamp1Eye.z/2));

      TransformNode lamp1LeftEyeScale = new TransformNode("Scale lamp 1 left eye", Mat4Transform.scale(lamp1Eye));

      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundY(90),m);
      
      TransformNode lamp1LeftEyeSecondRotate = new TransformNode("Rotate lamp 1 left eye to hide texture line", m);

      ModelNode lamp1LeftEyeNode= new ModelNode("Lamp 1 left eye", sphereEye);

      NameNode lamp1RightEyeName = new NameNode("Lamp 1 left eye");
      
      m = new Mat4(1);

      TransformNode lamp1RightEyeRotate = new TransformNode("Rotate lamp 1 left eye", m);
  
      TransformNode lamp1RightEyeTranslate = new TransformNode("Translate lamp 1 left eye", Mat4Transform.translate(-lamp1Head.x/2 + lamp1Eye.x/2, 0, -lamp1Head.z/2 - lamp1Eye.z/2));

      TransformNode lamp1RightEyeScale = new TransformNode("Scale lamp 1 left eye", Mat4Transform.scale(lamp1Eye));

      ModelNode lamp1RightEyeNode= new ModelNode("Lamp 1 left eye", sphereEye);

      NameNode lamp1LightName = new NameNode("Lamp 1 light");

      TransformNode lamp1LightTranslate = new TransformNode("Translate lamp 1 light", Mat4Transform.translate(- lamp1Head.x/2 - lamp1Light.x/2, 0,0));

      TransformNode lamp1LightScale = new TransformNode("Scale lamp 1 light", Mat4Transform.scale(lamp1Light));

      // Check if light is on or off
      getLightColor(spotlight1On, spotLight);

      LightNode lightNode = new LightNode("lamp", spotLight, lamp1HeadNode, 1);

      // Scene graph      
      lamp1.addChild(translateToOrigin);
        translateToOrigin.addChild(rotateAll);
          rotateAll.addChild(lamp1BaseName);
            lamp1BaseName.addChild(lamp1BaseTransform);
              lamp1BaseTransform.addChild(lamp1BaseNode);
            lamp1BaseName.addChild(lamp1BottomPartRotate);
              lamp1BottomPartRotate.addChild(lamp1BottomPartTranslate);
                lamp1BottomPartTranslate.addChild(lamp1BottomPartName);
                  lamp1BottomPartName.addChild(lamp1BottomPartScale);
                    lamp1BottomPartScale.addChild(lamp1BottomPartNode);
                  lamp1BottomPartName.addChild(lamp1ConnectionTranslate); 
                    lamp1ConnectionTranslate.addChild(lamp1ConnectionName);
                      lamp1ConnectionName.addChild(lamp1ConnectionScale);
                        lamp1ConnectionScale.addChild(lamp1ConnectionNode);
                      lamp1ConnectionName.addChild(lamp1TailRotate);
                        lamp1TailRotate.addChild(lamp1TailTranslate);
                          lamp1TailTranslate.addChild(lamp1TailName);
                            lamp1TailName.addChild(lamp1TailScale);
                              lamp1TailScale.addChild(lamp1TailNode);
                            lamp1TailName.addChild(lamp1TailEndRotate);
                              lamp1TailEndRotate.addChild(lamp1TailEndTranslate);
                                lamp1TailEndTranslate.addChild(lamp1TailEndName);
                                  lamp1TailEndName.addChild(lamp1TailEndScale);
                                    lamp1TailEndScale.addChild(lamp1TailEndNode);
                      lamp1ConnectionName.addChild(lamp1UpperPartRotate);
                        lamp1UpperPartRotate.addChild(lamp1UpperPartTranslate);
                          lamp1UpperPartTranslate.addChild(lamp1UpperPartName);
                            lamp1UpperPartName.addChild(lamp1UpperPartScale);
                              lamp1UpperPartScale.addChild(lamp1UpperPartNode);
                            lamp1UpperPartName.addChild(lamp1HeadRotate);
                              lamp1HeadRotate.addChild(lamp1HeadTranslate);
                                lamp1HeadTranslate.addChild(lamp1HeadName);
                                  lamp1HeadName.addChild(lamp1HeadScale);
                                    lamp1HeadScale.addChild(lamp1HeadNode);
                                  lamp1HeadName.addChild(lamp1RightHornRotate);
                                    lamp1RightHornRotate.addChild(lamp1RightHornTranslate);
                                      lamp1RightHornTranslate.addChild(lamp1RightHornName);
                                        lamp1RightHornName.addChild(lamp1RightHornScale);
                                          lamp1RightHornScale.addChild(lamp1RightHornNode);
                                  lamp1HeadName.addChild(lamp1LeftHornRotate);
                                    lamp1LeftHornRotate.addChild(lamp1LeftHornTranslate);
                                      lamp1LeftHornTranslate.addChild(lamp1LeftHornName);
                                        lamp1LeftHornName.addChild(lamp1LeftHornScale);
                                          lamp1LeftHornScale.addChild(lamp1LeftHornNode);
                                  lamp1HeadName.addChild(lamp1RightEyeRotate);
                                    lamp1RightEyeRotate.addChild(lamp1RightEyeTranslate);
                                      lamp1RightEyeTranslate.addChild(lamp1RightEyeName);
                                        lamp1RightEyeName.addChild(lamp1RightEyeScale);
                                          lamp1RightEyeScale.addChild(lamp1RightEyeNode);
                                  lamp1HeadName.addChild(lamp1LeftEyeRotate);
                                    lamp1LeftEyeRotate.addChild(lamp1LeftEyeTranslate);
                                      lamp1LeftEyeTranslate.addChild(lamp1LeftEyeName);
                                        lamp1LeftEyeName.addChild(lamp1LeftEyeScale);
                                          lamp1LeftEyeScale.addChild(lamp1LeftEyeSecondRotate);
                                            lamp1LeftEyeSecondRotate.addChild(lamp1LeftEyeNode);
                                  lamp1HeadName.addChild(lamp1LightTranslate);
                                    lamp1LightTranslate.addChild(lamp1LightName);
                                      lamp1LightName.addChild(lamp1LightScale);
                                        lamp1LightScale.addChild(lightNode);
                      
        lamp1.update();

      return lamp1;
    }

    // Turn spotlight on or off
    public void getLightColor(boolean spotlight1On, Light spotLight){
      Vec3 lightColour;
      Material mat = spotLight.getMaterial();
      if(spotlight1On) {
        lightColour = new Vec3(1f, 1f, 1f);
        mat.setDiffuse(Vec3.multiply(lightColour,0.4f));
        mat.setAmbient(Vec3.multiply(mat.getDiffuse(),0.2f));
      } else {
        lightColour = new Vec3(0f, 0f, 0f);
        mat.setDiffuse(Vec3.multiply(lightColour,0.4f));
        mat.setAmbient(Vec3.multiply(mat.getDiffuse(),0.1f));
      }
      mat.setSpecular(Vec3.multiply(mat.getDiffuse(),0.2f));
      spotLight.setMaterial(mat);
      spotLight.setCubeColour(lightColour);
    }
    
}
