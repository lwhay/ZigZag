package cn.edu.whu.btree.tfidf.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.edu.whu.btree.api.iSelect;
import cn.edu.whu.tfidf.api.iIndex;
import cn.edu.whu.tfidf.process.TfidfPrefForward;
import cn.edu.whu.tfidf.storage.index.TfidfPrefIndex;

public class TfidfPrefForwardTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 7) {
            System.out.println("indexPath queryPath threshold qExtendedNum rExtendedNum useLengthIndex allMemory");
            System.exit(0);
        }
        String indexPath = args[0].trim(); //"g:\\simfind\\imdb\\pref\\index"; index position
        String queryPath = args[1].trim(); //"g:\\simfind\\imdb\\queryList";
        double threshold = Double.valueOf(args[2]);
        final int qExtendedNum = Integer.valueOf(args[3]);
        final int rExtendedNum = Integer.valueOf(args[4]);
        final boolean useLengthIndex = Integer.valueOf(args[5]) == 1 ? true : false;
        final boolean allMemory = Integer.valueOf(args[6]) == 1 ? true : false;
        iIndex index = TfidfPrefIndex.open(indexPath, allMemory);
        //        int[] ex = { 0, 1, 2 };
        //        for (int rExtendedNum : ex) {

        System.out.println(
                "token:" + index.getTokenBtree().getCacheSize() + "  reverse:" + index.getReverseBtree().getCacheSize()
                        + "  record:" + ((TfidfPrefIndex) index).getRecordBtree().getCacheSize() + "  length:"
                        + ((TfidfPrefIndex) index).getLengthBtree().getCacheSize() + " tfmax:"
                        + ((TfidfPrefIndex) index).getTfmaxBtree().getCacheSize());
        iSelect select = new TfidfPrefForward(index, qExtendedNum, rExtendedNum, useLengthIndex);
        List<List<Integer>> queryList = new ArrayList<List<Integer>>();
        BufferedReader queryReader;
        try {
                queryReader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(new File(queryPath)), "utf-8"));
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
        int findTimes = queryList.size();
        List<Integer> resultList = new ArrayList<Integer>();
            System.out.println("threshold=" + threshold + " rExtendedNum=" + rExtendedNum
                + " findTimes=" + findTimes);
        long begin = System.currentTimeMillis();
        //        for (int i = 0; i < findTimes; i++) {
        //            resultList = select.find(queryListPre.get(i), threshold);
        //        }
        long end = System.currentTimeMillis();
        //        System.out.println("prefind time " + (end - begin));
        int resultCount = 0;
        ((TfidfPrefForward) select).setCandiSize(0);
        begin = System.currentTimeMillis();
            for (int i = 0; i < findTimes; i++) {
            //            System.out.print("query: ");
            //            queryList.get(i).forEach(token -> System.out.print(token + " "));
            //            System.out.println();
                resultList = select.find(queryList.get(i), threshold);
                resultCount += resultList.size();
                //                System.out.println(resultList.size());
                //                Collections.sort(resultList);
                //                for (int re : resultList) {
                //                    System.out.println(re);
                //                }
        }
        end = System.currentTimeMillis();
        System.out.println("candidate : " + ((TfidfPrefForward) select).getCandiSize() * 1.0 / findTimes);

        System.out.println("false prefix : " + ((TfidfPrefForward) select).getPrefixTokens() * 1.0 / findTimes + " : "
                + ((TfidfPrefForward) select).getTotalTokens() * 1.0 / findTimes);
        System.out.println("actual prefix : " + ((TfidfPrefForward) select).getActualPrefix() * 1.0 / findTimes + " : "
                + ((TfidfPrefForward) select).getTotalTokens() * 1.0 / findTimes);
        System.out.println("length : " + ((TfidfPrefForward) select).getScanLength() * 1.0 / findTimes + " : "
                + ((TfidfPrefForward) select).getTotalLength() * 1.0 / findTimes + "  details : "
                + ((TfidfPrefForward) select).getLowerLength() * 1.0 / findTimes + " : "
                + ((TfidfPrefForward) select).getUpperLength() * 1.0 / findTimes);
        System.out.println("token=" + index.getTokenBtree().getReadNo() * 1.0 / findTimes + "  reverse="
                + index.getReverseBtree().getReadNo() * 1.0 / findTimes + "  record="
                + ((TfidfPrefIndex) index).getRecordBtree().getReadNo() * 1.0 / findTimes + "  length="
                + ((TfidfPrefIndex) index).getLengthBtree().getReadNo() * 1.0 / findTimes + "  tfmax="
                + ((TfidfPrefIndex) index).getTfmaxBtree().getReadNo() * 1.0 / findTimes);
        System.out.println("result : " + resultCount * 1.0 / findTimes);
        System.out.println("time " + (end - begin) * 1.0 / findTimes);
        }
    //    }

}
