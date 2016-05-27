package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.bean.Product;
import com.atguigu.crm.bean.Storage;

public interface StorageMapper {

	int getTotalElements(Map<String, Object> startingWith);

	List<Storage> getList(Map<String, Object> params);

	List<Product> getProducts();

	void save(@Param("s")Storage storage);

	Storage getById(int id);

	void update(@Param("s")Storage storage);

	void delete(int id);

	

}
