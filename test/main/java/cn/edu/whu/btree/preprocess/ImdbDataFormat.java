package cn.edu.whu.btree.preprocess;

import cn.edu.whu.btree.common.Tools;

public class ImdbDataFormat {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 6) {
            System.out.println("msrc asrc dest recordNum gramNum ratio");
            System.exit(1);
        }
        String msrc = String.valueOf(args[0]);//"d:\\DataSet\\data.tsv";
        String asrc = String.valueOf(args[1]);//"d:\\DataSet\\name.tsv";
        String dest = String.valueOf(args[2]); //"d:\\DataSet\\imdb_data.txt"; // String.valueOf(args[1]);
        final int recordNum = Integer.valueOf(args[3]);
        final int gramNum = Integer.parseInt(args[4]); //Integer.parseInt(args[2]);
        final double ratio = Double.parseDouble(args[5]);
        Tools.imdbDataFormat(msrc, asrc, dest, gramNum, recordNum, ratio);
    }
}
