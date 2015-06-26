package com.poplar.server.appExecutor.appInterface;

import com.poplar.server.context.app.AppRequest;
import com.poplar.server.context.app.AppResponse;

/**
 * User: FR
 * Time: 5/18/15 2:57 PM
 */
public abstract class AbstractController {

    public abstract AppResponse onAppRequest(AppRequest request);

}
