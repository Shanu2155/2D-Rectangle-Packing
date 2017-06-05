package mylogic2;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by LG-2 on 6/2/2017.
 */
public class TwoDPacking {
    public ArrayList<Point> /*xList,*/yList;
    public Rectangle bin;
    public TwoDPacking(int binWidth,int binHeight){
//        xList = new ArrayList<>();
        yList = new ArrayList<>();
        bin = new Rectangle(binWidth,binHeight);
    }
    public void addAllPoints(Rectangle r){
//        xList.add(r.topRight);
//        xList.add(r.bottomLeft);
//        xList.add(r.bottomRight);
//        xList.remove(r.topLeft);


        Point topRight=r.topRight,bottomRight=r.bottomRight,bottomLeft=r.bottomLeft;

        for(int i=topRight.y;i<=bottomRight.y;i++)
            yList.add(new Point(topRight.x,i));

        for(int i=bottomLeft.x;i<bottomRight.x;i++)
            yList.add(new Point(i,bottomLeft.y));

//        yList.add(r.topRight);
//        yList.add(r.bottomLeft);
//        yList.add(r.bottomRight);
        //yList.remove(r.topLeft);

//        Point.sortY(yList);

        int x1=r.topLeft.x,y1=r.topLeft.y,x2=r.topRight.x,y2=r.topRight.y;

        ListIterator listIterator=yList.listIterator();

        while(listIterator.hasNext()){
            Point p=(Point)listIterator.next();
            if(p.y<=y1 && p.x>=x1 && p.x<x2 )
                listIterator.remove();
        }

        Point.sortY(yList);

//        Point.sortX(xList);

    }
    public ArrayList<Rectangle> randomRectangles(int nos){
        Random r=new Random();
        ArrayList<Rectangle> list=new ArrayList<>();
        for(int i=0;i<nos;i++)
            list.add(new Rectangle((r.nextInt(10)+1)*23,(r.nextInt(10)+1)*23));
        return list;
    }
    public ArrayList<Rectangle> fit(ArrayList<Rectangle> inputRectangles){
        ArrayList<Rectangle> fitRectangles=new ArrayList<>();
        bin.setTopLeft(new Point(0,0));
        Rectangle r = inputRectangles.get(0);
        r.setTopLeft(new Point(0, 0));
        fitRectangles.add(r);
        addAllPoints(r);
        boolean flag;
        for (int i = 1; i < inputRectangles.size(); i++) {
            r = inputRectangles.get(i);
            for (int j = 0; j < yList.size(); j++) {
                Point p = yList.get(j);
                flag = true;
                if (flag && p.x + r.length <= bin.length && p.y + r.breadth <= bin.breadth ) {
                    r.setTopLeft(p);
                    if (!r.intersects(fitRectangles)) {
                        fitRectangles.add(r);
                        addAllPoints(r);
                        break;
                    } else {
                        r.rotate();
                        r.setTopLeft(p);
                        if (!r.intersects(fitRectangles)) {
                            fitRectangles.add(r);
                            addAllPoints(r);
                            break;
                        } else {
                            flag = false;
                        }
                    }
                }
                if (!flag && p.y + r.breadth <= bin.breadth && p.x + r.length <= bin.length && p.y + r.breadth >0 && p.x + r.length>0) {
                    r.reset();
                    r.setBottomLeft(p);
                    if (!r.intersects(fitRectangles)) {
                        fitRectangles.add(r);
                        addAllPoints(r);
                        break;
                    }
                    else {
                        r.reset();
                        r.rotate();
                        r.setBottomLeft(p);
                        if (!r.intersects(fitRectangles)) {
                            fitRectangles.add(r);
                            addAllPoints(r);
                            break;
                        }
                    }
                }
            }
        }
        return fitRectangles;
    }
    public static void main(String[] args) {
        TwoDPacking packing=new TwoDPacking(1300,725);
//        TwoDPacking packing=new TwoDPacking(300,400);
        ArrayList<Rectangle> inputRectangles = new ArrayList<>();
        inputRectangles=packing.randomRectangles(100);
//        System.out.println(inputRectangles);
//        inputRectangles.add(new Rectangle(100, 100));
//        inputRectangles.add(new Rectangle(120, 50));
//        inputRectangles.add(new Rectangle(120, 90));
//        inputRectangles.add(new Rectangle(100, 80));
//        inputRectangles.add(new Rectangle(80, 70));
//        inputRectangles.add(new Rectangle(120, 80));
//        inputRectangles.add(new Rectangle(70, 60));
//        inputRectangles.add(new Rectangle(80, 75));
//        inputRectangles.add(new Rectangle(50, 65));
//        inputRectangles.add(new Rectangle(30, 45));
        System.out.println(inputRectangles);
        ArrayList<Rectangle> fitRectangles=packing.fit(inputRectangles);
        System.out.println("FIT RECTANGLES:- "+fitRectangles.size());
        new Demo(packing.bin.length,packing.bin.breadth, fitRectangles);
    }
}
