package cn.chendd.ansj.enums;

import org.ansj.domain.Result;

/**
 * 分词接口定义
 *
 * @author chendd
 * @date 2020/8/8 9:57
 */
public interface IAnsjAnalysis {

    Result instance(String text);

}
