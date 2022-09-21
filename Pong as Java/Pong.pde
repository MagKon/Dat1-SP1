//Init sound and net libraries
import processing.sound.*;
import processing.net.*;

//Create global variables
float p1X = 0, p1Y = 100, p1W = 25, p1H = 50;
float p2X = 975, p2Y = 100, p2W = 25, p2H = 50;
float ballX = 487.5, ballY = 125, pballX = 0, pballY = 0, ballW = 25, ballH = 25, ballAcc = 1;
float br = 255, bg = 255, bb = 255, gabeY = 0;
float timer = 0;
float dirX = 1, dirY = 0;
float[] dirXR = {1, 0};
float[] dirYR = {1, 0};
int rate = 0, restart = -1;

//Init 2 player classes
Player p1;
Player p2;

//Init sound system and fft audio statistics util
FFT fft;
AudioIn in;
int bands = 512;
float[] spectrum = new float[bands];
Sound s;
SoundFile file;

//Init image
PImage img;

//Uncomment for server and client variables
//Server server;
//Client c;
//String cInput;
//float cData[];

void setup() {
  size(1000, 250);
  //fullScreen();
  frameRate(144);
  p2X = width - p2W;
  
  //Create sound playback. Reads this clients microphone and sends data to this client
  fft = new FFT(this);
  in = new AudioIn(this);
  in.start();
  fft.input(in);
   
  in.play();
  
  //init server
  //server = new Server(this, 11111);
  
  p1 = new Player(p1X, p1Y, p1W, p1H, 1);
  p2 = new Player(p2X, p2Y, p2W, p2H, 2);
  
  img = loadImage("Lord_And_Savior.png");
  
  if (restart == -1) {
    background(255);
    rectMode(CENTER);
    textSize(16);
    fill(0);
    text("Welcome to GABENBALL!", width/2, height/2, 200, 100); 
    text("Press on the screen to start", width/2-5, height/2+50, 200, 100); 
    //rectMode(CORNER);
    noLoop();
  }
}

void draw() {
  ballAcc += .001;
  rate += 1;
  time();
  if (restart != -1){
    background(br, bg, bb);
    if (gabeY <= height/4)
      gabeY += 1;
    image(img, width/4, gabeY, width/2, height/2);
    keyPressed();
    //p1();
    //p2();
    p1.drawPlayer();
    p2.drawPlayer();
    ball();
    moveBall();
    score();
    
    fft.analyze(spectrum);
  
    for(int i = 0; i < bands; i++){
    // The result of the FFT is normalized
    // draw the line for frequency band i scaling it up by 5 to get more amplitude.
    line( i, height, i*2, height - spectrum[i]*height*5 );
    } 
    
    if (p1.score >= 10 || p2.score >= 10) {
      noLoop();
      fill(0);
      text("Restart?", width/2 - 12.5, height/2, 200, 200);
      restart = 1;
    }
  }
}

//public void p1() {
  //rect(p1X,p1Y, p1W, p1H);
//}

//public void p2() {
  //Uncomment to receive player 2 data
  //c = server.available(); 
  //if (c != null){
    //cInput = c.readString();
    //cInput = cInput.substring(0, cInput.indexOf("\b"));
    //cData = float(split(cInput, ' '));
    //p2X = cData[0];
    //p2Y = cData[1]; 
    //p2W = cData[2]; 
    //p2H = cData[3];
    //rect(p2X, p2Y, p2W, p2H);
  //}  
  //else
  //rect(p2X, p2Y, p2W, p2H);
//}

public void ball() {
  //Uncomment to send ball data to client
  //server.write(p1X + " " + p1Y + " " + p1W + " " + p1H + "\b" + ballX + " " + ballY + " " + ballW + " " + ballH + " " + dirX + " " + dirY + "\n");
  ellipseMode(CENTER);
  ellipse(ballX, ballY, ballW, ballH);
  imageMode(CENTER);
  image(img, ballX, ballY, ballW, ballH);
  imageMode(CORNER);
}

public void moveBall(){
  //Determine X
  if (isInsideRect(p1.x, p1.y, p1.w, p1.h, ballX, ballY)){
    dirX = 1; 
  }
  else if (isInsideRect(p2.x, p2.y, p2.w, p2.h, ballX, ballY)) {
    dirX = 0;
  }
  
  //(ballX > p1X && ballX < p1X + p1H) && (ballY > p1Y && ballY < p1Y + p1H)

  if (ballY > height)
    dirY = 1;
  else if (ballY < 0)
    dirY = 0;
  //move X
  if (dirX == 1)
    ballX += 5 * ballAcc;
  else
    ballX -= 5 * ballAcc;
  
  //Move Y
  if (dirY == 0)
    ballY += 3 * ballAcc;
  else
    ballY -= 3 * ballAcc;
}

public void score() {
  boolean isScored = false;
  if (ballX < 0){
    p2.score += 1;
    println("Player 2: " + p2.score);
    isScored = true;
  }
  else if (ballX > width){
    p1.score += 1;
    println("Player 1: " + p1.score);
    isScored = true;
  }
  
  if (isScored) {
    ballX = 487.5;
    ballY = 125;
    br = random(0, 255);
    bg = random(0, 255);
    bb = random(0, 255);
    int randomX = int(random(dirXR.length));
    int randomY = int(random(dirYR.length));
    dirX = dirXR[randomX];
    dirY = dirYR[randomY];
    ballAcc = 1;
  }
  
  rectMode(CORNER);
  textSize(16);
  fill(0);
  text("Time: " + (int)timer, 492.5, 0, 200, 100); 
  text(p1.score + ":" + p2.score, 500, 15, 100, 100); 
  fill(255);
}
public void keyPressed(){
  p1.movePlayer(key);
  p2.movePlayer(key);
  
  key = '0';
  keyCode = LEFT;
}

void time() {
  if (rate >= frameRate){
    timer += 1;
    rate = 0;
  }
}

void mousePressed() {
  if (restart == 1) {
    p1.score = 0;
    p2.score = 0;
    timer = 0;
    restart = 0;
    loop();
  }
  else if (restart == -1){
    restart = 0;
    loop();
  }
}

boolean isInsideRect(float rx, float ry, float rw, float rh, float px, float py) {
  boolean isInside = false;
  
  if (isInRange(rx, rx + rw*2, px) && isInRange(ry, ry + rh, py)) {
    isInside = true;
  }
  
  return isInside;
}

boolean isInRange(float start, float end, float value) {
  boolean isRange = false;
  if (value >= start && value <= end)
    isRange = true;
  return isRange;
}
