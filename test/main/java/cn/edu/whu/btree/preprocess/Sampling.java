/**
 * 
 */
package cn.edu.whu.btree.preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author iclab
 *
 */
public class Sampling {

    public static void main(String[] args) throws IOException {
        if (args.length != 6) {
            System.out.println("Usage: totalRecords samplingRecords input intInput output intOuput");
            System.exit(0);
        }
        int totalCount = Integer.parseInt(args[0]);
        int samplingCount = Integer.parseInt(args[1]);
        String dataPath = args[2];
        String intPath = args[3];
        String dataQuery = args[4];
        String intQuery = args[5];
        int[] samples = new int[samplingCount];
        for (int i = 0; i < samplingCount; i++) {
            samples[i] = (int) (Math.random() * totalCount);
        }
        Arrays.sort(samples);
        BufferedReader brIS = new BufferedReader(new FileReader(dataPath));
        BufferedReader brII = new BufferedReader(new FileReader(intPath));
        BufferedWriter bwIS = new BufferedWriter(new FileWriter(dataQuery));
        BufferedWriter bwII = new BufferedWriter(new FileWriter(intQuery));
        
        for (int i = 0, cursor = 0; i < totalCount && cursor < samplingCount; i++) {
            String query = brIS.readLine();
            if (i == samples[cursor]) {
                query += "\n";
                bwIS.write(query);
                cursor++;
            }
        }
        brIS.close();
        bwIS.close();
        for (int i = 0, cursor = 0; i < totalCount && cursor < samplingCount; i++) {
            String query = brII.readLine();
            if (i == samples[cursor]) {
                query += "\n";
                bwII.write(query);
                cursor++;
            }
        }
        brII.close();
        bwII.close();
    }
}
