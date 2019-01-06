package cn.edu.whu.btree.preprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Distinct {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line = "";
        int lc = 0;
        int wc = 0;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            wc += words.length;
            lc++;
        }
        br.close();
        System.out.println(lc + "\t" + wc);
    }

}
