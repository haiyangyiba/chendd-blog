package cn.chendd.ansj;

import cn.chendd.ansj.enums.EnumAnalysis;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.app.summary.SummaryComputer;
import org.ansj.app.summary.pojo.Summary;
import org.ansj.domain.Result;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.nlpcn.commons.lang.jianfan.JianFan;

import java.util.List;

/**
 * Ansj分词工具类
 *
 * @author chendd
 * @date 2020/8/8 9:43
 */
public class Ansj {

    private Ansj() {

    }

    public static Ansj newInstance() {
        return new Ansj();
    }

    /**
     * 根据标题和内容分析文章摘要
     *
     * @param title   标题
     * @param content 内容
     * @return 摘要
     */
    public String getSummary(String title, String content) {
        SummaryComputer summaryComputer = new SummaryComputer(title, content);
        Summary summary = summaryComputer.toSummary();
        return summary.getSummary();
    }

    /**
     * @param text 文本
     * @return 繁转简
     */
    public String fan2jian(String text) {
        return JianFan.f2j(text);
    }

    /**
     * @param text 文本
     * @return 简转繁
     */
    public String jian2fan(String text) {
        return JianFan.j2f(text);
    }

    public List<Keyword> keyWords(String content) {
        KeyWordComputer<Analysis> keyWord = new KeyWordComputer<>();
        keyWord.setAnalysisType(new NlpAnalysis());
        List<Keyword> list = keyWord.computeArticleTfidf(content);
        return list;
    }

    public List<Keyword> keyWords(String title , String content , Integer count  , Analysis analysis) {
        KeyWordComputer<Analysis> keyWord = new KeyWordComputer<>(count , analysis);
        List<Keyword> list = keyWord.computeArticleTfidf(title, content);
        return list;
    }

    public List<Keyword> keyWords(String title , String content) {
        KeyWordComputer<NlpAnalysis> keyWord = new KeyWordComputer<>(5);
        List<Keyword> list = keyWord.computeArticleTfidf(title, content);
        return list;
    }

    public List<Keyword> keyWords(String content , Integer count) {
        KeyWordComputer<NlpAnalysis> keyWord = new KeyWordComputer<>();
        keyWord.setAnalysisType(new NlpAnalysis());
        List<Keyword> list = keyWord.computeArticleTfidf(content);
        return list;
    }

    public Result keyWords(EnumAnalysis analysis , String text) {
        return analysis.instance(text);
    }

}
