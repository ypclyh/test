package cn.e3mall.service;

import cn.e3mall.pojo.TbItem;
import pojo.EasyUIDataGridResult;

public interface ItemService {
	TbItem getItemById(Long id);
	EasyUIDataGridResult getItem(Integer page ,Integer rows);
}
