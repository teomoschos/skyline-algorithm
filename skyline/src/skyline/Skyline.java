/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**The class Skyline is a class which scanns the iputs of a txt file,which in this case,
 * is  a number  of coordinates in the level of axes x and y.In this class an ArrayList is used in order to 
 * save the valus of the coordinates in order to use them ,for finding the skyline of the coordinates.
 * In order to find the Skyline ,a devine and conquere algorithm is used in orden to minimize the complexity 
 * of the problem,usinng the method SkylineMethod ,that means that this algorithm can give as a result in 
 * complexity O(n).
 * 
 * @author ΜΟΣΧΟΣ ΘΕΟΔΩΡΟΣ (ΑΕΜ 2980)
   tmoschos@csd.auth.gr
*/
package skyline;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Skyline {
    
/**
 * The Class Coords,is a subclass that is used in order to make the elements of the ArayList,
 * look like coordinates because when an  ArrayList is used all the elements that are stored 
 * inside it ,are settled in a raw.So in order to change it we use the variables x and y,
 * of this class.
 */
 public static class Coords{
     private int x;
     private int y;
     
     /**
      * The constructor of this class,Coords,is used to initialize 
      * the values,of the viriables x and y.
      */
     public Coords(int x,int y){
         this.x=x;
         this.y=y;
     }
     /**
      * This method,getx,is used to return the value of the viriable x.
      * @return x
      */
    public int getx(){
        return x;
    }
     /**
      * This method,gety,is used to return the value of the viriable y.
      * @return y
      */
    public int gety(){
        return y;
    }
    /**
     * This method,toString,is used to return the viriables  x and y ,as a string
     * in order to make them look like coordinates.
     * @return x,y as coordinates.
     */
     public String toString(){
         return x+" "+y;
     } 
     
 }
/**
 * This method,SkylineMethod,is the method that is used to calculate the skyline,
 * of the coordinates that are given in the programm.Here is the link ,that i used 
 * to create the devine and conquere algorithm https://stackoverflow.com/questions/49603454/skyline-of-2d-points-divide-and-conquer-algorithm,
 * i have personally  adjusted the code in orden to fit in my own programm.
 * In this method a divine and conqure algorithm is used.More specifically,we create two subArrayList ,leftPoints
 * and right points,in which we store the values of the main ArrayList coordinate.Then we use two other subArrayLists 
 * ,leftSkyline and rightSkyline ,to call recursivly the method SkylineMethod until we complitely devide the elements
 * of the initial Arraylist.After that ,specific conditions are used in order to create the Skyline.
 * every element which is closer to the axes is stored into leftSkyline ,creating the final skyline ,
 * which is returned in the main method.
 * Because of this algorithm,the problem is solved a lot faster ,than using brute force,because the complexity 
 * of the algorithm is a lot simpler ,specifically the divine proccess is about O(n) ,so the conclusional
 * complexity is about  O(n) in the best case and about O(nlogn) in the worse.
 * @return leftSkyline ,that is the finalk skyline.
 */
 public static ArrayList<Coords> SkylineMethod(ArrayList<Coords> coordinate){
     
    if (coordinate.size()<=1){
        return coordinate;
    }
//SubArrayLists in which we store the values of initial ArrayList.
    ArrayList<Coords> leftSkyline=new ArrayList<>();
    ArrayList<Coords> rightSkyline=new ArrayList<>();
    ArrayList<Coords> leftPoints=new ArrayList<>();
    ArrayList<Coords> rightPoints=new ArrayList<>();
    
    for (int i=0;i<coordinate.size()/2;i++){
       leftPoints.add(coordinate.get(i));
    }
    for (int i=coordinate.size()/2;i<coordinate.size();i++){
      rightPoints.add(coordinate.get(i));
    }
//recuscive method of using the algorithm
    leftSkyline=SkylineMethod(leftPoints);
    rightSkyline=SkylineMethod(rightPoints);
//Using the viriable min ,with the if condition ,
//every element with a smaller y dominates the min
//and is stored into it.
    int min=1000000000;
    for (int i=0;i<leftSkyline.size();i++){
        if (leftSkyline.get(i).y<min){
            min=leftSkyline.get(i).y;
        }
    }
 //With this conition every element with y that does not dominate
 //is being remover from the list.
  for (int i=rightSkyline.size() -1; i >=0; i--){
        if (rightSkyline.get(i).y>=min){
            rightSkyline.remove(i);
        }
    }   
  
    leftSkyline.addAll(rightSkyline);//creating the skyline.
    return leftSkyline; //returning the skyline.
}
 
 /**
  * This method ,is the main method of the programm.Initially ,in this 
  * method the input file ,is being scanned and after that it is stored into
  * the ArrayList coordinate.Then,Using the viriables x,y and sizeofcoords
  * the values of the ArayList ,change into the form of coordinates.Then,the coordinates
  * are getting sorted ,using collectios.sort ,in order to get sorted based also on the y
  * coordinate.Finally,an ArrayList is created in order to assign into it the slyline 
  * that is returned from the method SkylineMethod and then the slyine in printed
  * as  the result of the programm. 
  * @param args
  * @throws Exception 
  */
    public static void main (String[] args)throws Exception { 
    try{
        //Scanning the file. 
        Scanner scanner=null;
        scanner=new Scanner(new File("input500000.txt"));
        ArrayList<Coords> coordinate=new ArrayList<>();//the main ArrayList.
        
        int sizeofcoords=scanner.nextInt();
        int x;
        int y;   
        //Turning the values into coordinates.
    while(sizeofcoords>0){
            x=(scanner.nextInt());
            y=(scanner.nextInt());
            coordinate.add(new Coords(x,y)); 
            sizeofcoords--;
    }  
    //Sorting the coordinates ,based also on the y coordinate.
     Collections.sort(coordinate,Comparator.comparing(Coords::getx).thenComparing(Coords::gety));    
     ArrayList<Coords> finalskyline= SkylineMethod(coordinate);
     System.out.print("The skyline is: ");
     System.out.println(finalskyline);//Printing the skyline.
     
    }catch(FileNotFoundException e){
        System.out.println("could not find file");
    }
 }
}
