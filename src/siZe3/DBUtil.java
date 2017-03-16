package siZe3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//连接数据库封装类，
/*
 * 静态方法有获得数据库的连接、关闭数据库
 */
public class DBUtil {
	private static final String driverName = "com.mysql.jdbc.Driver";// 连接MySQL数据库的驱动信息
	private static final String userName = "root";// 连接MySQL数据库的用户名
	private static final String userPwd = "490272";// 连接数据库的用户名的密码
	private static final String dbName = "shiTiKu";// 数据库名

	
	public static void main(String[] args) {
		Connection conn = getDBConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		closeRS(conn, pstmt, rs);
	}
	
	public static Connection getDBConnection() {
		// 定义连接URL
		String url1 = "jdbc:mysql://localhost:3306/" + dbName;
		String url2 = "?user=" + userName + "&password=" + userPwd;
		String url3 = "&useUnicode=true&characterEncoding=UTF-8";
		String url = url1 + url2 + url3;

		Connection conn = null;

		try {
			Class.forName(driverName);// 注册驱动
			conn = DriverManager.getConnection(url);// 获得连接
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;

	}

	// 释放资源
	public static void closeRS(Connection conn, PreparedStatement pstmt, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
