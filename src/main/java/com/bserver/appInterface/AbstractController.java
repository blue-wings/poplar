package com.bserver.appInterface;

import com.bserver.context.app.AppRequest;
import com.bserver.context.app.AppResponse;

/**
 * User: FR
 * Time: 5/18/15 2:57 PM
 */
public abstract class AbstractController {

    public abstract AppResponse onAppRequest(AppRequest request);

}
