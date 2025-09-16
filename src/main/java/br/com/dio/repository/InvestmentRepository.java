package br.com.dio.repository;

import br.com.dio.exception.AccountWithInvestmentException;
import br.com.dio.exception.InvestmentNotFoundException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.InvestmentWallet;
import br.com.dio.model.Investment;
import java.util.ArrayList;
import java.util.List;
import static br.com.dio.repository.CommonsRepository.checkFundsForTransaction;
import static br.com.dio.repository.CommonsRepository.checkeFundsForTransaction;

public class InvestmentRepository {

    private long nextId;
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    public Investment create(final long tax, final long daysToRest, final long initialFunds){
        this.nextId++;
        var investment = new Investment(nextId,tax,initialFunds);
        investments.add(investment);
        return investment;
    }

    public InvestmentWallet initInvestment(final AccountWallet account, final String id) {

        var accountUse =  wallets.stream().map(InvestmentWallet::getAccount).toList();
        for(int i = 0; i < accountUse.size(); i++){
            if(accountUse.contains(accountUse.get(i))){
                throw new AccountWithInvestmentException("Pix"+accountUse.get(i)+"já está em uso");
            }
        }

        var investment = findById(id);
        checkFundsForTransaction(account, investment.initialFunds());
        var wallet = new InvestmentWallet(investment, account, investment.initialFunds());
        wallets.add(wallet);
        return wallet;
    }

    public InvestmentWallet deposit(final String pix, final long funds){
        var wallet = findByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds),wallet.getService(),"Investimentos");
        return wallet;
    }

    public InvestmentWallet withDraw(final String pix, final long funds){
        var wallet = findByAccountPix(pix);
        checkeFundsForTransaction(wallet,funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getService(),"Saque de investimentos");

        if(wallet.getFunds()==0){
            wallets.remove(wallet);
        }
        return wallet;
    }

    public void updateAmmount(final long percent){
        wallets.forEach(w-> w.updateAmount(percent));
    }

    public Investment findById(final String pix){
        return wallets.stream()
                .filter(w-> w.get)
                .findFirst()
                .ifPresentOrElse(()-> new InvestmentNotFoundException("A carteira não foi encontrada"));
    }

    public InvestmentWallet findByAccountPix(final String pix){
        return wallets.stream()
                .filter(w-> w.get)
                .findFirst()
                .ifPresentOrElse(()-> new WalletNotFoundException("A carteira não foi encontrada"));
    }

    public List<Investment> list(){
        return this.investments;
    }

    public List<InvestmentWallet> listInvestmentsWallet(){
        return this.wallets;
    }

}
