package br.com.dio;

import br.com.dio.exception.AccountFoundException;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static AccountRepository accountRepository;
    static InvestmentRepository investmentRepository;

    public static void main(String[] args) {
            System.out.println("Ola seja bem vindo ao DIO Bank");

            while (true) {
                System.out.println("Selecione a operacao desejada");
                System.out.println("1 - Criar uma conta");
                System.out.println("2 - Criar um investimento");
                System.out.println("3 - Fazer um investimento");
                System.out.println("4 - Depositar na conta");
                System.out.println("5 - Sacar da conta");
                System.out.println("6 - Transferencia entre contas");
                System.out.println("7 - Investir");
                System.out.println("8 - Sacar investimento");
                System.out.println("9 - Listar contas");
                System.out.println("10 - Listar Investimentos");
                System.out.println("11 - Listar contas de investimento");
                System.out.println("12 - Atualizar investimentos");
                System.out.println("13 - Historico de conta");
                System.out.println("14 - Historico de conta");
                System.out.println("15 - Sair");

                var option = scanner.nextInt();
                       switch (option) {
                           case 1: createAccount();
                           case 2: createInvestments();
                           case 3:
                           case 4:deposit();
                           case 5:withDraw();
                           case 6:transferToAccount();
                           case 7:
                           case 8:
                           case 9:
                           case 10: investmentRepository.list().forEach(System.out::println);
                           case 11: investmentRepository.listInvestmentsWallet().forEach(System.out::println);
                           case 12:{
                               investmentRepository.updateAmmount(10);
                               System.out.println("Investimentos ajustados");
                           }
                           case 13:
                           case 14:System.exit(0);
                           default: System.out.println("Opção inválida");
                        }
            }



    }

    private static void createAccount(){

        System.out.println("Informe as chaves pix separadas por ;");
        var pix = Arrays.stream(scanner.next().split(";")).toList();
        System.out.println("Informe o valor do deposito  ;");
        var amount = scanner.nextLong();
        var wallet = accountRepository.create(pix,amount);
        System.out.println("Conta criada"+wallet);

    }

    private static void createInvestments(){
        System.out.println("Informe a taxa do investimento");
        var tax = scanner.nextInt();
        System.out.println("Informe o valor do deposito  ;");
        var initialFunds = scanner.nextLong();
        var investiment = investmentRepository.create(tax,initialFunds);
        System.out.println("Investimento criado"+investiment);
    }

    private static void withDraw(){
        System.out.println("Informe a chave pix para saque:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será sacado:");
        var amount = scanner.nextLong();

        try{
            accountRepository.withDraw(pix,amount);
        }catch (AccountFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void deposit(){
        System.out.println("Informe a chave deposito pix:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será depostiado");
        var amount = scanner.nextLong();
        try{
            accountRepository.deposit(pix,amount);
        }catch (AccountFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void transferToAccount(){
        System.out.println("Informe a chave deposito pix:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será depostiado");
        var amount = scanner.nextLong();
        try{
            accountRepository.deposit(pix,amount);
        }catch (AccountFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

}