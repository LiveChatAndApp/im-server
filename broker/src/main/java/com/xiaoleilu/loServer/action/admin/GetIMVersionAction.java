/*
 * This file is part of the Wildfire Chat package.
 * (c) Heavyrain2012 <heavyrain.lee@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package com.xiaoleilu.loServer.action.admin;

import cn.wildfirechat.common.APIPath;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.InputGetChatroomInfo;
import cn.wildfirechat.pojos.OutputStringList;
import cn.wildfirechat.pojos.SystemVersionDto;
import com.xiaoleilu.loServer.RestResult;
import com.xiaoleilu.loServer.annotation.HttpMethod;
import com.xiaoleilu.loServer.annotation.Route;
import com.xiaoleilu.loServer.handler.Request;
import com.xiaoleilu.loServer.handler.Response;
import io.moquette.BrokerConstants;
import io.moquette.persistence.UserClientEntry;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.internal.StringUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Route(APIPath.IM_VERSION)
@HttpMethod("POST")
public class GetIMVersionAction extends AdminAction {

    @Override
    public boolean isTransactionAction() {
        return true;
    }

    @Override
    public boolean action(Request request, Response response) {
        if (request.getNettyRequest() instanceof FullHttpRequest) {
            String imVersion = iconfig.getProperty(BrokerConstants.IM_VERSION);

            RestResult result;
            if (!StringUtil.isNullOrEmpty(imVersion)) {
                result = RestResult.ok(new SystemVersionDto("IM", imVersion));
            } else {
                result = RestResult.resultOf(ErrorCode.ERROR_CODE_NOT_EXIST);
            }
            setResponseContent(result, response);

        }
        return true;
    }
}
