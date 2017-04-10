package com.topolr.polling.router.impl;

import com.topolr.polling.base.ConnectManager.PollingConnect;
import com.topolr.polling.base.message.MessageWrapper;
import com.topolr.polling.router.BaseRouter;

public class WithoutGroupRouter extends BaseRouter {

    @Override
    public void rout(PollingConnect connect, MessageWrapper wrapper) throws Exception {
        connect.getConnectManager().broadCastWithOutGroupId(wrapper.getTo(), wrapper.getMessage());
    }

}
