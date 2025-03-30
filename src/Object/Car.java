package src.Object;

import src.Util.GameUtil;
import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import static src.MusicPlay.SoundControlUnits.Car_Move_Music;
import static src.User_interface.GameInterface.moves;

/**
 * This class represents the car template.
 * It contains methods to judge the validity of movements of the cars.
 * @author Pei Yinan
 * @since 1.0
 */
public class Car extends JButton implements FocusListener {
    public int index;
    public String name;
    public int moveDistance = GameUtil.SQUARE_LENGTH/6;

    /**
     * Constructor that contains the index and name of the car.
     * @param index  the index of the car
     * @param name   the name of the car
     */
    public Car(int index, String name){
        this.index=index;
        this.name=name;
        addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e) {}

    /**
     * This method judges whether the car's x and y coordinates are out of bound.
     * @param newX The new x coordinate after the move
     * @param newY The new y coordinate after the move
     * @return Judge whether the car is within the bound
     */
    public boolean outOfBound(int newX, int newY) {
        int leftBound = 3*GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2;
        int rightBound = 3*GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2+GameUtil.SQUARE_LENGTH;
        int upperBound = 2*GameUtil.OFFSET;
        int lowerBound = 2*GameUtil.OFFSET+GameUtil.SQUARE_LENGTH;
        return newX < leftBound || newX + getWidth() > rightBound ||
                newY < upperBound || newY + getHeight() > lowerBound;
    }

    /**
     * This method judges whether there are other cars around
     * @param newX The new x coordinate after the move
     * @param newY The new y coordinate after the move
     * @param cars Other vehicles
     * @return Judge whether it collides with other vehicles
     */
    public boolean collide(int newX, int newY, Car[] cars) {
        for (Car car : cars) {
            if (car != this && car.getBounds().intersects
                    (newX, newY, getWidth(), getHeight())) {
                return true;
            }
        }
        return false;
    }

    /* The following four methods implements the function of making the vehicles move left, right, upward
     * and downward. When a car has no other cars around and doesn't move out of the bound, it can
     * be moved. Meanwhile, for horizontal and vertical cars, they can only be moved right and left,
     * upward and downward respectively.
     */
    /**
     * This method implements the function of making the cars move left.
     * @param x  x coordinate of the car
     * @param y  y coordinate of the car
     * @param cars  other vehicles
     */
    public void moveLeft(int x, int y, Car[] cars) {
        x -= moveDistance;
        if (getWidth()>getHeight() && !outOfBound(x, y) && !collide(x, y, cars)) {
            setBounds(x, y, getWidth(), getHeight());
            Car_Move_Music();
            moves++;
        }
    }

    /**
     * This method implements the function of making the cars move right.
     * @param x  x coordinate of the car
     * @param y  y coordinate of the car
     * @param cars  other vehicles
     */
    public void moveRight(int x, int y, Car[] cars) {
        x += moveDistance;
        if (getWidth()>getHeight() && !outOfBound(x, y) && !collide(x, y, cars)) {
            setBounds(x, y, getWidth(), getHeight());
            Car_Move_Music();
            moves++;
        }
    }

    /**
     * This method implements the function of making the cars move upward.
     * @param x  x coordinate of the car
     * @param y  y coordinate of the car
     * @param cars  other vehicles
     */
    public void moveUpward(int x, int y, Car[] cars) {
        y -= moveDistance;
        if (getWidth()<getHeight() && !outOfBound(x, y) && !collide(x, y, cars)){
            setBounds(x, y, getWidth(), getHeight());
            Car_Move_Music();
            moves++;
        }
    }

    /**
     * This method implements the function of making the cars move downward.
     * @param x  x coordinate of the car
     * @param y  y coordinate of the car
     * @param cars  other vehicles
     */
    public void moveDownward(int x, int y, Car[] cars) {
        y += moveDistance;
        if(getWidth()<getHeight() && !outOfBound(x, y) && !collide(x, y, cars)){
            setBounds(x, y, getWidth(), getHeight());
            Car_Move_Music();
            moves++;
        }
    }
}