/**
 * 
 */
package cn.edu.whu.btree.api;

import java.util.List;

/**
 * @author iclab
 *
 */
public interface ITopK {
    public List<Integer> find(List<Integer> query, int K);

    public List<Integer> adaptiveFind(List<Integer> query, int K);
}
