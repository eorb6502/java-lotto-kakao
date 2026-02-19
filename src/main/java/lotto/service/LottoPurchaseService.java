package lotto.service;

import lotto.model.LottoNumberGenerator;
import lotto.model.LottoNumbers;
import lotto.model.PurchaseResult;
import lotto.model.PurchasedLottoNumbers;

import java.util.List;
import java.util.stream.Stream;

public class LottoPurchaseService {
    private final LottoNumberGenerator lottoGenerator;

    public LottoPurchaseService(LottoNumberGenerator lottoGenerator) {
        validateLottoGenerator(lottoGenerator);
        this.lottoGenerator = lottoGenerator;
    }

    public PurchaseResult purchase(List<LottoNumbers> manualLottoNumbers, int autoCount) {
        validateManualLottoNumbers(manualLottoNumbers);
        List<PurchasedLottoNumbers> manualNumbers = manualLottoNumbers.stream()
            .map(PurchasedLottoNumbers::new)
            .toList();
        List<PurchasedLottoNumbers> autoNumbers = lottoGenerator.generate(autoCount);
        List<PurchasedLottoNumbers> purchasedNumbers = Stream.concat(
            manualNumbers.stream(),
            autoNumbers.stream()
        ).toList();
        return new PurchaseResult(purchasedNumbers, manualNumbers.size(), autoCount);
    }

    private void validateManualLottoNumbers(List<LottoNumbers> manualLottoNumbers) {
        if (manualLottoNumbers == null) {
            throw new IllegalArgumentException("수동 구매 번호 목록은 비어 있을 수 없습니다.");
        }
        for (LottoNumbers manualLottoNumber : manualLottoNumbers) {
            if (manualLottoNumber == null) {
                throw new IllegalArgumentException("수동 구매 번호에는 null이 포함될 수 없습니다.");
            }
        }
    }

    private void validateLottoGenerator(LottoNumberGenerator lottoGenerator) {
        if (lottoGenerator == null) {
            throw new IllegalArgumentException("로또 번호 생성기는 비어 있을 수 없습니다.");
        }
    }
}
