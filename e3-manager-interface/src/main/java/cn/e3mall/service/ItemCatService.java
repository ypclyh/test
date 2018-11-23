package cn.e3mall.service;

import java.util.List;

import cn.e3mall.pojo.TbItem;
import pojo.EasyUITreeNode;
import utils.E3Result;

public interface ItemCatService {

	List<EasyUITreeNode> getItemCatlist(long parentId);
	
	E3Result addItem(TbItem item, String desc);
}