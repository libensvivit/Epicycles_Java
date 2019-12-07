import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class Epicycles extends PApplet {
    String STATE = "FOURIER";

    float amplitude;
    float time;
    float interval;
    float frequency;
    float phase;
    float zoom;

    ArrayList<PVector> path;
    ArrayList<PVector> drawing;
    ArrayList<Complex> toAnalyze;
    ArrayList<Complex> epicycles;
    PVector pos = new PVector();
    PVector prev = new PVector();

    public void settings(){
        size(800, 600);

        toAnalyze = new ArrayList<>();
        epicycles = new ArrayList<>();
        path = new ArrayList<>();
        drawing = new ArrayList<>();
        zoom = 1f;
    }

    public void draw(){
        frameRate(40);
        background(51);
        stroke(255);
        noFill();


        if (STATE.equals("USER")){
            drawing.add(new PVector(mouseX-width/2f, mouseY-height/2f));
            drawing.add(new PVector(mouseX-width/2f, mouseY-height/2f));
        }

        if (STATE.equals("FOURIER")){
            path.add(getFourier(width/2f, height/2f, epicycles));

            beginShape();
            for (int i = 0; i < path.size(); i++) {
                vertex(path.get(i).x, path.get(i).y);
            }
            endShape();

            scale(0.5f);

            time += interval;

            if (time > TWO_PI){
                time = 0;
                path.clear();
            }
            stroke(255, 25);
        }

        beginShape();
        for (PVector p : drawing){
            vertex(p.x + width/2f, p.y + height/2f);
        }
        endShape();

        text("FPS = " + frameRate, 20, 20);
        text("Epicycles = " + epicycles.size(), 20, 40);
    }

    PVector getFourier(float x, float y, ArrayList<Complex> ongoing){
        pos.x = x;
        pos.y = y;
        interval = TWO_PI/ongoing.size();

        for (Complex complex : ongoing) {
            prev.x = pos.x;
            prev.y = pos.y;

            frequency = complex.freq;
            amplitude = complex.amp;
            phase = complex.phase;

            float theta = frequency * time + phase;

            pos.x += amplitude * cos(theta);
            pos.y += amplitude * sin(theta);

            noFill();
            stroke(255, 150);
            ellipse(prev.x, prev.y, amplitude * 2, amplitude * 2);
            stroke(255);
            line(prev.x, prev.y, pos.x, pos.y);
        }
        return new PVector(pos.x, pos.y);
    }

    public void mousePressed(){
        STATE = "USER";
        drawing.clear();
        path.clear();
        toAnalyze.clear();
        time = 0;
    }

    public void mouseReleased(){
        STATE = "FOURIER";

        for (int i = 0; i < drawing.size()-1; i ++){
            PVector point = drawing.get(i);
            toAnalyze.add(new Complex(point.x, point.y));
        }
        epicycles = Discrete_Fourier_Transform.dft(toAnalyze);
        Complex.SortComplex(epicycles);
    }


    public static void main(String[] args){
        String[] processingArgs = {"Epicycles"};
        Epicycles sketch = new Epicycles();
        PApplet.runSketch(processingArgs,sketch);
    }
}
