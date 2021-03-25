package com.github.miyohide;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

public class TimerTriggerHandler extends FunctionInvoker<String, String> {
    @FunctionName("minutesBatch")
    public void minutesBatch(
            @TimerTrigger(name = "minutesBatch", schedule = "0 * * * * *") String timerInfo,
            ExecutionContext context
    ) {
        context.getLogger().info("Java Timer trigger process start.");
        handleRequest("minutesBatch start", context);
        context.getLogger().info("Java Timer trigger process end.");
    }
}
