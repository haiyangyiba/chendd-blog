package cn.chendd.blog.admin.blog.article.service.impl;

import cn.chendd.blog.admin.blog.article.enums.EnumArticleProperty;
import cn.chendd.blog.admin.blog.article.mapper.ArticleStateMapper;
import cn.chendd.blog.admin.blog.article.model.ArticleProperty;
import cn.chendd.blog.admin.blog.article.service.IArticlePropertyService;
import cn.chendd.blog.admin.blog.article.vo.ArticlePropertyResult;
import cn.chendd.blog.base.spring.component.SystemParamComponent;
import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.core.spring.SpringBeanFactory;
import cn.chendd.core.spring.service.watermark.ImageWatermarkService;
import cn.chendd.toolkit.operationlog.annotions.Log;
import cn.chendd.toolkit.operationlog.enums.LogScopeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文章状态Service接口实现
 *
 * @author chendd
 * @date 2020/8/2 22:22
 */
@Service
public class ArticlePropertyServiceImpl extends ServiceImpl<ArticleStateMapper, ArticleProperty> implements IArticlePropertyService {

    @Resource
    private ArticleStateMapper articleStateMapper;

    @Resource
    ImageWatermarkService imageWatermarkService;

    @Override
    public ArticlePropertyResult getArticleStateById(Long articleId) {
        ArticlePropertyResult result = new ArticlePropertyResult();
        List<ArticleProperty> dataList = articleStateMapper.selectList(new QueryWrapper<ArticleProperty>().eq("articleId" , articleId));
        if (CollectionUtils.isEmpty(dataList)) {
            return result;
        }
        result.setArticleId(articleId);
        for (ArticleProperty property : dataList) {
            EnumArticleProperty articleProperty = EnumArticleProperty.valueOf(property.getProperty());
            this.fillProperty(articleProperty , property , result);
        }
        return result;
    }

