package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Банкомат.
 * Инициализируется набором купюр и умеет выдавать купюры для заданной суммы, либо отвечать отказом.
 * При выдаче купюры списываются с баланса банкомата.
 * Допустимые номиналы: 50₽, 100₽, 500₽, 1000₽, 5000₽.
 */
class ATM {
    public NavigableMap<Kupyra, Long> getBalance() {
        return balance;
    }

    private NavigableMap<Kupyra, Long> balance;

    public ATM(NavigableMap<Kupyra, Long> balance) {
        this.balance = balance;
    }

    public static void main(String[] args) {
        NavigableMap<Kupyra, Long> balance = new TreeMap<>();
        balance.put(Kupyra.FIFTY, 1l);
        balance.put(Kupyra.ONE_HUNDRED, 1l);
        balance.put(Kupyra.FIVE_HUNDRED, 1l);
        balance.put(Kupyra.ONE_THOUSAND, 1l);
        balance.put(Kupyra.FIVE_THOUSAND, 1l);
        Long moneyToGet = 6660l;
        ATM atm = new ATM(balance);
        Map<Kupyra, Long> gettedMoney = atm.getMoney(moneyToGet);

        System.out.println(gettedMoney);
    }

    public Map<Kupyra, Long> getMoney(Long moneyToGet) {
        long moneysLeft = moneyToGet;
        Map<Kupyra, Long> gettedMoney = new HashMap<>();
        for (Map.Entry<Kupyra, Long> entry : balance.descendingMap().entrySet()) {
            Long countKupyres = countKupyres(entry.getKey(), moneyToGet, entry.getValue());
            if (countKupyres != null && countKupyres > 0) {
                gettedMoney.put(entry.getKey(), countKupyres);
                moneysLeft = moneysLeft - countKupyres * entry.getKey().getValue();
            }
        }
        if(moneysLeft>0l){
            throw new RuntimeException("Cannot give money, low balance");
        }
        for(Map.Entry<Kupyra, Long> entry : gettedMoney.entrySet()){
            balance.put(entry.getKey(),balance.get(entry.getKey())-entry.getValue());
        }
        return gettedMoney;
    }

    public enum Kupyra {
        FIFTY(50), ONE_HUNDRED(100), FIVE_HUNDRED(500), ONE_THOUSAND(1000), FIVE_THOUSAND(5000);
        private long value;

        public long getValue() {
            return value;
        }

        Kupyra(long value) {
            this.value = value;
        }
    }

    public static Long countKupyres(Kupyra kupyra, Long moneyToGet, Long countFiveThousandKupyres) {
        if (countFiveThousandKupyres != null && countFiveThousandKupyres > 0 && moneyToGet - kupyra.getValue() > 0) {
            long countGettedKupyres = moneyToGet / kupyra.getValue();
            return Math.min(countGettedKupyres, countFiveThousandKupyres);
        }
        return 0l;
    }
}