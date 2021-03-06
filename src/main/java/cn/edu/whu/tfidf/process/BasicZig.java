package cn.edu.whu.tfidf.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.edu.whu.btree.api.iSelect;
import cn.edu.whu.btree.common.KeyPair;
import cn.edu.whu.btree.core.Btree;
import cn.edu.whu.btree.core.BtreeCluster;
import cn.edu.whu.btree.serialize.iSerializable;
import cn.edu.whu.btree.utils.btree.BtreeClusterSp;
import cn.edu.whu.btree.utils.btree.BtreeClusterSp2;
import cn.edu.whu.tfidf.api.iIndex;
import cn.edu.whu.tfidf.storage.index.TfIdfHeadTuple;
import cn.edu.whu.tfidf.storage.index.TfPrefNodeTuple;
import cn.edu.whu.tfidf.storage.index.TfTokenSet;
import cn.edu.whu.tfidf.storage.reader.TfTokenPair;

import java.util.Set;

public class BasicZig implements iSelect {

    private Btree<Integer, TfTokenSet> recordBtree;
    private BtreeCluster<Integer, iSerializable> reverseBtree;
    private BtreeCluster<Integer, TfIdfHeadTuple> tokenBtree;
    private BtreeClusterSp2<KeyPair, Integer> lengthBtree;
    private BtreeClusterSp<KeyPair, Short> tfmaxBtree;
    private long candiSize, prefixTokens, totalTokens, upperLength, lowerLength, scanLength;
    private int qExtendedNum, rExtendedNum;
    private boolean useLengthIndex;

