// --- Modification History ---
// Modified by Ryoma Ohno・Raiki Yoshino, Hosei University, 2026

// Turtle Graphics Library extension for teaching OO concepts
// by Hideki Tsuiki, Kyoto University
// as a modification of Tatsuya Hagino's Turtle Graphics Library
// Copyright (C) 2000, Hideki Tsuiki, Kyoto University

// Turtle Graphics Library for Information Processing I
// Copyright (C) 1998, Tatsuya Hagino, Keio University
//
// Permission to use, copy, modify, and distribute this software
// for educational purpose only is hereby granted, provided that
// the above copyright notice appear in all copies and that both
// the copyright notice and this permission notice appear in
// supporting documentation.  This software is provided "as is"
// with no warranty.

package jetlearnsystem;

import java.awt.*;
import java.lang.Math;
import java.util.Vector;

public class Turtle implements Cloneable{
   public static boolean withTurtleAll = true;
   public Color tcolor = new Color(0, 130, 0);
   public double tscale = 0.5;

   // animation parameters
   private static double rotateStep = 5.0 *Math.PI/180.0 ;
   private static int moveStep = 5;
   private static int[] speedStep = {100000, 20, 5, 2};

   // turtle animation speed constants
   public final static int speedNoTurtle = 0;
   public final static int speedFast = 1;
   public final static int speedNormal = 2;
   public final static int speedSlow = 3;
   

   // set the menu
   public static void speedAll(int s) {
      if (s <= 0 || s >= speedStep.length) return;
      rotateStep = speedStep[s] *Math.PI/180.0;
      moveStep = speedStep[s];
   }

   private TurtleFrame f;
   private double angle;
   private double x, y;
   private double dx, dy;
   private boolean penDown;
   public Color c = Color.black; 
   private int turtleType = 0;
   // turtle animation rubber line
   private int rx, ry;
   private boolean rubber = false;
   public int moveWait = 20; 
   public int rotateWait = 20; 
   
   private boolean isGhostMode = false;
   
   public void setGhostMode(boolean isGhost) {
        this.isGhostMode = isGhost;
    }
   
   void addLineElement(int xx, int yy, int x, int y, Color c) {
        if (f == null) return;

        if (this.isGhostMode) {
            f.addGhostLineElement(xx, yy, x, y, c);
        } else {
            f.addLineElement(xx, yy, x, y, c);
        }
    }

   public Turtle(int x,int y, int ia)
      {
	 this.x = ((double)x + 0.5);
	 this.y = ((double)y + 0.5);
	 setangle((double)ia *Math.PI/180.0);
	 penDown = true;
      }

   public Turtle()
      {
	 this(200,200, 0);
      }

   public static final int turtleFig[][] =
   { {-12,-6,-12,6,0,18,12,6,12,-6,0,-18,-12,-6},
     {-18,-12,-12,-6},
     {-6,-24,0,-18,6,-24},
     {12,-6,18,-12},
     {12,6,18,12},
     {-6,24,0,18,6,24},
     {-18,12,-12,6},
     {-18,12,-18,-12,-6,-24,6,-24,18,-12,18,12,6,24,-6,24,-18,12},
     {-15,-15,-18,-24,-9,-21},
     {9,-21,18,-24,15,-15},
     {15,15,18,24,9,21},
     {-9,21,-18,24,-15,15},
     {-3,24,0,30,3,24},
     {-6,-24,-12,-36,-6,-48,6,-48,12,-36,6,-24}
   };
   public static final int turtleRFig[][] =
   { {-12,-6,-12,6,0,18,12,6,12,-6,0,-18,-12,-6},
     {-18,-12,-12,-6},
     {-6,-24,0,-18,6,-24},
     {12,-6,18,-12},
     {12,6,18,12},
     {-6,24,0,18,6,24},
     {-18,12,-12,6},
     {-18,12,-18,-12,-6,-24,6,-24,18,-12,18,12,6,24,-6,24,-18,12},
     {-15,-15,-24,-18,-9,-21},
     {-9,21,-24,18,-15,15},
     {-3,24,-3,30,3,24},
     {-6,-24,-6,-36,0,-48,12,-48,18,-36,6,-24},
     {9,-21,18,-30,15,-15},
     {15,15,18,30,9,21}
   };
   public static final int turtleLFig[][] =
   { {-12,-6,-12,6,0,18,12,6,12,-6,0,-18,-12,-6},
     {-18,-12,-12,-6},
     {-6,-24,0,-18,6,-24},
     {12,-6,18,-12},
     {12,6,18,12},
     {-6,24,0,18,6,24},
     {-18,12,-12,6},
     {-18,12,-18,-12,-6,-24,6,-24,18,-12,18,12,6,24,-6,24,-18,12},
     {-15,-15,-18,-30,-9,-21},
     {-9,21,-18,30,-15,15},
     {-3,24,3,30,3,24},
     {-6,-24,-18,-36,-12,-48,0,-48,6,-36,6,-24},
     {9,-21,24,-18,15,-15},
     {15,15,24,18,9,21}
   };
   protected int[][] turtleC = turtleFig;
   protected int[][] turtleR = turtleRFig;
   protected int[][] turtleL = turtleLFig;


