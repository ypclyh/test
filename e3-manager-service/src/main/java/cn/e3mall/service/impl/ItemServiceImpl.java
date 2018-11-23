package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import pojo.EasyUIDataGridResult;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public TbItem getItemById(Long id) {
		TbItem item = itemMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public EasyUIDataGridResult getItem(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example=new TbItemExample();
	    List<TbItem> listItem = itemMapper.selectByExample(example);
	    PageInfo<TbItem> pageInfo =new PageInfo<TbItem>(listItem);
	    EasyUIDataGridResult Result=new EasyUIDataGridResult();
	    Result.setTotal(pageInfo.getTotal());
	    Result.setRows(listItem);
	    return Result;
	
	}

}
