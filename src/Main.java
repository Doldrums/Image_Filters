import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static  void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        String str = in.nextLine();
        str = in.nextLine(); //считываем набор пикселей в 16-ой системе

        /*
        F  может принимать следующие значения:

        1 - фильтр, основанный на вычислении среднего геометрического;
        2 - медианный фильтр.

         D  может принимать следующие значения:

        1 - использование cреднего арифметического;
        2 - использование средневзвешенного;
        3 - использование ближайшей точки на нейтральной оси;
        4 - определение величины яркости.
         */


        int F = in.nextInt();       //определяем алгоритм подавления шума
        int D = in.nextInt();       //определяем алгоритм обесцвечивания
        if (F==2&&D==1) System.out.println(Min(ArifmeticFilter(MedianFilter(Exchange(str, n ,m)))));//+"  " +Min(ArifmeticFilter(MedianFilter(Exchange(str, n ,m)))));
        if (F==2&&D==2) System.out.println(Max(Sredn(MedianFilter(Exchange(str, n ,m))))+Min(ArifmeticFilter(MedianFilter(Exchange(str, n ,m)))));
        if (F==2&&D==3) ArifmeticFilter(MedianFilter(Exchange(str, n ,m)));
        if (F==2&&D==4) System.out.println(Max(MaxBrightness(MedianFilter(Exchange(str, n ,m))))+Min(ArifmeticFilter(MaxBrightness(MedianFilter(Exchange(str, n ,m))))));
        if (F==1&&D==1) System.out.println(Max(ArifmeticFilter(GeometricFilter(Exchange(str, n ,m))))+Min(ArifmeticFilter(GeometricFilter(Exchange(str, n ,m)))));
        if (F==1&&D==2) System.out.println(Max(Sredn(GeometricFilter(Exchange(str, n ,m))))+Min(Sredn(GeometricFilter(Exchange(str, n ,m)))));
        if (F==1&&D==3) ArifmeticFilter(GeometricFilter(Exchange(str, n ,m)));
        if (F==1&&D==4) System.out.println(Max(MaxBrightness(GeometricFilter(Exchange(str, n ,m))))+Min((MaxBrightness(GeometricFilter(Exchange(str, n ,m))))));

    }


    //ПЕРЕВОД СТРОКИ ШЕСТНАДЦАТИРИЧНЫХ СИМВОЛОВ В ДВУМЕРНЫЙ ДЕСЯТИЧНЫЙ МАССИВ
    public static  int[][] Exchange(String str, int n, int m) {
        String[][] pixels_str = new String[n][m];
        int[][] pixels = new int[n][m];
        int k=0;
        String[] ary = str.split(" ");  //строка в одномерный массив строк


        for (int i = 0; i < pixels_str.length; i++) {           //одномерный массив строк в двумерный массив строк
            for (int j = 0; j < pixels_str[i].length; j++) {
                pixels_str[i][j] = ary[k];
                k++;

            }
        }


        for (int i = 0; i < pixels_str.length; i++) {
            for (int j = 0; j < pixels_str[i].length; j++) {
                pixels[i][j]= Integer.parseInt(pixels_str[i][j], 16);
                //двумерный массив строк в двумерный массив десятичных чисел
            }
        }




        System.out.println("Полученный массив");      //просто проверка
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
               System.out.print(pixels[i][j]+ " ");
            }
        }
        return pixels;


    }


    //ФИЛЬТРЫ НАПРАВЛЕННЫЕ НА ПОДАВЛЕНИЕ ШУМА
    public static int[][] MedianFilter(int mass[][])  {   //медианный фильтр

        /*
        Захватываем цвет 8 пикселей вокруг главного пикселя. Включая главный пиксель, будет 9 пикселей.
  Берем значения R, G, B каждого пикселя и помещаем их в массив. Сортируем массивы. Получаем среднее значение массива,
 какой будет медиана значений цвета в этих 9 пикселях. Устанавливаем цвет в главный пиксель.
*/

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

                mass[i][j] = new Color(R[4],G[4],B[4]).getRGB(); // вот тут основная проблема

                mass[i][j]= Integer.parseInt(Integer.toHexString(Math.abs(mass[i][j])).substring(2,7)); //попытка её решить

            }


        //проверка
        System.out.println();
        System.out.println("Медианный фильтр");
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < mass[i].length; j++) {
                System.out.print(mass[i][j] + " ");
            }
        }
        return mass;
    }
    public static int[][] GeometricFilter(int mass[][]){  //геометрический фильтр
        /*Алгоритм фильтра перемножает все значения пикселей плавающего окна и затем вычисляет корень от полученного числа.
Степень корня определяется размером плавающего окна.*/

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




    //ФИЛЬТРЫ НАПРАВЛЕННЫЕ НА ОБСЦВЕЧИВАНИЕ
    public static int[][] ArifmeticFilter(int mass[][]){ //арифметический фильтр

        /*Яркость итогового серого пикселя вычисляется как среднее арифметическое трех цветовых каналов */

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

        //Проверка
        System.out.println();
        System.out.println("Арифметический фильтр");
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < mass[i].length; j++) {
                System.out.print(mass[i][j] + " ");
            }
        }
        System.out.println();


        return mass;
    }
    public static int[][] MaxBrightness(int mass[][]){ //фильтр основанный на определении величины яркости
        /* Величина яркости определяется как максимальная интенсивость одной из компонент RGB:*/
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
    public static int[][] Sredn(int mass[][]){ //фильтр на основе средневзвешенного
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



    //НА ВЫХОДЕ ПОЛУЧАЕМ МАКС И МИН ЯРКОСТЬ
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
