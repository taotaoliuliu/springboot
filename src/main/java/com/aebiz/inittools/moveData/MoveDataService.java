package com.aebiz.inittools.moveData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aebiz.entity.MoveRules;
import com.aebiz.entity.SrcDataSource;
import com.aebiz.entity.TargetDataSource;

@Component
public class MoveDataService {

	private static final Log LOGGER = LogFactory.getLog(MoveDataService.class);

	private PreparedStatement pst = null;

	protected int totalDataCount = 0; //tartgetDataCOunt
	
	private int loopCount=0;
	
	
	private int loopStart=0;
	
	private int srcTotalDataCount=0;
	

	@Autowired
	public MoveRules moveRules;

	public void moveData(int pageShow) {

		Connection srcConnection=null;
		Connection targetConnection =null;
		try {
			List<String> querysql = moveRules.getSrcDataSource().getQuerysql();


			SrcDataSource srcDataSource = moveRules.getSrcDataSource();
			TargetDataSource targetDataSource = moveRules.getTargetDataSource();
			 srcConnection = DriverManager.getConnection(srcDataSource.getUrl(), srcDataSource.getUsername(),
					srcDataSource.getPassword());

			 targetConnection= DriverManager.getConnection(targetDataSource.getUrl(),
					targetDataSource.getUsername(), targetDataSource.getPassword());

			for ( int i=0;i<querysql.size();i++) {
				
				String tableName = moveRules.getTargetDataSource().getTargetTables().get(i);
				
				
				

				execData(srcConnection,targetConnection,querysql.get(i),tableName ,1,pageShow);

				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			try {
				targetConnection.close();
				srcConnection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void execData(Connection srcConnection,Connection targetConnection,String sql,String tableName,int pageNow,int pageShow) {
		
		// 如果 源数据比较多 可以分批 迁移  查询总条数
		String countsql=getCountSql(sql);
		
		int doGetDataCountFromSrc = doGetDataCountFromSrc(srcConnection,countsql); //46   10 
		
		
		int pageCount=doGetDataCountFromSrc/pageShow+1;  // 5  46/10 +1 =5
	
		
		List<Map<String, Object>> doGetDataFromSrc = doGetDataFromSrc(srcConnection, sql, pageShow);
		srcTotalDataCount+=doGetDataFromSrc.size();

		totalDataCount = doGetDataFromSrc.size();

		System.out.println(doGetDataFromSrc);


		String insertSql = "";

		List<String> fieldNames = new ArrayList<>();
		for (Map<String, Object> map : doGetDataFromSrc) {

			Set<String> keySet = map.keySet();
			insertSql = getInsertSql(tableName, keySet);

			fieldNames.addAll(keySet);
			break;
		}

		for (Map<String, Object> map : doGetDataFromSrc) {

			List<Object> listValues = new ArrayList<>();

			for (String filedName : map.keySet()) {
				listValues.add(map.get(filedName));
			}

			insertData(targetConnection, insertSql, listValues);

		}	
		///////继续下一页
			//1 2 3 4 5 
		if(pageNow<=pageCount){
			pageNow++;
			execData(srcConnection, targetConnection, sql, tableName,pageNow, pageShow);
		}
		
		
	}
	
	private String getCountSql(String sql){
		String substring = sql.substring(sql.indexOf("from"));
		
		return "select count(*) "+substring;
	}

	private String getInsertSql(String tableName, Set<String> fieldNames) {

		String columNamesStr = "";
		String valuesStr = "";
		for (String fieldName : fieldNames) {
			columNamesStr = columNamesStr + fieldName + ",";
			valuesStr = valuesStr + "? ,";
		}
		columNamesStr = columNamesStr.substring(0, columNamesStr.length() - 1);
		valuesStr = valuesStr.substring(0, valuesStr.length() - 1);

		if (columNamesStr.length() == 0) {
			throw new RuntimeException("初始化Context出错：表名：" + tableName + " 对应的实体类没有定义任何属性");
		}

		String insertSQL = "INSERT INTO " + tableName + " ( " + columNamesStr + " ) VALUES ( " + valuesStr + " )";

		return insertSQL;

	}
	
	
	
	
	private int doGetDataCountFromSrc(Connection connection,String sql){
		
		QueryRunner runner = new QueryRunner();

			try {
				Long query = runner.query(connection, sql, new ScalarHandler<Long>());
				return query.intValue();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return 0;
	}
	
	public static void main(String[] args) {
		String sql="select uuid as id,productName as name,skuNo from goods_sku";
		String sql2="select uuid,brand from goods_sku INNER JOIN goods_main on goods_sku.goodsUuid=goods_main.uuid";
		
		String substring = sql.substring(sql.indexOf("from"));
		String substring2 = sql2.substring(sql2.indexOf("from"));
		
		System.out.println("select count(*) "+substring);
		System.out.println(substring2);
		
		for(int x=2;x<5;x++){
			System.out.println(x);
		}
	}

	private List<Map<String, Object>> doGetDataFromSrc(Connection connection, String sql, int pageSize) {
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		QueryRunner runner = new QueryRunner();
		// String dbType =
		// config.getDataSources(srcData.getSrcDataSourceRef()).getDBType();
		// querySQL = getQuerySQL(dbType, querySQL, pageNo, pageSize);

		try {
			
			int start=pageSize*loopStart;  // 0 10 , 10,10 ,20 10, 30 10 ,40 10
			int end =pageSize;
			
			sql=sql+" limit "+start+","+end;
			
			resultMap = runner.query(connection, sql, new MapListHandler());
		} catch (SQLException e) {
			// LOGGER.error(errorMsg,e);
			e.printStackTrace();
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return resultMap;
		}
		loopStart++;
		return resultMap;
	}

	// obj user
	public boolean insertData(Connection conn, String insertSql, List<Object> values) {

		// List values = getValues(obj, beanClass);

		// 如果有需要对即将插入的数据进行特殊处理的；在自己的实现类里面重写这个方法就可以；
		// customProcessValues(values,fieldNames);

		try {
			conn.setAutoCommit(false);
			if (pst == null) {
				// insert into t_user (name,age) values (? ,?)
				pst = conn.prepareStatement(insertSql);
			}
			for (int i = 0; i < values.size(); i++) {
				pst.setObject(i + 1, values.get(i));
			}
			loopCount++;
			pst.addBatch();
			// pst.executeUpdate();
			if (loopCount != 0 && loopCount % 2000 == 0) {
				System.out.println("id:的任务已经迁移了" + loopCount + "条");
				pst.executeBatch();
				pst.clearBatch();
				conn.commit();
			}

			if (loopCount == totalDataCount) {
				System.out.println("id的任务已经迁移了" + loopCount + "条");
				pst.executeBatch();
				pst.clearBatch();
				conn.commit();
				
				loopCount=0;
				totalDataCount=0;
			}

			// DbUtils.close(pst);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
