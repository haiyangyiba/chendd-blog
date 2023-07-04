package cn.chendd.ansj.test;

import cn.chendd.ansj.Ansj;
import cn.chendd.ansj.enums.EnumAnalysis;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Ansj测试类
 *
 * @author chendd
 * @date 2020/8/8 19:09
 */
public class AnsjTest {

    @Test
    public void test() {
        String title = "开发微信小程序";
        String text = "微信小程序上面有许许多多的app应用。";
        System.out.println("base:" + EnumAnalysis.BASE.instance(text));
        System.out.println("dic:" + EnumAnalysis.DIC.instance(text));
        System.out.println("nlp:" + EnumAnalysis.NLP.instance(text));
        System.out.println("index:" + EnumAnalysis.INDEX.instance(text));
        System.out.println("default:" + EnumAnalysis.DEFAULT.instance(text));
        System.out.println("EnumAnalysis.main");
        System.out.println("根据内容提取5个关键词：" + Ansj.newInstance().keyWords(text));
        System.out.println("根据标题和内容提取5个关键词：" + Ansj.newInstance().keyWords(title , text));
        System.out.println("简转繁：" + Ansj.newInstance().jian2fan("陈冬冬"));
        System.out.println("繁转简：" + Ansj.newInstance().fan2jian("陳冬冬"));
        System.out.println("标题和内容分析摘要：" + Ansj.newInstance().getSummary(title , text));
        System.out.println("----");
        Result result = EnumAnalysis.NLP.instance(text);
        List<Term> titleList = result.getTerms().stream().filter(item -> item.getName().length() > 1).collect(Collectors.toList());
        System.out.println(titleList);

        System.out.println("提取摘要：" + Ansj.newInstance().getSummary("毛泽东的事迹" , "毛泽东（1893年12月26日-1976年9月9日），字润之（原作咏芝，后改润芝），笔名子任。湖南湘潭人。中国人民的领袖，伟大的马克思主义者，无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人，军事家 [1]  ，诗人，书法家。 [2] \n" +
                "1949至1976年，毛泽东担任中华人民共和国最高领导人。他对马克思列宁主义的发展、军事理论的贡献以及对共产党的理论贡献被称为毛泽东思想。因毛泽东担任过的主要职务几乎全部称为主席，所以也被人们尊称为“毛主席”。\n" +
                "毛泽东被视为现代世界历史中最重要的人物之一，《时代》杂志也将他评为20世纪最具影响100人之一。\n" +
                "1925年冬至1927年春，先后发表《中国社会各阶级的分析》、《湖南农民运动考察报告》等著作，指出农民问题在中国革命中的重要地位和无产阶级领导农民斗争的极端重要性，批评了陈独秀的右倾思想。\n" +
                "国共合作全面破裂后，在1927年8月中共中央紧急会议上，他提出“政权是由枪杆子中取得的”，即以革命武装夺取政权的思想，并被选为中央政治局候补委员。会后，到湖南、江西边界领导秋收起义。接着率起义部队上井冈山，发动土地革命，创立第一个农村革命根据地。\n" +
                "1928年4月，同朱德领导的起义部队会师，成立工农革命军（不久改称红军）第四军，他任党代表、前敌委员会书记。以他为主要代表的中国共产党人，从中国的实际出发，在国民党政权统治比较薄弱的农村发展武装斗争，开创了以农村包围城市、最后夺取城市和全国政权的道路。他在《中国的红色政权为什么能够存在？》、《星星之火，可以燎原》等著作中对这个问题从理论上作了阐述。"));

    }


}
