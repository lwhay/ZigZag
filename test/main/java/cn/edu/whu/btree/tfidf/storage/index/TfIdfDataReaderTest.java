package cn.edu.whu.btree.tfidf.storage.index;

import cn.edu.whu.btree.analyzer.WordTokenizer;
import cn.edu.whu.btree.api.iTokenizer;
import cn.edu.whu.tfidf.storage.reader.TfIdfDataReader;

public class TfIdfDataReaderTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 2) {
            System.out.println("path recordNum");
            System.exit(1);
        }
        final String path = String.valueOf(args[0]);//"d:\\simfind\\stanford_data.txt";
        final int maxLine = Integer.valueOf(args[1]);//10000;
        iTokenizer tokenizer = new WordTokenizer();
        TfIdfDataReader reader = new TfIdfDataReader(path, maxLine, tokenizer);
        //reader.printTfMap();
        System.out.println(
                reader.getFreqMap().size() + " " + reader.getRecordList().size() + " " + reader.getTfMaxMap().size());
        //        reader.printTfMinMap();
        //        System.out.println("max");
        //        reader.printTfMaxMap();
        //        reader.printRecordList();
        //        reader.printFreqMap();
        //        System.out.println(reader.getAvglen());
        //        reader.printTfmaxToRecordListMap();
        System.out.println("111111");
    }

}
