package com.ltf.rpc.service.rpcimpl;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import rpcservice.MyService;

@Service
@AutoJsonRpcServiceImpl
public class MyServiceImpl implements MyService {
    @Override
    public int div(int a, int b) {
        return a + b;
    }
}