   private void turtleDraw(Graphics g, int[][] data) {
      int ix = (int)x, iy = (int)y;
      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(2.5f));
      g2.setColor(tcolor);
      for (int i = 0; i < data.length; i++) {
	 int px = 0, py = 0;
	 for (int j = 0; j < data[i].length; j += 2) {
	    int kx = data[i][j], ky = data[i][j+1];
	    int nx = (int)((kx*(-dy) + ky*(-dx)) * tscale);
	    int ny = (int)((kx*dx+ ky*(-dy)) * tscale);
	    if (j > 0) g.drawLine(ix + px, iy + py, ix + nx, iy + ny);
	    px = nx;
	    py = ny;
	 }
      }
      g.setColor(c);
      g.fillOval(ix - 1, iy - 1, 2,2);
   }

   void setFrame(TurtleFrame f){
      this.f = f;
   }

   void show(Graphics g){
      if (rubber) {
	 g.setColor(c);
	 g.drawLine(rx, ry, (int)x, (int)y);
      }
      if (withTurtleAll) {
	 switch ((turtleType/2) % 4) {
	 case 0: case 2: 
	    turtleDraw(g, turtleC);
	    break;
	 case 1:
	    turtleDraw(g, turtleR);
	    break;
	 case 3:
	    turtleDraw(g, turtleL);
	    break;
	 }
      }
   }

   private void fcheck(){
      if(f == null) {
	 System.out.println("Turtle に対して fd などを呼び出すまえに，TurtleFrame に add してください。");
	 System.exit(1);
      }
   }

    private boolean animationEnabled = true;

    // turtle animation update
    private void turtleShow(int wait) {
        if (!animationEnabled) { 
            return;
        }
        turtleType++;
        
        if (f != null) {
            f.waitForPaintCompletion();
        }

        if (withTurtleAll) {
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (f != null) {
            f.repaint(); 
        }
    }
    
 


    /**
     * タートルの状態をアニメーションなしで即座に初期化する。
     */
    public void resetState() {
        this.x = 200.5;
        this.y = 200.5;
        
        setangle(0.0);
        this.penDown = true;
        this.c = Color.black;
        this.turtleType = 0;
        this.rubber = false;
        this.moveWait = 20;
        this.rotateWait = 20;

        if (f != null) {
            f.repaint();
        }
    }


    /**
     * タートルの描画アニメーションを有効/無効に設定する。
     * @param enabled trueなら有効、falseなら無効
     */
    public void setAnimationEnabled(boolean enabled) {
        this.animationEnabled = enabled;
    }

   /** n だけ前に進む。 */
   public void fd(int n) {
      dfd(n);
   }

   public void dfd(double n) {
      double xx = x;
      double yy = y;
      int back = 1;
      if(n < 0){
	 back = -1; n = -n;
      }
      fcheck();
      if (penDown) {
	 rx = (int)x;
	 ry = (int)y;
	 rubber = true;
      }

      if (!isGhostMode) {
          for (int i = moveStep; i < n; i += moveStep) {
             x = xx + back * dx * i;
             y = yy + back * dy * i;
             turtleShow(moveWait);
          }
      }
      
      x = xx + back * dx * n;
      y = yy + back * dy * n;

      if (penDown) {
         if (this.isGhostMode) {
             f.addGhostLineElement((int)xx, (int)yy, (int)x, (int)y, c);
         } else {
             f.addLineElement((int)xx, (int)yy, (int)x, (int)y, c);
         }
         rubber = false;
      }
      
      turtleShow(moveWait);
   }

   /** n だけ後ろに進む。 */
   public void bk(int n) {
      fd(-n);
   }

   private void setangle(double a)
      {
	 angle = a;
	 dx = Math.sin(a);
	 dy = -Math.cos(a);
      }

   private void turtleAngle(double a) {
      dx = Math.sin(a);
      dy = -Math.cos(a);
      turtleShow(rotateWait);
   }

   /** n 度だけ右に回る。 */
   public void rt(int ia) {
      rtd((double)ia*Math.PI/180.0);
   }
  
   private void rtd(double a)  {
      fcheck();
      for (double i = rotateStep; i < a; i += rotateStep)
	 turtleAngle(angle + i);
      angle = (angle + a);
      turtleAngle(angle);
   }

   /** n 度だけ左に回る。 */
   public void lt(int ia) {
      ltd((double)ia*Math.PI/180.0);
   }
  
   public void ltd(double a)  {
      fcheck();
      for (double i = rotateStep; i < a; i += rotateStep)
	 turtleAngle(angle - i);
      angle = (angle - a);
      turtleAngle(angle);
   }

   /** ペンをあげる。  */
   public void up() {
      penDown = false;
   }

   /** ペンをおろす。ペンをおろした状態で進むと、その軌跡が画面に残る。*/
   public void down() {
      penDown = true;
   }

   /** ペンが上がっているかどうか */
   public boolean isDown() {
      return (penDown);
   }

   /** ペンの色を nc に変更する。 */
   public void setColor(Color nc) {
      c = nc;
   }

   /** 動きの早さを x に設定する。x = 20 がデフォルトである。数字が小さいほど早い。*/
   public void speed(int x)
      {
	 moveWait = x;
	 rotateWait = x;
      }
  
   /** 現在の座標の X 成分を返す。 */
   public int getX()
      {
	 return (int)this.x;
      }

   /** 現在の座標の Y 成分を返す。*/
   public int getY()
      {
	 return (int)this.y;
      }

   /** 現在の角度を返す。*/
   public int getAngle()
      {
	 return (int)(this.angle * 180.0 /Math.PI);
      }
      
   /** 演習モード採点用: 現在の角度を正確なdouble型で返す。 */
   public double getDoubleAngle()
      {
	 return this.angle * 180.0 /Math.PI;
      }

   public int moveTo(double x, double y) {
      double prevx = this.x; double prevy = this.y;
      double a = Math.atan2(x - prevx, -( y - prevy));
      setangle(a);
      int r = (int)Math.sqrt((prevx - x) * (prevx - x) + (prevy - y) * (prevy - y));
      fd(r);
      this.x = ((double)x + 0.5);
      this.y = ((double)y + 0.5);
      turtleShow(moveWait);
      return(r);
   }

   public int moveTo(double x, double y, double aa) {
      int r = moveTo(x, y);
      setangle(aa);
      turtleShow(moveWait);
      return(r);
   }

   /** moveTo(x, y) と同様だが，その後に，angle の方向を向く。
    * 移動した距離を返す。
    */
   public int moveTo(int x, int y, int angle){
      return moveTo((double)x, (double)y, (double)angle*Math.PI/180.0);
   }
   
   /** (x, y) という座標まで移動する。ペンをおろした状態なら、線が描画される。
    * 移動した距離を返す。
    */
   public int moveTo(int x, int y){
      return moveTo((double)x, (double)y);
   }

   /* t と同じ座標まで移動する。ペンをおろした状態なら、線が描画される。
    * 移動した距離を返す。
    */
   public int moveTo(Turtle t)
      {
	 return moveTo(t.x, t.y);
      }

   /* 自分と同じ状態の Turtle を作成し，返す。
    */
   public Turtle clone()
      {
	 try{
	    return (Turtle)super.clone();
	 }catch(CloneNotSupportedException e){}
	 return null;
      }

      /** 演習モード採点用: 現在の座標の X 成分を正確なdouble型で返す。 */
   public double getDoubleX()
      {
	 return this.x;
      }

   /** 演習モード採点用: 現在の座標の Y 成分を正確なdouble型で返す。*/
   public double getDoubleY()
      {
	 return this.y;
      }

    private int getWidth() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private int getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}