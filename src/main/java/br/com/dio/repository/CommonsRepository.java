package br.com.dio.repository;


import br.com.dio.exception.NoFundsEnoughtException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.BankService;
import br.com.dio.model.Money;
import br.com.dio.model.MoneyAudit;
import lombok.AllArgsConstructor;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public final class CommonsRepository {

    public static void checkFundsForTransaction(final AccountWallet source, final long amount){
        if(source.getFunds() < amount){
            throw new NoFundsEnoughtException("Sua conta não tem dinheiro o suficiente para realizar esta transação");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description){
        var history = new MoneyAudit(transactionId, BankService.ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(()-> new Money(history)).limit(funds).toList();
    }

}
