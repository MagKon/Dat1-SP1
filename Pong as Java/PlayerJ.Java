class PlayerJ{
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
        g.drawRect(x,y, w, h);
    }
}