package cn.chendd.ansj.enums;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;

/**
 * 分词实现类
 *
 * @author chendd
 * @date 2020/8/8 9:55
 */
public enum EnumAnalysis implements IAnsjAnalysis {

    /**
     * 基本分词：最基本的分词.词语颗粒度最非常小的
     */
    BASE() {
        @Override
        public Result instance(String text) {
            return BaseAnalysis.parse(text);
        }
    },
    /**
     * 精准分词：在易用性,稳定性.准确性.以及分词效率上.都取得了一个不错的平衡
     */
    DIC() {
        @Override
        public Result instance(String text) {
            return DicAnalysis.parse(text);
        }
    },
    /**
     * nlp分词：语法实体名抽取.未登录词整理.只要是对文本进行发现分析等工作
     */
    NLP() {
        @Override
        public Result instance(String text) {
            return NlpAnalysis.parse(text);
        }
    },
    /**
     * 面向索引分词：故名思议就是适合在lucene等文本检索中用到的分词
     */
    INDEX() {
        @Override
        public Result instance(String text) {
            return IndexAnalysis.parse(text);
        }
    },
    /**
     * 默认分词
     */
    DEFAULT {
        @Override
        public Result instance(String text) {
            return NLP.instance(text);
        }
    };

}