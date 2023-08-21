import gmaths.*;
//import java.nio.*;
//import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
//import com.jogamp.opengl.util.*;
//import com.jogamp.opengl.util.awt.*;
//import com.jogamp.opengl.util.glsl.*;
import com.jogamp.opengl.util.texture.Texture;
import java.util.Random;
  
public class Hatch_GLEventListener implements GLEventListener {
  
  private static final boolean DISPLAY_SHADERS = false;
  private Camera camera;
    
  /* The constructor is not used to initialise anything */
  public Hatch_GLEventListener(Camera camera) {
    this.camera = camera;
    this.camera.setPosition(new Vec3(6f,9f,17f));
  }
  
  // ***************************************************
  /*
   * METHODS DEFINED BY GLEventListener
   */

  /* Initialisation */
  public void init(GLAutoDrawable drawable) {   
    GL3 gl = drawable.getGL().getGL3();
    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glEnable(GL.GL_BLEND); 
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);  
    gl.glDepthFunc(GL.GL_LESS);
    gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
    gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled'
    gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW
    initialise(gl);
    startTime = getSeconds();
  }
  
  /* Called to indicate the drawing surface has been moved and/or resized  */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL3 gl = drawable.getGL().getGL3();
    gl.glViewport(x, y, width, height);
    float aspect = (float)width/(float)height;
    camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
  }

  /* Draw */
  public void display(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    render(gl);
  }

  // Dispose method 
  public void dispose(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    floor.dispose(gl);
    leftWall.dispose(gl);
    rightWall.dispose(gl);
    window.dispose(gl);
    windowBottomFrame.dispose(gl);
    windowTopFrame.dispose(gl);
    windowLeftFrame.dispose(gl);
    windowRightFrame.dispose(gl);
    light.dispose(gl);
    light2.dispose(gl);
    spotlight1.dispose(gl);
    spotlight2.dispose(gl);
  }

  // ***************************************************
  /* THE SCENE
   * Now define all the methods to handle the scene.
   * This will be added to in later examples.
   */

  private Model floor, leftWall, rightWall, window, windowBottomFrame, windowTopFrame, windowLeftFrame, windowRightFrame;
  private SGNode skybox, eggStructure, lamp1, lamp2, monsterStructure;
  private Light light, light2, spotlight1, spotlight2;

  Model[] walls;
  
  private float eggAngle = 0;
  private float eggAngleZ = 0;
  private float eggHeight = 0;
  private float monsterPositionY = 0;
  private float monsterPositionX = 20f;
  private boolean monsterForwards = true;
  float rotateSkyAngle = 0;

  private Boolean light1On = true;
  private Boolean light2On = true;
  private Boolean spotlight1On = true;
  private Boolean spotlight2On = true;

  private Egg egg;

  private Monster monster;

  private Lamp1 lamp1Class;
  private Lamp2 lamp2Class;

  private Room room;

  private Sky sky;

  int lamp1Position = 1;
  int lamp2Position = 1;

  Vec3 light1Position = new Vec3(5, 10, -5);
  Vec3 light2Position = new Vec3(-2, 10, 6);

  // ***************************************************
  /* Textures
  * All textures needed
  * All textures used are either created by me or have a creative commons license
  */
  
  private Texture[] texture;

  private void loadTextures(GL3 gl) {

    texture = new Texture[18];
    texture[0] = TextureLibrary.loadTexture(gl, "textures/base.jpg");
    texture[1] = TextureLibrary.loadTexture(gl, "textures/egg.png");
    texture[2] = TextureLibrary.loadTexture(gl, "textures/egg_specular.png");
    texture[3] = TextureLibrary.loadTexture(gl, "textures/alien_body.jpg");
    texture[4] = TextureLibrary.loadTexture(gl, "textures/eye.jpg");
    texture[5] = TextureLibrary.loadTexture(gl, "textures/floor.jpg");
    texture[6] = TextureLibrary.loadTexture(gl, "textures/wall.jpg");
    texture[7] = TextureLibrary.loadTexture(gl, "textures/window.jpg");
    texture[8] = TextureLibrary.loadTexture(gl, "textures/window_border.jpg");
    texture[9] = TextureLibrary.loadTexture(gl, "textures/sky_middle.png");
    texture[10] = TextureLibrary.loadTexture(gl, "textures/table.jpg");
    texture[11] = TextureLibrary.loadTexture(gl, "textures/chicken.png");
    texture[12] = TextureLibrary.loadTexture(gl, "textures/building.jpg");
    texture[13] = TextureLibrary.loadTexture(gl, "textures/sky_left.png");
    texture[14] = TextureLibrary.loadTexture(gl, "textures/sky_top.png");
    texture[15] = TextureLibrary.loadTexture(gl, "textures/sky_bottom.png");
    texture[16] = TextureLibrary.loadTexture(gl, "textures/sky_right.png");
    texture[17] = TextureLibrary.loadTexture(gl, "textures/sky_back.png");

  }
  
  // Initialisation method
  public void initialise(GL3 gl) {

    loadTextures(gl);

    light = new Light(gl);
    light.setCamera(camera);

    light2 = new Light(gl);
    light2.setCamera(camera);

    spotlight1 = new Light(gl);
    spotlight1.setCamera(camera);

    spotlight2 = new Light(gl);
    spotlight2.setCamera(camera);

    Light[] lights = new Light[]{light, light2, spotlight1, spotlight2};
    
    updateSkyAngle();
    sky = new Sky(gl, camera, lights);
    skybox = sky.makeSky(rotateSkyAngle, new Texture[]{texture[9], texture[12], texture[13], texture[14], texture[15], texture[16], texture[17]});

    room = new Room(gl, camera, lights);
    walls = room.makeRoom(new Texture[]{texture[5], texture[6], texture[7], texture[8]});
    floor = walls[0];
    rightWall = walls[1];
    leftWall = walls[2];
    window = walls[3];
    windowBottomFrame = walls[4];
    windowTopFrame = walls[5];
    windowLeftFrame = walls[6];
    windowRightFrame = walls[7];

    egg = new Egg(gl, camera, lights);
    eggStructure = egg.makeEggStructure(eggAngle, eggAngleZ , eggHeight, new Texture[]{texture[0], texture[1], texture[2], texture[10]});

    monster = new Monster(gl, camera, lights);
    monsterStructure = monster.makeMonster(new Texture[]{texture[11]}, monsterPositionY, monsterPositionX);

    lamp1Class = new Lamp1(gl, camera, lights);
    lamp1 = lamp1Class.makeLamp1(spotlight1, spotlight1On, lamp1Position, new Texture[]{texture[3], texture[4]});

    lamp2Class = new Lamp2(gl, camera, lights);
    lamp2 = lamp2Class.makeLamp2(spotlight2, spotlight2On, lamp2Position, new Texture[]{texture[3], texture[4]});
  }
 
  // Render method
  
  public void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    light.setPosition(light1Position); 
    light2.setPosition(light2Position);
    Light1Colour();
    Light2Colour();
    light.render(gl);
    light2.render(gl);
    
    updateSkyAngle();
    skybox = sky.makeSky(rotateSkyAngle, new Texture[]{texture[9], texture[12], texture[13], texture[14], texture[15], texture[16], texture[17]});
    
    skybox.draw(gl);

    updateEggAngle();
    updateEggAngleZ();
    updateEggHeight();
    eggStructure = egg.makeEggStructure(eggAngle, eggAngleZ ,eggHeight, new Texture[]{texture[0], texture[1], texture[2], texture[10]});
    eggStructure.draw(gl);

    lamp1 = lamp1Class.makeLamp1(spotlight1, spotlight1On, lamp1Position, new Texture[]{texture[3], texture[4]});
    lamp1.draw(gl);

    lamp2 = lamp2Class.makeLamp2(spotlight2, spotlight2On, lamp2Position, new Texture[]{texture[3], texture[4]});
    lamp2.draw(gl);

    updateMonsterPosition();
    monsterStructure = monster.makeMonster(new Texture[]{texture[11]}, monsterPositionY, monsterPositionX);
    monsterStructure.draw(gl);

    floor.render(gl);
    leftWall.render(gl);
    rightWall.render(gl);

    window.render(gl);
    windowBottomFrame.render(gl);
    windowTopFrame.render(gl);
    windowLeftFrame.render(gl);
    windowRightFrame.render(gl);
  }

  private void updateSkyAngle() {
    double elapsedTime = getSeconds()-startTime;
    rotateSkyAngle = (float)elapsedTime * (0.5f);
    //rotateSkyAngle = 0;
  }
  
  // Turn light on or off
  private void Light1Colour() {
    Vec3 lightColour;
    Material m = light.getMaterial();
    if(light1On) {
      lightColour = new Vec3(1f, 1f, 1f);
      m.setAmbient(0.5f,0.5f,0.5f);
    } else {
      lightColour = new Vec3(0f, 0f, 0f);
      m.setAmbient(0.3f,0.3f,0.3f);
    }
    m.setDiffuse(Vec3.multiply(lightColour,0.9f));
    m.setSpecular(Vec3.multiply(m.getDiffuse(),0.5f));
    light.setMaterial(m);
    light.setCubeColour(lightColour);
  }

  // Turn light2 on or off
  private void Light2Colour() {
    Material m = light2.getMaterial();
    Vec3 lightColour;
    if(light2On) {
      lightColour = new Vec3(1f, 1f, 1f);
      m.setAmbient(0.5f,0.5f,0.5f);
    } else {
      lightColour = new Vec3(0f, 0f, 0f);
      m.setAmbient(0.3f,0.3f,0.3f);
    }
    m.setDiffuse(Vec3.multiply(lightColour,0.9f));
    m.setSpecular(Vec3.multiply(m.getDiffuse(),0.5f));
    light2.setMaterial(m);
    light2.setCubeColour(lightColour);
  }

  // Update the monster position
  private void updateMonsterPosition() {
    double elapsedTime = getSeconds() - startTime + 20;
    monsterPositionY = (float) Math.sin(elapsedTime * 1);
    if(monsterForwards)
      monsterPositionX -= 0.05f;   

    if(monsterPositionX >= 50f){
      monsterForwards = true;
    } else if (monsterPositionX <= -50f){
      monsterPositionX = 50f;
    }
  }

  // Update the egg angle rotating around y axis
  private void updateEggAngle() {
    double elapsedTime = getSeconds() - startTime;
    eggAngle = 90 + (float)elapsedTime * (5);
  }

  // Update the egg angle rotating around x and z axis
  private void updateEggAngleZ() {
    double elapsedTime = getSeconds()-startTime;
    Random rand = new Random();
    int n = rand.nextInt(10);
    int m = rand.nextInt(11);
    if(elapsedTime % 10 > 5 && elapsedTime % 10 < 8 )
      if(m > 5)
        eggAngleZ = n/2;
      else
        eggAngleZ = -n/2;
  }

  // Update the egg height
  private void updateEggHeight() {
    double elapsedTime = getSeconds()-startTime;
    if((float) Math.sin(elapsedTime * 3) > 0){
        eggHeight = (float) Math.sin(elapsedTime * 3);
    }
  }

  // Control the lights
  public void lightControls(String command){
    if(command.equals("Light 1")){
      light1On = !light1On;
    }
    else if(command.equals("Light 2")){
      light2On = !light2On;
    }
    else if(command.equals("Spotlight 1")){
      spotlight1On = !spotlight1On;
    }
    else if(command.equals("Spotlight 2")){
      spotlight2On = !spotlight2On;
    }
  }

  // Control lamp poses
  public void lampControls(String command){

    switch(command){
      case "Lamp 1 - Position 1":
        lamp1Position = 1;
        break;
      case "Lamp 1 - Position 2":
        lamp1Position = 2;
        break;
      case "Lamp 1 - Position 3":
        lamp1Position = 3;
        break;
      case "Lamp 2 - Position 1":
        lamp2Position = 1;
        break;
      case "Lamp 2 - Position 2":
        lamp2Position = 2;
        break;
      case "Lamp 2 - Position 3":
        lamp2Position = 3;
        break;
    }
  }

    // ***************************************************
  /* TIME
   */ 
  
  private  double startTime;
  
  private  double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }
}

