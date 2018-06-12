package cn.edu.whu.btree.preprocess;

import java.io.File;

import cn.edu.whu.btree.common.Tools;

public class AmazonDataFormat {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 3) {
            System.out.println("src dest field");
            System.exit(1);
        }
        String src = String.valueOf(args[0]); //"D:\\simfind\\tfidf\\user_dedup_small.json";
        String dest = String.valueOf(args[1]); //"D:\\simfind\\amazon.txt";
        String field = String.valueOf(args[2]);
        File file = new File(dest);
        if (file.exists()) {
            file.delete();
        }
        Tools.amazonDataFormat(src, dest, field.trim());
    }

}
