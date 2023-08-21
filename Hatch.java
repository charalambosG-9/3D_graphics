
/* I declare that this code is my own work 
/* Author: Charalambos Georgiades
/* Email: CGeorgiades1@sheffield.ac.uk
*/
import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Hatch extends JFrame implements ActionListener {
  
  private static final int WIDTH = 1024;
  private static final int HEIGHT = 768;
  private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
  private GLCanvas canvas;
  private GLEventListener glEventListener;
  private final FPSAnimator animator; 

  public static void main(String[] args) {
    Hatch b1 = new Hatch("Office");
    b1.getContentPane().setPreferredSize(dimension);
    b1.pack();
    b1.setVisible(true);
    b1.canvas.requestFocusInWindow();
  }

  public Hatch(String textForTitleBar) {
    super(textForTitleBar);
    GLCapabilities glcapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
    canvas = new GLCanvas(glcapabilities);
    Camera camera = new Camera(Camera.DEFAULT_POSITION, Camera.DEFAULT_TARGET, Camera.DEFAULT_UP);
    glEventListener = new Hatch_GLEventListener(camera);
    canvas.addGLEventListener(glEventListener);
    canvas.addMouseMotionListener(new MyMouseInput(camera));
    canvas.addKeyListener(new MyKeyboardInput(camera));
    getContentPane().add(canvas, BorderLayout.CENTER);
    
    JMenuBar menuBar=new JMenuBar();
    this.setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("File");
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);
    menuBar.add(fileMenu);
    
    JPanel p = new JPanel();
    GridLayout layout = new GridLayout(2, 6);
    p.setLayout(layout);
      JButton b = new JButton("Light 1");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Light 2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Spotlight 1");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Spotlight 2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 1 - Position 1");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 1 - Position 2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 1 - Position 3");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 2 - Position 1");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 2 - Position 2");
      b.addActionListener(this);
      p.add(b);
      b = new JButton("Lamp 2 - Position 3");
      b.addActionListener(this);
      p.add(b);;
    this.add(p, BorderLayout.SOUTH);
    
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        animator.stop();
        remove(canvas);
        dispose();
        System.exit(0);
      }
    });
    animator = new FPSAnimator(canvas, 60);
    animator.start();
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equalsIgnoreCase("Light 1")) {
      ((Hatch_GLEventListener) glEventListener).lightControls("Light 1");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Light 2")) {
      ((Hatch_GLEventListener) glEventListener).lightControls("Light 2");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Spotlight 1")) {
      ((Hatch_GLEventListener) glEventListener).lightControls("Spotlight 1");
      
    }
    else if (e.getActionCommand().equalsIgnoreCase("Spotlight 2")) {
      ((Hatch_GLEventListener) glEventListener).lightControls("Spotlight 2"); 
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 1 - Position 1")) {
      ((Hatch_GLEventListener) glEventListener).lampControls("Lamp 1 - Position 1");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 1 - Position 2")) {
      ((Hatch_GLEventListener) glEventListener).lampControls("Lamp 1 - Position 2");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 1 - Position 3")) {
      ((Hatch_GLEventListener) glEventListener).lampControls("Lamp 1 - Position 3");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 2 - Position 1")) {
      ((Hatch_GLEventListener) glEventListener).lampControls("Lamp 2 - Position 1");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 2 - Position 2")) {
      ((Hatch_GLEventListener) glEventListener).lampControls("Lamp 2 - Position 2");
    }
    else if (e.getActionCommand().equalsIgnoreCase("Lamp 2 - Position 3")) {
      ((Hatch_GLEventListener) glEventListener).lampControls("Lamp 2 - Position 3");
    }
  }
}

class MyKeyboardInput extends KeyAdapter  {
  private Camera camera;
  
  public MyKeyboardInput(Camera camera) {
    this.camera = camera;
  }
  
  public void keyPressed(KeyEvent e) {
    Camera.Movement m = Camera.Movement.NO_MOVEMENT;
    switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:  m = Camera.Movement.LEFT;  break;
      case KeyEvent.VK_RIGHT: m = Camera.Movement.RIGHT; break;
      case KeyEvent.VK_UP:    m = Camera.Movement.UP;    break;
      case KeyEvent.VK_DOWN:  m = Camera.Movement.DOWN;  break;
      case KeyEvent.VK_A:  m = Camera.Movement.FORWARD;  break;
      case KeyEvent.VK_Z:  m = Camera.Movement.BACK;  break;
    }
    camera.keyboardInput(m);
  }
}

class MyMouseInput extends MouseMotionAdapter {
  private Point lastpoint;
  private Camera camera;
  
  public MyMouseInput(Camera camera) {
    this.camera = camera;
  }
  
    /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */    
  public void mouseDragged(MouseEvent e) {
    Point ms = e.getPoint();
    float sensitivity = 0.001f;
    float dx=(float) (ms.x-lastpoint.x)*sensitivity;
    float dy=(float) (ms.y-lastpoint.y)*sensitivity;
    //System.out.println("dy,dy: "+dx+","+dy);
    if (e.getModifiers()==MouseEvent.BUTTON1_MASK)
      camera.updateYawPitch(dx, -dy);
    lastpoint = ms;
  }

  /**
   * mouse is used to control camera position
   *
   * @param e  instance of MouseEvent
   */  
  public void mouseMoved(MouseEvent e) {   
    lastpoint = e.getPoint(); 
  }
}