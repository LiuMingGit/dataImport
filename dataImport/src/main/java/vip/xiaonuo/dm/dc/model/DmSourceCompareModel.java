package vip.xiaonuo.dm.dc.model;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.bsoft.kettle.compare.TableCompareRes;
import com.bsoft.kettle.database.DataBase;
import vip.xiaonuo.dm.dt.entity.DmDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class DmSourceCompareModel {
    public List<TableCompareRes> compareTable(DmDataSource originDataBase, DmDataSource targetDataBase){
        Map properties = new HashMap();
        properties.put("url",originDataBase.getDataSourceUrl());
        properties.put("username",originDataBase.getDataSourceUsername());
        properties.put("password",originDataBase.getDataSourcePassword());
        properties.put("driverClassName","oracle.jdbc.OracleDriver");
        try {
            DataSource originSource = DruidDataSourceFactory.createDataSource(properties);
            properties.put("url",targetDataBase.getDataSourceUrl());
            properties.put("username",targetDataBase.getDataSourceUsername());
            properties.put("password",targetDataBase.getDataSourcePassword());
            DataSource targetDataSource = DruidDataSourceFactory.createDataSource(properties);
            Connection originConnection = originSource.getConnection();
            Connection targetConnection = targetDataSource.getConnection();
            Statement statement = originConnection.createStatement();
            String sql = "select TABLE_NAME from user_tables";
            String sql2 = "SELECT * FROM USER_TAB_COLUMNS where TABLE_NAME = ?";
            PreparedStatement originPs = originConnection.prepareStatement(sql2);
            PreparedStatement targetPs = targetConnection.prepareStatement(sql2);

            ResultSet resultSet = statement.executeQuery(sql);
            List<TableColumnInfo> originInfo = new ArrayList<>();
            List<TableColumnInfo> targetInfo = new ArrayList<>();
            while (resultSet.next()){
                String table_name = resultSet.getString("TABLE_NAME");
                originPs.setString(1,table_name);
                targetPs.setString(1,table_name);
                ResultSet resultSet1 = originPs.executeQuery();
                ResultSet targetResultSet = targetPs.executeQuery();
                while (resultSet1.next()){
                    TableColumnInfo build = TableColumnInfo.builder()
                            .TABLE_NAME(resultSet1.getString("TABLE_NAME"))
                            .COLUMN_NAME(resultSet1.getString("COLUMN_NAME"))
                            .DATA_TYPE(resultSet1.getString("DATA_TYPE"))
                            .DATA_LENGTH(resultSet1.getInt("DATA_LENGTH"))
                            .DATA_PRECISION(resultSet1.getInt("DATA_PRECISION")).build();
                    originInfo.add(build);
                }
                while (targetResultSet.next()){
                    TableColumnInfo build = TableColumnInfo.builder()
                            .TABLE_NAME(targetResultSet.getString("TABLE_NAME"))
                            .COLUMN_NAME(targetResultSet.getString("COLUMN_NAME"))
                            .DATA_TYPE(targetResultSet.getString("DATA_TYPE"))
                            .DATA_LENGTH(targetResultSet.getInt("DATA_LENGTH"))
                            .DATA_PRECISION(targetResultSet.getInt("DATA_PRECISION")).build();
                    targetInfo.add(build);
                }
            }
            Map<String, List<TableColumnInfo>> originCollect = originInfo.stream().collect(Collectors.groupingBy(TableColumnInfo::getTABLE_NAME));
            Map<String, List<TableColumnInfo>> targetCollect = targetInfo.stream().collect(Collectors.groupingBy(TableColumnInfo::getTABLE_NAME));
            originCollect.forEach((key,list)->{
                if(!targetCollect.containsKey(key)){
                    //新增表
                    System.err.println("新增表:"+list);
                }else{
                    List<TableColumnInfo> tableColumnInfos = targetCollect.get(key);
                    list.forEach(tableColumnInfo -> {
                        TableColumnInfo first = tableColumnInfos.stream().filter(tableColumnInfo1 -> tableColumnInfo1.getCOLUMN_NAME().equals(tableColumnInfo.getCOLUMN_NAME())).findFirst().orElse(null);
                        if(first == null){
                            //增加字段
                            System.err.println("增加字段：字段名-"+tableColumnInfo.getCOLUMN_NAME()+";字段类型-"+tableColumnInfo.getDATA_TYPE()+";字段长度-"+tableColumnInfo.getDATA_LENGTH());
                            return;
                        }else if(first.getDATA_LENGTH() < tableColumnInfo.getDATA_LENGTH()){
                            //字段加长
                            System.err.println("字段加长：字段名-"+tableColumnInfo.getCOLUMN_NAME()+";字段类型-"+tableColumnInfo.getDATA_TYPE()+";字段长度-"+tableColumnInfo.getDATA_LENGTH());
                        }
                        if(!first.getDATA_TYPE().equals(tableColumnInfo.getDATA_TYPE())){
                            //字段类型变化
                            System.err.println("字段类型变化：原类型-"+first.getDATA_TYPE()+";现类型-"+tableColumnInfo.getDATA_TYPE());
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        DmDataSource originDataBase = new DmDataSource();
        originDataBase.setDataSourceUrl("jdbc:oracle:thin:@10.8.2.131:1521:orcl");
        originDataBase.setDataSourceUsername("BSPHIS2630_BASE");
        originDataBase.setDataSourcePassword("BSPHIS2630_BASE");

        DmDataSource targetDataBase = new DmDataSource();
        targetDataBase.setDataSourceUrl("jdbc:oracle:thin:@10.8.2.131:1521:orcl");
        targetDataBase.setDataSourceUsername("BSPHISTEST");
        targetDataBase.setDataSourcePassword("BSPHISTEST");
        DmSourceCompareModel model = new DmSourceCompareModel();
        model.compareTable(originDataBase,targetDataBase);
    }
}
