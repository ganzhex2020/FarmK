package com.jony.farm.di




import com.combodia.httplib.factory.RetrofitClient
import com.jony.farm.config.Const.BASE_URL
import com.jony.farm.model.api.ServiceApi
import com.jony.farm.model.repository.LocalDataSource
import com.jony.farm.model.repository.RemoteDataSource
import com.jony.farm.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val commonModule = module {
    single { RetrofitClient.createApi(ServiceApi::class.java, BASE_URL) }
//    single { AuthonManager() }
//    single { AppDatabase.getInstance(MyApp.CONTEXT) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { MarketViewModel(get()) }
    viewModel { LuckDrawViewModel(get()) }
    viewModel { FarmViewModel(get(),get()) }
    viewModel { MineViewModel(get(),get()) }
    viewModel { LoginViewModel(get(),get()) }
    viewModel { UserInfoViewModel(get(),get()) }
    viewModel { ShareViewModel(get()) }
    viewModel { RechargeViewModel(get()) }
    viewModel { WithDrawViewModel(get()) }
    viewModel { BankCardListViewModel(get()) }
    viewModel { BindCardViewModel(get()) }
    viewModel { AnimalHistoryViewModel(get()) }
    viewModel { FodderDetailViewModel(get()) }
    viewModel { ChangePwdViewModel(get()) }
    viewModel { InviteViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { FundDetailViewModel(get()) }
    viewModel { TeamViewModel(get()) }
    viewModel { TeamFundViewModel(get()) }
    viewModel { BlockNewsViewModel(get()) }
    viewModel { RankViewModel(get()) }
    viewModel { FAQViewModel(get()) }
    viewModel { CommunityViewModel(get()) }
    viewModel { SubFarmViewModel(get()) }
    viewModel { ChatViewModel(get()) }

}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource() }

}
val appModule = listOf(commonModule, viewModelModule, repositoryModule)
