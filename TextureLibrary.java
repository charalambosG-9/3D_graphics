// import java.io.File;
// import java.io.FileInputStream;

// import com.jogamp.opengl.*;
// import com.jogamp.opengl.util.texture.Texture;
// import com.jogamp.opengl.util.texture.spi.JPEGImage;

// import java.io.File;
// import java.io.FileInputStream;
// import com.jogamp.opengl.*;
// import com.jogamp.opengl.util.texture.*;

// public final class TextureLibrary {
    
//   // only deals with rgb jpg files
  
//   // public static int[] loadTexture(GL3 gl, String filename) {
//   //   return loadTexture(gl, filename, GL.GL_REPEAT, GL.GL_REPEAT,
//   //                                    GL.GL_LINEAR, GL.GL_LINEAR);
//   // }
  
//   // deals with rgba files

//   // public static int[] loadTextureRGBA(GL3 gl, String filename) {
//   //   return loadTextureRGBA(gl, filename, GL.GL_REPEAT, GL.GL_REPEAT,
//   //                                    GL.GL_LINEAR, GL.GL_LINEAR);
//   // }
  
//   // public static int[] loadTexture(GL3 gl, String filename, 
//   //                                 int wrappingS, int wrappingT, int filterS, int filterT) {
//   //   int[] textureId = new int[1];
//   //   try {
//   //     File f = new File(filename);      
//   //     JPEGImage img = JPEGImage.read(new FileInputStream(f));
//   //     gl.glGenTextures(1, textureId, 0);
//   //     gl.glBindTexture(GL.GL_TEXTURE_2D, textureId[0]);
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, wrappingS);
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, wrappingT);      
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, filterS);
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, filterT);
//   //     gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getData());
//   //     gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
//   //     gl.glBindTexture(GL.GL_TEXTURE_2D, 0); 
//   //   }
//   //   catch(Exception e) {
//   //     System.out.println("Error loading texture " + filename); 
//   //   }
//   //   return textureId;
//   // }

//   // deals with rgba files

//   // public static int[] loadTextureRGBA(GL3 gl, String filename, 
//   //                                 int wrappingS, int wrappingT, int filterS, int filterT) {
//   //   int[] textureId = new int[1];
//   //   try {
//   //     File f = new File(filename);      
//   //     JPEGImage img = JPEGImage.read(new FileInputStream(f));
//   //     gl.glGenTextures(1, textureId, 0);
//   //     gl.glBindTexture(GL.GL_TEXTURE_2D, textureId[0]);
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, wrappingS);
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, wrappingT);      
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, filterS);
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, filterT);
//   //     gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, img.getWidth(), img.getHeight(), 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE,  img.getData()); 
//   //     gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
//   //     gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
//   //     gl.glBindTexture(GL.GL_TEXTURE_2D, 0); 
//   //   }
//   //   catch(Exception e) {
//   //     System.out.println("Error loading texture " + filename); 
//   //   }
//   //   return textureId;
//   // }


//   public static Texture loadTexture(GL3 gl3, String filename) {
//     Texture t = null; 
//     try {
//       File f = new File(filename);
//       t = (Texture)TextureIO.newTexture(f, true);
//       t.setTexParameteri(gl3, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
//       t.setTexParameteri(gl3, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
//       t.setTexParameteri(gl3, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_EDGE);
//       t.setTexParameteri(gl3, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_EDGE); 
//       t.bind(gl3);
//     }
//     catch(Exception e) {
//       System.out.println("Error loading texture " + filename); 
//     }
//     return t;
//   }
  
// }

import java.io.File;
import java.io.FileInputStream;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;
  
  public final class TextureLibrary {
    
    public static Texture loadTexture(GL3 gl3, String filename) {
      Texture t = null; 
      try {
        File f = new File(filename);
        t = (Texture)TextureIO.newTexture(f, true);
        t.bind(gl3);
        t.setTexParameteri(gl3, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        t.setTexParameteri(gl3, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        t.setTexParameteri(gl3, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_EDGE);
        t.setTexParameteri(gl3, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_EDGE); 
      }
      catch(Exception e) {
        System.out.println("Error loading texture " + filename); 
      }
      return t;
    }
  }