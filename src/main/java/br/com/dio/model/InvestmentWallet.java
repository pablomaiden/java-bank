package br.com.dio.model;


import lombok.Getter;
import lombok.ToString;

import static br.com.dio.model.BankService.INVESTMENT;

@ToString
@Getter
public class InvestmentWallet extends Wallet{

    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getService(), "investimento");
    }
}
