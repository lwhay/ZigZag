package cn.edu.whu.btree.api;

import java.util.List;

public interface iSelect {

    public abstract List<Integer> find(List<Integer> query, double threshold);

}
