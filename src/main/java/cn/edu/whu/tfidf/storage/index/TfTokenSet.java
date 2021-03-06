package cn.edu.whu.tfidf.storage.index;

import java.util.ArrayList;
import java.util.List;

import cn.edu.whu.btree.common.Utils;
import cn.edu.whu.btree.serialize.iSerializable;
import cn.edu.whu.tfidf.storage.reader.TfTokenPair;

public class TfTokenSet implements iSerializable {

    List<TfTokenPair> record;

    public TfTokenSet() {
    }

    public TfTokenSet(List<TfTokenPair> record) {
        this.record = record;
    }

    @Override
    public void deseriablize(byte[] data) {
        // TODO Auto-generated method stub
        int size = data.length / 6;
        record = new ArrayList<TfTokenPair>();
        for (int i = 0; i < size; i++) {
            record.add(new TfTokenPair(Utils.getInt(data, i * 6), Utils.getShort(data, i * 6 + 4)));
        }
    }

    @Override
    public byte[] serialize() {
        // TODO Auto-generated method stub
        byte[] data = new byte[record.size() * 6];
        int pos = 0;
        for (int i = 0; i < record.size(); i++) {
            Utils.getBytes4(record.get(i).getTid(), data, pos);
            pos += 4;
            Utils.getBytes2(record.get(i).getTf(), data, pos);
            pos += 2;
        }
        return data;
    }

    public List<TfTokenPair> getRecord() {
        return record;
    }
}
