package fault.retry;

import com.lyon.lyonrpc.fault.retry.FixedIntervalRetryStrategy;
import com.lyon.lyonrpc.fault.retry.NoRetryStrategy;
import com.lyon.lyonrpc.fault.retry.RetryStrategy;
import com.lyon.lyonrpc.model.RpcResponse;
import org.junit.Test;

/**
 * 重试策略模式
 */
public class RetryStrategyTest {
    RetryStrategy retryStrategy = new NoRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.dpRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
