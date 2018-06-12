package cn.edu.whu.tfidf.api;

import java.util.Map;

import cn.edu.whu.btree.common.KeyPair;
import cn.edu.whu.btree.core.Btree;
import cn.edu.whu.btree.core.BtreeCluster;
import cn.edu.whu.btree.serialize.iSerializable;
import cn.edu.whu.btree.utils.btree.BtreeClusterSp;
import cn.edu.whu.btree.utils.btree.BtreeClusterSp2;
import cn.edu.whu.tfidf.storage.index.TfIdfHeadTuple;
import cn.edu.whu.tfidf.storage.index.TfTokenSet;

public interface iIndex {

    //    protected BtreeCluster<Integer, iNodeTuple> reverseBtree;
    //    protected BtreeCluster<Integer, HeadTuple> tokenBtree;

    public BtreeCluster<Integer, iSerializable> getReverseBtree();

    public BtreeCluster<Integer, TfIdfHeadTuple> getTokenBtree();

    public Btree<Integer, TfTokenSet> getRecordBtree();

    public BtreeClusterSp2<KeyPair, Integer> getLengthBtree();

    public BtreeClusterSp<KeyPair, Short> getTfmaxBtree();

    public abstract void printReverseList(Map<Integer, String> idToTokenMap, Map<Integer, String> recordMap);

    public abstract void close();

}
