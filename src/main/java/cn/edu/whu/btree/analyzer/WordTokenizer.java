package cn.edu.whu.btree.analyzer;

import cn.edu.whu.btree.api.iTokenizer;

public class WordTokenizer implements iTokenizer {

	@Override
	public String[] tokenize(String text) {
		// TODO Auto-generated method stub
		return text.split(" ");
	}

}
