package builder;

import java.util.List;

public class BuilderStage {

    private Bank bank;

    public BuilderStage(List<BankItem> bankItems) {
        bank = new Bank(bankItems);
    }
}
