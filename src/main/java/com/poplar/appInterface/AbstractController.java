package com.poplar.appInterface;

import com.poplar.context.app.AppRequest;
import com.poplar.context.app.AppResponse;

/**
 * User: FR
 * Time: 5/18/15 2:57 PM
 */
public abstract class AbstractController {

    public abstract AppResponse onAppRequest(AppRequest request);

}
