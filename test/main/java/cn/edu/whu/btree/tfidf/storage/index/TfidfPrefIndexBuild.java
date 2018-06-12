package cn.edu.whu.btree.tfidf.storage.index;

import cn.edu.whu.btree.analyzer.WordTokenizer;
import cn.edu.whu.btree.api.iTokenizer;
import cn.edu.whu.btree.common.TfidfConfig;
import cn.edu.whu.tfidf.api.iIndex;
import cn.edu.whu.tfidf.storage.index.TfidfPrefIndex;
import cn.edu.whu.tfidf.storage.reader.TfIdfDataReader;

public class TfidfPrefIndexBuild {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length != 26) {
            System.out.println(
                    "recordNum dataPath indexPath tokenCacheSize tokenBlockSize tokenNodeNumOfBlock tokenNodeCacheSize reverseCacheSize reverseBlockSize reverseNodeNumOfBlock reverseNodeCacheSize recordCacheSize recordDataCacheSize recordBlockSize recordNodeNumOfBlock recordNodeCacheSize lengthCacheSize lengthBlockSize lengthNodeNumOfBlock lengthNodeCacheSize tfmaxCacheSize tfmaxBlockSize tfmaxNodeNumOfBlock tfmaxNodeCacheSize rExtendedNum lengthIndexGap");
            System.exit(0);
        }
        System.out.println("ExPrefIndexBuild start");
        // Total comments for tfidf compared to idf: Except for tfmax*, all other parameters are the same as the structures in tf.
        int pos = 0;
        final int recordNum = Integer.valueOf(args[pos++]);
        final String dataPath = args[pos++];
        final String indexPath = args[pos++]; //"g:\\bplustree\\data.txt";// index position
        final int tokenCacheSize = Integer.valueOf(args[pos++]); // 100; 
        final int tokenBlockSize = Integer.valueOf(args[pos++]);
        final int tokenNodeNumOfBlock = Integer.valueOf(args[pos++]);
        final int tokenNodeCacheSize = Integer.valueOf(args[pos++]);
        final int reverseCacheSize = Integer.valueOf(args[pos++]);
        final int reverseBlockSize = Integer.valueOf(args[pos++]);
        final int reverseNodeNumOfBlock = Integer.valueOf(args[pos++]);
        final int reverseNodeCacheSize = Integer.valueOf(args[pos++]);
        final int recordCacheSize = Integer.valueOf(args[pos++]);
        final int recordDataCacheSize = Integer.valueOf(args[pos++]);
        final int recordBlockSize = Integer.valueOf(args[pos++]);
        final int recordNodeNumOfBlock = Integer.valueOf(args[pos++]);
        final int recordNodeCacheSize = Integer.valueOf(args[pos++]);
        final int lengthCacheSize = Integer.valueOf(args[pos++]);
        final int lengthBlockSize = Integer.valueOf(args[pos++]);
        final int lengthNodeNumOfBlock = Integer.valueOf(args[pos++]);
        final int lengthNodeCacheSize = Integer.valueOf(args[pos++]);
        final int tfmaxCacheSize = Integer.valueOf(args[pos++]); // Cache number for tfmax index.
        final int tfmaxBlockSize = Integer.valueOf(args[pos++]);
        final int tfmaxNodeNumOfBlock = Integer.valueOf(args[pos++]); // Nodes per Block, set as 1 by default.
        final int tfmaxNodeCacheSize = Integer.valueOf(args[pos++]); // Cache number for all of the nodes in tfmax index, for avoiding deserialization.
        final int rExtendedNum = Integer.valueOf(args[pos++]);
        final int lengthIndexGap = Integer.parseInt(args[pos++]);
        final float cachefac = 0.6f;
        TfidfConfig conf = new TfidfConfig(indexPath, tokenCacheSize, tokenBlockSize, tokenNodeNumOfBlock, reverseCacheSize,
                reverseBlockSize, reverseNodeNumOfBlock, recordCacheSize, recordDataCacheSize, recordBlockSize,
                recordNodeNumOfBlock,
                lengthCacheSize, lengthBlockSize, lengthNodeNumOfBlock, tfmaxCacheSize, tfmaxBlockSize,
                tfmaxNodeNumOfBlock, cachefac);
        conf.setTokenNodeCacheSize(tokenNodeCacheSize);
        conf.setReverseNodeCacheSize(reverseNodeCacheSize);
        conf.setRecordNodeCacheSize(recordNodeCacheSize);
        conf.setLengthNodeCacheSize(lengthNodeCacheSize);
        conf.setTfmaxNodeCacheSize(tfmaxNodeCacheSize);
        System.out.println("RecordNum " + recordNum);
        iTokenizer tokenizer = new WordTokenizer();
        TfIdfDataReader reader = new TfIdfDataReader(dataPath, recordNum, tokenizer);
        long begin = System.currentTimeMillis();
        iIndex index = new TfidfPrefIndex(reader, conf,
                rExtendedNum, lengthIndexGap);
        long end = System.currentTimeMillis();
        System.out.println("TokenCount " + index.getTokenBtree().getSize());
        System.out.println("Token: KeyNum = " + index.getTokenBtree().getKeyNum() + " Height = "
                + index.getTokenBtree().getHeight() + " InterNum = "
                + index.getTokenBtree().getInterNum() + " LeafNum = " + index.getTokenBtree().getLeafNum()
                + " InterSize = " + index.getTokenBtree().getInternsize() + " LeafSize = "
                + index.getTokenBtree().getLeafnsize());
        System.out.println("Reverse: KeyNum = " + index.getReverseBtree().getKeyNum() + " Height = "
                + index.getReverseBtree().getHeight() + " InterNum = "
                + index.getReverseBtree().getInterNum() + " LeafNum = " + index.getReverseBtree().getLeafNum()
                + " InterSize = " + index.getReverseBtree().getInternsize() + " LeafSize = "
                + index.getReverseBtree().getLeafnsize());
        System.out.println("Record: KeyNum = " + index.getRecordBtree().getKeyNum() + " Height = "
                + index.getRecordBtree().getHeight() + " InterNum = "
                + index.getRecordBtree().getInterNum() + " LeafNum = " + index.getRecordBtree().getLeafNum()
                + " InterSize = " + index.getRecordBtree().getInternsize() + " LeafSize = "
                + index.getRecordBtree().getLeafnsize() + " DataBlockNum " + index.getRecordBtree().getDataBlockNum());
        System.out.println("Length: KeyNum = " + index.getLengthBtree().getKeyNum() + " Height = "
                + index.getLengthBtree().getHeight() + " InterNum = "
                + index.getLengthBtree().getInterNum() + " LeafNum = " + index.getLengthBtree().getLeafNum()
                + " InterSize = " + index.getLengthBtree().getInternsize() + " LeafSize = "
                + index.getLengthBtree().getLeafnsize());
        //        System.out.println("Tfmax: KeyNum = " + index.getTfmaxBtree().getKeyNum() + " Height = "
        //                + index.getTfmaxBtree().getHeight() + " InterNum = " + index.getTfmaxBtree().getInterNum()
        //                + " LeafNum = " + index.getTfmaxBtree().getLeafNum() + " InterSize = "
        //                + index.getTfmaxBtree().getInternsize() + " LeafSize = " + index.getTfmaxBtree().getLeafnsize());

        System.out.println("index build time " + (end - begin));
        index.close();
    }

}
