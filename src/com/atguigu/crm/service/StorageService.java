package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.bean.Product;
import com.atguigu.crm.bean.Storage;
import com.atguigu.crm.mapper.StorageMapper;
import com.atguigu.crm.orm.Page;

@Service
public class StorageService {
	@Autowired
	private StorageMapper rm;

	/**
	 * 2016年5月17日 上午10:07:12 dell 库存条件查询分页
	 */
	@Transactional(readOnly = true)
	public Page<Storage> getPage(int pageNo, Map<String, Object> startingWith) {
		Page<Storage> page = new Page<>();
		// 将参数转换为 Mybatis需要的
		Map<String, Object> params = MapparseRequestParams2MyBatisParams(startingWith);
		page.setPageNo(pageNo);
		// 获取条件查询的总数
		int totalElements = rm.getTotalElements(params);
		page.setTotalElements(totalElements);
		int firstIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = firstIndex + page.getPageSize();

		params.put("firstIndex", firstIndex);
		params.put("endIndex", endIndex);
		page.setContent(rm.getList(params));
		return page;
	}

	/**
	 * 2016年5月17日 上午10:29:51 dell 字符串截取
	 */
	private Map<String, Object> MapparseRequestParams2MyBatisParams(
			Map<String, Object> map) {

		Map<String, Object> params = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().trim().equals("")) {
				continue;
			}
			if (key.startsWith("LIKE_")) {
				key = key.substring(5, key.length());
				if (key.startsWith("product.name")) {
					key = "productName";
				}
				value = "%" + value + "%";
				params.put(key, value);
			}
			params.put(key, value);
		}
		return params;
	}

	/**
	 * 2016年5月17日 下午4:10:40 dell 获取所有产品信息
	 */
	@Transactional(readOnly = true)
	public List<Product> getProducts() {
		return rm.getProducts();
	}

	/**
	 * 2016年5月17日 下午6:26:35 dell 添加保存
	 */
	@Transactional
	public void save(Storage storage) {
		rm.save(storage);
	}

	@Transactional(readOnly = true)
	public Storage getById(int id) {

		return rm.getById(id);
	}

	@Transactional
	public void update(Storage storage) {
		rm.update(storage);
	}

	@Transactional
	public void delete(int id) {
		rm.delete(id);
	}
}
