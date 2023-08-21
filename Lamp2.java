import gmaths.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;


public class Lamp2 {

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

    public Lamp2(GL3 gl, Camera camera, Light[] light) {
        this.camera = camera;
        this.light = light;
        this.gl = gl;
    }

    public SGNode makeLamp2(Light spotLight, Boolean spotlight2On, int angle, Texture[] textures){

      // Angles for the lamp which change depending on it's state
      if (angle == 1){
        targetLowerPartYAngle = 0;
        targetLowerPartZAngle = -10;
        targetUpperPartAngle = 20;
        targetHeadAngle = 0;

      }else if (angle == 2){
        targetLowerPartYAngle = 60;
        targetLowerPartZAngle = -50;
        targetUpperPartAngle = 50;
        targetHeadAngle = 0;
      
      }else{
        targetLowerPartYAngle = -50;
        targetLowerPartZAngle = 30;
        targetUpperPartAngle = 10;
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
      Vec3 lamp2Base = new Vec3(2f,1f,2f);
      Vec3 lamp2Lower = new Vec3(0.7f,2.5f,0.7f);
      Vec3 lamp2Connection = new Vec3(0.7f,0.7f,0.7f);
      Vec3 lamp2Tail = new Vec3(0.3f,2f,0.3f);
      Vec3 lamp2TailEnd = new Vec3(0.5f,0.5f,0.5f);
      Vec3 lamp2Upper = new Vec3(0.7f,2.5f,0.7f);
      Vec3 lamp2Head = new Vec3(2f,1f,1f);
      Vec3 lamp2Horn = new Vec3(0.5f,1f,0.5f);
      Vec3 lamp2Eye = new Vec3(1.5f,0.75f,0.75f);
      Vec3 lamp2Light = new Vec3(0.5f,0.5f,0.5f);
      Vec3 lamp2Antenna = new Vec3(0.1f,1f,0.1f);

      // Scene graph nodes
      SGNode lamp2 = new SGNode("Lamp 2");
      TransformNode translateToOrigin = new TransformNode("translate", Mat4Transform.translate(-7f, 0f,2f));
      Mat4 m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundY(20),m);
      m = Mat4.multiply(Mat4Transform.rotateAroundY(180),m);
      TransformNode rotateAll  = new TransformNode("Initial rotation", m);
        
      NameNode lamp2BaseName = new NameNode("Lamp 2 base");
      m = Mat4Transform.scale(lamp2Base);
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode lamp2BaseTransform  = new TransformNode("Transform lamp 2 base", m);
      ModelNode lamp2BaseNode= new ModelNode("Lamp 2 base", cube);
        
      NameNode lamp2BottomPartName = new NameNode("Lamp 2 bottom part");
      
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
      
      TransformNode lamp2BottomPartRotate  = new TransformNode("Rotate lamp 2 bottom part", m);
      
      TransformNode lamp2BottomPartTranslate  = new TransformNode("Translate lamp 2 bottom part", Mat4Transform.translate(0, lamp2Base.y + lamp2Lower.y/2,0));
   
      TransformNode lamp2BottomPartScale  = new TransformNode("Scale lamp 2 bottom part", Mat4Transform.scale(lamp2Lower));
      
      ModelNode lamp2BottomPartNode= new ModelNode("Lamp 2 bottom part", sphere);
        
      NameNode lamp2ConnectionName = new NameNode("Lamp 2 connection part");

      TransformNode lamp2ConnectionTranslate = new TransformNode("Translate lamp 2 connection part", Mat4Transform.translate(0, lamp2Lower.y/2 + lamp2Connection.y/2,0));

      TransformNode lamp2ConnectionScale = new TransformNode("Scale lamp 2 connection part", Mat4Transform.scale(lamp2Connection));

      ModelNode lamp2ConnectionNode = new ModelNode("Lamp 2 connection part", sphere);

      NameNode lamp2Tail1Name = new NameNode("Lamp 2 tail");
      
      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundZ(-70),m);
      m = Mat4.multiply(Mat4Transform.rotateAroundY(30),m);
      
