import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintWriter;


/** This project is the simplied version of a perceptron 
which uses a part of the data to train weights and then uses those weights
 to classify the remining data.  
*/
/**
Author's name: Shreya Wadhwa.
JHED ID: swadhwa5
Student ID:3D5DB3
Date: 09/03/2019
*/


public class ProjectB {

/** Main method.
@param args not used
@throws IOException for inputoutput
   */ 
   public static void main(String[] args) throws IOException {
   
      double[][] x = new double[1048][4]; //data from training.txt
      int[] y = new int[1048]; //5th feature of training.txt
      double[] w = {0, 0, 0, 0}; //for weights.txt
      double[][] z = new double[324][4]; //data from validate.txt
      int[] b = new int[324]; 
      /**used to store authenticity (5th element in each row in validate.txt)
       of data from validate.txt which will later be 
       compared with the perceptron calculated classification*/
      
      int[] a = new int[324]; 
      /**predicted classification that is later to 
      be compared with real classification*/
      
      readTrainingFile(x, y);
      weightsTraining(w, x, y);
      readValidateFile(z, b);
      predictClassification(z, w, a);
      inputPredictFile(z, a);
      comparePredictWithValidateFile(a, b);
      
   
      
   
      
   }
   
   
   /**read training.txt and storing in in two seperate arrays X and Y.
   @param x an array storing the features of currency
   @param y storing classification of data
   @throws IOException for inputoutput
      */
   public static void readTrainingFile(double[][] x, int[] y) throws IOException {
   
   //reading training.txt
      FileInputStream trainingFileByteStream = null; 
      Scanner inFStraining = null; 
      trainingFileByteStream = new FileInputStream("training.txt");
      inFStraining = new Scanner(trainingFileByteStream);
      
      for (int i = 0, k = 0; i < 1048; i++) {
         for (int j = 0; j < 5; j++) {
            double temp1 = inFStraining.nextDouble();
            if (j == 4) {
               int value = Double.compare(temp1, 0.0);
               if (value == 0) {
                  y[k++] = 1;
                  
               }
               else {
                  y[k++] = -1;
               }
            }
            else {
               x[i][j] = temp1;
            }
         
         }
      }
      
      
      trainingFileByteStream.close();
   
   
   }
   
  
/**checking sign of wdotxi.
@param wdotxi to check sign
@return classification value
*/
   public static int checkSignOfwdotxi(double wdotxi) {
   
      int classification = 0;
   
      if (wdotxi >= 0) {
         classification = 1;
      }
      else if (wdotxi < 0) {
         classification = -1;
      }
      return classification;
   }
   
   /**training weights with input of array W, array X, and array Y.
   @param w for storing weights
   @param x an array stroing the feautures of currency
   @param y storing classification of data
   @throws IOException for inputoutput
   */
   public static void weightsTraining(double[] w, double[][] x, int[] y) throws IOException  {
   
      FileOutputStream weightsFileByteStream = null;
      PrintWriter outFSweights = null;
      weightsFileByteStream = new FileOutputStream("weights.txt");
      outFSweights = new PrintWriter(weightsFileByteStream);
      
      for (int i = 0; i < 1048; i++) {
      
         double wdotxi = 0;
         for (int j = 0; j < 4; j++) {
            wdotxi += w[j] * x[i][j];
         }
      
         if (checkSignOfwdotxi(wdotxi) != y[i]) {
            for (int l = 0; l < 4; l++) {
               w[l] += y[i] * x[i][l];
               outFSweights.printf("%.5f ", w[l]);
               outFSweights.flush();
            
            }
            outFSweights.println("");
            outFSweights.flush();
         }
         
          
      }
      weightsFileByteStream.close();
   }
   
         
   /**read validate.txt.
   @param z to store features of currency
   @param b to store pre defined classification of data in validate.txt
   @throws IOException for inputoutput
   */
   public static void readValidateFile(double[][] z, int[] b) throws IOException {
   
   //reading validate.txt
      FileInputStream validateFileByteStream = null; 
      Scanner inFSvalidate = null; 
      validateFileByteStream = new FileInputStream("validate.txt");
      inFSvalidate = new Scanner(validateFileByteStream); 
      
      for (int i = 0, k = 0; i < 324; i++) {
         for (int j = 0; j < 5; j++) {
            double temp2 = inFSvalidate.nextDouble();
            if (j == 4) {
               int value = Double.compare(temp2, 0.0);
               if (value == 0) {
                  b[k++] = 1;
                 
               }
               else {
                  b[k++] = -1;
                  
               
               }
            }
            else {
               z[i][j] = temp2;
            }
         }
      }
      
      
      validateFileByteStream.close();
   
   }
   
   /**predicting classification using weights.
   @param z containing data to be evaluated
   @param w contains weights
   @param a to store predicted classification
   */
   public static void predictClassification(double[][] z, double[] w, int[] a) {
   
      for (int i = 0; i < 324; i++) {
      
         double wdotzi = 0;
         for (int j = 0; j < 4; j++) {
            wdotzi += w[j] * z[i][j];
         }
         a[i] = checkSignOfwdotxi(wdotzi);
         
         
      }
   }
   
   /**input to predict.txt.
   @param z conataining data
   @param a containing calculated classification
   @throws IOException for inputoutput
   
   */
   public static void inputPredictFile(double[][] z, int[] a) throws IOException {
      FileOutputStream predictFileByteStream = null;
      PrintWriter outFSpredict = null;
      predictFileByteStream = new FileOutputStream("predict.txt");
      outFSpredict = new PrintWriter(predictFileByteStream);
      
      for (int i = 0; i < 324; i++) {
      
         for (int j = 0; j < 4; j++) {
            outFSpredict.print(z[i][j] + " ");
            outFSpredict.flush();
         }
      
         outFSpredict.println(a[i]);
         outFSpredict.flush();
         
      
         
      }
      
      predictFileByteStream.close();
      
      
      
   
   }
   
   /**comparing final classification of predict.txt and validate.txt.
   @param a containing calculated classification
   @param b containing pre defined classification
   */
   public static void comparePredictWithValidateFile(int[] a, int[] b) {
   
      int correct = 0;
      int incorrect = 0;
      double percentCorrect = 0.0;
   
      for (int i = 0; i < 324; i++) {
         if (a[i] == b[i]) {
            correct++;
         }
         else if (a[i] != b[i]) {
            incorrect++;
         }
      }
      
      percentCorrect = (correct / 324.0) * 100;
      
      System.out.println("Correct: " + correct);
      System.out.println("Incorrect: " + incorrect);
      System.out.println("Percent Correct: " + percentCorrect);
      
      
   }
   
   
   
   
}  
   
