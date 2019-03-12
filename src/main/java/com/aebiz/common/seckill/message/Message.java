package com.aebiz.common.seckill.message;

import java.io.Serializable;

/**

 */
public class Message implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8657613687306891080L;

	/**
	 * 消息唯一标识
	 */
	private String key;

	/**
	 * 消息具体内容
	 */
	private Object content;

	/**
	 * 执行失败次数
	 */
	private Integer failTimes;

	public Message(String key, Object content)
	{
		super();
		this.key = key;
		this.content = content;
		this.failTimes = new Integer(0);
	}

	public Message()
	{
		super();
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public Object getContent()
	{
		return content;
	}

	public void setContent(Object content)
	{
		this.content = content;
	}

	public Integer getFailTimes()
	{
		return failTimes;
	}

	public void setFailTimes(Integer failTimes)
	{
		this.failTimes = failTimes;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Message [key=");
		builder.append(key);
		builder.append(", content=");
		builder.append(content);
		builder.append(", failTimes=");
		builder.append(failTimes);
		builder.append("]");
		return builder.toString();
	}

}
