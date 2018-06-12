package cn.edu.whu.idf.api;

import java.util.Map;

import cn.edu.whu.btree.common.KeyPair;
import cn.edu.whu.btree.core.Btree;
import cn.edu.whu.btree.core.BtreeCluster;
import cn.edu.whu.btree.utils.btree.BtreeClusterSp2;
import cn.edu.whu.idf.storage.index.IdfHeadTuple;
import cn.edu.whu.idf.storage.index.prefix.IdfTokenSet;
import cn.edu.whu.idf.storage.index.prefix.PrefNodeTuple;

public interface iIndex {

    //    protected BtreeCluster<Integer, iNodeTuple> reverseBtree;
    //    protected BtreeCluster<Integer, HeadTuple> tokenBtree;

    public BtreeCluster<Integer, PrefNodeTuple> getReverseBtree();

    public BtreeCluster<Integer, IdfHeadTuple> getTokenBtree();

    public Btree<Integer, IdfTokenSet> getRecordBtree();

    public BtreeClusterSp2<KeyPair, Integer> getLengthBtree();

	public abstract void printReverseList(Map<Integer, String> idToTokenMap, Map<Integer, String> recordMap);

    public abstract void close();

}
