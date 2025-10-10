package com.woniu.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.address.Address;
import com.woniu.dao.AddressDao;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {
}
