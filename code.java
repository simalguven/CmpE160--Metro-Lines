import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class simal_guven {

    private static int how_many=0;
    private static final ArrayList<String> myEmptyList = new ArrayList<String>();
    public static void main(String[] args) throws Exception{


        File file = new File("coordinates.txt");
        Scanner metroInfo = new Scanner(file);

        String[][] lines = new String[10][2];
        int counter = 0;

        while(counter < 10){// with while loop we fill array lines
            String line1 = metroInfo.nextLine();
            String[] parts = line1.split(" ");

            String line2 = metroInfo.nextLine();

            lines[counter][0] = parts[1]; // lines[][0]  RGB values
            lines[counter][1] = line2;// lines[][1] station names and coordinates

            counter++;

        }

        metroInfo.close();//closing the file after finishing reading it

        ///creating all_metro_stations and all_coordinates arrays that will construct 3D array "everything " with array lines///
        String[] all_metro_stations = new String[10];// an array that includes each 10 metro line's metro station's names respectively
        String[] all_coordinates = new String[10];// an array that includes each 10 metro line's metro station's coordinates respectively

        int x = 0;
        while(x<10) {//while loop that creates all_metro_stations and all_coordinates
            String[] info = lines[x][1].split(" ");//making a string an array to iterate over it (lines[][1] are strings)

            int i = 0;
            counter = 0;
            String[] coordinates = new String[info.length/2];//coordinates are half of the number of elements in lines[][1]
            while(i< info.length){
                if(!(i%2 ==0)){//selecting odd indexes to extract coordinates not metro station names
                    coordinates[counter] = info[i];
                    counter++;//counter increases only if an odd index is found
                }
                i++;// i increases after every iteration

            }
            all_coordinates[x] = Arrays.toString(coordinates);//assigning the values

            //cleaning the metro line names starting with "*" and making a new list out of them

            // we construct all_metro_stations for this we need to use lines array
            String[] splitted = lines[x][1].split(" ");
            int length = splitted.length;

            String[] clean = new String[length/2];
            for(int r=0; r< splitted.length;r++){
                if((r%2 == 0)){
                    clean[r/2] = splitted[r];// Array splitted - containing each metro lines' stations with or without "*" sign

                }

            }

            for (int t = 0; t < clean.length; t++) {
                if (clean[t].startsWith("*")){     // Array clean - containing each metro lines' stations without any "*" sign
                    clean[t] = clean[t].substring(1);//reassigning array clean and now it became clean from "*"
                }

            }

            all_metro_stations[x] = Arrays.toString(clean);// assigning the values
            x++;
        }
        ///finished creating all_metro_stations and all_coordinates///



        // getting user inputs
        Scanner scanner = new Scanner(System.in);

        System.out.print("Starting station: ");
        String start = scanner.nextLine();

        System.out.print("Finishing station: ");
        String finish = scanner.nextLine();
        scanner.close();
        //finishing getting the inputs



        /// creating a 3D array to contain every info using predefined arrays all_metro_stations and all_coordinates///
        String[][][] everything = new String[10][2][];

        for(int i = 0; i < all_metro_stations.length; i++){//max value ofi is set to 9 (10-1)

            String[] all_metro_stations_new = new String[all_metro_stations[i].split(",").length];//create another array that has the lenght of how many stations a line contains

            int number = all_metro_stations[i].split(",").length;
            String[][] final_metro = new String[10][];//creating a ragged array
            final_metro[i] = new String[number];

            for(int j = 0; j<number;j++){//filling the empty ragged array // number is again equals to the length of how many stations a line(one we are looking to currently) contains

                if(j == 0){
                    all_metro_stations_new[j] = all_metro_stations[i].split(",")[j].substring(1);// if it is the first station get rid of "["(because the one we had was not a 2D array, but only a list with 10 (number of metro lines) elements)
                    final_metro[i][j] = all_metro_stations_new[j];

                }
                else if(j== number-1){
                    all_metro_stations_new[j] = all_metro_stations[i].split(",")[j].substring(0, all_metro_stations[i].split(",")[j].length()-1 ).substring(1);// if it is the last station get rid of "]" (because the one we had was not a 2D array, but only a list with 10 (number of metro lines) elements)
                    final_metro[i][j]= all_metro_stations_new[j];

                }else{
                    all_metro_stations_new[j] = all_metro_stations[i].split(",")[j].substring(1);
                    final_metro[i][j]= all_metro_stations_new[j];
                }

            }


            String[] all_coordinates_new = new String[all_coordinates[i].split(", ").length];//create another array that has the lenght of how many coordinates a line contains

            int number2 = all_coordinates[i].split(", ").length;
            String[][] final_coordinates = new String[10][];//creating a ragged array
            final_coordinates[i] = new String[number2];

            for(int j = 0; j<number2;j++){//filling the empty ragged array // number is again equals to the length of how many stations a line(one we are looking to currently) contains

                if(j == 0){
                    all_coordinates_new[j] = all_coordinates[i].split(", ")[j].substring(1);//if it is the first station's coordinate get rid of "["(because the one we had was not a 2D array, but only a list with 10 (number of metro lines) elements)
                    final_coordinates[i][j] = all_coordinates_new[j];

                }
                else if(j== number2-1){
                    all_coordinates_new[j] = all_coordinates[i].split(", ")[j].substring(0, all_coordinates[i].split(", ")[j].length()-1 );// if it is the last station's coordinate get rid of "]" (because the one we had was not a 2D array, but only a list with 10 (number of metro lines) elements)
                    final_coordinates[i][j]= all_coordinates_new[j];

                }else{
                    all_coordinates_new[j] = all_coordinates[i].split(", ")[j];
                    final_coordinates[i][j]= all_coordinates_new[j];
                }

            }

            everything[i][0] = final_metro[i];
            everything[i][1] = final_coordinates[i];//be aware that coordinate for instance 153,45 is stored like a string, it is "153,45", being one element

        }
        ///ending of the creation of 3D list that contains all information///


        ////// constructing this for loop to get how many stations will be visited, doing it outside the for loop that calls the draw function
        int input_iindex = 0;
        int output_iindex = 0;
        //int left_small_orange = 0;
        for (int i = 0; i<10; i++){//checking all the possible metro lines

            if ((Arrays.asList(everything[i][0]).contains(start)) && (Arrays.asList(everything[i][0]).contains(finish))){ // seeing if the same metro line contains starting and finishing points

                input_iindex = Arrays.asList(everything[i][0]).indexOf(start); //getting the index of starting point
                output_iindex = Arrays.asList(everything[i][0]).indexOf(finish); //getting the index of finishing point

                if(input_iindex> output_iindex) {  //traveling backwards
                    for (int m = 0; m < ((input_iindex - output_iindex) + 1); m++) {//iterating as many times as the number of visited stations

                        myEmptyList.add(everything[i][1][input_iindex - m]);// adding every visited station's coordinates to the arraylist that was first empty
                    }

                }else{ //traveling forward
                    for (int m = 0; m < ((output_iindex - input_iindex) + 1); m++){//iterating as many times as the number of visited stations

                        myEmptyList.add(everything[i][1][input_iindex + m]);//adding every visited station's coordinates to the arrayList that was first empty

                    }
                }
            }

        }

        int input_index = 0;
        int output_index = 0;

        for(int i = 0; i<10;i++){ // same for loop as before but second one calling draw() function and printing out visited stations names respectively

            if ((Arrays.asList(everything[i][0]).contains(start)) && (Arrays.asList(everything[i][0]).contains(finish))){

                input_index = Arrays.asList(everything[i][0]).indexOf(start);
                output_index = Arrays.asList(everything[i][0]).indexOf(finish);

                if(input_index> output_index) {
                    for (int m = 0; m < ((input_index - output_index) + 1); m++) {
                        System.out.println(everything[i][0][input_index - m]);//printing out visited metro stations

                    }
                    draw();//calling the draw() function

                }else{
                    for (int m = 0; m < ((output_index - input_index) + 1); m++){
                        System.out.println(everything[i][0][input_index+m]);//printing out visited metro stations

                    }
                    draw();//calling the draw function

                }}
        }
    }

    ////draw() function does necessary calculations to reach the values (RGB, metrolines's stations and coordinates) to draw teh canvas.
    /// draw() body contains the same calculations done previously(with arrays) to construct everything array,but this time it is only for drawing the canvas
    public static void draw() throws FileNotFoundException {

        File file = new File("coordinates.txt");
        Scanner metroInfo = new Scanner(file);

        String[][] lines = new String[10][2];
        int counter = 0;

        while(counter < 10){
            String line1 = metroInfo.nextLine();
            String[] parts = line1.split(" ");

            String line2 = metroInfo.nextLine();

            lines[counter][0] = parts[1];
            lines[counter][1] = line2;

            counter++;

        }

        metroInfo.close();

        StdDraw.setCanvasSize(1024, 482);
        StdDraw.setXscale(0,1024);
        StdDraw.setYscale(0,482);
        StdDraw.picture(512, 241, "background.jpg");//getting the background image

        String[] all_metro_stations = new String[10];// an array that includes each 10 metro line's metro station's names respectively
        String[] all_coordinates = new String[10];// an array that includes each 10 metro line's metro station's coordinates respectively

        int l = 0;
        while(l<10) {
            String[] info = lines[l][1].split(" ");

            int i = 0;
            counter = 0;
            String[] coordinates = new String[info.length/2];
            while(i< info.length){
                if(!(i%2 ==0)){
                    coordinates[counter] = info[i];
                    counter++;
                }
                i++;

            }
            all_coordinates[l] = Arrays.toString(coordinates);

            String[] RGB =lines[l][0].split(",");//creating a string to get the Red, Blue and Green values seperately

            StdDraw.setPenColor(Integer.parseInt(RGB[0]),  Integer.parseInt(RGB[1]),  Integer.parseInt(RGB[2]));
            StdDraw.setPenRadius(0.012);
            int counter2 = 0;
            int a = 0;
            while(a<((coordinates.length)-1)){//drawing lines and points with iteration
                String[] xy = coordinates[a].split(","); //in the second iteration x0 becomes the first iteration's x1 and goes like it
                double x0 = Integer.parseInt(xy[0]), y0 = Integer.parseInt(xy[1]);

                String[] xy2 = coordinates[a+1].split(",");
                double x1 = Integer.parseInt(xy2[0]), y1 = Integer.parseInt(xy2[1]);

                StdDraw.line(x0,y0,x1,y1);//drawing the line
                double circle_radius = 0.01;
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.point(Double.parseDouble(xy[0]),Double.parseDouble(xy[1]));//draw a point on the metro station to emphasize it
                StdDraw.setPenColor(Integer.parseInt(RGB[0]),  Integer.parseInt(RGB[1]),  Integer.parseInt(RGB[2]));// change the pen color to the metro line's color at the end of the while loop to prevent it staying white
                a++;


            }


            String[][] star = new String[info.length/2][2];

            int s = 0;
            int u = 0;
            while (s < info.length/2){// info contains each metro line's station's names and their coordinates one by one
                star[s][0] =info[u];//getting the metro station's names
                u++;
                star[s][1] =info[u];//getting the coordinates

                if (star[s][0].startsWith("*")) {// only print the metro station's name if it starts with "*"
                    String p = "";

                    p = star[s][0].substring(1);// p is what text is going to be written to the canvas
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.setFont(new Font("Helvetica", Font.BOLD,8));
                    StdDraw.textLeft((Integer.parseInt(star[s][1].split(",")[0]))-25,(Integer.parseInt(star[s][1].split(",")[1]))+5, p);//setting the coordinates of the text that is going to be and the text
                }

                s++;
                u++;
            }
            l++;
        }

        for(int i=0; i<(myEmptyList.size());i++){//loop that draws points on visited stations in the canvas

            if(!(i==myEmptyList.size()-1)){
                StdDraw.setPenRadius(0.012);
                StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                StdDraw.point(Double.parseDouble(myEmptyList.get(i).split(",")[0]), Double.parseDouble(myEmptyList.get(i).split(",")[1]));//draws the little points
            }else{//index of the finishing station that is in the visited stations arraylist
                StdDraw.setPenRadius(0.02);
                StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                StdDraw.point(Double.parseDouble(myEmptyList.get(i).split(",")[0]), Double.parseDouble(myEmptyList.get(i).split(",")[1]));//draws the last big point
            }


            int pauseDuration = 300; // pause duration in milliseconds


            StdDraw.show(); // show the drawing on the screen
            StdDraw.pause(pauseDuration);// pause the drawing at each iteration



        }



        }







}



