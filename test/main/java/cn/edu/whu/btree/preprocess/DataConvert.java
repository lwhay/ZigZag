package cn.edu.whu.btree.preprocess;

import java.io.File;

import cn.edu.whu.btree.common.Tools;

public class DataConvert {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 5) {
            System.out.println("src stopwords dest recordNum lengthMin");
            System.exit(1);
        }
        String src = String.valueOf(args[0]); //"d:\\simfind\\amazon.txt";
        String sw = String.valueOf(args[1]); //"d:\\simfind\\stopwords.txt";
        String dest = String.valueOf(args[2]); //"d:\\simfind\\amazon_data.txt";
        int recordNum = Integer.valueOf(args[3]); //10000;
        final int lengthMin = Integer.parseInt(args[4]);

        //        String src = "d:\\pubmed_title_2017.txt";
        //        String sw = "d:\\simfind\\stopwords.txt";
        //        String dest = "d:\\simfind\\pubmed_title_data.txt";
        //        int recordNum = 20000000;
        File file = new File(dest);
        if (file.exists()) {
            file.delete();
        }
        Tools.stopWordsDel(src, dest, sw, "\\s+", recordNum, lengthMin);
    }

}
