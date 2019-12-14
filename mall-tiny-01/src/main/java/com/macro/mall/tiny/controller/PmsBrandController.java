package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.mbg.model.PmsBrand;
import com.macro.mall.tiny.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理Controller
 * Created by macro on 2019/4/19.
 */
@RestController
@RequestMapping("/brand")
@Api(tags = "PmsBrandController", description = "商品品牌管理")
public class PmsBrandController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);
    @Autowired
    private PmsBrandService pmsBrandService;

    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(pmsBrandService.listAllBrand());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation("添加品牌")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrandDto) {
        CommonResult commonResult;
        int count = pmsBrandService.createBrand(pmsBrandDto);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrandDto);
            LOGGER.debug("createBrand success:{}", pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
        }
        return commonResult;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ApiOperation("更新指定id品牌信息")
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto) {
        CommonResult commonResult;
        int count = pmsBrandService.updateBrand(id, pmsBrandDto);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
        }
        return commonResult;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation("删除指定id的品牌")
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        CommonResult commonResult;
        int count = pmsBrandService.deleteBrand(id);
        if (count == 1) {
            commonResult = CommonResult.success(null);
        } else {
            commonResult = CommonResult.failed("操作失败");
        }
        return commonResult;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation("分页查询品牌列表")
    public CommonResult listBrand(@RequestParam(value = "pageNum", defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam("页数") Integer pageSize) {
        List<PmsBrand> brandList = pmsBrandService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @ApiOperation("获取指定id的品牌详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        return CommonResult.success(pmsBrandService.getBrand(id));
    }
}
