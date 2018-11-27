import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static  void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        String str = in.nextLine();
        str = in.nextLine();
        int F = in.nextInt();
        int D = in.nextInt();
        if (F==2&&D==1) ArifmeticFilter(MedianFilter(Exchange(str, n ,m)));

    }



    public static  int[][] Exchange(String str, int n, int m) {
        String[][] pixels_str = new String[n][m];
        int[][] pixels = new int[n][m];
        int k=0;
        String[] ary = str.split(" ");
        for (int i = 0; i < pixels_str.length; i++) {           //to array of strings
            for (int j = 0; j < pixels_str[i].length; j++) {
                pixels_str[i][j] = ary[k];
                k++;

            }
        }
        for (int i = 0; i < pixels_str.length; i++) {              //to array of hex
            for (int j = 0; j < pixels_str[i].length; j++) {
                pixels[i][j]= Integer.parseInt(pixels_str[i][j], 16);
            }
        }
        return pixels;
    }

    public static int[][] MedianFilter(int mass[][])  {
        Color[] pixel=new Color[9];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];
        for(int i=1;i<mass.length-1;i++)
            for(int j=1;j<mass.length-1;j++)
            {
                pixel[0]=new Color(mass[i-1][j-1]);
                pixel[1]=new Color(mass[i-1][j]);
                pixel[2]=new Color(mass[i-1][j+1]);
                pixel[3]=new Color(mass[i][j+1]);
                pixel[4]=new Color(mass[i+1][j+1]);
                pixel[5]=new Color(mass[i+1][j]);
                pixel[6]=new Color(mass[i+1][j-1]);
                pixel[7]=new Color(mass[i][j-1]);
                pixel[8]=new Color(mass[i][j]);
                for(int k=0;k<9;k++){
                    R[k]=pixel[k].getRed();
                    B[k]=pixel[k].getBlue();
                    G[k]=pixel[k].getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                mass[i][j] = new Color(R[4],B[4],G[4]).getRGB();
            }

        return mass;
    }


    public static int[][] ArifmeticFilter(int mass[][]){
        Color[] pixel=new Color[9];
        int P;
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];
        for(int i=1;i<mass.length-1;i++)
            for(int j=1;j<mass.length-1;j++)
            {
                pixel[0]=new Color(mass[i][j]);
                R[0]=pixel[0].getRed();
                B[0]=pixel[0].getBlue();
                G[0]=pixel[0].getGreen();
                P=(R[0]+B[0]+G[0])/3;
                mass[i][j] = P;
            }

        return mass;
    }

    




}
