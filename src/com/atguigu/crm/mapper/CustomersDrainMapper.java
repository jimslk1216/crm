package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.atguigu.crm.bean.CustomerDrain;

public interface CustomersDrainMapper {

	int getTotalElements(Map<String, Object> map);

	List<CustomerDrain> getPage(Map<String, Object> map);

	@Update("{call check_drain}")
	void callcheckDrainProcedure();

	CustomerDrain getById(int id);

	int delay(@Param("id")int id, @Param("text")String texr);

	void updeteCus(@Param("ids")int id);

	void updetecd(CustomerDrain cd);
}
