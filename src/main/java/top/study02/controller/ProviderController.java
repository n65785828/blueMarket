package top.study02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.study02.pojo.PackageProvider;
import top.study02.pojo.Provider;
import top.study02.service.ProviderService;
import top.study02.utils.CheckByRegularExpression;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProviderController {

    @Autowired
    private ProviderService providerService;


    /**
     * 跳转到供应商详情页面分页显示第一页
     * @param pageNum  第几页
     * @param pageSize 每页显示多少个
     * @param model    存放信息带给模板引擎
     * @return
     */
    @RequestMapping(value = "/providerList.html",method = RequestMethod.GET)
    public String providerList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize, Model model){
        PackageProvider packageProvider = providerService.getProvidersByPageNumAndPageSize(pageNum, pageSize);
        model.addAttribute("providers", packageProvider.getProviders());
        model.addAttribute("totalPage", packageProvider.getTotalPageNum());
        model.addAttribute("currentPage", pageNum);
        return "providerList";
    }

    /**
     *  ajax显示供应商分页信息 返回json数据
     * @param pageNum 第几页
     * @param pageSize 每页个数
     * @return
     */
    @RequestMapping(value = "/providerList.html",method = RequestMethod.POST)
    @ResponseBody
    public List<Provider> ajaxProviderList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize){
        PackageProvider packageProvider = providerService.getProvidersByPageNumAndPageSize(pageNum, pageSize);
        return packageProvider.getProviders();
    }

    /**
     * 根据id删除用户
     * @param id
     * @return 请求准发给分页显示页面
     */
    @RequestMapping("/deleteProvider")
    public String deleteProvider(int id){
        int result = providerService.deleteProviderById(id);
        System.out.println(result==1?"删除成功":"删除失败");
        return "forward:/providerList.html";
    }

    @RequestMapping("/providerView.html")
    public String providerView(int id ,Model model,int pageNum){
        Provider provider = providerService.getProviderByPrimaryKey(id);
        model.addAttribute("provider",provider);
        model.addAttribute("pageNum",pageNum);
        return "providerView";
    }

    /**
     * 根据ID展示修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/providerUpdate.html",method = RequestMethod.GET)
    public String providerUpdate(int id , Model model,int pageNum){
        Provider provider = providerService.getProviderByPrimaryKey(id);
        model.addAttribute("provider",provider);
        model.addAttribute("pageNum",pageNum);
        return "providerUpdate";
    }

    /**
     * 接受提交的修改请求，根据ID修改
     * @param provider
     * @param model
     * @return
     */
    @RequestMapping(value = "/providerUpdate.html",method = RequestMethod.POST)
    public String upDateProvider(Provider provider , Model model,int pageNum){
        int result  = providerService.upDateProviderByPrimaryKey(provider);
        return providerList(pageNum,8,model);
    }


    @RequestMapping(value = "/providerAdd.html" ,method = RequestMethod.GET)
    public String providerAdd(Model model){
        model.addAttribute("provider",new Provider());
        return "providerAdd";
    }

    @RequestMapping(value = "/providerAdd.html" ,method = RequestMethod.POST)
    public String doAddProvider(Provider provider,Model model){
        int flag =0;
        //判断传真号码是否合法
        if(provider.getFaxNum()==null||"".equals(provider.getFaxNum())){
            model.addAttribute("faxNumError","不能为空");
            flag++;
        }else if(!CheckByRegularExpression.isTele(provider.getFaxNum())){
            model.addAttribute("faxNumError","传真号码不合法，(不能包含空格特殊字符)");
            flag++;
        }
        //判断厂商编号是否合法
        if(provider.getProId()==null||"".equals(provider.getProId())){
            model.addAttribute("ProIdError","不能为空");
            flag++;
        }else if(!CheckByRegularExpression.isMatchProviderId(provider.getProId())){
            model.addAttribute("ProIdError","编号格式不正确，请输入zcf-k开始加上2至4位数字");
            flag++;
        }else {
            Provider hasThisProvider = providerService.getProviderByProId(provider);
            if(hasThisProvider!=null){
                model.addAttribute("ProIdError","该编号已存在,请重新输入");
                flag++;
            }
        }
        //判断电话号码是否合法
        if(provider.getContactPhone()==null||"".equals(provider.getContactPhone())){
                model.addAttribute("ContactPhoneError","不能为空");
                flag++;
        }else if(!CheckByRegularExpression.isTele(provider.getContactPhone())){
            model.addAttribute("ContactPhoneError","手机号码不合法，请重新输入");
            flag++;
        }
        //判断联系人是否合法
        if(provider.getContacts()==null||"".equals(provider.getContacts())){
            model.addAttribute("ContactError","不能为空");
            flag++;
        }else if(provider.getContacts().indexOf(' ')!=-1){
            model.addAttribute("ContactError","不能包含空格");
            flag++;
        }
        //判断供应商名称是否合法
        if(provider.getProviderName()==null||"".equals(provider.getProviderName())){
            model.addAttribute("ProviderNameError","不能为空");
            flag++;
        }else if(provider.getProviderName().indexOf(' ')!=-1){
            model.addAttribute("ProviderNameError","不能包含空格");
            flag++;
        }else {
            Provider hasThisProvider =providerService.getProviderByName(provider);
            if(hasThisProvider!=null){
                model.addAttribute("ProviderNameError","该供应商已存在，请修改");
                flag++;
            }
        }
        //判断地址是否合法
        if(provider.getAddress()==null||"".equals(provider.getAddress())){
            model.addAttribute("AddressError","不能为空");
            flag++;
        }else if(provider.getAddress().indexOf(' ')!=-1){
            model.addAttribute("AddressError","不能包含空格");
            flag++;
        }

        if(flag>0){
            model.addAttribute("provider",provider);
            return "/providerAdd.html";
        }

        //全部合法就进行插入操作
        int result = providerService.addProvider(provider);
        System.out.println(result==1?"添加成功":"添加失败");
        return "redirect:providerList.html";
    }


    @RequestMapping(value = "/queryProvider",method = RequestMethod.GET)
    public String doQueryProvider(Model model,Provider provider){
        List<Provider> providerList = new ArrayList<>();
        if(provider.getProviderName()==null||"".equals(provider.getProviderName().replace(" ",""))){
            model.addAttribute("queryError","请输入查询内容");
            model.addAttribute("providers",providerList);
            model.addAttribute("totalPage", 1);
            model.addAttribute("currentPage", 1);
            return "providerList";
        }
        Provider queryProvider = providerService.getProviderByName(provider);
        if(queryProvider==null){
            model.addAttribute("queryError","没有查询到");
            model.addAttribute("providers",providerList);
            model.addAttribute("totalPage", 1);
            model.addAttribute("currentPage", 1);
            return "providerList";
        }
        providerList.add(queryProvider);
        model.addAttribute("providers",providerList);
        model.addAttribute("totalPage", 1);
        model.addAttribute("currentPage", 1);
        return "providerList";
    }



}
