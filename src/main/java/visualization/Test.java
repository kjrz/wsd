package visualization;

import road.Position;
import road.Road;
import road.Route;
import traffic.CarWithSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Test {

    public static void addCars(Visualization canvas){

    }

    public static void main(String [] args){
        Visualization v = new Visualization();

        int i = 0;
        while(true) {

            Set<Integer> positions = new HashSet<Integer>();
            positions.add(i+1);
            positions.add(i+50);

            v.nextTimeFrame(positions);
            i++;
            i++;

            try {
                Thread.sleep(10);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

    }
}