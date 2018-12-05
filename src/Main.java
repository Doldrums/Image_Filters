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
        if (F==2&&D==1) System.out.println(Max(ArifmeticFilter(MedianFilter(Exchange(str, n ,m))))+"  " +Min(ArifmeticFilter(MedianFilter(Exchange(str, n ,m)))));
        if (F==2&&D==2) System.out.println(Max(Sredn(MedianFilter(Exchange(str, n ,m))))+Min(ArifmeticFilter(MedianFilter(Exchange(str, n ,m)))));
        // if (F==2&&D==3) ArifmeticFilter(MedianFilter(Exchange(str, n ,m)));
        if (F==2&&D==4) System.out.println(Max(MaxBrightness(MedianFilter(Exchange(str, n ,m))))+Min(ArifmeticFilter(MaxBrightness(MedianFilter(Exchange(str, n ,m))))));
        if (F==1&&D==1) System.out.println(Max(ArifmeticFilter(GeometricFilter(Exchange(str, n ,m))))+Min(ArifmeticFilter(GeometricFilter(Exchange(str, n ,m)))));
        if (F==1&&D==2) System.out.println(Max(Sredn(GeometricFilter(Exchange(str, n ,m))))+Min(Sredn(GeometricFilter(Exchange(str, n ,m)))));
        // if (F==1&&D==3) ArifmeticFilter(GeometricFilter(Exchange(str, n ,m)));
        if (F==1&&D==4) System.out.println(Max(MaxBrightness(GeometricFilter(Exchange(str, n ,m))))+Min((MaxBrightness(GeometricFilter(Exchange(str, n ,m))))));

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

    public static int[][] MaxBrightness(int mass[][]){
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
                mass[i][j] = new Color(R[8],B[8],G[8]).getRGB();
            }

        return mass;
    }


    public static int[][] GeometricFilter(int mass[][]){

        int[][] mass1= new int[mass.length-2][mass.length-2];
        final int z = 1/9;
        for(int i=1;i<mass.length-1;i++)
            for(int j=1;j<mass.length-1;j++) {
                mass1[i-1][j-1]= (int) Math.pow(mass[i-1][j-1]*mass[i-1][j]*mass[i-1][j+1]*mass[i][j+1]*mass[i+1][j+1]*mass[i+1][j]*mass[i+1][j-1]*mass[i][j-1]*mass[i][j],z);
                for(int k=1;k<mass.length;k++){
                    for (int l = 1; l < mass.length; l++) {
                        mass[i][j]=mass1[i-1][j-1];
                    }
                }
            }

        return mass;
    }

    public static int[][] Sredn(int mass[][]){
        int R;
        int B;
        int G;
        for(int i=0;i<mass.length;i++){
            for(int j=0;j<mass.length;j++)
            {
                Color pixel=new Color(mass[i][j]);
                R=pixel.getRed();
                B=pixel.getBlue();
                G=pixel.getGreen();

                mass[i][j]= (int)(1*0.299*R+0.587*G+0.114*B);
            }}
        return mass;
    }

    public static int Max(int mass[][]){

        double max=0;
        int maxx=0;
        int maxy=0;
        for (int i = 0; i <mass.length ; i++) {
            for (int j = 0; j <mass[i].length ; j++) {
                int R = 0;
                int B = 0;
                int G = 0;
                double Y = 0;
                Color pixel = new Color(mass[i][j]);
                R = pixel.getRed();
                B = pixel.getBlue();
                G = pixel.getGreen();
                Y = 0.299 * R + 0.587 * G + 0.114 * B;
                if (Y >= max) {
                    max = Y;
                    maxx=i;
                    maxy=j;
                }
            }
        }
        return (int)mass[maxx][maxy];

    }
    public static  int Min(int mass[][]){

        double max=0;
        int maxx=0;
        int maxy=0;
        for (int i = 0; i <mass.length ; i++) {
            for (int j = 0; j <mass[i].length ; j++) {
                int R = 0;
                int B = 0;
                int G = 0;
                double Y = 0;
                Color pixel = new Color(mass[i][j]);
                R = pixel.getRed();
                B = pixel.getBlue();
                G = pixel.getGreen();
                Y = 0.299 * R + 0.587 * G + 0.114 * B;
                if (Y <= max) {
                    max = Y;
                    maxx=i;
                    maxy=j;
                }
            }
        }
        return (int)mass[maxx][maxy];

    }





}