    public BasicZig(iIndex index, int qExtendedNum, int rExtendedNum, boolean useLengthIndex) {
        this.recordBtree = index.getRecordBtree();
        this.reverseBtree = index.getReverseBtree();
        this.tokenBtree = index.getTokenBtree();
        this.lengthBtree = index.getLengthBtree();
        this.tfmaxBtree = index.getTfmaxBtree();
        candiSize = prefixTokens = totalTokens = scanLength = upperLength = lowerLength = 0;
        this.qExtendedNum = qExtendedNum;
        this.rExtendedNum = rExtendedNum;
        this.useLengthIndex = useLengthIndex;
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<Integer> find(List<Integer> queryString, double threshold) {
        // TODO Auto-generated method stub

        Map<Integer, Short> qTfMap = new HashMap<Integer, Short>();
        List<Integer> query = new ArrayList<Integer>();

        Double lenq = 0.0;
        for (int token : queryString) {
            if (!qTfMap.containsKey(token)) {
                qTfMap.put(token, (short) 1);
                query.add(token);
            } else {
                qTfMap.put(token, (short) (qTfMap.get(token) + 1));
            }
        }
        query.sort((a, b) -> {
            TfIdfHeadTuple temp_a = tokenBtree.find(a);
            TfIdfHeadTuple temp_b = tokenBtree.find(b);
            return compareToken(a, b, temp_a, temp_b);
        });
        short tfmaxOfQuery = 1;
        short[] qsufTfmax = new short[query.size()];
        TfIdfHeadTuple[] headInfo = new TfIdfHeadTuple[query.size()];
        for (int i = query.size() - 1; i >= 0; i--) {
            int token = query.get(i);
            short q_tf = qTfMap.get(token);
            qsufTfmax[i] = tfmaxOfQuery;
            if (tfmaxOfQuery < q_tf) {
                tfmaxOfQuery = q_tf;
            }
            TfIdfHeadTuple temp = tokenBtree.find(token);
            lenq += temp.getIdf() * q_tf * q_tf * temp.getIdf();
            headInfo[i] = temp;
        }
        lenq = Math.sqrt(lenq);
        double suffix = threshold * threshold * lenq * lenq / tfmaxOfQuery;
        Double[] qsuf = new Double[query.size() + qExtendedNum + 1];

        double sufLengthAccu = 0.0, sufLapAccu = 0.0;
        //        int tokenNum = query.size();
        boolean lenflag = false;
        for (int i = query.size() - 1; i >= 0; i--) {
            TfIdfHeadTuple temp = headInfo[i];
            int token = query.get(i);
            sufLengthAccu += temp.getIdf() * qTfMap.get(token) * temp.getIdf() * qTfMap.get(token);
            qsuf[i] = sufLengthAccu;

            double tfidfEstimate = temp.getIdf() * temp.getIdf() * qTfMap.get(token) * temp.getTfmax();

            if (!lenflag) {

                if (sufLapAccu + tfidfEstimate >= suffix) {
                    //                    tokenNum = i + 1;
                    lenflag = true;
                }
                sufLapAccu += tfidfEstimate;
            }
            //            System.out.println("accu=" + sufLapAccu + " suffix=" + suffix + " tokenNum=" + tokenNum + " tfmax&idf "
            //                    + qTfMap.get(token) + ":" + temp.getTfmax() + "-" + temp.getIdf());
        }
        for (int i = 0; i <= qExtendedNum; i++) {
            qsuf[query.size() + qExtendedNum] = 0.0;
        }
        double qleft = threshold * lenq / tfmaxOfQuery;

        Map<Integer, Cpair> candidate = new HashMap<Integer, Cpair>();
        //        tokenNum += qExtendedNum;
        //        if (tokenNum > query.size()) {
        //            tokenNum = query.size();
        //        }
        //        System.out.println("tokenNum =" + tokenNum + "     total = " + qDistinct.size());

        Iterator<Entry<Integer, Cpair>> it = null;
        short[] TfMax = new short[query.size()];
        for (int i = 0; i < TfMax.length; i++) {
            TfMax[i] = headInfo[i].getTfmax();
        }
        double maxlenC = 0.0, maxlenS = 0.0, qUpper, minlenC = Double.MAX_VALUE;
        for (int i = 0; i < query.size(); i++) {
            maxlenS += headInfo[i].getIdf() * headInfo[i].getIdf() * qTfMap.get(query.get(i)) * headInfo[i].getTfmax();
        }
        maxlenS = maxlenS / lenq / threshold;
        qUpper = maxlenS;
        double qright = maxlenS;
        //        System.out.println("qleft = " + qleft + "     qright = " + qright + " qUpper = " + qUpper);
        Set<Integer> resultSet = new HashSet<Integer>();
        for (int i = 0; i < query.size(); i++) {
            if (i >= query.size()) {
                break;
            }

            int token = query.get(i);
            double idf = headInfo[i].getIdf();
            short qtf = qTfMap.get(token);

            TfPrefNodeTuple curTuple = null;
            if (useLengthIndex) {
                Integer addr = lengthBtree.find(new KeyPair(token, qleft));
                if (addr == null) {
                    curTuple = (TfPrefNodeTuple) reverseBtree.find(headInfo[i].getHead());
                } else {
                    curTuple = (TfPrefNodeTuple) reverseBtree.find(addr);
                }
            } else {
                curTuple = (TfPrefNodeTuple) reverseBtree.find(headInfo[i].getHead());
            }
            while (curTuple != null) {

                if (curTuple.pair.getLen() >= qleft) {
                    break;
                }
                lowerLength++;
                scanLength++;
                curTuple = (TfPrefNodeTuple) reverseBtree.find(curTuple.next);
            }
            while (curTuple != null) {
                scanLength++;
                double totalUpperBound = qright < qUpper ? qright : qUpper;

                if (curTuple.pair.getLen() > totalUpperBound) {


                    //                    while (curTuple != null) {
                    //                        curTuple = (TfPrefNodeTuple) reverseBtree.find(curTuple.next);
                    //                        totalLength++;
                    //                    }
                    break;
                }

                int id = curTuple.pair.getId();

                if (!resultSet.contains(id)) {
                    Cpair cpair;
                    if (candidate.containsKey(id)) {
                        cpair = candidate.get(id);
                    } else {
                        cpair = new Cpair(curTuple.pair.getLen(), query.size());
                    }
                    double rLap = curTuple.pair.getLen() * curTuple.pair.getLen() - curTuple.pair.getAccu();
                    double qLap = qsuf[i];
                    double minsuf = Math.sqrt(rLap * qLap);

                    if (cpair.accu + minsuf >= cpair.len * lenq * threshold) {
                        cpair.rLap = rLap - idf * idf * curTuple.pair.getTf() * curTuple.pair.getTf();
                        if (cpair.rLap < 0) {
                            cpair.rLap = 0;
                        }
                        cpair.token = token;
                        cpair.tokenEx = new TfTokenPair[rExtendedNum];
                        System.arraycopy(curTuple.pair.getTokenEx(), 0, cpair.tokenEx, 0, rExtendedNum);
                        cpair.accu += idf * idf * qtf * curTuple.pair.getTf();
                        cpair.tf[i] = curTuple.pair.getTf();
                        if (cpair.accu >= cpair.len * lenq * threshold) {
                            resultSet.add(id);
                            if (candidate.containsKey(id)) {
                                candidate.remove(id);
                            }
                        } else if (!candidate.containsKey(id) && cpair.len <= maxlenS) {
                            candidate.put(id, cpair);
                        }
                    } else {
                        if (candidate.containsKey(id)) {
                            candidate.remove(id);
                        }
                    }
                }
                if (curTuple.next == -1) {
                    break;
                }
                curTuple = (TfPrefNodeTuple) reverseBtree.find(curTuple.next);
            }


        }

        return new ArrayList<Integer>(resultSet);
    }

    private int compareToken(int tokenq, int tokenr, TfIdfHeadTuple qTuple, TfIdfHeadTuple rTuple) {
        int cmp = Double.compare(rTuple.getIdf(), qTuple.getIdf());
        if (cmp == 0) {
            int cmp2 = Double.compare(rTuple.getTfmax(), qTuple.getTfmax());
            if (cmp2 == 0) {
                return Integer.compare(tokenq, tokenr);
            } else {
                return cmp2;
            }
        } else {
            return cmp;
        }
    }

    public long getCandiSize() {
        return candiSize;
    }

    public void setCandiSize(long candiSize) {
        this.candiSize = candiSize;
    }

    public void setqExtendedNum(int qExtendedNum) {
        this.qExtendedNum = qExtendedNum;
    }

    private class Cpair {
        double accu, rLap, len;
        TfTokenPair tokenEx[];
        int token;
        short[] tf;

        public Cpair(double len, int querySize) {
            this.accu = 0.0;
            this.len = len;
            this.tf = new short[querySize];
            Arrays.fill(tf, (short) 0);
        }

        @Override
        public String toString() {
            return "Cpair [len=" + len + ", accu=" + accu + ", rLap=" + rLap + "]";
        }
    }

    public long getPrefixTokens() {
        return prefixTokens;
    }

    public long getTotalTokens() {
        return totalTokens;
    }

    public long getScanLength() {
        return scanLength;
    }

    public long getTotalLength() {
        return upperLength + scanLength;
    }

    public long getLowerLength() {
        return lowerLength;
    }

    public long getUpperLength() {
        return upperLength;
    }
}
