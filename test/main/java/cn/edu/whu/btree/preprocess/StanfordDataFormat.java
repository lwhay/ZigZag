package cn.edu.whu.btree.preprocess;

import cn.edu.whu.btree.common.Tools;

public class StanfordDataFormat {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 3) {
            System.out.println("src dest count");
            System.exit(1);
        }
        String src = String.valueOf(args[0]); //"g:\\Ѹ������\\quotes_2008-08.txt";
        String dest = String.valueOf(args[1]); //"g:\\Ѹ������\\stanford_2008-08.txt";
        final int recordNum = Integer.parseInt(args[2]);
        Tools.stanfordDataFormat(src, dest, recordNum);
    }

}
