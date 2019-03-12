package com.aebiz.common.seckill.model;

public class UserRequestRecord {


		/**
		 * 手机号，唯一标志用户身份
		 */
		private String mobile;

		/**
		 * 时间戳
		 */
		private long timestamp;

		public String getMobile()
		{
			return mobile;
		}

		public void setMobile(String mobile)
		{
			this.mobile = mobile;
		}

		public long getTimestamp()
		{
			return timestamp;
		}

		public void setTimestamp(long timestamp)
		{
			this.timestamp = timestamp;
		}

		@Override
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			builder.append("UserRequestRecord [mobile=");
			builder.append(mobile);
			builder.append(", timestamp=");
			builder.append(timestamp);
			builder.append("]");
			return builder.toString();
		}

}