      TransformNode lamp2Tail1Rotate = new TransformNode("Rotate lamp 2 tail", m);

      TransformNode lamp2Tail1Translate = new TransformNode("Translate lamp 2 tail", Mat4Transform.translate(0, lamp2Connection.y/2 + lamp2Tail.y/2,0));

      TransformNode lamp2Tail1Scale = new TransformNode("Scale lamp 2 tail", Mat4Transform.scale(lamp2Tail));

      ModelNode lamp2Tail1Node = new ModelNode("Lamp 2 tail", sphere);

      NameNode lamp2Tail2Name = new NameNode("Lamp 2 tail");
      
      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundZ(-70),m);
      m = Mat4.multiply(Mat4Transform.rotateAroundY(-30),m);
      
      TransformNode lamp2Tail2Rotate = new TransformNode("Rotate lamp 2 tail", m);

      TransformNode lamp2Tail2Translate = new TransformNode("Translate lamp 2 tail", Mat4Transform.translate(0, lamp2Connection.y/2 + lamp2Tail.y/2,0));

      TransformNode lamp2Tail2Scale = new TransformNode("Scale lamp 2 tail", Mat4Transform.scale(lamp2Tail));

      ModelNode lamp2Tail2Node = new ModelNode("Lamp 2 tail", sphere);

      NameNode lamp2Tail1EndName = new NameNode("Lamp 2 tail left split");
      
      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundZ(0),m);
      
      TransformNode lamp2Tail1EndRotate = new TransformNode("Rotate lamp 2 tail left split", m);

      TransformNode lamp2Tail1EndTranslate = new TransformNode("Translate lamp 2 tail left split", Mat4Transform.translate(0, lamp2Tail.y/2 + lamp2TailEnd.y/2,0));

      TransformNode lamp2Tail1EndScale = new TransformNode("Scale lamp 2 tail left split", Mat4Transform.scale(lamp2TailEnd));

      ModelNode lamp2Tail1EndNode= new ModelNode("Lamp 2 tail left split", sphere);

      NameNode lamp2Tail2EndName = new NameNode("Lamp 2 tail left split");
      
      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundZ(0),m);
      
      TransformNode lamp2Tail2EndRotate = new TransformNode("Rotate lamp 2 tail left split", m);

      TransformNode lamp2Tail2EndTranslate = new TransformNode("Translate lamp 2 tail left split", Mat4Transform.translate(0, lamp2Tail.y/2 + lamp2TailEnd.y/2,0));

      TransformNode lamp2Tail2EndScale = new TransformNode("Scale lamp 2 tail left split", Mat4Transform.scale(lamp2TailEnd));

      ModelNode lamp2Tail2EndNode= new ModelNode("Lamp 2 tail left split", sphere);

      NameNode lamp2UpperPartName = new NameNode("Lamp 2 upper part");
      
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

      TransformNode lamp2UpperPartRotate = new TransformNode("Rotate lamp 2 upper part", m);

      TransformNode lamp2UpperPartTranslate = new TransformNode("Translate lamp 2 upper part", Mat4Transform.translate(0, lamp2Connection.y/2 + lamp2Upper.y/2,0));

      TransformNode lamp2UpperPartScale = new TransformNode("Scale lamp 2 upper part", Mat4Transform.scale(lamp2Upper));

      ModelNode lamp2UpperPartNode= new ModelNode("Lamp 2 upper part", sphere);
        
      NameNode lamp2HeadName = new NameNode("Lamp 2 head");
      
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

      TransformNode lamp2HeadRotate = new TransformNode("Rotate lamp 2 head", m);

      TransformNode lamp2HeadTranslate = new TransformNode("Translate lamp 2 head", Mat4Transform.translate(0, lamp2Upper.y/2 + lamp2Head.y/2,0));

      TransformNode lamp2HeadScale = new TransformNode("Scale lamp 2 head", Mat4Transform.scale(lamp2Head));
      
      ModelNode lamp2HeadNode= new ModelNode("Lamp 2 head", cube);
      
      NameNode lamp2RightHornName = new NameNode("Lamp 2 right horn");
      
      m = new Mat4(1);

      TransformNode lamp2RightHornRotate = new TransformNode("Rotate lamp 2 right horn", m);

      TransformNode lamp2RightHornTranslate = new TransformNode("Translate lamp 2 right horn", Mat4Transform.translate(lamp2Head.x/3, lamp2Head.y/2 + lamp2Horn.y/2, lamp2Head.z/2));

      TransformNode lamp2RightHornScale = new TransformNode("Scale lamp 2 right horn", Mat4Transform.scale(lamp2Horn));

      ModelNode lamp2RightHornNode= new ModelNode("Lamp 2 left horn", sphere);

      NameNode lamp2LeftHornName = new NameNode("Lamp 2 left horn");
      
      m = new Mat4(1);

      TransformNode lamp2LeftHornRotate = new TransformNode("Rotate lamp 2 right horn", m);

      TransformNode lamp2LeftHornTranslate = new TransformNode("Translate lamp 2 right horn", Mat4Transform.translate(lamp2Head.x/3, lamp2Head.y/2 + lamp2Horn.y/2, -lamp2Head.z/2));

      TransformNode lamp2LeftHornScale = new TransformNode("Scale lamp 2 right horn", Mat4Transform.scale(lamp2Horn));

      ModelNode lamp2LeftHornNode= new ModelNode("Lamp 2 left horn", sphere);

      NameNode lamp2LeftAntennaName = new NameNode("Lamp 2 left antenna");
      
      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundX(20),m);
      
      TransformNode lamp2LeftAntennaRotate = new TransformNode("Rotate lamp 2 left antenna", m);

      TransformNode lamp2LeftAntennaTranslate = new TransformNode("Translate lamp 2 left antenna", Mat4Transform.translate(-lamp2Head.x/2, lamp2Head.y/2 + lamp2Antenna.y/2, 0));

      TransformNode lamp2LeftAntennaScale = new TransformNode("Scale lamp 2 left antenna", Mat4Transform.scale(lamp2Antenna));

      ModelNode lamp2LeftAntennaNode = new ModelNode("Lamp 2 left antenna", sphere);

      NameNode lamp2RightAntennaName = new NameNode("Lamp 2 right antenna");
      
      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundX(-20),m);
      
      TransformNode lamp2RightAntennaRotate = new TransformNode("Rotate lamp 2 right antenna", m);

      TransformNode lamp2RightAntennaTranslate = new TransformNode("Translate lamp 2 right antenna", Mat4Transform.translate(-lamp2Head.x/2, lamp2Head.y/2 + lamp2Antenna.y/2, 0));

      TransformNode lamp2RightAntennaScale = new TransformNode("Scale lamp 2 right antenna", Mat4Transform.scale(lamp2Antenna));

      ModelNode lamp2RightAntennaNode = new ModelNode("Lamp 2 left antenna", sphere);

      NameNode lamp2LeftEyeName = new NameNode("Lamp 2 left eye");
      
      m = new Mat4(1);
      //m = Mat4.multiply(Mat4Transform.rotateAroundY(90),m);

      TransformNode lamp2LeftEyeRotate = new TransformNode("Rotate lamp 2 left eye", m);

      TransformNode lamp2LeftEyeTranslate = new TransformNode("Translate lamp 2 left eye", Mat4Transform.translate(-lamp2Head.x/2 + lamp2Eye.x/2, 0, lamp2Head.z/2 + lamp2Eye.z/2));
      // Mat4Transform.translate(-lamp2Head.x/2 + lamp2Eye.x/2, 0, -lamp2Head.z/2 - lamp2Eye.z/2)
      TransformNode lamp2LeftEyeScale = new TransformNode("Scale lamp 2 left eye", Mat4Transform.scale(lamp2Eye));

      m = new Mat4(1);
      m = Mat4.multiply(Mat4Transform.rotateAroundY(90),m);
      
      TransformNode lamp2LeftEyeSecondRotate = new TransformNode("Rotate lamp 2 left eye to hide texture line", m);

      ModelNode lamp2LeftEyeNode= new ModelNode("Lamp 2 left eye", sphereEye);

      NameNode lamp2RightEyeName = new NameNode("Lamp 2 left eye");
      
      m = new Mat4(1);

      TransformNode lamp2RightEyeRotate = new TransformNode("Rotate lamp 2 left eye", m);

      TransformNode lamp2RightEyeTranslate = new TransformNode("Translate lamp 2 left eye", Mat4Transform.translate(-lamp2Head.x/2 + lamp2Eye.x/2, 0, -lamp2Head.z/2 - lamp2Eye.z/2));

      TransformNode lamp2RightEyeScale = new TransformNode("Scale lamp 2 left eye", Mat4Transform.scale(lamp2Eye));

      ModelNode lamp2RightEyeNode= new ModelNode("Lamp 2 left eye", sphereEye);

      NameNode lamp2lightName = new NameNode("Lamp 2 light");

      TransformNode lamp2lightTranslate = new TransformNode("Translate lamp 2 light", Mat4Transform.translate(- lamp2Head.x/2 - lamp2Light.x/2, 0,0));

      TransformNode lamp2lightScale = new TransformNode("Scale lamp 2 light", Mat4Transform.scale(lamp2Light));

      // Check if light is on or off
      getLightColor(spotlight2On, spotLight);

      LightNode lightNode = new LightNode("lamp", spotLight, lamp2HeadNode,2);

      // Scene graph      
      lamp2.addChild(translateToOrigin);
        translateToOrigin.addChild(rotateAll);
          rotateAll.addChild(lamp2BaseName);
            lamp2BaseName.addChild(lamp2BaseTransform);
              lamp2BaseTransform.addChild(lamp2BaseNode);
            lamp2BaseName.addChild(lamp2BottomPartRotate);
              lamp2BottomPartRotate.addChild(lamp2BottomPartTranslate);
                lamp2BottomPartTranslate.addChild(lamp2BottomPartName);
                  lamp2BottomPartName.addChild(lamp2BottomPartScale);
                    lamp2BottomPartScale.addChild(lamp2BottomPartNode);
                  lamp2BottomPartName.addChild(lamp2ConnectionTranslate); 
                    lamp2ConnectionTranslate.addChild(lamp2ConnectionName);
                      lamp2ConnectionName.addChild(lamp2ConnectionScale);
                        lamp2ConnectionScale.addChild(lamp2ConnectionNode);
                      lamp2ConnectionName.addChild(lamp2Tail1Rotate);
                        lamp2Tail1Rotate.addChild(lamp2Tail1Translate);
                          lamp2Tail1Translate.addChild(lamp2Tail1Name);
                            lamp2Tail1Name.addChild(lamp2Tail1Scale);
                              lamp2Tail1Scale.addChild(lamp2Tail1Node);
                            lamp2Tail1Name.addChild(lamp2Tail1EndRotate);
                              lamp2Tail1EndRotate.addChild(lamp2Tail1EndTranslate);
                                lamp2Tail1EndTranslate.addChild(lamp2Tail1EndName);
                                  lamp2Tail1EndName.addChild(lamp2Tail1EndScale);
                                    lamp2Tail1EndScale.addChild(lamp2Tail1EndNode);
                      lamp2ConnectionName.addChild(lamp2Tail2Rotate);
                        lamp2Tail2Rotate.addChild(lamp2Tail2Translate);
                            lamp2Tail2Translate.addChild(lamp2Tail2Name);
                              lamp2Tail2Name.addChild(lamp2Tail2Scale);
                                lamp2Tail2Scale.addChild(lamp2Tail2Node);
                              lamp2Tail2Name.addChild(lamp2Tail2EndRotate);
                                lamp2Tail2EndRotate.addChild(lamp2Tail2EndTranslate);
                                  lamp2Tail2EndTranslate.addChild(lamp2Tail2EndName);
                                    lamp2Tail2EndName.addChild(lamp2Tail2EndScale);
                                      lamp2Tail2EndScale.addChild(lamp2Tail2EndNode);
                      lamp2ConnectionName.addChild(lamp2UpperPartRotate);
                        lamp2UpperPartRotate.addChild(lamp2UpperPartTranslate);
                          lamp2UpperPartTranslate.addChild(lamp2UpperPartName);
                            lamp2UpperPartName.addChild(lamp2UpperPartScale);
                              lamp2UpperPartScale.addChild(lamp2UpperPartNode);
                            lamp2UpperPartName.addChild(lamp2HeadRotate);
                              lamp2HeadRotate.addChild(lamp2HeadTranslate);
                                lamp2HeadTranslate.addChild(lamp2HeadName);
                                  lamp2HeadName.addChild(lamp2HeadScale);
                                    lamp2HeadScale.addChild(lamp2HeadNode);
                                  lamp2HeadName.addChild(lamp2RightHornRotate);
                                    lamp2RightHornRotate.addChild(lamp2RightHornTranslate);
                                      lamp2RightHornTranslate.addChild(lamp2RightHornName);
                                        lamp2RightHornName.addChild(lamp2RightHornScale);
                                          lamp2RightHornScale.addChild(lamp2RightHornNode);
                                  lamp2HeadName.addChild(lamp2LeftHornRotate);
                                    lamp2LeftHornRotate.addChild(lamp2LeftHornTranslate);
                                      lamp2LeftHornTranslate.addChild(lamp2LeftHornName);
                                        lamp2LeftHornName.addChild(lamp2LeftHornScale);
                                          lamp2LeftHornScale.addChild(lamp2LeftHornNode);
                                  lamp2HeadName.addChild(lamp2LeftAntennaRotate);
                                    lamp2LeftAntennaRotate.addChild(lamp2LeftAntennaTranslate);
                                      lamp2LeftAntennaTranslate.addChild(lamp2LeftAntennaName);
                                        lamp2LeftAntennaName.addChild(lamp2LeftAntennaScale);
                                          lamp2LeftAntennaScale.addChild(lamp2LeftAntennaNode);
                                  lamp2HeadName.addChild(lamp2RightAntennaRotate);
                                    lamp2RightAntennaRotate.addChild(lamp2RightAntennaTranslate);
                                      lamp2RightAntennaTranslate.addChild(lamp2RightAntennaName);
                                        lamp2RightAntennaName.addChild(lamp2RightAntennaScale);
                                          lamp2RightAntennaScale.addChild(lamp2RightAntennaNode);
                                  lamp2HeadName.addChild(lamp2RightEyeRotate);
                                    lamp2RightEyeRotate.addChild(lamp2RightEyeTranslate);
                                      lamp2RightEyeTranslate.addChild(lamp2RightEyeName);
                                        lamp2RightEyeName.addChild(lamp2RightEyeScale);
                                          lamp2RightEyeScale.addChild(lamp2RightEyeNode);
                                  lamp2HeadName.addChild(lamp2LeftEyeRotate);
                                    lamp2LeftEyeRotate.addChild(lamp2LeftEyeTranslate);
                                      lamp2LeftEyeTranslate.addChild(lamp2LeftEyeName);
                                        lamp2LeftEyeName.addChild(lamp2LeftEyeScale);
                                          lamp2LeftEyeScale.addChild(lamp2LeftEyeSecondRotate);
                                            lamp2LeftEyeSecondRotate.addChild(lamp2LeftEyeNode);
                                  lamp2HeadName.addChild(lamp2lightTranslate);
                                    lamp2lightTranslate.addChild(lamp2lightName);
                                      lamp2lightName.addChild(lamp2lightScale);
                                        lamp2lightScale.addChild(lightNode);
                      
        lamp2.update();

      return lamp2;
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
