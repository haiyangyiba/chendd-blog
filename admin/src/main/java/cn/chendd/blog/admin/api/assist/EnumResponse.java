package cn.chendd.blog.admin.api.assist;

import cn.chendd.core.enums.EnumResult;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.ErrorResult;
import cn.chendd.core.result.SuccessResult;

import java.awt.*;
import java.util.ArrayList;

/**
 * 响应结果类型定义
 *
 * @author chendd
 * @date 2019/9/22 18:35
 */
public enum EnumResponse implements IEnumReponse {

    /**
     *
     */
    BaseResult {
        @Override
        public cn.chendd.core.result.BaseResult getResult() {
            return new BaseResult<>(EnumResult.success.name() ,"描述信息" , Entity.getResult());
        }
    },
    Success {
        @Override
        public BaseResult getResult() {
            return new SuccessResult<>(Entity.getResult());
        }
    },
    Error {
        @Override
        public BaseResult getResult() {
            return new ErrorResult<Point>("系统异常");
        }
    },
    Integer {
        @Override
        public Integer getResult() {
            return java.lang.Integer.MAX_VALUE;
        }
    },
    Long {
        @Override
        public Long getResult() {
            return java.lang.Long.MAX_VALUE;
        }
    },
    Double {
        @Override
        public Double getResult() {
            return java.lang.Double.MAX_VALUE;
        }
    },
    Boolean {
        @Override
        public Boolean getResult() {
            return java.lang.Boolean.TRUE;
        }
    },
    String {
        @Override
        public String getResult() {
            return "hello";
        }
    },
    Entity {
        @Override
        public Point getResult() {
            return new Point(9 , 11);
        }
    },
    List {
        @Override
        public java.util.List<Point> getResult() {
            java.util.List<Point> dataList = new ArrayList<>();
            dataList.add((Point) Entity.getResult());
            dataList.add((Point) Entity.getResult());
            dataList.add((Point) Entity.getResult());
            return dataList;
        }
    },
    NULL {
        @Override
        public Object getResult() {
            return null;
        }
    }
    ;

}
