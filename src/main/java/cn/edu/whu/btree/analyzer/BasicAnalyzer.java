package cn.edu.whu.btree.analyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.whu.btree.api.Analyzer;
import cn.edu.whu.btree.api.iTokenizer;

public class BasicAnalyzer extends Analyzer {

    public BasicAnalyzer(Map<String, Integer> tokenToIdMap, iTokenizer tokenizer) {
        super(tokenToIdMap, tokenizer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Integer> analyze(String query) {
		String[] token = tokenizer.tokenize(query);
		List<Integer> q = new ArrayList<Integer>();
		Set<String> tmp = new HashSet<String>();
		for (int i = 0; i < token.length; i++) {
			if (tmp.contains(token[i])) {
				continue;
			}
            q.add(tokenToIdMap.get(token[i]));
			tmp.add(token[i]);
		}
		return q;
	}

}
