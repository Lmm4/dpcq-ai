package org.dpcq.ai.rpc;

import org.dpcq.ai.rpc.dto.UserBalanceVo;
import org.dpcq.ai.rpc.dto.WalletMsg;
import org.dpcq.ai.rpc.dto.WalletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("wallet")
public interface FeignWalletApi {

    @GetMapping("/internal/wallet/balanceList")
    List<UserBalanceVo> balanceList(@RequestParam("userIds") List<Long> userIds, @RequestParam("symbol") String symbol);

    @GetMapping("/internal/wallet/balance")
    Long getBalance(@RequestParam("userId") Long userId, @RequestParam("symbol") String symbol,@RequestParam("roleType") String roleType);

    @PostMapping("/internal/wallet/balanceChange")
    WalletResponse balanceChange(@RequestBody WalletMsg msg);

}
