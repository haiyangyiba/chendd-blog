package cn.chendd.toolkit.dbproperty.controller;

import cn.chendd.core.exceptions.ValidateDataException;
import cn.chendd.core.result.BaseResult;
import cn.chendd.core.result.SuccessResult;
import cn.chendd.toolkit.dbproperty.model.SysDbValue;
import cn.chendd.toolkit.dbproperty.po.SysDbValueParam;
import cn.chendd.toolkit.dbproperty.service.SysDbValueService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统参数管理Controller
 *
 * @author chendd
 * @date 2020/6/28 22:06
 */
@Api(value = "系统参数管理" , tags = "系统参数管理")
@ApiSort(60)
@RequestMapping(value = "/toolkit/dbvalue" , produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class SysDbValueController {

    @Resource
    private SysDbValueService sysDbValueService;

    @GetMapping
    @ApiOperation(value = "系统参数管理" , notes = "系统参数管理页面")
    @ApiOperationSupport(order = 10)
    public String dbValueManage() {
        return "/toolkit/dbvalue/dbValueManage";
    }

    @PostMapping
    @ApiOperation(value = "系统参数分页查询",notes = "系统参数分页查询")
    @ApiOperationSupport(order = 20)
    @ResponseBody
    public BaseResult userManage(@ApiParam("参数查询") SysDbValueParam queryParam) {
        String param = queryParam.getParam();
        String group = queryParam.getGroup();
        List<SysDbValue> dataList = sysDbValueService.selectAll(queryParam).stream()
                .filter(item -> StringUtils.isBlank(param) || (StringUtils.contains(item.getKey() , param) || StringUtils.contains(item.getValue() , param)))
                .filter(item -> StringUtils.isBlank(group) || StringUtils.contains(item.getGroup() , group))
                .collect(Collectors.toList());
        return new SuccessResult<>(dataList);
    }

    @GetMapping("/dbvalue")
    @ApiOperation(value = "系统参数管理" , notes = "系统参数管理页面")
    @ApiOperationSupport(order = 30)
    public String dbValue() {
        return "/toolkit/dbvalue/dbValue";
    }

    @PutMapping
    @ApiOperation(value = "保存系统参数",notes = "保存系统参数")
    @ApiOperationSupport(order = 40)
    @ResponseBody
    public BaseResult dbValue(
            @ApiParam("参数对象")
            @RequestBody SysDbValue dbValue) {

        this.validatorData(dbValue);
        sysDbValueService.saveSysDbValue(dbValue);
        return new SuccessResult<>("参数保存成功！");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "系统参数管理" , notes = "系统参数管理页面")
    @ApiOperationSupport(order = 50)
    public String editor(@PathVariable @ApiParam("主键ID") Long id , HttpServletRequest request) {
        SysDbValue dbValue = sysDbValueService.getById(id);
        request.setAttribute("dbValue" , dbValue);
        return "/toolkit/dbvalue/dbValue";
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "系统参数管理" , notes = "系统参数管理页面")
    @ApiOperationSupport(order = 60)
    @ResponseBody
    public BaseResult remove(@PathVariable @ApiParam("主键ID") Long id) {
        sysDbValueService.removeSysDbValue(id);
        return new SuccessResult<>("参数保存成功！");
    }

    @PutMapping("/async")
    @ApiOperation(value = "同步更新参数列表" , notes = "同步更新参数列表")
    @ApiOperationSupport(order = 70)
    @ResponseBody
    public BaseResult async() {
        sysDbValueService.asyncSysDbValue();
        return new SuccessResult<>("参数同步更新成功！");
    }

    @GetMapping("/{group}/{key}")
    @ApiOperation(value = "根据group-key查询value值" , notes = "按group-key取值")
    @ApiOperationSupport(order = 80)
    @ResponseBody
    public BaseResult getDbValueByKey(@ApiParam("group") @PathVariable String group , @ApiParam("key") @PathVariable String key) {
        return new SuccessResult<>(sysDbValueService.getDbValueByKey(group , key));
    }

    private void validatorData(SysDbValue dbValue) {
        if(StringUtils.isBlank(dbValue.getKey())) {
            throw new ValidateDataException("参数名称不能为空！");
        }
        if(StringUtils.isBlank(dbValue.getValue())) {
            throw new ValidateDataException("参数值不能为空！");
        }
        String group = dbValue.getGroup();
        if(StringUtils.isBlank(group)) {
            throw new ValidateDataException("参数组名不能为空！");
        }
        if(StringUtils.length(dbValue.getRemark()) > 200) {
            throw new ValidateDataException("参数描述不能超过200个字符！");
        }
    }
}
