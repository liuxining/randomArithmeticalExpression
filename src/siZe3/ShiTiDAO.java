package siZe3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//试题数据库操作类，即对数据库进行增删改查的操作的类
public class ShiTiDAO {
	private String INSERT_SQL = "insert into shiTiKu01(tiMu,answer,calculateOrder,numberCount) values(?,?,?,?)";
	private String SELECTBYNUMBERCOUNT_SQL = "select * from shiTiKu01 where numberCount=?";
	
	private String SELECTALL = "select * from shiTiKu01";

	// 向数据库中插入一条记录
	public void insert(ShiTi stb) throws SQLException, MyException {
		if(stb == null)
		{
			throw new MyException("试题无效！");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		conn = DBUtil.getDBConnection();// 获取连接

		pstmt = conn.prepareStatement(INSERT_SQL);// 获取操作对象

		// 设置字段值
		pstmt.setString(1, stb.getTiMu());
		pstmt.setString(2, stb.getAnswer());
		pstmt.setString(3, stb.getCalculateOrder());
		pstmt.setInt(4, stb.getCountNumber());

		// 执行
		pstmt.executeUpdate();
		// 释放资源
		DBUtil.closeRS(conn, pstmt, rs);

	}

	// 向数据库中插入多条记录，参数为list
	public void insert(List<ShiTi> list) throws SQLException, MyException {
		if(list == null)
		{
			throw new MyException("试题无效！");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		conn = DBUtil.getDBConnection();// 获取连接

		pstmt = conn.prepareStatement(INSERT_SQL);// 获取操作对象

		// 设置字段值
		for (ShiTi stb : list) {
			pstmt.setString(1, stb.getTiMu());
			pstmt.setString(2, stb.getAnswer());
			pstmt.setString(3, stb.getCalculateOrder());
			pstmt.setInt(4, stb.getCountNumber());
			pstmt.executeUpdate();
		}

		// 执行
		// 释放资源
		DBUtil.closeRS(conn, pstmt, rs);

	}

	// 根据运算数个数查询，返回结果为list
	public List<ShiTi> selectByNumbercount(int count) throws SQLException, MyException {
		if(count < 2)
		{
			throw new MyException("运算数个数应大于等于2");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ShiTi> list = new ArrayList<ShiTi>();

		conn = DBUtil.getDBConnection();// 获取连接

		pstmt = conn.prepareStatement(SELECTBYNUMBERCOUNT_SQL);// 获取操作对象

		// 设置字段值
		pstmt.setInt(1, count);

		// 执行
		rs = pstmt.executeQuery();
		// 结果处理
		ShiTi stb = null;
		while (rs.next()) {
			stb = new ShiTi();
			stb.setTiMu(rs.getString("tiMu"));
			stb.setAnswer(rs.getString("answer"));
			stb.setCalculateOrder(rs.getString("calculateOrder"));
			stb.setCountNumber(rs.getInt("numberCount"));
			list.add(stb);
		}

		// 释放资源
		DBUtil.closeRS(conn, pstmt, rs);
		return list;

	}
	
	
	
	
	
	//查询数据库中的全部记录
	public List<ShiTi> selectAll() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ShiTi> list = new ArrayList<ShiTi>();

		conn = DBUtil.getDBConnection();// 获取连接

		pstmt = conn.prepareStatement(SELECTALL);// 获取操作对象


		// 执行
		rs = pstmt.executeQuery();
		// 结果处理
		ShiTi stb = null;
		while (rs.next()) {
			stb = new ShiTi();
			stb.setTiMu(rs.getString("tiMu"));
			stb.setAnswer(rs.getString("answer"));
			stb.setCalculateOrder(rs.getString("calculateOrder"));
			stb.setCountNumber(rs.getInt("numberCount"));
			list.add(stb);
		}

		// 释放资源
		DBUtil.closeRS(conn, pstmt, rs);
		return list;

	}


}
