package cn.edu.whu.btree.preprocess;

import cn.edu.whu.btree.common.Tools;

public class PubmedDataFormat {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String src = "h:\\pubmed_dataset";
        String dest = "g:\\pubmed_2017_title.txt";
        Tools.pubmedDataFormat(src, dest);
    }

}