    @Override
    public List<ArticlePropertyResult> getArticleStateById(List<Long> articleIds) {
        List<ArticlePropertyResult> resultList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(articleIds)) {
            return resultList;
        }
        //根据一组文章ID数据查询，并按文章ID对应一组数据
        List<ArticleProperty> dataList = articleStateMapper.selectList(new QueryWrapper<ArticleProperty>().in("articleId" , articleIds));
        Map<Long , List<ArticleProperty>> dataMap = Maps.newLinkedHashMap();
        for (ArticleProperty state : dataList) {
            Long articleId = state.getArticleId();
            if(dataMap.containsKey(articleId)) {
                dataMap.get(articleId).add(state);
            } else {
                dataMap.put(articleId , Lists.newArrayList(state));
            }
        }
        //将同一组文章ID对应的数据转换为一条数据
        Set<Map.Entry<Long , List<ArticleProperty>>> set = dataMap.entrySet();
        for (Map.Entry<Long, List<ArticleProperty>> entry : set) {
            List<ArticleProperty> itemList = entry.getValue();
            ArticlePropertyResult result = new ArticlePropertyResult();
            result.setArticleId(entry.getKey());
            for (ArticleProperty state : itemList) {
                EnumArticleProperty articleProperty = EnumArticleProperty.valueOf(state.getProperty());
                this.fillProperty(articleProperty , state , result);
            }
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    @Log(description = "'文章属性-设置' + #property.text" , scope = LogScopeEnum.ALL)
    public BaseResult changeProperty(EnumArticleProperty property, Long articleId , String name , String yesText , String noText) {

        QueryWrapper<ArticleProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("articleId", articleId);
        queryWrapper.eq("property", property.name());
        Integer count = this.articleStateMapper.selectCount(queryWrapper);
        if (count == 0) {
            //存在数据，则删除，否则则新增
            ArticleProperty articleProperty = new ArticleProperty();
            articleProperty.setArticleId(articleId);
            articleProperty.setProperty(property.name());
            articleProperty.setValue(name);
            this.articleStateMapper.insert(articleProperty);
            return new SuccessResult<>(yesText);
        } else {
            this.articleStateMapper.delete(queryWrapper);
            return new SuccessResult<>(noText);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Log(description = "文章属性-封面上传" , scope = LogScopeEnum.ALL , exclude = "file")
    public void uploadArticleCover(Long articleId, MultipartFile file , String basePath) {
        SystemParamComponent systemParam = SpringBeanFactory.getBean(SystemParamComponent.class);
        String rootPath = systemParam.getRootPath();
        String fileName = file.getOriginalFilename();
        String suffix = FilenameUtils.getExtension(fileName);
        final File destFile = new File(rootPath + "/system/article/cover" ,articleId + "." + suffix);
        if(! destFile.exists()) {
            destFile.mkdirs();
        }
        try {
            file.transferTo(destFile);
            this.changeCover(EnumArticleProperty.cover , FilenameUtils.getExtension(fileName) , articleId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ValidateDataException(String.format("操作出现错误，参考信息：%s" , e.getMessage()));
        }
        //封面图片增加水印
        String text = basePath + "/article/" + articleId + ".html";
        text = "https://www.chendd.cn";
        imageWatermarkService.imageWatermark4ArticleCover(destFile , text);
    }

    @Override
    @Log(description = "文章属性-封面删除" , scope = LogScopeEnum.ALL)
    public void removeArticleCover(final Long articleId) {
        QueryWrapper<ArticleProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("articleId", articleId);
        queryWrapper.eq("property", EnumArticleProperty.cover.name());
        this.articleStateMapper.delete(queryWrapper);
        SystemParamComponent systemParam = SpringBeanFactory.getBean(SystemParamComponent.class);
        String rootPath = systemParam.getRootPath();

        File folder = new File(rootPath + "/system/article/cover");
        String[] fileNames = folder.list((file , name) -> StringUtils.startsWith(name , String.valueOf(articleId)));
        if(fileNames != null) {
            for (String fileName : fileNames) {
                new File(folder , fileName).delete();
            }
        }
    }

    private void changeCover(EnumArticleProperty cover , String value, Long articleId) {
        QueryWrapper<ArticleProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("articleId", articleId);
        queryWrapper.eq("property", cover.name());
        //存在数据，则删除，否则则新增
        ArticleProperty articleProperty = new ArticleProperty();
        articleProperty.setArticleId(articleId);
        articleProperty.setProperty(EnumArticleProperty.cover.name());
        articleProperty.setValue(value);
        this.articleStateMapper.delete(queryWrapper);
        this.articleStateMapper.insert(articleProperty);
    }

    /**
     * 将对应类型的属性填充至结果集对象中
     * @param articleProperty 数据类型枚举对象
     * @param state 数据对象
     * @param result 结果集
     */
    private void fillProperty(EnumArticleProperty articleProperty , ArticleProperty state , ArticlePropertyResult result) {
        Field[] fields = result.getClass().getDeclaredFields();
        //获取对象所有字段，并根据对应字段类型来赋值
        for (Field field : fields) {
            if(field.getType() != articleProperty.getEnumClass()) {
                continue;
            }
            //字段类型枚举
            Enum[] enums = articleProperty.getEnumClass().getEnumConstants();
            for (Enum e : enums) {
                if (e.name().equals(state.getValue()) || e.name().equals(state.getProperty())) {
                    try {
                        FieldUtils.writeField(field , result , e , true);
                        break;
                    } catch (IllegalAccessException exception) {
                        throw new ClassCastException(String.format("字段 %s 赋值 %s 出现错误：" , field.getName() , e.name()));
                    }
                }
            }
        }
        //对于某些属性的特殊处理
        if ( EnumArticleProperty.cover.name().equals(state.getProperty())) {
            String coverImageUrl = "/system/article/cover/" + result.getArticleId() + "." + state.getValue();
            result.setCoverImageUrl(coverImageUrl);
        }
    }
}
