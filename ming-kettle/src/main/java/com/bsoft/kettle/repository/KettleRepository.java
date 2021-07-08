package com.bsoft.kettle.repository;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.Log;
import com.alibaba.druid.pool.DruidDataSource;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;

public class KettleRepository {
    private static final Log log = Log.get();
    private static KettleRepository instance;
    private KettleDatabaseRepository repository;
    private String initType;

    private KettleRepository(){}

    /**
     *
     * @return
     */
    public static KettleRepository getInstance(){
        if(instance == null){
            synchronized (KettleRepository.class){
                instance = new KettleRepository();
                instance.initRepository();
            }
        }
        return instance;
    }

    /**
     * 系统初始化用
     * @return
     */
    public static KettleRepository getInstanceWithInit() {
        if(instance == null){
            synchronized (KettleRepository.class){
                instance = new KettleRepository();
                instance.initRepository();
                instance.repository.create();
            }
        }
        return instance;
    }

    private void initRepository(){
        try {
            //初始化kettle环境
            EnvUtil.environmentInit();
            KettleEnvironment.init();
        } catch (KettleException e) {
            log.error("初始化kettle资源库失败",e);
        }
        //创建资源库
        repository = new KettleDatabaseRepository();
        DatabaseMeta meta = getMyDataBaseMeta();
        KettleDatabaseRepositoryMeta kettleDatabaseMeta = new KettleDatabaseRepositoryMeta("lm-data", "lm-data", "king description",meta);
        repository.init(kettleDatabaseMeta);
        if(!repository.isConnected()){
            try {
                repository.connect("admin","admin");
                repository.disconnect();
            }catch (Exception e){
                repository.create();
            }
        }
        //repository.create();
    }

    public KettleDatabaseRepository getRepository() throws KettleException {
        if(!repository.isConnected()){
            try {
                repository.connect("admin","admin");
            } catch (KettleException e) {
                log.error("kettle资源库连接失败",e);
                throw new KettleException("kettle资源库连接失败");
            }
        }
        return repository;
    }
    private DatabaseMeta getMyDataBaseMeta(){
        DruidDataSource dataSource = SpringUtil.getBean("dataSource",DruidDataSource.class);
        String url = dataSource.getUrl();
        String dataBaseUrl = url.substring(url.indexOf("//")+2,url.lastIndexOf("/"));
        String dbType = dataSource.getDbType();
        return new DatabaseMeta("本程序数据库", dbType, "Native(JDBC)", dataBaseUrl, "dm-kettle", "1521", "myKettle", "myKettle");
    }

}
