package cn.edu.whu.btree.tfidf.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.whu.btree.api.iSelect;
import cn.edu.whu.tfidf.api.iIndex;
import cn.edu.whu.tfidf.process.RecordScan;
import cn.edu.whu.tfidf.storage.index.TfidfPrefIndex;

public class TfidfRecordScanTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 4) {
            System.out.println("indexPath queryPath threshold allMemory");
            System.exit(0);
        }
        String indexPath = args[0].trim(); //"g:\\simfind\\imdb\\pref\\index"; index position
        String queryPath = args[1].trim(); //"g:\\simfind\\imdb\\queryList";
        double threshold = Double.valueOf(args[2]);
        final boolean allMemory = Integer.valueOf(args[3]) == 1 ? true : false;
        iIndex index = TfidfPrefIndex.open(indexPath, allMemory);
        System.out.println(
                "token:" + index.getTokenBtree().getCacheSize() + "  reverse:" + index.getReverseBtree().getCacheSize()
                        + "  record:" + ((TfidfPrefIndex) index).getRecordBtree().getCacheSize() + "  length:"
                        + ((TfidfPrefIndex) index).getLengthBtree().getCacheSize());
        iSelect select = new RecordScan(index);
        List<List<Integer>> queryList = new ArrayList<List<Integer>>();
        BufferedReader queryReader;
        try {
            queryReader = new BufferedReader(new FileReader(queryPath));
            String line = null;
            while ((line = queryReader.readLine()) != null) {
                String[] temp = line.split(" ");
                List<Integer> qtemp = new ArrayList<Integer>();
                for (int i = 0; i < temp.length; i++) {
                    qtemp.add(Integer.valueOf(temp[i].trim()));
                }
                queryList.add(qtemp);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //        List<List<Integer>> queryListPre = new ArrayList<List<Integer>>();
        //        try {
        //            queryReader = new BufferedReader(
        //                    new FileReader(
        //                            queryPath.substring(0, queryPath.lastIndexOf(File.separator) + 1) + "query.txt-pre"));
        //            String line = null;
        //            while ((line = queryReader.readLine()) != null) {
        //                String[] temp = line.split(" ");
        //                List<Integer> qtemp = new ArrayList<Integer>();
        //                for (int i = 0; i < temp.length; i++) {
        //                    qtemp.add(Integer.valueOf(temp[i].trim()));
        //                }
        //                queryListPre.add(qtemp);
        //            }
        //        } catch (IOException e) {
        //            // TODO Auto-generated catch block
        //            e.printStackTrace();
        //        }
        int findTimes = queryList.size();
        List<Integer> resultList = new ArrayList<Integer>();
        System.out.println("threshold=" + threshold + " findTimes=" + findTimes);
        long begin = System.currentTimeMillis();
        //        for (int i = 0; i < findTimes; i++) {
        //            resultList = select.find(queryListPre.get(i), threshold);
        //        }
        long end = System.currentTimeMillis();
        //        System.out.println("prefind time " + (end - begin));
        int resultCount = 0;
        begin = System.currentTimeMillis();
        for (int i = 0; i < findTimes; i++) {
            // System.out.println(reader.getQueryList().get(i));
            long tempstart = System.currentTimeMillis();
            resultList = select.find(queryList.get(i), threshold);
            resultCount += resultList.size();
            //            System.out.println((System.currentTimeMillis() - tempstart) + "  " + i + ":" + threshold + " resultSize="
            //                    + resultList.size() + "\n");
        }
        end = System.currentTimeMillis();
        System.out.println("token=" + index.getTokenBtree().getReadNo() * 1.0 / findTimes + "  reverse="
                + index.getReverseBtree().getReadNo() * 1.0 / findTimes + "  record="
                + ((TfidfPrefIndex) index).getRecordBtree().getReadNo() * 1.0 / findTimes + "  length="
                + ((TfidfPrefIndex) index).getLengthBtree().getReadNo() * 1.0 / findTimes);
        System.out.println("avg result : " + resultCount * 1.0 / findTimes);
        System.out.println("time " + (end - begin) * 1.0 / findTimes);
    }

}
