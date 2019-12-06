package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.Goods;
import com.qf.entity.ResultData;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Auth RuanHao
 * @Time 2019/12/2 21:04
 **/
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    //注入对象使用fastdfs
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("/list")
    public String list(Model model){
        List<Goods> goodsList = goodsService.list();
        model.addAttribute("goodsList",goodsList);
        //分页查询
       /* Page<Goods> page=new Page<>(1,1);
        IPage<Goods> goodsPage=goodsService.listPage(page);

         //当前页码
        System.out.println("goodsPage.getCurrent:"+goodsPage.getCurrent());
        //总页数
        System.out.println("goodsPage.getPages:"+goodsPage.getPages());
        //总记录数
        System.out.println("goodsPage.getTotal:"+goodsPage.getTotal());
        //每页记录数
        System.out.println("goodsPage.getSize:"+goodsPage.getSize());

        //goodsPage.getRecords() 存储查询出来数据的集合
        model.addAttribute("goodsList",goodsPage.getRecords());*/
        return "goodslist";
    }

    /**
     * 上传的图片存到fdfs,并返回路径
     * @param file
     * @return
     */
    @RequestMapping("/uploader")
    @ResponseBody
    public ResultData<String> uploader(MultipartFile file){//MultipartFile 表示读取二进制文件
        String path="";
        try {
            StorePath imagePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "JPG",
                    null
            );
            path=imagePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData(path);
    }

    /**
     * 图片的回显,网页无法直接访问电脑里的文件,通过服务器间接访问
     * @param imagePath
     * @param response
     */
   /* @RequestMapping("/show")
    public void show(String imagePath, HttpServletResponse response){
        //新建一个输入流,从本地路径读取到文件,然后使用一个输出流,输出到响应体.谁发的请求谁接收数据
        try(
                InputStream is=new FileInputStream(imagePath);
                OutputStream os=response.getOutputStream();
                ) {
            IOUtils.copy(is,os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 添加商品,在商品实体类中添加一个存封面的属性,一个存其他图片的list[存储的都是地址]
     * @return
     */
    @RequestMapping("/insert")
    public String insert(Goods goods){
        goodsService.insert(goods);
        return "redirect:/goods/list";
    }

    //ajax报错测试
   /* @RequestMapping("/ajax")
    @ResponseBody
    public ResultData<String> ajax(){
        System.out.println("ajax请求");
        System.out.println(1/0);
        return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData("mydata");
    }*/
}
