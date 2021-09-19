package com.health.service;

/**
 * @author HMR
 * @create 2021/7/29 10:04
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.health.SetmealService;
import com.health.constant.RedisConstant;
import com.health.dao.SetmealDao;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.pojo.Setmeal;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体检套餐服务
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutPath;//从属性文件中读取要生成的html对应的目录


//    //新增套餐信息，同时需要关联检查组
//    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
//        setmealDao.add(setmeal);
//        Integer setmealId = setmeal.getId();
//        this.setSetmealAndCheckgroup(setmealId, checkgroupIds);
//
//    }

    //设置套餐和检查组多对多关系，操作t_setmeal_checkgroup
    public void setSetmealAndCheckgroup(Integer setmealId,Integer[] checkgroupIds){
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap();
                map.put("setmealId",setmealId);
                System.out.println(checkgroupId+"========================================-----");
                map.put("checkgroupId",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }


    //新增套餐信息，同时需要关联检查组
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckgroup(setmealId, checkgroupIds);
        //将图片名称保存到Redis集合中
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, fileName);


        //当添加套餐后需要重新生成静态页面（套餐列表页面、套餐详情页面）
        generateMobileStaticHtml();

    }

    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //根据ID查询检查组
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    public List<Integer> findSetmealByCheckItemIds(Integer id) {
        return setmealDao.findSetmealByCheckItemIds(id);
    }

    public void edit(Setmeal setmeal, Integer[] checkitemIds) {
        //修改检查组基本信息，操作检查组t_checkgroup表
        setmealDao.edit(setmeal);
        //清理当前检查组关联的检查项，操作中间关系表t_checkgroup_checkitem表
        setmealDao.deleteTCheckgroupCheckitemBYId(setmeal.getId());
        //重新建立当前检查组和检查项的关联关系
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckgroup(setmealId,checkitemIds);
    }

    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    public Setmeal findById(int id) {
        return setmealDao.findById(id);

    }

    //生成当前方法所需的静态页面
    public void generateMobileStaticHtml(){
        //在生成静态页面之前需要查询数据
        List<Setmeal> list = setmealDao.findAll();

        //需要生成套餐列表静态页面
        generateMobileSetmealListHtml(list);

        //需要生成套餐详情静态页面
        generateMobileSetmealDetailHtml(list);
    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> list){
        Map map = new HashMap();
        //为模板提供数据，用于生成静态页面
        map.put("setmealList",list);
        generteHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }

    //生成套餐详情静态页面（可能有多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> list){
        for (Setmeal setmeal : list) {
            Map map = new HashMap();
            map.put("setmeal",setmealDao.findById4Detail(setmeal.getId()));
            generteHtml("mobile_setmeal_detail.ftl","setmeal_detail_" + setmeal.getId() + ".html",map);
        }
    }

    //通用的方法，用于生成静态页面
    public void generteHtml(String templateName,String htmlPageName,Map map){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();//获得配置对象
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);
            //构造输出流
            out = new FileWriter(new File(outPutPath + "/" + htmlPageName));
            //输出文件
            template.process(map,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
