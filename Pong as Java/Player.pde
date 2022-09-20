class Player {
  float x;
  float y;
  float w;
  float h;
  int score = 0;
  int pNum;
  
  Player(float x_, float y_, float w_, float h_, int pNum_) {
    x = x_;
    y = y_;
    w = w_;
    h = h_;
    pNum = pNum_;
    //drawPlayer();
  }
  
  void drawPlayer() {
    rect(x,y, w, h);
  }
  
  void movePlayer(int key) {
    if (pNum == 1){
      switch (key){
        case 'w':
          if (y > 0){
            y -= height/10;
            //Uncomment to send player 1 data to client
            //server.write(p1X + " " + p1Y + " " + p1W + " " + p1H + "\b");
          }
        break;
        case 's':
          if (y < height-h){
            y += height/10;
            //Uncomment to send player 1 data to client
            //server.write(p1X + " " + p1Y + " " + p1W + " " + p1H + "\b");
          }
        break;
      }
      key = '0';
    }
    if (pNum == 2) {
      switch (keyCode) {
        case UP:
        if (y > 0){
          y -= height/10*1.5;
        }
        break;
        case DOWN:
        if (y < height-h){
          y += height/10*1.5;
        }
        break;
      }
    }
  }
}
