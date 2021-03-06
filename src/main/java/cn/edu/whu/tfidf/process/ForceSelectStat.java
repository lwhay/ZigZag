package cn.edu.whu.tfidf.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.whu.tfidf.api.iDataReader;
import cn.edu.whu.tfidf.storage.reader.TfTokenPair;

public class ForceSelectStat {

    private List<List<TfTokenPair>> recordList;
    private Map<Integer, Double> idfMap;

    public ForceSelectStat(iDataReader reader) {
        this.idfMap = reader.getIdfMap();
        this.recordList = reader.getRecordList();
        // TODO Auto-generated constructor stub
    }

    public List<ResultPair> find(List<Integer> query, double threshold) {
        // TODO Auto-generated method stub
        List<ResultPair> resultList = new ArrayList<ResultPair>();

        Map<Integer, Integer> qTfMap = new HashMap<Integer, Integer>();
        Set<Integer> qSet = new HashSet<Integer>();
        for (int token : query) {
            if (!qTfMap.containsKey(token)) {
                qTfMap.put(token, 1);
                qSet.add(token);
            } else {
                qTfMap.put(token, qTfMap.get(token) + 1);
            }
        }
        double lenq = 0.0;
        for (Map.Entry<Integer, Integer> entry : qTfMap.entrySet()) {
            double idf = idfMap.get(entry.getKey());
            lenq += idf * idf * entry.getValue() * entry.getValue();
        }
        lenq = Math.sqrt(lenq);
        for (int i = 0; i < recordList.size(); i++) {
            List<TfTokenPair> record = recordList.get(i);
            double lenr = 0.0;
            double simTotal = 0.0;
            for (int j = 0; j < record.size(); j++) {
                TfTokenPair pair = (TfTokenPair) record.get(j);
                double idf = idfMap.get(pair.getTid());
                lenr += pair.getTf() * idf * pair.getTf() * idf;
                if (qSet.contains(pair.getTid())) {
                    simTotal += idf * idf * pair.getTf() * qTfMap.get(pair.getTid());
                }
            }
            lenr = Math.sqrt(lenr);

            //            if (i == 408952) {
            //                System.out.println("candidate " + i + " " + simTotal / (lenq * lenr));
            //                record.forEach(pair -> {
            //                    System.out
            //                            .println("[" + pair.getTid() + "-" + pair.getTf() + "-" + idfMap.get(pair.getTid()) + "]");
            //                });
            //                System.out.println();
            //            }
            //
            //            if (i == 408952) {
            //                System.out.println(threshold + " | " + lenr + " | " + lenq);
            //
            //                System.out.println(simTotal + " vs " + threshold * lenq * lenr);
            //            }
            if (simTotal >= threshold * lenq * lenr) {
                resultList.add(new ResultPair(i, simTotal / (lenq * lenr)));

                //                if (i == 408952) {
                //                    //                System.out.println("resultId=" + i + " simlarity=" + simTotal * simTotal / (lenq * lenr));
                //                    System.out.println("resultId " + i + " " + String.format("%.5f", simTotal) + " : "
                //                        + String.format("%.2f", Math.sqrt(threshold * lenq * lenr)));
                //                }
                //                System.out.println("result " + i + " " + record);
            }


        }

        //        System.out.println("query " + query);
        //        System.out.println("query tf " + qTfMap);
        //        query.forEach(e -> System.out.print(idfMap.get(e) + " "));
        //        System.out.println();

        //        resultList.stream().sorted().forEach(e -> System.out.println("result id " + e));
        return resultList;
    }
}

class ResultPair implements Comparable<ResultPair> {
    int id;
    double similarity;

    public ResultPair(int id, double similarity) {
        this.id = id;
        this.similarity = similarity;
    }

    @Override
    public int compareTo(ResultPair arg0) {
        // TODO Auto-generated method stub
        return Double.compare(arg0.similarity, this.similarity);
    }
}

