package rpcservice;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/rpc/myservice")
public interface MyService {
    int div(@JsonRpcParam("a") int a, @JsonRpcParam("b") int b);


}
