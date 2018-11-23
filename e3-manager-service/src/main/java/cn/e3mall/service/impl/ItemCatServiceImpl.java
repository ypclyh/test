package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemCatService;
import pojo.EasyUITreeNode;
import utils.E3Result;
import utils.IDUtils;
@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Resource
	private Destination topicDestination;
	
	@Override
	public List<EasyUITreeNode> getItemCatlist(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		 criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list  = tbItemCatMapper.selectByExample(example);
		List<EasyUITreeNode>  resultList =new ArrayList();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode easyUITreeNode=new EasyUITreeNode();
			easyUITreeNode.setId(tbItemCat.getId());
			easyUITreeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			easyUITreeNode.setText(tbItemCat.getName());
			resultList.add(easyUITreeNode);
		}
		return resultList;
	}

	@Override
	public E3Result addItem(TbItem item, String desc) {
		
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 3、向商品表插入数据
		tbItemMapper.insert(item);
		TbItemDesc tbItemDesc=new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		tbItemDescMapper.insert(tbItemDesc);
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}
		});
		return E3Result.ok();
	}

	
	

}
