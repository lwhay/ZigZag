package cn.edu.whu.btree.preprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InfoGet {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 2) {
            System.out.println("src recordNum");
            System.exit(1);
        }
        final String src = String.valueOf(args[0]);
        final int recordNum = Integer.valueOf(args[1]);
        long count = 0;
        int lineCount = 0;
        try {
            BufferedReader srcReader = new BufferedReader(new FileReader(src));
            String line = null;
            while ((line = srcReader.readLine()) != null && lineCount < recordNum) {
                count += line.trim().split("\\s+").length;
                lineCount++;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("lineCount " + lineCount);
        System.out.println("avglen " + count * 1.0 / lineCount);
    }

}
