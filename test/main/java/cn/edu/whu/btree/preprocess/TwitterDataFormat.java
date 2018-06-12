package cn.edu.whu.btree.preprocess;

import java.io.File;

import cn.edu.whu.btree.common.Tools;

public class TwitterDataFormat {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 2) {
            System.out.println("src dest");
            System.exit(1);
        }
        String src = String.valueOf(args[0]).trim(); //"D:\\simfind\\tfidf\\user_dedup_small.json";
        String dest = String.valueOf(args[1]); //"D:\\simfind\\amazon.txt";
        File file = new File(dest);
        if (file.exists()) {
            file.delete();
        }
        Tools.twitterDataFormat(src, dest);
    }

}
