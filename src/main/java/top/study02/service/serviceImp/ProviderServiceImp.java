package top.study02.service.serviceImp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.study02.mapper.ProviderMapper;
import top.study02.pojo.PackageProvider;
import top.study02.pojo.Provider;
import top.study02.service.ProviderService;

import java.util.Date;
import java.util.List;

@Service
public class ProviderServiceImp implements ProviderService {

    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public PackageProvider getProvidersByPageNumAndPageSize(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Provider> allProviders = providerMapper.getAllProviders();
        PageInfo<Provider> info = new PageInfo<>(allProviders);
        PackageProvider packageProvider = new PackageProvider();
        packageProvider.setProviders(allProviders);
        packageProvider.setTotalPageNum(info.getPages());
        return packageProvider;
    }

    @Override
    public int deleteProviderById(int id){
        return providerMapper.deleteProviderByPrimaryKey(id);
    }

    @Override
    public Provider getProviderByPrimaryKey(int id) {

        return  providerMapper.getProviderByPrimaryKey(id);
    }

    @Override
    public int upDateProviderByPrimaryKey(Provider provider) {

        return providerMapper.upDateProviderByPrimaryKey(provider);
    }

    @Override
    public int addProvider(Provider provider) {
        provider.setCreateDate(new Date());
        return providerMapper.addProvider(provider);
    }

    @Override
    public Provider getProviderByProId(Provider provider) {
        return providerMapper.getProviderByProId(provider);
    }

    @Override
    public Provider getProviderByName(Provider provider) {
        return providerMapper.getProviderByProviderName(provider);
    }

    @Override
    public List<Provider> getProviderInBills() {
        return providerMapper.getProviderInBills();
    }

    @Override
    public List<Provider> getAllProviders() {
        return providerMapper.getAllProviders();
    }
}
