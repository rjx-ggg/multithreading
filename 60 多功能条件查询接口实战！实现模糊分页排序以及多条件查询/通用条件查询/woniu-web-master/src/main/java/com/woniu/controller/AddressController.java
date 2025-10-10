package com.woniu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.address.Address;
import com.woniu.address.AddressCondition;
import com.woniu.address.AddressFuzzyQueries;
import com.woniu.address.AddressQueries;
import com.woniu.dao.AddressDao;
import com.woniu.service.AddressService;
import com.woniu.suanfa.AggregateQueries;
import com.woniu.suanfa.PaginationDTO;
import com.woniu.util.AggregateQueriesUtil;
import com.woniu.vo.ApiResponse;
import com.woniu.vo.MessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Java实现一个通用的多功能条件(聚合)查询接口
 * 实现模糊、分页、排序以及多条件查询
 */
@RequestMapping("/api")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressDao addressDao;

    /**
     * 聚合查询
     *
     * @param aggregate 聚合查询对象
     * @return {@link ApiResponse}<{@link List}<{@link Address}>>
     */
    @PostMapping("/get")
    @ResponseBody
    public ApiResponse<List<Address>> get(@RequestBody AggregateQueries<AddressQueries, AddressCondition, AddressFuzzyQueries> aggregate) {
        if (!aggregate.hasPagination()) {
            return ApiResponse.fail(null, MessageEnum.ParamException);
        }
        PaginationDTO pagination = aggregate.getPagination();
        QueryWrapper<Address> wrapper = AggregateQueriesUtil.splicingAggregateQueries(new QueryWrapper<>(), aggregate);
        Page<Address> page = new Page<>(pagination.getPage(), pagination.getSize());
        Page<Address> addressPage = addressDao.selectPage(page, wrapper);
        List<Address> records = addressPage.getRecords();
        return ApiResponse.ok(records);
    }


}
