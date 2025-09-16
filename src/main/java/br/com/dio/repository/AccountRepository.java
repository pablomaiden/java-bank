package br.com.dio.repository;

import br.com.dio.exception.AccountFoundException;
import br.com.dio.exception.PixUseException;
import br.com.dio.model.AccountWallet;

import java.util.List;

public class AccountRepository {

    private List<AccountWallet> accounts;

    public AccountWallet findByPix(final String pix){
        return accounts.stream().filter(a-> a.getPix().contains(pix)).findFirst().orElseThrow(()-> new AccountFoundException("A Conta com a chave pix '"+pix+"' não existe! "));
    }

    private List<AccountWallet> list(){
        return this.accounts;
    }

    public AccountWallet create(final List<String> pix, final long initialFunds){

        var pixUse =  accounts.stream().flatMap(a -> a.getPix().stream()).toList();
        for(int i = 0; i < pix.size(); i++){
            if(pixUse.contains(pix.get(i))){
                throw new PixUseException("Pix"+pix.get(i)+"já está em uso");
            }
        }

        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount){
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "DEPOSITO");
    }

    public long withDraw(final String pix, final long amount){
        var source = findByPix(pix);
        checkeFundsForTransaction(source,amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount){
        var source = findByPix(sourcePix);
        checkeFundsForTransaction(source,amount);
        var target = findByPix(targetPix);
        var message = "pix enviado de '"+ sourcePix +"' para '"+targetPix;
        target.addMoney(source.reduceMoney(amount), source.getService(),message);
    }


    private void checkeFundsForTransaction(AccountWallet soruce, long amount) {


    }


}
